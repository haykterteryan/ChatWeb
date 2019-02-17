package am.springboot.chat.domain;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegisterRequest {


    @NotNull
    @Size(min=8, max=20)
    private String login;

    @NotNull
    @Min(6)
    private String password;

    @NotNull
    @Size(min=2, max=20)
    private String firstName;

    @NotNull
    @Size(min=2, max=20)
    private String lastName;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
