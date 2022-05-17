package com.ut.oop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;

public class SocketProcessor implements Runnable {
  private final Logger logger;

  private final Queue<Socket> socketQueue;

  private final Selector readSelector;
  private final Selector writeSelector;

  public SocketProcessor(Queue<Socket> socketQueue) throws IOException {
    this.logger = LoggerFactory.getLogger(Server.class);

    this.socketQueue = socketQueue;

    this.readSelector = Selector.open();
    this.writeSelector = Selector.open();
  }

  @Override
  public void run() {
    logger.info("Socket processor now on duty and waiting for clients");

    while (true) {
      try {
        addSockets();
        readSockets();
        processSockets();
      } catch (IOException e) {
        logger.error(e.getMessage());
      }

      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        logger.warn("My short break was interrupted, now I'm grumpy");
      }
    }
  }

  private void addSockets() {
    Socket socket = this.socketQueue.poll();

    while (socket != null) {
      try {
        socket.socketChannel.configureBlocking(false);

        SelectionKey key = socket.socketChannel.register(this.readSelector, SelectionKey.OP_READ);
        key.attach(socket);

        logger.info("Added socket " + socket.toString());
        readSelector.select();
        socket = this.socketQueue.poll();
      } catch (IOException e) {
        logger.error(e.getMessage());
      }
    }
  }

  private void readSockets() throws IOException {

    Set<SelectionKey> selectedKeys = this.readSelector.selectedKeys();
    Iterator<SelectionKey> i = selectedKeys.iterator();

    while (i.hasNext()) {
      SelectionKey key = i.next();

      if (key.attachment() == null) {
        logger.info("socket is null");
        return;
      }

      if (key.isReadable()) {
        readSocket(key);
      }
      i.remove();
    }

  }

  private void readSocket(SelectionKey selectionKey) {

    Socket socket = (Socket) selectionKey.attachment();
    logger.info("reading socket " + socket.toString());

    try {

      socket.read();
      SelectionKey key = socket.socketChannel.register(this.writeSelector, SelectionKey.OP_WRITE);
      key.attach(socket);
      this.writeSelector.select();

      if (socket.isEndOfStream()) {
        int socketId = socket.getId();

        try {
          selectionKey.attach(null);
          selectionKey.cancel();
          selectionKey.channel().close();
          System.out.println("Closed socket channel");
          logger.info("Closed socket channel");
        } catch (IOException e) {
          logger.error("Failed to close socket channel: " + socketId);
        }

        logger.info("End of stream reached: " + socketId);
      }

    } catch (IOException ioe) {
      ioe.printStackTrace();
      logger.error(ioe.getMessage() + " on readSocket()");
    }


  }


  private void processSockets() {
    Set<SelectionKey> selectedKeys = this.writeSelector.selectedKeys();
    Iterator<SelectionKey> i = selectedKeys.iterator();

    while (i.hasNext()) {
      SelectionKey key = i.next();

      if (key.attachment() == null) {
        logger.info("socket is null");
        return;
      }

      if (key.isWritable()) {
        processSocket(key);
      }
      i.remove();
    }
  }

  private void processSocket(SelectionKey selectionKey) { // socket.process() processes data and writes to socket
    Socket socket = (Socket) selectionKey.attachment();
    socket.process();
    try {
      socket.socketChannel.close();
      logger.info("socket channel " + socket.toString() + " closed");
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
