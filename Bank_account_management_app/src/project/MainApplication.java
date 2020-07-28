/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author XXXXX
 */
public class MainApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        PromptBox.display("Important!!", "Please refer to the included README file before creating or handling customer files", "OK");
        
        Manager.LOADCUSTOMERDATA();

        primaryStage.setTitle("Welcome !!");

        //GridPane with 10px padding around edge
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        //Name Label - constrains use (child, column, row)
        Label nameLabel = new Label("Username:");
        GridPane.setConstraints(nameLabel, 0, 0);

        //Name Input
        TextField nameInput = new TextField();
        GridPane.setConstraints(nameInput, 1, 0);

        //Password Label
        Label passLabel = new Label("Password:");
        GridPane.setConstraints(passLabel, 0, 1);

        //Password Input
        TextField passInput = new TextField();
        passInput.setPromptText("password");
        GridPane.setConstraints(passInput, 1, 1);

        //Login
        Button loginButton = new Button("Log In");
        GridPane.setConstraints(loginButton, 1, 2);

        //Choicebox
        ChoiceBox<String> choice = new ChoiceBox<String>();
        choice.getItems().addAll("Manager", "Customer");
        choice.setValue("Customer");
        GridPane.setConstraints(choice, 2, 1);

        //Add everything to grid (Prinary login scene)
        grid.getChildren().addAll(nameLabel, nameInput, passLabel, passInput, loginButton, choice);

        //setting up the login scene
        Scene loginScreen = new Scene(grid, 350, 200);

        // setting up pane of the manager's scene
        GridPane gridOfManager = new GridPane();
        gridOfManager.setPadding(new Insets(10, 10, 10, 10));
        gridOfManager.setVgap(8);
        gridOfManager.setHgap(10);
        //creating Manager's scene components

        Button addCustomer = new Button("Add customer");
        GridPane.setConstraints(addCustomer, 1, 1);//can be adjusted

        Button deleteCustomer = new Button("Delete customer");
        GridPane.setConstraints(deleteCustomer, 1, 3);//can be adjusted

        Button logOutAsManager = new Button("Log out");
        GridPane.setConstraints(logOutAsManager, 1, 5);//can be adjusted

        gridOfManager.getChildren().addAll(addCustomer, deleteCustomer, logOutAsManager);

        Scene managerScene = new Scene(gridOfManager, 350, 200);

        // Scene of the customer
        Button depositMoney = new Button("Deposit money");
        GridPane.setConstraints(depositMoney, 0, 0);// to be adjusted later

        Button withdrawMoney = new Button("Withdraw money");
        GridPane.setConstraints(withdrawMoney, 0, 2);// to be adjusted later

        Button getBalance = new Button("Get balance info.");
        GridPane.setConstraints(getBalance, 0, 4);// to be adjusted later

        Button doOnlinePurchases = new Button("Do online shoppings");
        GridPane.setConstraints(doOnlinePurchases, 0, 6);// to be adjusted later

        Button setUserName = new Button("Change username");
        GridPane.setConstraints(setUserName, 0, 8);// to be adjusted later

        Button setPassword = new Button("Change password");
        GridPane.setConstraints(setPassword, 0, 10);// to be adjusted later

        Button logOutAsCustomer = new Button("Log out");
        GridPane.setConstraints(logOutAsCustomer, 0, 12);// to be adjusted later

        //setting up customer's scene
        GridPane gridOfCustomer = new GridPane();
        gridOfCustomer.setPadding(new Insets(10, 10, 10, 10));//to be adjusted later
        gridOfCustomer.setVgap(8);//to be adjusted later
        gridOfCustomer.setHgap(10);//to be adjusted later

        gridOfCustomer.getChildren().addAll(depositMoney, withdrawMoney, getBalance, doOnlinePurchases, setUserName, setPassword, logOutAsCustomer);

        Scene CustomerScene = new Scene(gridOfCustomer, 450, 360);//second number is height!!

        // handler of login button 
        loginButton.addEventHandler(ActionEvent.ACTION,
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (choice.getValue().equals("Manager")) {
                    if (Manager.logIN(nameInput.getText(), passInput.getText())) {
                        nameInput.clear();
                        passInput.clear();
                        primaryStage.setScene(managerScene);
                        primaryStage.setTitle("Manager");
                    } else {
                        PromptBox.display("Error!!", "Wrong username or password", "OK");
                        nameInput.clear();
                        passInput.clear();
                    }
                } else {
                    if (Customer.logIN(nameInput.getText(), passInput.getText())) {
                        nameInput.clear();
                        passInput.clear();
                        primaryStage.setScene(CustomerScene);
                        primaryStage.setTitle("Welcome " + Customer.GETCURRENTCUSTOMER().getAccount().getFirstName() + "!!");
                    } else {
                        PromptBox.display("Error!!", "Wrong username or password", "OK");
                        nameInput.clear();
                        passInput.clear();
                    }
                }
            }
        }
        );

        //handler of managers logout
        logOutAsManager.addEventHandler(ActionEvent.ACTION,
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                primaryStage.setScene(loginScreen);
                primaryStage.setTitle("Welcome");
                Manager.logOut();
            }
        }
        );
        //handler of addCustomer
        addCustomer.addEventHandler(ActionEvent.ACTION,
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                CustomerCreationConfirmationBox.display();
            }
        }
        );

        // handler of deleteCustomer
        deleteCustomer.addEventHandler(ActionEvent.ACTION,
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Stage window = new Stage();

                window.initModality(Modality.APPLICATION_MODAL);
                window.setTitle("Delete customer");
                //window.setMinWidth(250);//commented for debug

                Label sceneTitle = new Label();
                sceneTitle.setText("Please enter the account number of the user you want to delete:");
                GridPane.setConstraints(sceneTitle, 0, 0);

                Label accountNumberLabel = new Label();
                accountNumberLabel.setText("Account number:");
                GridPane.setConstraints(accountNumberLabel, 0, 1);

                TextField numField = new TextField();
                GridPane.setConstraints(numField, 2, 1);

                Button OKButton = new Button("OK");
                GridPane.setConstraints(OKButton, 1, 3);

                GridPane grid = new GridPane();
                grid.setPadding(new Insets(10, 10, 10, 10));
                grid.setVgap(10);
                grid.setHgap(10);

                grid.getChildren().addAll(sceneTitle, accountNumberLabel, numField, OKButton);

                //handler of ok
                OKButton.addEventHandler(ActionEvent.ACTION,
                        new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        int num = Integer.parseInt(numField.getText());
                        if (Manager.GETCURRENTMANAGER().deleteCustomer(num)) {
                            PromptBox.display("Successful", "Specified user successfully deleted", "OK");
                            window.close();
                        } else {
                            PromptBox.display("Error!!", "Specified account number is not in system, can't be deleted", "OK");
                            numField.clear();
                        }
                    }
                }
                );

                Scene s = new Scene(grid);

                window.setScene(s);
                window.showAndWait();
            }
        }
        );

        //handlers of customer scene (login is same for both customer and manager)
        depositMoney.addEventHandler(ActionEvent.ACTION,
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Stage window = new Stage();

                window.initModality(Modality.APPLICATION_MODAL);
                window.setTitle("Deposit money");
                //window.setMinWidth(250);//commented for debug

                Label sceneTitle = new Label();
                sceneTitle.setText("Please enter the amount you would like to deposit:");
                GridPane.setConstraints(sceneTitle, 0, 0);

                Label amountOfMoney = new Label();
                amountOfMoney.setText("Amount:");
                GridPane.setConstraints(amountOfMoney, 0, 1);

                TextField amountField = new TextField();
                GridPane.setConstraints(amountField, 2, 1);

                Button OKButton = new Button("OK");
                GridPane.setConstraints(OKButton, 1, 3);

                GridPane grid = new GridPane();
                grid.setPadding(new Insets(10, 10, 10, 10));
                grid.setVgap(10);
                grid.setHgap(10);

                grid.getChildren().addAll(sceneTitle, amountOfMoney, amountField, OKButton);

                //handler of ok
                OKButton.addEventHandler(ActionEvent.ACTION,
                        new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        double num = Double.parseDouble(amountField.getText());
                        if (Customer.GETCURRENTCUSTOMER().depositMoney(num)) {
                            PromptBox.display("Successful", "Successful deposit!!", "OK");
                            window.close();
                        } else {
                            PromptBox.display("Error!!", "Specified amount is must be above $0!!", "OK");
                            amountField.clear();
                        }
                    }
                }
                );

                Scene s = new Scene(grid);
                window.setScene(s);
                window.showAndWait();
            }
        }
        );

        withdrawMoney.addEventHandler(ActionEvent.ACTION,
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Stage window = new Stage();

                window.initModality(Modality.APPLICATION_MODAL);
                window.setTitle("Withdraw money");
                //window.setMinWidth(250);//commented for debug

                Label sceneTitle = new Label();
                sceneTitle.setText("Please enter the amount you would like to withdraw:");
                GridPane.setConstraints(sceneTitle, 0, 0);

                Label amountOfMoney = new Label();
                amountOfMoney.setText("Amount:");
                GridPane.setConstraints(amountOfMoney, 0, 1);

                TextField amountField = new TextField();
                GridPane.setConstraints(amountField, 2, 1);

                Button OKButton = new Button("OK");
                GridPane.setConstraints(OKButton, 1, 3);

                GridPane grid = new GridPane();
                grid.setPadding(new Insets(10, 10, 10, 10));
                grid.setVgap(10);
                grid.setHgap(10);

                grid.getChildren().addAll(sceneTitle, amountOfMoney, amountField, OKButton);

                //handler of ok
                OKButton.addEventHandler(ActionEvent.ACTION,
                        new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        double num = Double.parseDouble(amountField.getText());
                        if (num <= 0) {
                            PromptBox.display("Error!!", "Specified amount is must be above $0!!", "OK");
                            amountField.clear();
                        } else {
                            if (Customer.GETCURRENTCUSTOMER().withdrawMoney(num)) {
                                PromptBox.display("Successful", "Successfuly withdrawn!!", "OK");
                                window.close();
                            } else {
                                PromptBox.display("Error!!", "You don't have sufficient funds!!", "OK");
                                window.close();
                            }
                        }
                    }
                }
                );
                
              

                Scene s = new Scene(grid);
                window.setScene(s);
                window.showAndWait();
            }
        }
        );
        
        getBalance.addEventHandler(ActionEvent.ACTION,
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String balance = ""+ Customer.GETCURRENTCUSTOMER().getBalance();
                String state = Customer.GETCURRENTCUSTOMER().getState().toString();
                PromptBox.display("Balance info.", "Current balance is: $" + balance + ", current level: " + state , "OK");
            }
        }
        );
        
        doOnlinePurchases.addEventHandler(ActionEvent.ACTION,
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Stage window = new Stage();

                window.initModality(Modality.APPLICATION_MODAL);
                window.setTitle("Do online purchase");
                window.setMinWidth(250);//commented for debug

                Label sceneTitle = new Label();
                sceneTitle.setText("Please enter the price of the product you want to purchase(must be at least $50):");
                GridPane.setConstraints(sceneTitle, 0, 0);

                Label amountOfMoney = new Label();
                amountOfMoney.setText("Price:");
                GridPane.setConstraints(amountOfMoney, 0, 1);

                TextField amountField = new TextField();
                GridPane.setConstraints(amountField, 2, 1);

                Button OKButton = new Button("OK");
                GridPane.setConstraints(OKButton, 1, 3);

                GridPane grid = new GridPane();
                grid.setPadding(new Insets(10, 10, 10, 10));
                grid.setVgap(10);
                grid.setHgap(10);

                grid.getChildren().addAll(sceneTitle, amountOfMoney, amountField, OKButton);

                //handler of ok
                OKButton.addEventHandler(ActionEvent.ACTION,
                        new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        double num = Double.parseDouble(amountField.getText());
                        if (num <= 0) {
                            PromptBox.display("Error!!", "Specified amount is must be at least $50!!", "OK");
                            amountField.clear();
                        } else {
                            if (Customer.GETCURRENTCUSTOMER().doOnlinePurchase(num)) {
                                PromptBox.display("Successful", "Successfuly purchased!!", "OK");
                                window.close();
                            } else {
                                PromptBox.display("Error!!", "You don't have sufficient funds!!", "OK");
                                window.close();
                            }
                        }
                    }
                }
                );
                
              

                Scene s = new Scene(grid);
                window.setScene(s);
                window.showAndWait();
            }
        }
        );
        
        setUserName.addEventHandler(ActionEvent.ACTION,
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Stage window = new Stage();

                window.initModality(Modality.APPLICATION_MODAL);
                window.setTitle("Set userID");
                window.setMinWidth(250);//commented for debug

                Label sceneTitle = new Label();
                sceneTitle.setText("Please enter the desired userID: ");
                GridPane.setConstraints(sceneTitle, 0, 0);

                Label amountOfMoney = new Label();
                amountOfMoney.setText("UserID:");
                GridPane.setConstraints(amountOfMoney, 0, 1);

                TextField amountField = new TextField();
                GridPane.setConstraints(amountField, 2, 1);

                Button OKButton = new Button("OK");
                GridPane.setConstraints(OKButton, 1, 3);

                GridPane grid = new GridPane();
                grid.setPadding(new Insets(10, 10, 10, 10));
                grid.setVgap(10);
                grid.setHgap(10);

                grid.getChildren().addAll(sceneTitle, amountOfMoney, amountField, OKButton);

                //handler of ok
                OKButton.addEventHandler(ActionEvent.ACTION,
                        new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        String sa = amountField.getText();
                        if(Customer.GETCURRENTCUSTOMER().setUserName(sa)){
                            PromptBox.display("Success!!", "Successfully updated your userID!!", "OK");
                            window.close();
                        }
                        else{
                            PromptBox.display("Already taken!!", "Specified userID already taken by another user!!", "OK");
                            amountField.clear();
                        }
                    }
                }
                );
                
              

                Scene s = new Scene(grid);
                window.setScene(s);
                window.showAndWait();
            }
        }
        );
        
        setPassword.addEventHandler(ActionEvent.ACTION,
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Stage window = new Stage();

                window.initModality(Modality.APPLICATION_MODAL);
                window.setTitle("Set password");
                window.setMinWidth(250);//commented for debug

                Label sceneTitle = new Label();
                sceneTitle.setText("Please enter the desired password: ");
                GridPane.setConstraints(sceneTitle, 0, 0);

                Label amountOfMoney = new Label();
                amountOfMoney.setText("password:");
                GridPane.setConstraints(amountOfMoney, 0, 1);

                TextField amountField = new TextField();
                GridPane.setConstraints(amountField, 2, 1);

                Button OKButton = new Button("OK");
                GridPane.setConstraints(OKButton, 1, 3);

                GridPane grid = new GridPane();
                grid.setPadding(new Insets(10, 10, 10, 10));
                grid.setVgap(10);
                grid.setHgap(10);

                grid.getChildren().addAll(sceneTitle, amountOfMoney, amountField, OKButton);

                //handler of ok
                OKButton.addEventHandler(ActionEvent.ACTION,
                        new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        String sa = amountField.getText();
                        if(Customer.GETCURRENTCUSTOMER().setPassword(sa)){
                            PromptBox.display("Success!!", "Successfully updated your password!!", "OK");
                            window.close();
                        }
                        else{
                            PromptBox.display("Error!!", "An error occured, try again with a different password!!", "OK");
                            amountField.clear();
                        }
                    }
                }
                );
                
              

                Scene s = new Scene(grid);
                window.setScene(s);
                window.showAndWait();
            }
        }
        );
        
        logOutAsCustomer.addEventHandler(ActionEvent.ACTION,
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                primaryStage.setScene(loginScreen);
                primaryStage.setTitle("Welcome");
                Customer.logOut();
            }
        }
        );
        
        

        primaryStage.setScene(loginScreen);
        primaryStage.show();

    }

}
