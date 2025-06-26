package ECommerce.Requirements;

import ECommerce.Input.Input;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Requirements {
    public static void requirement() {
        System.out.println("""
                1. Add Category
                2. Get Category By ID
                3. Delete Category
                4. Add Product
                5. Get Product By CategoryID
                6. Delete Product
                0. Exit
                """);
        switch (Input.num("Choose: ")) {
            case 1 -> addCategory();
            case 2 -> getCategoryById();
            case 3 -> deleteCategory();
            case 4 -> addProduct();
            case 5 -> getProductByCategoryId();
            case 6 -> deleteProduct();
            case 0 -> {
                System.out.println("Exit program");
                return;
            }
            default -> System.out.println("Invalid choice");
        }
    }

    private static void listAllCategories(File folder, String indent) {
        File[] files = folder.listFiles(File::isDirectory);
        if (files == null || files.length == 0) return;

        for (File file : files) {
            System.out.println(indent + "- " + file.getPath().replace("src/ECommerce/Requirements/Category/", ""));
            listAllCategories(file, indent + "  ");
        }
    }

    private static void showCategoryList() {
        File folder = new File("src/ECommerce/Requirements/Category");
        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("No categories found!");
            return;
        }

        System.out.println("Category list: ");
        listAllCategories(folder, "");
    }

    private static void addCategory() {
        File file = new File("src/ECommerce/Requirements");
        if (!file.exists()) file.mkdirs();

        System.out.println("Existing categories:");
        listAllCategories(file, "");

        String path = Input.str("Enter add a category: ");
        File selectedDir = new File(file, path);

        if (!selectedDir.exists()) {
            System.out.println("This path does not exist.");
            if (selectedDir.mkdirs()) {
                System.out.println("Category created!");
            } else {
                System.out.println("Category could not be created!");
                requirement();
            }
        }

        String newCategoryName = Input.str("Enter new category name to create inside " + path + ": ");
        File newCategory = new File(selectedDir, newCategoryName);

        if (newCategory.exists()) {
            System.out.println("Category already exists.");
        } else if (newCategory.mkdirs()) {
            System.out.println("Category '" + newCategoryName + "' created successfully inside '" + path + "'");
        } else {
            System.out.println("Failed to create category.");
        }
    }

    private static void getCategoryById() {
        showCategoryList();
        File file = new File("src/ECommerce/Requirements/Category");
        String path = Input.str("Enter get category ID: ");
        File selectedDir = new File(file, path);
        if (!selectedDir.exists() || !selectedDir.isDirectory()) {
            System.out.println("This path does not exist.");
            requirement();
        }
        System.out.println(path + " kategoriya ichidagilari: ");
        listAllCategories(selectedDir, "");
    }

    private static void deleteCategory() {
        showCategoryList();
        File file = new File("src/ECommerce/Requirements/Category");
        String path = Input.str("Enter delete category ID: ");
        File selectedDir = new File(file, path);
        if (!selectedDir.exists() || !selectedDir.isDirectory()) {
            System.out.println("This path does not exist.");
            requirement();
        }
        if (selectedDir.delete()) {
            System.out.println("Category '" + path + "' deleted successfully.");
        } else {
            System.out.println("Failed to delete category.");
        }
    }

    private static void addProduct() {
        File file = new File("src/ECommerce/Requirements/Category");
        List<File> files = new ArrayList<>();
        findLeafFolders(file, files);
        if (files.isEmpty()) {
            System.out.println("No categories found!");
            requirement();
        }
        System.out.println("Available product categories:");
        for (int i = 0; i < files.size(); i++) {
            String relativePath = files.get(i).getPath().replace("src/ECommerce/Requirements/Category/", "");
            System.out.println((i + 1) + ". " + relativePath);
        }

        int choice = Input.num("Choose a category to add product into: ");
        if (choice < 1 || choice > files.size()) {
            System.out.println("Invalid choice!");
            requirement();
        }
        File selectedFolder = files.get(choice - 1);

        String productName = Input.str("Enter product name: ");
        File productFile = new File(selectedFolder, productName + ".txt");
        try (FileWriter writer = new FileWriter(productFile)) {
            writer.write("Product Name: " + productName + "\n");
            System.out.println("Product saved successfully to: " + productFile.getPath());
        } catch (IOException e) {
            System.out.println("Error writing product file: " + e.getMessage());
        }
    }

    private static void findLeafFolders(File folder, List<File> leafFolders) {
        File[] subDirs = folder.listFiles(File::isDirectory);
        if (subDirs == null || subDirs.length == 0) {
            leafFolders.add(folder);
            return;
        }

        for (File sub : subDirs) {
            findLeafFolders(sub, leafFolders);
        }
    }

    private static void getProductByCategoryId() {
        showCategoryList();
        File file = new File("src/ECommerce/Requirements/Category");
        String path = Input.str("Enter get category ID: ");
        File selectedDir = new File(file, path);
        if (!selectedDir.exists() || !selectedDir.isDirectory()) {
            System.out.println("This path does not exist.");
            requirement();
        }
        File[] subDirs = selectedDir.listFiles((dir, name) -> name.endsWith(".txt"));
        if (subDirs == null || subDirs.length == 0) {
            System.out.println("No categories found!");
            requirement();
        }
        System.out.println("Available product categories:");
        for (File sub : subDirs) {
            System.out.println(sub.getPath());
            try (BufferedReader reader = new BufferedReader(new FileReader(sub))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
                System.out.println();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void deleteProduct() {
        File file = new File("src/ECommerce/Requirements/Category");
        showCategoryList();
        String path = Input.str("Enter delete category ID: ");
        File selectedDir = new File(file, path);
        if (!selectedDir.exists() || !selectedDir.isDirectory()) {
            System.out.println("This path does not exist.");
            requirement();
        }
        File[] subDirs = selectedDir.listFiles((dir, name) -> name.endsWith(".txt"));
        if (subDirs == null || subDirs.length == 0) {
            System.out.println("No categories found!");
            requirement();
        }
        System.out.println("Available product categories:");
        for (int i = 0; i < subDirs.length; i++) {
            System.out.println(i + 1 + ". " + subDirs[i].getPath());
        }
        int choice = Input.num("Choose a category to delete product: ");
        if (choice < 1 || choice > subDirs.length) {
            System.out.println("Invalid choice!");
            requirement();
        }
        File selectedFolder = subDirs[choice - 1];
        if (selectedFolder.delete()) {
            System.out.println("Category '" + path + "' deleted successfully.");
        }
        else {
            System.out.println("Failed to delete category.");
        }
    }
}
