import com.github.javafaker.Faker;

public class CourierGenerator {
    private static Faker faker = new Faker();
    private static String login = faker.name().firstName()+faker.number().numberBetween(100, 999);
    private static String password = faker.number().digits(10);
    private static String firstName = faker.name().firstName();

    public static Courier getDefault(){
        return new Courier(login, password, firstName);
    }

    public static Courier getIsInvalidLogin(){
        return new Courier(null,password, firstName);
    }

    public static Courier getIsInvalidPass(){
        return new Courier(login,null, firstName);
    }

    public static Courier getDoubleLogin(){
        return new Courier(login, faker.number().digits(10), faker.name().firstName());
    }
}
