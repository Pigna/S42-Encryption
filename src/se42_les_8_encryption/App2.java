/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se42_les_8_encryption;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.security.PublicKey;
import java.security.Signature;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.security.*;
import java.security.spec.*;

/**
 *
 * @author myron
 */
public class App2 extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
public static void main(String[] args) {
 
    String finalString;
    args = new String[4];
    args[3] = "Pjotter";
    args[2] = "test.txt";
    args[1] = "sig";
    args[0] = "suepk";
        /* Verify a DSA signature */
 
        if (args.length != 4) {
            System.out.println("Usage: VerSig publickeyfile signaturefile datafile");
            }
        else try{
 
            /* import encoded public key */
 
            FileInputStream keyfis = new FileInputStream(args[0]);
            byte[] encKey = new byte[keyfis.available()];  
            keyfis.read(encKey);
 
            keyfis.close();
 
            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);
 
            KeyFactory keyFactory = KeyFactory.getInstance("DSA", "SUN");
            PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);
 
            /* input the signature bytes */
            FileInputStream sigfis = new FileInputStream(args[1]);
            byte[] sigToVerify = new byte[sigfis.available()]; 
            sigfis.read(sigToVerify );
 
            sigfis.close();
 
            /* create a Signature object and initialize it with the public key */
            Signature sig = Signature.getInstance("SHA1withDSA", "SUN");
            sig.initVerify(pubKey);
 
            /* Update and verify the data */
 
            FileInputStream datafis = new FileInputStream(args[2]);
            BufferedInputStream bufin = new BufferedInputStream(datafis);
 
            byte[] buffer = new byte[1024];
            int len;            
            while (bufin.available() != 0) {
                len = bufin.read(buffer);
                sig.update(buffer, 0, len);
                };
 
            bufin.close();
            
            byte[] realSig = sig.sign();
 
 
            boolean verifies = sig.verify(sigToVerify);
 
            System.out.println("signature verifies: " + verifies);
            
            
            BufferedReader br = new BufferedReader(new FileReader(args[2]));
            try{
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();
                while (line != null){
                    sb.append(line);
                    line = br.readLine();
                }
                finalString = sb.toString();
            }
            finally{
                br.close();
            }
            
            File file = new File(args[1]);
            FileOutputStream fos = new FileOutputStream("input(SidnetBy" + args[3] + ")");
            fos.write((int) file.length());
            fos.write(realSig);
            fos.write(finalString.getBytes());
            
            fos.close();
 
 
        }
        catch (Exception e)
        {
            System.err.println("Caught exception " + e.toString());
        };
    }
    
}
