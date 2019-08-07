import java.io.*;
import java.util.*;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;


class VendingMachine{
    private ArrayList<Product> products;
    public CoinSet coins;
    public CoinSet currentCoins;
    private ArrayList<VendingOperator> operators = new ArrayList<VendingOperator>();
	

    public VendingMachine() { 
       products = new ArrayList<Product>();
       coins = new CoinSet();
       currentCoins = new CoinSet();
       this.readVendingState();
    }
    
    public CoinSet getCurrentCoins(){
        return this.currentCoins;
    }
    
    public CoinSet getCoins(){
        return this.coins;
    }

    public Product[] getProductTypes(){
        Product[] p = {};
        ArrayList<Product> pt = new ArrayList<Product>();
        for(int i = 0; i < this.products.size(); i++){
            if(!(pt.contains(this.products.get(i)))){
                pt.add(this.products.get(i));
            }
        }
        return pt.toArray(p);
    }

    public void addCoin(Coin chosenCoin,CoinSet coinset, String file){
        coinset.newCoin(chosenCoin);
        this.coinsToFile(coinset, file);
    }

    public double removeMoney(CoinSet coinset, String file){
        double removed = coinset.getValue();
        coinset.remove();
        this.coinsToFile(coinset, file);
        return removed;
    }

    public double buyProduct(Product p) throws VendingException {
        if (p.getPrice() <= this.currentCoins.getValue()){
            CoinSet clear = new CoinSet();
            double change = this.currentCoins.getValue() - p.getPrice();
            this.transaction(change);
            this.removeMoney(this.currentCoins, "coins.txt");
            this.products.remove(p);
            this.coinsToFile(clear, "allCoins.txt");
            this.coinsToFile(this.coins, "allCoins.txt");
            this.coinsToFile(this.currentCoins, "coins.txt");
            this.productToFile(this.products);
            return change;
        }
        else {
            throw new VendingException("Insufficient funds");
        }
    }

