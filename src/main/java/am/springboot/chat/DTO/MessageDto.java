package am.springboot.chat.DTO;

import java.util.Date;

public class MessageDto {

    private int messageFromId;

    private int messageToId;

    private String message;

    private Date registerDate = new Date();

    public MessageDto(int messageFromId, int messageToId, String message, Date registerDate) {
        this.messageFromId = messageFromId;
        this.messageToId = messageToId;
        this.message = message;
        this.registerDate = registerDate;
    }

    public int getMessageFromId() {
        return messageFromId;
    }

    public void setMessageFromId(int messageFromId) {
        this.messageFromId = messageFromId;
    }

    public int getMessageToId() {
        return messageToId;
    }

    public void setMessageToId(int messageToId) {
        this.messageToId = messageToId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }
}
