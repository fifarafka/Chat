import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;


public class Server {
    private Socket socket;
    private ServerSocket server;
    private HashMap<String,LinkedBlockingQueue<Message>> usersList;


    public Server(int port) {
        usersList = new HashMap<String, LinkedBlockingQueue<Message>>();
        try {
            server = new ServerSocket(port);
            System.out.println("Server is running");
        }
        catch (IOException e) {
            System.out.println("IOException");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void start() {
        while (true) {
            try {
                socket = server.accept();
                System.out.println("New connection");
                Thread chat = new Thread(new UserThreadPushMessage(socket, usersList));
                chat.start();
            }
            catch (IOException e) {
                System.out.println("IOException");
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Server server=new Server(6000);
        server.start();
    }


}
