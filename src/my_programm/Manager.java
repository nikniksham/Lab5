package my_programm;

import my_programm.enums.Climate;
import my_programm.enums.StandardOfLiving;
import my_programm.obj.City;
import my_programm.obj.Coordinates;
import my_programm.obj.Human;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Hashtable;
import java.util.List;

public class Manager {
    private Hashtable<Integer, City> table;
    private Integer id = 1;

    public Manager(String filename) throws IOException {
        List<String> stroki =  CustomFileReader.readFile(filename);
        createCitiesFromJson(stroki);
    }

    private void createCitiesFromJson(List<String> stroki) {
        boolean start_create_city = false, past_city = false, start_coordinates = false, start_governor = false, start_governor_birthdate = false;
        String name = null, gov_name = null;
        Coordinates coordinates = null;
        Long area = null, population = null;
        Integer MASL = null, gov_year = null, gov_month = null, gov_day = null, cor_x = null, cor_y = null, carCode = null;
        Climate climate = null;
        StandardOfLiving standardOfLiving = null;
        Human gover = null;
        for (String s : stroki) {
            if (!past_city) {if (s.contains("\"city\": [")) {past_city = true;}}
            else if (s.contains("{")) {
                start_create_city = true;
                start_coordinates = false; start_governor = false; start_governor_birthdate = false;
                name = null; gov_name = null;
                coordinates = null;
                area = null; population = null;
                MASL = null; gov_year = null; gov_month = null; gov_day = null; cor_x = null; cor_y = null; carCode = null;
                climate = null;
                standardOfLiving = null;
                gover = null;
            }
            else if (s.contains("}")) {
                start_create_city = false;
                City city = new City(id, name, coordinates, area, population, MASL, carCode, climate, standardOfLiving, gover);
                System.out.println(city.toString());
            }
            if (start_create_city) {
                if (start_coordinates) {
                    if (cor_x == null) {
                        cor_x = Pomogtor.StringToInteger(s);
                    } else if (cor_y == null) {cor_y = Pomogtor.StringToInteger(s);
                    } else if (s.contains("]")) {
                        start_coordinates = false;
                        coordinates = new Coordinates(cor_x, cor_y);
                    }
                } else if (start_governor) {
                    if (start_governor_birthdate) {
                        if (gov_year == null) {gov_year = Pomogtor.StringToInteger(s);
                        } else if (gov_month == null) {gov_month = Pomogtor.StringToInteger(s);
                        } else if (gov_day == null) {gov_day = Pomogtor.StringToInteger(s);
                        } else if (s.contains("]")) {start_governor_birthdate = false;}
                    } else if (s.contains("]")) {
                        start_governor = false;
                        ZonedDateTime dateTime = ZonedDateTime.of(gov_year, gov_month, gov_day, 0, 0, 0, 0, ZoneId.of("Europe/Moscow"));
                        gover = new Human(dateTime, gov_name);
                    } else {gov_name = Pomogtor.StringToNormalString(s);}
                    if (s.contains("[")) {start_governor_birthdate = true;}
                } else if (s.contains("\"name\":")) {
                    name = Pomogtor.StringToNormalString(s.split("\":")[1]);
                } else if (s.contains("\"coordinates\":")) {
                    start_coordinates = true;
                } else if (s.contains("\"area\":")) {
                    area = Pomogtor.StringToLong(s.split("\":")[1]);
                } else if (s.contains("\"population\":")) {
                    population = Pomogtor.StringToLong(s.split("\":")[1]);
                } else if (s.contains("\"metersAboveSeaLevel\":")) {
                    MASL = Pomogtor.StringToInteger(s.split("\":")[1]);
                } else if (s.contains("\"carCode\":")) {
                    carCode = Pomogtor.StringToInteger(s.split("\":")[1]);
                } else if (s.contains("\"Climate\":")) {
                    climate = Climate.getById(Pomogtor.StringToInt(s.split("\":")[1]));
                } else if (s.contains("\"StandardOfLiving\":")) {
                    standardOfLiving = StandardOfLiving.getById(Pomogtor.StringToInt(s.split("\":")[1]));
                } else if (s.contains("\"Governor\":")) {
                    start_governor = true;
                }
            }
//            System.out.println(s);
        }
    }
}


class Pomogtor {
    public static Integer StringToInteger(String s) {
        return Integer.valueOf(StringToNormalString(s));
    }

    public static Long StringToLong(String s) {
        return Long.valueOf(StringToNormalString(s));
    }

    public static Integer StringToInt(String s) {
        return Integer.parseInt(StringToNormalString(s));
    }

    public static String StringToNormalString(String s) {
        return s.replace(",", "").replace("\"", "").strip();
    }
}