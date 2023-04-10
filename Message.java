package pj4;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// following class implements Serializabe and Comparable<Message> so that the messages can be compared
public class Message implements Serializable, Comparable<Message> {
    private boolean read;
    private User sender;
    private User receiver;
    private User messageOwner;
    private String message;
    private String createDate;
    private boolean disappearing;

    private static final long serialVersionUID = 1L;

    // following constructor passes all the previously stated fields along with a User message owner
    public Message(User messageOwner, User sender, User receiver, String message, boolean disappearing, String createDate) {
        this.messageOwner = messageOwner;
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.disappearing = disappearing;
        this.read = false;
        this.createDate = createDate;
    }

    // following method checks if an object is equal to this and returns true. If not and o is null, it returns false.
    // if true, a new message object is created that checks if sender equals the message sender, the receiver equals the message receiver
    //, the owner equals  the message owner, and if the timestamp equals the message timestamp. if all of this is true, the message is returned
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

    // following method functions the same as the previous class except without the message owner section
    public boolean related(Object o) {
        if (this == o) return false;
        if (o == null || getClass() != o.getClass()) return false;

        Message message1 = (Message) o;

        if (!getSender().equals(message1.getSender())) return false;
        if (!getReceiver().equals(message1.getReceiver())) return false;
        if (!getCreateDate().equals(message1.getCreateDate())) return false;
        return getMessage().equals(message1.getMessage());
    }


    // Override the compareTo method, following method returns different values is the message read is equal or not equal to
    // what is read
    public int compareTo(Message message)
    {
        if (isRead() == message.isRead())
            return 0;
        if (isRead() && !message.isRead())
            return 1;
        else
            return -1;
    }

    // checks if the message is read and returns whether or not it was
    public boolean isRead() {
        return read;
    }

    // sets the read field
    public void setRead(boolean read) {
        this.read = read;
    }

    // gets the sender field
    public User getSender() {
        return sender;
    }

    // gets the receiver field
    public User getReceiver() {
        return receiver;
    }

    // gets the Message Owner field
    public User getMessageOwner() {
        return messageOwner;
    }

    // sets the Message field
    public void setMessage(String message) {
        this.message = message;
    }

    // gets the message field
    public String getMessage() {
        return message;
    }

    //gets the timestamp of when the message was created
    public String getCreateDate() {
        return this.createDate;
    }

    // possible method for disappearing messages
    public boolean isDisappearing() {
        return disappearing;
    }

}
