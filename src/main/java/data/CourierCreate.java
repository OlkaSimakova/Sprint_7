package data;

public class CourierCreate {
    public String login;
    public String password;
    public String firstName;

    public CourierCreate(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }
}