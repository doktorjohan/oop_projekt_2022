import java.util.Queue;

public class SocketProcessor implements Runnable {
    private final Queue<Socket> socketQueue;

    public SocketProcessor(Queue<Socket> socketQueue) {
        this.socketQueue = socketQueue;
    }

    @Override
    public void run() {
        while (true) {
            // TODO: process sockets
        }
    }
}
