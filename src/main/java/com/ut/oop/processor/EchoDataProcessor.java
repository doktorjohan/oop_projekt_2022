package com.ut.oop.processor;

import com.ut.oop.MessageData;
import com.ut.oop.Socket;

public class EchoDataProcessor implements DataProcessor {
    @Override
    public void process(Socket socket, MessageData message) {

        String messageAsString = message.toString();
        System.out.println(messageAsString);

        socket.write(message.getMessage());

    }
}
