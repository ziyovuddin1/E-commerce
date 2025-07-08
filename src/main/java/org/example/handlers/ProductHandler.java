package org.example.handlers;

import org.example.model.Product;

import org.example.service.ProductService;

import static org.example.utils.BotUtils.sendMsg;

public class ProductHandler {

    public static void handle(long chatId, String msg) {
        if (msg.equals("1")) {
            sendMsg(chatId, "Enter: <id> <categoryId> <name>");
        } else if (msg.equals("2")) {
            var list = ProductService.getAllProducts();
            if (list.isEmpty()) sendMsg(chatId, "No products.");
            else list.forEach(p -> sendMsg(chatId, p.toString()));
        } else if (msg.equals("3")) {
            sendMsg(chatId, "Enter product ID to delete:");
        } else if (msg.matches("\\d+\\s+\\d+\\s+.+")) {
            var p = msg.split(" ", 3);
            ProductService.addProduct(new Product(
                    Integer.parseInt(p[0]), Integer.parseInt(p[1]), p[2]));
            sendMsg(chatId, "✅ Added.");
        } else if (msg.matches("\\d+")) {
            ProductService.deleteProduct(Integer.parseInt(msg));
            sendMsg(chatId, "✅ Deleted.");
        } else {
            sendMsg(chatId, "Invalid input.");
        }
    }
}
