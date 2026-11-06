package com.mycompany.poepart1;

import java.util.ArrayList;
import java.util.Scanner;

public class PoePart1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

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

        System.out.println("\nWelcome to QuickChat.");
        
        boolean running = true;
        int numMessagesToSend = 0;
        int messagesSentCount = 0;

        ArrayList<Message> sentMessages = new ArrayList<>();
        ArrayList<Message> disregardedMessages = new ArrayList<>();
        ArrayList<Message> storedMessages = new ArrayList<>();

        while (running) {
            System.out.println("\n--- QuickChat Menu ---");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Stored Messages");
            System.out.println("4) Quit");
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
                            break; 
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
                        
                        int action = 1;
                        try {
                            action = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Defaulting to Disregard.");
                            action = 2;
                        }

                        String actionResult = msg.SentMessage(action);
                        System.out.println(actionResult);

                        if (action == 1) {
                            sentMessages.add(msg);
                            messagesSentCount++; 
                        } else if (action == 2) {
                            disregardedMessages.add(msg);
                        } else if (action == 3) {
                            storedMessages.add(msg);
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
                    storedMessages.clear();
                    storedMessages.addAll(Message.loadMessagesFromJson());
                    manageStoredMessages(scanner, storedMessages);
                    break;

                case "4":
                    System.out.println("Exiting QuickChat. Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option. Please choose 1, 2, 3, or 4.");
            }
        }

        scanner.close();
    }

    private static void manageStoredMessages(Scanner scanner, ArrayList<Message> storedMessages) {
        boolean inSubMenu = true;
        while (inSubMenu) {
            System.out.println("\n--- Stored Messages Menu ---");
            System.out.println("1) Display sender and recipient of all stored messages");
            System.out.println("2) Display the longest stored message");
            System.out.println("3) Search for a message ID");
            System.out.println("4) Search for all messages for a recipient");
            System.out.println("5) Delete a message using the message hash");
            System.out.println("6) Display full report of all stored messages");
            System.out.println("7) Back to Main Menu");
            System.out.print("Choose an option: ");
            String subChoice = scanner.nextLine();

            switch (subChoice) {
                case "1":
                    System.out.println("\n--- Senders and Recipients ---");
                    if (storedMessages.isEmpty()) {
                        System.out.println("No stored messages.");
                    } else {
                        for (Message m : storedMessages) {
                            System.out.println("Sender: Current User | Recipient: " + m.getRecipient());
                        }
                    }
                    break;
                case "2":
                    Message longest = getLongestMessage(storedMessages);
                    if (longest != null) {
                        System.out.println("Longest Message: \"" + longest.getMessageText() + "\"");
                    } else {
                        System.out.println("No stored messages.");
                    }
                    break;
                case "3":
                    System.out.print("Enter Message ID to search: ");
                    String searchId = scanner.nextLine();
                    Message foundId = searchByID(storedMessages, searchId);
                    if (foundId != null) {
                        System.out.println("Recipient: " + foundId.getRecipient());
                        System.out.println("Message: " + foundId.getMessageText());
                    } else {
                        System.out.println("Message ID not found.");
                    }
                    break;
                case "4":
                    System.out.print("Enter Recipient to search (e.g., +27838884567): ");
                    String searchRec = scanner.nextLine();
                    ArrayList<Message> foundRecs = searchByRecipient(storedMessages, searchRec);
                    if (foundRecs.isEmpty()) {
                        System.out.println("No messages found for this recipient.");
                    } else {
                        for (Message m : foundRecs) {
                            System.out.println("Message: " + m.getMessageText());
                        }
                    }
                    break;
                case "5":
                    System.out.print("Enter Message Hash to delete: ");
                    String searchHash = scanner.nextLine();
                    if (deleteByHash(storedMessages, searchHash)) {
                        Message.saveAllMessagesToJson(storedMessages);
                    } else {
                        System.out.println("Message Hash not found.");
                    }
                    break;
                case "6":
                    System.out.println(generateReport(storedMessages));
                    break;
                case "7":
                    inSubMenu = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    public static Message getLongestMessage(ArrayList<Message> messages) {
        if (messages.isEmpty()) return null;
        Message longest = messages.get(0);
        for (Message m : messages) {
            if (m.getMessageText().length() > longest.getMessageText().length()) {
                longest = m;
            }
        }
        return longest;
    }

    public static Message searchByID(ArrayList<Message> messages, String id) {
        for (Message m : messages) {
            if (m.getMessageID().equals(id)) {
                return m;
            }
        }
        return null;
    }

    public static ArrayList<Message> searchByRecipient(ArrayList<Message> messages, String recipient) {
        ArrayList<Message> results = new ArrayList<>();
        for (Message m : messages) {
            if (m.getRecipient().equals(recipient)) {
                results.add(m);
            }
        }
        return results;
    }

    public static boolean deleteByHash(ArrayList<Message> messages, String hash) {
        for (int i = 0; i < messages.size(); i++) {
            if (messages.get(i).getMessageHash().equals(hash)) {
                System.out.println("Message: \"" + messages.get(i).getMessageText() + "\" successfully deleted.");
                messages.remove(i);
                return true;
            }
        }
        return false;
    }

    public static String generateReport(ArrayList<Message> messages) {
        StringBuilder report = new StringBuilder("\n--- Full Stored Messages Report ---\n");
        if (messages.isEmpty()) {
            report.append("No stored messages.\n");
        } else {
            for (Message m : messages) {
                report.append("Hash: ").append(m.getMessageHash()).append("\n");
                report.append("Recipient: ").append(m.getRecipient()).append("\n");
                report.append("Message: ").append(m.getMessageText()).append("\n");
                report.append("-------------------------\n");
            }
        }
        return report.toString();
    }
}