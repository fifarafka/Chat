import java.io.PrintWriter;
import java.util.Scanner;

public class ClientWrite implements Runnable{
    private PrintWriter out;
    private String login;
    private boolean logged;
    private Scanner console;


    public ClientWrite(PrintWriter out, String login) {
        this.out = out;
        this.login = login;
        this.logged = true;
        this.console = new Scanner(System.in);
    }
    public void run() {
        String command;
        while (logged) {
            command=console.nextLine();
            out.println(command);
            out.flush();
            if (command.equals("chat")) {
                System.out.println("Who do you want to speak with?");
                command=console.nextLine();
                out.println(command);
                out.flush();
            }
            else if (command.equals("logout")) {
                logged = false;
            }
        }
    }
}
