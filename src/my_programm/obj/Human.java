package my_programm.obj;

import java.time.ZonedDateTime;

public class Human {
    private java.time.ZonedDateTime birthday;
    private String name;

    public Human(java.time.ZonedDateTime date, String name) {
        birthday = date;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Его высочество: " +name + " родился: " + birthday;
    }

    public String getName() {
        return name;
    }

    public ZonedDateTime getBirthday() {
        return birthday;
    }
}
