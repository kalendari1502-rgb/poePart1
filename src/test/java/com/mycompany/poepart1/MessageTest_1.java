package com.mycompany.poepart1;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Student
 */
public class MessageTest_1 {

    @Test
    public void testSentMessagesArrayCorrectlyPopulated() {
        // Test Data Message 1 (Sent) and Message 4 (Sent)
        Message msg1 = new Message(0, "+27834557896", "Did you get the cake?");
        msg1.SentMessage(1); 

        Message msg4 = new Message(1, "0838884567", "It is dinner time!");
        msg4.SentMessage(1);

        ArrayList<Message> sentMessages = new ArrayList<>();
        sentMessages.add(msg1);
        sentMessages.add(msg4);

        assertEquals(2, sentMessages.size());
        assertEquals("Did you get the cake?", sentMessages.get(0).getMessageText());
        assertEquals("It is dinner time!", sentMessages.get(1).getMessageText());
    }

    @Test
    public void testDisplayLongestMessage() {
        // Test Data Message 1 - 4
        Message msg1 = new Message(0, "+27834557896", "Did you get the cake?");
        Message msg2 = new Message(1, "+27838884567", "Where are you? You are late! I have asked you to be on time.");
        Message msg3 = new Message(2, "+27834484567", "Yohoooo, I am at your gate.");
        Message msg4 = new Message(3, "0838884567", "It is dinner time!");

        ArrayList<Message> allMessages = new ArrayList<>();
        allMessages.add(msg1);
        allMessages.add(msg2);
        allMessages.add(msg3);
        allMessages.add(msg4);

        Message longest = PoePart1.getLongestMessage(allMessages);
        assertEquals("Where are you? You are late! I have asked you to be on time.", longest.getMessageText());
    }

    @Test
    public void testSearchForMessageID() {
        // Test Data Message 4
        Message msg4 = new Message(0, "0838884567", "It is dinner time!");
        ArrayList<Message> storedMessages = new ArrayList<>();
        storedMessages.add(msg4);

        Message found = PoePart1.searchByID(storedMessages, msg4.getMessageID());
        assertNotNull(found);
        assertEquals("It is dinner time!", found.getMessageText());
    }

    @Test
    public void testSearchAllMessagesForRecipient() {
        // Test Data Message 2 and Message 5
        String recipientToSearch = "+27838884567";
        Message msg2 = new Message(0, recipientToSearch, "Where are you? You are late! I have asked you to be on time.");
        Message msg5 = new Message(1, recipientToSearch, "Ok, I am leaving without you.");
        
        ArrayList<Message> allMessages = new ArrayList<>();
        allMessages.add(msg2);
        allMessages.add(msg5);

        ArrayList<Message> results = PoePart1.searchByRecipient(allMessages, recipientToSearch);
        assertEquals(2, results.size());
        assertEquals("Where are you? You are late! I have asked you to be on time.", results.get(0).getMessageText());
        assertEquals("Ok, I am leaving without you.", results.get(1).getMessageText());
    }

    @Test
    public void testDeleteMessageByHash() {
        // Test Data Message 2
        Message msg2 = new Message(0, "+27838884567", "Where are you? You are late! I have asked you to be on time.");
        ArrayList<Message> storedMessages = new ArrayList<>();
        storedMessages.add(msg2);

        boolean deleted = PoePart1.deleteByHash(storedMessages, msg2.getMessageHash());
        assertTrue(deleted);
        assertEquals(0, storedMessages.size());
    }

    @Test
    public void testDisplayReport() {
        // Test Data Message 1
        Message msg1 = new Message(0, "+27834557896", "Did you get the cake?");
        ArrayList<Message> storedMessages = new ArrayList<>();
        storedMessages.add(msg1);

        String report = PoePart1.generateReport(storedMessages);
        assertTrue(report.contains(msg1.getMessageHash()));
        assertTrue(report.contains("+27834557896"));
        assertTrue(report.contains("Did you get the cake?"));
    }
}