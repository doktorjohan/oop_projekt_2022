public class Main {
    static final String ADDRESS = "localhost";
    static final int PORT = 1337;

    public static void main(String[] args) throws InterruptedException {
        Runnable server = new Server(ADDRESS, PORT);
        Runnable testClient = new Client(ADDRESS, PORT);

        new Thread(server).start();
        Thread.sleep(1000);
        new Thread(testClient).start();
    }
}
