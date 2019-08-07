
public class Coin
{
   private double value;
   private String name;

   public Coin(double aValue, String aName) 
   { 
      value = aValue; 
      name = aName;
   }
   
   public Coin(){
        
   }

   public double getCoinValue(){
       return this.value;
   }
   
   @Override
   public String toString(){
       return this.name;
   }
   
}