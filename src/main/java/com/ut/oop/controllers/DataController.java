package com.ut.oop.controllers;

import com.ut.oop.Socket;

import java.nio.ByteBuffer;

public interface DataController {

    int read(Socket socket, ByteBuffer byteBuffer);


}
