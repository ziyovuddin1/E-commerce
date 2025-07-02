package ECommerce.Requirements;

import ECommerce.Input.Input;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;

public class Requirements {
    public static void requirement() {
        while (true) {
            System.out.println("""
                    1. Add Category
                    2. Get Category By ID
                    3. Delete Category
                    4. Add Product
                    5. Get Product By CategoryID
                    6. Delete Product
                    7. Create Card
                    8. Get Card By UserID
                    9. Get All Cards from All Users
                    0. Exit
                    """);

            switch (Input.num("Choose: ")) {
                case 1 -> addCategory();
                case 2 -> getCategoryById();
                case 3 -> deleteCategory();
                case 4 -> addProduct();
                case 5 -> getProductByCategoryId();
                case 6 -> deleteProduct();
                case 7 -> createCard();
                case 8 -> getCardByUserId();
                case 9 -> getAllCardsFromAllUsers();
                case 0 -> {
                    System.out.println("Exit program");
                    return;
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private static void createCard() {
        String userId = Input.str("Enter user ID: ");
        String cardNumber = Input.str("Enter card number (16 digits): ");
        String holderName = Input.str("Enter card holder name: ");
        String expiry = Input.str("Enter expiry date (MM/YY): ");

        File userCardDir = new File("src/ECommerce/Requirements/Cards/" + userId);
        if (!userCardDir.exists()) {
            userCardDir.mkdirs();
        }

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        File cardFile = new File(userCardDir, "card_" + timestamp + ".txt");

        try (FileWriter writer = new FileWriter(cardFile)) {
            writer.write("Card Number: " + cardNumber + " ");
            writer.write("Holder Name: " + holderName + " ");
            writer.write("Expiry Date: " + expiry + " ");
            System.out.println("Card successfully created at: " + cardFile.getPath());
        } catch (IOException e) {
            System.out.println("Failed to create card: " + e.getMessage());
        }
    }

    private static void getCardByUserId() {
        String userId = Input.str("Enter user ID to retrieve cards: ");
        File userCardDir = new File("src/ECommerce/Requirements/Cards/" + userId);

        if (!userCardDir.exists() || !userCardDir.isDirectory()) {
            System.out.println("No cards found for this user.");
            return;
        }

        File[] cardFiles = userCardDir.listFiles((dir, name) -> name.endsWith(".txt"));
        if (cardFiles == null || cardFiles.length == 0) {
            System.out.println("No cards available for user: " + userId);
            return;
        }

        Arrays.stream(cardFiles).sorted().forEach(file -> {
            System.out.println(" --" + file.getName() + " --");
            try {
                Files.lines(file.toPath()).forEach(System.out::println);
            } catch (IOException e) {
                System.out.println("Failed to read card: " + file.getName());
            }
        });
    }

    private static void getAllCardsFromAllUsers() {
        File cardsDir = new File("src/ECommerce/Requirements/Cards");
        if (!cardsDir.exists() || !cardsDir.isDirectory()) {
            System.out.println("No card directory found.");
            return;
        }

        File[] userDirs = cardsDir.listFiles(File::isDirectory);
        if (userDirs == null || userDirs.length == 0) {
            System.out.println("No user cards found.");
            return;
        }

        for (File userDir : userDirs) {
            System.out.println(" == = Cards for User: " + userDir.getName() + " == = ");
            File[] cardFiles = userDir.listFiles((dir, name) -> name.endsWith(".txt"));
            if (cardFiles == null || cardFiles.length == 0) {
                System.out.println("No cards found for this user.");
                continue;
            }
            Arrays.stream(cardFiles).sorted().forEach(file -> {
                System.out.println(" --" + file.getName() + " --");
                try {
                    Files.lines(file.toPath()).forEach(System.out::println);
                } catch (IOException e) {
                    System.out.println("Failed to read card: " + file.getName());
                }
            });
        }
    }

    private static void listAllCategories(File folder, String indent) {
        File[] dirs = folder.listFiles(File::isDirectory);
        if (dirs != null) {
            Arrays.stream(dirs).sorted(Comparator.comparing(File::getName)).forEach(file -> {
                System.out.println(indent + "- " + file.getName());
                listAllCategories(file, indent + "  ");
            });
        }
    }

    private static void showCategoryList() {
        File folder = new File("src/ECommerce/Requirements/Category");
        if (!folder.exists()) {
            System.out.println("No categories found!");
            return;
        }
        System.out.println("Category list:");
        listAllCategories(folder, "");
    }

    private static void addCategory() {
        File base = new File("src/ECommerce/Requirements/Category");
        if (!base.exists()) base.mkdirs();

        showCategoryList();
        String path = Input.str("Enter base category path (empty for root): ");
        File parent = path.isEmpty() ? base : new File(base, path);

        if (!parent.exists()) {
            System.out.println("Invalid path.");
            return;
        }

        String name = Input.str("Enter new category name: ");
        File newCat = new File(parent, name);

        if (newCat.exists()) {
            System.out.println("Category already exists.");
        } else if (newCat.mkdirs()) {
            System.out.println("Created category: " + name);
        } else {
            System.out.println("Failed to create category.");
        }
    }

    private static void getCategoryById() {
        showCategoryList();
        String path = Input.str("Enter category path to view: ");
        File target = new File("src/ECommerce/Requirements/Category", path);

        if (!target.exists()) {
            System.out.println("Invalid path.");
            return;
        }

        System.out.println("Subcategories of " + path + ":");
        listAllCategories(target, "");
    }

    private static void deleteCategory() {
        showCategoryList();
        String path = Input.str("Enter category path to delete: ");
        File dir = new File("src/ECommerce/Requirements/Category", path);

        if (!dir.exists()) {
            System.out.println("Invalid path.");
            return;
        }

        try {
            Files.walk(dir.toPath()).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
            System.out.println("Category deleted successfully.");
        } catch (IOException e) {
            System.out.println("Error deleting category: " + e.getMessage());
        }
    }

    private static void addProduct() {
        showCategoryList();
        String categoryPath = Input.str("Enter the category path to add product into: ");
        File categoryDir = new File("src/ECommerce/Requirements/Category", categoryPath);

        if (!categoryDir.exists() || !categoryDir.isDirectory()) {
            System.out.println("Invalid category path.");
            return;
        }

        String productName = Input.str("Enter product name: ");
        String productDescription = Input.str("Enter product description: ");
        double productPrice = Input.num("Enter product price: ");

        File productFile = new File(categoryDir, productName + ".txt");

        try (FileWriter writer = new FileWriter(productFile)) {
            writer.write("Name: " + productName + " "); writer.write("Description: " + productDescription + " "); writer.write("Price: $" + productPrice + " "); System.out.println("Product added successfully: " + productFile.getPath());
        } catch (IOException e) {
            System.out.println("Failed to add product: " + e.getMessage());
        }
    }

    private static void getProductByCategoryId() {
        showCategoryList();
        String categoryPath = Input.str("Enter category path to view products: ");
        File categoryDir = new File("src/ECommerce/Requirements/Category", categoryPath);

        if (!categoryDir.exists() || !categoryDir.isDirectory()) {
            System.out.println("Invalid category path.");
            return;
        }

        File[] productFiles = categoryDir.listFiles((dir, name) -> name.endsWith(".txt"));
        if (productFiles == null || productFiles.length == 0) {
            System.out.println("No products found in this category.");
            return;
        }

        System.out.println("Products in category '" + categoryPath + "':");
        Arrays.stream(productFiles).forEach(file -> {
            System.out.println(" --" + file.getName() + " --"); try {
                Files.lines(file.toPath()).forEach(System.out::println);
            } catch (IOException e) {
                System.out.println("Failed to read product: " + file.getName());
            }
        });
    }

    private static void deleteProduct() {
        showCategoryList();
        String categoryPath = Input.str("Enter category path where product exists: ");
        File categoryDir = new File("src/ECommerce/Requirements/Category", categoryPath);

        if (!categoryDir.exists() || !categoryDir.isDirectory()) {
            System.out.println("Invalid category path.");
            return;
        }

        String productName = Input.str("Enter product name to delete: ");
        File productFile = new File(categoryDir, productName + ".txt");

        if (productFile.exists() && productFile.delete()) {
            System.out.println("Product deleted successfully.");
        } else {
            System.out.println("Failed to delete product or product does not exist.");
        }
    }
}

