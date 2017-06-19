/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se42_les_8_encryption;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

/**
 *
 * @author myron
 */
public class App3 {
    public static void main(String[] args)
    {
        
        args = new String[5];
        args[4] = "";
        args[3] = "";
        args[2] = "test.txt";
        args[1] = "sig";
        args[0] = "suepk";
        
        try
        {
            //read the public key file
            Path publicKeyPath = Paths.get(args[0]);
            byte[] keyBytes = Files.readAllBytes(publicKeyPath);
            
            //Convert bytes to public key
            PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(keyBytes));

            //Read the test.txt file
            FileInputStream fis = new FileInputStream(args[2]);
            BufferedInputStream bufin = new BufferedInputStream(fis);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bufin.read(buffer)) >= 0)
            {
//                dsa.update(buffer, 0, len);
            };
            bufin.close();
            Signature sig = null;
            sig.initVerify(publicKey);
//            if(sig.verify(s)))//Needs the signature
//            {
//                //decode file
//            }
        }
        catch(Exception ex)
        {

        };
    }
}
