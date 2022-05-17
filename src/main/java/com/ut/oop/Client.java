package com.ut.oop;


public class Client {

  public static void main(String[] args) throws InterruptedException {

    while (true) {
      TestTextGenerator.main(new String[0]);
      FileClient.main(new String[0]);
      System.gc();
      Thread.sleep(10000);
    }


  }

}
