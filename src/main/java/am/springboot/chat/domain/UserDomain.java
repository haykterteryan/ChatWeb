package am.springboot.chat.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDomain extends User {

    private int userId;


    public int getUserId() {
        return userId;
    }


    public UserDomain(String username, String password, Collection<? extends GrantedAuthority> authorities, int userId) {
        super(username, password, authorities);
        this.userId = userId;
    }


}
