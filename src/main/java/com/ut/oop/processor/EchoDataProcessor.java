package com.ut.oop.processor;
// for testing purposes, decodes byteBuffer into string
// TODO: message parsing, store requests somewhere

import com.ut.oop.MessageData;
import com.ut.oop.Socket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;

public class EchoDataProcessor implements DataProcessor {
    @Override
    public void process(Socket socket, MessageData message) {

        String messageAsString = message.toString();
        System.out.println(messageAsString + " from TestDataProcessor");

        socket.write(message.getMessage());

    }
}
