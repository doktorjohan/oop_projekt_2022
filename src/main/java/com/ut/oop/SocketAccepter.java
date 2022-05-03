package com.ut.oop;

import com.ut.oop.processor.EchoDataProcessor;
import com.ut.oop.controllers.FileDataController;
import com.ut.oop.writers.FileDataWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
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
            serverSocketChannel.bind(new InetSocketAddress(ServerConfig.PORT));

            logger.info("Server socket channel opened on port " + ServerConfig.PORT);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return;
        }

        while (true) {
            try {
                SocketChannel socketChannel = serverSocketChannel.accept();

                Runnable socketSetup = () -> {
                    // TODO: siin saab kasutaja sisendit küsidda socketi seadistamiseks
                    System.out.println("To choose a device or format type: arduino/rasp-pi/tekst");
                    List<String> formats = Arrays.asList("arduino", "rasp-pi", "tekst");
                    Scanner sc = new Scanner(System.in);
                    label:
                    while (true) {
                        String request = sc.next().toLowerCase(Locale.ROOT);
                        if (!formats.contains(request)) {
                            System.out.println("Choose correct format to proceed.");
                        }
                        else{
                            switch (request) {
                                case "arduino":
                                    break label;
                                case "rasp-pi":
                                    break label;
                                case "tekst":
                                    break label;
                            }
                        }

                    }
                    this.socketQueue.add(new Socket(nextSocketId++, socketChannel, new FileDataController(), new FileDataWriter(), new EchoDataProcessor()));
                    logger.info("Socket accepted: " + (nextSocketId - 1));
                };

                new Thread(socketSetup).start();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }
}
