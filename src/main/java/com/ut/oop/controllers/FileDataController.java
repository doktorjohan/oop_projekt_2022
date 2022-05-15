package com.ut.oop.controllers;

import com.ut.oop.Socket;

import java.io.IOException;
import java.nio.ByteBuffer;

public class FileDataController implements DataController {
  @Override
  public int read(Socket socket, ByteBuffer byteBuffer) {
    try {

      if (!(socket.socketChannel == null)) {


        int bytesRead = socket.socketChannel.read(byteBuffer);
        int totalBytesRead = bytesRead;
        System.out.println(totalBytesRead);

        while (bytesRead > 0) {
          bytesRead = socket.socketChannel.read(byteBuffer);
          byteBuffer.flip();
          totalBytesRead += bytesRead;
        }

        if (bytesRead == -1)
          socket.setEndOfStream(true);

        return totalBytesRead;

      }

    } catch (IOException e) {
      e.printStackTrace();
    }
    return -1;
  }
}
