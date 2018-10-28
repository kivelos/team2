package dev.java;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateProcessor {

  static String[] arrOfFormat = new String[] {"dd/MM/yy", "dd-MM-yyyy", "dd/MM/yyyy", "d-M-yyyy"};

  public static Date tryParseDate(String strDate) {
    Date res = null;
    for (int i=0; i<arrOfFormat.length; i++) {
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
    int age = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR);
    return age;
  }

  public static int calcDaysToBirth(Date dateOfBirth) {
    //календарь сегодняшнего дня
    Date today  = new Date();
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
    int days = (int)(diffMillis/1000/60/60/24);
    return days+1;
  }
}
