package com.ut.oop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Time;
import java.util.Random;
import java.util.Scanner;

public class TestTextGenerator {
    public static void generate() {

        Logger logger = LoggerFactory.getLogger(Server.class);

        Scanner sc = new Scanner(System.in);
        while (!sc.equals("q")) {
            Path workingDir = Paths.get(System.getProperty("user.dir"));
            try(FileWriter fw = new FileWriter("dummygen.txt", true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw))
            {
                out.println(new Random().nextDouble());
                Thread.sleep(500);

            } catch (IOException | InterruptedException e) {
                // TODO: 03/05/2022 error handling
                logger.error(e.getMessage() + " from TestTextGenerator");
            }
        }
    }
}
