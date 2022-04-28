import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.*;

public class SocketProcessor implements Runnable {
    private final Logger logger;

    private final Queue<Socket> socketQueue;
    private final Map<Integer, Socket> socketMap;

    private final Selector readSelector;
    private final Selector writeSelector;

    public SocketProcessor(Queue<Socket> socketQueue) throws IOException {
        this.logger = LoggerFactory.getLogger(Server.class);

        this.socketQueue = socketQueue;
        this.socketMap = new HashMap<>();

        this.readSelector = Selector.open();
        this.writeSelector = Selector.open();
    }

    @Override
    public void run() {
        logger.info("Socket processor now on duty and waiting for clients");

        while (true) {
            try {
                addSockets();
                readSockets();
                writeSockets();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                logger.warn("My short break was interrupted, now I'm grumpy");
            }
        }
    }

    private void addSockets() {
        Socket socket = this.socketQueue.poll();

        while (socket != null) {
            try {
                socket.socketChannel.configureBlocking(false);
                socketMap.put(socket.getId(), socket);

                SelectionKey key = socket.socketChannel.register(this.readSelector, SelectionKey.OP_READ);
                key.attach(socket);

                socket = this.socketQueue.poll();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }

    private void readSockets() throws IOException {
        Set<SelectionKey> selectedKeys = this.readSelector.selectedKeys();

        selectedKeys.forEach(this::readSocket);
        selectedKeys.clear();
    }

    private void readSocket(SelectionKey selectionKey) {
        Socket socket = (Socket) selectionKey.attachment();

        try {
            socket.read();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            logger.error(ioe.getMessage() + " on socketprocessor readSocket()");
        }

        if (socket.isEndOfStream()) {
            int socketId = socket.getId();

            try {
                this.socketMap.remove(socketId);

                selectionKey.attach(null);
                selectionKey.cancel();
                selectionKey.channel().close();
            } catch (IOException e) {
                logger.error("Failed to close socket channel: " + socketId);
            }

            logger.info("End of stream reached: " + socketId);
        }
    }

    private void writeSockets() {
        Set<SelectionKey> selectedKeys = this.writeSelector.selectedKeys();

        selectedKeys.forEach(this::writeSocket);
        selectedKeys.clear();
    }

    private void writeSocket(SelectionKey selectionKey) {
        Socket socket = (Socket) selectionKey.attachment();
        try {
            socket.write();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage() + " from SocketProcessor writeSocket()");
        }

    }
}
