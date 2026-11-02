package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
    static Scanner input = new Scanner(System.in);
    static ArrayList<Transaction> transactions = getTransactions();
    static LocalDateTime localDateTime = LocalDateTime.now();
    static Transaction transaction = new Transaction();

    public static void main(String[] args) {

        runHomeScreen();



    }



    private static void makePayment(){
        System.out.println("""
                
                ===============================
                \tEnter Payment Information
                ===============================""");

        String paymentDescription = promptForString("Description: ");

        String paymentVendor = promptForString("Vendor: ");

        double paymentAmount = promptForDouble("Amount: ");
        while(paymentAmount <= 0){
            System.out.println("Deposit amount can't be less than or equal to 0.");
            paymentAmount = promptForDouble("Amount: ");
        }

        LocalTime timeStamp = LocalTime.parse(localDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        LocalDate dateStamp = LocalDate.parse(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        transaction = new Transaction(dateStamp, timeStamp, paymentDescription, paymentVendor, -Math.abs(paymentAmount));

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("transactions.csv", true));
            bufferedWriter.write(transaction.toCSV());
            bufferedWriter.close();
            System.out.print("\nPayment has been recorded!");
            pressEnterToContinue();

        } catch (IOException e) {
            System.out.println("File not found.");;
        }
    }

    private static void addDeposit() {
        System.out.println("""
                
                ===============================
                \tEnter Deposit Information
                ===============================""");

        String depositDescription = promptForString("Description: ");

        String depositVendor = promptForString("Vendor: ");

        double depositAmount = promptForDouble("Amount: ");
        while(depositAmount <= 0){
            System.out.println("Deposit amount can't be less than or equal to 0.");
            depositAmount = promptForDouble("Amount: ");
        }

        LocalTime timeStamp = LocalTime.parse(localDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        LocalDate dateStamp = LocalDate.parse(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        transaction = new Transaction(dateStamp, timeStamp, depositDescription, depositVendor, depositAmount);

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("transactions.csv", true));
            bufferedWriter.write(transaction.toCSV());
            bufferedWriter.close();
            System.out.print("\nTransaction has been added!");
            pressEnterToContinue();

        } catch (IOException e) {
            System.out.println("File not found.");;
        }


    }

    private static String promptForString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String answer = input.nextLine().trim();
            if (answer.isEmpty()) {
                System.out.println("This can't be blank.");
            } else if (answer.contains("|")) {
                System.out.println("Please don't use the | character.");
            } else {
                return answer;
            }
        }
    }

    private static Double promptForDouble(String amount){
        while (true) {
            System.out.print(amount);
            //double answer = input.nextDouble();

                try {
                    return Double.parseDouble(input.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a proper amount.");
                }
//            }


        }
    }

    private static void runReportsScreen() {
        String reportsScreenOption;
        do {
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
            //input.nextLine();

            pressEnterToContinue();
            switch (reportsScreenOption){
                case "1"-> System.out.println("a");
                case "2"-> System.out.println("d");
                case "3"-> System.out.println("p");
                case "4"-> System.out.println("l");
                case "5"-> System.out.println("5");
                case "0"-> System.out.println("Returning to ledger..");//System.out.println("Going To Home Screen...");
                default -> System.out.println("Invalid Input: Number entered is not valid, try Again.");
            }


        } while (!reportsScreenOption.equals("0"));
    }


    private static void runLedgerScreen() {
        String ledgerScreenOption;
        do {
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
            //input.nextLine();

            pressEnterToContinue();
            switch (ledgerScreenOption){
                case "a"-> System.out.println("a");
                case "d"-> System.out.println("d");
                case "p"-> System.out.println("p");
                case "r"-> runReportsScreen();
                case "h"-> System.out.println("Returning To Home Screen..");//System.out.println("Going To Home Screen...");
                default -> System.out.println("Invalid Input: Letter entered is not valid.");
            }



        } while (!ledgerScreenOption.equals("h"));
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
            if(!mainScreenOption.equals("x")) {
                pressEnterToContinue();
            }

            switch (mainScreenOption){
                case "d"-> addDeposit();
                case "p"-> makePayment();
                case "l"-> runLedgerScreen();
                case "x"-> System.out.println("Goodbye!");
                default -> System.out.println("Invalid Input: Letter entered is not valid, try again.");
            }


        } while (!mainScreenOption.equals("x"));
    }

    public static ArrayList<Transaction> getTransactions(){
        return new ArrayList<Transaction>();
    }

    private static void pressEnterToContinue() {
        System.out.println();
        System.out.print("Press Enter to continue...");
        input.nextLine();
    }

}
