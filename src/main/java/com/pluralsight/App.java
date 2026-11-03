package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class App {
    static Scanner input = new Scanner(System.in);
    static ArrayList<Transaction> transactions = getTransactions();
    static LocalDateTime localDateTime = LocalDateTime.now();
    static Transaction transaction = new Transaction();

    public static void main(String[] args) {
        loadTransactions();

       runHomeScreen();

    }

    private static void displayMonthToDate() {
        String year = String.valueOf(localDateTime.getYear());
        String month = String.valueOf(localDateTime.getMonthValue());
        int day = Integer.parseInt(String.valueOf(localDateTime.getDayOfMonth()));

        for(Transaction transaction : transactions){
            if(transaction != null){
                String[] tokens = transaction.getDate().toString().split(Pattern.quote("-"));
                if(tokens[0].equals(year) && tokens[1].equals(month) && Integer.parseInt(tokens[2]) < day){
                    System.out.println(transaction.toString());
                }
            }
        }
        pressEnterToContinue();
    }

    private static void displayDeposits() {
        for(Transaction transaction : transactions){
            if(transaction != null && transaction.getAmount() > 0){
                System.out.print(transaction.toCSV());
            }
        }
        pressEnterToContinue();
    }

    private static void displayPayments() {
        for(Transaction transaction : transactions){
            if(transaction != null && transaction.getAmount() < 0){
                System.out.print(transaction.toCSV());
            }
        }
        pressEnterToContinue();
    }

    private static void displayAllEntries() {
        for(Transaction transaction : transactions){
            System.out.println(transaction.toCSV());
        }
        pressEnterToContinue();
    }

    private static void loadTransactions(){
        try {
            String line;
            BufferedReader bufferedReader = new BufferedReader(new FileReader("transactions.csv"));
            bufferedReader.readLine();
            while((line = bufferedReader.readLine()) != null){
                if(!line.isBlank()){
                    String[] tokens = line.split(Pattern.quote("|"));
                    transaction = new Transaction(LocalDate.parse(tokens[0]), LocalTime.parse(tokens[1]), tokens[2], tokens[3], Double.parseDouble(tokens[4]));
                    transactions.add(transaction);
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            System.out.print("File not found.");;
        }
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

            switch (reportsScreenOption){
                case "1"-> displayMonthToDate();
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
                    D) Deposits
                    P) Payments
                    R) Reports
                    H) Home:\s""");
            ledgerScreenOption = input.nextLine().toLowerCase().trim();

            switch (ledgerScreenOption){
                case "a"-> displayAllEntries();
                case "d"-> displayDeposits();
                case "p"-> displayPayments();
                case "r"-> runReportsScreen();
                case "h"-> System.out.println("Returning To Home Screen..");//System.out.println("Going To Home Screen...");
                default -> System.out.println("Invalid Input: Letter entered is not valid.");
            }
        } while (!ledgerScreenOption.equals("h"));
    }

    private static void runHomeScreen() {
        String homeScreenOption;
        do {
            System.out.print("""
                    
                    ==================
                    \tHome Screen
                    ==================
                    D) Add Deposit
                    P) Make Payment
                    L) Ledger
                    X) Exit:\s""");
            homeScreenOption = input.nextLine().toLowerCase().trim();

            switch (homeScreenOption){
                case "d"-> addDeposit();
                case "p"-> makePayment();
                case "l"-> runLedgerScreen();
                case "x"-> System.out.println("Goodbye!");
                default -> System.out.println("Invalid Input: Letter entered is not valid, try again.");
            }
        } while (!homeScreenOption.equals("x"));
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
