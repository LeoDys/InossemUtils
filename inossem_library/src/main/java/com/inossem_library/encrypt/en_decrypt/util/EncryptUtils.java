package com.inossem_library.encrypt.en_decrypt.util;

import android.security.keystore.KeyProperties;
import android.text.TextUtils;
import android.util.Base64;

import com.inossem_library.encrypt.en_decrypt.constant.EncryptConstant;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 加密解密相关
 *
 * @author Lin
 */
public final class EncryptUtils {
    // TODO: 哈希加密
    // TODO: MD5加密

    /**
     * MD5加密
     *
     * @param data 数据
     * @return MD5加密的十六进制字符串
     */
    public static String encryptMD5ToString(final String data) throws Throwable {
        if (TextUtils.isEmpty(data)) {
            return "";
        }
        return encryptMD5ToString(data.getBytes());
    }

    /**
     * MD5加盐加密
     *
     * @param data 数据
     * @param salt 盐值 防止系统底层权限反查
     *             <a>https://www.cnblogs.com/yangyi9343/p/5775743.html</a>
     * @return MD5加密的十六进制字符串
     */
    public static String encryptMD5ToString(final String data, final String salt) throws Throwable {
        if (data == null && salt == null) {
            return "";
        }
        if (salt == null) {
            return bytes2HexString(encryptMD5(data.getBytes()));
        }
        if (data == null) {
            return bytes2HexString(encryptMD5(salt.getBytes()));
        }
        return bytes2HexString(encryptMD5((data + salt).getBytes()));
    }

    /**
     * MD5加密
     *
     * @param data 数据
     * @return MD5加密的十六进制字符串
     */
    public static String encryptMD5ToString(final byte[] data) throws Throwable {
        return bytes2HexString(encryptMD5(data));
    }

    /**
     * MD5加盐加密
     *
     * @param data 数据
     * @param salt 盐值 防止系统底层权限反查
     * @return MD5加密的十六进制字符串
     */
    public static String encryptMD5ToString(final byte[] data, final byte[] salt) throws Throwable {
        if (data == null && salt == null) {
            return "";
        }
        if (salt == null) {
            return bytes2HexString(encryptMD5(data));
        }
        if (data == null) {
            return bytes2HexString(encryptMD5(salt));
        }
        byte[] dataSalt = new byte[data.length + salt.length];
        System.arraycopy(data, 0, dataSalt, 0, data.length);
        System.arraycopy(salt, 0, dataSalt, data.length, salt.length);
        return bytes2HexString(encryptMD5(dataSalt));
    }

    /**
     * MD5加密
     *
     * @param data 数据
     * @return MD5加密的十六进制字符串
     */
    public static byte[] encryptMD5(final byte[] data) throws Throwable {
        return hashTemplate(data, "MD5");
    }

    /**
     * 返回文件MD5加密的十六进制字符串
     *
     * @param filePath 文件的路径
     * @return 文件MD5加密的十六进制字符串
     */
    public static String encryptMD5File2String(final String filePath) {
        File file = isSpace(filePath) ? null : new File(filePath);
        return encryptMD5File2String(file);
    }

    /**
     * MD5加密
     *
     * @param filePath 文件的路径
     * @return 文件MD5加密的byte[]
     */
    public static byte[] encryptMD5File(final String filePath) {
        File file = isSpace(filePath) ? null : new File(filePath);
        return encryptMD5File(file);
    }

    /**
     * MD5加密
     *
     * @param file 文件
     * @return 文件MD5加密的十六进制字符串
     */
    public static String encryptMD5File2String(final File file) {
        return bytes2HexString(encryptMD5File(file));
    }

