import my_programm.Manager;
import java.util.Scanner;

public class Main {

    private static Manager manager;

    public static void main(String[] args) {
        try {
            manager = new Manager("test.json");
//            manager = new Manager(args[0]);
            Scanner scanner = new Scanner(System.in);
            String input;
            while (!"no".equalsIgnoreCase(input = scanner.nextLine())) {
                try {
                    execute_command(input);
                } catch (Exception e) {
                    System.out.println(e.toString());
//                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private static void execute_command(String input) { // {Новгород [15 23] 125500 522000 301 51 [null] [null] [null]}
        if (input.equals("help")) {
            manager.help();
        } else if (input.equals("info")) {
            manager.info();
        } else if (input.equals("show")) {
            manager.show();
        } else if (input.equals("clear")) {
            manager.clear();
        } else if (input.contains("insert ")) {
            manager.insert_id(input.split("\s")[1], input.substring(input.indexOf("{")));
        } else if (input.contains("remove_key ")) {
            manager.remove_key(input.split(" ")[1]);
        } else if (input.equals("exit")) {
            System.exit(0);
        } else if (input.equals("print_unique_climate")) {
            manager.print_unique_climate();
        } else if (input.contains("update ")) {
            manager.update_id(input.split(" ")[1], input.substring(input.indexOf("{")));
        } else if (input.contains("remove_lower ")) {
            manager.remove_lower(input.split(" ")[1]);
        } else if (input.contains("replace_if_lower ")) {
            manager.replace_if_lower(input.split(" ")[1], input.substring(input.indexOf("{")));
        } else if (input.contains("remove_greater_key ")) {
            manager.remove_greater_key(input.split(" ")[1]);
        } else if (input.equals("sum_of_meters_above_sea_level")) {
            manager.sum_of_meters_above_sea_level();
        } else if (input.equals("print_field_descending_governor")) {
            manager.print_field_descending_governor();
        }
//        else {
//            System.out.println("Я не знаю команды " + input + ", для справки по командам напишите help");
//        }
    }
}
