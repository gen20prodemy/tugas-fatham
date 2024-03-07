public class Medicine extends Product{
    public Medicine(String name, String exp, double price){
        super(name, exp, price);
    }

    @Override
    public double getPrice(){
        return price;
    }
}
