public class CourierCredentials {
    private String login;
    private String password;

    public CourierCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static CourierCredentials from(Courier courier){
        return new CourierCredentials(courier.getLogin(), courier.getPassword());
    }

    public static CourierCredentials emptyLoginFrom(Courier courier){
        return new CourierCredentials(null, courier.getPassword());
    }

    public static CourierCredentials emptyPassFrom(Courier courier){
        return new CourierCredentials(courier.getLogin(), null);
    }

    public static CourierCredentials inValidLoginFrom(Courier courier){
        return new CourierCredentials("qwerty"+courier.getLogin(), courier.getPassword());
    }

    public static CourierCredentials inValidPassFrom(Courier courier){
        return new CourierCredentials(courier.getLogin(), "qwerty"+courier.getPassword());
    }
}
