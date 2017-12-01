package bank;/*
* В условието се казва, че changeAmount() трябва да е private. Само че ако това е така - bank.VIPAccount няма как да
* имплементира reduceMoney() метода. Алтернативно amount може да стане protected. Предполагам е typo в условието.
* */

import java.util.ArrayList;

public class Bank_7 {
    public static void main(String[] args) {

        Bank b = new Bank("Barcleys", "London", "1");

        Account acc = new VIPAccount(b, "001");
        Customer customer = new Customer("Pesho", "93");
        customer.addAccount(acc);

        acc.reduceMoney(5000);
        System.out.println("New Balance: " + acc.getAmount());
    }
}


class Bank {
    private String name;
    private String city;
    private String branchNumber;

    public Bank(String name, String city, String branchNumber) {
        this.name = name;
        this.city = city;
        this.branchNumber = branchNumber;
    }

    public String getName() {
        return this.name;
    }

    public String getCity() {
        return this.city;
    }

    public void setBranchNumber(String branchNumber) {
        this.branchNumber = branchNumber;
    }
}

class Account {
    private String accountNumber;
    private Bank branch;
    private int amount;

    public Account(Bank branch, String accountNumber) {
        this.branch = branch;
        this.accountNumber = accountNumber;

        this.amount = 0;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Bank getBranch() {
        return branch;
    }

    protected int getAmount() {
        return amount;
    }

    protected void changeAmount(int amount) {
        this.amount = amount;
    }

    protected void addMoney(int amount) {
        if (amount > 0) {
            this.changeAmount(this.amount + amount);
        } else {
            System.err.println("Can't add less than 0");
        }
    }

    protected void reduceMoney(int amount) {
        if (this.amount >= amount) {
            this.changeAmount(this.amount - amount);
        } else {
            System.err.println("Insufficient balance");
        }
    }
}

class Customer {
    String name;
    String EGN;
    ArrayList<Account> accounts = new ArrayList<>();

    public Customer(String name, String EGN) {

        this.name = name;
        this.EGN = EGN;
    }

    public void addAccount(Account acc) {
        this.accounts.add(acc);
    }

    public void removeAccount(String accountNumber) {

        Account toBeDeleted = null;
        for (Account acc :
                this.accounts) {
            if (acc.getAccountNumber().equals(accountNumber)) {
                toBeDeleted = acc;
                break;
            }
        }
        if (toBeDeleted != null) {
            this.accounts.remove(toBeDeleted);
        } else {
            System.err.println("bank.Customer doesn't have account with number " + accountNumber);
        }
    }

}

class VIPAccount extends Account {
    public VIPAccount(Bank branch, String accountNumber) {
        super(branch, accountNumber);
    }

    @Override
    protected void reduceMoney(int amount) {
        this.changeAmount(this.getAmount() - amount);
    }
}