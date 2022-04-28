import java.nio.ByteBuffer;

public interface DataWriter {
    int write(Socket socket, ByteBuffer byteBuffer);
}
