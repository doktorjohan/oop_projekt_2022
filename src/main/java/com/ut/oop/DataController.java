package com.ut.oop;

import java.nio.ByteBuffer;

public interface DataController {

    int read(Socket socket, ByteBuffer byteBuffer);

}
