package CustomException;

import java.util.Scanner;
public class custom_exception {

    public static void main(String[] args) throws age_custom_e {
        Scanner input = new Scanner(System.in);
        System.out.print("Masukkan umur anda: ");

        try {
            int age = Integer.parseInt(input.nextLine());//konversi string to integer, jika string tidak bisa di konversi menjadi int maka NumberFormatException dijalankan
            validateAge(age);
        } catch (NumberFormatException e){
            System.err.println("Input harus berupa angka.");
        } catch (age_custom_e e){
            System.err.println(e.getMessage());
        }

    }

    private static void validateAge(int age) throws age_custom_e{//exception negative age
        if (age < 0){
            throw new age_custom_e("Age cannot be negative");
        }
        else {
            System.out.println("Umur anda: "+age);
        }
    }

}
