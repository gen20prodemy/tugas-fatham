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

interface CRUD {
    void create(Map<Integer, Product> map, Scanner scanner);

    void read(Map<Integer, Product> map);

    void update(Map<Integer, Product> map, Scanner scanner);

    void delete(Map<Integer, Product> map, Scanner scanner);
}

class MapCRUD implements CRUD {

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
        System.out.println("Data in the Map:");
        for (Map.Entry<Integer, Product> entry : map.entrySet()) {
            System.out.println("ID: " + entry.getKey() + ", Name: " + entry.getValue().getName());
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
