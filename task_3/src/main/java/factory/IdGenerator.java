package factory;

import java.util.HashMap;
import java.util.Map;

public class IdGenerator {
    // Мапа для хранения счетчиков для каждого типа деталей
    private static volatile Map<Class<?>, Integer> idCounters = new HashMap<>();

    /**
     * Генерирует уникальный ID для объекта.
     *
     * @param type Класс объекта.
     * @return Уникальный ID в формате строки.
     */
    public static String generateId(Class<?> type) {
        // Получаем текущий счетчик для типа
        int idCounter = idCounters.getOrDefault(type, 0);

        // Формируем ID
        String prefix = getPrefix(type);
        String id = prefix + idCounter;

        // Увеличиваем счетчик для типа и сохраняем его в мапе
        idCounters.put(type, idCounter + 1);

        return id;
    }

    /**
     * Возвращает префикс для типа объекта.
     *
     * @param type Класс объекта.
     * @return Префикс для ID.
     */
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