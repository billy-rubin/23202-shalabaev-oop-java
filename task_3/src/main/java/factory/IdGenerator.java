package factory;

import java.util.HashMap;
import java.util.Map;

public class IdGenerator {
    private static volatile Map<Class<?>, Integer> idCounters = new HashMap<>();

    public static String generateId(Class<?> type) {
        // Получаем текущий счетчик для типа
        int idCounter = idCounters.getOrDefault(type, 0);

        String prefix = getPrefix(type);
        String id = prefix + idCounter;

        idCounters.put(type, idCounter + 1);
        return id;
    }

    private static String getPrefix(Class<?> type) {
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