public class Main {

    public static void main(String[] args) {

        Server server = new Server("localhost", 20999);
        Client client = new Client("localhost", 20999);

        new Thread(null, server::spaceDestroy, "server").start();
        new Thread(null, client::inputString, "client").start();
    }
}
