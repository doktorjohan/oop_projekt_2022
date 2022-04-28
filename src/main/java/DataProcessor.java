import java.nio.ByteBuffer;

public interface DataProcessor {
    void process(ByteBuffer byteBuffer, Socket socket);
}
