import java.util.Scanner;

public class throw_e_input {
    private int[] data;

    public throw_e_input (int[] data) {
        this.data = data;
    }

    public void processData() {
        System.out.println("Data diproses: " + data.length);
    }

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        int length;
        do {
            System.out.print("Masukkan panjang data (Positif): ");
            length = scan.nextInt();
        } while (length <=0);

        int[] input = new int[length];
        System.out.println("\nMasukkan data (Positif) : ");
        for (int i=0; i<length; i++){
            System.out.printf("Data ke-%d : ",i);
            input[i] = scan.nextInt();
        }

        scan.close();

        try {
            DataValidator.validateInput(input);
            throw_e_input data = new throw_e_input(input);
            data.processData();
        } catch (IllegalArgumentException e) {
            System.out.println("Terjadi kesalahan: " + e.getMessage());
        }
    }

    public static class DataValidator {
        public static void validateInput(int[] data) throws IllegalArgumentException {
            if (data == null || data.length == 0) {
                throw new IllegalArgumentException("Data tidak boleh kosong");
            }
            for (int num : data) {
                if (num < 0) {
                    throw new IllegalArgumentException("Data tidak boleh negatif");
                }
            }
        }
    }
}

