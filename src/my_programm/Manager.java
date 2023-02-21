package my_programm;

import my_programm.obj.City;

import java.io.*;
import java.util.Hashtable;

public class Manager {
    private Hashtable<Integer, City> table;
    private Integer id = 1;

    public Manager(String filename) {
        try {
            this.readFile(filename);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void readFile(String filename) throws FileNotFoundException, IOException {
        String content = readAllCharactersOneByOne(new FileReader(new File(filename)));
        System.out.println(content);
    }

    private static String readAllCharactersOneByOne(Reader reader) throws IOException {
        StringBuilder content = new StringBuilder();
        int nextChar;
        while ((nextChar = reader.read()) != -1) {
            content.append((char) nextChar);
        }
        return String.valueOf(content);
    }
}
