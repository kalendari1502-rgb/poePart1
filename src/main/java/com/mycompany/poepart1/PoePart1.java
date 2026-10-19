package com.mycompany.poepart1;

import java.util.Scanner;

public class PoePart1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // --- PART 1: REGISTRATION & LOGIN ---
        System.out.println("=== Chat App Registration ===");
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter SA cell number (+27...): ");
        String cellPhone = scanner.nextLine();

        Registration registration = new Registration();
        String registerResult = registration.registerUser(firstName, lastName, username, password, cellPhone);
        System.out.println("\n" + registerResult);

        if (registration.getUsername() == null) {
            System.out.println("Registration failed. Exiting.");
            scanner.close();
            return;
        }

        Login login = new Login();
        login.setCredentials(registration.getFirstName(), registration.getLastName(), registration.getUsername(), registration.getPassword());

        System.out.println("\n=== Login ===");
        System.out.print("Enter username: ");
        String loginUser = scanner.nextLine();
        System.out.print("Enter password: ");
        String loginPass = scanner.nextLine();

        boolean success = login.loginUser(loginUser, loginPass);
        System.out.println(login.returnLoginStatus(success));

        if (!success) {
            scanner.close();
            return;
        }

        // --- PART 2: QUICKCHAT MENU ---
        System.out.println("\nWelcome to QuickChat.");
        
        boolean running = true;
        int numMessagesToSend = 0;
        int messagesSentCount = 0;

        while (running) {
            System.out.println("\n--- QuickChat Menu ---");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Choose an option: ");
            
            String menuChoice = scanner.nextLine();

            switch (menuChoice) {
                case "1":
                    if (numMessagesToSend == 0) {
                        System.out.print("How many messages do you wish to enter? ");
                        try {
                            numMessagesToSend = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid number. Defaulting to 1.");
                            numMessagesToSend = 1;
                        }
                    }

                    if (messagesSentCount < numMessagesToSend) {
                        System.out.print("Enter recipient cell number (e.g., +27718693002): ");
                        String recipient = scanner.nextLine();

                        System.out.print("Enter message text (max 250 chars): ");
                        String msgText = scanner.nextLine();

                        if (msgText.length() > 250) {
                            System.out.println("Please enter a message of less than 250 characters.");
                            break; // Go back to menu
                        }

                        Message msg = new Message(messagesSentCount, recipient, msgText);

                        if (!msg.checkRecipientCell().equals("Cell phone number successfully captured.")) {
                            System.out.println(msg.checkRecipientCell());
                            break; 
                        }

                        System.out.println("\nMessage generated:");
                        System.out.println(msg.printMessages());

                        System.out.println("\nWhat would you like to do?");
                        System.out.println("1) Send Message");
                        System.out.println("2) Disregard Message");
                        System.out.println("3) Store Message to send later");
                        System.out.print("Choice: ");
                        int action = Integer.parseInt(scanner.nextLine());

                        String actionResult = msg.SentMessage(action);
                        System.out.println(actionResult);

                        if (action == 1) {
                            messagesSentCount++; 
                        }

                        if (messagesSentCount >= numMessagesToSend) {
                            System.out.println("\nTotal messages sent: " + msg.returnTotalMessages());
                            numMessagesToSend = 0; 
                            messagesSentCount = 0;
                        }
                    }
                    break;

                case "2":
                    System.out.println("Coming Soon.");
                    break;

                case "3":
                    System.out.println("Exiting QuickChat. Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option. Please choose 1, 2, or 3.");
            }
        }

        scanner.close();
    }
}