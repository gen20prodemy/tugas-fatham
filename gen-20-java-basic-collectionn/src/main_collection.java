import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Map;

public class main_collection {
    private List<String> dataList;
    private Map<Integer, String> dataMap;
    private Scanner scanner;

    //Konstruktor
    public main_collection() {
        this.dataList = new ArrayList<>();
        this.dataMap = new HashMap<>();
        this.scanner = new Scanner(System.in);
    }

    //Method menambah nama
    public void addDataToList(String data) {
        dataList.add(data);
    }

    //Method menambahkan ID ke Nama
    public void convertListToMap() {
        for (int i = 0; i < dataList.size(); i++) {
            System.out.print("Masukkan id untuk nama \"" + dataList.get(i) + "\": ");
            int key = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            dataMap.put(key, dataList.get(i));
        }
    }


    public void displayDataList() {
        System.out.println("Data Nama :");
        for (int i = 0; i < dataList.size(); i++) {
            System.out.println(dataList.get(i));
        }
    }

    public void displayDataMap() {
        System.out.println("Data Nama & ID :");
        for (Map.Entry<Integer, String> entry : dataMap.entrySet()) {
            System.out.println("ID: " + entry.getKey() + ", Nama: " + entry.getValue());
        }
    }

    public void displayDataByKey(int key) {
        if (dataMap.containsKey(key)) {
            System.out.println("Data dengan key " + key + ": " + dataMap.get(key));
        } else {
            System.out.println("Data dengan key " + key + " tidak ditemukan.");
        }
    }

    public void displayMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Tambahkan Nama");
        System.out.println("2. Tambahkan ID");
        System.out.println("3. Tampilkan Data Nama");
        System.out.println("4. Tampilkan Data Nama dan ID");
        System.out.println("5. Tampilkan Data berdasarkan ID");
        System.out.println("6. Keluar");
    }

    public void handleUserInput(int choice) {
        switch (choice) {
            case 1:
                addDataToListWithQ();
                break;
            case 2:
                convertListToMap();
                break;
            case 3:
                displayDataList();
                break;
            case 4:
                displayDataMap();
                break;
            case 5:
                System.out.print("Masukkan nomor key untuk menampilkan data: ");
                int selectedKey = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                displayDataByKey(selectedKey);
                break;
            case 6:
                System.out.println("Terima kasih! Keluar dari program.");
                scanner.close();
                System.exit(0);
            default:
                System.out.println("Pilihan tidak valid.");
        }
    }

    public void addDataToListWithQ() {
        System.out.println("Masukkan Nama, ketik 'q' untuk berhenti:");
        while (true) {
            System.out.print("Nama : ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("q")) {
                break;
            }
            addDataToList(input);
        }
    }

    public static void main(String[] args) {
        main_collection converter = new main_collection();

        while (true) {
            converter.displayMenu();
            System.out.print("Masukkan pilihan: ");
            int choice = converter.scanner.nextInt();
            converter.scanner.nextLine(); // Consume newline
            converter.handleUserInput(choice);
        }
    }
}
