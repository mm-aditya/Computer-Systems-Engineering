package NetworkLab2;

import javax.xml.bind.DatatypeConverter;
import javax.crypto.Cipher;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.*;


public class DigitalSignatureStartingCode {

    public static void main(String[] args) throws Exception {
   //Read the text file and save to String data
    String data = "";
    String line;
    BufferedReader bufferedReader = new BufferedReader(new FileReader(System.getProperty("user.dir")+"/src/NetWorkLab2/TextFiles/"+args[0]));
    while((line= bufferedReader.readLine())!=null){
        data = data +"\n" + line;
    }

    //TODO: generate a RSA keypair, initialize as 1024 bits, get public key and private key from this keypair.
    KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA"); keyGen.initialize(1024);
    KeyPair keyPair = keyGen.generateKeyPair();
    Key publicKey = keyPair.getPublic();
    Key privateKey = keyPair.getPrivate();


    //TODO: Calculate message digest, using MD5 hash function

    MessageDigest md = MessageDigest.getInstance("MD5");
    md.update(data.getBytes());
    byte[] digest = md.digest();

    //TODO: print the length of output digest byte[], compare the length of file smallSize.txt and largeSize.txt

    System.out.println("Length of digested byte array: "+digest.length);

//TODO: Create RSA("RSA/ECB/PKCS1Padding") cipher object and initialize is as encrypt mode, use PRIVATE key.

        Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        rsaCipher.init(Cipher.ENCRYPT_MODE, privateKey);

//TODO: encrypt digest message
        byte[] encrypted = rsaCipher.doFinal(digest);
        System.out.println("Length: "+encrypted.length);

//TODO: print the encrypted message (in base64format String using DatatypeConverter)
        String encryptedLegible = DatatypeConverter.printBase64Binary(encrypted);

//TODO: Create RSA("RSA/ECB/PKCS1Padding") cipher object and initialize is as decrypt mode, use PUBLIC key.
        rsaCipher.init(Cipher.DECRYPT_MODE, publicKey);

//TODO: decrypt message
        byte[] decrypted = rsaCipher.doFinal(encrypted);

//TODO: print the decrypted message (in base64format String using DatatypeConverter), compare with origin digest
        String decryptedLegible = DatatypeConverter.printBase64Binary(decrypted);
        System.out.println(decryptedLegible);

        System.out.println("\nIs decrypted same as original? "+decryptedLegible.equals(DatatypeConverter.printBase64Binary(digest)));

    }

}
