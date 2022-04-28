package com.ut.oop;

import java.nio.ByteBuffer;

public class MessageData {

  private final ByteBuffer message;
  private final int messageSize;

  public MessageData(ByteBuffer message, int messageSize) {
    this.message = message;
    this.messageSize = messageSize;
  }

  public String toString() {
    return new String(message.array());
  }

  public ByteBuffer getMessage() {
    return message;
  }

  public int getMessageSize() {
    return messageSize;
  }
}
