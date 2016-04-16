import java.util.Scanner;
public class ClientRead implements Runnable{
    private Scanner input;
    private boolean logged;
    private String login;

    public ClientRead(Scanner input, String login) {
        this.input = input;
        this.logged = true;
        this.login = login;
    }
    public void run() {
        String line;
        while (logged) {
            line = input.nextLine();
            System.out.println(line);
            if (line.equals(login+": logout")||line.equals(("logout")))
                logged=false;
        }
    }
}
