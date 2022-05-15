package com.ut.oop;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class Main {
    public static void main(String[] args) {
        try {
            Server.start();

            Thread.sleep(1000);
        } catch (IOException e) {
            System.out.println("Failed to start server");
        } catch (InterruptedException e) {
            System.out.println("Sleep interrupted");
        }
    }
}
