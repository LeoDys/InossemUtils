package com.inossem_library.encrypt.certificate;

import com.inossem_library.encrypt.encode.EncodeUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * keystore证书操作工具类
 * <p>
 *
 * @since 1.0.20
 */
public class CertificateUtils {

    /**
     * 加载本地证书
     *
     * @param keyStoreInputStream 证书输入流
     * @param password            证书密码
     * @return 证书keystore
     * <p>
     * @since 1.0.20
     */
    public static KeyStore loadKeyStore(InputStream keyStoreInputStream, String password) {
        if (keyStoreInputStream == null) {
            return null;
        }
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(keyStoreInputStream, password.toCharArray());
            return keyStore;
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加载本地证书 根据证书路径 生成file加载
     *
     * @param filePath 证书路径
     * @param password 证书密码
     * @return 证书keystore
     * <p>
     * @since 1.0.20
     */
    public static KeyStore loadKeyStore(String filePath, String password) {
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(new FileInputStream(file), password.toCharArray());
            return keyStore;
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取证书中的私钥
     *
     * @param keyStore 证书keystore
     * @param alias    证书别名
     * @param password 证书密码
     * @return 证书中的私钥
     * <p>
     * @since 1.0.20
     */
    public static PrivateKey getPriveteKey(KeyStore keyStore, String alias, String password) {
        try {
            PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, password.toCharArray());
            return privateKey;
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取证书
     *
     * @param keyStore 证书keystore
     * @param alias    证书别名
     * @return 证书的X509（提供了一个标准访问X.509证书的所有属性的方法）
     * <p>
     * @since 1.0.20
     */
    public static X509Certificate getCertificate(KeyStore keyStore, String alias) {
        try {
            X509Certificate certificate = (X509Certificate) keyStore.getCertificate(alias);
            return certificate;
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 利用证书加密数据
     *
     * @param certificate 证书的X509
     * @param plaintext   待加密数据
     * @param charset     加密编码格式
     * @return 加密完成的数据
     * <p>
     * @since 1.0.20
     */
    public static String encrypt(X509Certificate certificate, String plaintext, Charset charset) {
        try {
            Cipher cipher = Cipher.getInstance(certificate.getPublicKey().getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, certificate.getPublicKey());
            byte[] plaintextByte = plaintext.getBytes(charset == null ? Charset.defaultCharset() : charset);
            byte[] result = cipher.doFinal(plaintextByte);
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

    /**
     * 利用私钥解密数据
     *
     * @param privateKey 证书的私钥
     * @param ciphertext 待解密数据
     * @param charset    解密编码格式
     * @return 解密完成的数据
     * <p>
     * @since 1.0.20
     */
    public static String decrypt(PrivateKey privateKey, String ciphertext, Charset charset) {
        try {
            Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] ciphertextByte = EncodeUtils.base64StrDecode(ciphertext, charset == null ? Charset.defaultCharset() : charset);
            byte[] result = cipher.doFinal(ciphertextByte);
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

    /**
     * 利用私钥和证书的X509对数据进行签名
     *
     * @param privateKey  证书的私钥
     * @param certificate 证书的X509
     * @param plaintext   待签名数据
     * @param charset     签名编码格式
     * @return 签名完成的数据
     * <p>
     * @since 1.0.20
     */
    public static String sign(PrivateKey privateKey, X509Certificate certificate, String plaintext, Charset charset) {
        try {
            Signature signature = Signature.getInstance(certificate.getSigAlgName());
            signature.initSign(privateKey);
            signature.update(plaintext.getBytes(charset == null ? Charset.defaultCharset() : charset));
            byte[] result = signature.sign();
            return new String(result, charset == null ? Charset.defaultCharset() : charset);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 利用证书的X509对数据签名校验
     *
     * @param certificate 证书的X509
     * @param plaintext   待校验数据
     * @param charset     校验编码格式
     * @return 校验完成的结果
     * <p>
     * @since 1.0.20
     */
    public static boolean verify(X509Certificate certificate, String plaintext, String signText, Charset charset) {
        try {
            byte[] signTextByte = EncodeUtils.base64StrDecode(signText, charset == null ? Charset.defaultCharset() : charset);
            Signature signature = Signature.getInstance(certificate.getSigAlgName());
            signature.initVerify(certificate);
            signature.update(plaintext.getBytes(charset == null ? Charset.defaultCharset() : charset));
            return signature.verify(signTextByte);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        }
        return false;
    }

}
