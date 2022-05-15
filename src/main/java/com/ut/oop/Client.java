package com.ut.oop;

import java.io.File;

public class Client {

  public static void main(String[] args) throws InterruptedException {


    while (true) {
      TestTextGenerator.main(new String[0]);
      Thread.sleep(500);
      FileClient.main(new String[0]);


    }

  }

}
