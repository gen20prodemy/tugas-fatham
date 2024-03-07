public class Drink extends Product {
    public Drink(String name, String exp, double price){
        super(name, exp, price);
    }

    @Override
    public double getPrice(){
        return price;
    }

}
