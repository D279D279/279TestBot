package DateFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Solution {
    public static void main(String[] args) {
        Calendar calendar = new GregorianCalendar();

        int dayoff = Calendar.SUNDAY;
        int currentDay = calendar.get(Calendar.DAY_OF_WEEK);
        int openFrom = 10;
        int openTill = 17;
        int currentTime = calendar.get(Calendar.HOUR_OF_DAY);

        if(currentDay == dayoff) {
            System.out.println("Sorry! Sunday!");
        } else if((currentTime < openFrom) || (currentTime > openTill - 1)) {
            System.out.println("Sorry! Too late;(");
        } else {
            System.out.println("Welcome!");
        }


    }
}
