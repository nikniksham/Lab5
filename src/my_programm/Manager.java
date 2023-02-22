package my_programm;

import my_programm.enums.Climate;
import my_programm.enums.StandardOfLiving;
import my_programm.obj.City;
import my_programm.obj.Coordinates;
import my_programm.obj.Human;
import org.w3c.dom.ls.LSOutput;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

public class Manager {
    private Hashtable<Integer, City> table = new Hashtable<>();
    private Integer id = 1;
    private java.util.Date dateIni;
    private Integer loc_id = null;
    public Manager(String filename) throws IOException {
        List<String> stroki =  CustomFileReader.readFile(filename);
        createCitiesFromJson(stroki);
        dateIni = Calendar.getInstance().getTime();
    }

    public void help() {
        System.out.println("help : вывести справку по доступным командам\n" +
                           "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                           "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                           "insert {id} {element} : откроет меню создания нового элемента с заданным ключом\n" +
                           "update {id} {element} : откроет меню создания нового элемента, для замены старого по id\n" +
                           "remove_key {id} : удалить элемент из коллекции по его ключу\n" +
                           "clear : очистить коллекцию\n" +
                           "save : сохранить коллекцию в файл\n" +
                           "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                           "exit : завершить программу (без сохранения в файл)\n" +
                           "remove_lower {element}: удалить из коллекции все элементы, меньшие чем заданный\n" +
                           "replace_if_lower {id} {element} : заменить значение по ключу, если новое созданное значение меньше старого (по выбранному параметру)\n" +
                           "remove_greater_key {id} : удалить из коллекции все элементы, ключ которых превышает заданный\n" +
                           "sum_of_meters_above_sea_level : вывести сумму значений поля metersAboveSeaLevel для всех элементов коллекции\n" +
                           "print_unique_climate : вывести уникальные значения поля climate всех элементов в коллекции\n" +
                           "print_field_descending_governor : вывести значения поля governor всех элементов в порядке убывания\n" +
                           "*Под {id} подразумевается id города в таблице\n" +
                           "*Под {element} подразумевается {name [x y] area population MASL carCode [null/1-5] [null/1-5] [null/year month day name_gov]}");
    }

    public void info() {
        System.out.println("Таблица: ключ - Integer, хранимые данные - City\nДата инициализации: " + dateIni.toString() + "\nКоличество элементов: " + table.size());
    }

    public void show() {
        if (table.size() == 0) {
            System.out.println("Таблица пуста");
        }

        ArrayList<City> arr_val = new ArrayList<>();
        ArrayList<Integer> arr_key = new ArrayList<>();
        for (Map.Entry<Integer,City> entry : table.entrySet()) {
            arr_val.add(entry.getValue());
            arr_key.add(entry.getKey());
        }

        for (int i = arr_val.size() - 1; i > -1; --i) {
            System.out.println(arr_key.get(i) + " " + arr_val.get(i));
            System.out.println("-------------------------------------");
        }
    }

    public void clear() {
        table.clear();
        System.out.println("Таблица очищена");
        this.id = 1;
    }

    public void insert_id(String sid, String element) {
        int id = Pomogtor.StringToInt(sid);
        if (id < 0) {
            throw new RuntimeException("id должен быть больше 0");
        } else if (table.containsKey(id)) {
            throw new RuntimeException("Этот id уже занят");
        } else {
            table.put(id++, this.create_city_by_string(element));
            this.id = id;
            System.out.println("Новый город добавлен");
        }
    }

    public void print_unique_climate() {
        if (table.size() == 0) {
            System.out.println("Таблица пуста");
        }

        ArrayList<Climate> arr = new ArrayList<>();
        for (Map.Entry<Integer,City> entry : table.entrySet()) {
            Climate climate = entry.getValue().getClimate();
            if (climate != null && !arr.contains(climate)) {
                System.out.println(climate);
                arr.add(climate);
            }
        }
    }

    public void remove_key(String sid) {
        int id = Pomogtor.StringToInt(sid);
        if (table.containsKey(id)) {
            table.remove(id);
            System.out.println("Город удалён");
        } else {
            System.out.println("Не найден город с таким id");
        }
    }

