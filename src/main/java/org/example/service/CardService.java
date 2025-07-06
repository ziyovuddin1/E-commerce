/**
 * 💳 Professional CardService
 */

package org.example.service;

import org.example.model.Card;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CardService {
    private static final String FILE_NAME = "cards.dat";

    public static void addCard(Card card) {
        List<Card> cards = getAllCards();
        cards.add(card);
        save(cards);
    }

    public static List<Card> getCardsByUserId(String userId) {
        return getAllCards()
                .stream()
                .filter(c -> c.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public static List<Card> getAllCards() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                return (List<Card>) obj;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private static void save(List<Card> cards) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(cards);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String formatCardsForInline(String userId) {
        StringBuilder sb = new StringBuilder();
        List<Card> cards = getCardsByUserId(userId);
        if (cards.isEmpty()) {
            return "🚫 Bu foydalanuvchiga tegishli kartalar topilmadi.";
        }
        cards.forEach(c -> sb.append(c.toString()).append("\n"));
        return sb.toString();
    }

    public static String formatAllCards() {
        StringBuilder sb = new StringBuilder();
        List<Card> cards = getAllCards();
        if (cards.isEmpty()) {
            return "🚫 Hech qanday karta topilmadi.";
        }
        cards.forEach(c -> sb.append(c.toString()).append("\n"));
        return sb.toString();
    }
}
