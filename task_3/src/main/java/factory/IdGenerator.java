package factory;

public class IdGenerator<T> {
    private int idCounter;

    public IdGenerator() {
        this.idCounter = 0;
    }

    public String generateId(Class<T> type) {
        String prefix = getPrefix(type);
        String id = prefix + idCounter;
        idCounter++;
        return id;
    }

    private String getPrefix(Class<?> type) {
        if (type == AccessoryDetail.class) {
            return "ACS";
        } else if (type == BodyDetail.class) {
            return "BDY";
        } else if (type == MotorDetail.class) {
            return "MTR";
        } else if (type == Car.class) {
            return "CAR";
        } else {
            throw new IllegalArgumentException("Unsupported type: " + type.getSimpleName());
        }
    }
}