import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {

    private String adress;
    private int port;
    SocketChannel socketChannel;

    public Client(String adress, int port) {
        this.adress = adress;
        this.port = port;
    }

    public void inputString() {
        String msg;
        try (Scanner scanner = new Scanner(System.in)){
            socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress(adress, port));
            while (true) {
                System.out.println("Введите строку:");
                msg = scanner.nextLine();
                if (!msg.isEmpty() && !msg.equals("end")) {
                    socketChannel.write(ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8)));
                } else if (msg.equals("end")) {
                    break;
                } else {
                    continue;
                }
                readMsg();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readMsg() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(2 << 10);
        int count = socketChannel.read(buffer);
        if (count == -1) {
            System.out.println("Что-то пошло не так");
        } else {
            System.out.println(new String(buffer.array(), 0, count, StandardCharsets.UTF_8));
        }
        buffer.clear();
    }
}
