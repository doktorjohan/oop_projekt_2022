import java.nio.ByteBuffer;

public interface DataReader {

    int read(Socket socket, ByteBuffer byteBuffer);

}
