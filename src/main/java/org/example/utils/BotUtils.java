package org.example.utils;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class BotUtils {
    private static AbsSender bot;

    public static void init(AbsSender absSender) {
        bot = absSender;
    }

    public static void sendMsg(long chatId, String text) {
        SendMessage msg = new SendMessage();
        msg.setChatId(String.valueOf(chatId));
        msg.setText(text);
        try {
            bot.execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
