import java.io.IOException;
import java.nio.ByteBuffer;

public class TestDataReader implements DataReader {
    @Override
    public void read(Socket socket) {
        try {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 1024);
            int bytesRead = socket.read(byteBuffer);

            socket.setMessage(new MessageData(byteBuffer, bytesRead));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
