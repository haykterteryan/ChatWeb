package am.springboot.chat.dto;

import java.util.Date;


public class RequestDto {
    private int requestFromid;

    private int requestToId;

    private String firstName;
    private String lastName;

    public RequestDto(int requestFromid,String firstName, String lastName) {
        this.requestFromid= requestFromid;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    private Date requestDate;

    public int getRequestFromid() {
        return requestFromid;
    }

    public void setRequestFromid(int requestFromid) {
        this.requestFromid = requestFromid;
    }

    public int getRequestToId() {
        return requestToId;
    }

    public void setRequestToId(int requestToId) {
        this.requestToId = requestToId;
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

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }
}
