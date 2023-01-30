package club.xiaojiawei.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

/**
 * @author 肖嘉威
 * @date 2023/1/24 22:28
 */
public class ZlibUtil {
    
    /**
     * 压缩
     * @param data 待压缩数据
     * @return byte[] 压缩后的数据
     */
    public static byte[] compress(byte[] data, int level) {
        byte[] output;
        Deflater compressor = new Deflater(level);
        compressor.reset();
        compressor.setInput(data);
        compressor.finish();
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length)){
            byte[] buf = new byte[1024];
            while (!compressor.finished()) {
                int i = compressor.deflate(buf);
                bos.write(buf, 0, i);
            }
            output = bos.toByteArray();
        } catch (Exception e) {
            output = data;
            e.printStackTrace();
        }
        compressor.end();
        return output;
    }

    /**
     * 压缩
     * @param data 待压缩数据
     * @return byte[] 压缩后的数据
     */
    public static byte[] compress(byte[] data) {
        return compress(data, -1);
    }

    /**
     * 压缩
     * @param data 待压缩数据
     * @param os 输出流
     */
    public static void compress(byte[] data, OutputStream os) {
        try (DeflaterOutputStream dos = new DeflaterOutputStream(os)){
            dos.write(data, 0, data.length);
            dos.finish();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解压缩
     * @param data  待压缩的数据
     * @return byte[] 解压缩后的数据
     */
    public static byte[] decompress(byte[] data) {
        byte[] output;
        Inflater decompressor = new Inflater();
        decompressor.reset();
        decompressor.setInput(data);
        try (ByteArrayOutputStream o = new ByteArrayOutputStream(data.length)){
            byte[] buf = new byte[1024];
            while (!decompressor.finished()) {
                int i = decompressor.inflate(buf);
                o.write(buf, 0, i);
            }
            output = o.toByteArray();
        } catch (Exception e) {
            output = data;
            e.printStackTrace();
        }
        decompressor.end();
        return output;
    }

    /**
     * 解压缩
     * @param is 输入流
     * @return byte[] 解压缩后的数据
     */
    public static byte[] decompress(InputStream is) {
        byte[] output;
        InflaterInputStream iis = new InflaterInputStream(is);
        try (ByteArrayOutputStream o = new ByteArrayOutputStream(1024)){
            int i = 1024;
            byte[] buf = new byte[i];
            while ((i = iis.read(buf, 0, i)) > 0) {
                o.write(buf, 0, i);
            }
            output = o.toByteArray();
        } catch (IOException e) {
            output = new byte[]{};
            e.printStackTrace();
        }
        return output;
    }

}
