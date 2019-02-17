package am.springboot.chat.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDomain extends User {

    private int userId;

    private String firstName;

    private  String lastName;


    public UserDomain(String username, String password, Collection<? extends GrantedAuthority> authorities, int userId, String firstName, String lastName) {
        super(username, password, authorities);
        this.userId = userId;
        this.firstName=  firstName;
        this.lastName= lastName;
    }

    public int getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }


}
