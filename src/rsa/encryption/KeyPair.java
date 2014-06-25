
package rsa.encryption;

import java.util.*;
import java.math.BigInteger;
import java.io.*;

/**
 * RSA KeyPair generation.
 * @author reflekt2
 */
public class KeyPair {
    
    Random rand1 = new Random(System.currentTimeMillis());
    Random rand2 = new Random(System.currentTimeMillis()*10);

    BigInteger P = BigInteger.probablePrime(64, rand1);
    BigInteger Q = BigInteger.probablePrime(64, rand2);

    BigInteger p1 = P.subtract(new BigInteger ("1"));
    BigInteger q1 = Q.subtract(new BigInteger ("1"));

    BigInteger Phi = p1.multiply(q1);

    
    /**
     * KeyPair Generation
     * @throws IOException 
     */
    public void generation()
            throws IOException
    {
        // File allocation
        File efile = new File("PublicKey.txt");
        File dfile = new File("PrivateKey.txt");
        File nfile = new File("n.txt");
        
        // File writing streams and buffers
        FileWriter estream = new FileWriter(efile);
        FileWriter dstream = new FileWriter(dfile);
        FileWriter nstream = new FileWriter(nfile);
        BufferedWriter pubout = new BufferedWriter(estream);
        BufferedWriter priout = new BufferedWriter(dstream);
        BufferedWriter nout = new BufferedWriter(nstream);

        // random number and initialization of the odd public key
        Random rand3 = new Random(50);
        BigInteger e = BigInteger.probablePrime(32, rand3);
        e = (e.multiply(BigInteger.TEN))
                .subtract(BigInteger.ONE);

        // loop to find an appropriate public key
        while (true){

            // greatest common denominator variable
            BigInteger gcd = Phi.gcd(e);

            // check for appropriate gcd
            if(gcd.equals(BigInteger.ONE)){
                
                // private key and n calculation
                BigInteger d = e.modInverse(Phi);
                BigInteger n = P.multiply(Q);
                
                // public, private, and n file write-outs
                pubout.write(e.toString());
                pubout.close();
                priout.write(d.toString());
                priout.close();
                nout.write(n.toString());
                nout.close();
                
                // display the keys along with n
                System.out.println("\nPublicKey: \n" + "\t" + e
                        + "\n\t" + n + "\n\n" + "\nPrivateKey: \n"
                        + "\t" + d + "\n\t" + n);
                break;

            }

            // otherwise increment by one and continue through the loop
            e.add(BigInteger.ONE);
        }

    }
  
    
    /**
     * Encryption Method.
     * @param Input - an input string of numbers
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public void encrypt(String Input)
            throws FileNotFoundException,
            IOException
    {
        // file allocation with writing tools
        File cfile = new File("EncryptedMessage.txt");
        FileWriter cstream = new FileWriter(cfile.getAbsoluteFile());
        BufferedWriter cout = new BufferedWriter(cstream);
        
        // buffered readers
        BufferedReader pubread = new BufferedReader(
                new FileReader("PublicKey.txt"));
        BufferedReader nread = new BufferedReader(
                new FileReader("n.txt"));
        
        // read to strings
        String pubKey = pubread.readLine();
        String N = nread.readLine();
        
        // string to BigIntegers
        BigInteger pub = new BigInteger(pubKey);
        BigInteger n = new BigInteger(N);
        
        // encryption
        BigInteger m = new BigInteger(""+Input);
        BigInteger c = m.modPow(pub, n);
        
        // encrypted message write out with display
        cout.write(c.toString());
        cout.close();
        System.out.println("\nEncrypted Message: " + c);
        
    }
    
    
    /**
     * Decryption Method.
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public void decrypt()
            throws FileNotFoundException,
            IOException
    {
        // buffered readers
        BufferedReader priread = new BufferedReader(
                new FileReader("PrivateKey.txt"));
        BufferedReader nread = new BufferedReader(
                new FileReader("n.txt"));
        BufferedReader cread = new BufferedReader(
                new FileReader("EncryptedMessage.txt"));
        
        // read to strings
        String PRI = priread.readLine();
        String N = nread.readLine();
        String C = cread.readLine();
        
        // strings to BigIntegers
        BigInteger d = new BigInteger(PRI);
        BigInteger n = new BigInteger(N);
        BigInteger c = new BigInteger(C);
        
        // check to see if read-in correctly
//        System.out.println("\n\tTEST OF PRIVATE KEY: " + d);
//        System.out.println("\n\tTEST OF N: " + n);
//        System.out.println("\n\tTEST OF ENCRYPTED MESSAGE: " + c);
        
        // decryption with display
        BigInteger m = c.modPow(d, n);
        System.out.println("\nDecrypted Message: " + m.toString());
        
    }
}



