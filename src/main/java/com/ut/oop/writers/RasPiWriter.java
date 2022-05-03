package com.ut.oop.writers;

import com.ut.oop.Socket;

import java.nio.ByteBuffer;

public class RasPiWriter implements DataWriter{
  @Override
  public int write(Socket socket, ByteBuffer byteBuffer) {
    return 0;
  }
}
