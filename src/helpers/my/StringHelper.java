package helpers.my;

public class StringHelper {

    public static String firstUpper(String in) {
        String out = in.substring(0, 1).toUpperCase() + in.substring(1, in.length()).toLowerCase();
        return out;
    }
}
