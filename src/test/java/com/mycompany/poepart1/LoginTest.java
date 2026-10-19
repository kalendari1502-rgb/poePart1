package com.mycompany.poepart1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {
    @Test public void testLoginSuccess() {
        Login login = new Login();
        login.setCredentials("John", "Doe", "kyl_1", "Ch&&sec@ke99!");
        assertTrue(login.loginUser("kyl_1", "Ch&&sec@ke99!"));
        assertEquals("Welcome John,Doe it is great to see you.", login.returnLoginStatus(true));
    }

    @Test public void testLoginFail() {
        Login login = new Login();
        login.setCredentials("John", "Doe", "kyl_1", "Ch&&sec@ke99!");
        assertFalse(login.loginUser("wrong", "wrong"));
    }
}
