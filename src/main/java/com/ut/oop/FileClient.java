package com.ut.oop;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class FileClient {

    SocketChannel socketChannel;
    String filename;

    public FileClient(SocketChannel socketChannel, String filename) {
        this.socketChannel = socketChannel;
        this.filename = filename;
    }

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
