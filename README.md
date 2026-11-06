# Accounting Ledger

Accounting ledger can track all financial transactions for a
business or for personal use. It adds deposits or payments made and
if the user wants reports it will be displayed to them upon request.

## Features

- Add Deposit
- Make Payment (Debit)
- Option to display all entries 
- Display deposits made into the account
- Display payments made into the account
- Show report of Month To Date
- Show report of Previous Month
- Show report of Year To Date
- Show report of  Previous Year
- Search by Vendor

## How to run it

1. Clone the repository and open it in IntelliJ
2. Run `[App].java`
3. Type an option from the Home Screen. Ex: `D`

## Screenshots

![The home screen](images/home-screen.png)

![The reports screen](images/reports-screen.png)

## Interesting code

This interesting to me because reduces code and make this method for 
an action method

```java
 private static void displayTransactions(ArrayList<Transaction> transactions){
    if(transactions.isEmpty()){
        System.out.println("No transactions were found!");
    }else{
        displayTableHeader();
        for(Transaction transaction : transactions){
            System.out.println(transaction.toTable());
        }
    }
}
```

## Author

Ruby Santos, Year Up Java Academy