package org.example.service;

import org.example.model.Category;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryService {
    private static final String FILE_NAME = "categories.dat";

    public static void addCategory(Category category) {
        List<Category> categories = getAllCategories();
        categories.add(category);
        save(categories);
    }

    public static Category getCategoryById(int id) {
        return getAllCategories()
                .stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public static void deleteCategory(int id) {
        List<Category> categories = getAllCategories();
        categories.removeIf(c -> c.getId() == id);
        save(categories);
    }

    public static List<Category> getAllCategories() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                return (List<Category>) obj;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private static void save(List<Category> categories) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(categories);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String formatCategoriesForInline() {
        StringBuilder sb = new StringBuilder();
        List<Category> categories = getAllCategories();
        if (categories.isEmpty()) {
            return "🚫 Hech qanday kategoriya topilmadi.";
        }
        categories.forEach(c -> sb.append(c.toString()).append("\n"));
        return sb.toString();
    }
}
