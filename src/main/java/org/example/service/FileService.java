/**
 * 🔐 Professional FileService (User)
 */

package org.example.service;

import org.example.model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileService {
    private static final String DB_FILE = "DB_Users.dat";

    public static void writeUser(User user) {
        List<User> users = readUsers();
        users.add(user);
        save(users);
    }

    public static List<User> readUsers() {
        File file = new File(DB_FILE);
        if (!file.exists()) return new ArrayList<>();

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = in.readObject();
            if (obj instanceof List<?>) {
                return (List<User>) obj;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static boolean validateUser(String name, String surname, String password) {
        return readUsers()
                .stream()
                .anyMatch(u -> u.getName().equals(name) &&
                        u.getSurname().equals(surname) &&
                        u.getPassword().equals(password));
    }

    private static void save(List<User> users) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(DB_FILE))) {
            out.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String formatUsersForInline() {
        StringBuilder sb = new StringBuilder();
        List<User> users = readUsers();
        if (users.isEmpty()) {
            return "🚫 Foydalanuvchilar topilmadi.";
        }
        users.forEach(u -> sb.append(u.toString()).append("\n"));
        return sb.toString();
    }
}
