/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.io.*;
import java.nio.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author XXXXX
 */
public class Customer {
    // Overview: Customer is a type of mutable user of a bank account management application programme,
    //           which allows this user to deposit and withdraw money, get his/her balance info., login/logout
    //           using customizable userID and password. 
    //
    // The Abstraction Function is:
    // AF(c) = A type of user S of the above specified bank account management application programme, such 
    //         that c.state = current promotional level of S (based on the amount of money currently saved in S's account, if amount < $10,000, level is 
    //                          silver, if $10,000 <= amount < $20,000 then the state is gold, and if amount > $20,000 then the state is platinum)
    //              c.account = a typical bank account which has information regarding S's first, last name, unique account number, and total amount of funds currently available
    // The rep invariant is:
    // RI(c) = True, if and only if, c.state is either instanceof silver, platinum, or gold; and (c.account.firstName or c.account.lastName and c.account.accountNum is unique, that is,
    //                 that is no two customers' account can have same first or last names and same account number). 
    //                 Moreover, c.account.balance cannot be negative.
    //         False, otherwise
    

    private static String CURRENTLYOPENFILENAME = null;
    private static Customer CURRENTCUSTOMER = null;
    
    //the instance variables that are relevant to implement the abstraction function. Please note
    // that username and password are managed by FileOperations class, thus is not considered a part of the rep.
    private CustomerState state;
    private BankAccount account;

    public Customer(BankAccount b, CustomerState s) {
        //Requires: b and s cannot be null
        //Modifies: this
        //Effects : Creates a new Customer object with account b, promotional level s
        account = b;
        state = s;
    }

    public Customer(BankAccount b) {
        //Requires: b cannot be null
        //Modifies: this
        //Effects: Creates a new Customer object with account b
        account = b;
        state = new Silver();
    }
    
    public static Customer GETCURRENTCUSTOMER(){
        //Requires: logIN(String userID, String passWord) must return true
        //Modifies: Nothing
        //Effects: This method has no effects on changin states of any Customer objects. Rather, it serves as a handle that allow a customer app
        //          user to invoke various customer instance methods. This is due to the requirements of generating user files of the project
        return CURRENTCUSTOMER;
    }

    public static boolean logIN(String userID, String passWord) {
        //Requires: userID and passWord cannot be null
        //Modifies:Nothing
        //Effects: This method first makes sure userID exists in the master database file (log.txt), if this is true, it finds
        //         account details from specific user file to ensure the pssWord matches with the password stored in
        //         the specific user file. If userID and passWeord matches with the info stored in user file, it returns
        //         true, otherwise false.
   
        boolean b1 = false;
        String s = null;
        String s2 = null;
        String s3 = null;
        int d = 0;
        try {
            s = FileOperations.readSpecificLine(userID, 0);
            s2 = FileOperations.readSpecificLine(userID, 1);
            if (s2.equals(passWord)) {
                b1 = true;
                CURRENTLYOPENFILENAME = userID;
                s3 = FileOperations.readSpecificLine(CURRENTLYOPENFILENAME, 2);
                d = Integer.parseInt(s3);
                CURRENTCUSTOMER = Manager.GETSPECIFICACCOUNTHOLDER(d);//to be implemented in Manager class
                return b1;
            } else {
                b1 = false;
                return b1;
            }
        } catch (Exception e) {// signifies if such file does not exist
            System.out.println("User file does not exist");
            b1 = false;
            return b1;
        }
    }

    public static boolean logOut() {
        //Requires: Nothing
        //Modifies: Nothing
        //Effects: If no customer is currently logged in the app, then return false,
        //         else return true
        if (CURRENTLYOPENFILENAME != null) {
            CURRENTLYOPENFILENAME = null;
            CURRENTCUSTOMER = null;
            return true;
        } else {
            return false;
        }
        
    }

