import java.util.ArrayList;
import java.util.Scanner;


public class CheckingAccountDriver {
    public static void main(String[] args) {
        // CheckingAccount chkAccount = new CheckingAccount(50, 100, 0.12);
        // System.out.println("Overdraft limit: $" + chkAccount.getOverDraft());
        // System.out.println(chkAccount.toString());
        // chkAccount.withdraw(150);
        // System.out.println(chkAccount.getBalance());
    }
}

class CheckingAccount extends Account {

    private double overDraft;

    public double getOverDraft() {
        return overDraft;
    }

    public void setOverDraft(double overDraft) {
        this.overDraft = overDraft;
    }

    public CheckingAccount(double overDraft, double amount, double rate) {
        // To Do: Uncomment when exception class is built out further. Does not currently work
        //super(amount, rate);
        this.overDraft = overDraft;
    }

    @Override
    public String toString() {
        return super.toString() + " Your overdraft limit is $" + overDraft + ".";
    }

    @Override
    public void withdraw(double amount) {
        double balance = super.getBalance();
        double amountRemaining = balance - amount;
        double overdraftLimit = overDraft * -1;
        if (amountRemaining >= overdraftLimit) {
            super.withdraw(amount);
        }
    }
}
class Atm {
    private ArrayList<Account> accounts = new ArrayList<Account>();

     public Atm() {
         try {
             Account account1 = new Account(100, 0.12);
             Account account2 = new Account(150, 0.12);
             Account account3 = new Account(85, 0.12);
             accounts.add(account1);
             accounts.add(account2);
             accounts.add(account3);
         } catch (NegativeBalanceException ex) {
             System.out.println(ex.getMessage());
         }
     }

     public void menu() {
         System.out.println("Enter an account number. 1 - 3: ");
         Scanner scanner = new Scanner(System.in);
         int accountNum = scanner.nextInt();
         accountNum--; //Subtract 1 to match index
         int selection = displayMenu();
         while (selection != 4) {
             selection = displayMenu();
             makeSelection(accountNum, selection);
         }
     }

     private int displayMenu() {
         Scanner scanner = new Scanner(System.in);
         System.out.println("Main Menu:");
         System.out.println("1: Check Balance");
         System.out.println("2: Deposit");
         System.out.println("3: Withdraw");
         System.out.println("4: Exit");
         int selection = scanner.nextInt();
         return selection;
     }

     private void makeSelection(int index, int selection){
         Scanner scanner = new Scanner(System.in);
        Account selectedAccount = accounts.get(index);
        switch (selection) {
            case 1:
                System.out.println("The current balance is $ " + selectedAccount.getBalance());
                break;
            case 2:
                System.out.println("Enter the amount to deposit: ");
                double depositAmt = scanner.nextDouble();
                selectedAccount.deposit(depositAmt);
                break;
            case 3:
                System.out.println("Enter the amount to withdraw: ");
                double withdrawAmt = scanner.nextDouble();
                selectedAccount.withdraw(withdrawAmt);
                break;
            case 4:
                System.exit(0);
                break;
        }
     }
}


class Account {
   public Account(){
        System.out.println("This is your account");
    }


    private double balance;

    private double annualInterestRate;

    public Account(double amount, double rate) throws NegativeBalanceException {
        balance = amount;
        annualInterestRate = rate;
        if(balance < 0) {
            throw new NegativeBalanceException(balance);
        }
    }

    public double getBalance() {
        return balance;
    }

    public double getAnnualInterestRate() {
        return annualInterestRate;
    }

    public void setAnnualInterestRate(double rate) {
        annualInterestRate = rate;
    }

    public void withdraw(double amount) {
        balance -= amount;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public double getMonthlyInterestRate() {
        return annualInterestRate / 12;
    }

    public double getMonthlyInterest() {
        double monthlyInterestRate = getMonthlyInterestRate();
        return balance * monthlyInterestRate;
    }

    public String toString() {
        return "This account contains $"
                + balance + ". You have earned $"
                + getMonthlyInterest() + " in the last month.";
    }

    public boolean equals(Account a2) {
        return this.getBalance() == a2.getBalance()
                && this.getAnnualInterestRate() == a2.getAnnualInterestRate();
    }

}

 class NegativeBalanceException extends Exception {
    public NegativeBalanceException() {
        super("The account balance is negative.");
    }
    public NegativeBalanceException(double balance) {
        super("The account is $" + balance + " in the negative.");
    }
}