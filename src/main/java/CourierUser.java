//package ru.praktikum.courier;

        //import io.restassured.RestAssured;
   //     import io.restassured.response.ValidatableResponse;
   //     import ru.praktikum.data.Courier;
  //      import ru.praktikum.data.CourierCreds;

     //   import static io.restassured.RestAssured.given;

public class CourierUser {
    private String login;
    private String password;
   // private String firstName;

    public CourierUser(String login, String password) {
        this.login = login;
        this.password = password;
      //  this.firstName = firstName;
    }

    public CourierUser() {
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

 //   public String getFirstName() {
 //       return firstName;
 //   }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

 //   public void setFirstName(String firstName) {
 //       this.firstName = firstName;
  //  }

}