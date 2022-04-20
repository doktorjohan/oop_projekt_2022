import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class Server {
    public static void start() {
        Queue<Socket> socketQueue = new ArrayBlockingQueue<>(ServerConfig.QUEUE_CAPACITY);

        SocketAccepter socketAccepter = new SocketAccepter(socketQueue);
        SocketProcessor socketProcessor = new SocketProcessor(socketQueue);

        new Thread(socketAccepter).start();
        new Thread(socketProcessor).start();
    }
}
