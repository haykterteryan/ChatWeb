package am.springboot.chat.dto;

public class UserDto {

    private int userId;
    private String firstName;
    private String lastName;

//    public UserDto(String firstName, String lastName) {
//
//        this.firstName = firstName;
//        this.lastName = lastName;
//    }

    public UserDto(int userId, String firstName, String lastName) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
