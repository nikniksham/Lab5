package my_programm.obj;

public class Coordinates {
    private long x; //Максимальное значение поля: 136
    private long y;

    public Coordinates(long x_c, long y_c) {
        if (x_c > 136) {throw new Error("Максимальное значение X кооридинаты - 136");}
        else if (x_c < 0) {throw new Error("Минимальное значение X кооридинаты - 0");}
        else {x = x_c;}

        if (y_c > 136) {throw new Error("Максимальное значение Y кооридинаты - 136");}
        else if (y_c < 0) {throw new Error("Минимальное значение Y кооридинаты - 0");}
        else {y = y_c;}
    }
}