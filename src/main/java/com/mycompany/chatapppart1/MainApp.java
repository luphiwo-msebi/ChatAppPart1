/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.chatapppart1;


/**
 *
 * @author Student
 */
   





import java.util.ArrayList;
import java.util.Scanner;

@SuppressWarnings("empty-statement")

public class MainApp {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        // Store messages
        ArrayList<Message> messages = new ArrayList<>();

        // ================= USER DETAILS =================
        System.out.print("Enter first name: ");
        String firstName = input.nextLine();

        System.out.print("Enter last name: ");
        String lastName = input.nextLine();

        login user = new login(firstName, lastName);

        // ================= USERNAME =================
        String username;

        while (true) {

            System.out.print("Enter username: ");
            username = input.nextLine();

            String message = user.registerUsername(username);
            System.out.println(message);

            if (user.checkUserName(username)) {
                break;
            }
        }

        // ================= PASSWORD =================
        String password;

        while (true) {

            System.out.print("Enter password: ");
            password = input.nextLine();

            String message = user.registerPassword(password);
            System.out.println(message);

            if (user.checkPasswordComplexity(password)) {
                break;
            }
        }

        // ================= PHONE NUMBER =================
        String phone;

        while (true) {

            System.out.print("Enter phone number (e.g. +27831234567): ");
            phone = input.nextLine();

            String message = user.registerCellPhoneNumber(phone);
            System.out.println(message);

            if (user.checkCellPhoneNumber(phone)) {
                break;
            }
        }

        // ================= LOGIN =================
        System.out.println("\n--- LOGIN ---");
        
        Message.loadStoredMessages();

        while (true) {

            System.out.print("Enter username: ");
            String loginUser = input.nextLine();

            System.out.print("Enter password: ");
            String loginPass = input.nextLine();

            String message = user.returnLoginStatus(loginUser, loginPass);
            System.out.println(message);

            if (user.loginUser(loginUser, loginPass)) {
                break;
            }
        }

        // ================= PART 2 MENU =================
        boolean running = true;

        while (running) {

            System.out.println("\n===== MENU =====");
            System.out.println("1) Send Messages");
            System.out.println("2) Show sent messages");
            System.out.println("3) Quit");
            System.out.println("4) Stored Messages"); // <-- add this line

            System.out.print("Choose an option: ");

            int choice = input.nextInt();
            input.nextLine(); // clear buffer

            switch (choice) {

                case 1:

                    System.out.print("How many messages do you want to send? ");
                    int count = input.nextInt();
                    input.nextLine();

                    for (int i = 0; i < count; i++) {

                        System.out.println("\nMessage " + (i + 1));

                        System.out.print("Enter recipient (+27...): ");
                        String recipient = input.nextLine();

                        System.out.print("Enter message: ");
                        String text = input.nextLine();

                        Message msg = new Message(
                                i + 1,
                                recipient,
                                text
                        );

                        // Validate recipient
                        System.out.println(
                                msg.checkRecipientCell()
                        );

                        // Validate message length
                        System.out.println(
                                msg.checkMessageLength()
                        );

                        // Print message details
                        System.out.println(
                                msg.printMessageDetails()
                        );

                        // Add to ArrayList
                        messages.add(msg);

                        // Send/store/disregard
                        System.out.println(
                                msg.sentMessage()
                        );
                    }

                    break;

                case 2:

                    if (messages.isEmpty()) {

                        System.out.println(
                                "No messages sent yet."
                        );

                    } else {

                        System.out.println(
                                "\n=== SENT MESSAGES ==="
                        );

                        for (Message m : messages) {

                            System.out.println(
                                    m.printMessages()
                            );
                        }
                    }

                    break;

                case 3:

                    System.out.println(
                            "Application closing..."
                    );

                    running = false;

                    break;
            
            
                    
               case 4:

            Message.storedMessageMenu();
            break;

        default:

            System.out.println(
                    "Please enter option 1, 2, 3 or 4."
            );
            }
        }
        }
        
    }

    