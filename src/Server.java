import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    List<Client> clients = new ArrayList<>();
    ServerSocket serverSocket;

    public Server() throws IOException {
        // создаем серверный сокет на порту 1234
        serverSocket = new ServerSocket(1234);
    }
public void sedAll(String message) {
        for(Client  client : clients){
            client.receive(message);
        }
}
    public void run() {
        while (true) {
            System.out.println("Waiting...");

            try {
                // ждем клиента из сети
                Socket socket = serverSocket.accept();
                System.out.println(socket.getPort());

                // создаем клиента на своей стороне
                clients.add(new Client(socket, this));
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }

    public static void main(String[] args) throws IOException {
        new Server().run();
    }
}