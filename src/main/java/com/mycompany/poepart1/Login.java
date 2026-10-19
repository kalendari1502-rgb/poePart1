package com.mycompany.poepart1;

public class Login {

    private String username;
    private String password;
    private String firstName;
    private String lastName;

    public boolean loginUser(String username, String password) {
        return this.username != null && this.username.equals(username) && this.password.equals(password);
    }

    public String returnLoginStatus(boolean loginSuccess) {
        if (loginSuccess) {
            return "Welcome " + firstName + "," + lastName + " it is great to see you.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }

    public void setCredentials(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }
}