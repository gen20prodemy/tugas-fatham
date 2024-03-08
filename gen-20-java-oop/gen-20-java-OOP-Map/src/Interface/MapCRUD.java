package Interface;

import Product.Product;

import java.util.Map;
import java.util.Scanner;

public class MapCRUD implements CRUD {

    @Override
    public void create(Map<Integer, Product> map, Scanner scanner) {

        try {
            System.out.print("Enter new product ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            if (map.containsKey(id)) {
                System.out.println("Product with ID " + id + " already exists");
            } else {
                System.out.print("Enter the name for ID " + id + ": ");
                String name = scanner.nextLine();
                map.put(id, new Product(id, name));
                System.out.println("Data successfully added.");
            }
        } catch (RuntimeException e) {
            System.err.println("ID yang Anda Masukkan tidak sesuai Format");
            scanner.nextLine();
        }

    }

    @Override
    public void read(Map<Integer, Product> map) {
        System.out.println("Data dalam Map:");
        if (map.isEmpty()){
            System.err.println("Data masih kosong");
        }else {
            for (Map.Entry<Integer, Product> entry : map.entrySet()) {
                System.out.println("ID: " + entry.getKey() + ", Name: " + entry.getValue().getName());
            }
        }
    }

    @Override
    public void update(Map<Integer, Product> map, Scanner scanner) {
        try {
            System.out.print("Enter the ID to update: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            if (!map.containsKey(id)) {
                System.out.println("Product with ID " + id + " does not exist");
            } else {
                System.out.print("Enter the new name for ID " + id + ": ");
                String name = scanner.nextLine();
                map.get(id).setName(name);
                System.out.println("Data successfully updated.");
            }
        } catch (RuntimeException e) {
            System.err.println("ID yang Anda Masukkan tidak sesuai Format");
            scanner.nextLine();
        }
    }

    @Override
    public void delete(Map<Integer, Product> map, Scanner scanner) {

        try {
            System.out.print("Enter the ID to delete: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            if (!map.containsKey(id)) {
                System.out.println("Product with ID " + id + " does not exist");
            } else {
                map.remove(id);
                System.out.println("Data successfully deleted.");
            }
        } catch (RuntimeException e) {
            System.err.println("ID yang Anda Masukkan tidak sesuai Format");
            scanner.nextLine();
        }
    }
}
