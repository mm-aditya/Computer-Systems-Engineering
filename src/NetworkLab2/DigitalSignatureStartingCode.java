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
    BufferedReader bufferedReader = new BufferedReader(new FileReader("/src/NetworkLab2/TextFiles/"+args[0]));
    while((line= bufferedReader.readLine())!=null){
        data = data +"\n" + line;
    }
    

//TODO: generate a RSA keypair, initialize as 1024 bits, get public key and private key from this keypair.


//TODO: Calculate message digest, using MD5 hash function


//TODO: print the length of output digest byte[], compare the length of file smallSize.txt and largeSize.txt

           
//TODO: Create RSA("RSA/ECB/PKCS1Padding") cipher object and initialize is as encrypt mode, use PRIVATE key.


//TODO: encrypt digest message


//TODO: print the encrypted message (in base64format String using DatatypeConverter) 

//TODO: Create RSA("RSA/ECB/PKCS1Padding") cipher object and initialize is as decrypt mode, use PUBLIC key.           

//TODO: decrypt message

//TODO: print the decrypted message (in base64format String using DatatypeConverter), compare with origin digest 



    }

}
