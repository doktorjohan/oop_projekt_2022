package com.ut.oop.writers;

import com.ut.oop.Socket;

import java.nio.ByteBuffer;

public interface DataWriter {
    int write(Socket socket, ByteBuffer byteBuffer);
}


