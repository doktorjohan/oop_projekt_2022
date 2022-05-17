package com.ut.oop;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileClient {

  static String filename = "dummygen.txt";

  public static void main(String[] args) {

    try {

      SocketChannel client = SocketChannel.open(new InetSocketAddress(ServerConfig.PORT));

      String type = "threshold";
      ByteBuffer typeBuffer = ByteBuffer.allocate(100);
      typeBuffer.put(type.getBytes(StandardCharsets.UTF_8));
      typeBuffer.flip();
      client.write(typeBuffer);
      Thread.sleep(1000);

      giveData(client);

      while (true) {
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);

        try {
          client.read(readBuffer);

          String data = new String(readBuffer.array()).trim();
          if (data.length() > 0) {
            System.out.println(data);
          } else {
            break;
          }

        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      client.close();

    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }

  }

  public static void giveData(SocketChannel socketChannel) {

    Path workingDir = Paths.get(System.getProperty("user.dir"));

    try {
      FileInputStream in = new FileInputStream(workingDir + "\\src\\" + filename);
      try (FileChannel channel = in.getChannel()) {
        MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
        while (buffer.hasRemaining()) {
          socketChannel.write(buffer);
        }
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
