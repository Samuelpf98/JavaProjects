
import java.util.ArrayList;
import java.util.Arrays;

/**
   A set of coins.
*/
public class CoinSet
{  
   private ArrayList<Coin> set;
   private double value;

   /**
      Constructs a CoinSet object.
   */
   public CoinSet()
   {  
      set = new ArrayList<Coin>();
   }
   
   public void newCoin(Coin aCoin){
       this.set.add(aCoin);
       this.value += aCoin.getCoinValue();
   }
   
   public double getValue(){
       return this.value;
   }
   
   public ArrayList<Coin> getCoins(){
       return this.set;
   }
   
   public void setValue(double d){
       this.value = d;
   }
   
   public void remove(){
       this.set.clear();
       this.value = 0;
   }
   
   @Override
   public String toString(){
       return Arrays.toString(set.toArray());
   }
}