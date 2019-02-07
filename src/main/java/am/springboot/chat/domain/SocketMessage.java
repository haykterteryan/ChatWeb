package am.springboot.chat.domain;

import com.fasterxml.jackson.annotation.JsonProperty;


public class SocketMessage {

    @JsonProperty()
    private String message;
    private int personId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }
}




