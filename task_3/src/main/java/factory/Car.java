package factory;

public class Car extends Detail{
    private BodyDetail body;
    private AccessoryDetail accessory;
    private MotorDetail motor;
    public Car(String ID, BodyDetail body, MotorDetail motor, AccessoryDetail accessory){
        super(ID);
        this.accessory = accessory;
        this.body = body;
        this.motor = motor;
    }
}
