import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            Server.start();
        } catch (IOException e) {
            System.out.println("Failed to start server");
        }
    }
}
