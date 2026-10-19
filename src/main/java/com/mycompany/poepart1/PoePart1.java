package com.mycompany.poepart1;

import java.util.Scanner;

public class PoePart1 {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
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
        }
    }
}