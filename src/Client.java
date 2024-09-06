import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {
    Server server;
    Socket socket;
    Scanner in;
    PrintStream out;

    static String color;

    public Client(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;

        // запускаем поток
        new Thread(this).start();
    }

    public void receive(String message) {
        out.println(message);
    }

    public void run() {
        try {
            // получаем потоки ввода и вывода
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            // создаем удобные средства ввода и вывода
            in = new Scanner(is);
            out = new PrintStream(os);

            // читаем из сети и пишем в сеть
            out.println("Welcome to chat " + "User - " + socket.getPort() + "!");
            String input = "Message from - " + socket.getPort() + "\t" + in.nextLine();
            while (!input.equals("bye")) {
                server.sedAll(input);
                input = "Message from - " + socket.getPort() + "\t" + in.nextLine();
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }
}
