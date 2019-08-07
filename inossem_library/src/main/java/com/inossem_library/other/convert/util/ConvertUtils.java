package com.inossem_library.other.convert.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.View;

import com.inossem_library.exception.constant.ExceptionEnum;
import com.inossem_library.exception.InossemException;
import com.inossem_library.other.convert.constant.ConvertConstants;
import com.inossem_library.other.string.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Objects;

/**
 * @author 郭晓龙
 * @time on 2019/7/15
 * @email xiaolong.guo@inossem.com
 * @describe 转换封装 字符串、字节、比特、输入输出流、位图、View、Drawable、sp、dp、xp相互转换
 */
public class ConvertUtils {

    /**
     * 不能被实例化
     */
    private ConvertUtils() {
        throw new UnsupportedOperationException("不能被实例化");
    }

    /**
     * 字节转比特
     *
     * @param bytes 字节
     * @return 返回比特
     */
    public static String bytes2Bits(@NonNull final byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "bytes不能为空！");
        }
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            for (int j = 7; j >= 0; --j) {
                sb.append(((aByte >> j) & 0x01) == 0 ? '0' : '1');
            }
        }
        return sb.toString();
    }

    /**
     * 比特转字节
     *
     * @param bits 比特
     * @return 返回字节
     */
    public static byte[] bits2Bytes(@NonNull String bits) {
        if (StringUtils.isEmpty(bits)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "bits不能为空！");
        }
        int lenMod = bits.length() % 8;
        int byteLen = bits.length() / 8;
        // 添加0直到长度到8
        if (lenMod != 0) {
            for (int i = lenMod; i < 8; i++) {
                bits = "0" + bits;
            }
            byteLen++;
        }
        byte[] bytes = new byte[byteLen];
        for (int i = 0; i < byteLen; ++i) {
            for (int j = 0; j < 8; ++j) {
                bytes[i] <<= 1;
                bytes[i] |= bits.charAt(i * 8 + j) - '0';
            }
        }
        return bytes;
    }

    /**
     * 字节转字符
     *
     * @param bytes 字节
     * @return 返回字符
     */
    public static char[] bytes2Chars(@NonNull final byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "bytes不能为空！");
        }
        int len = bytes.length;
        char[] chars = new char[len];
        for (int i = 0; i < len; i++) {
            chars[i] = (char) (bytes[i] & 0xff);
        }
        return chars;
    }

    /**
     * 字符转字节
     *
     * @param chars 字符
     * @return 返回字节
     */
    public static byte[] chars2Bytes(@NonNull final char[] chars) {
        if (chars == null || chars.length <= 0) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "chars不能为空！");
        }
        int len = chars.length;
        byte[] bytes = new byte[len];
        for (int i = 0; i < len; i++) {
            bytes[i] = (byte) (chars[i]);
        }
        return bytes;
    }

    /**
     * 字节转16进制字符串
     *
     * @param bytes 字节
     * @return 返回16进制字符串
     */
    public static String bytes2HexString(@NonNull final byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "bytes不能为空！");
        }
        int len = bytes.length;
        char[] ret = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++) {
            ret[j++] = ConvertConstants.hexDigits[bytes[i] >> 4 & 0x0f];
            ret[j++] = ConvertConstants.hexDigits[bytes[i] & 0x0f];
        }
        return new String(ret);
    }

    /**
     * 16进制字符串转字节
     *
     * @param hexString 16进制字符串
     * @return 返回字节
     */
    public static byte[] hexString2Bytes(@NonNull String hexString) {
        if (StringUtils.isEmpty(hexString)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "hexString不能为空！");
        }
        int len = hexString.length();
        if (len % 2 != 0) {
            hexString = "0" + hexString;
            len = len + 1;
        }
        char[] hexBytes = hexString.toUpperCase().toCharArray();
        byte[] ret = new byte[len >> 1];
        for (int i = 0; i < len; i += 2) {
            ret[i >> 1] = (byte) (hex2Int(hexBytes[i]) << 4 | hex2Int(hexBytes[i + 1]));
        }
        return ret;
    }

    /**
     * 16进制字符转整型
     *
     * @param hexChar 16进制字符
     * @return 返回整型
     */
    private static int hex2Int(@NonNull final char hexChar) {
        if (hexChar >= '0' && hexChar <= '9') {
            return hexChar - '0';
        } else if (hexChar >= 'A' && hexChar <= 'F') {
            return hexChar - 'A' + 10;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * 固定单位内存转字节
     *
     * @param memorySize 内存大小
     * @param unit       内存单位
     *                   <ul>
     *                   <li>{@link ConvertConstants#BYTE}</li>
     *                   <li>{@link ConvertConstants#KB}</li>
     *                   <li>{@link ConvertConstants#MB}</li>
     *                   <li>{@link ConvertConstants#GB}</li>
     *                   </ul>
     * @return 返回字节大小 如果memorySize<0返回-1
     */
    public static long memorySize2Byte(final long memorySize, @ConvertConstants.Unit final int unit) {
        if (memorySize < 0) {
            return -1;
        }
        return memorySize * unit;
    }

    /**
     * 字节转固定单位内存
     *
     * @param byteSize 字节大小
     * @param unit     内存单位
     *                 <ul>
     *                 <li>{@link ConvertConstants#BYTE}</li>
     *                 <li>{@link ConvertConstants#KB}</li>
     *                 <li>{@link ConvertConstants#MB}</li>
     *                 <li>{@link ConvertConstants#GB}</li>
     *                 </ul>
     * @return 返回内存单位大小 byteSize<0返回-1
     */
    public static double byte2MemorySize(final long byteSize, @ConvertConstants.Unit final int unit) {
        if (byteSize < 0) {
            return -1;
        }
        return (double) byteSize / unit;
    }

    /**
     * 字节转合适内存大小 精确到小数点后三位
     *
     * @param byteSize 字节大小
     * @return 返回合适内存大小 如果byteSize<0返回shouldn't be less than zero!
     */
    @SuppressLint("DefaultLocale")
    public static String byte2FitMemorySize(final long byteSize) {
        if (byteSize < 0) {
            return "shouldn't be less than zero!";
        } else if (byteSize < ConvertConstants.KB) {
            return String.format("%.3fB", (double) byteSize);
        } else if (byteSize < ConvertConstants.MB) {
            return String.format("%.3fKB", (double) byteSize / ConvertConstants.KB);
        } else if (byteSize < ConvertConstants.GB) {
            return String.format("%.3fMB", (double) byteSize / ConvertConstants.MB);
        } else {
            return String.format("%.3fGB", (double) byteSize / ConvertConstants.GB);
        }
    }

    /**
     * 输入流转输出流
     *
     * @param inputStream 输入流
     * @return 返回输出流
     */
    public static ByteArrayOutputStream input2OutputStream(@NonNull final InputStream inputStream) throws IOException {
        if (inputStream == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "inputStream不能为空！");
        }
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] b = new byte[ConvertConstants.KB];
            int len;
            while ((len = inputStream.read(b, 0, ConvertConstants.KB)) != -1) {
                os.write(b, 0, len);
            }
            return os;
        } finally {
            inputStream.close();
        }
    }

    /**
     * 输出流转输入流
     *
     * @param outputStream 输出流
     * @return 返回输入流
     */
    public ByteArrayInputStream output2InputStream(@NonNull final OutputStream outputStream) {
        Objects.requireNonNull(outputStream);
        if (outputStream == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "outputStream不能为空！");
        }
        return new ByteArrayInputStream(((ByteArrayOutputStream) outputStream).toByteArray());
    }

    /**
     * 输入流转字节
     *
     * @param inputStream 输入流
     * @return 返回字节
     */
    public static byte[] inputStream2Bytes(@NonNull final InputStream inputStream) throws IOException {
        if (inputStream == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "inputStream不能为空！");
        }
        return input2OutputStream(inputStream).toByteArray();
    }

    /**
     * 字节转输入流
     *
     * @param bytes 字节
     * @return 返回输入流
     */
    public static InputStream bytes2InputStream(@NonNull final byte[] bytes) {
        if (bytes == null || bytes.length <= 0) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "bytes不能为空！");
        }
        return new ByteArrayInputStream(bytes);
    }

    /**
     * 输出流转字节
     *
     * @param outputStream 输出流
     * @return 返回字节
     */
    public static byte[] outputStream2Bytes(@NonNull final OutputStream outputStream) {
        if (outputStream == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "outputStream不能为空！");
        }
        return ((ByteArrayOutputStream) outputStream).toByteArray();
    }

    /**
     * 字节转输出流
     *
     * @param bytes 字节
     * @return 返回输出流
     */
    public static OutputStream bytes2OutputStream(@NonNull final byte[] bytes) throws IOException {
        if (bytes == null || bytes.length <= 0) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "bytes不能为空！");
        }
        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream();
            os.write(bytes);
            return os;
        } finally {
            if (os != null) {
                os.close();
            }
        }
    }

    /**
     * 输入流转字符串
     *
     * @param inputStream 输入流
     * @param charsetName 字符集名字（GBK、UTF-8）
     * @return string
     */

    public static String inputStream2String(@NonNull final InputStream inputStream, @NonNull final String charsetName) throws IOException {
        if (inputStream == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "inputStream不能为空！");
        }
        if (StringUtils.isEmpty(charsetName)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "charsetName不能为空！");
        }
        ByteArrayOutputStream baos = input2OutputStream(inputStream);
        return baos.toString(charsetName);
    }

    /**
     * 字符串转输入流
     *
     * @param string      字符串
     * @param charsetName 字符集名字（GBK、UTF-8）
     * @return 返回输入流
     */
    public static InputStream string2InputStream(@NonNull final String string, @NonNull final String charsetName) throws UnsupportedEncodingException {
        if (StringUtils.isEmpty(string)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "string不能为空！");
        }
        if (StringUtils.isEmpty(charsetName)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "charsetName不能为空！");
        }
        return new ByteArrayInputStream(string.getBytes(charsetName));
    }

    /**
     * 输出流转字符串
     *
     * @param outputStream 输出流
     * @param charsetName  字符集名字（GBK、UTF-8）
     * @return 返回字符串
     */
    public static String outputStream2String(@NonNull final OutputStream outputStream, @NonNull final String charsetName) throws UnsupportedEncodingException {
        if (outputStream == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "outputStream不能为空！");
        }
        if (StringUtils.isEmpty(charsetName)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "charsetName不能为空！");
        }
        return new String(outputStream2Bytes(outputStream), charsetName);
    }

    /**
     * 字符串转输出流
     *
     * @param string      字符串
     * @param charsetName 字符集名字（GBK、UTF-8）
     * @return 返回输出流
     */
    public static OutputStream string2OutputStream(@NonNull final String string, @NonNull final String charsetName) throws IOException {
        if (StringUtils.isEmpty(string)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "string不能为空！");
        }
        if (StringUtils.isEmpty(charsetName)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "charsetName不能为空！");
        }
        return bytes2OutputStream(string.getBytes(charsetName));
    }

    /**
     * 位图转字节
     *
     * @param bitmap  位图
     * @param format  位图格式
     * @param quality 提示压缩机，0-100。0表示压缩小尺寸，100意味着压缩最大质量。一些
     *                格式，如无损PNG，将忽略质量设定
     * @return 返回字节
     */
    public static byte[] bitmap2Bytes(@NonNull final Bitmap bitmap, @NonNull final Bitmap.CompressFormat format, int quality) {
        if (bitmap == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "bitmap不能为空！");
        }
        if (format == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "format不能为空！");
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(format, quality, baos);
        return baos.toByteArray();
    }

    /**
     * 字节转位图
     *
     * @param bytes 字节
     * @return 返回位图
     */
    public static Bitmap bytes2Bitmap(@NonNull final byte[] bytes) {
        if (bytes == null || bytes.length <= 0) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "bytes不能为空！");
        }
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * Drawable转位图
     *
     * @param drawable Drawable
     * @return 返回位图
     */
    public static Bitmap drawable2Bitmap(@NonNull final Drawable drawable) {
        if (drawable == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "drawable不能为空！");
        }
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }
        Bitmap bitmap;
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1,
                    drawable.getOpacity() != PixelFormat.OPAQUE
                            ? Bitmap.Config.ARGB_8888
                            : Bitmap.Config.RGB_565);
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight(),
                    drawable.getOpacity() != PixelFormat.OPAQUE
                            ? Bitmap.Config.ARGB_8888
                            : Bitmap.Config.RGB_565);
        }
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 位图转drawable.
     *
     * @param context 上下文
     * @param bitmap  位图
     * @return 返回Drawable
     */
    public static Drawable bitmap2Drawable(@NonNull Context context, @NonNull final Bitmap bitmap) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        if (bitmap == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "bitmap不能为空！");
        }
        return new BitmapDrawable(context.getResources(), bitmap);
    }

    /**
     * Drawable转字节
     *
     * @param drawable Drawable
     * @param format   位图格式
     * @param quality  提示压缩机，0-100。0表示压缩小尺寸，100意味着压缩最大质量。一些
     *                 格式，如无损PNG，将忽略质量设定
     * @return 返回字节
     */
    public static byte[] drawable2Bytes(@NonNull final Drawable drawable, @NonNull final Bitmap.CompressFormat format, int quality) {
        if (drawable == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "drawable不能为空！");
        }
        if (format == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "format不能为空！");
        }
        return bitmap2Bytes(drawable2Bitmap(drawable), format, quality);
    }

    /**
     * 字节转Drawable
     *
     * @param context 上下文
     * @param bytes   字节
     * @return 返回Drawable
     */
    public static Drawable bytes2Drawable(@NonNull Context context, @NonNull final byte[] bytes) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        if (bytes == null || bytes.length == 0) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "bitmap不能为空！");
        }
        return bitmap2Drawable(context, bytes2Bitmap(bytes));
    }

    /**
     * View转位图
     *
     * @param view View
     * @return 返回位图
     */
    public static Bitmap view2Bitmap(@NonNull final View view) {
        if (view == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "view不能为空！");
        }
        Bitmap ret = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(ret);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(canvas);
        } else {
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return ret;
    }

    /**
     * dp转px.
     *
     * @param dpValue dp
     * @return 返回px
     */
    public static int dp2px(final float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转dp.
     *
     * @param pxValue px
     * @return 返回dp
     */
    public static int px2dp(final float pxValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp转px.
     *
     * @param spValue sp
     * @return 返回px
     */
    public static int sp2px(final float spValue) {
        final float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * px转sp.
     *
     * @param pxValue px
     * @return 返回sp
     */
    public static int px2sp(final float pxValue) {
        final float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }
}