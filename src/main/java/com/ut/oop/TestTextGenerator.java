package com.ut.oop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class TestTextGenerator {
    public static void generate() {

        Logger logger = LoggerFactory.getLogger(Server.class);

        Path workingDir = Paths.get(System.getProperty("user.dir"));
        while (true) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(workingDir + "src\\dummy.txt", true), true)) {
                pw.println(new Random().nextDouble());
                Thread.sleep(1500);
            } catch (IOException | InterruptedException e) {
                logger.error(e.getMessage() + " from TestTextGenerator");
            }
        }
    }
}
