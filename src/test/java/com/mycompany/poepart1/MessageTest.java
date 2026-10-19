package com.mycompany.poepart1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @Test
    public void testCreateMessageHash() {
        // Test Case 1 from the brief
        Message msg = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("00:0:HITONIGHT", msg.createMessageHash());
    }

    @Test
    public void testCheckRecipientCellSuccess() {
        Message msg = new Message(0, "+27718693002", "Test");
        assertEquals("Cell phone number successfully captured.", msg.checkRecipientCell());
    }

    @Test
    public void testCheckRecipientCellFailure() {
        Message msg = new Message(0, "08575975889", "Test");
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", msg.checkRecipientCell());
    }

    @Test
    public void testSentMessage() {
        Message msg = new Message(0, "+27718693002", "Test");
        assertEquals("Message successfully sent.", msg.SentMessage(1));
        assertEquals("Press 0 to delete the message.", msg.SentMessage(2));
        assertEquals("Message successfully stored.", msg.SentMessage(3));
    }
}