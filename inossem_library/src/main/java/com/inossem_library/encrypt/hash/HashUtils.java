package com.inossem_library.encrypt.hash;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Hash和hmac加密工具类
 *
 * @author LeoDys E-mail:changwen.sun@inossem.com 2020/7/10 11:44
 * @version 1.0.8
 * @since 1.0.20
 */
public class HashUtils {

//    public static final String ALGORITHM_MD2 = "MD2";

    public static final String ALGORITHM_MD5 = "MD5";

    public static final String ALGORITHM_SHA1 = "SHA-1";

    public static final String ALGORITHM_SHA224 = "SHA-224";

    public static final String ALGORITHM_SHA256 = "SHA-256";

    public static final String ALGORITHM_SHA384 = "SHA-384";

    public static final String ALGORITHM_SHA512 = "SHA-512";

//    public static final String ALGORITHM_SHA512_224 = "SHA-512/224";

//    public static final String ALGORITHM_SHA512_256 = "SHA-512/256";

    public static final String HMAC_ALGORITHM_MD5 = "HmacMD5";

    public static final String HMAC_ALGORITHM_SHA1 = "HmacSHA1";

    public static final String HMAC_ALGORITHM_SHA224 = "HmacSHA224";

    public static final String HMAC_ALGORITHM_SHA256 = "HmacSHA256";

    public static final String HMAC_ALGORITHM_SHA384 = "HmacSHA384";

    public static final String HMAC_ALGORITHM_SHA512 = "HmacSHA512";

    /**
     * 密钥生成器
     *
     * @param algorithm 要生成密码的加密算法
     * @return 密钥
     * <p>
     * @since 1.0.20
     */
    public static String createHmacSecretKey(String algorithm) {
        try {
            // KeyGenerator此类提供(对称加密算法:AES,DES 等等)密钥生成器的功能
            // 通过指定算法,可指定提供者来构造KeyGenerator对象,有多个重载方法
            KeyGenerator keyGen = KeyGenerator.getInstance(algorithm);
            // 生成秘钥 // 返回SecertKey对象
            SecretKey secretKey = keyGen.generateKey();
            return new BigInteger(1, secretKey.getEncoded()).toString(16);//转成16进制字符串
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取生成的密钥（将密钥转为SecretKey格式）
     *
     * @param algorithm 要获取密码的加密算法 必须是createHmacSecretKey的算法
     * @return 密钥（SecretKey类型）
     * <p>
     * @since 1.0.20
     */
    public static SecretKey getHmacSecretKey(String algorithm, String secretKey) {
        return new SecretKeySpec(new BigInteger(secretKey, 16).toByteArray(), algorithm);//将16进制字符串转成byte[]
//        return new SecretKeySpec(secretKey.getBytes(),algorithm);
    }

    /**
     * hmacMD5加密
     *
     * @param secretKey 密钥
     * @param message   待加密数据
     * @param charset   加密编码格式
     * @return 加密完成数据
     * <p>
     * @since 1.0.20
     */
    public static String hmacHashMD5(SecretKey secretKey, String message, Charset charset) {
        return hmacHashWithAlgorithm(HMAC_ALGORITHM_MD5, secretKey, message, charset);
    }

    /**
     * hmacMD5加密
     *
     * @param secretKey 密钥
     * @param message   待加密数据
     * @param charset   加密编码格式
     * @return 加密完成数据
     * <p>
     * @since 1.0.20
     */
    public static String hmacHashSHA1(SecretKey secretKey, String message, Charset charset) {
        return hmacHashWithAlgorithm(HMAC_ALGORITHM_SHA1, secretKey, message, charset);
    }

    /**
     * SHA256加密
     *
     * @param secretKey 密钥
     * @param message   待加密数据
     * @param charset   加密编码格式
     * @return 加密完成数据
     * <p>
     * @since 1.0.20
     */
    public static String hmacHashSHA256(SecretKey secretKey, String message, Charset charset) {
        return hmacHashWithAlgorithm(HMAC_ALGORITHM_SHA256, secretKey, message, charset);
    }

    /**
     * SHA512加密
     *
     * @param secretKey 密钥
     * @param message   待加密数据
     * @param charset   加密编码格式
     * @return 加密完成数据
     * <p>
     * @since 1.0.20
     */
    public static String hmacHashSHA512(SecretKey secretKey, String message, Charset charset) {
        return hmacHashWithAlgorithm(HMAC_ALGORITHM_SHA512, secretKey, message, charset);
    }

    /**
     * 通用hmac算法工具类
     *
     * @param algorithm 消息摘要算法 如HmacSHA1或HmacSHA256
     * @param message   待加密数据
     * @param charset   加密编码格式 默认UTF_8
     * @return 加密完成数据
     * <p>
     * @since 1.0.20
     */
    public static String hmacHashWithAlgorithm(String algorithm, SecretKey secretKey, String message, Charset charset) {
        try {
            Mac mac = Mac.getInstance(algorithm);
            mac.init(secretKey);
            mac.update(message.getBytes(charset == null ? Charset.defaultCharset() : charset));
            byte[] result = mac.doFinal();
            return new BigInteger(1, result).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * md5加密
     *
     * @param message 待加密数据
     * @param charset 加密编码格式
     * @return 加密完成数据
     * <p>
     * @since 1.0.20
     */
    public static String hashMD5(String message, Charset charset) {
        return hashWithAlgorithm(ALGORITHM_MD5, message, charset);
    }

    /**
     * SHA1加密
     *
     * @param message 待加密数据
     * @param charset 加密编码格式 默认UTF_8
     * @return 加密完成数据
     * <p>
     * @since 1.0.20
     */
    public static String hashSHA1(String message, Charset charset) {
        return hashWithAlgorithm(ALGORITHM_SHA1, message, charset);
    }

    /**
     * SHA256加密
     *
     * @param message 待加密数据
     * @param charset 加密编码格式 默认UTF_8
     * @return 加密完成数据
     * <p>
     * @since 1.0.20
     */
    public static String hashSHA256(String message, Charset charset) {
        return hashWithAlgorithm(ALGORITHM_SHA256, message, charset);
    }

    /**
     * SHA512加密
     *
     * @param message 待加密数据
     * @param charset 加密编码格式 默认UTF_8
     * @return 加密完成数据
     * <p>
     * @since 1.0.20
     */
    public static String hashSHA512(String message, Charset charset) {
        return hashWithAlgorithm(ALGORITHM_SHA512, message, charset);
    }

    /**
     * 通用Hash算法工具类
     *
     * @param algorithm 消息摘要算法 如SHA-1或SHA-256
     * @param message   待加密数据
     * @param charset   加密编码格式 默认UTF_8
     * @return 加密完成数据
     * <p>
     * @since 1.0.20
     */
    public static String hashWithAlgorithm(String algorithm, String message, Charset charset) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(message.getBytes(charset == null ? Charset.defaultCharset() : charset));
            byte[] bytes = md.digest(); // hash函数转换
            //1代表正数，0代表0，-1代表负数。 TODO 为什么传的是1
            BigInteger bigInteger = new BigInteger(1, bytes);
            // 转16进制
            return bigInteger.toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}
