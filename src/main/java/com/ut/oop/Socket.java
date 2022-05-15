package com.ut.oop;

import com.ut.oop.controllers.DataController;
import com.ut.oop.processor.DataProcessor;
import com.ut.oop.writers.DataWriter;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Socket {
    private final int id;
    public final SocketChannel socketChannel;
    public final DataController dataController;
    public final DataWriter dataWriter;
    public final DataProcessor dataProcessor;
    private MessageData message;
    private boolean endOfStream;

    ByteBuffer readBytes = ByteBuffer.allocate(1024);

    public Socket(int id, SocketChannel socketChannel, DataController dataController,
                  DataWriter dataWriter, DataProcessor dataProcessor) {

        this.id = id;
        this.socketChannel = socketChannel;
        this.dataController = dataController;
        this.dataWriter = dataWriter;
        this.dataProcessor = dataProcessor;
        this.endOfStream = false;

    }

    public int getId() {
        return id;
    }

    public boolean isEndOfStream() {
        return endOfStream;
    }

    public void setEndOfStream(boolean endOfStream) {
        this.endOfStream = endOfStream;
    }

    public MessageData getMessage() {
        return message;
    }

    public void setMessage(MessageData message) {
        this.message = message;
    }

    public void read() throws IOException {
        int totalBytesRead = this.dataController.read(this, readBytes);
        message = new MessageData(readBytes, totalBytesRead);
    }

    public void process() {
        this.dataProcessor.process(this, message);
    }

    public void write(ByteBuffer byteBuffer) {
        this.dataWriter.write(this, byteBuffer);
    }

    public String toString() {
        return "Socket number: " + id + " processor: " + this.dataProcessor.toString();
    }
}
