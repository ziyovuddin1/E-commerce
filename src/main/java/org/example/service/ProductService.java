/**
 * 📦 Professional ProductService
 */

package org.example.service;

import org.example.model.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductService {
    private static final String FILE_NAME = "products.dat";

    public static void addProduct(Product product) {
        List<Product> products = getAllProducts();
        products.add(product);
        save(products);
    }

    public static List<Product> getProductsByCategoryId(int categoryId) {
        return getAllProducts()
                .stream()
                .filter(p -> p.getCategoryId() == categoryId)
                .collect(Collectors.toList());
    }

    public static void deleteProduct(int id) {
        List<Product> products = getAllProducts();
        products.removeIf(p -> p.getId() == id);
        save(products);
    }

    public static List<Product> getAllProducts() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                return (List<Product>) obj;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private static void save(List<Product> products) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(products);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String formatProductsForInline(int categoryId) {
        StringBuilder sb = new StringBuilder();
        List<Product> products = getProductsByCategoryId(categoryId);
        if (products.isEmpty()) {
            return "🚫 Bu kategoriyada mahsulotlar topilmadi.";
        }
        products.forEach(p -> sb.append(p.toString()).append("\n"));
        return sb.toString();
    }
}
