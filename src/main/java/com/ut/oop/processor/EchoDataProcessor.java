package com.ut.oop.processor;
// for testing purposes, decodes byteBuffer into string
// TODO: message parsing, store requests somewhere

import com.ut.oop.MessageData;
import com.ut.oop.Socket;

import java.nio.ByteBuffer;

public class EchoDataProcessor implements DataProcessor {
    @Override
    public void process(Socket socket, MessageData message) {

        String messageAsString = message.toString();
        System.out.println(messageAsString + " from TestDataProcessor");
        socket.dataWriter.write(socket, message.getMessage());

    }
}
