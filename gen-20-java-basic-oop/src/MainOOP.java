import java.util.Scanner;

public class MainOOP {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Food foodProduct = null;
        Drink drinkProduct = null;
        Medicine medicineProduct = null;

        menu(scanner, foodProduct, drinkProduct, medicineProduct);

        scanner.close();

    }

    public static void menu(Scanner scanner, Product foodProduct, Product drinkProduct, Product medicineProduct){
        System.out.println("Pilih Kategori Product : \n1. Makanan\n2. Minuman\n3. Obat\nKategori : ");
        int category = scanner.nextInt();
        scanner.nextLine();

        switch (category){
            case 1 :
                foodProduct = Food.inputProduct(scanner);
                Food.displayProduct(foodProduct);
                break;
            case 2 :
                drinkProduct = Drink.inputProduct(scanner);
                Drink.displayProduct(drinkProduct);
                break;
            case 3 :
                medicineProduct = Medicine.inputProduct(scanner);
                Medicine.displayProduct(medicineProduct);
                break;
            default:
                System.out.println("Pilihan tidak valid.");
                break;
        }
    }

}
