package com.jebysun.android.appcommon.util;

import android.text.TextUtils;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class AESBase64EncodeUtil {

    private static final String AES_KEY = "m3f12dcuzsoo3ye3";

    public static String encode(String src) {
        return encrypt(AES_KEY, src);
    }

    // 加密
    public static String encrypt(String sKey, String sSrc) {
        if (TextUtils.isEmpty(sKey) || TextUtils.isEmpty(sSrc)) {
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            return null;
        }

        Cipher cipher;
        byte[] encrypted = null;
        try {
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            // "算法/模式/补码方式"
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        // 此处使用BASE64做转码功能，同时能起到2次加密的作用。
        return Base64.encodeToString(encrypted, Base64.NO_WRAP);
    }
}
