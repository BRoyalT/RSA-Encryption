/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rsa.encryption;

import java.io.*;
import java.util.Scanner;
/**
 * Message of numbers encryption and decryption with prompt.
 * @author reflekt2
 */
public class RSAEncryption {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
            throws IOException
    {
        // declare KeyPair
        KeyPair aKeyPair;
        aKeyPair = new KeyPair();
        
        // encryption
        enCryption(aKeyPair);
    }
    
    public static void enCryption(KeyPair aKeyPair)
            throws IOException
    {
        // scanner declaration
            Scanner Scan = new Scanner(System.in);
            
            // prompt for string of numbers with read-in
            System.out.println("PLEASE ENTER A STRING OF NUMBERS TO BE "
                    + "ENCRYPTED: ");
            String in = Scan.nextLine();
            
            // call to generation and encryption methods of KeyPair
            aKeyPair.generation();
            aKeyPair.encrypt(in);
            
            // call to decryption method of KeyPair
            System.out.println("\nTHE MESSAGE WILL NOW BE DECRYPTED:");
            aKeyPair.decrypt();
        
        }
}

