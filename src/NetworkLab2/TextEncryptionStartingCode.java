package NetworkLab2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.*;
import javax.xml.bind.DatatypeConverter;


public class TextEncryptionStartingCode {
    static Cipher desCipher;
    static SecretKey desKey;

    public static void main(String[] args) throws Exception {

        String data = "";
        String line;
        byte[] encrypted;
        String output;
        byte[] decrypted;

        BufferedReader bufferedReader = new BufferedReader(new FileReader(System.getProperty("user.dir")+"/src/NetWorkLab2/TextFiles/" + args[0])); //args[0] is the file you are going to encrypt.
        while ((line = bufferedReader.readLine()) != null) {
            data = data + "\n" + line;
        }

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Original File is: ");
        //TODO: Print to screen contents of the file
        if(data!=null)
            System.out.println(data);

        //TODO: generate secret key using DES algorithm
        genKey();

        //TODO: create cipher object, initialize the ciphers with the given key, choose encryption mode as DES
        genCipher();

        //TODO: do encryption, by calling method Cipher.doFinal().
        encrypted = performEncrypt(data);

        System.out.println(" ");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        //TODO: print the length of output encrypted byte[], compare the length of file smallSize.txt and largeSize.txt
        System.out.println("Length of encrypted array: "+encrypted.length);

        //TODO: do format conversion. Turn the encrypted byte[] format into base64format String using DatatypeConverter
        System.out.println(new String(encrypted));
        output = DatatypeConverter.printBase64Binary(encrypted);

        //TODO: print the encrypted message (in base64format String format)
        System.out.println(output);

        //TODO: create cipher object, initialize the ciphers with the given key, choose decryption mode as DES
        //TODO: do decryption, by calling method Cipher.doFinal().
        decrypted = performDecrypt(encrypted);

        //TODO: do format conversion. Convert the decrypted byte[] to String, using "String a = new String(byte_array);"
        output = new String(decrypted);

        //TODO: print the decrypted String text and compare it with original text
        System.out.println(" ");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Decrypted text is: ");
        System.out.println(output);

        System.out.println(" ");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Is the decrypted same as original? " + output.equals(data));


        byte[] smallEncrypted = encrypted.clone();



        bufferedReader = new BufferedReader(new FileReader(System.getProperty("user.dir")+"/src/NetWorkLab2/TextFiles/" + args[1])); //args[0] is the file you are going to encrypt.
        while ((line = bufferedReader.readLine()) != null) {
            data = data + "\n" + line;
        }

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Original File is: ");
        //TODO: Print to screen contents of the file
        if(data!=null)
            System.out.println(data);

        //TODO: generate secret key using DES algorithm
        genKey();

        //TODO: create cipher object, initialize the ciphers with the given key, choose encryption mode as DES
        genCipher();

        //TODO: do encryption, by calling method Cipher.doFinal().
        encrypted = performEncrypt(data);

        System.out.println(" ");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        //TODO: print the length of output encrypted byte[], compare the length of file smallSize.txt and largeSize.txt
        System.out.println("Length of encrypted array: "+encrypted.length);

        //TODO: do format conversion. Turn the encrypted byte[] format into base64format String using DatatypeConverter
        output = DatatypeConverter.printBase64Binary(encrypted);

        //TODO: print the encrypted message (in base64format String format)
        System.out.println(output);

        //TODO: create cipher object, initialize the ciphers with the given key, choose decryption mode as DES
        //TODO: do decryption, by calling method Cipher.doFinal().
        decrypted = performDecrypt(encrypted);

        //TODO: do format conversion. Convert the decrypted byte[] to String, using "String a = new String(byte_array);"
        output = new String(decrypted);

        //TODO: print the decrypted String text and compare it with original text
        System.out.println(" ");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Decrypted text is: ");
        System.out.println(output);

        System.out.println(" ");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Is the decrypted same as original? " + output.equals(data));

        System.out.println(" ");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        System.out.println("Now comparing lengths of largefile and smallfile encrpted byte sequences");
        System.out.println("Small file bytearray length: "+smallEncrypted.length);
        System.out.println("Large file bytearray length: "+encrypted.length);
    }
       


    public static void genKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("DES");
        desKey = keyGen.generateKey();
    }

   public static void genCipher() throws NoSuchPaddingException, NoSuchAlgorithmException {
        desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
   }

   public static void setMode(String mode) throws InvalidKeyException {
        switch(mode){
            case "encrypt": desCipher.init(Cipher.ENCRYPT_MODE, desKey);
                            break;
            case "decrypt": desCipher.init(Cipher.DECRYPT_MODE, desKey);
                            break;
            default:
                System.out.println("invalid!");
                break;
        }
   }

    public static byte[] performEncrypt(String toEncryp) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        if(toEncryp!=null) {
            setMode("encrypt");
            return desCipher.doFinal(toEncryp.getBytes());
        }
        else {
            System.out.println("No string to encrypt!");
            return null;
        }
    }

    public static byte[] performDecrypt(byte[] toDecryp) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        if(toDecryp!=null) {
            setMode("decrypt");
            return desCipher.doFinal(toDecryp);
        }
        else {
            System.out.println("No byte array to decrypt!");
            return null;
        }
    }


}
