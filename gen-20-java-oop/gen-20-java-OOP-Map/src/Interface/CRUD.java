package Interface;

import Product.Product;

import java.util.Map;
import java.util.Scanner;

public interface CRUD {
    void create(Map<Integer, Product> map, Scanner scanner);

    void read(Map<Integer, Product> map);

    void update(Map<Integer, Product> map, Scanner scanner);

    void delete(Map<Integer, Product> map, Scanner scanner);
}
