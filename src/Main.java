import my_programm.Manager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    private static Manager manager;

    public static void main(String[] args) {
        boolean quest = false, cycle = true, exist = false;
        try {
            manager = new Manager();
            File f = new File("localsave.json");
            if (f.exists() && !f.isDirectory()) {
                System.out.println("У вас существует локальное сохранение, вы желаете его загрузить? [Y/n]");
                exist = true;
            }
            Scanner scanner = new Scanner(System.in);
            List<String> commands = new ArrayList<>();
            List<String> locarr = new ArrayList<>();

            String input = args[0];
//            String input = "test.json";

            if (exist) {
                if (scanner.nextLine().equalsIgnoreCase("y")) {
                    manager.setFile("localsave.json", false);
                    System.out.println("Данные восстановлены");
                } else {
                    System.out.println("Данные утеряны(");
                }
                try {
                    f.delete();
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
            } else {
                manager.setFile(input, true);
            }

            while (cycle) {
                input = scanner.nextLine();
                try {
                    if (quest && input.equalsIgnoreCase("y")) {
                        commands.add("exit");
                        cycle = false;
                    } else {
                        quest = false;
                    }
                    if (input.equals("exit")) {
                        System.out.println("Вы уверены, что хотите выйти? Y/n");
                        quest = true;
                    } else {
                        commands.add(input);
                    }
                    int count = 0;
                    while (commands.size() > 0) {
                        for (String s : commands) {
                            for (String str : manager.commandHandler(s.strip())) {
                                locarr.add(str);
                            }
                        }
                        commands.clear();
                        for (String s : locarr) {
                            commands.add(s);
                        }
                        locarr.clear();
                        count++;
                        if (count > 200) {
                            throw new RuntimeException("Слишком многочисленный вызов исполняемых файлов в автоматическом режиме");
                        }
                    }
                    commands.clear();
                } catch (Exception e) {
                    System.out.println(e.toString());
//                    e.printStackTrace();
                    commands.clear();
                    locarr.clear();
                }
            }
        } catch (NoSuchElementException e) {
            try {
                if (manager.isChange_something()) {
                    manager.save("localsave.json");
                }
            } catch (Exception e2) {
                System.out.println(e2.toString());
            }
        } catch (Exception e) {
            System.out.println(e.toString());
//            e.printStackTrace();
        }
    }
}
