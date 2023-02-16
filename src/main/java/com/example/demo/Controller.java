package com.example.demo;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class Controller
{
    @FXML
    GridPane gridPane;
    @FXML
    private TextField textField;
    @FXML
    private RadioButton radioThick;
    @FXML
    private RadioButton radioThin;
    @FXML
    private ChoiceBox<String> toppingsChoiceBox;
    @FXML
    private Label deliveryInstruction;
    @FXML
    private Label deliveryTarget;
    @FXML
    private ListView<String> deliveryBranches;
    @FXML
    private Button submitButton;

    final private double THICK_COST = 1.0;
    final private double THIN_COST = 0.75;
    //These should be final, but I'm losing the will to live.
    private double peppersCost = 4.99;
    private double mushroomCost = 3.99;
    private double anchovyCost = 12.99;
    private double pepperoniCost = 5.99;

    private double crustPrice = 0;
    private double toppingPrice = 0;

    private String store;

    public void initialize()
    {
        gridPane.setPadding(new Insets(10));
        ArrayList<String> branches = getLocations();
        deliveryBranches.getItems().setAll(branches);
        deliveryBranches.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        toppingsChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1)
            {
                String topping = toppingsChoiceBox.getSelectionModel().getSelectedItem();
                toppingPrice = chargeTopping(topping);
            }

        });
//              THIS WORKS, BUT I DON'T THINK ITS THE PROPER WAY?????
//        deliveryBranches.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>()
//             {
//                 @Override
//                 public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
//                 {
//                     String item = deliveryBranches.getSelectionModel().getSelectedItem();
//                     changeDeliveryInstructions(item);
//                 }
//             }
//        );
        deliveryBranches.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>()
                 {
                     @Override
                     public void changed(ObservableValue<? extends String> observableValue, String s, String t1)
                     {
                         store = deliveryBranches.getSelectionModel().getSelectedItem();
                         changeDeliveryInstructions(store);
                     }
                 }
        );
    }

    private double chargeTopping(String topping)
    {
        switch (topping)
        {
            case ("Peppers") ->
            {
                toppingPrice = peppersCost;
                break;
            }
            case ("Mushrooms") ->
            {
                toppingPrice = mushroomCost;
                break;
            }
            case ("Anchovies") ->
            {
                toppingPrice = anchovyCost;
                break;
            }
            case ("Pepperoni") ->
            {
                toppingPrice = pepperoniCost;
                break;
            }
        }
        return toppingPrice;
    }

    private ArrayList<String> getLocations()
    {
        ArrayList<String> branches = new ArrayList<>();
        branches.add("Selly Oak");
        branches.add("Bournville");
        branches.add("Solihull");
        return branches;
    }

    public void changeDeliveryInstructions(String _location)
    {
        deliveryInstruction.setText("Pizza will be delivered from");
        deliveryTarget.setText(_location);
    }
    @FXML
    public void handleMouseClick(MouseEvent event)
    {
        //user submitted order
        //sout Name, order, price, and branch

        String name = textField.getText();
        double total = crustPrice + toppingPrice;
        System.out.printf("Hello, %s. Your order comes to %.2f, and will be delivered from %s.", name, total, store);
    }

    public void chooseCrust(ActionEvent event)
    {
        if(radioThick.isSelected())
        {
            crustPrice = THICK_COST;
        }
        else if(radioThin.isSelected())
        {
            crustPrice = THIN_COST;
        }
        else
        {
            System.out.println("Unhandled crust exception");
        }
    }
}