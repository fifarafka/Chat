import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.Set;

public class UserThreadWriteMessage implements Runnable {
    private boolean logged;
    private String login;
    private HashMap<String, LinkedBlockingQueue<Message>> usersList;
    private Scanner input;
    private PrintWriter out;

    public UserThreadWriteMessage(String login, HashMap<String, LinkedBlockingQueue<Message>> usersList, Scanner input, PrintWriter out) {
        this.login = login;
        this.usersList = usersList;
        logged = true;
        this.input = input;
        this.out = out;
    }

    public void run() {
        Message message;
        while (logged) {
            try {
                message = usersList.get(login).take();
                if (message.getSender().equals(login))
                    checkContent(message);
                else
                    writeMessage(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeMessage(Message message) {
        out.println(message.getSender() + ": " + message.getContent());
        out.flush();
    }

    private void checkContent(Message message) {
        switch (message.getContent()) {
            case "logout":
                out.println("logout");
                out.flush();
                logged = false;
                break;
            case "list":
                list();
                break;
            case "menu":
                menu();
                break;
        }
    }

    private void list() {
        Set<String> users = usersList.keySet();
        out.println("> Users list: ");
        out.flush();
        for (String user : users) {
            out.println("> " + user);
            out.flush();
        }
    }

    private void menu() {
        out.println("> Available commands: ");
        out.flush();
        out.println("> 'chat' to start isTalk");
        out.flush();
        out.println("> 'end' to end isTalk");
        out.flush();
        out.println("> 'list' to see available users");
        out.flush();
        out.println("> 'menu' to see command list");
        out.flush();
        out.println("> 'logout' to exit");
        out.flush();
    }
}
