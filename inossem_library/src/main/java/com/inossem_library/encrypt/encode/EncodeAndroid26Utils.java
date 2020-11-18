package com.inossem_library.encrypt.encode;

import android.os.Build;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Base64;

import androidx.annotation.RequiresApi;

/**
 * 编码解码相关工具类
 *
 * @since 1.0.20
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class EncodeAndroid26Utils {

    /**
     * URL编码
     *
     * @param urlPlaintext 待编码URL
     * @param charSet      编码格式 默认UTF_8
     * @return 编码完成后的URL
     * <p>
     * @since 1.0.20
     */
    public static String urlEncode(String urlPlaintext, Charset charSet) throws UnsupportedEncodingException {
        return URLEncoder.encode(urlPlaintext, charSet == null ? Charset.defaultCharset().displayName() : charSet.displayName());
    }

    /**
     * URL解码
     *
     * @param urlCiphertext 待解码URL
     * @param charSet       编码格式 默认UTF_8
     * @return 解码完成后的URL
     * <p>
     * @since 1.0.20
     */
    public static String urlDecode(String urlCiphertext, Charset charSet) throws UnsupportedEncodingException {
        return URLDecoder.decode(urlCiphertext, charSet == null ? Charset.defaultCharset().displayName() : charSet.displayName());
    }

    /**
     * base64编码
     *
     * @param base64Plaintext 待编码字符串
     * @param charSet         编码格式 默认UTF_8
     * @return 编码完成字符串
     * <p>
     * @since 1.0.20
     */

    public static String base64EndoceO(String base64Plaintext, Charset charSet) {
        byte[] bytes = base64Plaintext.getBytes(charSet == null ? Charset.defaultCharset() : charSet);
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * base64解码
     *
     * @param base64Ciphertext 待解码字符串
     * @param charset          解码格式 默认UTF_8
     * @return 解码完成字符串
     * <p>
     * @since 1.0.20
     */
    public static String base64DecodeO(String base64Ciphertext, Charset charset) {
        byte[] bytes = base64Ciphertext.getBytes(charset == null ? Charset.defaultCharset() : charset);
        return new String(Base64.getDecoder().decode(bytes));
    }

    /**
     * base64 url编码
     *
     * @param base64URLPaintext 待编码字符串
     * @param charSet           编码格式 默认UTF_8
     * @return 编码完成字符串
     * <p>
     * @since 1.0.20
     */
    public static String base64URLEncodeO(String base64URLPaintext, Charset charSet) {
        byte[] bytes = base64URLPaintext.getBytes(charSet == null ? Charset.defaultCharset() : charSet);
        return Base64.getUrlEncoder().encodeToString(bytes);
    }

    /**
     * base64 Url 解码
     *
     * @param base64URLCiphertext 待解码字符串
     * @param charset             解码格式 默认UTF_8
     * @return 解码完成字符串
     * <p>
     * @since 1.0.20
     */

    public static String base64URLDecodeO(String base64URLCiphertext, Charset charset) {
        byte[] bytes = base64URLCiphertext.getBytes(charset == null ? Charset.defaultCharset() : charset);
        return new String((Base64.getUrlDecoder().decode(bytes)));
    }

    /**
     * base64编码
     *
     * @param input 数据
     * @return 编码数据
     */
    private static byte[] base64Encode(final byte[] input) {
        return android.util.Base64.encode(input, android.util.Base64.NO_WRAP);
    }

    /**
     * base64编码
     *
     * @param input 数据
     * @return 编码数据
     */
    private static byte[] base64StrEncode(final String input, Charset charset) {
        return android.util.Base64.encode(input.getBytes(charset == null ? Charset.defaultCharset() : charset), android.util.Base64.NO_WRAP);
    }

}
