package my_programm.obj;

public class Human {
    private java.time.ZonedDateTime birthday;
    private String name;

    public Human(java.time.ZonedDateTime date, String name) {
        birthday = date;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Human{" +
                "birthday=" + birthday +
                ", name='" + name + '\'' +
                '}';
    }
}
