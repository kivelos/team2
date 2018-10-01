package dev.java;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;


public class BirthDateTest {
    private BirthDate[] birthDate = new BirthDate[26];
    private Calendar calendar = Calendar.getInstance();


    private int[] setBirthDateHelpMethod(int indexInArray, String dateString) {
        int[] result = new int[3];
        try {
            BirthDate currentBirthDate = new BirthDate(dateString);
            birthDate[indexInArray] = currentBirthDate;
            result[0] = currentBirthDate.getYear();
            result[1] = currentBirthDate.getMonth();
            result[2] = currentBirthDate.getDay();
            return result;
        }
        catch (IllegalArgumentException e) {
            return null;
        }

    }

    @Before
    public void Constructor() {
        int[] expected = {2000, 2, 26};
        assertArrayEquals(expected, setBirthDateHelpMethod(0, "26/02/2000"));
        expected[0] = 1995; expected[1] = 12; expected[2] = 18;
        assertArrayEquals(expected, setBirthDateHelpMethod(1, "18-12-1995"));
        expected[0] = 1983; expected[1] = 6; expected[2] = 9;
        assertArrayEquals(expected, setBirthDateHelpMethod(2, "9-06-83"));
        expected[0] = 2004; expected[1] = 2; expected[2] = 28;
        assertArrayEquals(expected, setBirthDateHelpMethod(3, "28/02/2004"));
        expected[0] = 2004; expected[1] = 2; expected[2] = 29;
        assertArrayEquals(expected, setBirthDateHelpMethod(4, "29/02/04"));
        assertArrayEquals(null, setBirthDateHelpMethod(5, "13-13-13"));
        assertArrayEquals(null, setBirthDateHelpMethod(6, "31-04-2010"));
        assertArrayEquals(null, setBirthDateHelpMethod(7, "29/02/05"));
        assertArrayEquals(null, setBirthDateHelpMethod(8, "31/11/2008"));
        assertArrayEquals(null, setBirthDateHelpMethod(9, "3o/11/2008"));
        assertArrayEquals(null, setBirthDateHelpMethod(10, "ab/ab/ab"));
        assertArrayEquals(null, setBirthDateHelpMethod(11, "hello"));
        assertArrayEquals(null, setBirthDateHelpMethod(12, "28.05.2017"));
        assertArrayEquals(null, setBirthDateHelpMethod(13, "30-04/2010"));
        expected[0] = 1998; expected[1] = 5; expected[2] = 31;
        assertArrayEquals(expected, setBirthDateHelpMethod(14, "31-5-1998"));
        expected[0] = 2001; expected[1] = 1; expected[2] = 1;
        assertArrayEquals(expected, setBirthDateHelpMethod(15, "01-01-2001"));
        expected[0] = 2010; expected[1] = 6; expected[2] = 17;
        assertArrayEquals(expected, setBirthDateHelpMethod(16, "17/06/2010"));
        expected[0] = 2003; expected[1] = 12; expected[2] = 31;
        assertArrayEquals(expected, setBirthDateHelpMethod(17, "31/12/2003"));
        assertArrayEquals(null, setBirthDateHelpMethod(18, "-30-15-1996"));
        expected[0] = 1993; expected[1] = 9; expected[2] = 21;
        assertArrayEquals(expected, setBirthDateHelpMethod(19, "21/09/1993"));
        expected[0] = 1945; expected[1] = 5; expected[2] = 9;
        assertArrayEquals(expected, setBirthDateHelpMethod(20, "9/05/45"));
        expected[0] = 1917; expected[1] = 3; expected[2] = 8;
        assertArrayEquals(expected, setBirthDateHelpMethod(21, "08/3/1917"));
        expected[0] = 1956; expected[1] = 7; expected[2] = 16;
        assertArrayEquals(expected, setBirthDateHelpMethod(22, "16-07-56"));
        expected[0] = 1972; expected[1] = 4; expected[2] = 3;
        assertArrayEquals(expected, setBirthDateHelpMethod(23, "3-4-72"));
        expected[0] = 1963; expected[1] = 8; expected[2] = 25;
        assertArrayEquals(expected, setBirthDateHelpMethod(24, "25-08-63"));
        expected[0] = 1988; expected[1] = 2; expected[2] = 29;
        assertArrayEquals(expected, setBirthDateHelpMethod(25, "29-2-1988"));
    }

    private Date getCurrentDate(int year, int month, int date) {
        calendar.set(year, month, date);
        return calendar.getTime();
    }

