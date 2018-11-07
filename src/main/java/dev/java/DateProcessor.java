package dev.java;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DateProcessor {

    private static String[] arrOfFormat = new String[]{"dd/MM/yy", "dd-MM-yyyy", "dd/MM/yyyy", "d-M-yyyy"};
    private static final int HOURS_IN_DAY = 24;
    private static final int MINUTES_IN_HOUR = 60;
    private static final int SECONDS_IN_MINUTE = 60;
    private static final int MILLISECONDS_IN_SECOND = 1000;

    private DateProcessor() {

    }

    public static Date tryParseDate(String strDate) {
        Date res = null;
        for (int i = 0; i < arrOfFormat.length; i++) {
            SimpleDateFormat dateFmt = new SimpleDateFormat(arrOfFormat[i]);
            try {
                res = dateFmt.parse(strDate);
                break;
            } catch (ParseException e) {
                res = null;
            }
        }
        return res;
    }

    public static int calcAge(Date dateOfBirth) {
        Date today = new Date();
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(dateOfBirth);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(today);

        return cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR);
    }

    public static int calcDaysToBirth(Date dateOfBirth) {
        //календарь сегодняшнего дня
        Date today = new Date();
        Calendar calToday = Calendar.getInstance();
        calToday.setTime(today);
        //следующий день рождения
        Calendar calNextDob = Calendar.getInstance();
        calNextDob.setTime(dateOfBirth);
        //назначаем текущий год дню рождения - получаем ДР этого года. Может быть уже прошедший.
        int year = calToday.get(Calendar.YEAR);
        calNextDob.set(Calendar.YEAR, year);
        //если ДР прошел, то добавляем еще 1 год к году дня рождения
        if (calNextDob.before(calToday)) {
            year++;
            calNextDob.set(Calendar.YEAR, year);
        }
        Date nextDob = calNextDob.getTime();
        //разница между двумя датами в милисекундах
        long diffMillis = Math.abs(nextDob.getTime() - today.getTime());
        int days = (int) (diffMillis / MILLISECONDS_IN_SECOND / SECONDS_IN_MINUTE
                / MINUTES_IN_HOUR / HOURS_IN_DAY);
        return days + 1;
    }
}
