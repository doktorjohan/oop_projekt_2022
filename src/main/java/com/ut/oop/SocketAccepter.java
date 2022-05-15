package com.ut.oop;

import com.ut.oop.controllers.ArduinoController;
import com.ut.oop.controllers.DataController;
import com.ut.oop.controllers.RasPiController;
import com.ut.oop.processor.DataProcessor;
import com.ut.oop.processor.EchoDataProcessor;
import com.ut.oop.controllers.FileDataController;
import com.ut.oop.processor.ThresholdDataProcessor;
import com.ut.oop.writers.FileDataWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;

public class SocketAccepter implements Runnable {
    private final Logger logger;

    private final Queue<Socket> socketQueue;
    private int nextSocketId;

    public SocketAccepter(Queue<Socket> socketQueue) {
        this.logger = LoggerFactory.getLogger(Server.class);

        this.socketQueue = socketQueue;
        this.nextSocketId = 1;
    }

    @Override
    public void run() {
        ServerSocketChannel serverSocketChannel;

        try {
            serverSocketChannel = ServerSocketChannel.open();
            ServerSocket serverSocket = serverSocketChannel.socket();
            serverSocket.bind(new InetSocketAddress(ServerConfig.PORT));

            logger.info("Server socket channel opened on port " + ServerConfig.PORT);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return;
        }

        while (true) {
            try {

                final SocketChannel client = serverSocketChannel.accept();

                this.socketQueue.add(new Socket(nextSocketId++, client, new FileDataController(), new FileDataWriter(), new EchoDataProcessor()));


                Runnable socketSetup = () -> {

                    DataProcessor processor;
                    String processorLabel;
                    ByteBuffer typeBuffer = ByteBuffer.allocate(100);

                    try {
                        client.read(typeBuffer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    String type = new String(typeBuffer.array()).trim();

                    if ("threshold".equals(type)) {
                        processor = new ThresholdDataProcessor();
                        processorLabel = "threshold";

                    } else {
                        processor = new EchoDataProcessor();
                        processorLabel = "echo";
                    }
                    this.socketQueue.add(new Socket(nextSocketId++, client, new FileDataController(), new FileDataWriter(), processor));
                    logger.info("Socket accepted: " + (nextSocketId - 1) + " " + processorLabel);

                };
                new Thread(socketSetup).start();


            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }
}
