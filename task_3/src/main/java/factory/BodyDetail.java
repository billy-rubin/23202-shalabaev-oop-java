package factory;

public class BodyDetail extends Detail {
    private static int idCounter = 0;
    BodyDetail(String ID) {
        super(ID);
        idCounter++;
    }
}
