/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

/**
 *
 * @author XXXXX
 */
public class CustomerCreationConfirmationBox {

    public static void display() {
        Stage window = new Stage();

        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Create Account");
        window.setMinWidth(250);

        Label sceneTitle = new Label();
        sceneTitle.setText("A bank account with relevant info. must be created first");
        GridPane.setConstraints(sceneTitle, 0, 0);

        Label firstNameLabel = new Label();
        firstNameLabel.setText("First name:");
        GridPane.setConstraints(firstNameLabel, 1, 1);

        TextField firstNameField = new TextField();
        GridPane.setConstraints(firstNameField, 2, 1);

        Label lastNameLabel = new Label();
        lastNameLabel.setText("Last name:");
        GridPane.setConstraints(lastNameLabel, 1, 2);

        TextField lastNameField = new TextField();
        GridPane.setConstraints(lastNameField, 2, 2);

        Label accountNumberLabel = new Label();
        accountNumberLabel.setText("Account number:");
        GridPane.setConstraints(accountNumberLabel, 1, 3);

        TextField accountNumberField = new TextField();
        GridPane.setConstraints(accountNumberField, 2, 3);

        Button OKButton = new Button("OK");
        GridPane.setConstraints(OKButton, 4, 5);

        /*closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);*/
        //Display window and wait for it to be closed before returning
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        grid.getChildren().addAll(sceneTitle, firstNameLabel, firstNameField, lastNameLabel, lastNameField, accountNumberLabel, accountNumberField, OKButton);

        Scene scene = new Scene(grid);

        //okButton handling
        OKButton.addEventHandler(ActionEvent.ACTION,
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if ((firstNameField.getText().length() == 0) || (lastNameField.getText().length() == 0) || (accountNumberField.getText().length() == 0)) {
                    PromptBox.display("Invalid input", "None of the fields can be empty", "OK");
                } else {
                    String fName = firstNameField.getText();
                    String lName = lastNameField.getText();
                    int aNumber = Integer.parseInt(accountNumberField.getText());
                    if (Manager.GETCURRENTMANAGER().addCustomer(fName, lName, aNumber)) {
                        PromptBox.display("User created!!", "User created!!" + " Default username is: " + firstNameField.getText()+lastNameField.getText()+ " & default password: " + ((firstNameField.getText().hashCode())+(lastNameField.getText().hashCode())), "OK");
                        firstNameField.clear();
                        lastNameField.clear();
                        accountNumberField.clear();
                        window.close();
                    }
                    else{
                        PromptBox.display("already exists!!", "No two users can have the same account num or First and last names!!", "OK");
                        firstNameField.clear();
                        lastNameField.clear();
                        accountNumberField.clear();
                    }
                    /*System.out.println(firstNameField.getText().length());//for debugging
                    System.out.println(lastNameField.getText().length());//for debugging
                    System.out.println(accountNumberField.getText().length());//for debugging
                    System.out.println(""+Integer.parseInt(accountNumberField.getText()));//for debugging*/
                }
            }
        }
        );

        window.setScene(scene);
        window.showAndWait();
    }

}
