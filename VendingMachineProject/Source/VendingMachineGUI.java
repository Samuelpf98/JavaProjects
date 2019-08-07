import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Scanner;
import javafx.application.*;
import javafx.scene.*;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.collections.*;
import javafx.scene.layout.*;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.effect.DropShadow;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class VendingMachineGUI extends Application {
    
    VendingMachine machine = new VendingMachine();
    private String selection;
    private String input = "";
    
    public static void main(String[] args)
            throws IOException{ 
       VendingMachine machine = new VendingMachine();
       Scanner in = new Scanner(System.in);
       while (true){       
           System.out.println("CLI(0) or GUI(1):");
           String input = in.next();
           if(input.equals("1")){
               VendingMachineGUI.launch(args);
               System.exit(0);
           }
           else if(input.equals("0")){
               machine.commandLineAuthentication();
               System.exit(0);
           } 
       }
   }
	
    @Override
    public void start(Stage primaryStage) throws Exception{

        Label Products = new Label("Items:");
        Label Codes = new Label("Item Codes:");
        Label Prices = new Label("Item Prices:");
        Label Left = new Label("Items Remaining:");
        Label Inserted = new Label("Current Funds:");
        ObservableList<String> ItemDescription = FXCollections.<String>observableArrayList();
        ObservableList<String> ItemPrice = FXCollections.<String>observableArrayList();
        ObservableList<String> ItemCode = FXCollections.<String>observableArrayList();
        ObservableList<String> ItemQuantity = FXCollections.<String>observableArrayList();
        ObservableList<String> CurrentAmount = FXCollections.<String>observableArrayList();
        ObservableList<String> Space = FXCollections.<String>observableArrayList();
        for (int i = 0; i < machine.getProducts().size(); i++){
            if(!ItemDescription.contains(machine.getProducts().get(i).getDesc())){
                ItemDescription.add(machine.getProducts().get(i).getDesc());
                ItemPrice.add("Euro " + String.valueOf(machine.getProducts().get(i).getPrice()));
                ItemCode.add(machine.getProducts().get(i).getCode());
                int quantity = 0;
                for(int j = 0; j < machine.getProducts().size(); j++){
                    if(machine.getProducts().get(i).getDesc().equals(machine.getProducts().get(j).getDesc())){
                        quantity++;
                    }
                }
                ItemQuantity.add(String.valueOf(quantity));
            }
        }
        CurrentAmount.add("Euro " + String.valueOf(machine.getCurrentCoins().getValue()));
        ListView<String> Desc = new ListView<>(ItemDescription);
        ListView<String> Code = new ListView<>(ItemCode);
        ListView<String> Quantity = new ListView<>(ItemQuantity);
        ListView<String> Price = new ListView<>(ItemPrice);
        ListView<String> Amount = new ListView<>(CurrentAmount);
        ListView<String> space1 = new ListView<>(Space);
        ListView<String> space2 = new ListView<>(Space);
        Desc.setOrientation(Orientation.VERTICAL);
        Code.setOrientation(Orientation.VERTICAL);
        Quantity.setOrientation(Orientation.VERTICAL);
        Price.setOrientation(Orientation.VERTICAL);
		
        primaryStage.getIcons().add(new Image("Vending_Icon.png"));

        DropShadow shadow = new DropShadow();

        Button InsertCoin = new Button("Insert Coins");
        Button Buy = new Button("Buy");
        Button RemoveCoins = new Button("Remove Coins");
        Button OperatorsBt = new Button("Operator Login");
        Button ExitVendingMachine = new Button("Exit");

        InsertCoin.setFont(Font.font("Open Sans",FontWeight.BOLD, 17));
        Buy.setFont(Font.font("Open Sans",FontWeight.BOLD, 17));
        RemoveCoins.setFont(Font.font("Open Sans", FontWeight.BOLD, 17));
        OperatorsBt.setFont(Font.font("Open Sans", FontWeight.BOLD, 17));
        ExitVendingMachine.setFont(Font.font("Open Sans", FontWeight.BOLD, 17));

        InsertCoin.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            InsertCoin.setEffect(shadow);
        });
        InsertCoin.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    InsertCoin.setEffect(null);
        });
        Buy.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    Buy.setEffect(shadow);
        });
        Buy.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    Buy.setEffect(null);
        });	
        RemoveCoins.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    RemoveCoins.setEffect(shadow);
        });
        RemoveCoins.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    RemoveCoins.setEffect(null);
        });	
        OperatorsBt.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    OperatorsBt.setEffect(shadow);
        });
        OperatorsBt.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    OperatorsBt.setEffect(null);
        });
        ExitVendingMachine.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    ExitVendingMachine.setEffect(shadow);
        });
        ExitVendingMachine.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    ExitVendingMachine.setEffect(null);
        });
		
	InsertCoin.setStyle("-fx-background-color: CHARTREUSE;");
        Buy.setStyle("-fx-background-color: CHARTREUSE;");
        RemoveCoins.setStyle("-fx-background-color: CYAN;");
        OperatorsBt.setStyle("-fx-background-color: CYAN;");
        ExitVendingMachine.setStyle("-fx-background-color: RED;");

        InsertCoin.setPrefWidth(300);
        Buy.setPrefWidth(300);
        RemoveCoins.setPrefWidth(300);
        OperatorsBt.setPrefWidth(300);
        ExitVendingMachine.setPrefWidth(300);

        InsertCoin.setPrefHeight(60);
        Buy.setPrefHeight(60);
        RemoveCoins.setPrefHeight(60);
        OperatorsBt.setPrefHeight(60);

        OperatorsBt.setOnAction((ActionEvent event) -> {
            keyPad("o", primaryStage, "Enter Passcode: ");
        });
        Buy.setOnAction((ActionEvent event) -> {
            keyPad("b", primaryStage, "Enter Product Code: ");
        });
        InsertCoin.setOnAction((ActionEvent event) -> {
            Coin newCoin = new Coin();
            chooseCoin(machine, primaryStage);
        });
        RemoveCoins.setOnAction((ActionEvent event) -> {
            try {
                removeCoinsQuery(machine.getCurrentCoins(), "Remove Coins: Are you sure?", "coins.txt", primaryStage, "user");
            } catch (IOException ex) {
                machine.display("Warning: IO Error");
            }
        });
        ExitVendingMachine.setOnAction((ActionEvent event) -> {
            machine.exitQuery();
        });

        VBox a = new VBox();
        a.getChildren().addAll(Products, Desc);
        a.setPrefWidth(100);
        VBox b = new VBox();
        b.getChildren().addAll(Codes, Code);
        b.setPrefWidth(100);
        VBox c = new VBox();
        c.getChildren().addAll(Left, Quantity);
        c.setPrefWidth(100);
        VBox d = new VBox();
        d.getChildren().addAll(Prices, Price);
        d.setPrefWidth(100);
        VBox e = new VBox();
        e.getChildren().addAll(Inserted, Amount);
        e.setPrefWidth(100);
        e.setPrefHeight(5);
        VBox f = new VBox();
        f.getChildren().addAll(space1);
        f.setPrefWidth(100);
        f.setPrefHeight(5);
        VBox g = new VBox();
        g.getChildren().addAll(space2);
        g.setPrefWidth(100);
        g.setPrefHeight(5);
        
        VBox Buttons1 = new VBox();
        Buttons1.setSpacing(10);
        Buttons1.getChildren().addAll(InsertCoin, RemoveCoins);
        Buttons1.setAlignment(Pos.CENTER);
        VBox Buttons2 = new VBox();
        Buttons2.setSpacing(10);
        Buttons2.getChildren().addAll(Buy, OperatorsBt);
        Buttons2.setAlignment(Pos.CENTER);
        HBox Buttons3 = new HBox();
        Buttons3.setSpacing(10);
        Buttons3.getChildren().addAll(Buttons1, Buttons2);
        Buttons3.setAlignment(Pos.CENTER);
        VBox Buttons = new VBox();
        Buttons.setSpacing(10);
        Buttons.getChildren().addAll(Buttons3, ExitVendingMachine);
        Buttons.setAlignment(Pos.CENTER);

        GridPane Gpane = new GridPane();
        Gpane.setPadding(new Insets(10, 10, 10, 10));
        Gpane.setHgap(10);
        Gpane.setVgap(10);
        Gpane.addColumn(0, a);
        Gpane.addColumn(1, b);
        Gpane.addColumn(2, d);
        Gpane.addColumn(3, c);
        Gpane.addRow(4, Buttons);
        Gpane.addRow(4, f);
        Gpane.addRow(4, e);
        Gpane.addRow(4, g);
        Gpane.setStyle("-fx-padding: 10;" +
                        "-fx-border-style: solid inside;" +
                        "-fx-border-width: 6;" +
                        "-fx-border-insets: 0;" +
                        "-fx-border-radius: 6;" +
                        "-fx-border-color: slategrey");

        Scene scene = new Scene(Gpane);
        primaryStage.setTitle("Vending Machine - User");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public void operatorMenu(Stage primaryStage) {
        
        this.selection = "";
        
        Label Products = new Label("Items:");
        Label Codes = new Label("Item Codes:");
        Label Prices = new Label("Item Prices:");
        Label Left = new Label("Items Remaining:");
        Label Actions = new Label("All Actions: ");
        ObservableList<String> ItemDescription = FXCollections.<String>observableArrayList();
        ObservableList<String> ItemPrice = FXCollections.<String>observableArrayList();
        ObservableList<String> ItemCode = FXCollections.<String>observableArrayList();
        ObservableList<String> ItemQuantity = FXCollections.<String>observableArrayList();
        for (int i = 0; i < machine.getProducts().size(); i++){
            if(!ItemDescription.contains(machine.getProducts().get(i).getDesc())){
                ItemDescription.add(machine.getProducts().get(i).getDesc());
                ItemPrice.add("Euro " + String.valueOf(machine.getProducts().get(i).getPrice()));
                ItemCode.add(machine.getProducts().get(i).getCode());
                int quantity = 0;
                for(int j = 0; j < machine.getProducts().size(); j++){
                    if(machine.getProducts().get(i).getDesc().equals(machine.getProducts().get(j).getDesc())){
                        quantity++;
                    }
                }
                ItemQuantity.add(String.valueOf(quantity));
            }
        }
        ListView<String> Desc = new ListView<>(ItemDescription);
        ListView<String> Code = new ListView<>(ItemCode);
        ListView<String> Quantity = new ListView<>(ItemQuantity);
        ListView<String> Price = new ListView<>(ItemPrice);
        Desc.setOrientation(Orientation.VERTICAL);
        Code.setOrientation(Orientation.VERTICAL);
        Quantity.setOrientation(Orientation.VERTICAL);
        Price.setOrientation(Orientation.VERTICAL);
        
        primaryStage.getIcons().add(new Image("Vending_Icon.png"));
        	
        Button AddProduct = new Button("Add Product");
        Button RemoveAllCoins = new Button("Remove All Coins");
        Button AddOperator = new Button("Add Operator");
        Button ExitVendingMachine = new Button("Exit");
        Button UserMode = new Button("Return To User Mode");
        
        AddProduct.setOnAction((ActionEvent event) -> {
            createProduct(machine, primaryStage);
        });
        RemoveAllCoins.setOnAction((ActionEvent event) -> {
            try {
                removeCoinsQuery(machine.getCoins(), "Remove All Coins: Are you sure?", "allCoins.txt", primaryStage, "operator");
            } catch (IOException ex) {
                machine.display("Warning: IO Error");
            }
        });
        AddOperator.setOnAction((ActionEvent event) -> {
            keyPad("a",primaryStage, "Enter New Passcode: ");
        });
        UserMode.setOnAction((ActionEvent event) -> {
            userMenu(primaryStage);
        });
        ExitVendingMachine.setOnAction((ActionEvent event) -> {
            machine.exitQuery();
        });
        
        AddProduct.setPrefWidth(200);
        RemoveAllCoins.setPrefWidth(200);
        UserMode.setPrefWidth(200);
        ExitVendingMachine.setPrefWidth(200);
        AddOperator.setPrefWidth(200);
        AddProduct.setPrefHeight(35);
        RemoveAllCoins.setPrefHeight(35);
        UserMode.setPrefHeight(35);
        ExitVendingMachine.setPrefHeight(35);
        AddOperator.setPrefHeight(35);

        VBox a = new VBox();
        a.getChildren().addAll(Products, Desc);
        a.setPrefWidth(100);
        VBox b = new VBox();
        b.getChildren().addAll(Codes, Code);
        b.setPrefWidth(100);
        VBox c = new VBox();
        c.getChildren().addAll(Left, Quantity);
        c.setPrefWidth(100);
        VBox d = new VBox();
        d.getChildren().addAll(Prices, Price);
        d.setPrefWidth(100);
        VBox Buttons = new VBox();
        Buttons.setSpacing(5);
        Buttons.getChildren().addAll(Actions, AddProduct, RemoveAllCoins, AddOperator, UserMode, ExitVendingMachine);
        Buttons.setAlignment(Pos.CENTER);

        GridPane Gpane = new GridPane();
        Gpane.setHgap(10);
        Gpane.setVgap(5);
        Gpane.addColumn(0, a);
        Gpane.addColumn(1, d);
        Gpane.addColumn(2, b);
        Gpane.addColumn(3, c);
        Gpane.addColumn(4, Buttons);

        Gpane.setStyle("-fx-padding: 10;" +
                        "-fx-border-style: solid inside;" +
                        "-fx-border-width: 6;" +
                        "-fx-border-insets: 6;" +
                        "-fx-border-radius: 6;" +
                        "-fx-border-color: red;");

        Scene scene = new Scene(Gpane);

        primaryStage.setTitle("Vending Machine - Operator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public void keyPad(String function, Stage stg, String lbl){
        this.selection = "";
        Label inputLabel = new Label("");
        Label label = new Label(lbl);
        
        Button Number1Bt = new Button("1");
        Button Number2Bt = new Button("2");
        Button Number3Bt = new Button("3");
        Button Number4Bt = new Button("4");
        Button Number5Bt = new Button("5");
        Button Number6Bt = new Button("6");
        Button Number7Bt = new Button("7");
        Button Number8Bt = new Button("8");
        Button Number9Bt = new Button("9");
        Button Number0Bt = new Button("0");
        Button delBt = new Button("CLR");
        Button okBt = new Button("OK");
		
        Number1Bt.setPrefWidth(50);
        Number2Bt.setPrefWidth(50);
        Number3Bt.setPrefWidth(50);
        Number4Bt.setPrefWidth(50);
        Number5Bt.setPrefWidth(50);
        Number6Bt.setPrefWidth(50);
        Number7Bt.setPrefWidth(50);
        Number8Bt.setPrefWidth(50);
        Number9Bt.setPrefWidth(50);
        Number0Bt.setPrefWidth(50);
        delBt.setPrefWidth(50);
        okBt.setPrefWidth(50);

        DropShadow shadow = new DropShadow();

        HBox Numbersr0 = new HBox();
        Numbersr0.setSpacing(10);
        Numbersr0.getChildren().addAll(label);
        HBox Numbersr1 = new HBox();
        Numbersr1.setSpacing(10);
        Numbersr1.getChildren().addAll(Number1Bt, Number2Bt, Number3Bt);
        HBox Numbersr2 = new HBox();
        Numbersr2.setSpacing(10);
        Numbersr2.getChildren().addAll(Number4Bt, Number5Bt, Number6Bt);
        HBox Numbersr3 = new HBox();
        Numbersr3.setSpacing(10);
        Numbersr3.getChildren().addAll(Number7Bt, Number8Bt, Number9Bt);
        HBox Numbersr4 = new HBox();
        Numbersr4.setSpacing(10);
        Numbersr4.getChildren().addAll(Number0Bt);
        HBox Numbersr5 = new HBox();
        Numbersr5.setSpacing(10);
        Numbersr5.getChildren().addAll(okBt,delBt);
        HBox Screen = new HBox();
        Screen.setSpacing(10);
        Screen.getChildren().addAll(inputLabel);

        Numbersr5.setAlignment(Pos.CENTER_RIGHT);
        Numbersr4.setAlignment(Pos.CENTER);

        GridPane pane2 = new GridPane();
        pane2.setHgap(10);
        pane2.setVgap(5);
        pane2.addColumn(0, Screen);
        pane2.addColumn(0, Numbersr0);
        pane2.addColumn(0, Numbersr1);
        pane2.addColumn(0, Numbersr2);
        pane2.addColumn(0, Numbersr3);
        pane2.addColumn(0, Numbersr4);
        pane2.addColumn(0, Numbersr5);
        pane2.setStyle("-fx-padding: 10;" +
                        "-fx-border-style: solid inside;" +
                        "-fx-border-width: 6;" +
                        "-fx-border-insets: 6;" +
                        "-fx-border-radius: 6;" +
                        "-fx-border-color: SLATEGREY;");

        Scene scene2 = new Scene(pane2);
        Stage stage = new Stage();
        stage.getIcons().add(new Image("Vending_Icon.png"));
        stage.setTitle("Keypad");
        stage.setScene(scene2);
        stage.show();
		
        Number1Bt.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    Number1Bt.setEffect(shadow);
        });
        Number1Bt.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    Number1Bt.setEffect(null);
        });
        Number2Bt.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    Number2Bt.setEffect(shadow);
        });
        Number2Bt.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    Number2Bt.setEffect(null);
        });
        Number3Bt.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    Number3Bt.setEffect(shadow);
        });
        Number3Bt.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    Number3Bt.setEffect(null);
        });
        Number4Bt.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    Number4Bt.setEffect(shadow);
        });
        Number4Bt.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    Number4Bt.setEffect(null);
        });
        Number5Bt.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    Number5Bt.setEffect(shadow);
        });
        Number5Bt.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    Number5Bt.setEffect(null);
        });
        Number6Bt.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    Number6Bt.setEffect(shadow);
        });
        Number6Bt.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    Number6Bt.setEffect(null);
        });
        Number7Bt.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    Number7Bt.setEffect(shadow);
        });
        Number7Bt.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    Number7Bt.setEffect(null);
        });
        Number8Bt.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    Number8Bt.setEffect(shadow);
        });
        Number8Bt.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    Number8Bt.setEffect(null);
        });
        Number9Bt.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    Number9Bt.setEffect(shadow);
        });
        Number9Bt.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    Number9Bt.setEffect(null);
        });
        Number0Bt.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    Number0Bt.setEffect(shadow);
        });
        Number0Bt.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    Number0Bt.setEffect(null);
        });
        delBt.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    delBt.setEffect(shadow);
        });
        delBt.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    delBt.setEffect(null);
        });
        okBt.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    okBt.setEffect(shadow);
        });
        okBt.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    okBt.setEffect(null);
        });
        
        Number1Bt.setOnAction((ActionEvent event) -> {
                    updateSelection("1");
                    this.input += 1;
                    inputLabel.setText(this.input);
        });
        Number2Bt.setOnAction((ActionEvent event) -> {
                    updateSelection("2");
                    this.input += 2;
                    inputLabel.setText(this.input);
        });
        Number3Bt.setOnAction((ActionEvent event) -> {
                    updateSelection("3");
                    this.input += 3;
                    inputLabel.setText(this.input);
        });
        Number4Bt.setOnAction((ActionEvent event) -> {
                    updateSelection("4");
                    this.input += 4;
                    inputLabel.setText(this.input);
        });
        Number5Bt.setOnAction((ActionEvent event) -> {
                    updateSelection("5");
                    this.input += 5;
                    inputLabel.setText(this.input);
        });
        Number6Bt.setOnAction((ActionEvent event) -> {
                    updateSelection("6");
                    this.input += 6;
                    inputLabel.setText(this.input);
        });
        Number7Bt.setOnAction((ActionEvent event) -> {
                    updateSelection("7");
                    this.input += 7;
                    inputLabel.setText(this.input);
        });
        Number8Bt.setOnAction((ActionEvent event) -> {
                    updateSelection("8");
                    this.input += 8;
                    inputLabel.setText(this.input);
        });
        Number9Bt.setOnAction((ActionEvent event) -> {
                    updateSelection("9");
                    this.input += 9;
                    inputLabel.setText(this.input);
        });
        Number0Bt.setOnAction((ActionEvent event) -> {
                    updateSelection("0");
                    this.input += 0;
                    inputLabel.setText(this.input);
        });
        delBt.setOnAction((ActionEvent event) -> {
                    this.selection = "";
                    this.input = "";
                    inputLabel.setText(this.input);
        });
        okBt.setOnAction((ActionEvent event) -> {switch (function) {
            case "b":
                this.machine.buyProduct(this.selection);
                stage.close();
                userMenu(stg);
                stg.toBack();
                break;
            case "o":
                if(this.machine.operatorAuthentication(this.selection)){
                    operatorMenu(stg);
                }
                else{
                    machine.display("Invalid Operator Passcode");
                }
                stage.close();
                break;
            default:
                this.machine.getOperators().add(new VendingOperator(String.valueOf(this.machine.getOperators().size()), this.selection));
                this.machine.operatorsToFile();
                stage.close();
                break;
            }
        });

   }
     
    public String updateSelection(String next){
        if (selection.length() >= 4){
            return selection;
        }
        return selection += next;
    }
    
    public void userMenu(Stage primaryStage) {
        
        this.selection = "";

        Label Products = new Label("Items:");
        Label Codes = new Label("Item Codes:");
        Label Prices = new Label("Item Prices:");
        Label Left = new Label("Items Remaining:");
        Label Inserted = new Label("Current Funds:");
        ObservableList<String> ItemDescription = FXCollections.<String>observableArrayList();
        ObservableList<String> ItemPrice = FXCollections.<String>observableArrayList();
        ObservableList<String> ItemCode = FXCollections.<String>observableArrayList();
        ObservableList<String> ItemQuantity = FXCollections.<String>observableArrayList();
        ObservableList<String> CurrentAmount = FXCollections.<String>observableArrayList();
        ObservableList<String> Space = FXCollections.<String>observableArrayList();
        for (int i = 0; i < machine.getProducts().size(); i++){
            if(!ItemDescription.contains(machine.getProducts().get(i).getDesc())){
                ItemDescription.add(machine.getProducts().get(i).getDesc());
                ItemPrice.add("Euro " + String.valueOf(machine.getProducts().get(i).getPrice()));
                ItemCode.add(machine.getProducts().get(i).getCode());
                int quantity = 0;
                for(int j = 0; j < machine.getProducts().size(); j++){
                    if(machine.getProducts().get(i).getDesc().equals(machine.getProducts().get(j).getDesc())){
                        quantity++;
                    }
                }
                ItemQuantity.add(String.valueOf(quantity));
            }
        }
        CurrentAmount.add("Euro " + String.valueOf(machine.getCurrentCoins().getValue()));
        ListView<String> Desc = new ListView<>(ItemDescription);
        ListView<String> Code = new ListView<>(ItemCode);
        ListView<String> Quantity = new ListView<>(ItemQuantity);
        ListView<String> Price = new ListView<>(ItemPrice);
        ListView<String> Amount = new ListView<>(CurrentAmount);
        ListView<String> space1 = new ListView<>(Space);
        ListView<String> space2 = new ListView<>(Space);
        Desc.setOrientation(Orientation.VERTICAL);
        Code.setOrientation(Orientation.VERTICAL);
        Quantity.setOrientation(Orientation.VERTICAL);
        Price.setOrientation(Orientation.VERTICAL);
		
        primaryStage.getIcons().add(new Image("Vending_Icon.png"));

        DropShadow shadow = new DropShadow();

        Button InsertCoin = new Button("Insert Coins");
        Button Buy = new Button("Buy");
        Button RemoveCoins = new Button("Remove Coins");
        Button OperatorsBt = new Button("Operator Login");
        Button ExitVendingMachine = new Button("Exit");

        InsertCoin.setFont(Font.font("Open Sans",FontWeight.BOLD, 17));
        Buy.setFont(Font.font("Open Sans",FontWeight.BOLD, 17));
        RemoveCoins.setFont(Font.font("Open Sans", FontWeight.BOLD, 17));
        OperatorsBt.setFont(Font.font("Open Sans", FontWeight.BOLD, 17));
        ExitVendingMachine.setFont(Font.font("Open Sans", FontWeight.BOLD, 17));

        InsertCoin.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            InsertCoin.setEffect(shadow);
        });
        InsertCoin.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    InsertCoin.setEffect(null);
        });
        Buy.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    Buy.setEffect(shadow);
        });
        Buy.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    Buy.setEffect(null);
        });	
        RemoveCoins.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    RemoveCoins.setEffect(shadow);
        });
        RemoveCoins.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    RemoveCoins.setEffect(null);
        });	
        OperatorsBt.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    OperatorsBt.setEffect(shadow);
        });
        OperatorsBt.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    OperatorsBt.setEffect(null);
        });
        ExitVendingMachine.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    ExitVendingMachine.setEffect(shadow);
        });
        ExitVendingMachine.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    ExitVendingMachine.setEffect(null);
        });
		
	InsertCoin.setStyle("-fx-background-color: CHARTREUSE;");
        Buy.setStyle("-fx-background-color: CHARTREUSE;");
        RemoveCoins.setStyle("-fx-background-color: CYAN;");
        OperatorsBt.setStyle("-fx-background-color: CYAN;");
        ExitVendingMachine.setStyle("-fx-background-color: RED;");

        InsertCoin.setPrefWidth(300);
        Buy.setPrefWidth(300);
        RemoveCoins.setPrefWidth(300);
        OperatorsBt.setPrefWidth(300);
        ExitVendingMachine.setPrefWidth(300);

        InsertCoin.setPrefHeight(60);
        Buy.setPrefHeight(60);
        RemoveCoins.setPrefHeight(60);
        OperatorsBt.setPrefHeight(60);

        OperatorsBt.setOnAction((ActionEvent event) -> {
            keyPad("o", primaryStage, "Enter Passcode: ");
        });
        Buy.setOnAction((ActionEvent event) -> {
            keyPad("b", primaryStage, "Enter Product Code");
        });
        InsertCoin.setOnAction((ActionEvent event) -> {
            Coin newCoin = new Coin();
            chooseCoin(machine, primaryStage);
        });
        RemoveCoins.setOnAction((ActionEvent event) -> {
            try {
                removeCoinsQuery(machine.getCurrentCoins(), "Remove Coins: Are you sure?", "coins.txt", primaryStage, "user");
            } catch (IOException ex) {
                machine.display("Warning: IO Error");
            }
        });
        ExitVendingMachine.setOnAction((ActionEvent event) -> {
            machine.exitQuery();
        });

        VBox a = new VBox();
        a.getChildren().addAll(Products, Desc);
        a.setPrefWidth(100);
        VBox b = new VBox();
        b.getChildren().addAll(Codes, Code);
        b.setPrefWidth(100);
        VBox c = new VBox();
        c.getChildren().addAll(Left, Quantity);
        c.setPrefWidth(100);
        VBox d = new VBox();
        d.getChildren().addAll(Prices, Price);
        d.setPrefWidth(100);
        VBox e = new VBox();
        e.getChildren().addAll(Inserted, Amount);
        e.setPrefWidth(100);
        e.setPrefHeight(5);
        VBox f = new VBox();
        f.getChildren().addAll(space1);
        f.setPrefWidth(100);
        f.setPrefHeight(5);
        VBox g = new VBox();
        g.getChildren().addAll(space2);
        g.setPrefWidth(100);
        g.setPrefHeight(5);
        
        VBox Buttons1 = new VBox();
        Buttons1.setSpacing(10);
        Buttons1.getChildren().addAll(InsertCoin, RemoveCoins);
        Buttons1.setAlignment(Pos.CENTER);
        VBox Buttons2 = new VBox();
        Buttons2.setSpacing(10);
        Buttons2.getChildren().addAll(Buy, OperatorsBt);
        Buttons2.setAlignment(Pos.CENTER);
        HBox Buttons3 = new HBox();
        Buttons3.setSpacing(10);
        Buttons3.getChildren().addAll(Buttons1, Buttons2);
        Buttons3.setAlignment(Pos.CENTER);
        VBox Buttons = new VBox();
        Buttons.setSpacing(10);
        Buttons.getChildren().addAll(Buttons3, ExitVendingMachine);
        Buttons.setAlignment(Pos.CENTER);

        GridPane Gpane = new GridPane();
        Gpane.setPadding(new Insets(10, 10, 10, 10));
        Gpane.setHgap(10);
        Gpane.setVgap(10);
        Gpane.addColumn(0, a);
        Gpane.addColumn(1, b);
        Gpane.addColumn(2, d);
        Gpane.addColumn(3, c);
        Gpane.addRow(4, Buttons);
        Gpane.addRow(4, f);
        Gpane.addRow(4, e);
        Gpane.addRow(4, g);
        Gpane.setStyle("-fx-padding: 10;" +
                        "-fx-border-style: solid inside;" +
                        "-fx-border-width: 6;" +
                        "-fx-border-insets: 0;" +
                        "-fx-border-radius: 6;" +
                        "-fx-border-color: slategrey;");

        Scene scene = new Scene(Gpane);
        primaryStage.setTitle("Vending Machine - User");
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }
    
    public void chooseCoin(VendingMachine Vmachine, Stage stg){

        ImageView image1 = new ImageView(new Image("5_Cent.png"));  
        image1.setFitHeight(75); 
        image1.setFitWidth(75);
        image1.setPreserveRatio(true);  
        ImageView image2 = new ImageView(new Image("10_Cent.png"));  
        image2.setFitHeight(75); 
        image2.setFitWidth(75);
        image2.setPreserveRatio(true);
        ImageView image3 = new ImageView(new Image("50_Cent.png"));  
        image3.setFitHeight(75); 
        image3.setFitWidth(75);
        image3.setPreserveRatio(true);
        ImageView image4 = new ImageView(new Image("1_euro.png"));  
        image4.setFitHeight(75); 
        image4.setFitWidth(75);
        image4.setPreserveRatio(true);
        ImageView image5 = new ImageView(new Image("2_euro.png"));  
        image5.setFitHeight(75); 
        image5.setFitWidth(75);
        image5.setPreserveRatio(true);
        ImageView image6 = new ImageView(new Image("20_Cent.png"));  
        image6.setFitHeight(75); 
        image6.setFitWidth(75);
        image6.setPreserveRatio(true);
        
        Button fiveCBt = new Button("", image1);
        Button tenCBt = new Button("", image2);
        Button fiftyCBt = new Button("", image3);
        Button oneEBt = new Button("", image4);
        Button twoEBT = new Button("", image5);
        Button twentyCBT = new Button("", image6);
		
        fiveCBt.setPrefWidth(150);
        tenCBt.setPrefWidth(150);
        fiftyCBt.setPrefWidth(150);
        oneEBt.setPrefWidth(150);
        twoEBT.setPrefWidth(150);
        twentyCBT.setPrefWidth(150);

        DropShadow shadow = new DropShadow();
		
        fiveCBt.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                fiveCBt.setEffect(shadow);
        });
        fiveCBt.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                fiveCBt.setEffect(null);
        });
        tenCBt.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                tenCBt.setEffect(shadow);
        });
        tenCBt.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                tenCBt.setEffect(null);
        });
        fiftyCBt.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                fiftyCBt.setEffect(shadow);
        });
        fiftyCBt.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                fiftyCBt.setEffect(null);
        });
        oneEBt.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                oneEBt.setEffect(shadow);
        });
        oneEBt.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                oneEBt.setEffect(null);
        });
        fiveCBt.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                fiveCBt.setEffect(shadow);
        });
        fiveCBt.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                fiveCBt.setEffect(null);
        });
        twoEBT.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                twoEBT.setEffect(shadow);
        });
        twoEBT.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                twoEBT.setEffect(null);
        });
        twentyCBT.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                twentyCBT.setEffect(shadow);
        });

        twentyCBT.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                twentyCBT.setEffect(null);
        });

        HBox r1 = new HBox();
        r1.setSpacing(10);
        r1.getChildren().addAll(fiveCBt, tenCBt, twentyCBT);
        HBox r2 = new HBox();
        r2.setSpacing(10);
        r2.getChildren().addAll(fiftyCBt, oneEBt, twoEBT);
        r1.setAlignment(Pos.CENTER);

        GridPane pane2 = new GridPane();
        pane2.setHgap(10);
        pane2.setVgap(5);
        pane2.addColumn(0, r1);
        pane2.addColumn(0, r2);
        pane2.setStyle("-fx-padding: 10;" +
                        "-fx-border-style: solid inside;" +
                        "-fx-border-width: 6;" +
                        "-fx-border-insets: 6;" +
                        "-fx-border-radius: 6;" +
                        "-fx-border-color: SLATEGREY;");


        Scene scene2 = new Scene(pane2);
        Stage stage = new Stage();
        stage.getIcons().add(new Image("Vending_Icon.png"));
        stage.setTitle("Coin Insert");
        stage.setScene(scene2);
        stage.show();

        fiveCBt.setOnAction((ActionEvent event) -> {Vmachine.addCoin(new Coin(0.05, "5 cent"), Vmachine.getCurrentCoins(), "coins.txt"); stage.close(); userMenu(stg);});
        tenCBt.setOnAction((ActionEvent event) -> {Vmachine.addCoin(new Coin(0.10, "10 cent"), Vmachine.getCurrentCoins(), "coins.txt"); stage.close(); userMenu(stg);});
        twentyCBT.setOnAction((ActionEvent event) -> {Vmachine.addCoin(new Coin(0.2, "20 cent"), Vmachine.getCurrentCoins(), "coins.txt"); stage.close(); userMenu(stg);});
        fiftyCBt.setOnAction((ActionEvent event) -> {Vmachine.addCoin(new Coin(0.50, "50 cent"), Vmachine.getCurrentCoins(), "coins.txt"); stage.close(); userMenu(stg);});
        oneEBt.setOnAction((ActionEvent event) -> {Vmachine.addCoin(new Coin(1.0, "1 euro"), Vmachine.getCurrentCoins(), "coins.txt"); stage.close(); userMenu(stg);});
        twoEBT.setOnAction((ActionEvent event) -> {Vmachine.addCoin(new Coin(2.0, "2 euro"), Vmachine.getCurrentCoins(), "coins.txt"); stage.close(); userMenu(stg);});
        
    } 

    public void createProduct(VendingMachine Vmachine, Stage stg){
        
        Stage window = new Stage();
        window.setTitle("Add Product");
        window.getIcons().add(new Image("Vending_Icon.png"));
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);
        grid.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 6;" +
                "-fx-border-insets: 6;" +
                "-fx-border-radius: 6;" +
                "-fx-border-color: SLATEGREY;");

        Button add = new Button("Add");
        grid.setAlignment(Pos.CENTER);
        GridPane.setConstraints(add, 1, 4);

        Label descL = new Label("Description");
        GridPane.setConstraints(descL, 0, 0);

        TextField descI = new TextField();
        descI.setPromptText("description");
        GridPane.setConstraints(descI, 1, 0);

        Label priceL = new Label("Price");
        GridPane.setConstraints(priceL, 0, 1);

        TextField priceI = new TextField();
        priceI.setPromptText("price");
        GridPane.setConstraints(priceI, 1, 1);

        Label codeL = new Label("Code");
        GridPane.setConstraints(codeL, 0, 2);

        TextField codeI = new TextField();
        codeI.setPromptText("code");
        GridPane.setConstraints(codeI, 1, 2);

        Label quanL = new Label("Quantity");
        GridPane.setConstraints(quanL, 0, 3);

        TextField quanI = new TextField();
        quanI.setPromptText("quantity");
        GridPane.setConstraints(quanI, 1, 3);

        grid.getChildren().addAll(descL, descI, priceL, priceI, codeL, codeI, quanL, quanI, add);

        Scene scene = new Scene(grid);
        window.setScene(scene);
        window.show();

        add.setOnAction((ActionEvent event) -> {Vmachine.addProduct(new Product(descI.getText(), Double.parseDouble(priceI.getText()), codeI.getText()),  Integer.parseInt(quanI.getText())); window.close(); operatorMenu(stg);});

}

    public void removeCoinsQuery(CoinSet coinset, String message, String file, Stage stg, String mode) throws IOException{

        Label label = new Label(message);

        Button yesBt = new Button("Yes");
        Button noBt = new Button("No");
        yesBt.setStyle("-fx-background-color: CHARTREUSE;");
        noBt.setStyle("-fx-background-color: RED;");

        DropShadow shadow = new DropShadow();
		 
        yesBt.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                     yesBt.setEffect(shadow);
        });
        yesBt.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    yesBt.setEffect(null);
        });
        noBt.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    noBt.setEffect(shadow);
        });
        noBt.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    noBt.setEffect(null);
        });

        HBox r1 = new HBox();
        r1.setSpacing(10);
        r1.getChildren().addAll(label);
        HBox r2 = new HBox();
        r2.setSpacing(10);
        r2.getChildren().addAll(yesBt, noBt);
        r2.setAlignment(Pos.CENTER);

        GridPane pane2 = new GridPane();
        pane2.setHgap(10);
        pane2.setVgap(5);
        pane2.addColumn(0, r1);
        pane2.addColumn(0, r2);
        pane2.setStyle("-fx-padding: 10;" +
                        "-fx-border-style: solid inside;" +
                        "-fx-border-width: 6;" +
                        "-fx-border-insets: 6;" +
                        "-fx-border-radius: 6;" +
                        "-fx-border-color: RED;");

        Scene scene2 = new Scene(pane2);
        Stage stage = new Stage();
        stage.getIcons().add(new Image("Vending_Icon.png"));
        stage.setTitle("Removal");
        stage.setScene(scene2);
        stage.show();
        
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_EVEN);

        yesBt.setOnAction((ActionEvent event) -> { machine.display("Returned:\tEuro " + df.format(machine.removeMoney(coinset, file))); 
                                                   stage.close();
                                                   if("user".equals(mode)){
                                                       userMenu(stg);
                                                       stg.toBack();
                                                   }
                                                   else{
                                                       operatorMenu(stg);
                                                       stg.toBack();
                                                   }
                                                   });
        noBt.setOnAction((ActionEvent event) -> {stage.close();});

    }    

}
