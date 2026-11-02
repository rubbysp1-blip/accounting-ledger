package com.pluralsight;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class App {
    static Scanner input = new Scanner(System.in);
    //static ArrayList<>
    public static void main(String[] args) {
        runHomeScreen();

        //runReportsScreen();

    }

    private static void runReportsScreen() {
        String reportsScreenOption;
        //do {
        System.out.print("""
                    ==================
                    \tReports Screen
                    ==================
                    1) Month To Date
                    2) Previous Month
                    3) Year To Date
                    4) Previous Year
                    5) Search by Vendor
                    0) Back:\s""");
        reportsScreenOption = input.nextLine().trim();
        input.nextLine();

        switch (reportsScreenOption){
            case "1"-> System.out.println("a");
            case "2"-> System.out.println("d");
            case "3"-> System.out.println("p");
            case "4"-> System.out.println("l");
            case "5"-> System.out.println("5");
            case "0"-> System.out.println("Going To Ledger Screen..");//System.out.println("Going To Home Screen...");
            default -> System.out.println("Invalid Input: Letter entered is not valid.");
        }
    }


    private static void runLedgerScreen() {
        String ledgerScreenOption;
        //do {
            System.out.print("""
                    ==================
                    \tLedger Screen
                    ==================
                    A) All
                    D) Deposit
                    P) Payments
                    R) Reports
                    H) Home:\s""");
            ledgerScreenOption = input.nextLine().toLowerCase().trim();
            input.nextLine();

            switch (ledgerScreenOption){
                case "a"-> System.out.println("a");
                case "d"-> System.out.println("d");
                case "p"-> System.out.println("p");
                case "r"-> runReportsScreen();
                case "h"-> System.out.println("Going To Home Screen..");//System.out.println("Going To Home Screen...");
                default -> System.out.println("Invalid Input: Letter entered is not valid.");
            }
//            if(!ledgerScreenOption.equals("h")) {
//                pressEnterToContinue();
//            }

        //} while (!ledgerScreenOption.equals("x"));
    }

    private static void runHomeScreen() {
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
            //input.nextLine();

            switch (mainScreenOption){
                case "d"-> System.out.println("d");
                case "p"-> System.out.println("p");
                case "l"-> runLedgerScreen();
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
