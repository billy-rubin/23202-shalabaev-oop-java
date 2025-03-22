package factory;

public class MotorDetail extends Detail{
    private static int idCounter = 0;
    MotorDetail(String ID){
        super(ID);
        idCounter++;
    }
}
