package com.inossem.security.en_decrypt;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.inossem.BaseActivity;
import com.inossem.R;
import com.inossem.util.Utils;
import com.inossem_library.encrypt.asymmetricEncryption.AsymmetricEncryptionUtils;
import com.inossem_library.encrypt.certificate.CertificateUtils;
import com.inossem_library.encrypt.encode.EncodeUtils;
import com.inossem_library.encrypt.hash.HashUtils;
import com.inossem_library.encrypt.symmetricEncryption.SymmetricEncryptionUtils;
import com.inossem_library.tips.toast.util.ToastUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.crypto.SecretKey;

public class NewDecryptActivity extends BaseActivity {

    @Override
    protected void onCreateImpl(Bundle savedInstanceState) {
        setContentView(R.layout.activity_decrypt);
        LinearLayout buttonLayout = findViewById(R.id.buttonLayout);
        findViewById(R.id.description).setVisibility(View.GONE);
        findViewById(R.id.show).setVisibility(View.GONE);

        Utils.createButtons(NewDecryptActivity.this, buttonLayout, 20, new Utils.ButtonListener() {
            @Override
            public void onCreated(final Button button, int position) {
                switch (position) {
                    case 0:
                        button.setText("编码解码");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    String urlPlaintext = "https://www.baidu.com/s?wd=沈阳";
                                    String urlCipherText = EncodeUtils.urlEncode(urlPlaintext, null);
                                    System.out.println(urlCipherText);
                                    System.out.println(EncodeUtils.urlDecode(urlCipherText, null));

                                    String base64Plaintext = "在java中，一切存储在硬盘上的数据都是二进制的字节";
                                    String base64Ciphertext = new String(EncodeUtils.base64StrEncode(base64Plaintext, null), Charset.defaultCharset());
                                    System.out.println(base64Ciphertext);
                                    System.out.println(new String(EncodeUtils.base64StrDecode(base64Ciphertext, null), Charset.defaultCharset()));
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                                System.out.println("======================================================================================");
                            }
                        });
                        break;
                    case 1:
                        button.setText("Hash");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                System.out.println("HelloWorld MD5 ************" + HashUtils.hashMD5("HelloWorld", Charset.forName("UTF-8")));
                                System.out.println("HelloWorld SHA1 ************" + HashUtils.hashSHA1("HelloWorld", null));
                                System.out.println("HelloWorld SHA256 ************" + HashUtils.hashSHA256("HelloWorld", null));
                                System.out.println("HelloWorld SHA512 ************" + HashUtils.hashSHA512("HelloWorld", null));
                                System.out.println("===========================================");
                                System.out.println("HelloWorld ALGORITHM_SHA224 ************" + HashUtils.hashWithAlgorithm(HashUtils.ALGORITHM_SHA224, "HelloWorld", null));
                                System.out.println("HelloWorld ALGORITHM_SHA384 ************" + HashUtils.hashWithAlgorithm(HashUtils.ALGORITHM_SHA384, "HelloWorld", null));
                                System.out.println("======================================================================================");
                            }
                        });
                        break;
                    case 2:
                        button.setText("HamcHash");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // 密钥生成 TODO createHmacSecretKey getHmacSecretKey(这个get的函数的作用是什么) 密钥可以自己随意定义
                                String secretKeyStr = HashUtils.createHmacSecretKey(HashUtils.HMAC_ALGORITHM_MD5);
                                String secretKeyStr2 = HashUtils.createHmacSecretKey(HashUtils.HMAC_ALGORITHM_SHA1);
                                System.out.println(secretKeyStr);
                                System.out.println(secretKeyStr2);
                                System.out.println("===========================================");
                                // 将密钥转为SecretKey格式 供以下加密使用
                                SecretKey secretKey = HashUtils.getHmacSecretKey(HashUtils.HMAC_ALGORITHM_MD5, secretKeyStr/*TODO 可更换为自定义密钥*/);
                                System.out.println(new BigInteger(1, secretKey.getEncoded()).toString(16));
                                System.out.println("===========================================");
                                System.out.println(HashUtils.hmacHashMD5(secretKey, "HelloWorld", null));
                                System.out.println(HashUtils.hmacHashSHA1(secretKey, "HelloWorld", null));
                                System.out.println(HashUtils.hmacHashSHA256(secretKey, "HelloWorld", null));
                                System.out.println(HashUtils.hmacHashSHA512(secretKey, "HelloWorld", null));
                                System.out.println(HashUtils.hmacHashWithAlgorithm(HashUtils.HMAC_ALGORITHM_SHA224, secretKey, "HelloWorld", null));
                                System.out.println(HashUtils.hmacHashWithAlgorithm(HashUtils.HMAC_ALGORITHM_SHA384, secretKey, "HelloWorld", null));
                                System.out.println("======================================================================================");
                            }
                        });
                        break;
                    case 3:
                        button.setText("AES");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                System.out.println("=====================默认AES加密 ECB======================");
                                //默认AES加密 ECB
                                String ciphertext = SymmetricEncryptionUtils.encryptAES(SymmetricEncryptionUtils.DEFAULT_AES_SECRET_KEY, "HellowWorld", null);
                                System.out.println(ciphertext);
                                String plaintext = SymmetricEncryptionUtils.decryptAES(SymmetricEncryptionUtils.DEFAULT_AES_SECRET_KEY, ciphertext, null);
                                System.out.println(plaintext);

                                System.out.println("=====================AES加密，CBC，每次随机IV，(建议使用)======================");
                                //AES加密，CBC，每次随机IV，(建议使用)
                                String ciphertext1 = SymmetricEncryptionUtils.encryptAESRandomIV(SymmetricEncryptionUtils.DEFAULT_AES_SECRET_KEY, "HellowWorld", null);
                                System.out.println(ciphertext1);
                                String plaintext1 = SymmetricEncryptionUtils.decryptAESRandomIV(SymmetricEncryptionUtils.DEFAULT_AES_SECRET_KEY, ciphertext1, null);
                                System.out.println(plaintext1);
                                System.out.println("=====================AES加密，CBC，自定义IV======================");
                                //AES加密，CBC，自定义IV
                                String ciphertext2 = SymmetricEncryptionUtils.encryptAESWithIV(SymmetricEncryptionUtils.DEFAULT_AES_SECRET_KEY,
                                        "HellowWorld", SymmetricEncryptionUtils.DEFAULT_AES_INITIALIZATION_VECTOR, null);
                                System.out.println(ciphertext2);
                                String plaintext2 = SymmetricEncryptionUtils.decryptAESWithIV(SymmetricEncryptionUtils.DEFAULT_AES_SECRET_KEY,
                                        ciphertext2, SymmetricEncryptionUtils.DEFAULT_AES_INITIALIZATION_VECTOR, null);
                                System.out.println(plaintext2);
                                System.out.println("======================================================================================");
                            }
                        });
                        break;
                    case 4:
                        button.setText("RSA");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // DH 算法不通
