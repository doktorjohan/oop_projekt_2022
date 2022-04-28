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

    ByteBuffer readBytes;
    ByteBuffer writeBytes;

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

    public void setEndOfStream(boolean endOfStream) {
        this.endOfStream = endOfStream;
    }

    public MessageData getMessage() {
        return message;
    }

    public void setMessage(MessageData message) {
        this.message = message;
    }

    public void read() throws IOException {
        int totalBytesRead = this.dataReader.read(this, readBytes);
    }

    public void write() throws IOException {
        this.dataWriter.write(this, readBytes);
    }
}