    /**
     * MD5加密
     *
     * @param file 文件
     * @return 文件MD5加密的byte[]
     */
    public static byte[] encryptMD5File(final File file) {
        if (file == null) {
            return null;
        }
        FileInputStream fis = null;
        DigestInputStream digestInputStream;
        try {
            fis = new FileInputStream(file);
            MessageDigest md = MessageDigest.getInstance("MD5");
            digestInputStream = new DigestInputStream(fis, md);
            byte[] buffer = new byte[256 * 1024];
            while (true) {
                if (digestInputStream.read(buffer) <= 0) {
                    break;
                }
            }
            md = digestInputStream.getMessageDigest();
            return md.digest();
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    // TODO: SHA1加密

    /**
     * SHA1加密
     *
     * @param data 数据
     * @return SHA1加密的十六进制字符串
     */
    public static String encryptSHA1ToString(final String data) throws Throwable {
        if (TextUtils.isEmpty(data)) {
            return "";
        }
        return encryptSHA1ToString(data.getBytes());
    }

    /**
     * SHA1加密
     *
     * @param data 数据
     * @return SHA1加密的十六进制字符串
     */
    public static String encryptSHA1ToString(final byte[] data) throws Throwable {
        return bytes2HexString(encryptSHA1(data));
    }

    /**
     * SHA1加密
     *
     * @param data 数据
     * @return SHA1加密的byte[]
     */
    public static byte[] encryptSHA1(final byte[] data) throws Throwable {
        return hashTemplate(data, "SHA-1");
    }
    // TODO: SHA224加密

    /**
     * SHA224加密
     *
     * @param data 数据
     * @return 十六进制串的SHA224加密
     */
    public static String encryptSHA224ToString(final String data) throws Throwable {
        if (TextUtils.isEmpty(data)) {
            return "";
        }
        return encryptSHA224ToString(data.getBytes());
    }

    /**
     * SHA224加密
     *
     * @param data 数据
     * @return 十六进制串的SHA224加密
     */
    public static String encryptSHA224ToString(final byte[] data) throws Throwable {
        return bytes2HexString(encryptSHA224(data));
    }

    /**
     * SHA224加密
     *
     * @param data 数据
     * @return SHA224加密的byte[]
     */
    public static byte[] encryptSHA224(final byte[] data) throws Throwable {
        return hashTemplate(data, "SHA224");
    }
    // TODO: SHA256加密

    /**
     * SHA256加密
     *
     * @param data 数据
     * @return SHA256加密的十六进制字符串
     */
    public static String encryptSHA256ToString(final String data) throws Throwable {
        if (TextUtils.isEmpty(data)) {
            return "";
        }
        return encryptSHA256ToString(data.getBytes());
    }

    /**
     * SHA256加密
     *
     * @param data 数据
     * @return SHA256加密的十六进制字符串
     */
    public static String encryptSHA256ToString(final byte[] data) throws Throwable {
        return bytes2HexString(encryptSHA256(data));
    }

    /**
     * SHA256加密
     *
     * @param data 数据
     * @return SHA256加密的byte[]
     */
    public static byte[] encryptSHA256(final byte[] data) throws Throwable {
        return hashTemplate(data, "SHA-256");
    }
    // TODO: SHA384加密

    /**
     * SHA384加密
     *
     * @param data 数据
     * @return SHA384加密的十六进制字符串
     */
    public static String encryptSHA384ToString(final String data) throws Throwable {
        if (TextUtils.isEmpty(data)) {
            return "";
        }
        return encryptSHA384ToString(data.getBytes());
    }

    /**
     * SHA384加密
     *
     * @param data 数据
     * @return SHA384加密的十六进制字符串
     */
    public static String encryptSHA384ToString(final byte[] data) throws Throwable {
        return bytes2HexString(encryptSHA384(data));
    }

    /**
     * SHA384加密
     *
     * @param data 数据
     * @return SHA384加密的byte[]
     */
    public static byte[] encryptSHA384(final byte[] data) throws Throwable {
        return hashTemplate(data, "SHA-384");
    }
    // TODO: SHA512加密

    /**
     * SHA512加密
     *
     * @param data 数据
     * @return 十六进制字符串SHA512加密
     */
    public static String encryptSHA512ToString(final String data) throws Throwable {
        if (TextUtils.isEmpty(data)) {
            return "";
        }
        return encryptSHA512ToString(data.getBytes());
    }

    /**
     * SHA512加密
     *
     * @param data 数据
     * @return 十六进制字符串SHA512加密
     */
    public static String encryptSHA512ToString(final byte[] data) throws Throwable {
        return bytes2HexString(encryptSHA512(data));
    }

    /**
     * SHA512加密
     *
     * @param data 数据
     * @return SHA512加密的byte[]
     */
    public static byte[] encryptSHA512(final byte[] data) throws Throwable {
        return hashTemplate(data, "SHA-512");
    }

    /**
     * 哈希加密
     *
     * @param data      数据
     * @param algorithm 哈希加密的名称
     * @return 哈希加密的byte[]
     */
    private static byte[] hashTemplate(final byte[] data, final String algorithm) throws Throwable {
        if (data == null || data.length <= 0) {
            return null;
        }
        MessageDigest md = MessageDigest.getInstance(algorithm);
        md.update(data);
        return md.digest();
    }
    // TODO: hmac加密

    /**
     * HmacMD5加密
     *
     * @param data 数据
     * @param key  密钥
     * @return HmacMD5加密的十六进制字符串
     */
    public static String encryptHmacMD5ToString(final String data, final String key) throws Throwable {
        if (data == null || data.length() == 0 || key == null || key.length() == 0) {
            return "";
        }
        return encryptHmacMD5ToString(data.getBytes(), key.getBytes());
    }

    /**
     * HmacMD5加密
     *
     * @param data 数据
     * @param key  密钥
     * @return HmacMD5加密的十六进制字符串
     */
    public static String encryptHmacMD5ToString(final byte[] data, final byte[] key) throws Throwable {
        return bytes2HexString(encryptHmacMD5(data, key));
    }

    /**
     * HmacMD5加密
     *
     * @param data 数据
     * @param key  密钥
     * @return HmacMD5加密的byte[]
     */
    public static byte[] encryptHmacMD5(final byte[] data, final byte[] key) throws Throwable {
        return hmacTemplate(data, key, "HmacMD5");
    }
    // TODO: HmacSHA1加密

    /**
     * HmacSHA1加密
     *
     * @param data 数据
     * @param key  密钥
     * @return HmacSHA1加密的十六进制字符串
     */
    public static String encryptHmacSHA1ToString(final String data, final String key) throws Throwable {
        if (data == null || data.length() == 0 || key == null || key.length() == 0) {
            return "";
        }
        return encryptHmacSHA1ToString(data.getBytes(), key.getBytes());
    }

    /**
     * HmacSHA1加密
     *
     * @param data 数据
     * @param key  密钥
     * @return HmacSHA1加密的十六进制字符串
     */
    public static String encryptHmacSHA1ToString(final byte[] data, final byte[] key) throws Throwable {
        return bytes2HexString(encryptHmacSHA1(data, key));
    }

    /**
     * HmacSHA1加密
     *
     * @param data 数据
     * @param key  密钥
     * @return HmacSHA1加密的byte[]
     */
    public static byte[] encryptHmacSHA1(final byte[] data, final byte[] key) throws Throwable {
        return hmacTemplate(data, key, "HmacSHA1");
    }
    // TODO: HmacSHA224加密

    /**
     * HmacSHA224加密
     *
     * @param data 数据
     * @param key  密钥
     * @return HmacSHA224加密的十六进制字符串
     */
    public static String encryptHmacSHA224ToString(final String data, final String key) throws Throwable {
        if (data == null || data.length() == 0 || key == null || key.length() == 0) {
            return "";
        }
        return encryptHmacSHA224ToString(data.getBytes(), key.getBytes());
    }

    /**
     * HmacSHA224加密
     *
     * @param data 数据
     * @param key  密钥
     * @return HmacSHA224加密的十六进制字符串
     */
    public static String encryptHmacSHA224ToString(final byte[] data, final byte[] key) throws Throwable {
        return bytes2HexString(encryptHmacSHA224(data, key));
    }

    /**
     * HmacSHA224加密
     *
     * @param data 数据
     * @param key  密钥
     * @return HmacSHA224加密的byte[]
     */
    public static byte[] encryptHmacSHA224(final byte[] data, final byte[] key) throws Throwable {
        return hmacTemplate(data, key, "HmacSHA224");
    }
    // TODO: HmacSHA256加密

    /**
     * HmacSHA256加密
     *
     * @param data 数据
     * @param key  密钥
     * @return the hex string of HmacSHA256 encryption
     */
    public static String encryptHmacSHA256ToString(final String data, final String key) throws Throwable {
        if (data == null || data.length() == 0 || key == null || key.length() == 0) {
            return "";
        }
        return encryptHmacSHA256ToString(data.getBytes(), key.getBytes());
    }

    /**
     * HmacSHA256加密
     *
     * @param data 数据
     * @param key  密钥
     * @return the hex string of HmacSHA256 encryption
     */
    public static String encryptHmacSHA256ToString(final byte[] data, final byte[] key) throws Throwable {
        return bytes2HexString(encryptHmacSHA256(data, key));
    }

    /**
     * HmacSHA256加密
     *
     * @param data 数据
     * @param key  密钥
     * @return the bytes of HmacSHA256 encryption
     */
    public static byte[] encryptHmacSHA256(final byte[] data, final byte[] key) throws Throwable {
        return hmacTemplate(data, key, "HmacSHA256");
    }
    // TODO: HmacSHA384加密

    /**
     * HmacSHA384加密
     *
     * @param data 数据
     * @param key  密钥
     * @return HmacSHA384加密的十六进制字符串
     */
    public static String encryptHmacSHA384ToString(final String data, final String key) throws Throwable {
        if (data == null || data.length() == 0 || key == null || key.length() == 0) {
            return "";
        }
        return encryptHmacSHA384ToString(data.getBytes(), key.getBytes());
    }

    /**
     * HmacSHA384加密
     *
     * @param data 数据
     * @param key  密钥
     * @return HmacSHA384加密的十六进制字符串
     */
    public static String encryptHmacSHA384ToString(final byte[] data, final byte[] key) throws Throwable {
        return bytes2HexString(encryptHmacSHA384(data, key));
    }

    /**
     * HmacSHA384加密
     *
     * @param data 数据
     * @param key  密钥
     * @return HmacSHA384加密的byte[]
     */
    public static byte[] encryptHmacSHA384(final byte[] data, final byte[] key) throws Throwable {
        return hmacTemplate(data, key, "HmacSHA384");
    }
    // TODO: HmacSHA512加密

    /**
     * HmacSHA512加密
     *
     * @param data 数据
     * @param key  密钥
     * @return 十六进制字符串的HmacSHA512加密
     */
    public static String encryptHmacSHA512ToString(final String data, final String key) throws Throwable {
        if (data == null || data.length() == 0 || key == null || key.length() == 0) {
            return "";
        }
        return encryptHmacSHA512ToString(data.getBytes(), key.getBytes());
    }

    /**
     * HmacSHA512加密
     *
     * @param data 数据
     * @param key  密钥
     * @return 十六进制字符串的HmacSHA512加密
     */
    public static String encryptHmacSHA512ToString(final byte[] data, final byte[] key) throws Throwable {
        return bytes2HexString(encryptHmacSHA512(data, key));
    }

    /**
     * HmacSHA512加密
     *
     * @param data 数据
     * @param key  密钥
     * @return HmacSHA512加密的byte[]
     */
    public static byte[] encryptHmacSHA512(final byte[] data, final byte[] key) throws Throwable {
        return hmacTemplate(data, key, "HmacSHA512");
    }

    /**
     * hmac加密
     *
     * @param data      数据
     * @param key       密钥
     * @param algorithm hmac加密的名称
     * @return hmac加密的byte[]
     */
    private static byte[] hmacTemplate(final byte[] data, final byte[] key, final String algorithm) throws Throwable {
        if (data == null || data.length == 0 || key == null || key.length == 0) {
            return null;
        }
        SecretKeySpec secretKey = new SecretKeySpec(key, algorithm);
        Mac mac = Mac.getInstance(algorithm);
        mac.init(secretKey);
        return mac.doFinal(data);
    }
    // TODO: DES加密

    /**
     * DES加密
     *
     * @param data           数据 需要为8位
     * @param key            密钥 需要为8位
     * @param transformation 块加密的模式以及数据填充
     *                       <p>样例"DES/CBC/PKCS5Padding"
     *                       Cipher加密器初始化需要一个字符串,字符串里提供了三种设置.
     *                       <p>一是,加解密算法;二是,加解密模式;三是,是否需要填充.
     *                       <p>
     *                       <p>第二个参数是:
     *                       ECB(电码本模式),CBC(加密块链模式),OFB(输出反馈模式),CFB(加密反馈模式){@link KeyProperties}
     *                       {@link KeyGenerator} {@link KeyPairGenerator}都是javax.crypto包的，生成的key主要是提供给AES，DES，3DES，MD5，SHA1等 对称 和 单向 加密算法
     *                       {@link KeyFactory}{@link SecretKeyFactory}都是java.security包的，生成的key主要是提供给DSA，RSA， EC等 非对称加密算法
     *                       ECB模式简单,缺点是块加密的内容容易重复,会被统计分析攻击;
     *                       CBC,OFB,CFB三个模式,都是根据前面加密块的内容,对key进行新一轮处理后再,再对下一数据块进行处理,如此类推下去,这样一来,加密的强度也有所增强.
     *                       他们都需要用到初始化向量IV,英文是Initialization Vector的缩写.
     *                       <p>
     *                       <p>第三个参数是:
     *                       ZeroPadding,数据长度不对齐时使用0填充,否则不填充.
     *                       PKCS7Padding,假设数据长度需要填充n(n>0)个字节才对齐,那么填充n个字节,每个字节都是n;如果数据本身就已经对齐了,则填充一块长度为块大小的数据,每个字节都是块大小.
     *                       PKCS5Padding,PKCS7Padding的子集,块大小固定为8字节.
     *                       DES为快加密,只能使用PKCS5Padding.
     *                       <a>https://blog.csdn.net/qq_18870023/article/details/52180768</a>
     * @param iv             使用CBC,OFB,CFB三个模式,需要一个向量iv,可增加加密算法的强度
     *                       <p>
     *                       <p>样例
     *                       byte[] iv = new byte[16];
     *                       SecureRandom r = new SecureRandom();{@link SecureRandom 随机数类}
     *                       r.nextBytes(iv);{@link SecureRandom#nextBytes(byte[])}生成用户指定的随机byte}
     *                       IvParameterSpec iv = new IvParameterSpec(iv);
     *                       System.out.println(iv.getIV());
     *                       <p>
     *                       <p>错误样例
     *                       IvParameterSpec iv = new IvParameterSpec("1234567890123456".getBytes());不要写""固定值
     *                       <p>
     *                       <p>修复建议:
     *                       禁止使用常量初始化矢量参数构建IvParameterSpec
     * @return DES加密的base64编码byte[]
     * @see SecretKeyFactory#getInstance(String algorithm) 加密方式
     * @see Cipher#getInstance(String transformation) 块加密的模式以及数据填充
     * @see IvParameterSpec (byte[]) 向量iv
     */
    public static byte[] encryptDES2Base64(final byte[] data, final byte[] key, final String transformation, final byte[] iv) throws Throwable {
        return base64Encode(encryptDES(data, key, transformation, iv));
    }

    /**
     * DES加密
     *
     * @param key 需要为8位
     * @param iv  需要为8位
     * @return DES加密的十六进制字符串
     * @see EncryptUtils#encryptDES2Base64(byte[], byte[], String, byte[]) 参数详情
     */
    public static String encryptDES2HexString(final byte[] data, final byte[] key, final String transformation, final byte[] iv) throws Throwable {
        return bytes2HexString(encryptDES(data, key, transformation, iv));
    }

    /**
     * DES加密
     *
     * @param key 需要为8位
     * @param iv  需要为8位
     * @return DES加密的byte[]
     * @see EncryptUtils#encryptDES2Base64(byte[], byte[], String, byte[]) 参数详情
     */
    public static byte[] encryptDES(final byte[] data, final byte[] key, final String transformation, final byte[] iv) throws Throwable {
        return symmetricTemplate(data, key, "DES", transformation, iv, true);
    }
    // TODO: DES解密

    /**
     * DES解密
     *
     * @param key 需要为8位
     * @param iv  需要为8位
     * @return 使用了base64编码的DES解密byte[]
     * @see EncryptUtils#encryptDES2Base64(byte[], byte[], String, byte[]) 参数详情
     */
    public static byte[] decryptBase64DES(final byte[] data, final byte[] key, final String transformation, final byte[] iv) throws Throwable {
        return decryptDES(base64Decode(data), key, transformation, iv);
    }

    /**
     * DES解密
     *
     * @param key 需要为8位
     * @param iv  需要为8位
     * @return 十六进制字符串的DES解密byte[]
     * @see EncryptUtils#encryptDES2Base64(byte[], byte[], String, byte[]) 参数详情
     */
    public static byte[] decryptHexStringDES(final String data, final byte[] key, final String transformation, final byte[] iv) throws Throwable {
        return decryptDES(hexString2Bytes(data), key, transformation, iv);
    }

    /**
     * DES解密
     *
     * @param key 需要为8位
     * @param iv  需要为8位
     * @return DES解密的byte[]
     * @see EncryptUtils#encryptDES2Base64(byte[], byte[], String, byte[]) 参数详情
     */
    public static byte[] decryptDES(final byte[] data, final byte[] key, final String transformation, final byte[] iv) throws Throwable {
        return symmetricTemplate(data, key, "DES", transformation, iv, false);
    }

    // TODO: AES加密

    /**
     * AES加密
     *
     * @param key            需要为16位
     * @param iv             需要为16位
     * @param transformation AES/CBC/PKCS5Padding
     * @return AES加密的base64编码byte[]
     * @see EncryptUtils#encryptDES2Base64(byte[], byte[], String, byte[]) 参数详情
     */
    public static byte[] encryptAES2Base64(final byte[] data, final byte[] key, final String transformation, final byte[] iv) throws Throwable {
        return base64Encode(encryptAES(data, key, transformation, iv));
    }

    /**
     * AES加密
     *
     * @param key            需要为16位
     * @param iv             需要为16位
     * @param transformation AES/CBC/PKCS5Padding
     * @return AES加密的十六进制字符串
     * @see EncryptUtils#encryptDES2Base64(byte[], byte[], String, byte[]) 参数详情
     */
    public static String encryptAES2HexString(final byte[] data, final byte[] key, final String transformation, final byte[] iv) throws Throwable {
        return bytes2HexString(encryptAES(data, key, transformation, iv));
    }

    /**
     * AES加密
     *
     * @param key            需要为16位
     * @param iv             需要为16位
     * @param transformation AES/CBC/PKCS5Padding
     * @return AES加密的byte[]
     * @see EncryptUtils#encryptDES2Base64(byte[], byte[], String, byte[]) 参数详情
     */
    public static byte[] encryptAES(final byte[] data, final byte[] key, final String transformation, final byte[] iv) throws Throwable {
        return symmetricTemplate(data, key, "AES", transformation, iv, true);
    }
    // TODO: AES解密

    /**
     * AES解密
     *
     * @param key            需要为16位
     * @param iv             需要为16位
     * @param transformation AES/CBC/PKCS5Padding
     * @return 用于base64编码byte[]的AES解密byte[]
     * @see EncryptUtils#encryptDES2Base64(byte[], byte[], String, byte[]) 参数详情
     */
    public static byte[] decryptBase64AES(final byte[] data, final byte[] key, final String transformation, final byte[] iv) throws Throwable {
        return decryptAES(base64Decode(data), key, transformation, iv);
    }

    /**
     * AES解密
     *
     * @param key            需要为16位
     * @param iv             需要为16位
     * @param transformation AES/CBC/PKCS5Padding
     * @return 十六进制字符串的AES解密byte[]
     * @see EncryptUtils#encryptDES2Base64(byte[], byte[], String, byte[]) 参数详情
     */
    public static byte[] decryptHexStringAES(final String data, final byte[] key, final String transformation, final byte[] iv) throws Throwable {
        return decryptAES(hexString2Bytes(data), key, transformation, iv);
    }

    /**
     * AES解密
     *
     * @param key            需要为16位
     * @param iv             需要为16位
     * @param transformation AES/CBC/PKCS5Padding
     * @return AES解密的byte[]
     * @see EncryptUtils#encryptDES2Base64(byte[], byte[], String, byte[]) 参数详情
     */
    public static byte[] decryptAES(final byte[] data, final byte[] key, final String transformation, final byte[] iv) throws Throwable {
        return symmetricTemplate(data, key, "AES", transformation, iv, false);
    }

    /**
     * 对称加密或解密的byte[]
     *
     * @param isEncrypt True是加密,False是解密.
     * @return 对称加密或解密的byte[]
     * @see EncryptUtils#encryptDES2Base64(byte[], byte[], String, byte[]) 参数详情
     */
    private static byte[] symmetricTemplate(final byte[] data, final byte[] key, final String algorithm, final String transformation, final byte[] iv, final boolean isEncrypt) throws Throwable {
        if (data == null || data.length == 0 || key == null || key.length == 0) {
            return null;
        }
        SecretKey secretKey;
        if ("DES".equals(algorithm)) {
            DESKeySpec desKey = new DESKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm);
            secretKey = keyFactory.generateSecret(desKey);
        } else {
            secretKey = new SecretKeySpec(key, algorithm);
        }
        Cipher cipher = Cipher.getInstance(transformation);
        if (iv == null || iv.length == 0) {
            cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, secretKey);
        } else {
            AlgorithmParameterSpec params = new IvParameterSpec(iv);
            cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, secretKey, params);
        }
        return cipher.doFinal(data);
    }
    // TODO: RSA加密

    /**
     * RSA加密
     *
     * @param isPublicKey    使用公钥为True，使用私钥为FALSE
     * @param transformation RSA/CBC/PKCS1Padding
     * @return RSA加密的base64编码byte[]
     * @see EncryptUtils#encryptDES2Base64(byte[], byte[], String, byte[]) 参数详情
     */
    public static byte[] encryptRSA2Base64(final byte[] data, final byte[] key, final boolean isPublicKey, final String transformation) throws Throwable {
        return base64Encode(encryptRSA(data, key, isPublicKey, transformation));
    }

    /**
     * RSA加密
     *
     * @param isPublicKey    使用公钥为True，使用私钥为FALSE
     * @param transformation RSA/CBC/PKCS1Padding
     * @return RSA加密的十六进制字符串
     * @see EncryptUtils#encryptDES2Base64(byte[], byte[], String, byte[]) 参数详情
     */
    public static String encryptRSA2HexString(final byte[] data, final byte[] key, final boolean isPublicKey, final String transformation) throws Throwable {
        return bytes2HexString(encryptRSA(data, key, isPublicKey, transformation));
    }

    /**
     * RSA加密
     *
     * @param isPublicKey    使用公钥为True，使用私钥为FALSE
     * @param transformation RSA/CBC/PKCS1Padding
     * @return RSA加密的byte[]
     * @see EncryptUtils#encryptDES2Base64(byte[], byte[], String, byte[]) 参数详情
     */
    public static byte[] encryptRSA(final byte[] data, final byte[] key, final boolean isPublicKey, final String transformation) throws Throwable {
        return rsaTemplate(data, key, isPublicKey, transformation, true);
    }
    // TODO: RSA解密

    /**
     * RSA解密
     *
     * @param isPublicKey    使用公钥为True，使用私钥为FALSE
     * @param transformation RSA/CBC/PKCS1Padding
     * @return 用于base64编码byte[]的RSA解密的byte[]
     * @see EncryptUtils#encryptDES2Base64(byte[], byte[], String, byte[]) 参数详情
     */
    public static byte[] decryptBase64RSA(final byte[] data, final byte[] key, final boolean isPublicKey, final String transformation) throws Throwable {
        return decryptRSA(base64Decode(data), key, isPublicKey, transformation);
    }

    /**
     * RSA解密
     *
     * @param isPublicKey    使用公钥为True，使用私钥为FALSE
     * @param transformation RSA/CBC/PKCS1Padding
     * @return 十六进制字符串的RSA解密byte[]
     * @see EncryptUtils#encryptDES2Base64(byte[], byte[], String, byte[]) 参数详情
     */
    public static byte[] decryptHexStringRSA(final String data, final byte[] key, final boolean isPublicKey, final String transformation) throws Throwable {
        return decryptRSA(hexString2Bytes(data), key, isPublicKey, transformation);
    }

    /**
     * RSA解密
     *
     * @param isPublicKey    使用公钥为True，使用私钥为FALSE
     * @param transformation RSA/CBC/PKCS1Padding
     * @return RSA解密的byte[]
     * @see EncryptUtils#encryptDES2Base64(byte[], byte[], String, byte[]) 参数详情
     */
    public static byte[] decryptRSA(final byte[] data, final byte[] key, final boolean isPublicKey, final String transformation) throws Throwable {
        return rsaTemplate(data, key, isPublicKey, transformation, false);
    }

    /**
     * RSA加密或解密
     *
     * @param isPublicKey    使用公钥为True，使用私钥为FALSE
     * @param transformation RSA/CBC/PKCS1Padding
     * @param isEncrypt      True是加密,False是解密.
     * @return RSA加密或解密的byte[]
     * @see EncryptUtils#encryptDES2Base64(byte[], byte[], String, byte[]) 参数详情
     */
    private static byte[] rsaTemplate(final byte[] data, final byte[] key, final boolean isPublicKey, final String transformation, final boolean isEncrypt) throws Throwable {
        if (data == null || data.length == 0 || key == null || key.length == 0) {
            return null;
        }
        Key rsaKey;
        if (isPublicKey) {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(key);
            rsaKey = KeyFactory.getInstance("RSA").generatePublic(keySpec);
        } else {
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(key);
            rsaKey = KeyFactory.getInstance("RSA").generatePrivate(keySpec);
        }
        if (rsaKey == null) {
            return null;
        }
        Cipher cipher = Cipher.getInstance(transformation);
        cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, rsaKey);
        int len = data.length;
        int maxLen = isEncrypt ? 117 : 128;
        int count = len / maxLen;
        if (count > 0) {
            byte[] ret = new byte[0];
            byte[] buff = new byte[maxLen];
            int index = 0;
            for (int i = 0; i < count; i++) {
                System.arraycopy(data, index, buff, 0, maxLen);
                ret = joins(ret, cipher.doFinal(buff));
                index += maxLen;
            }
            if (index != len) {
                int restLen = len - index;
                buff = new byte[restLen];
                System.arraycopy(data, index, buff, 0, restLen);
                ret = joins(ret, cipher.doFinal(buff));
            }
            return ret;
        } else {
            return cipher.doFinal(data);
        }
    }

    /**
     * 链接方法
     *
     * @param prefix 前缀
     * @param suffix 后缀
     * @return 链接后的byte[]
     */
    private static byte[] joins(final byte[] prefix, final byte[] suffix) {
        byte[] ret = new byte[prefix.length + suffix.length];
        System.arraycopy(prefix, 0, ret, 0, prefix.length);
        System.arraycopy(suffix, 0, ret, prefix.length, suffix.length);
        return ret;
    }

    /**
     * 十六进制解密
     *
     * @param bytes 数据
     * @return 解密数据
     */
    private static String bytes2HexString(final byte[] bytes) {
        if (bytes == null) {
            return "";
        }
        int len = bytes.length;
        if (len <= 0) {
            return "";
        }
        char[] ret = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++) {
            ret[j++] = EncryptConstant.HEX_DIGITS[bytes[i] >> 4 & 0x0f];
            ret[j++] = EncryptConstant.HEX_DIGITS[bytes[i] & 0x0f];
        }
        return new String(ret);
    }

    /**
     * 十六进制加密
     *
     * @param hexString 数据
     * @return 加密数据
     */
    private static byte[] hexString2Bytes(String hexString) {
        if (isSpace(hexString)) {
            return null;
        }
        int len = hexString.length();
        if (len % 2 != 0) {
            hexString = "0" + hexString;
            len = len + 1;
        }
        char[] hexBytes = hexString.toUpperCase().toCharArray();
        byte[] ret = new byte[len >> 1];
        for (int i = 0; i < len; i += 2) {
            ret[i >> 1] = (byte) (hex2Dec(hexBytes[i]) << 4 | hex2Dec(hexBytes[i + 1]));
        }
        return ret;
    }

    private static int hex2Dec(final char hexChar) {
        if (hexChar >= '0' && hexChar <= '9') {
            return hexChar - '0';
        } else if (hexChar >= 'A' && hexChar <= 'F') {
            return hexChar - 'A' + 10;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * base64加密
     *
     * @param input 数据
     * @return 加密数据
     */
    private static byte[] base64Encode(final byte[] input) {
        return Base64.encode(input, Base64.NO_WRAP);
    }

    /**
     * base64解密
     *
     * @param input 数据
     * @return 解密数据
     */
    public static byte[] base64Decode(final byte[] input) {
        return Base64.decode(input, Base64.NO_WRAP);
    }

    private static boolean isSpace(final String s) {
        if (s == null) {
            return true;
        }
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
