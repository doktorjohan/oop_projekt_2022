import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Queue;

public class SocketAccepter implements Runnable {
    private final Logger logger;

    private final Queue<Socket> socketQueue;
    private int nextSocketId;

    public SocketAccepter(Queue<Socket> socketQueue) {
        this.logger = LoggerFactory.getLogger(Server.class);

        this.socketQueue = socketQueue;
        this.nextSocketId = 1;
    }

    @Override
    public void run() {
        ServerSocketChannel serverSocketChannel;

        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(ServerConfig.PORT));

            logger.info("Server socket channel opened on port " + ServerConfig.PORT);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return;
        }

        while (true) {
            try {
                SocketChannel socketChannel = serverSocketChannel.accept();
                // TODO: factory to determine socket type (currently using test)
                this.socketQueue.add(new Socket(nextSocketId++, socketChannel, new TestDataReader(), new TestDataWriter(), new TestDataProcessor()));

                logger.info("Socket accepted: " + (nextSocketId - 1));
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }
}
