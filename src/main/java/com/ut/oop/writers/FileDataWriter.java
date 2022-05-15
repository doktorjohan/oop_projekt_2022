package com.ut.oop.writers;

import com.ut.oop.Server;
import com.ut.oop.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class FileDataWriter implements DataWriter {
    private final Logger logger = LoggerFactory.getLogger(Server.class);

    @Override
    public int write(Socket socket, ByteBuffer byteBuffer){
        int bytesWritten = 0;
        int totalBytesWritten = bytesWritten;
        try {
            bytesWritten = socket.socketChannel.write(byteBuffer);

            while (bytesWritten > 0 && byteBuffer.hasRemaining()) {
                bytesWritten = socket.socketChannel.write(byteBuffer);
                totalBytesWritten += bytesWritten;
            }

            //TODO: determine end of writing

            ByteBuffer endStatement = ByteBuffer.wrap("end".getBytes(StandardCharsets.UTF_8));
            endStatement.flip();
            socket.socketChannel.write(endStatement);

        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Error with writing data back to socket: TestDataWriter");
        }

        return totalBytesWritten;
    }
}
