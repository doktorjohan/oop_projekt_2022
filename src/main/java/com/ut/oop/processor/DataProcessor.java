package com.ut.oop.processor;

import com.ut.oop.MessageData;
import com.ut.oop.Socket;

import java.nio.ByteBuffer;

public interface DataProcessor {

    void process(Socket socket, MessageData message);

}
