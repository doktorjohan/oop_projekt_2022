package com.ut.oop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.file.Paths;

import java.util.Random;

public class TestTextGenerator {
    public static void generate(String filename) {

        Logger logger = LoggerFactory.getLogger(Server.class);

        File file = new File(Paths.get(System.getProperty("user.dir")) + "\\src\\" + filename);

        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            for (int i = 0; i < 100; i++) {
                pw.println(new Random().nextDouble());
                pw.flush();
            }
        } catch (IOException e) {
            logger.error(e.getMessage() + " from TestTextGenerator");
        }
    }

    public static void main(String[] args) {
        generate("dummygen.txt");
    }
}
