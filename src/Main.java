import my_programm.CustomFileReader;
import my_programm.Manager;

import java.util.List;
import java.util.Scanner;
import java.util.SortedMap;

public class Main {

    private static Manager manager;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;
        while (!"no".equalsIgnoreCase(input = scanner.next())) {
            try {
                manager = new Manager(input);
            } catch (Exception e) {
                System.out.println(e.toString());
                for (var st : e.getStackTrace()) {
                    System.out.println(st);
                }
            }
        }
    }
}
