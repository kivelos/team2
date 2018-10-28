package dev.java;

import java.util.Date;

public class Main {
    private Main(Object o) {
        System.out.println("Object");
    }
    private Main(double[] dArray) {
        System.out.println("double array");
    }
    private Main(int[] iArray) {
        System.out.println("int array");
    }
    public Main() {

    }
    public static void main(String[] args) {
        // write your code here
        String str = "\uD835\uDD38";
        System.out.println(str);
        System.out.println(str.length()); //prints 2
        //new Main(null);
        String s = null;
        System.out.println(s instanceof String); //false
        //System.out.println(new Main() instanceof String); //compilation
        //Main m = (Main) new Object(); //runtime
        //System.out.println(m instanceof Main);
    }
}
