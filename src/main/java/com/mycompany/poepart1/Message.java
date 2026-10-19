package com.mycompany.poepart1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class Message {

    private final String messageID;
    private final int messageNumber;
    private final String recipient;
    private final String messageText;
    private final String messageHash;
    
    private static int totalMessagesSent = 0;

    public Message(int messageNumber, String recipient, String messageText) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }

    private String generateMessageID() {
        Random random = new Random();
        long id = (long) (random.nextDouble() * 9_000_000_000L) + 1_000_000_000L;
        return String.valueOf(id);
    }

    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    public String checkRecipientCell() {
        if (recipient != null && recipient.matches("^\\+27[0-9]{9}$")) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    public String createMessageHash() {
        String cleanText = messageText.replaceAll("[^a-zA-Z\\s]", "").trim();
        String[] words = cleanText.split("\\s+");
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;

        return String.format("%02d:%d:%s%s", messageNumber, messageNumber, firstWord, lastWord).toUpperCase();
    }

    public String SentMessage(int choice) {
        switch (choice) {
            case 1 -> {
                totalMessagesSent++;
                return "Message successfully sent.";
            }
            case 2 -> {
                return "Press 0 to delete the message.";
            }
            case 3 -> {
                storeMessage();
                return "Message successfully stored.";
            }
            default -> {
                return "Invalid option.";
            }
        }
    }

    public String printMessages() {
        return "Message ID: " + messageID + "\n" +
               "Message Hash: " + messageHash + "\n" +
               "Recipient: " + recipient + "\n" +
               "Message: " + messageText;
    }

    public int returnTotalMessages() {
        return totalMessagesSent;
    }

    public void storeMessage() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<Message> existingMessages = new ArrayList<>();
        
        try (FileReader reader = new FileReader("messages.json")) {
            Type listType = new TypeToken<ArrayList<Message>>(){}.getType();
            List<Message> loaded = gson.fromJson(reader, listType);
            if (loaded != null) {
                existingMessages.addAll(loaded);
            }
        } catch (IOException e) {
            // File doesn't exist yet
        }

        existingMessages.add(this);

        try (FileWriter writer = new FileWriter("messages.json")) {
            gson.toJson(existingMessages, writer);
        } catch (IOException e) {
            System.out.println("Error saving message to JSON: " + e.getMessage());
        }
    }
}