package Main;

import Interface.CRUD;
import Interface.MapCRUD;
import Product.Product;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Map<Integer, Product> map = new HashMap<>();
        CRUD crud = new MapCRUD();

        while (true) {
            try {
                System.out.println("\nMenu:");
                System.out.println("1. Create");
                System.out.println("2. Read");
                System.out.println("3. Update");
                System.out.println("4. Delete");
                System.out.println("5. Exit");
                System.out.print("Choose operation: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        crud.create(map, scanner);
                        break;
                    case 2:
                        crud.read(map);
                        break;
                    case 3:
                        crud.update(map, scanner);
                        break;
                    case 4:
                        crud.delete(map, scanner);
                        break;
                    case 5:
                        System.out.println("Program Selesai.");
                        scanner.close();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.err.println("Error: Masukkan pilihan menu dalam bilangan bulat");
                scanner.nextLine();
            }
        }
    }
}

