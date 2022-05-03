package com.ut.oop.controllers;
import com.ut.oop.Socket;
import java.nio.ByteBuffer;

public class RasPiController implements DataController {

  @Override
  public int read(Socket socket, ByteBuffer byteBuffer) {
    return 0;
  }
}
