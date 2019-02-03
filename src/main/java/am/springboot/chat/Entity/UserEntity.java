package am.springboot.chat.Entity;

import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "users")
public class UserEntity  {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(unique = true)
    private String userLogin;

    @Column
    private String userPassword;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column(name = "User_role", insertable=false , updatable = true,
            nullable = false, columnDefinition = "User_role DEFAULT 'ROLE_USER'")
    private String userRole;


    @Column( nullable = false, updatable = false,
            insertable = false, columnDefinition = "register_date DEFAULT CURRENT_TIMESTAMP")
    private Timestamp registerDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userEntity")
    private List<FriendsEntity> friendsEntity = new ArrayList<FriendsEntity>();

    public UserEntity() {
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
