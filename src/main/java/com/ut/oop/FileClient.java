package com.ut.oop;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class FileClient {

    static String filename = "dummygen.txt";

    public static void main(String[] args) {

        try {
            SocketChannel client = SocketChannel.open(new InetSocketAddress(ServerConfig.PORT));


            giveData(client);

            /*
            ByteBuffer buffer = ByteBuffer.allocate(64);
            buffer.put("echo test server pls respond".getBytes(StandardCharsets.UTF_8));
            buffer.flip();
            int bytesWritten = client.write(buffer);
            //System.out.println("Sending message: " + new String(buffer.array()).trim() + " bytes: " + bytesWritten);
*/

            while (true) {
                ByteBuffer readBuffer = ByteBuffer.allocate(64);

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

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void giveData(SocketChannel socketChannel) {

        try {
            FileInputStream in = new FileInputStream(filename);
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
