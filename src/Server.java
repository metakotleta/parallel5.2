import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;


public class Server {
    private String adress;
    private int port;
    private ServerSocketChannel serverSocketChannel;

    public Server(String adress, int port) {
        this.adress = adress;
        this.port = port;
    }

    public void spaceDestroy() {
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(adress, port));
            SocketChannel serverSocket= serverSocketChannel.accept();
            ByteBuffer buffer = ByteBuffer.allocate(2 << 10);
            int count;
            String msg;
            while (true) {
                StringBuilder sb = new StringBuilder();
                count = serverSocket.read(buffer);
                new String(buffer.array(), 0, count, StandardCharsets.UTF_8).chars().
                        filter(c -> (c != 32)).
                        boxed().
                        map(c -> Character.toChars(c)).
                        forEach(sb::append);
               serverSocket.write(ByteBuffer.wrap(sb.toString().getBytes(StandardCharsets.UTF_8)));
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
