public class Message {
    private String sender;
    private String content;

    public Message() {}

    public Message(String sender, String content) {
        this.sender = sender;
        this.content = content;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setContent(String content){
        this.content = content;
    }

    public String getSender() {
        return this.sender;
    }

    public String getContent() {
        return this.content;
    }
}
