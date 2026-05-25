/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapppart1;


import java.util.Random;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONObject;

public class Message {

    // Fields
    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;

    private static int totalMessages = 0;

    // Constructor
    public Message(int messageNumber, String recipient, String messageText) {

        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;

        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }

    // Generate random 10-digit ID
    private String generateMessageID() {

        Random random = new Random();

        long number = 1000000000L
                + (long) (random.nextDouble() * 9000000000L);

        return String.valueOf(number);
    }

    // Check ID length
    public boolean checkMessageID() {

        return messageID != null && messageID.length() == 10;
    }

    // Validate recipient number
    public String checkRecipientCell() {

        if (recipient != null
                && recipient.matches("^\\+\\d{11,12}$")) {

            return "Cell phone number successfully captured.";

        } else {

            return "Cell phone number is incorrectly formatted or does not contain an international code.";
        }
    }

    // Validate message length
    public String checkMessageLength() {

        if (messageText == null) {

            return "Message cannot be empty.";
        }

        if (messageText.length() <= 250) {

            return "Message ready to send.";

        } else {

            int over = messageText.length() - 250;

            return "Message exceeds 250 characters by "
                    + over
                    + ", please reduce the size.";
        }
    }

    // Create message hash
    public String createMessageHash() {

        if (messageText == null || messageText.trim().isEmpty()) {

            return "INVALID";
        }

        String idPart = messageID.substring(0, 2);

        String[] words = messageText.trim().split("\\s+");

        String firstWord = words[0];
        String lastWord = words[words.length - 1];

        String hash = idPart + ":" + messageNumber
                + ":" + firstWord + lastWord;

        return hash.toUpperCase();
    }

    // Send/store/disregard menu
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

                totalMessages++;
                return "Message successfully sent.";

            case 2:

                return "Message disregarded.";

            case 3:

                storeMessage();
                return "Message successfully stored.";

            default:

                return "Invalid option.";
        }
    }

    // Store message in JSON file
    public void storeMessage() {

        JSONObject obj = new JSONObject();

        obj.put("messageID", messageID);
        obj.put("messageHash", messageHash);
        obj.put("recipient", recipient);
        obj.put("message", messageText);

        try (FileWriter fw = new FileWriter("messages.json", true)) {

            fw.write(obj.toString());

            // New line after each JSON object
            fw.write(System.lineSeparator());

            System.out.println("Message stored successfully.");

        } catch (IOException e) {

            System.out.println("Error writing to JSON file.");
        }
    }

    // Print message details
    public String printMessages() {

        return "\nMessage ID: " + messageID
                + "\nMessage Hash: " + messageHash
                + "\nRecipient: " + recipient
                + "\nMessage: " + messageText;
    }

    // Return total messages
    public static int returnTotalMessages() {

        return totalMessages;
    }

    // Getters for testing
    public String getMessageID() {

        return messageID;
    }

    public String getMessageHash() {

        return messageHash;
    }
}
