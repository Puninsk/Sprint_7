package project;

public class CourierCreds {

    private String login;
    private String password;

    private String firstName;

    public CourierCreds(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public static CourierCreds credsFrom(Courier courier) {
        return new CourierCreds(courier.getLogin(), courier.getPassword(), courier.getFirstName());
    }

}
