public class Product {
    private String name;
    private String exp;
    protected double price;

    public Product(){

    }

    public Product(String name, String exp, double price){
        this.name = name;
        this.exp = exp;
        this.price = price;
    }

    public String getName(){
        return name;
    }

    public String getExp(){
        return exp;
    }
    public double getPrice(){
        return price;
    }

    public static void displayProduct(Product product){
        System.out.println("Nama : "+product.getName());
        System.out.println("Expired : "+product.getExp());
        System.out.println("Harga : "+product.getPrice()+"\n");
    }
}
