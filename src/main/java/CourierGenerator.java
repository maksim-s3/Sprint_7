public class CourierGenerator {
    public static Courier getDefault(){
        return new Courier("LoginCourier733", "98635", "Courier733");
    }

    public static Courier getIsInvalidLogin(){
        return new Courier(null,"98635", "Courier733");
    }

    public static Courier getIsInvalidPass(){
        return new Courier("LoginCourier733",null, "Courier733");
    }

    public static Courier getDoubleLogin(){
        return new Courier("LoginCourier733", "123456", "Courier528");
    }
}
