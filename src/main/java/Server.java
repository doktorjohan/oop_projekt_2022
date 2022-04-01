import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;

public class Server implements Runnable {
    private Selector selector;
    private final InetSocketAddress socketAddress;

    public Server(String address, int port) {
        this.socketAddress = new InetSocketAddress(address, port);
    }

    /**
     * Accept connection if acceptable
     *
     * @param key Channel's key
     * @throws IOException Thrown by multiple SocketChannel's methods
     */
    private void acceptConnection(SelectionKey key) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel channel = serverChannel.accept();
        channel.configureBlocking(false);
        Socket socket = channel.socket();
        System.out.println("Connected to: " + socket.getRemoteSocketAddress());

        channel.register(this.selector, SelectionKey.OP_READ);
    }

    /**
     * Read information from stream
     *
     * @param key Channel's key
     * @throws IOException Thrown by multiple SocketChannel's methods
     */
    private void read(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int numRead = channel.read(buffer);

        if (numRead == -1) {
            Socket socket = channel.socket();
            SocketAddress remoteAddr = socket.getRemoteSocketAddress();
            System.out.println("Connection closed by client: " + remoteAddr);
            channel.close();
            key.cancel();
            return;
        }

        byte[] data = new byte[numRead];
        System.arraycopy(buffer.array(), 0, data, 0, numRead);
        System.out.println("Got: " + new String(data));
    }

    /**
     * Start server side thread
     */
    @Override
    public void run() {
        try {
            selector = Selector.open();
            ServerSocketChannel serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false);

            serverChannel.socket().bind(socketAddress);
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("Server up and running");

            // a false-positive intellij warning, we actually want an endless loop
            while (true) {
                selector.select();

                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                while (keys.hasNext()) {
                    SelectionKey key = keys.next();
                    // remove current key to prevent it from coming up again
                    keys.remove();

                    if (!key.isValid())
                        continue;

                    if (key.isAcceptable())
                        acceptConnection(key);
                    else if (key.isReadable())
                        read(key);
                }
            }
        } catch (IOException e) {
            // TODO: error handling
            e.printStackTrace();
        }
    }
}