package am.springboot.chat.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BlockFriend {
    @JsonProperty
    private int userId;
    private int block;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
    }
}
