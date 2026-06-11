/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.chatapppart1;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


/**
 *
 * @author luphi
 */
public class MessageTest {
    
    @Test
    public void testMessageLengthValid(){
        
        Message msg = new Message(
                
                1,
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?"
        );
        
        assertEquals(
                
                "Message ready to send.",
                msg.checkMessageLength()
        );
        
    }
    
     @Test
    public void testRecipientNumberFail() {

        Message msg = new Message(
                1,
                "0831234567",
                "Hello"
        );

        assertEquals(
                "Cell phone number is incorrectly formatted or does not contain an international code.",
                msg.checkRecipientCell()
        );
    }

     @Test
    public void testMessageLengthSuccess() {

        Message msg = new Message(
                1,
                "+27831234567",
                "Short message"
        );

        assertEquals(
                "Message ready to send.",
                msg.checkMessageLength()
        );
    }

    
    
    
    
    @Test
    public void testMessageHash(){
        
        Message msg = new Message(
                0,
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?"
        );
        
        String hash = msg.createMessageHash();
        
        assertTrue(hash.contains(":0:HITONIGHT"));
    }


    @Test
    public void testDisplayLongestMessage(){
        
        Message.addStoredMessage(
        "Where are you? You are late! I have asked you to be on time.",
                Message.displayLongestMessage()
                        );            
    }

    @Test
    public void testSearchByRecipient(){
        
        Message msg = new Message(
                1,
                "+27838884567",
                "Ok, I am leaving without you."
        );

        Message.storeMessage(msg);

        String result =
                Message.searchByRecipient("+27838884567");
        
        System.out.println("Search Result: " + result);

        assertTrue(
                result.contains("Ok, I am leaving without you.")
        );
    }
    
    @Test
    public void testDeleteByHash(){
        
        String result =
                Message.deleteByHash("00:1:HITONIGHT");
        
        asserTrue(
        result.contains("successfully deleted")
        );
    }

    private void asserTrue(boolean contains) {
    }
}

