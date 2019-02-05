package am.springboot.chat.Entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "friend_request")
public class FriendRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friend_request_id")
    private int friendRequestId;

    @Column(name = "request_from_id")
    private int requestFromId;

    @Column(name = "request_to_id")
    private int requestToId;

    @Column(name = "readed")
    private boolean readed;

    @Column(name = "aproved", nullable = false)
    private boolean aprooved;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="friendship_date", nullable = false,
            columnDefinition="TIMESTAMP default CURRENT_TIMESTAMP")
    private Date frinedshipDate = new Date();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_from_id",insertable = false ,updatable = false)
    private UserEntity fromRequestEntity;

    public FriendRequestEntity() {

    }

    public UserEntity getFromRequestEntity() {
        return fromRequestEntity;
    }

    public void setFromRequestEntity(UserEntity fromRequestEntity) {
        this.fromRequestEntity = fromRequestEntity;
    }

    public boolean isReaded() {
        return readed;
    }

    public void setReaded(boolean readed) {
        this.readed = readed;
    }

    public int getFriendRequestId() {
        return friendRequestId;
    }

    public void setFriendRequestId(int friendRequestId) {
        this.friendRequestId = friendRequestId;
    }

    public int getRequestFromId() {
        return requestFromId;
    }

    public void setRequestFromId(int requestFromId) {
        this.requestFromId = requestFromId;
    }

    public int getRequestToId() {
        return requestToId;
    }

    public void setRequestToId(int requestToId) {
        this.requestToId = requestToId;
    }

    public boolean isAprooved() {
        return aprooved;
    }

    public void setAprooved(boolean aprooved) {
        this.aprooved = aprooved;
    }

    public Date getFrinedshipDate() {
        return frinedshipDate;
    }

    public void setFrinedshipDate(Date frinedshipDate) {
        this.frinedshipDate = frinedshipDate;
    }



}
