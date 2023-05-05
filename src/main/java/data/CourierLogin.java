package data;

public class CourierLogin {
    public String login;
    public String password;


    public CourierLogin(String login, String password){
        this.login = login;
        this.password = password;

    }

    public static CourierLogin from(CourierCreate courier) {
        return new CourierLogin(courier.getLogin(), courier.getPassword());
    }

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
}

