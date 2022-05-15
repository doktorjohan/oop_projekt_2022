package com.ut.oop;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class FileClient {

    SocketChannel socketChannel;
    String filename;

    public FileClient(SocketChannel socketChannel, String filename) {
        this.socketChannel = socketChannel;
        this.filename = filename;
    }

    public static void main(String[] args) {

        try {
            SocketChannel client = SocketChannel.open(new InetSocketAddress(ServerConfig.PORT));

            ByteBuffer buffer = ByteBuffer.allocate(64);
            buffer.put("echo test server pls respond".getBytes(StandardCharsets.UTF_8));
            buffer.flip();
            int bytesWritten = client.write(buffer);
            System.out.println("Sending message: " + new String(buffer.array()).trim() + " bytes: " + bytesWritten);

            ByteBuffer readBuffer = ByteBuffer.allocate(64);

            while (true) {

                try {
                    client.read(readBuffer);

                    String data = new String(readBuffer.array()).trim();

                    if (data.equals("end"))
                        break;
                    System.out.println(data);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            client.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // saab kasutada failist lugemiseks veel
    public void giveData() {

        try {
            FileInputStream in = new FileInputStream(filename);
            try (FileChannel channel = in.getChannel()) {
                MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
                while (buffer.hasRemaining()) {
                    System.out.println("jes");
                    socketChannel.write(buffer);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
