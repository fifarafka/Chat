import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private String login;
    private Scanner console;
    private Scanner input;
    private PrintWriter out;
    private boolean talk;

    public Client(int port) {
        try {
            this.socket = new Socket("0.0.0.0", port);
            System.out.println("Successfully connected to a server");
            this.input = new Scanner(socket.getInputStream());
            this.out = new PrintWriter(socket.getOutputStream());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        talk = false;
        this.console = new Scanner(System.in);
    }

    public void start() {
        logIn();
        Thread read = new Thread(new ClientRead(input, login));
        read.start();
        Thread write = new Thread(new ClientWrite(out, login));
        write.start();
        menu();
    }

    private void logIn() {
        System.out.println("Give your login: ");
        login = console.nextLine();
        out.println(login);
        out.flush();
    }

    private void menu() {
        System.out.println("Available commands: ");
        System.out.println("*) 'chat' to start isTalk");
        System.out.println("*) 'end' to end isTalk");
        System.out.println("*) 'list' to see available users");
        System.out.println("*) 'menu' to see command list");
        System.out.println("*) 'logout' to exit");

    }

    public String getLogin() {
        return this.login;
    }

    public boolean isTalk() {
        return this.talk;
    }

    public static void main(String[] args) {
        Client c=new Client(6000);
        c.start();
    }

}
