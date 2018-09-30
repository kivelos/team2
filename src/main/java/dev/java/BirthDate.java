package dev.java;

import java.util.Calendar;
import java.util.Date;

public class BirthDate {
    private int year;
    private int month;
    private int day;
    private final int[] daysInMonths = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public BirthDate(String date) throws IllegalArgumentException {
        parseDate(date);
    }

    private void parseDate(String date) throws IllegalArgumentException {
        int year, month, day;
        String errorMessage = "Invalid date format";
        String[] yearMonthDay = date.split("-");
        if (yearMonthDay.length != 3)
            yearMonthDay = date.split("/");
        if (yearMonthDay.length == 3) {
            try {
                year = Integer.parseInt(yearMonthDay[2]);
                month = Integer.parseInt(yearMonthDay[1]);
                day = Integer.parseInt(yearMonthDay[0]);
                if (yearMonthDay[2].length() != 2 && yearMonthDay[2].length() != 4) {
                    throw new IllegalArgumentException(errorMessage);
                }
                if (yearMonthDay[2].length() == 2)
                    if (year / 10 == 0)
                        year += 2000;
                    else
                        year += 1900;
                if (month < 1 || month > 12 || day < 1 || month == 2 && day == 29 && !isLeapYear(year) ||
                        month != 2 && day > daysInMonths[month - 1])
                    throw new IllegalArgumentException(errorMessage);
                this.year = year;
                this.month = month;
                this.day = day;
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(errorMessage);
            }
        } else
            throw new IllegalArgumentException(errorMessage);
    }

    private int[] extractYearMonthDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int[] result = {calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH)};
        return result;
    }

    public int getAge(Date currentDate) {
        int[] yearMonthDay = extractYearMonthDay(currentDate);
        int currentYear = yearMonthDay[0];
        int currentMonth = yearMonthDay[1];
        int currentDay = yearMonthDay[2];
        if (year >= currentYear)
            return 0;
        int result = currentYear - year;
        if (month < currentMonth)
            return result;
        if (month > currentMonth)
            return result - 1;
        if (day <= currentDay)
            return result;
        if (month == 2 && day == 29 && currentDay == 28 && !isLeapYear(currentYear))
            return result;
        return result - 1;
    }

    private boolean isLeapYear(int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    public int getNumberOfDaysUntilNextBirthday(Date from) {
        int[] yearMonthDay = extractYearMonthDay(from);
        int fromYear = yearMonthDay[0];
        int fromMonth = yearMonthDay[1];
        int fromDay = yearMonthDay[2];
        if (month == 2 && fromMonth == 2 && day == 29 && fromDay == 28 && !isLeapYear(fromYear))
            return 0;
        int result = 0;
        if (month == fromMonth && day >= fromDay)
            result += day - fromDay;
        else {
            result += day + daysInMonths[fromMonth - 1] - fromDay;
            if (fromMonth == 2 && isLeapYear(fromYear))
                result++;
            if (month > fromMonth) {
                for (int i = fromMonth; i < month - 1; i++) {
                    result += daysInMonths[i];
                    if (i == 1 && isLeapYear(fromYear))
                        result++;
                }
            }
            else {
                for (int i = fromMonth; i < 12; i++) {
                    result += daysInMonths[i];
                    if (i == 1 && isLeapYear(fromYear))
                        result++;
                }
                for (int i = 0; i < month - 1; i++) {
                    result += daysInMonths[i];
                    if (i == 1 && isLeapYear(fromYear + 1))
                        result++;
                }
            }
        }

        return result;
    }


    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }
}
