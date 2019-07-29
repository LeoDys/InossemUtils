package com.inossem_library.other.compress;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author 詹建宇
 * @time on 2019/7/25
 * @email jianyu.zhan@inossem.com
 * @describe GZip压缩工具类(建议在网络请求中使用)
 */
public class GZIPUtils {

    /**
     * 压缩
     *
     * @param data 需要压缩的数据
     * @return 压缩后的数据
     * @throws IOException IO异常
     */
    public static byte[] compress(byte[] data) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip;

        try {
            //ByteArray流转GZip流
            gzip = new GZIPOutputStream(out);
            //写入
            gzip.write(data);
            //关闭
            gzip.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }

    /**
     * 解压
     *
     * @param data 需要解压的数据
     * @return 解压后的数据
     * @throws IOException IO异常
     */
    public static byte[] uncompress(byte[] data) throws IOException {
        //结果流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        //需要处理的流
        ByteArrayInputStream in = new ByteArrayInputStream(data);

        try {
            //解压
            GZIPInputStream ungzip = new GZIPInputStream(in);
            byte[] buffer = new byte[2048];
            int n;
            while ((n = ungzip.read(buffer)) >= 0) {
                //写入到结果流中
                out.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }

}
