package pj4;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Message implements Serializable, Comparable<Message> {
    private boolean read;
    private User sender;
    private User receiver;
    private User messageOwner;
    private String message;
    private String createDate;
    private boolean disappearing;

    private static final long serialVersionUID = 1L;

    public Message(User messageOwner, User sender, User receiver, String message, boolean disappearing, String createDate) {
        this.messageOwner = messageOwner;
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.disappearing = disappearing;
        this.read = false;
        this.createDate = createDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message1 = (Message) o;

        if (!getSender().equals(message1.getSender())) return false;
        if (!getReceiver().equals(message1.getReceiver())) return false;
        if (!getMessageOwner().equals(message1.getMessageOwner())) return false;
        if (!getCreateDate().equals(message1.getCreateDate())) return false;
        return getMessage().equals(message1.getMessage());
    }

    public boolean related(Object o) {
        if (this == o) return false;
        if (o == null || getClass() != o.getClass()) return false;

        Message message1 = (Message) o;

        if (!getSender().equals(message1.getSender())) return false;
        if (!getReceiver().equals(message1.getReceiver())) return false;
        if (!getCreateDate().equals(message1.getCreateDate())) return false;
        return getMessage().equals(message1.getMessage());
    }

    // Override the compareTo method
    public int compareTo(Message message)
    {
        if (isRead() == message.isRead())
            return 0;
        if (isRead() && !message.isRead())
            return 1;
        else
            return -1;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public User getSender() {
        return sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public User getMessageOwner() {
        return messageOwner;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getCreateDate() {
        return this.createDate;
    }

    public boolean isDisappearing() {
        return disappearing;
    }

}
