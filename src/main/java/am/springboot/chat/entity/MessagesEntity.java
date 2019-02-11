package am.springboot.chat.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "messages")
public class MessagesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "conversation_id")
    private int conversationId;

    @Column(name = "message_from_id")
    private int messageFromId;

    @Column(name = "message_to_id")
    private int messageToId;

    @Column(name = "message", columnDefinition = "text")
    private String message;

    @Column(name = "readed")
    boolean readed;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="send_date", nullable = false,
            columnDefinition="TIMESTAMP default CURRENT_TIMESTAMP")
    private Date registerDate = new Date();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name="message_from_id",insertable = false, updatable = false)
    private UserEntity usersToEntity;


    public MessagesEntity() {

    }

    public UserEntity getUsersToEntity() {
        return usersToEntity;
    }

    public void setUsersToEntity(UserEntity usersToEntity) {
        this.usersToEntity = usersToEntity;
    }

    public int getConversationId() {
        return conversationId;
    }

    public void setConversationId(int conversationId) {
        this.conversationId = conversationId;
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

    public boolean isReaded() {
        return readed;
    }

    public void setReaded(boolean readed) {
        this.readed = readed;
    }

    //    public UserEntity getUsersMessageFromEntity() {
//        return usersMessageFromEntity;
//    }
//
//    public void setUsersMessageFromEntity(UserEntity usersMessageFromEntity) {
//        this.usersMessageFromEntity = usersMessageFromEntity;
//    }



}
