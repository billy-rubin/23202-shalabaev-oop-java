package factory;

public class AccessoryDetail extends Detail{
    private static int idCounter = 0;
    AccessoryDetail(String  ID) {
        super(ID);
        idCounter++;
    }
}
