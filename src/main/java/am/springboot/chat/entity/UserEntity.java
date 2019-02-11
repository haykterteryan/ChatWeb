package am.springboot.chat.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "users")
public class UserEntity  {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="user_id")
    private int userId;

    @Column(unique = true)
    private String userLogin;

    @Column
    private String userPassword;

    @Column(name ="first_name ")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "User_role", insertable=false , updatable = true,
            nullable = false, columnDefinition = "User_role DEFAULT 'ROLE_USER'")
    private String userRole;


    @Column( nullable = false, updatable = false,
            insertable = false, columnDefinition = "register_date DEFAULT CURRENT_TIMESTAMP")
    private Timestamp registerDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userEntity", fetch = FetchType.EAGER)
    private List<FriendsEntity> friendsEntity = new ArrayList<FriendsEntity>();


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fromRequestEntity")
    private List<FriendRequestEntity> friendRequestEntities = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "usersToEntity")
    private List<MessagesEntity> usersMessageToEntity = new ArrayList<>();

    public UserEntity() {
    }

    public List<MessagesEntity> getUsersMessageToEntity() {
        return usersMessageToEntity;
    }

    public void setUsersMessageToEntity(List<MessagesEntity> usersMessageToEntity) {
        this.usersMessageToEntity = usersMessageToEntity;
    }

    public List<FriendsEntity> getFriendsEntity() {
        return friendsEntity;
    }

    public void setFriendsEntity(List<FriendsEntity> friendsEntity) {
        this.friendsEntity = friendsEntity;
    }

    public List<FriendRequestEntity> getFriendRequestEntities() {
        return friendRequestEntities;
    }

    public void setFriendRequestEntities(List<FriendRequestEntity> friendRequestEntities) {
        this.friendRequestEntities = friendRequestEntities;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public Timestamp getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Timestamp registerDate) {
        this.registerDate = registerDate;
    }

}
