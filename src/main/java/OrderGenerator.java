public class OrderGenerator {
    public static Order getDefaultColorBlack(){
        return new Order("Иван", "Иванов", "Открытое шоссе, 12с1", "Бульвар Рокоссовского", "89123456789", 5, "2022-08-20", "Позвонить за 15мин", new String[]{ColorScooter.BLACK});
    }

    public static Order getDefaultColorGrey(){
        return new Order("Иван", "Иванов", "Открытое шоссе, 12с1", "4", "89123456789", 5, "2022-08-20", "Позвонить за 15мин", new String[]{ColorScooter.GREY});
    }

    public static Order getDefaultColorBlackAndGrey(){
        return new Order("Иван", "Иванов", "Открытое шоссе, 12с1", "4", "89123456789", 5, "2022-08-20", "Позвонить за 15мин", new String[]{ColorScooter.BLACK, ColorScooter.GREY});
    }

    public static Order getDefaultColorEmpty(){
        return new Order("Иван", "Иванов", "Открытое шоссе, 12с1", "4", "89123456789", 5, "2022-08-20", "Позвонить за 15мин", new String[]{});
    }
}
