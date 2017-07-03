package socketMultithread;

import java.io.Serializable;

/**
 * Created by emilio on 03/07/17.
 */
public class Message implements Serializable {
    String message;
    String anotherMessage;

    public Message(String message, String anotherMessage) {
        this.message = message;
        this.anotherMessage = anotherMessage;
    }


    public String getMessage() {
        return message;
    }

    public String getAnotherMessage() {
        return anotherMessage;
    }

    public void setMessage(String quitter) {
        this.message = quitter;
    }
}