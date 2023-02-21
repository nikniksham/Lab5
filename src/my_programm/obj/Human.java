package my_programm.obj;

import java.time.ZonedDateTime;

public class Human {
    private java.time.ZonedDateTime birthday;
    private String name;

    public Human(java.time.ZonedDateTime date, String name) {
        birthday = date;
//        ZonedDateTime dateTime = ZonedDateTime.of(1998, 8, 21, 0, 0, 0, 0, ZoneId.of("Europe/Moscow"));
        this.name = name;
    }
}
