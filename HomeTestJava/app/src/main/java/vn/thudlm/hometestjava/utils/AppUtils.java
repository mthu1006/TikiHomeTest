package vn.thudlm.hometestjava.utils;

public class AppUtils {

    public static String addDotToNumber(int num) {
        String s = String.valueOf(num);
        if(num>1000) {
            try {
                // The comma in the format specifier does the trick
                s = String.format("%,d", Long.parseLong(String.valueOf(num)));
            } catch (NumberFormatException e) {
            }
        }
        // Set s back to the view after temporarily removing the text change listener
        return s;
    }

    public static int randomWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }

}
