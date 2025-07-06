package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class MyBot extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return "@@G5607_bot";
    }

    @Override
    public String getBotToken() {
        return "7632595677:AAEJVIuYi8vzqIBfS6dHs_VaJDehjnmM3eQ";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            if (text.equals("/start")) {
                sendMainMenu(chatId);
            }
        } else if (update.hasCallbackQuery()) {
            String data = update.getCallbackQuery().getData();
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            int messageId = update.getCallbackQuery().getMessage().getMessageId();

            switch (data) {
                case "SIGNUP" -> editMessage(chatId, messageId, "📝 Sign Up jarayoni boshlanadi…");
                case "SIGNIN" -> editMessage(chatId, messageId, "🔑 Sign In jarayoni boshlanadi…");
                case "CATEGORY" -> editMessage(chatId, messageId, "📂 Category bo‘limi");
                case "PRODUCT" -> editMessage(chatId, messageId, "📦 Product bo‘limi");
                case "CARD" -> editMessage(chatId, messageId, "💳 Card bo‘limi");
                case "BACK" -> sendMainMenu(chatId, messageId);
                default -> answerCallback(update.getCallbackQuery().getId(), "Tanlanmagan!");
            }
        }
    }

    private void sendMainMenu(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("👋 Xush kelibsiz! Tanlang:");
        message.setReplyMarkup(mainMenuKeyboard());

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendMainMenu(long chatId, int messageId) {
        EditMessageText edit = new EditMessageText();
        edit.setChatId(String.valueOf(chatId));
        edit.setMessageId(messageId);
        edit.setText("👋 Xush kelibsiz! Tanlang:");
        edit.setReplyMarkup(mainMenuKeyboard());

        try {
            execute(edit);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private InlineKeyboardMarkup mainMenuKeyboard() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        rows.add(List.of(button("📝 Sign Up", "SIGNUP"), button("🔑 Sign In", "SIGNIN")));
        rows.add(List.of(button("📂 Categories", "CATEGORY")));
        rows.add(List.of(button("📦 Products", "PRODUCT")));
        rows.add(List.of(button("💳 Cards", "CARD")));

        markup.setKeyboard(rows);
        return markup;
    }

    private InlineKeyboardButton button(String text, String callbackData) {
        InlineKeyboardButton btn = new InlineKeyboardButton();
        btn.setText(text);
        btn.setCallbackData(callbackData);
        return btn;
    }

    private void editMessage(long chatId, int messageId, String newText) {
        EditMessageText edit = new EditMessageText();
        edit.setChatId(String.valueOf(chatId));
        edit.setMessageId(messageId);
        edit.setText(newText);

        List<List<InlineKeyboardButton>> back = List.of(
                List.of(button("⬅️ Orqaga", "BACK"))
        );
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(back);

        edit.setReplyMarkup(markup);

        try {
            execute(edit);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void answerCallback(String callbackQueryId, String text) {
        AnswerCallbackQuery answer = new AnswerCallbackQuery();
        answer.setCallbackQueryId(callbackQueryId);
        answer.setText(text);
        try {
            execute(answer);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
