
public class VendingOperator {
    
    private String name;
    private String pass;
    
    public VendingOperator(String name, String pass){
        this.name = name;
        this.pass = pass;
    }
    
    public String getName(){
        return this.name;
    }
    
    public String getPassword(){
        return this.pass;
    }
    
}