    public void update_id(String sid, String string) {
        int id = Pomogtor.StringToInt(sid);
        if (!table.containsKey(id)) {
            throw new RuntimeException("По этому id ничего не найдено");
        } else {
            loc_id = id;
            table.replace(id, this.create_city_by_string(string));
            loc_id = null;
            System.out.println("Новое значение задано");
        }
    }

    public void remove_lower(String sid) {
        int id = Pomogtor.StringToInt(sid);
        if (!table.containsKey(id)) {
            throw new RuntimeException("По этому id ничего не найдено");
        } else {
            long num = table.get(id).get_num_for_srav();
            ArrayList<City> arr_val = new ArrayList<>();
            ArrayList<Integer> arr_key = new ArrayList<>();
            for (Map.Entry<Integer,City> entry : table.entrySet()) {
                arr_val.add(entry.getValue());
                arr_key.add(entry.getKey());
            }

            for (int i = 0; i < arr_key.size(); ++i) {
                if (arr_val.get(i).get_num_for_srav() < num) {
                    table.remove(arr_key.get(i));
                }
            }
            System.out.println("Все лишние города удалены");
        }
    }

    public void replace_if_lower(String sid, String string) {
        int id = Pomogtor.StringToInt(sid);
        if (!table.containsKey(id)) {
            throw new RuntimeException("По этому id ничего не найдено");
        } else {
            City old_city = table.get(id);
            loc_id = id;
            City new_city = create_city_by_string(string);
            loc_id = null;
            if (old_city.get_num_for_srav() > new_city.get_num_for_srav()) {
                table.replace(id, new_city);
                System.out.println("Город заменён");
                return;
            }
            System.out.println("Город не заменён");
        }
    }

    public void remove_greater_key(String sid) {
        int id = Pomogtor.StringToInt(sid);
        ArrayList<Integer> arr_key = new ArrayList<>();
        for (Map.Entry<Integer,City> entry : table.entrySet()) {
            arr_key.add(entry.getKey());
        }
        for (Integer integer : arr_key) {
            if (integer > id) {
                table.remove(integer);
            }
        }
        System.out.println("Всё слишком большое удалено");
    }

    public void sum_of_meters_above_sea_level() {
        int sum = 0;
        for (Map.Entry<Integer,City> entry : table.entrySet()) {
            sum += entry.getValue().getMetersAboveSeaLevel();
        }
        System.out.println(sum);
    }

    public void print_field_descending_governor() {
        if (table.size() == 0) {
            System.out.println("Таблица пуста");
        }

        for (Map.Entry<Integer,City> entry : table.entrySet()) {
            System.out.println(entry.getValue().getGovernor());
        }
    }

    private City create_city_by_string(String string) {
//         {name [x y] area population MASL carCode [null/1-5] [null/1-5] [null/year month day name_gov]}
        boolean climate_is_set = false, level_is_set = false;
        String name = null, gov_name = "";
        Coordinates coordinates = null;
        Long area = null, population = null;
        Integer MASL = null, gov_year = null, gov_month = null, gov_day = null, cor_x = null, cor_y = null, carCode = null;
        Climate climate = null;
        StandardOfLiving standardOfLiving = null;
        Human gover = null;
        ArrayList<String> stt = new ArrayList<>();
        for (String s : string.strip().replace("{", "").replace("}", "").split(" ")) {
            stt.add(Pomogtor.StringToString(s, new String []{"[", "]"}));
        }
        String s;
        for (int i = 0; i < stt.size(); ++i) {
            s = stt.get(i);
            if (name == null) {
                name = s;
            } else if (cor_x == null) {
                cor_x = Pomogtor.StringToInteger(s);
            } else if (cor_y == null) {
                cor_y = Pomogtor.StringToInteger(s);
                coordinates = new Coordinates(cor_x, cor_y);
            } else if (area == null) {
                area = Pomogtor.StringToLong(s);
            } else if (population == null) {
                population = Pomogtor.StringToLong(s);
            } else if (MASL == null) {
                MASL = Pomogtor.StringToInteger(s);
            } else if (carCode == null) {
                carCode = Pomogtor.StringToInteger(s);
            } else if (!climate_is_set) {
                if (!s.equals("null")) {
                    climate = Climate.getById(Pomogtor.StringToInt(s));
                }
                climate_is_set = true;
            } else if (!level_is_set) {
                if (!s.equals("null")) {
                    standardOfLiving = StandardOfLiving.getById(Pomogtor.StringToInt(s));
                }
                level_is_set = true;
            } else {
                if (!s.equals("null")) {
                    if (gov_year == null) {
                        gov_year = Pomogtor.StringToInteger(s);
                    } else if (gov_month == null) {
                        gov_month = Pomogtor.StringToInteger(s);
                    } else if (gov_day == null) {
                        gov_day = Pomogtor.StringToInteger(s);
                    } else {
                        if (i < stt.size() - 1) {
                            gov_name += s + " ";
                        } else {
                            gov_name += s;
                            ZonedDateTime dateTime = ZonedDateTime.of(gov_year, gov_month, gov_day, 0, 0, 0, 0, ZoneId.of("Europe/Moscow"));
                            gover = new Human(dateTime, gov_name);
                        }
                    }
                } else {
                    gover = null;
                    break;
                }
            }
        }
        City city = new City(id, name, coordinates, area, population, MASL, carCode, climate, standardOfLiving, gover);
        id++;
        return city;
    }

