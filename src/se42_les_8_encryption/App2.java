/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se42_les_8_encryption;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
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
 
    args = new String[3];
    args[2] = "test.txt";
    args[1] = "sig";
    args[0] = "suepk";
        /* Verify a DSA signature */
 
        if (args.length != 3) {
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
 
 
            boolean verifies = sig.verify(sigToVerify);
 
            System.out.println("signature verifies: " + verifies);
 
 
        }
        catch (Exception e)
        {
            System.err.println("Caught exception " + e.toString());
        };
    }
    
}
