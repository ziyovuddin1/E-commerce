package org.example.handlers;

import org.example.model.Card;
import org.example.service.CardService;

import static org.example.utils.BotUtils.sendMsg;

public class CardHandler {

    public static void handle(long chatId, String msg) {
        if (msg.equals("1")) {
            sendMsg(chatId, "Enter: <id> <userId> <details>");
        } else if (msg.equals("2")) {
            var list = CardService.getAllCards();
            if (list.isEmpty()) sendMsg(chatId, "No cards.");
            else list.forEach(c -> sendMsg(chatId, c.toString()));
        } else if (msg.matches("\\d+\\s+\\S+\\s+.+")) {
            var p = msg.split(" ", 3);
            CardService.addCard(new Card(Integer.parseInt(p[0]), p[1], p[2]));
            sendMsg(chatId, "✅ Added.");
        } else {
            sendMsg(chatId, "Invalid input.");
        }
    }
}
