package com.rcf.banking.util;

import java.util.Scanner;

/**
 * This class is used to get input from user
 *
 * @author Chengfeng RONG
 * @version 1.0
 */
public class Input {

    /* The scanner used to get input from user */
    static Scanner scanner = new Scanner(System.in);

    /**
     * Get an integer from user
     *
     * @return an integer
     */
    public static int InputInt() {
        while (true) {
            try {
                System.out.print("> ");
                return Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Please enter a valid int");
            }
        }
    }

    /**
     * Get an integer with the range from user
     *
     * @param lower the lower bound of the range
     * @param upper the upper bound of the range
     * @return an integer
     */
    public static int InputInt(int lower, int upper) {
        while (true) {
            try {
                System.out.print("> ");
                int i = Integer.parseInt(scanner.nextLine());
                if (i >= lower && i <= upper)
                    return i;
                else {
                    System.out.println("Please input an integer within " + lower + " and " + upper);
                }
            } catch (Exception e) {
                System.out.println("Please enter a valid int");
            }
        }
    }

    /**
     * Get a double from user
     *
     * @return a double
     */
    public static double inputDouble() {
        while (true) {
            try {
                System.out.print("> ");
                return Double.parseDouble(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Please enter a valid double");
            }
        }
    }

    /**
     * Get a double within a range(exclusively) from user
     *
     * @return a double
     */
    public static double inputDouble(double lower, double upper) {
        while (true) {
            try {
                System.out.print("> ");
                double i = Double.parseDouble(scanner.nextLine());
                if (i > lower && i < upper)
                    return i;
                else {
                    System.out.println("Please input a double within " + lower + " and " + upper);
                }
            } catch (Exception e) {
                System.out.println("Please enter a valid double");
            }
        }
    }

    /**
     * Get a string from user
     *
     * @return a string
     */
    public static String inputString() {
        System.out.print("> ");
        return scanner.nextLine();
    }

    /**
     * Get a currency from user
     *
     * @return a currency
     */
    public static Currency inputCurrency() {
        while (true) {
            try {
                System.out.print("> ");
                return Currency.valueOf(scanner.nextLine().toUpperCase());
            } catch (Exception e) {
                System.out.println("Please enter a valid currency");
            }
        }
    }

    /* Since the input is from the console, we need to manually test the input in the wrong format
     *  because I use an infinite loop to prompt the user to enter a value.
     */
    public static void main(String[] args) {
        System.out.println("Please enter an int");
        System.out.println(InputInt());
        System.out.println("Please enter an int");
        System.out.println(InputInt(0, 100));
        System.out.println("Please enter a double");
        System.out.println(inputDouble());
        System.out.println("Please enter a double");
        System.out.println(inputDouble(0.0, 100.0));
        System.out.println("Please enter a string");
        System.out.println(inputString());
        System.out.println("Please enter a currency");
        System.out.println(inputCurrency());
    }
}
