import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client implements Runnable {
    private final String address;
    private final int port;

    public Client(String address, int port) {
        this.address = address;
        this.port = port;
    }

    /**
     * Start client side thread
     */
    @Override
    public void run() {
        try {
            InetSocketAddress hostAddress = new InetSocketAddress(address, port);
            SocketChannel client = SocketChannel.open(hostAddress);

            System.out.println("Client is now alive");

            String threadName = Thread.currentThread().getName();

            // Send messages to server
            String [] messages = new String []
                    {threadName + ": test1",threadName + ": test2",threadName + ": test3"};

            for (String s : messages) {
                byte[] message = s.getBytes();
                ByteBuffer buffer = ByteBuffer.wrap(message);
                client.write(buffer);
                System.out.println(s);
                buffer.clear();
                Thread.sleep(5000);
            }
            client.close();
        } catch (IOException | InterruptedException e) {
            // TODO: error handling
            e.printStackTrace();
        }
    }
}
