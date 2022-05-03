package com.ut.oop;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Time;
import java.util.Random;
import java.util.Scanner;

public class TestTextGenerator {
    public static void generate() {
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
            }
        }
    }
}
