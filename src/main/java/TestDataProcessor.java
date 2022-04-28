import java.nio.ByteBuffer;

// for testing purposes, decodes byteBuffer into string
// TODO: message parsing, store requests somewhere

public class TestDataProcessor implements DataProcessor {
    @Override
    public void process(ByteBuffer byteBuffer, Socket socket) {
        byte[] readBytes = new byte[1024 * 1024];
        socket.message = new String(readBytes);
    }
}
