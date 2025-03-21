package factory;

public class BodyDetail extends Detail {
    private String ID;
    BodyDetail(String ID){
        super(ID);
    }
    @Override
    public String getID() {
        return ID;
    }
}
