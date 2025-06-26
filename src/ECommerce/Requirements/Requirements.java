package ECommerce.Requirements;

import ECommerce.Input.Input;

import java.io.File;

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
        File file = new File("src/ECommerce/Requirements/Category");
        if (!file.exists()) file.mkdirs();
        System.out.println("Existing categories:");
        listAllCategories(file, "");
        String path = Input.str("Enter add a category: ");
        File selectedDir = new File(file, path);

        if (!selectedDir.exists() || !selectedDir.isDirectory()) {
            System.out.println("This path does not exist.");
            requirement();
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

    }

    private static void getProductByCategoryId() {

    }

    private static void deleteProduct() {

    }
}
