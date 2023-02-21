import my_programm.Manager;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;
        while (!"no".equalsIgnoreCase(input = scanner.next())) {
//            System.out.println("Вы ввели: " + input);
            Manager m = new Manager(input);
        }
    }
}
