public class MainOOP {
    public static void main(String[] args) {
        Food foodProduct = new Food("Roti","5 Desember 2024",25000);
        Drink drinkProduct = new Drink("Susu","20 Desember 2025",45000);
        Medicine medicineProduct = new Medicine("Paracetamol","1 Januari 2050",5000);

        Product.displayProduct(foodProduct);
        Product.displayProduct(drinkProduct);
        Product.displayProduct(medicineProduct);

    }

}
