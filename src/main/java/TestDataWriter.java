import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.nio.ByteBuffer;

public class TestDataWriter implements DataWriter{

    private final Logger logger = LoggerFactory.getLogger(Server.class);

    @Override
    public void write(Socket socket, ByteBuffer byteBuffer) {
        try {
            socket.write(byteBuffer);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Error with writing data to socket");
        }
    }
}
