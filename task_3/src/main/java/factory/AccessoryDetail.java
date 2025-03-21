package factory;

public class AccessoryDetail extends Detail{
    private String ID;
    AccessoryDetail(String  ID){
        super(ID);
    }
    @Override
    public String getID() {
        return ID;
    }
}
