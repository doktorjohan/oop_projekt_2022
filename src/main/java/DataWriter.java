import java.nio.ByteBuffer;

public interface DataWriter {

    void write(Socket socket, ByteBuffer byteBuffer);

}
