package factory;

public class Car extends Detail{
    private BodyDetail body;
    private AccessoryDetail accessory;
    private MotorDetail motor;
    private String ID;
    Car(String ID, BodyDetail body, MotorDetail motor, AccessoryDetail accessory){
        super(ID);
        this.ID = ID;
        this.accessory = accessory;
        this.body = body;
        this.motor = motor;
    }

    @Override
    public String getID() {
        return ID;
    }

    public BodyDetail getBody(){
        return body;
    }

    public AccessoryDetail getAccessory(){
        return accessory;
    }

    public MotorDetail getMotor(){
        return motor;
    }
}