    private City createNewCity(Scanner scanner) {
        System.out.println("Как город обзывается?");
        String name = scanner.nextLine();
        System.out.println("На каких координатах тот находится? (2 числа через пробел в диапазоне [0, 136])");
        String coords = scanner.nextLine();
        int x_cor = Pomogtor.StringToInt(coords.split(" ")[0]), y_cor = Pomogtor.StringToInt(coords.split(" ")[1]);
        Coordinates coordinates = new Coordinates(x_cor, y_cor);
        System.out.println("Какую площадь занимает город?");
        long area = Pomogtor.StringToLong(scanner.nextLine());
        System.out.println("Сколько человеков живёт в городе?");
        Long population = Pomogtor.StringToLong(scanner.nextLine());
        System.out.println("Какая высота над уровнем моря?");
        Integer MASL = Pomogtor.StringToInteger(scanner.nextLine());
        System.out.println("Какой код региона для авто?");
        int carCode = Pomogtor.StringToInt(scanner.nextLine());
        System.out.println("Какой климат в городе? (написать '-' чтобы пропустить пункт)");
        int id_c = 1;
        for (Climate c : Climate.values()) {System.out.println(id_c++ + " --> " + c);}
        Climate climate = null;
        String res = scanner.nextLine();
        if (!res.equals("-")) {climate = Climate.getById(Pomogtor.StringToInt(res));}
        System.out.println("Какой уровень жизни в городе? (написать '-' чтобы пропустить пункт)");
        id_c = 1;
        for (StandardOfLiving s : StandardOfLiving.values()) {System.out.println(id_c++ + " --> " + s);}
        StandardOfLiving standardOfLiving = null;
        res = scanner.nextLine();
        if (!res.equals("-")) {standardOfLiving = StandardOfLiving.getById(Pomogtor.StringToInt(res));}
        System.out.println("Лично вы знаете старосту этого города? ('-' пропусть)");
        res = scanner.nextLine();
        Human gover = null;
        if (!res.equals("-")) {
            System.out.println("Как его зовут?");
            String gov_name = scanner.nextLine();
            System.out.println("Год рождения?");
            int gov_year = Pomogtor.StringToInt(scanner.nextLine());
            System.out.println("Месяц рождения?");
            int gov_month = Pomogtor.StringToInt(scanner.nextLine());
            System.out.println("День рождения?");
            int gov_day = Pomogtor.StringToInt(scanner.nextLine());
            ZonedDateTime dateTime = ZonedDateTime.of(gov_year, gov_month, gov_day, 0, 0, 0, 0, ZoneId.of("Europe/Moscow"));
            gover = new Human(dateTime, gov_name);
        }
        if (loc_id == null) {
            return new City(id, name, coordinates, area, population, MASL, carCode, climate, standardOfLiving, gover);
        }
        return new City(loc_id, name, coordinates, area, population, MASL, carCode, climate, standardOfLiving, gover);
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
                this.table.put(id, city);
                id++;
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

    public static String StringToString(String string, String[] extra) {
        string = string.strip();
        for (String s : extra) {
            string = string.replace(s, "");
        }
        return string;
    }
}