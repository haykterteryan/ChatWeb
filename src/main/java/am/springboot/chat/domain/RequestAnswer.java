package am.springboot.chat.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestAnswer {

    @JsonProperty
    private  int userId;
    private  int isAccept;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getIsAccept() {
        return isAccept;
    }

    public void setIsAccept(int isAccept) {
        this.isAccept = isAccept;
    }
}
