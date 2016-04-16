import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

public class UserThreadPushMessage implements Runnable{
    private Socket socket;
    private Scanner input;
    private PrintWriter out;
    private String login;
    private HashMap<String,LinkedBlockingQueue<Message>> usersList;
    private boolean logged;

    UserThreadPushMessage(Socket socket, HashMap<String,LinkedBlockingQueue<Message>> usersList) throws IOException {
        this.socket = socket;
        this.usersList = usersList;
        this.logged = false;
        this.input = new Scanner(socket.getInputStream());
        this.out = new PrintWriter(socket.getOutputStream());
    }

    public void run() {
        logIn();
        Thread pushMessage = new Thread(new UserThreadWriteMessage(login,usersList, input, out));
        pushMessage.start();
        while (logged)
            pushMessage();
        System.out.println(login+" logged out");
    }

    private void logIn() {
        login = input.nextLine();
        System.out.println("Logged in: "+login);
        if (!usersList.containsKey(login))
            usersList.put(login, new LinkedBlockingQueue<Message>());
        logged = true;
    }

    private void pushMessage() {
        String command = input.nextLine();
        System.out.println(login+": called command - "+command);
        switch (command) {
            case "chat":
                startChat();
                break;
            case "logout":
                pushCommand(command);
                logged=false;
                break;
            case "list":
                pushCommand(command);
                break;
            case "menu":
                pushCommand(command);
                break;
            default:
                pushCommand("Bad command");
        }
    }

    private void pushCommand(String command) {
        usersList.get(login).add(new Message(login, command));
    }


    private void startChat() {
        String recipient = input.nextLine();
        if (usersList.get(recipient)!=null)
            talk(recipient);
        else
        {
            System.out.println(login+": want to tak with unavailable user");
            usersList.get(login).add(new Message(login, "This user is unavailable."));
        }

    }

    private void talk(String recipient) {
        boolean talk = true;
        Message m = new Message();
        m.setSender(login);
        System.out.println(login+": start to chat with - "+recipient);
        while (talk) {
            m.setContent(input.nextLine());
            usersList.get(recipient).add(m);
            if (m.getContent().equals("end")) {
                System.out.println(login+": ended chat with - "+recipient);
                talk=false;
            }
        }
    }


}
