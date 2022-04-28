import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.nio.ByteBuffer;

public class TestDataWriter implements DataWriter{

    private final Logger logger = LoggerFactory.getLogger(Server.class);

    @Override
    public void write(Socket socket, ByteBuffer byteBuffer) {
        try {
            int bytesread = socket.read(byteBuffer);
            System.out.println("Socket got bytes(if you have coin): " + bytesread);
        } catch (IOException e) {
            logger.error(e.getMessage() + " testdatawriter");
            e.printStackTrace();
        }
    }
}
