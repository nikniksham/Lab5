package my_programm.obj;

import my_programm.enums.Climate;
import my_programm.enums.StandardOfLiving;
import java.util.Calendar;

public class City {
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long area; //Значение поля должно быть больше 0
    private Long population; //Значение поля должно быть больше 0, Поле не может быть null
    private Integer metersAboveSeaLevel;
    private int carCode; //Значение поля должно быть больше 0, Максимальное значение поля: 1000
    private Climate climate; //Поле может быть null
    private StandardOfLiving standardOfLiving; //Поле может быть null
    private Human governor; //Поле может быть null

    public City(Integer id_p, String name_p, Coordinates coordinates_p, long area_p, Long population_p, int metersAboveSeaLevel_p, int carCode_p, Climate climate_p, StandardOfLiving standardOfLiving_p, Human governor_p) {
//        if (name_p != null) {throw new Error("Имя города не должно быть null");}
//        if (area_p < 1) {throw new Error("Площадь города должна быть больше 0");}
//        if (population_p != null) {throw new Error("Население города не должно быть null");}
//        if (population_p.longValue() < 1) {throw new Error("Население города должна быть больше 0");}
//        if (carCode_p < 1 || carCode_p > 1000) {throw new Error("Код автомобиля должен быть в диапозоне [1; 1000]");}
        this.id = id_p;
        this.name = name_p;
        this.coordinates = coordinates_p;
        this.area = area_p;
        this.population = population_p;
        this.metersAboveSeaLevel = metersAboveSeaLevel_p;
        this.carCode = carCode_p;
        this.climate = climate_p;
        this.standardOfLiving = standardOfLiving_p;
        this.governor = governor_p;
        this.creationDate = Calendar.getInstance().getTime();
    }
}
