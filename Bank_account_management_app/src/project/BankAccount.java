/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

/**
 *
 * @author XXXXX
 */
public class BankAccount {
    private String firstName;
    private String lastName;
    private final int accountNumber;
    private double balance;
    
    // constructor used by the loader function
    public BankAccount (String firstName, String lastName, int accountNumber, double balance){
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }
    
    // constructor used by the manager
    public BankAccount(String firstName, String lastName, int accountNumber){
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountNumber = accountNumber;
        this.balance = 100.0;
    }
    
    public double getBalance(){
        return this.balance;
    }
    
    public void setBalance(double amount){
        this.balance = amount;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
    
    
    
    
}
