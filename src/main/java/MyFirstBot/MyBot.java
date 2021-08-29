package MyFirstBot;

import org.checkerframework.checker.units.qual.A;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ForwardMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendContact;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class MyBot extends TelegramLongPollingBot {
    private final String BOT_USERNAME = "Chat279TestBot";
    private final String BOT_TOKEN = "";
    private final String ADMIN_CHAT = "-510026915";

    private ConcurrentMap<String, User> usersHashMap;     // key = userId, value = "userName"

    public MyBot() {
        this.usersHashMap = new ConcurrentHashMap<String, User>();
    }

    private String parseArticle(String article) {
        BufferedReader reader = new BufferedReader(new StringReader(article));
        // выделение заголовка (userInfo)
        String userInfo = "";
        try {
            if (reader.ready()) {
                userInfo = reader.readLine();
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Выделение chatId
        Scanner scanner = new Scanner(userInfo);
        String chatId = "";
        while (scanner.hasNext()) {
            chatId = scanner.next();
        }
        scanner.close();;

        return chatId.substring(1, chatId.length() - 2);
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        // если сообщение из "админской группы", то
        if((update.hasMessage()) &&
                (update.getMessage().getChatId().toString().equals(ADMIN_CHAT)) &&
                (update.getMessage().isReply()) ) {

            Message message = update.getMessage();
            String chatId = update.getMessage().getReplyToMessage().getForwardFrom().getId().toString();

            if ((message != null) && (message.hasText())) {
                SendMessage sendMessage = new SendMessage();
//                sendMessage.enableMarkdownV2(true);
//                String article = String.format("\\* %s*", update.getMessage().getFrom().getFirstName());
                sendMessage.enableHtml(true);
                sendMessage.setChatId(chatId);

                String article = String.format("<b>%s</b>", update.getMessage().getFrom().getFirstName());
                String text = update.getMessage().getText();
                sendMessage.setText(String.format("%s: %s", article, text));

                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

            }
        }
//        if((update.hasMessage()) &&
//                (update.getMessage().getChatId().toString().equals(ADMIN_CHAT)) &&
//                (update.getMessage().isReply()) ) {
//            String chatId = update.getMessage().getReplyToMessage().getForwardFrom().getId().toString();
//
//            ForwardMessage forwardMessage = new ForwardMessage();
//            forwardMessage.setChatId(chatId);
//            forwardMessage.setFromChatId(update.getMessage().getChatId().toString());
//            forwardMessage.setMessageId(update.getMessage().getMessageId());
//
//
//            try {
//                execute(forwardMessage);
//            } catch (TelegramApiException e) {
//                e.printStackTrace();
//            }



//            if(update.getMessage().hasText()) {                 // если сообщение текстовое
//                SendMessage replyMessage = new SendMessage();
//
//
////                String messageToReply = update.getMessage().getReplyToMessage().getText();
////                String chatID = parseArticle(messageToReply);
////
////                String firstName = update.getMessage().getFrom().getFirstName();
////                String lastName = update.getMessage().getFrom().getLastName();
////                String userName;
////                if ((firstName != null) && (lastName != null))
////                    userName = String.format("%s %s", firstName, lastName);
////                else if (firstName != null)
////                    userName = firstName;
////                else
////                    userName = lastName;
////
////                replyMessage.setChatId(chatID);
////                replyMessage.setText(String.format("%s:\n%s", userName, update.getMessage().getText()));
//                replyMessage.setChatId(chatId);
//                replyMessage.setText(update.getMessage().getText());
//
//                try {
//                    execute(replyMessage);
//                } catch (TelegramApiException e) {
//                    e.printStackTrace();
//                }
//            }


        // * * * * *
        // если сообщение отправил не администратор
        if( (update.hasMessage()) && (!update.getMessage().getChatId().toString().equals(ADMIN_CHAT)) ) {
            if(update.getMessage().isCommand()) {
                return;
            }

            ForwardMessage forwardMessage = new ForwardMessage();
            forwardMessage.setChatId(ADMIN_CHAT);
            forwardMessage.setFromChatId(update.getMessage().getChatId().toString());
            forwardMessage.setMessageId(update.getMessage().getMessageId());

            try {
                execute(forwardMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

//            boolean isVoice = update.getMessage().hasVoice();
//            boolean isInvoice = update.getMessage().hasInvoice();
//            boolean isPhoto = update.getMessage().hasPhoto();
//            boolean isAudio = update.getMessage().hasAudio();
//            boolean isLocation = update.getMessage().hasLocation();
//
//
//            User user = update.getMessage().getFrom();
//            if(!usersHashMap.containsKey(user.getId().toString())) {
//                usersHashMap.put(user.getId().toString(), user);
//            } else {
//                if(!usersHashMap.get(user.getId().toString()).equals(user))
//                    usersHashMap.replace(user.getId().toString(), user);
//            }
//
//            if(update.getMessage().hasText()) {                 // текстовое сообщение (текст + смайлики)
//                SendMessage sendMessage = new SendMessage();
//
//                sendMessage.setChatId(ADMIN_CHAT);
////                String text = String.format("%s (message from: %s)", update.getMessage().getText(),
////                                                                     update.getMessage().getChatId().toString());
//                String userName = user.getUserName();
//                String firstName = user.getFirstName();
//                String lastName = user.getLastName();
//                String userId = user.getId().toString();
//                StringBuilder userInfo = new StringBuilder("");
//                if(userName != null) {
//                    userInfo.append("@" + userName);
//                    userInfo.append(" (" + userId + ")");
//                }
//                else {
//                    if (firstName != null)
//                        userInfo.append(firstName + " ");
//                    if (lastName != null)
//                        userInfo.append(lastName);
//                    userInfo.append(" (" + userId + ")");
//                }
//
//                String text = String.format("%s:\n%s", userInfo.toString(),
//                                                              update.getMessage().getText());
//                sendMessage.setText(text);
//
//                try {
//                    execute(sendMessage);
//                    return;
//                } catch (TelegramApiException e) {
//                    e.printStackTrace();
//                }
//            } else if(update.getMessage().hasContact()) {       // контакт
//                SendContact contactMessage = new SendContact();
//                Contact contact = update.getMessage().getContact();
//
//                contactMessage.setChatId(ADMIN_CHAT);
//                contactMessage.setPhoneNumber(contact.getPhoneNumber());
//                contactMessage.setFirstName(contact.getFirstName());
//                contactMessage.setLastName(contact.getLastName());
//
//                try {
//                    execute(contactMessage);
//                    return;
//                } catch (TelegramApiException e) {
//                    e.printStackTrace();
//                }
//
//            } else if(update.getMessage().hasPhoto()) {
//                ForwardMessage forwardMessage = new ForwardMessage();
//                forwardMessage.setChatId(ADMIN_CHAT);
//                forwardMessage.setFromChatId(update.getMessage().getChatId().toString());
//                forwardMessage.setMessageId(update.getMessage().getMessageId());
//
//                try {
//                    execute(forwardMessage);
//                } catch (TelegramApiException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            else {
//                SendMessage unknownMessage = new SendMessage();
//                unknownMessage.setChatId(ADMIN_CHAT);                   // РѕС‚РїСЂР°РІРєР° СЃРѕРѕР±С‰РµРЅРёСЏ РІ "Р°РґРјРёРЅСЃРєРёР№" С‡Р°С‚
//                unknownMessage.setText("Тип сообщения не известен!");
//
//                try {
//                    execute(unknownMessage);
//                    return;
//                } catch (TelegramApiException e) {
//                    e.printStackTrace();
//                }
//            }

        }
    }
}
