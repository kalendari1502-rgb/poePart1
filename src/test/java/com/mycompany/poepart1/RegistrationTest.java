package com.mycompany.poepart1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Student
 */
public class RegistrationTest {
    Registration reg = new Registration();

    @Test public void testUsernameValid() { assertTrue(reg.checkUserName("kyl_1")); }
    @Test public void testUsernameInvalid() { assertFalse(reg.checkUserName("kyle!!!!!!!")); }
    @Test public void testPasswordValid() { assertTrue(reg.checkPasswordComplexity("Ch&&sec@ke99!")); }
    @Test public void testPasswordInvalid() { assertFalse(reg.checkPasswordComplexity("password")); }
    @Test public void testCellValid() { assertTrue(reg.checkCellPhoneNumber("+27838968976")); }
    @Test public void testCellInvalid() { assertFalse(reg.checkCellPhoneNumber("08966553")); }

    @Test public void testRegisterUserSuccess() {
        assertEquals("Username successfully captured.", reg.registerUser("John", "Doe", "kyl_1", "Ch&&sec@ke99!", "+27838968976"));
    }
}