//                                Charset charset = Charset.defaultCharset();
//                                Map<String, byte[]> keyPairA = AsymmetricEncryptionUtils.generateKeyPair(AsymmetricEncryptionUtils.ALGORITHM_DH, null);
//                                Map<String, byte[]> keyPairB = AsymmetricEncryptionUtils.generateKeyPair(AsymmetricEncryptionUtils.ALGORITHM_DH, null);
//                                byte[] publicKeyA = AsymmetricEncryptionUtils.getPublicKey(keyPairA);
//                                byte[] privateKeyA = AsymmetricEncryptionUtils.getPrivateKey(keyPairA);
//                                byte[] publicKeyB = AsymmetricEncryptionUtils.getPublicKey(keyPairB);
//                                byte[] privateKeyB = AsymmetricEncryptionUtils.getPrivateKey(keyPairB);
//                                byte[] secretKeyA = AsymmetricEncryptionUtils.generateSecretKey(AsymmetricEncryptionUtils.ALGORITHM_DH, publicKeyB, privateKeyA);
//                                byte[] secretKeyB = AsymmetricEncryptionUtils.generateSecretKey(AsymmetricEncryptionUtils.ALGORITHM_DH, publicKeyA, privateKeyB);
//                                System.out.println("A公钥：" + new String(EncodeUtils.base64Encode(publicKeyA), charset == null ? Charset.defaultCharset() : charset));
//                                System.out.println("A私钥：" + new String(EncodeUtils.base64Encode(privateKeyA), charset == null ? Charset.defaultCharset() : charset));
//                                System.out.println("A密钥：" + new String(EncodeUtils.base64Encode(secretKeyA), charset == null ? Charset.defaultCharset() : charset));
//                                System.out.println("B公钥：" + new String(EncodeUtils.base64Encode(publicKeyB), charset == null ? Charset.defaultCharset() : charset));
//                                System.out.println("B私钥：" + new String(EncodeUtils.base64Encode(privateKeyB), charset == null ? Charset.defaultCharset() : charset));
//                                System.out.println("B密钥：" + new String(EncodeUtils.base64Encode(secretKeyB), charset == null ? Charset.defaultCharset() : charset));
                                Map<String, byte[]> keyPairC = AsymmetricEncryptionUtils.generateKeyPair(AsymmetricEncryptionUtils.ALGORITHM_RSA, null);
                                Map<String, byte[]> keyPairD = AsymmetricEncryptionUtils.generateKeyPair(AsymmetricEncryptionUtils.ALGORITHM_RSA, null);
                                byte[] publicKeyC = AsymmetricEncryptionUtils.getPublicKey(keyPairC);
                                byte[] privateKeyC = AsymmetricEncryptionUtils.getPrivateKey(keyPairC);
                                byte[] publicKeyD = AsymmetricEncryptionUtils.getPublicKey(keyPairD);
                                byte[] privateKeyD = AsymmetricEncryptionUtils.getPrivateKey(keyPairD);
                                String ciphertextC = AsymmetricEncryptionUtils.encryptRSA(publicKeyC, "MOBILE_DEVELOPER", null);
                                String plaintextC = AsymmetricEncryptionUtils.decryptRSA(privateKeyC, ciphertextC, null);
                                System.out.println("C公钥加密：" + ciphertextC);
                                System.out.println("C私钥解密：" + plaintextC);
                                String ciphertextD = AsymmetricEncryptionUtils.encryptRSA(publicKeyD, "MOBILE_DEVELOPER", null);
                                String plaintextD = AsymmetricEncryptionUtils.decryptRSA(privateKeyD, ciphertextD, null);
                                System.out.println("D公钥加密：" + ciphertextD);
                                System.out.println("D私钥解密：" + plaintextD);
                                System.out.println("======================================================================================");
                            }
                        });
                        break;
                    case 5:
                        button.setText("RSA/DSA签名");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                long beginRSA = System.currentTimeMillis();
                                Map<String, byte[]> keyPairRSA = AsymmetricEncryptionUtils.generateKeyPair(AsymmetricEncryptionUtils.ALGORITHM_RSA, null);
                                byte[] publicKeyRSA = AsymmetricEncryptionUtils.getPublicKey(keyPairRSA);
                                byte[] privateKeyRSA = AsymmetricEncryptionUtils.getPrivateKey(keyPairRSA);
                                String ciphertextRSA = AsymmetricEncryptionUtils.signRSA(privateKeyRSA, "HelloWorld", null);
                                boolean resultRSA = AsymmetricEncryptionUtils.verifyRSA(publicKeyRSA, "HelloWorld", ciphertextRSA, null);
                                System.out.println("RSA签名校验结果：" + resultRSA);
                                long endRSA = System.currentTimeMillis();
                                System.out.println("RSA签名校验耗时：" + (endRSA - beginRSA));

                                long beginDSA = System.currentTimeMillis();
                                Map<String, byte[]> keyPairDSA = AsymmetricEncryptionUtils.generateKeyPair(AsymmetricEncryptionUtils.ALGORITHM_DSA, null);
                                byte[] publicKeyDSA = AsymmetricEncryptionUtils.getPublicKey(keyPairDSA);
                                byte[] privateKeyDSA = AsymmetricEncryptionUtils.getPrivateKey(keyPairDSA);
                                String ciphertextDSA = AsymmetricEncryptionUtils.signDSA(privateKeyDSA, "HelloWorld", null);
                                boolean resultDSA = AsymmetricEncryptionUtils.verifyDSA(publicKeyDSA, "HelloWorld", ciphertextDSA, null);
                                System.out.println("DSA签名校验结果：" + resultDSA);
                                long endDSA = System.currentTimeMillis();
                                System.out.println("DSA签名校验耗时：" + (endDSA - beginDSA));
                                System.out.println("======================================================================================");
                            }
                        });
                        break;
                    case 6:
                        button.setText("数字证书keystore");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Charset charset = Charset.defaultCharset();
                                String plaintext = "HelloWorld";
                                // 读取KeyStore
                                InputStream inputStream = null;
                                KeyStore ks = null;
                                try {
                                    //获得assets资源管理器（assets中的文件无法直接访问，可以使用AssetManager访问）
//                                    inputStream = getAssets().open("test.keystore");
//                                    InputStream jpgInputStream = getAssets().open("abc.jpg");
//                                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                    AssetManager asset = getResources().getAssets();
                                    InputStream ceris = asset.open("test.keystore");
                                    if (ceris == null) {
                                        ToastUtils.show(NewDecryptActivity.this, "未找到数字证书");
                                        return;
                                    }
//                                     FileInputStream is = new FileInputStream(keyStorePath);
//                                     KeyStore.getInstance(KeyStore.getDefaultType());
                                    ks = KeyStore.getInstance("BKS");
                                    ks.load(ceris, null);
                                    ceris.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (CertificateException e) {
                                    e.printStackTrace();
                                } catch (NoSuchAlgorithmException e) {
                                    e.printStackTrace();
                                } catch (KeyStoreException e) {
                                    e.printStackTrace();
                                }


                                KeyStore keyStore = CertificateUtils.loadKeyStore(inputStream, "123456");
                                // 读取私钥:
                                PrivateKey privateKey = CertificateUtils.getPriveteKey(keyStore, "test", "123456");
                                // 读取证书:
                                X509Certificate certificate = CertificateUtils.getCertificate(ks, "test");
                                System.out.println("公钥：" + new String(EncodeUtils.base64Encode(certificate.getPublicKey().getEncoded()),
                                        charset == null ? Charset.defaultCharset() : charset));
                                System.out.println("密钥：" + new String(EncodeUtils.base64Encode(privateKey.getEncoded()),
                                        charset == null ? Charset.defaultCharset() : charset));
                                // 加密:
                                String ciphertext = CertificateUtils.encrypt(certificate, plaintext, null);
                                System.out.println("加密后：" + ciphertext);
                                // 解密:
                                String result = CertificateUtils.decrypt(privateKey, ciphertext, null);
                                System.out.println("解密后：" + result);
                                // 签名:
                                String sign = CertificateUtils.sign(privateKey, certificate, plaintext, null);
                                System.out.println("签名：" + sign);
                                // 验证签名:
                                boolean verify = CertificateUtils.verify(certificate, plaintext, sign, null);
                                System.out.println("验证结果：" + verify);

                                System.out.println("======================================================================================");

                            }
                        });
                        break;
                }
            }
        });
    }
}