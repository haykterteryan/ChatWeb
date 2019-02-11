package am.springboot.chat.domain;

import com.fasterxml.jackson.annotation.JsonProperty;


public class SocketMessage {

    @JsonProperty()
    private String message;

    private int receiverId;

    private int senderId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }
}




