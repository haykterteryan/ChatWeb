package am.springboot.chat.Entity;


import javax.persistence.*;

@Entity
@Table(name= "friends")
public class FriendsEntity {

    @Id
    @Column(name = "friends_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int friendsId;

    @Column(name = "user_id", insertable = false, updatable = false)
    private int userId;

    @Column(name = "friend_id")
    private int friendId;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="user_id")
    private UserEntity userEntity;


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
}
