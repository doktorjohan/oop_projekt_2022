import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Socket {
    private final int id;
    public final SocketChannel socketChannel;
    public final DataReader dataReader;
    public final DataWriter dataWriter;
    public final DataProcessor dataProcessor;
    private MessageData message;
    private boolean endOfStream;

    public Socket(int id, SocketChannel socketChannel, DataReader dataReader,
                  DataWriter dataWriter, DataProcessor dataProcessor) {

        this.id = id;
        this.socketChannel = socketChannel;
        this.dataReader = dataReader;
        this.dataWriter = dataWriter;
        this.dataProcessor = dataProcessor;
        this.endOfStream = false;

    }

    public int getId() {
        return id;
    }

    public boolean isEndOfStream() {
        return endOfStream;
    }

    public MessageData getMessage() {
        return message;
    }

    public void setMessage(MessageData message) {
        this.message = message;
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