    public void buyProduct(String code) throws VendingException {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_EVEN);
        for(int i = 0; i < this.products.size(); i++){
            if(this.products.get(i).getCode().equals(code)){
                if (this.products.get(i).getPrice() <= this.currentCoins.getValue()){
                    try{
                        CoinSet clear = new CoinSet();
                        double change = this.currentCoins.getValue() - this.products.get(i).getPrice();
                        this.transaction(change);
                        this.removeMoney(this.currentCoins, "coins.txt");
                        String info = "Purchased:\t" + this.products.get(i).getDesc() + " (€" + this.products.get(i).getPrice() + ")\nReturned:\t\t€" + df.format(change);
                        this.products.remove(i);
                        this.coinsToFile(clear, "allCoins.txt");
                        this.coinsToFile(this.coins, "allCoins.txt");
                        this.coinsToFile(this.currentCoins, "coins.txt");
                        this.productToFile(this.products);
                        this.display(info);
                        break;
                    }
                    catch(VendingException e){
                        this.display("Insufficient funds");
                    }
                }
            }
        }
    }


    public void addProduct(Product p, int i){
        for (int j = 0; j < i; j++){
            products.add(p);
        }
        this.productToFile(this.products);
    }

    public ArrayList<Product> getProducts(){
        return this.products;
    }

    public void display(String l){
        Label label = new Label(l);
        Button okBt = new Button("OK");

        HBox r1 = new HBox();
        r1.setSpacing(10);
        r1.getChildren().addAll(label);
        HBox r2 = new HBox();
        r2.setSpacing(10);
        r2.getChildren().addAll(okBt);
        
        DropShadow shadow = new DropShadow();
		 
        okBt.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                     okBt.setEffect(shadow);
        });
        okBt.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    okBt.setEffect(null);
        });

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
                        "-fx-border-color: yellow;");

        Scene scene = new Scene(pane2);
        Stage stage = new Stage();
        stage.getIcons().add(new Image("Vending_Icon.png"));
        stage.setTitle("Information");
        stage.setScene(scene);
        stage.show();

        okBt.setOnAction((ActionEvent event) -> {stage.close();});
     }

    public void overwriteFile(String fileName, ArrayList<String> content) throws IOException {
        FileWriter newFileWriter = new FileWriter(new File(fileName));
        PrintWriter newPrintWriter = new PrintWriter(newFileWriter);
        for (int i = 0; i < content.size(); i++) {
            newPrintWriter.print( content.get( i ) + ",");
        }
        newPrintWriter.close();
        newFileWriter.close();
     }  
    
    public void coinsToFile( CoinSet coinsInMachine, String file ) {
        ArrayList< String > toFile = new ArrayList< String >();
        for( int i = 0; i < coinsInMachine.getCoins().size(); i++ ){
                toFile.add("\n" + coinsInMachine.getCoins().get( i ).toString() + "," + coinsInMachine.getCoins().get( i ).getCoinValue() );
        }
        try {
            this.overwriteFile( file, toFile );
        } catch (IOException ex) {
        }
    }
    
    public void operatorsToFile() {
        ArrayList< String > toFile = new ArrayList< String >();
        for( int i = 0; i < this.operators.size(); i++ ){
                toFile.add("\n" + this.operators.get( i ).getName() + "," + this.operators.get( i ).getPassword() );
        }
        try {
            this.overwriteFile( "operators.txt", toFile );
        } catch (IOException ex) {
        }
    }
    
    public ArrayList<String> fileRead (String fileName) throws FileNotFoundException {
	ArrayList<String> fileArrayList = new ArrayList<String>();
	Scanner newScanner = new Scanner(new File(fileName));
	String line;
	while(newScanner.hasNext()){
            line = newScanner.nextLine();
            fileArrayList.addAll(Arrays.asList(line.split(",")));
	}
	newScanner.close();
	return fileArrayList;
    }
    
    public boolean checkCode(String code, int length){
        Character[] nums = {'0','1','2','3','4','5','6','7','8','9'};
        ArrayList<Character> digits = new ArrayList<Character>(Arrays.asList(nums));
        if (code.length() != length){
            return true;
        }
        for(int i = 0; i < code.length(); i++){
            if(!(digits.contains(code.charAt(i)))){
                return true;
            }
        }
        for(int i = 0; i < this.products.size(); i++){
            if(code.equals(this.products.get(i).getCode())){
                return true;
            }
        }
        return false;
    }
    
    public void readVendingState() {
        ArrayList<String> productsIn = new ArrayList<String>();
        try {
            productsIn = fileRead("stock.txt");
        } catch (FileNotFoundException ex) {
            this.display("Warning: Missing File");
        }
        for( int i = 1; i < productsIn.size(); i = i + 4 ){
            String description = productsIn.get( i );
            double price = Double.parseDouble( productsIn.get( i + 1 ) );
            int quantity = Integer.parseInt( productsIn.get( i + 2 ) );
            String code = productsIn.get( i + 3 );
            this.addProduct(new Product(description, price, code), quantity);
        }
        ArrayList<String> coinsIn = new ArrayList<String>();
        try {
            coinsIn = fileRead("coins.txt");
        } catch (FileNotFoundException ex) {
            this.display("Warning: Missing File");
        }
        for( int i = 1; i < coinsIn.size(); i = i + 2 ){
            String name = coinsIn.get( i );
            double value = Double.parseDouble( coinsIn.get( i + 1 ) );
            this.addCoin( new Coin( value, name ), this.currentCoins, "coins.txt" );
        }
        ArrayList<String> allCoinsIn = new ArrayList<String>();
        try {
            allCoinsIn = fileRead("allCoins.txt");
        } catch (FileNotFoundException ex) {
            this.display("Warning: Missing File");
        }
        for( int i = 1; i < allCoinsIn.size(); i = i + 2 ){
            String name = allCoinsIn.get( i );
            double value = Double.parseDouble( allCoinsIn.get( i + 1 ) );
            this.addCoin( new Coin( value, name ), this.coins, "allCoins.txt" );
        }
        ArrayList<String> operatorsIn = new ArrayList<String>();
        try {
            operatorsIn = fileRead("operators.txt");
        } catch (FileNotFoundException ex) {
            this.display("Warning: Missing File");
        }
        for(int i = 1; i < operatorsIn.size(); i=i+2)
        {
            this.operators.add(new VendingOperator(operatorsIn.get(i), operatorsIn.get(i+1)));
        }
    }
    
    public void productToFile(ArrayList<Product> stuff){
        ArrayList<String> toFile = new ArrayList<String>();
        int counter = 1;
        int j = 0;
        for(int i = 1; i < stuff.size(); i++){
            j = i;
            while(j < stuff.size()){
                if(stuff.get(j -1).getDesc().equals(stuff.get(j).getDesc())){
                        counter++;
                        j++;
                        i = j;
                } 
                else {
                        break;
                }
            }
            toFile.add("\n" + stuff.get(i-1).getDesc() + "," + stuff.get(i-1).getPrice() + "," + counter + "," + stuff.get(i-1).getCode());
            counter = 1;
        }
        try {
            this.overwriteFile( "stock.txt", toFile );
        } catch (IOException ex) {
        }
        
    }
    
    public void commandLineAuthentication() throws IOException {
                VendingMachineMenu ui = new VendingMachineMenu();
		Scanner scanner = new Scanner(System.in);
		System.out.println("User(0), Operator(1):");
		int userType = scanner.nextInt();
		if(userType == 1)
		{
			System.out.print("Password: ");
			String password = scanner.next();
			ArrayList<String> info = fileRead("operators.txt");
			ArrayList<VendingOperator> operators = new ArrayList<VendingOperator>();
			for(int i = 1; i < info.size(); i=i+2)
			{
				operators.add(new VendingOperator(info.get(i), info.get(i+1)));
			}
			String aPass = "";
			boolean validPass = false;
			int counter = 1;
			for(int i = 0; i < operators.size() && counter <= 3; i++)
			{
				for(int j = 0; j < operators.size() && !validPass; j++)
				{
					aPass = operators.get(j).getPassword();				
					if(password.equals(aPass))
					{
						validPass = true;
						System.out.println("Welcome Operator " + operators.get(j).getName());
						ui.run(this, userType);
						break;				
					} 
				}
				if(validPass == false)
				{
				counter++;
				System.out.print("Retry Password: ");
				password = scanner.next();	
				}
			}
			if(counter > 3)
			{
			scanner.close();
			System.out.println("Multiple Incorrect Entries: Entering User Mode");
			}
		}
		if(userType == 0) {
			ui.run(this, userType);
		}
	}
    
    public void transaction(Double change){
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_EVEN);
        for (int i = 0; i < this.currentCoins.getCoins().size(); i++){
            this.coins.newCoin(currentCoins.getCoins().get(i));
        }
        for (double j = change; j > 0.00;){
            if (this.coins.getValue() < j){
            ArrayList<String> bufferIn = new ArrayList<String>();
            try {
                bufferIn = fileRead("changeBuffer.txt");
            } catch (FileNotFoundException ex) {
                this.display("Warning: Missing File");
            }
                for( int i = 1; i < bufferIn.size(); i = i + 2 ){
                    String name = bufferIn.get( i );
                    double value = Double.parseDouble( bufferIn.get( i + 1 ) );
                    this.addCoin( new Coin( value, name ), this.coins, "allCoins.txt" );
                }
        }
            for (int k = 0; k < this.coins.getCoins().size(); k++){
                j = Double.parseDouble(df.format(j));
                if(this.coins.getCoins().get(k).getCoinValue() <= j && !(j - this.coins.getCoins().get(k).getCoinValue() < 0.00)){
                    j -= this.coins.getCoins().get(k).getCoinValue();
                    this.coins.getCoins().remove(k);
                }
            }
        }
    }
    
    public void exitQuery(){
        Label label = new Label("Are you sure?");

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
                        "-fx-border-color: red;");

        Scene scene2 = new Scene(pane2);
        Stage stage = new Stage();
        stage.setTitle("Exit");
        stage.getIcons().add(new Image("Vending_Icon.png"));
        stage.setScene(scene2);
        stage.show();

        yesBt.setOnAction((ActionEvent event) -> {
            this.productToFile(products);
            this.coinsToFile(coins, "allCoins.txt");
            this.coinsToFile(currentCoins, "coins.txt");
            System.exit(0);});
        noBt.setOnAction((ActionEvent event) -> {stage.close();});

   }
    
    public boolean operatorAuthentication(String password){
		boolean validPass = false;
		int counter = 1;
		for(int i = 0; i < operators.size() && counter <= 3; i++)
		{
			for(int j = 0; j < operators.size() && !validPass; j++)
			{				
				if(operators.get(j).getPassword().equals(password))
				{
					validPass = true;
					return validPass;
				} 
			}
		}
		return validPass;
	}
    
    public ArrayList<VendingOperator> getOperators(){
        return this.operators;
    }
    
}



    
