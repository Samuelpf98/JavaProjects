
import java.util.Scanner;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.InputMismatchException;

public class VendingMachineMenu
{    
   private Scanner in = new Scanner(System.in);
   private static Coin[] coins = { new Coin(0.05, "5 cent"),
                                   new Coin(0.1, "10 cent"),
                                   new Coin(0.2, "20 cent"),
                                   new Coin(0.5, "50 cent"),
                                   new Coin(1, "1 euro"),
                                   new Coin(2, "2 euro")};

   public VendingMachineMenu()
   {
       
   }
   
    public void run(VendingMachine machine, int mode)
         throws IOException
   {
      boolean more = true;
      DecimalFormat df = new DecimalFormat("#.##");
      df.setRoundingMode(RoundingMode.HALF_EVEN);
      
      while (more)
      { 
        if (mode == 0){
            System.out.println("S)how products  I)nsert coin  B)uy  R)emove coins  Q)uit");
        }
        if (mode == 1){
            System.out.println("S)how products  A)dd product  R)emove all coins C)reate new operator  Q)uit");
        }
        
          String command = in.next().toUpperCase();
        
        if (mode == 0){
            switch (command) {
                case "I":
                    machine.addCoin((Coin) getChoice(coins), machine.getCurrentCoins(), "coins.txt");
                    break;
                case "R":
                    System.out.println("Removed: €" + machine.removeMoney(machine.getCurrentCoins(), "coins.txt"));
                    break;
                case "B":
                    try
                    {
                        Product p = (Product) getChoice(machine.getProductTypes());
                        double change = machine.buyProduct(p);
                        System.out.println("Purchased:\te" + p.getDesc() + " (" + p.getPrice() + ")\nReturned:\te" + df.format(change));
                    }
                    catch (VendingException ex)
                    {
                        System.out.println(ex.getMessage());
                    }   break;
                default:
                    break;
            }
        }
        else if (mode == 1){
            if (command.equals("A")){  
                boolean item = true;
                String description = "";
                System.out.println("Description:");
                description = in.next();
                for(int i = 0; i < machine.getProducts().size(); i++){
                    if(machine.getProducts().get(i).getDesc().equals(description)){
                        System.out.println("Item Already Stocked");
                        item = false;
                        break;
                    }
                }
                if (item == false){
                    continue;
                }
                System.out.println("Price:");
                double price = 0.0;
                try{
                    price = in.nextDouble();
                }catch (InputMismatchException e){
                    System.out.println("Invalid Price");
                    continue;
                }
                System.out.println("Quantity:");
                int quantity = in.nextInt();
                boolean check = true;
                String code = "";
                while(check){
                    System.out.println("Numerical Product Code [XX]:");
                    code = in.next();
                    check = machine.checkCode(code, 2);
                    if(check){
                        System.out.println("Invalid Code: Please enter two-digit numerical code [XX]");
                    }
                }            
                machine.addProduct(new Product(description, price, code), quantity);
            }
            else if (command.equals("R")){
                System.out.println("Removed: e" + Double.parseDouble(df.format(machine.removeMoney(machine.getCoins(), "allCoins.txt"))));
            }
            else if (command.equals("C")){
                boolean check = true;
                String code = "";
                while(check){
                    System.out.println("Enter Operator Passcode [XXXX]");
                    code = in.next();
                    check = machine.checkCode(code, 4);
                    if(check){
                        System.out.println("Invalid Code: Please enter four-digit numerical code [XXXX]");
                    }
                } 
                machine.getOperators().add(new VendingOperator(String.valueOf(machine.getOperators().size()), code));
                machine.operatorsToFile();
            }
            
        }
        if (command.equals("S")){ 
            for (Product p : machine.getProductTypes())
               System.out.println(p);
        }
        else if (command.equals("Q"))
        { 
            more = false;
        } 
      }
   }

   private Object getChoice(Object[] choices)
   {
       Scanner next = new Scanner(System.in);
      while (true)
      {
         char c = 'A';
         for (Object choice : choices)
         {
            System.out.println(c + ") " + choice.toString()); 
            c++;
         }
         String input = next.nextLine();
         int n = input.toUpperCase().charAt(0) - 'A';
         if (0 <= n && n < choices.length)
            return choices[n];
      }      
   }
}
