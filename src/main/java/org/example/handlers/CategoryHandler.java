package org.example.handlers;

import org.example.model.Category;
import org.example.service.CategoryService;

import static org.example.utils.BotUtils.sendMsg;

public class CategoryHandler {

    public static void handle(long chatId, String msg) {
        if (msg.equals("1")) {
            sendMsg(chatId, "Enter: <id> <name>");
        } else if (msg.equals("2")) {
            var list = CategoryService.getAllCategories();
            if (list.isEmpty()) sendMsg(chatId, "No categories.");
            else list.forEach(c -> sendMsg(chatId, c.toString()));
        } else if (msg.equals("3")) {
            sendMsg(chatId, "Enter category ID to delete:");
        } else if (msg.matches("\\d+\\s+.+")) {
            var p = msg.split(" ", 2);
            CategoryService.addCategory(new Category(Integer.parseInt(p[0]), p[1]));
            sendMsg(chatId, "✅ Added.");
        } else if (msg.matches("\\d+")) {
            CategoryService.deleteCategory(Integer.parseInt(msg));
            sendMsg(chatId, "✅ Deleted.");
        } else {
            sendMsg(chatId, "Invalid input.");
        }
    }
}
