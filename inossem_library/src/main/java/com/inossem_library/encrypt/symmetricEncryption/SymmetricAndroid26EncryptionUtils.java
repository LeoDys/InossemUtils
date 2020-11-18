package com.inossem_library.encrypt.symmetricEncryption;

import android.os.Build;

import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.O)
public class SymmetricAndroid26EncryptionUtils {

    public static final String TRANSFORMATION_AES_ECB_PKCS5PADDING = "AES/ECB/PKCS5Padding";

    public static final String TRANSFORMATION_AES_ECB_PKCS7PADDING = "AES/ECB/PKCS7Padding";

    public static final String TRANSFORMATION_AES_CBC_PKCS5PADDING = "AES/CBC/PKCS5Padding";

    public static final String TRANSFORMATION_AES_CBC_PKCS7PADDING = "AES/CBC/PKCS7Padding";

    public static final String ALGORITHM_AES = "AES";

    public static final String DEFAULT_AES_SECRET_KEY = "INOSSEM_IAC_0629";//此处使用最基础的AES加密，密钥需要16位，如果需要改变密钥长度，请配合使用PBE将密钥转换成16位的长度

    public static final String DEFAULT_AES_INITIALIZATION_VECTOR = "MOBILE_DEVELOPER";//初始化向量，必须16位长度

    /**
     * AES CBC 加密 随机向量
     *
     * @param secretKey 密钥
     * @param plaintext 待加密数据
     * @param charset   加密编码格式 默认UTF_8
     * @return 加密完成数据
     * <p>
     * @since 1.0.20
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String encryptAESRandomIVO(String secretKey, String plaintext, Charset charset) {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION_AES_CBC_PKCS5PADDING);
            SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(charset == null ? Charset.defaultCharset() : charset), ALGORITHM_AES);
            // CBC模式需要生成一个16 bytes的initialization vector:
            SecureRandom secureRandom = SecureRandom.getInstanceStrong();
            byte[] iv = secureRandom.generateSeed(16);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
            byte[] data = cipher.doFinal(plaintext.getBytes(charset == null ? Charset.defaultCharset() : charset));
            // IV不需要保密，把IV和密文一起返回:
            byte[] result = joinByte(iv, data);
            return Base64.getEncoder().encodeToString(result);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES CBC 加密 随机向量
     *
     * @param secretKey 密钥
     * @param plaintext 待加密数据
     * @param ivText    指定向量
     * @param charset   加密编码格式 默认UTF_8
     * @return 加密完成数据
     * <p>
     * @since 1.0.20
     */
    public static String encryptAESWithIV(String secretKey, String plaintext, String ivText, Charset charset) {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION_AES_CBC_PKCS5PADDING);
            SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(charset == null ? Charset.defaultCharset() : charset), ALGORITHM_AES);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(ivText.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
            byte[] data = cipher.doFinal(plaintext.getBytes(charset == null ? Charset.defaultCharset() : charset));
            return Base64.getEncoder().encodeToString(data);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES CBC 解密 向量是分割出来的
     *
     * @param secretKey  密钥
     * @param ciphertext 待解密数据
     * @param charset    解密编码格式 默认UTF_8
     * @return 解密完成数据
     * <p>
     * @since 1.0.20
     */
    public static String decryptAESRandomIV(String secretKey, String ciphertext, Charset charset) {
        try {
            byte[] ciphertextbyte = Base64.getDecoder().decode(ciphertext);
            // 把ciphertextbyte分割成IV和密文:
            byte[] iv = new byte[16];
            byte[] data = new byte[ciphertextbyte.length - 16];
            System.arraycopy(ciphertextbyte, 0, iv, 0, 16);
            System.arraycopy(ciphertextbyte, 16, data, 0, data.length);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION_AES_CBC_PKCS5PADDING);
            SecretKey key = new SecretKeySpec(secretKey.getBytes(charset == null ? Charset.defaultCharset() : charset), ALGORITHM_AES);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
            byte[] result = cipher.doFinal(data);
            return new String(result, charset == null ? Charset.defaultCharset() : charset);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES CBC 解密 随机向量
     *
     * @param secretKey  密钥
     * @param ciphertext 待解密数据
     * @param ivText     指定向量
     * @param charset    解密编码格式 默认UTF_8
     * @return 解密完成数据
     * <p>
     * @since 1.0.20
     */
    public static String decryptAESWithIV(String secretKey, String ciphertext, String ivText, Charset charset) {
        try {
            byte[] data = Base64.getDecoder().decode(ciphertext);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION_AES_CBC_PKCS5PADDING);
            SecretKey key = new SecretKeySpec(secretKey.getBytes(charset == null ? Charset.defaultCharset() : charset), ALGORITHM_AES);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(ivText.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
            byte[] result = cipher.doFinal(data);
            return new String(result, charset == null ? Charset.defaultCharset() : charset);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] joinByte(byte[] byte1, byte[] byte2) {
        byte[] result = new byte[byte1.length + byte2.length];
        System.arraycopy(byte1, 0, result, 0, byte1.length);
        System.arraycopy(byte2, 0, result, byte1.length, byte2.length);
        return result;
    }

    /**
     * AES ECB 加密
     *
     * @param secretKey 密钥
     * @param plaintext 待加密数据
     * @param charset   加密编码格式 默认UTF_8
     * @return 加密完成数据
     * <p>
     * @since 1.0.20
     */
    public static String encryptAES(String secretKey, String plaintext, Charset charset) {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION_AES_ECB_PKCS5PADDING);
            SecretKey key = new SecretKeySpec(secretKey.getBytes(charset == null ? Charset.defaultCharset() : charset), ALGORITHM_AES);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] result = cipher.doFinal(plaintext.getBytes(charset == null ? Charset.defaultCharset() : charset));
            return Base64.getEncoder().encodeToString(result);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES ECB 解密
     *
     * @param secretKey  密钥
     * @param ciphertext 待解密数据
     * @param charset    解密编码格式 默认UTF_8
     * @return 解密完成数据
     * <p>
     * @since 1.0.20
     */
    public static String decryptAES(String secretKey, String ciphertext, Charset charset) {
        try {
            byte[] ciphertextbyte = Base64.getDecoder().decode(ciphertext);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION_AES_ECB_PKCS5PADDING);
            SecretKey key = new SecretKeySpec(secretKey.getBytes(charset == null ? Charset.defaultCharset() : charset), ALGORITHM_AES);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] result = cipher.doFinal(ciphertextbyte);
            return new String(result, charset == null ? Charset.defaultCharset() : charset);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
