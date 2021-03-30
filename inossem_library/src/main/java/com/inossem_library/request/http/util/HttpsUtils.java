package com.inossem_library.request.http.util;

import android.content.Context;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import okhttp3.OkHttpClient;

/**
 * @author LeoDys E-mail:changwen.sun@inossem.com 2020/11/9 11:44
 * @version 1.0.8
 * @since 1.0.8
 */
public class HttpsUtils {

    public static InputStream getInputStreamFromAsset(Context context, String fileName) {
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    /**
     * 创建只信任指定证书的TrustManager
     *
     * @param inputStream：证书输入流
     * @return
     */
    @Nullable
    public static X509TrustManager createTrustCustomTrustManager(InputStream inputStream) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);

            Certificate certificate = certificateFactory.generateCertificate(inputStream);
            //将证书放入keystore中
            String certificateAlias = "ca";
            keyStore.setCertificateEntry(certificateAlias, certificate);
            if (inputStream != null) {
                inputStream.close();
            }

            TrustManagerFactory trustManagerFactory = TrustManagerFactory.
                    getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();

            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:"
                        + Arrays.toString(trustManagers));
            }
            return (X509TrustManager) trustManagers[0];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static OkHttpClient createSSLClient(X509TrustManager x509TrustManager) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                //再原有分装添加
                .sslSocketFactory(createSSLSocketFactory(x509TrustManager), x509TrustManager)
                .hostnameVerifier(new TrustAllHostnameVerifier());
        return builder.build();
    }

    public static SSLSocketFactory createSSLSocketFactory(TrustManager trustManager) {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{trustManager}, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ssfFactory;
    }

    //实现信任所有域名的HostnameVerifier接口
    public static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            //域名校验，默认都通过
            return true;
        }
    }
}
