package am.springboot.chat.DTO;

import java.util.Date;

public class RequestDto {
    private int requestFromid;

    private int requestToId;

    private String firstName;
    private String lastName;

    public RequestDto(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    private Date requestDate;
}
