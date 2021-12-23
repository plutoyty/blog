package bean;

public class Message {
    private String send;
    private String receive;
    private String content;
    private String time;

    public Message() {

    }

    public Message(String send, String receive, String content, String time) {
        this.send = send;
        this.receive = receive;
        this.content = content;
        this.time = time;
    }

    public String getSend() {
        return send;
    }

    public void setSend(String send) {
        this.send = send;
    }

    public String getReceive() {
        return receive;
    }

    public void setReceive(String receive) {
        this.receive = receive;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Message{" +
                "send='" + send + '\'' +
                ", receive='" + receive + '\'' +
                ", content='" + content + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
