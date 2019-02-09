package am.springboot.chat.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FriendRequest {

    @JsonProperty
    private int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
