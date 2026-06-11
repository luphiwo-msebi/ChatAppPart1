/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.chatapppart1;

import java.util.Random;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileReader;

public class Message {

    static void addStoredMessage(String where_are_you_You_are_late_I_have_asked_y, String displayLongestMessage) {
    }

    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;

    private static List<String> sentMessages = new ArrayList<>();
    private static List<String> disregardedMessages = new ArrayList<>();
    private static List<String> storedMessages = new ArrayList<>();
    private static List<String> messageHashes = new ArrayList<>();
    private static List<String> messageIDs = new ArrayList<>();
    private static List<String> recipients = new ArrayList<>();

    private static int totalMessages = 0;

    public Message(int messageNumber, String recipient, String messageText) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;

        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }

    private String generateMessageID() {
        Random random = new Random();
        // Fixed: Added missing semicolon to the line below
        long number = 1000000000L + (long) (random.nextDouble() * 9000000000L);
        return String.valueOf(number);
    }

    public boolean checkMessageID() {
        return messageID != null && messageID.length() == 10;
    }

    public String checkRecipientCell() {
        if (recipient != null && recipient.matches("^\\+\\d{10,15}$")) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code.";
        }
    }

    public String checkMessageLength() {
        if (messageText == null) {
            return "Message cannot be empty.";
        }

        if (messageText.length() <= 250) {
            return "Message ready to send.";
        }

        int over = messageText.length() - 250;

        return "Message exceeds 250 characters by " + over + ", please reduce the size.";
    }

    public String createMessageHash() {
        if (messageText == null || messageText.trim().isEmpty()) {
            return "INVALID";
        }

        String idPart = messageID.substring(0, 2);
        String[] words = messageText.trim().split("\\s+");

        String firstWord = words[0];
        String lastWord = words[words.length - 1];

        String hash = idPart + ":" + messageNumber + ":" + firstWord + lastWord;

        return hash.toUpperCase();
    }

    public String sentMessage() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nWhat would you like to do?");
        System.out.println("1) Send Message");
        System.out.println("2) Disregard Message");
        System.out.println("3) Store Message");

        int option;

        try {
            option = scanner.nextInt();
        } catch (Exception e) {
            return "Invalid input.";
        }

        switch (option) {
            case 1:
                sentMessages.add(messageText);
                messageHashes.add(messageHash);
                messageIDs.add(messageID);
                recipients.add(recipient);

                totalMessages++;
                return "Message successfully sent.";

            case 2:
                disregardedMessages.add(messageText);
                return "Message disregarded.";

            case 3:
                storedMessages.add(messageText);
                messageIDs.add(messageID);
                messageHashes.add(messageHash);
                recipients.add(recipient);

                storeMessage(); // Calls the JSON file writer
                return "Message successfully stored.";   

            default:
                return "Invalid option.";
        }
    }

    public static String displayLongestMessage() {
        if (storedMessages.isEmpty()) {
            return "No stored messages.";
        }

        String longest = storedMessages.get(0);

        for (String msg : storedMessages) {
            if (msg.length() > longest.length()) {
                longest = msg;
            }
        }

        return longest;
    }

    // Fixed: Standardized object method to route saving routine properly
   public static void storeMessage(Message msg) {

    if (msg == null) {
        return;
    }

    // Store in memory for searching
    storedMessages.add(msg.messageText);
    recipients.add(msg.recipient);
    messageIDs.add(msg.messageID);
    messageHashes.add(msg.messageHash);

    // Save to JSON file
    msg.storeMessage();
}

    public void storeMessage() {
        JSONObject obj = new JSONObject();

        obj.put("messageID", messageID);
        obj.put("messageHash", messageHash);
        obj.put("recipient", recipient);
        obj.put("message", messageText);

        try (FileWriter fw = new FileWriter("messages.json", true)) {
            fw.write(obj.toString());
            fw.write(System.lineSeparator());
        } catch (IOException e) {
            System.out.println("Error writing to JSON file.");
        }
    }

    public String printMessageDetails() {
        return "\nMessage ID: " + messageID
                + "\nMessage Hash: " + messageHash
                + "\nRecipient: " + recipient
                + "\nMessage: " + messageText;
    }

    public static int returnTotalMessages() {
        return totalMessages;
    }

    public String getMessageID() {
        return messageID;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public static void loadStoredMessages() {
        storedMessages.clear();
        messageIDs.clear();
        messageHashes.clear();
        recipients.clear();
        
        try (BufferedReader reader = new BufferedReader(new FileReader("messages.json"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                JSONObject obj = new JSONObject(line);

                storedMessages.add(obj.getString("message"));
                messageIDs.add(obj.getString("messageID"));
                messageHashes.add(obj.getString("messageHash"));
                recipients.add(obj.getString("recipient"));   
            }
        } catch (Exception e) {
            System.out.println("No stored messages found.");
        }
    }

    public static String getStoredMessages() {
        if (storedMessages.isEmpty()) {
            return "No stored messages.";
        }

        StringBuilder result = new StringBuilder();
        for (String msg : storedMessages) {
            result.append(msg).append("\n");
        }

        return result.toString();
    }

    // Fixed: Pulled message content from storedMessages instead of sentMessages
    public static String searchByMessageID(String id) {
        for (int i = 0; i < messageIDs.size(); i++) {
            if (messageIDs.get(i).equals(id)) {
                return "Message Found:\n"
                        + "ID: " + messageIDs.get(i) + "\n"
                        + "Recipient: " + recipients.get(i) + "\n"
                        + "Message: " + storedMessages.get(i);
            }
        }
        return "Message not found.";
    }

    // Fixed: Pulls content from storedMessages list
    public static String searchByRecipient(String recipient) {
        StringBuilder results = new StringBuilder();

        for (int i = 0; i < recipients.size(); i++) {
            if (recipients.get(i).equals(recipient)) {
                results.append(storedMessages.get(i)).append("\n");
            }
        }

        return results.length() > 0 ? results.toString() : "No messages found for this recipient.";
    }

    // Fixed: Correctly handles removing indexing data from storedMessages list
    public static String deleteByHash(String hash) {
        for (int i = 0; i < messageHashes.size(); i++) {
            if (messageHashes.get(i).equals(hash)) {
                String deleted = storedMessages.get(i);

                messageHashes.remove(i);
                messageIDs.remove(i);
                recipients.remove(i);
                storedMessages.remove(i); // Fixed target tracking array

                return "Message: \"" + deleted + "\" successfully deleted from memory.";
            }
        }
        return "Hash not found.";
    }

    // Fixed: Changed print tracking configuration to target stored data
    public static String printMessages() {
        StringBuilder report = new StringBuilder();
        report.append("=== Message Report ===\n");

        if (storedMessages.isEmpty()) {
            report.append("No saved messages to report.\n");
            return report.toString();
        }

        for (int i = 0; i < storedMessages.size(); i++) {
            report.append("----------------------\n");
            report.append("Message ID: ").append(messageIDs.get(i)).append("\n");
            report.append("Hash: ").append(messageHashes.get(i)).append("\n");
            report.append("Recipient: ").append(recipients.get(i)).append("\n");
            report.append("Message: ").append(storedMessages.get(i)).append("\n");
        }

        return report.toString();
    }

    public static void storedMessageMenu() {
        Scanner scanner = new Scanner(System.in);
        char option;

        do {
            System.out.println("\na) Display all stored messages");
            System.out.println("b) Display longest message");
            System.out.println("c) Search by message ID");
            System.out.println("d) Search by recipient");
            System.out.println("e) Delete by hash");
            System.out.println("f) Display report");
            System.out.println("g) Back");

            option = scanner.next().charAt(0);

            switch (option) {
                case 'a':
                    System.out.println(getStoredMessages());
                    break;
                case 'b':
                    System.out.println(displayLongestMessage());
                    break;
                case 'c':
                    System.out.print("Enter ID: ");
                    System.out.println(searchByMessageID(scanner.next()));
                    break;
                case 'd':
                    System.out.print("Enter Recipient: ");
                    String recipient = scanner.next();
                    System.out.println(searchByRecipient(recipient));
                    break;
                case 'e':
                    System.out.print("Enter Hash: ");
                    System.out.println(deleteByHash(scanner.next()));
                    break;
                case 'f':
                    System.out.println(printMessages());
                    break;
            }
        } while (option != 'g');
    }
}