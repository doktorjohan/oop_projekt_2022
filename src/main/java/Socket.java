import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Socket {
    private final SocketChannel socketChannel;
    private boolean endOfStream;

    public Socket(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
        this.endOfStream = false;
    }

    public boolean isEndOfStream() {
        return endOfStream;
    }

    public int read(ByteBuffer byteBuffer) throws IOException {
        int bytesRead = socketChannel.read(byteBuffer);
        int totalBytesRead = bytesRead;

        while (bytesRead > 0) {
            bytesRead = socketChannel.read(byteBuffer);
            totalBytesRead += bytesRead;
        }

        if (bytesRead == -1)
            this.endOfStream = true;

        return totalBytesRead;
    }

    public int write(ByteBuffer byteBuffer) throws IOException {
        int bytesWritten = socketChannel.write(byteBuffer);
        int totalBytesWritten = bytesWritten;

        while (bytesWritten > 0 && byteBuffer.hasRemaining()) {
            bytesWritten = socketChannel.write(byteBuffer);
            totalBytesWritten += bytesWritten;
        }

        return totalBytesWritten;
    }
}
