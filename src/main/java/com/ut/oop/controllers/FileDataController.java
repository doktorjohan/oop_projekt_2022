package com.ut.oop.controllers;

import com.ut.oop.DataController;
import com.ut.oop.Socket;

import java.io.IOException;
import java.nio.ByteBuffer;

public class FileDataController implements DataController {
    @Override
    public int read(Socket socket, ByteBuffer byteBuffer) {
        try {
            int bytesRead = socket.socketChannel.read(byteBuffer);
            int totalBytesRead = bytesRead;

            while (bytesRead > 0) {
                bytesRead = socket.socketChannel.read(byteBuffer);
                totalBytesRead += bytesRead;
            }

            if (bytesRead == -1)
                socket.setEndOfStream(true);

            return totalBytesRead;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