    @Test
    public void getAge() {
        assertEquals(18, birthDate[0].getAge(getCurrentDate(2018, Calendar.SEPTEMBER, 15)));
        assertEquals(23, birthDate[1].getAge(getCurrentDate(2019, Calendar.DECEMBER, 17)));
        assertEquals(37, birthDate[2].getAge(getCurrentDate(2020, Calendar.JUNE, 9)));
        assertEquals(14, birthDate[3].getAge(getCurrentDate(2018, Calendar.NOVEMBER, 1)));
        assertEquals(13, birthDate[4].getAge(getCurrentDate(2017, Calendar.MARCH, 1)));
        assertEquals(0, birthDate[14].getAge(getCurrentDate(1997, Calendar.MAY, 30)));
        assertEquals(7, birthDate[15].getAge(getCurrentDate(2008, Calendar.DECEMBER, 31)));
        assertEquals(5, birthDate[16].getAge(getCurrentDate(2016, Calendar.APRIL, 3)));
        assertEquals(14, birthDate[17].getAge(getCurrentDate(2018, Calendar.SEPTEMBER, 28)));
        assertEquals(25, birthDate[19].getAge(getCurrentDate(2018, Calendar.OCTOBER, 1)));
        assertEquals(73, birthDate[20].getAge(getCurrentDate(2018, Calendar.DECEMBER, 31)));
        assertEquals(0, birthDate[21].getAge(getCurrentDate(1918, Calendar.FEBRUARY, 7)));
        assertEquals(60, birthDate[22].getAge(getCurrentDate(2017, Calendar.MAY, 3)));
        assertEquals(47, birthDate[23].getAge(getCurrentDate(2019, Calendar.AUGUST, 2)));
        assertEquals(55, birthDate[24].getAge(getCurrentDate(2019, Calendar.JANUARY, 1)));
        assertEquals(31, birthDate[25].getAge(getCurrentDate(2019, Calendar.FEBRUARY, 28)));
    }

    @Test
    public void getNumberOfDaysUntilNextBirthday() {
        assertEquals(164, birthDate[0].getNumberOfDaysUntilNextBirthday(getCurrentDate(2018, Calendar.SEPTEMBER, 15)));
        assertEquals(1, birthDate[1].getNumberOfDaysUntilNextBirthday(getCurrentDate(2019, Calendar.DECEMBER, 17)));
        assertEquals(0, birthDate[2].getNumberOfDaysUntilNextBirthday(getCurrentDate(2020, Calendar.JUNE, 9)));
        assertEquals(119, birthDate[3].getNumberOfDaysUntilNextBirthday(getCurrentDate(2018, Calendar.NOVEMBER, 1)));
        assertEquals(365, birthDate[4].getNumberOfDaysUntilNextBirthday(getCurrentDate(2017, Calendar.MARCH, 1)));
        assertEquals(1, birthDate[14].getNumberOfDaysUntilNextBirthday(getCurrentDate(1997, Calendar.MAY, 30)));
        assertEquals(1, birthDate[15].getNumberOfDaysUntilNextBirthday(getCurrentDate(2008, Calendar.DECEMBER, 31)));
        assertEquals(75, birthDate[16].getNumberOfDaysUntilNextBirthday(getCurrentDate(2016, Calendar.APRIL, 3)));
        assertEquals(94, birthDate[17].getNumberOfDaysUntilNextBirthday(getCurrentDate(2018, Calendar.SEPTEMBER, 28)));
        assertEquals(355, birthDate[19].getNumberOfDaysUntilNextBirthday(getCurrentDate(2018, Calendar.OCTOBER, 1)));
        assertEquals(129, birthDate[20].getNumberOfDaysUntilNextBirthday(getCurrentDate(2018, Calendar.DECEMBER, 31)));
        assertEquals(29, birthDate[21].getNumberOfDaysUntilNextBirthday(getCurrentDate(1918, Calendar.FEBRUARY, 7)));
        assertEquals(74, birthDate[22].getNumberOfDaysUntilNextBirthday(getCurrentDate(2017, Calendar.MAY, 3)));
        assertEquals(245, birthDate[23].getNumberOfDaysUntilNextBirthday(getCurrentDate(2019, Calendar.AUGUST, 2)));
        assertEquals(236, birthDate[24].getNumberOfDaysUntilNextBirthday(getCurrentDate(2019, Calendar.JANUARY, 1)));
        assertEquals(0, birthDate[25].getNumberOfDaysUntilNextBirthday(getCurrentDate(2019, Calendar.FEBRUARY, 28)));
    }

}