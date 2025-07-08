package org.example;

import org.glassfish.grizzly.streams.Input;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.ForwardMessage;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.LinkedList;
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
            if (text.equals("/help")) {
                InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                Message message = update.getMessage();
                List<List<InlineKeyboardButton>> rowList = new LinkedList<>();
                List<InlineKeyboardButton> row = new LinkedList<>();
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText("Adminga xabar yuborish");
                button.setSwitchInlineQueryCurrentChat("Matnni kiriting: ");
                row.add(button);
                rowList.add(row);
                inlineKeyboardMarkup.setKeyboard(rowList);
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(message.getChatId());
                sendMessage.setText("Qandaydir savol va takliflaringiz bo'lsa admin bilan bog'laning");
                sendMessage.setReplyMarkup(inlineKeyboardMarkup);
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }

            if (update.getMessage().hasPhoto()) {
                List<PhotoSize> photos = update.getMessage().getPhoto();
                if (!photos.isEmpty()) {
                    PhotoSize photoSize = photos.get(photos.size() - 1);
                    String photoId = photoSize.getFileId();
                    String text1 = "You sent me a photo! ";

                    SendPhoto sendPhoto = new SendPhoto();
                    sendPhoto.setChatId(chatId);
                    sendPhoto.setPhoto(new InputFile(photoId));
                    sendPhoto.setCaption(text1);
                    sendPhoto.setParseMode(ParseMode.HTML);
                    sendPhoto.setAllowSendingWithoutReply(true);
                    sendPhoto.setDisableNotification(true);
                    sendPhoto.setReplyToMessageId(update.getMessage().getMessageId());
                    sendPhoto.setProtectContent(true);


                    try {
                        execute(sendPhoto);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            if (update.getMessage().hasAudio()) {

                Audio audio = update.getMessage().getAudio();
                SendAudio sendAudio = new SendAudio();
                sendAudio.setAudio(new InputFile(audio.getFileId()));
                sendAudio.setChatId(chatId);
                sendAudio.setCaption("you sent me an audio");

                try {
                    execute(sendAudio);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

            }

            if (update.getMessage().hasVideo()) {
                Video video = update.getMessage().getVideo();
                SendVideo sendVideo = new SendVideo();
                sendVideo.setChatId(chatId);
                sendVideo.setVideo(new InputFile(video.getFileId()));
                sendVideo.setCaption("You sent me a video");

                try {
                    execute(sendVideo);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }

            if (update.getMessage().hasText()) {
                if (update.getMessage().getText().equals("/picture")) {
                    InputMedia inputMedia1 = new InputMediaPhoto("https://www.google.com/imgres?q=pdp%20academy&imgurl=https%3A%2F%2Fwww.pdp.uz%2Ffavicon.webp&imgrefurl=https%3A%2F%2Fwww.pdp.uz%2Foffline&docid=uy_fVat9XG-SaM&tbnid=pdb-K7o47vkOPM&vet=12ahUKEwi25oX5gK2OAxU7VPEDHWrcLmwQM3oECG0QAA..i&w=448&h=241&hcb=2&ved=2ahUKEwi25oX5gK2OAxU7VPEDHWrcLmwQM3oECG0QAA");
                    InputMedia inputMedia2 = new InputMediaPhoto("https://www.google.com/imgres?q=pdp%20academy&imgurl=https%3A%2F%2Favatars.mds.yandex.net%2Fget-altay%2F10095165%2F2a00000190e9f3a8d486327ef5540971cd49%2FL_height&imgrefurl=https%3A%2F%2Fyandex.ru%2Fmaps%2Forg%2Fpdp_academy%2F132435894010%2F&docid=DiVPNwnqFcBcOM&tbnid=SF0TPxvhetA7UM&vet=12ahUKEwi25oX5gK2OAxU7VPEDHWrcLmwQM3oECHwQAA..i&w=500&h=375&hcb=2&ved=2ahUKEwi25oX5gK2OAxU7VPEDHWrcLmwQM3oECHwQAA");
                    InputMedia inputMedia3 = new InputMediaPhoto("https://www.google.com/imgres?q=pdp%20academy&imgurl=https%3A%2F%2Fkursi24.uz%2Fupload%2Fiblock%2Fa9c%2Fa9ceb6c2df755b1d9ed599e3e9c2b5ce.jpg&imgrefurl=https%3A%2F%2Fkursi24.uz%2Fcentre%2Fpdp-academy&docid=9vTE1P_x6osmWM&tbnid=jO22pUSJaCYX5M&vet=12ahUKEwi25oX5gK2OAxU7VPEDHWrcLmwQM3oECD0QAA..i&w=1920&h=1080&hcb=2&ved=2ahUKEwi25oX5gK2OAxU7VPEDHWrcLmwQM3oECD0QAA");
                    InputMedia inputMedia4 = new InputMediaPhoto("https://www.google.com/imgres?q=pdp%20academy&imgurl=https%3A%2F%2Fyt3.googleusercontent.com%2FrUhSxjPcFAtfD_t1e1Pzx1mzudbB9051fg4B6lAH-Eyldd-6EC3oCF5CFGrCjeL5m9_9Kzhl%3Ds900-c-k-c0x00ffffff-no-rj&imgrefurl=https%3A%2F%2Fwww.youtube.com%2Fchannel%2FUCLRXXDGv3L_gGxUHFY8D45g%2Fvideos&docid=eIa1yKYdCS1jfM&tbnid=oYEi7yENaQPKYM&vet=12ahUKEwi25oX5gK2OAxU7VPEDHWrcLmwQM3oECE4QAA..i&w=900&h=900&hcb=2&ved=2ahUKEwi25oX5gK2OAxU7VPEDHWrcLmwQM3oECE4QAA");
                    InputMedia inputMedia5 = new InputMediaPhoto("https://www.google.com/imgres?q=pdp%20academy%20in%20java&imgurl=https%3A%2F%2Fwww.goldenpages.uz%2Fconstructor%2F99420%2Fimg%2Forg_60e889718e6213f8b3683c8d2f1324cb_1400x900.webp&imgrefurl=https%3A%2F%2Fwww.goldenpages.uz%2Fcompany%2F%3FId%3D99420&docid=IRxNfTz3pBT1gM&tbnid=p2QIe_je-8CtAM&vet=12ahUKEwjZ5e3Wga2OAxX3-QIHHQcRNVEQM3oECHkQAA..i&w=1600&h=900&hcb=2&ved=2ahUKEwjZ5e3Wga2OAxX3-QIHHQcRNVEQM3oECHkQAA");
                   List<InputMedia> media = new ArrayList<>();
                    media.add(inputMedia1);
                    media.add(inputMedia2);
                    media.add(inputMedia3);
                    media.add(inputMedia4);
                    media.add(inputMedia5);


                    SendMediaGroup sendMediaGroup = new SendMediaGroup();
                    sendMediaGroup.setMedias(media);
                    sendMediaGroup.setChatId(update.getMessage().getChatId());
                   String messageCaption = "You sent me a media group!";
                try {
                        execute(sendMediaGroup);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            if (update.getMessage().hasText()) {
                if (update.getMessage().getText().equals("/location")) {
                    SendVenue sendVenue = new SendVenue();
                    sendVenue.setChatId(chatId);
                    sendVenue.setLatitude(41.326531);
                    sendVenue.setLongitude(69.228359);
                    sendVenue.setTitle("PDP Academy");
                    sendVenue.setAddress("+1000 Bitiruvchilarimiz\n" +
                            "13 mln.dan ziyod maosh olishmoqda!");

                    try {
                        execute(sendVenue);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            Message message = update.getMessage();
            if (message.hasText()) {
                String textFile = message.getText();
                if (text.equalsIgnoreCase("/file")) {
                    SendDocument sendDocument = new SendDocument();

                    sendDocument.setCaption("✅  Ommaviy oferta shartlari ");
                    sendDocument.setChatId(chatId);
                    sendDocument.setDocument(new InputFile("https://serv.comnet.uz/oferta_blank.pdf"));
                    sendDocument.setParseMode(ParseMode.HTML);
                    sendDocument.setAllowSendingWithoutReply(true);
                    sendDocument.setDisableNotification(true);
                    sendDocument.setReplyToMessageId(message.getMessageId());
                    sendDocument.setProtectContent(true);
                    try {
                        execute(sendDocument);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    if (message.hasDocument()) {
                        Document document = message.getDocument();
                        String log = """
                                    FileId: %s,
                                    FileName: %s,
                                    FileSize: %d,
                                    Thumbnail: %s
                                    FileUniqueId: %s,
                                    FileMimeType: %s
                                    """.formatted(
                                document.getFileId(),
                                document.getFileName(),
                                document.getFileSize(),
                                document.getThumbnail(),
                                document.getFileUniqueId(),
                                document.getMimeType()
                        );
                        System.out.println(log);
                    }
                }
            }
            else if (text.startsWith("@G5607_bot")) {
                ForwardMessage forwardMessage = new ForwardMessage();
                forwardMessage.setChatId("6036441667");
                forwardMessage.setMessageId(message.getMessageId());
                forwardMessage.setFromChatId(message.getChatId());
                try {
                    execute(forwardMessage);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
                SendMessage sendMessage = new SendMessage();
                sendMessage.setText("Xabaringiz yuborildi tez orada aloqaga chiqamiz.");
                sendMessage.setChatId(message.getChatId());
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                }
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
