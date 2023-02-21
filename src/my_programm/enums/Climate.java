package my_programm.enums;

public enum Climate {
    TROPICAL_SAVANNA,
    HUMIDSUBTROPICAL,
    STEPPE,
    SUBARCTIC,
    DESERT;

    public static Climate getById(int id) {
        switch (id) {
            case 1: return TROPICAL_SAVANNA;
            case 2: return HUMIDSUBTROPICAL;
            case 3: return STEPPE;
            case 4: return SUBARCTIC;
            case 5: return DESERT;
            default: throw new RuntimeException("Тип климата может быть от 1 до 5");
        }
    }
}