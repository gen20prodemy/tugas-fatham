public class Food extends Product{

    public Food(String name, String exp, double price){
        super(name, exp, price);
    }

    @Override
    public double getPrice(){
        return price;
    }

}
