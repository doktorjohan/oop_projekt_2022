package com.ut.oop.processor;

import com.ut.oop.MessageData;
import com.ut.oop.Socket;

public interface DataProcessor {

    void process(MessageData message, Socket socket);

}
