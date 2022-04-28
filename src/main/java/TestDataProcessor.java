// for testing purposes, decodes byteBuffer into string
// TODO: message parsing, store requests somewhere

public class TestDataProcessor implements DataProcessor {
    @Override
    public void process(MessageData message, Socket socket) {

        String messageAsString = message.toString();
        System.out.println(messageAsString + " from TestDataProcessor");
        socket.dataWriter.write(socket, message.getMessage());

    }
}
