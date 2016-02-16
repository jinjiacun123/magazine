package cn.com.magazine.help;

import java.io.IOException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

public class DES {  
	  
    private byte[] desKey; 
    private String key = "souh*e_i#2?0>1&5";
    public DES(String desKey) {  
        this.desKey = desKey.getBytes(); 
        this.desKey = key.getBytes();
    }  
  
    
    public byte[] desEncrypt(byte[] plainText) throws Exception {  
        SecureRandom sr = new SecureRandom();  
        byte rawKeyData[] = desKey;  
        DESKeySpec dks = new DESKeySpec(rawKeyData);  
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");  
        SecretKey key = keyFactory.generateSecret(dks);  
        Cipher cipher = Cipher.getInstance("DES");  
        cipher.init(Cipher.ENCRYPT_MODE, key, sr);  
        byte data[] = plainText;  
        byte encryptedData[] = cipher.doFinal(data); 
        return encryptedData;  
    }  
  
    public byte[] desDecrypt(byte[] encryptText) throws Exception {  
        SecureRandom sr = new SecureRandom();  
        byte rawKeyData[] = desKey;  
        DESKeySpec dks = new DESKeySpec(rawKeyData);  
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");  
        SecretKey key = keyFactory.generateSecret(dks);  
        Cipher cipher = Cipher.getInstance("DES");  
        cipher.init(Cipher.DECRYPT_MODE, key, sr);  
        byte encryptedData[] = encryptText;  
        byte decryptedData[] = cipher.doFinal(encryptedData);  
        return decryptedData;  
    }  
  
    public String encrypt(String input) throws Exception {  
        return base64Encode(desEncrypt(input.getBytes()));  
    }  
  
    public String decrypt(String input) throws Exception {  
        byte[] result = base64Decode(input);  
        return new String(desDecrypt(result));  
    }  
  
    public static String base64Encode(byte[] s) {  
        if (s == null)  
            return null;  
        BASE64Encoder b = new BASE64Encoder();  
        return b.encode(s);  
    }  
  
    public static byte[] base64Decode(String s) throws IOException {  
        if (s == null)  
            return null;  
        BASE64Decoder decoder = new BASE64Decoder();  
        byte[] b = decoder.decodeBuffer(s);  
        return b;  
    }  
}