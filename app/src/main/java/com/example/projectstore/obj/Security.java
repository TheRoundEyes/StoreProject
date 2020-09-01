package com.example.projectstore.obj;

import android.util.Base64;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Security {
    private final String ALGORITHM = "AES";

    private SecretKeySpec generateKey(String key_) {
        SecretKeySpec key = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(key_.getBytes("UTF-8"));
            byte[] bytes = digest.digest();
            key = new SecretKeySpec(bytes, ALGORITHM);
        }
        catch(Exception e) {}
        return key;
    }

    public String encryptData(String password, String key_) {
        String val = "";
        try {
            SecretKeySpec key = generateKey(key_);
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] value = c.doFinal(password.getBytes());
            val = Base64.encodeToString(value, Base64.DEFAULT);
        }
        catch(Exception e) {}
        return val;
    }

    public String decryptData(String encryptedValue, String key_) {
        String val = "";
        try {
            SecretKeySpec key = generateKey(key_);
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.DECRYPT_MODE, key);
            byte[] decodeValue = Base64.decode(encryptedValue, Base64.DEFAULT);
            byte[] value = c.doFinal(decodeValue);
            val = new String(value);
        }
        catch(Exception e) {}
        return val;
    }
}
