package com.pluralsight;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class App {
    static Scanner input = new Scanner(System.in);
    //static ArrayList<>
    public static void main(String[] args) {
        String mainScreenOption;
        do {
            System.out.print("""
                    ==================
                    \tMain Screen
                    ==================
                    D) Add Deposit
                    P) Make Payment
                    L) Ledger
                    X) Exit:\s""");
            mainScreenOption = input.nextLine().toLowerCase().trim();


            switch (mainScreenOption){
                case "d"-> System.out.println("d");
                case "p"-> System.out.println("p");
                case "l"-> System.out.println("l");
                case "x"-> System.out.println("Goodbye!");
                default -> System.out.println("Invalid Input: Letter entered is not valid.");
            }
            if(!mainScreenOption.equals("x")) {
                pressEnterToContinue();
            }

        } while (!mainScreenOption.equals("x"));
    }

    private static void pressEnterToContinue() {
        System.out.println();
        System.out.print("Press Enter to continue...");
        input.nextLine();
    }

}
