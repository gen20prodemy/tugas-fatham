package crudPackage;

import siswaPackage.Siswa;

import java.io.*;
import java.util.Scanner;

public class FileCRUD extends CRUD {

    @Override
    public void create(File file, Scanner scanner) throws IOException {
        FileWriter writer = new FileWriter(file, true);
        PrintWriter printWriter = new PrintWriter(writer);
        try {
            System.out.print("Enter new product ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            Siswa newProduct = new Siswa(id);
            System.out.print("Enter the name for ID " + id + ": ");
            String name = scanner.nextLine();
            newProduct.setName(name);
            printWriter.println(newProduct);
            System.out.println("Data successfully added.");
        } finally {
            printWriter.close();
            writer.close();
        }
    }

    @Override
    public void read(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();//Membaca baris pertama
        if (line == null){
            System.out.println("Data kosong.");
        }else{
            System.out.println("Data dalam file :");
            do{
                String[] parts = line.split(",");
                System.out.println("ID: " + parts[0] + ", Name: " + parts[1]);
            }while ((line = reader.readLine()) != null);
        }
        reader.close();
    }

    @Override
    public void update(File file, Scanner scanner) throws IOException {
        FileReader reader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String tempFile = "temp.txt";
        FileWriter writer = new FileWriter(tempFile, true);
        PrintWriter printWriter = new PrintWriter(writer);
        try {
            System.out.print("Enter the ID to update: ");
            int idToUpdate = scanner.nextInt();
            scanner.nextLine();

            String line;
            boolean updated = false;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                if (id == idToUpdate) {
                    System.out.print("Enter the new name for ID " + id + ": ");
                    String newName = scanner.nextLine();
                    printWriter.println(id + "," + newName);
                    updated = true;
                    System.out.println("Data successfully updated.");
                } else {
                    printWriter.println(line);
                }
            }

            if (!updated) {
                System.out.println("Product with ID " + idToUpdate + " does not exist");
            }
        } finally {
            printWriter.close();
            writer.close();
            bufferedReader.close();
            reader.close();
            // Delete the old file
            if (file.delete()) {
                // Rename the temp file to the original file name
                File newFile = new File(file.getAbsolutePath());
                File temp = new File(tempFile);
                if (temp.renameTo(newFile)) {
                    System.out.println("File updated successfully.");
                } else {
                    System.err.println("Failed to update file.");
                }
            } else {
                System.err.println("Failed to delete file.");
            }
        }
    }

    @Override
    public void delete(File file, Scanner scanner) throws IOException {
        FileReader reader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String tempFile = "temp.txt";
        FileWriter writer = new FileWriter(tempFile, true);
        PrintWriter printWriter = new PrintWriter(writer);
        try {
            System.out.print("Enter the ID to delete: ");
            int idToDelete = scanner.nextInt();
            scanner.nextLine();

            String line;
            boolean deleted = false;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                if (id != idToDelete) {
                    printWriter.println(line);
                } else {
                    deleted = true;
                    System.out.println("Data with ID " + id + " successfully deleted.");
                }
            }

            if (!deleted) {
                System.out.println("Product with ID " + idToDelete + " does not exist");
            }
        } finally {
            printWriter.close();
            writer.close();
            bufferedReader.close();
            reader.close();
            // Delete the old file
            if (file.delete()) {
                // Rename the temp file to the original file name
                File newFile = new File(file.getAbsolutePath());
                File temp = new File(tempFile);
                if (temp.renameTo(newFile)) {
                    System.out.println("File updated successfully.");
                } else {
                    System.err.println("Failed to update file.");
                }
            } else {
                System.err.println("Failed to delete file.");
            }
        }
    }
}
