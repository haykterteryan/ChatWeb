package am.springboot.chat.entity;


import javax.persistence.*;

@Entity
@Table(name= "friends")
public class FriendsEntity {

    @Id
    @Column(name = "friends_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int friendsId;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "friend_id", insertable = false,updatable = false)
    private int friendId;

    @Column(name = "block_status")
    private boolean blockStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn( name="friend_id")
    private UserEntity userEntity;

    public FriendsEntity() {
    }


    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public int getFriendsId() {
        return friendsId;
    }

    public void setFriendsId(int friendsId) {
        this.friendsId = friendsId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    public boolean isBlockStatus() {
        return blockStatus;
    }

    public void setBlockStatus(boolean blockStatus) {
        this.blockStatus = blockStatus;
    }
}