    public boolean depositMoney(double amount) {
        //Requires: CURRENTLYOPENFILENAME != null && CURRENTCUSTOMER != null
        //Modifies: The customer object refered by CURRENTCUSTOMER, on which it is invoked, and the 
        //          specific user file of the CURRENTCUSTOMER called CURRENTLYOPENFILENAME.
        //Effects: If true, then it changes the balance info. of CURRENTCUSTOMER(which in turn of course might cause
        //          the state of the CURRENTCUSTOMER to change by an increase in account balance of CURRENTCUSTOMER). Moreover, this method 
        //          updates userfile of CURRENTCUSTOMER to store possible new state and balance info.
        //          If, false, it does nothing.
      
        if (amount <= 0) {
            return false;
        } else {
            this.account.setBalance(this.account.getBalance() + amount);// add code to check for negative amounts later (if time permits)
            this.changeState();
            //now need to update the user file
            try {
                FileOperations.editLineOfFile(3, "" + this.account.getBalance(), CURRENTLYOPENFILENAME);
                FileOperations.editLineOfFile(4, this.state.toString(), CURRENTLYOPENFILENAME);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }

    public boolean withdrawMoney(double amount) {
        //Requires: CURRENTLYOPENFILENAME != null && CURRENTCUSTOMER != null
        //Modifies: The customer object referenced by CURRENTCUSTOMER, on which it is invoked, and the 
        //          specific user file of the CURRENTCUSTOMER called CURRENTLYOPENFILENAME.
        //Effects: If true, then it changes the balance info. of CURRENTCUSTOMER(which in turn of course might cause
        //          the state of the CURRENTCUSTOMER to change by an decrease in account balance of CURRENTCUSTOMER). Moreover, this method 
        //          updates userfile of CURRENTCUSTOMER to store possible new state and balance info.
        //          If, false, it does nothing.
        
        if (amount > this.account.getBalance()) {
            return false;
        }
        if (amount < 0) {
            return false;
        } else {
            this.account.setBalance(this.account.getBalance() - amount);
            this.changeState();
            try {
                FileOperations.editLineOfFile(3, "" + this.account.getBalance(), CURRENTLYOPENFILENAME);
                FileOperations.editLineOfFile(4, this.state.toString(), CURRENTLYOPENFILENAME);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }

    public double getBalance() {
        //Requires: CURRENTCUSTOMER != null && CURRENTLYOPENFILENAME != null
        //Modifies: Nothing
        //Effects: Returns the current account balance of currently logged in Customer, namely CURRENTCUSTOMER
        return this.account.getBalance();
    }

    public boolean doOnlinePurchase(double price) {
        //Requires: CURRENTCUSTOMER != null && CURRENTLYOPENFILENAME != null
        //Modifies: The currently logged in Customer, namely CURRENTCUSTOMER.
        //Effects: Returns true if price >= 50 && total payable price(relevant account fees + price) does not
        //         exceed the currently logged in Customer's account balance.
        //         Else return false
        if (price < 50) {
            return false;
        } else {
            double fee = this.state.calculateFee(this);
            double f = fee + price;
            return withdrawMoney(f);
        }
    }

    public void changeState() {
        //Requires: GETCURRENTCUSTOMER != null && CURRENTLYOPENFILENAME != null
        //Modifies: CURRENTCUSTOMER,CURRENTLYOPENFILENAME,master log file, and specific user file
        //Effects: Initiates the change of level or state of the currently logged in Customer, namely CURRENTCUSTOMER (actual state or level
        //         change is done by the concrete state class). 
        
        this.state.changeState(this);
    }

    public boolean setUserName(String ID) {
        //Requires: CURRENTCUSTOMER != null && CURRENTLYOPENFILENAME != null && ID != null
        //Modifies: CURRENTLYOPENFILENAME, specific userfile and master log file
        //Effects: This method changes the userid stored in the master "log.txt" and updates the userid stored 
        //          in the specific user file.Returns true if this is successfully done
        //         Returns false, if userid ID is already taken by an existing user
        try {
            // Basically renames the current USER file, updates the log file, updates username line of the USER file.
            if (!FileOperations.varifyExistanceOfCertainLine(ID, "log")) {//insert a method that scans through each line of the log file to find conflicting userID
                FileOperations.editLineOfFile(0, ID, CURRENTLYOPENFILENAME);
                File f1 = new File(CURRENTLYOPENFILENAME + ".txt");
                f1.createNewFile();
                File f2 = new File(ID + ".txt");
                
                f1.renameTo(f2);
                // now must update the log file called "log.txt".
                FileOperations.deleteSpecificLine("log", CURRENTLYOPENFILENAME);
                FileOperations.write("log", ID);
                // now setting CURRENTLYOPENFILENAME to the new userID
                CURRENTLYOPENFILENAME = ID;
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            System.out.println("Exception occured in customer's setUserName(String ID) method");
            return false;
        }
    }

    public boolean setPassword(String p) {
        //Requires: CURRENTCUSTOMER != null && CURRENTLYOPENFILENAME != null && p != null
        //Modifies: specific userfile and master log file
        //Effects: This method changes the password stored in the master "log.txt" and updates the userid stored 
        //          in the specific user file.Returns true if this is successfully done
        //         Returns false, if any file handling error occurs
        try {
            // must first edit line of the user file
            FileOperations.editLineOfFile(1, p, CURRENTLYOPENFILENAME);
            return true;
        } catch (Exception ex) {
            System.out.println("Exception occured in customer's setPassword(String p) method");
            return false;
        }
    }

    public BankAccount getAccount() {
        //Requires: this.account != null
        //Modifies: nothing
        //Effects: Returns the BannkAccount variable of this
        return this.account;
    }

    public void setState(CustomerState s) {
        //Requires: this.state != null
        //Modifies: this
        //Effects: sets the invoking Customer this' state to state s
        this.state = s;
    }

    public CustomerState getState() {
        //Requires: this.state != null
        //Modifies: Nothing
        //Effects: Returns invoking Customer this' current state
        return this.state;
    }
    
    @Override
    public String toString(){
        // Requires: this != null or precisely, CURRENTCUSTOMER != null
        // Modifies: Nothing
        // Effects: Returns the String representtion of an abstract bank account holder user who has a cetain account level and 
        //          and certain bank account info, such as, first name, last name, balance and a distinct account number.
        //          This method implements the abatraction function
        String fName = this.getAccount().getFirstName();
        String lName = this.getAccount().getLastName();
        String level = this.getState().toString();
        String balance ="" + this.getAccount().getBalance();
        String acNum = "" + this.account.getAccountNumber();
        
        String fR = "First name: " + fName + ", last name: " + lName + ", account number: " + acNum + ", balance: "
                + balance + ", account level: " + level + ".";
        return fR;
        
    }
    
    public boolean repOk(){
        //Requires: nothing
        //Modifies: nothing
        //Effects: Returns false if any of the instance variable of this class is null
        //         Returns false if firstName or lastName is null
        //         Returns false if account balance is negative
        //         Returns true if and only if this customer instance does not have both first
        //         and last name, and bank account number common with another Customer.
        //         Implements the rep invariant function
        if ((this.account == null)||(this.account.getFirstName() == null) || (this.account.getLastName() == null) || (this.account.getBalance() < 0) || (this.state == null)){
            return false;
        }
        return (ValidateAccount(this.getAccount().getFirstName(), this.getAccount().getLastName(), this.getAccount().getAccountNumber()) != null);
    }
    
    private BankAccount ValidateAccount(String firstName, String lastName, int accountNumber) {
        //private helper method
        //Requires : (firstName && lastNAme) != null
        //Modifies : Nothing
        //Effects: If a certain Customer with same first and last name or same same account number returns null
        //         else, return true
        try {
            BufferedReader in = new BufferedReader(
                    new FileReader("log" + ".txt"));
            String j;
            while ((j = in.readLine()) != null) {
 
                if (((Integer.parseInt(FileOperations.readSpecificLine(j, 2))) == accountNumber) || (((FileOperations.readSpecificLine(j, 5)).equals(firstName)) && ((FileOperations.readSpecificLine(j, 6)).equals(lastName)))) {
                    in.close();
                    //System.out.println("An user with the specified account number already exists!!");//added for debugging
                    return null;
                }
            }
            in.close();//added for debugging
            //System.out.println("Account created!!");//added for debugging
            return new BankAccount(firstName, lastName, accountNumber);
            
        } catch (Exception e) {
            System.out.println("Error!!");
            return null;
        }
    }

}
