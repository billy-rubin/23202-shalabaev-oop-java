package factory;

public class MotorDetail extends Detail{
    private String ID;
    MotorDetail(String ID){
        super(ID);
    }

    @Override
    public String getID() {
        return ID;
    }
}
