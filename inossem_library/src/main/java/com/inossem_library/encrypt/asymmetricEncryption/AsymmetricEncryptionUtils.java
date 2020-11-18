package com.inossem_library.encrypt.asymmetricEncryption;

import com.inossem_library.encrypt.encode.EncodeUtils;

import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyAgreement;
import javax.crypto.NoSuchPaddingException;

public class AsymmetricEncryptionUtils {

    public static final String ALGORITHM_DH = "DH";

    public static final String ALGORITHM_RSA = "RSA";

    public static final String ALGORITHM_DSA = "DSA";

    public static final String SIGN_MD5_RSA = "MD5withRSA";

    public static final String SIGN_SHA1_RSA = "SHA1withRSA";

    public static final String SIGN_SHA256_RSA = "SHA256withRSA";

    public static final String SIGN_SHA1_DSA = "SHA1withDSA";

    public static final int KEY_SIZE_DEFAULT = 512;

    public static final String KEY_PUBLIC = "public";

    public static final String KEY_PRIVATE = "private";

    /**
     * 生成密钥对
     *
     * @param algorithm 算法名称
     * @param keysize   算法位数 默认512
     * @return 密钥对
     * <p>
     * @since 1.0.20
     */
    public static Map<String, byte[]> generateKeyPair(String algorithm, Integer keysize) {
        try {
            KeyPairGenerator kpGen = KeyPairGenerator.getInstance(algorithm);
            kpGen.initialize(keysize == null ? KEY_SIZE_DEFAULT : keysize);
            KeyPair keyPair = kpGen.generateKeyPair();
            Map<String, byte[]> result = new HashMap<>();
            result.put(KEY_PUBLIC, keyPair.getPublic().getEncoded());
            result.put(KEY_PRIVATE, keyPair.getPrivate().getEncoded());
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得密钥
     *
     * @param algorithm           对称算法
     * @param localPrivateKeyByte 己方私钥
     * @param receivedPubKeyByte  对方公钥
     * @return 新密钥
     * <p>
     * @since 1.0.20
     */
    public static byte[] generateSecretKey(String algorithm, byte[] receivedPubKeyByte, byte[] localPrivateKeyByte) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            // 从byte[]恢复PublicKey
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(receivedPubKeyByte);
            PublicKey receivedPublicKey = keyFactory.generatePublic(publicKeySpec);
            // 从byte[]恢复PrivateKey
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(localPrivateKeyByte);
            PrivateKey localPrivateKey = keyFactory.generatePrivate(privateKeySpec);
            // 生成本地密钥
            KeyAgreement keyAgreement = KeyAgreement.getInstance(algorithm);
            keyAgreement.init(localPrivateKey);
            keyAgreement.doPhase(receivedPublicKey, true); // 对方的PublicKey
            return keyAgreement.generateSecret();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 密钥对中获取公钥
     *
     * @param keyPair 密钥对
     * @return 公钥
     * <p>
     * @since 1.0.20
     */
    public static byte[] getPublicKey(Map<String, byte[]> keyPair) {
        return keyPair.get(KEY_PUBLIC);
    }

    /**
     * 密钥对中获取私钥
     *
     * @param keyPair 密钥对
     * @return 私钥
     * <p>
     * @since 1.0.20
     */
    public static byte[] getPrivateKey(Map<String, byte[]> keyPair) {
        return keyPair.get(KEY_PRIVATE);
    }

    /**
     * RSA加密 公钥加
     *
     * @param publicKeyByte 公钥
     * @param plaintext     待加密数据
     * @param charset       加密编码格式 默认UTF_8
     * @return 加密完成数据
     * <p>
     * @since 1.0.20
     */
    public static String encryptRSA(byte[] publicKeyByte, String plaintext, Charset charset) {
        try {
            // 从byte[]恢复PublicKey
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyByte);
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
            Cipher cipher = Cipher.getInstance(ALGORITHM_RSA);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] plaintextByte = plaintext.getBytes(charset == null ? Charset.defaultCharset() : charset);
            byte[] result = cipher.doFinal(plaintextByte);
            return new String(EncodeUtils.base64Encode(result), charset == null ? Charset.defaultCharset() : charset);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
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
     * RSA解密 私钥解
     *
     * @param privateKeyByte 私钥
     * @param ciphertext     待解密数据
     * @param charset        解密编码格式 默认UTF_8
     * @return 解密完成数据
     * <p>
     * @since 1.0.20
     */
    public static String decryptRSA(byte[] privateKeyByte, String ciphertext, Charset charset) {
        try {
            // 从byte[]恢复PrivateKey
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyByte);
            PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
            Cipher cipher = Cipher.getInstance(ALGORITHM_RSA);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] ciphertextByte = EncodeUtils.base64StrDecode(ciphertext, charset == null ? Charset.defaultCharset() : charset);
            byte[] result = cipher.doFinal(ciphertextByte);
            return new String(result, charset == null ? Charset.defaultCharset() : charset);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
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

    public static String signRSA(byte[] privateKeyByte, String plaintext, Charset charset) {
        return sign(ALGORITHM_RSA, SIGN_SHA1_RSA, privateKeyByte, plaintext, charset);
    }

    //效率比RSA高
    public static String signDSA(byte[] privateKeyByte, String plaintext, Charset charset) {
        return sign(ALGORITHM_DSA, SIGN_SHA1_DSA, privateKeyByte, plaintext, charset);
    }

    public static String sign(String algorithm, String signAlgorithm, byte[] privateKeyByte, String plaintext, Charset charset) {
        try {
            // 从byte[]恢复PrivateKey
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyByte);
            PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
            Signature signature = Signature.getInstance(signAlgorithm);
            signature.initSign(privateKey);
            signature.update(plaintext.getBytes(charset == null ? Charset.defaultCharset() : charset));
            byte[] result = signature.sign();
            return new String(EncodeUtils.base64Encode(result), charset == null ? Charset.defaultCharset() : charset);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean verifyRSA(byte[] publicKeyByte, String plaintext, String ciphertext, Charset charset) {
        return verify(ALGORITHM_RSA, SIGN_SHA1_RSA, publicKeyByte, plaintext, ciphertext, charset);
    }

    //效率比RSA高
    public static boolean verifyDSA(byte[] publicKeyByte, String plaintext, String ciphertext, Charset charset) {
        return verify(ALGORITHM_DSA, SIGN_SHA1_DSA, publicKeyByte, plaintext, ciphertext, charset);
    }

    public static boolean verify(String algorithm, String signAlgorithm, byte[] publicKeyByte, String plaintext, String ciphertext, Charset charset) {
        try {
            // 从byte[]恢复PublicKey
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyByte);
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
            Signature v = Signature.getInstance(signAlgorithm);
            v.initVerify(publicKey);
            byte[] ciphertextByte = EncodeUtils.base64StrDecode(ciphertext, charset == null ? Charset.defaultCharset() : charset);
            v.update(plaintext.getBytes(charset == null ? Charset.defaultCharset() : charset));
            boolean result = v.verify(ciphertextByte);
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return false;
    }
}
