package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class App {
    static Scanner input = new Scanner(System.in);
    static ArrayList<Transaction> transactions = getTransactions();
    static LocalDateTime localDateTime = LocalDateTime.now();

    public static void main(String[] args) {
        loadTransactions();

        runHomeScreen();
        input.close();
    }

    private static void displaySearchByVendor() {
        boolean vendorTransaction = false;
        String searchVendor = promptForString("\nEnter Vendor: ");
        System.out.print("""
                
                \t\tSearch By Vendor Report
                --------------------------------------------------------------------------------""");
        displayTableHeader();
        vendorTransaction = filterSearchByVendor(searchVendor.toLowerCase(), vendorTransaction);
        if(!vendorTransaction){
            System.out.println("No transactions were found.");
        }
        pressEnterToContinue();
    }

    private static boolean filterSearchByVendor(String searchVendor, boolean vendorTransaction) {
        for(Transaction transaction : transactions){
            if(transaction != null){
                String vendor = transaction.getVendor().toLowerCase();
                if(vendor.contains(searchVendor)){
                    System.out.println(transaction.toTable());
                    vendorTransaction = true;
                }
            }
        }
        return vendorTransaction;
    }

    private static void displayPreviousYear() {
        boolean transactionMade = false;
        System.out.println("""
                
                \t\tPrevious Year Report
                --------------------------------------------------------------------------------""");
        displayTableHeader();
        transactionMade = filterPreviousYear(transactionMade);

        if(!transactionMade){
            System.out.println("No transactions were found.");
        }
        pressEnterToContinue();
    }

    private static boolean filterPreviousYear(boolean transactionMade) {
        for(Transaction transaction : transactions){
            if(transaction != null){
                if(transaction.getDate().getYear() == LocalDate.now().minusYears(1).getYear()){
                    System.out.println(transaction.toTable());
                    transactionMade = true;
                }
            }
        }
        return transactionMade;
    }

    private static void displayYearToDate() {
        boolean transactionMade = false;
        System.out.println("""
                
                \t\tYear To Date Report
                --------------------------------------------------------------------------------""");
        displayTableHeader();
        transactionMade = filterYearToDate(transactionMade);
        if(!transactionMade){
            System.out.println("No transactions were found.");
        }
        pressEnterToContinue();
    }

    private static boolean filterYearToDate(boolean transactionMade) {
        boolean beforeTodayDate;
        boolean currentYear;
        for(Transaction transaction : transactions){
            if(transaction != null){
                beforeTodayDate = transaction.getDate().isAfter(ChronoLocalDate.from(localDateTime));
                currentYear = transaction.getDate().getYear() == localDateTime.getYear();
                if(currentYear && !beforeTodayDate){
                    System.out.println(transaction.toTable());
                    transactionMade = true;
                }
            }
        }
        return transactionMade;
    }

    private static void displayPreviousMonth() {
        boolean transactionMade = false;
        System.out.println("""
                
                \t\tPrevious Month Report
               --------------------------------------------------------------------------------""");
        displayTableHeader();
        transactionMade = filterPreviousMonth(transactionMade);

        if(!transactionMade){
            System.out.println("No transactions were found.");
        }
        pressEnterToContinue();
    }

    private static boolean filterPreviousMonth(boolean transactionMade) {
        boolean currentYear;
        int previousMonth;
        for(Transaction transaction : transactions){
            if(transaction != null){
                currentYear = transaction.getDate().getYear() == localDateTime.getYear();
                previousMonth = LocalDate.now().minusMonths(1).getMonthValue();
                if( currentYear && transaction.getDate().getMonthValue() == previousMonth){
                    System.out.println(transaction.toTable());
                    transactionMade = true;
                }
            }
        }
        return transactionMade;
    }

    private static void displayMonthToDate() {
        boolean transactionMade = false;
        System.out.println("""
                
                \t\tMonth To Date Report
                --------------------------------------------------------------------------------""");
        displayTableHeader();
        transactionMade = filterMonthToDate(transactionMade);

        if(!transactionMade){
            System.out.println("No transactions were found.");
        }
        pressEnterToContinue();
    }

    private static boolean filterMonthToDate(boolean transactionMade) {
        boolean currentYear;
        int previousMonth;
        boolean currentDay;
        for(Transaction transaction : transactions){
            if(transaction != null){
                currentYear = transaction.getDate().getYear() == localDateTime.getYear();
                previousMonth = LocalDate.now().getMonthValue();
                currentDay = transaction.getDate().getDayOfMonth() <= localDateTime.getDayOfMonth();
                if( currentYear && transaction.getDate().getMonthValue() == previousMonth && currentDay){
                    System.out.println(transaction.toTable());
                    transactionMade = true;
                }
            }
        }
        return transactionMade;
    }

    private static void displayDeposits() {
        System.out.println("""
                
                \t\tDeposits
                --------------------------------------------------------------------------------""");
        displayTableHeader();
        for(Transaction transaction : transactions){
            if(transaction != null && transaction.getAmount() > 0){
                System.out.println(transaction.toTable());
            }
        }
        pressEnterToContinue();
    }

    private static void displayPayments() {
        System.out.println("""
                
                \t\tPayments
                --------------------------------------------------------------------------------""");
        displayTableHeader();
        for(Transaction transaction : transactions){
            if(transaction != null && transaction.getAmount() < 0){
                System.out.println(transaction.toTable());
            }
        }
        pressEnterToContinue();
    }

    private static void displayAllEntries() {
        System.out.println("""

                \t\tAll Entries
                --------------------------------------------------------------------------------""");
        displayTableHeader();
        for(Transaction transaction : transactions){
            System.out.println(transaction.toTable());
        }
        pressEnterToContinue();
    }

    private static void loadTransactions(){
        try {
            Transaction transaction;
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
            System.out.println("Payment amount can't be less than or equal to 0.");
            paymentAmount = promptForDouble("Amount: ");
        }

        filterAddPayment(paymentDescription, paymentVendor, paymentAmount);
    }

    private static void filterAddPayment(String paymentDescription, String paymentVendor, double paymentAmount) {
        LocalTime timeStamp = LocalTime.parse(localDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        LocalDate dateStamp = LocalDate.parse(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        Transaction transaction = new Transaction(dateStamp, timeStamp, paymentDescription, paymentVendor, -paymentAmount);

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

        filterAddDeposit(depositDescription, depositVendor, depositAmount);

    }

    private static void filterAddDeposit(String depositDescription, String depositVendor, double depositAmount) {
        LocalTime timeStamp = LocalTime.parse(localDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        LocalDate dateStamp = LocalDate.parse(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        Transaction transaction = new Transaction(dateStamp, timeStamp, depositDescription, depositVendor, depositAmount);

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
            try {
                return Double.parseDouble(input.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a proper amount.");
            }
        }
    }

    private static void runReportsScreen() {
        String reportsScreenOption;
        do {
            System.out.print("""
                        
                        ======================
                        \tReports
                        ======================
                        1) Month To Date
                        2) Previous Month
                        3) Year To Date
                        4) Previous Year
                        5) Search by Vendor
                        0) Back:\s""");
            reportsScreenOption = input.nextLine().trim();

            switch (reportsScreenOption){
                case "1"-> displayMonthToDate();
                case "2"-> displayPreviousMonth();
                case "3"-> displayYearToDate();
                case "4"-> displayPreviousYear();
                case "5"-> displaySearchByVendor();
                case "0"-> System.out.println("Returning to ledger..");
                default -> System.out.println("\nInvalid Input: Number entered is not valid, try Again.");
            }
        } while (!reportsScreenOption.equals("0"));
    }

    private static void runLedgerScreen() {
        String ledgerScreenOption;
        do {
            System.out.print("""
                    
                    ======================
                    \tLedger
                    ======================
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
                case "h"-> System.out.println("Returning To Home Screen..");
                default -> System.out.println("\nInvalid Input: Letter entered is not valid.");
            }
        } while (!ledgerScreenOption.equals("h"));
    }

    private static void runHomeScreen() {
        String homeScreenOption;
        do {
            System.out.print("""
                    
                    ==================
                    \tHome
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
                default -> System.out.println("\nInvalid Input: Letter entered is not valid, try again.");
            }
        } while (!homeScreenOption.equals("x"));
    }

    public static ArrayList<Transaction> getTransactions(){
        return new ArrayList<Transaction>();
    }

    private static void displayTableHeader(){
        System.out.printf("\n%-12s %-10s %-25s %-20s %10s%n",
                "DATE", "TIME", "DESCRIPTION", "VENDOR", "AMOUNT");

        System.out.println("--------------------------------------------------------------------------------");
    }

    private static void pressEnterToContinue() {
        System.out.println();
        System.out.print("Press Enter to continue...");
        input.nextLine();
    }

}
