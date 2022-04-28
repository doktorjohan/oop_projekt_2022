import java.nio.ByteBuffer;

public interface DataProcessor {
    void process(MessageData message, Socket socket);
}
