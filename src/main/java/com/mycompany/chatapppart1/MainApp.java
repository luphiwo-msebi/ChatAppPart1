/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.chatapppart1;


/**
 *
 * @author Student
 */
   

import java.util.Scanner;



@SuppressWarnings("empty-statement")
public class MainApp {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

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
    }
}