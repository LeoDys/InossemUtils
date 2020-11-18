package com.inossem_library.encrypt.encode;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;

/**
 * 编码解码相关工具类
 *
 * @since 1.0.20
 */
public class EncodeUtils {

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
     * @param input 数据
     * @return 编码数据 byte数组
     */
    public static byte[] base64Encode(final byte[] input) {
        return android.util.Base64.encode(input, android.util.Base64.NO_WRAP);
    }

    /**
     * base64编码
     *
     * @param input 数据
     * @return 编码数据 byte数组
     */
    public static byte[] base64StrEncode(final String input, Charset charset) {
        return android.util.Base64.encode(input.getBytes(charset == null ? Charset.defaultCharset() : charset), android.util.Base64.NO_WRAP);
    }

    /**
     * base64解码
     *
     * @param input 数据
     * @return 解码数据 byte数组
     */
    public static byte[] base64Decode(final byte[] input) {
        return android.util.Base64.decode(input, android.util.Base64.NO_WRAP);
    }

    /**
     * base64解码
     *
     * @param input 数据
     * @return 解码数据 byte数组
     */
    public static byte[] base64StrDecode(final String input, Charset charset) {
        return android.util.Base64.decode(input.getBytes(charset == null ? Charset.defaultCharset() : charset), android.util.Base64.NO_WRAP);
    }
}
