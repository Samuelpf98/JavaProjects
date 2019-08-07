
public class Product
{  
   private String description;
   private double price;
   private String code; 
   
   public Product(){  

   }
   
    public Product(String aDescription, double aPrice, String aCode){  
        this.description = aDescription;
        this.price = aPrice;
        this.code = aCode;
   }
   
   public double getPrice(){
       return this.price;
   }
   
   public String getCode(){
       return this.code;
   }
   
   public String getDesc(){
       return this.description;
   }
   
   @Override
   public String toString(){
       String output = "";
		 output += this.description;
                 for(int i = 0; i < 25 - this.description.length(); i++){
                     output+= " ";
                 }
                 output += "e"; 
		 output += this.price;
		 return output;
   }

}
