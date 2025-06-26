package ECommerce.Test;

import ECommerce.Input.Input;
import ECommerce.Requirements.Requirements;
import ECommerce.User.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static File DB_FILE = new File("src/ECommerce/User/DB_Users.txt");

    public static void main(String[] args) {
        while (true) {
            System.out.println("""
                    ----MENU----
                    1. Sign in
                    2. Sign up
                    0. Exit
                    """);
            switch (Input.num("Choose: ")) {
                case 1 -> signIn();
                case 2 -> signUp();
                case 0 -> {
                    System.out.println("Exited the program");
                    return;
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private static void signIn() {
        boolean valid = false;
        List<User> users = readUserFromFile();
        if (users.isEmpty()) {
            System.out.println("User not found");
            return;
        }
        String name = Input.str("Enter your username: ");
        String password = Input.str("Enter your password: ");
        for (User user : users) {
            if (name.equals(user.getName() + " " + user.getSurname()) &&
                    password.equals(user.getPassword())) {
                valid = true;
                System.out.println("Login successful!!!");
            }
        }
        if (!valid) {
            System.out.println("Username or password is incorrect!");
        } else {
            Requirements.requirement();
        }
    }

    private static void signUp() {
        User user = new User();
        user.setName(Input.str("Enter your name: "));
        user.setSurname(Input.str("Enter your surname: "));
        user.setAge(Input.num("Enter your age: "));
        user.setPhoneNumber(Input.str("Enter your phone number: "));
        user.setPassword(Input.str("Enter your password: "));

        writeUserToFile(user);
        System.out.println("successfully completed!");
    }

    private static void writeUserToFile(User user) {
        List<User> users = readUserFromFile();
        users.add(user);
        try (ObjectOutputStream obj = new ObjectOutputStream(new FileOutputStream(DB_FILE))) {
            obj.writeObject(users);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<User> readUserFromFile() {
        if (!DB_FILE.exists()) return new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(DB_FILE))) {
            return (List<User>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }
}
