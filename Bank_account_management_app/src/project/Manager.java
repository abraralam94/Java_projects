/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author XXXXX
 */
public class Manager {

    private static ArrayList<Customer> listOfCustomer = new ArrayList<Customer>();
    private static Manager CURRENTMANAGER = null;

    public static Manager GETCURRENTMANAGER() {
        return CURRENTMANAGER;
    }

    public static Customer GETSPECIFICACCOUNTHOLDER(int accountNum) {// this method is also used in Manager's delete customer method
        if (listOfCustomer.size() == 0) {
            return null;
        } else {
            for (Customer c : listOfCustomer) {
                if (c.getAccount().getAccountNumber() == accountNum) {
                    return c;
                }
            }
            return null;
        }
    }

    public static boolean logIN(String userName, String passWord) {
        if ((userName.equals("admin")) && (passWord.equals("admin"))) {
            CURRENTMANAGER = new Manager();
            return true;
        } else {
            System.out.println("manager's username or password is wrong!!");
            return false;
        }
    }

    public static boolean logOut() {
        if (CURRENTMANAGER != null) {
            CURRENTMANAGER = null;
            return true;
        } else {
            return false;
        }
    }

    private BankAccount createAccount(String firstName, String lastName, int accountNumber) {
        try {
            BufferedReader in = new BufferedReader(
                    new FileReader("log" + ".txt"));
            String j;
            while ((j = in.readLine()) != null) {
                //System.out.println(j);
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
            System.out.println("Error in Mananger's createAccount method");
            return null;
        }
    }

    public boolean addCustomer(String firstName, String lastName, int accountNumber) {
        if (this.createAccount(firstName, lastName, accountNumber) != null) {
            Manager.listOfCustomer.add(new Customer(this.createAccount(firstName, lastName, accountNumber)));
            
            try {
               
               /* FileOperations.write(firstName + lastName, firstName + lastName);//default username
                FileOperations.write(firstName + lastName, "1234");//default password
                FileOperations.write(firstName + lastName, "" + accountNumber);
                FileOperations.write(firstName + lastName, "" + 100.0);
                FileOperations.write(firstName + lastName, "silver");
                FileOperations.write(firstName + lastName, firstName);
                FileOperations.write(firstName + lastName, lastName);
                FileOperations.write("log", firstName + lastName);// saving the default username in the log file*/
                BufferedWriter b1 = new BufferedWriter(new FileWriter(firstName+lastName+".txt", true));
                b1.write(firstName+lastName + "\n");
                b1.close();
                
                BufferedWriter b2 = new BufferedWriter(new FileWriter(firstName+lastName+".txt", true));
                b2.write(""+ (firstName.hashCode()+ lastName.hashCode()) +"\n");
                b2.close();
                
                BufferedWriter b3 = new BufferedWriter(new FileWriter(firstName+lastName+".txt", true));
                b3.write("" + accountNumber + "\n");
                b3.close();
                
                BufferedWriter b4 = new BufferedWriter(new FileWriter(firstName+lastName+".txt", true));
                b4.write("" + 100.0 + "\n");
                b4.close();
                
                
                
                BufferedWriter b5 = new BufferedWriter(new FileWriter(firstName+lastName+".txt", true));
                b5.write("" + "silver" + "\n");
                b5.close();
                
                BufferedWriter b6 = new BufferedWriter(new FileWriter(firstName+lastName+".txt", true));
                b6.write("" + firstName + "\n");
                b6.close();
                
                BufferedWriter b7 = new BufferedWriter(new FileWriter(firstName+lastName+".txt", true));
                b7.write("" + lastName + "\n");
                b7.close();
                
                BufferedWriter b8 = new BufferedWriter(new FileWriter("log.txt", true));
                b8.write(firstName + lastName + "\n");
                b8.close();
                
                System.out.println("Customer with that account added!!");//added for debugging
         
                return true;
            } catch (Exception e) {
                System.out.println("Error in addCustomer method of Manager");
                return false;
            }
        } else {
            System.out.println("an acoount with the given info. already exists!!");
            return false;
            
        }
    }

    public boolean deleteCustomer(int accountNumber) {
       // boolean b = false;
        try {
            BufferedReader in = new BufferedReader(new FileReader("log.txt"));//based on the assumption that log file is already there
            String j;
            while ((j = in.readLine()) != null) {
                // System.out.println(j);
                if (Integer.parseInt(FileOperations.readSpecificLine(j, 2)) == accountNumber) {
                    in.close();
                    File f1 = new File(j + ".txt");
                    f1.createNewFile();//this is working
                    f1.delete();//working as well
                    FileOperations.deleteSpecificLine("log", j);
                    for (Customer v : listOfCustomer) {
                        if (v.getAccount().getAccountNumber() == accountNumber) {
                            listOfCustomer.remove(v);
                            System.out.println("Customer and associated account successfully deleted");
                            return true;
                        }
                    }
                
                    return true;
                }  
            }
            in.close();
            System.out.println("an account with that number does not exist!! Cant be deleted");
            return false;
        } catch (Exception e) {
            System.out.println("Error in the deleteAccount method of manager!!");
            return false;
        }
    }

    public static boolean LOADCUSTOMERDATA() {
       
        File f = new File("log.txt");
        try {
            if (f.createNewFile()) {
                System.out.println("log file created for the first time by the LOADERFUNCTION");
                return true;
            } else {
                BufferedReader in = new BufferedReader(new FileReader("log.txt"));
                String j;
                while ((j = in.readLine()) != null) {
                    String accountNum = FileOperations.readSpecificLine(j, 2);
                    String balance = FileOperations.readSpecificLine(j, 3);
                    String state = FileOperations.readSpecificLine(j, 4);
                    String FirstName = FileOperations.readSpecificLine(j, 5);
                    String LastName = FileOperations.readSpecificLine(j, 6);
                    if (state.equals("silver")) {
                        int accountNumber = Integer.parseInt(accountNum);
                        double bL = Double.parseDouble(balance);
                        BankAccount a = new BankAccount(FirstName, LastName, accountNumber, bL);
                        CustomerState s = new Silver();
                        Manager.listOfCustomer.add(new Customer(a, s));
                    } else if (state.equals("gold")) {
                        int accountNumber = Integer.parseInt(accountNum);
                        double bL = Double.parseDouble(balance);
                        BankAccount a = new BankAccount(FirstName, LastName, accountNumber, bL);
                        CustomerState s = new Gold();
                        Manager.listOfCustomer.add(new Customer(a, s));
                    } else if (state.equals("platinum")) {
                        int accountNumber = Integer.parseInt(accountNum);
                        double bL = Double.parseDouble(balance);
                        BankAccount a = new BankAccount(FirstName, LastName, accountNumber, bL);
                        CustomerState s = new Platinum();
                        Manager.listOfCustomer.add(new Customer(a, s));
                    }
                }
                in.close();
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error in the loaderfunction in Manager");
            return false;
        }
    }

}
