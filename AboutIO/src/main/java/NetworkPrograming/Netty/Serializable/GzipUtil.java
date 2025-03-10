package NetworkPrograming.Netty.Serializable;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author wincher
 * @date   21/09/2017.
 */
public class GzipUtil {
	
	public static byte[] gzip(byte[] data) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		GZIPOutputStream gzipOutputStream = new GZIPOutputStream(bos);
		gzipOutputStream.write(data);
		gzipOutputStream.finish();
		gzipOutputStream.close();
		byte[] ret = bos.toByteArray();
		bos.close();
		return ret;
	}
	
	public static byte[] ungzip(byte[] data) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		GZIPInputStream gzip = new GZIPInputStream(bis);
		byte[] buf = new byte[1024];
		int num = -1;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while ( (num = gzip.read(buf, 0 , buf.length)) != -1) {
			bos.write(buf, 0, num);
		}
		
		gzip.close();
		bis.close();
		byte[] ret = bos.toByteArray();
		bos.flush();
		bos.close();
		return ret;
	}
	
	public static void main(String[] args) throws IOException {
		//读取文件
		String readPath = System.getenv("suer.dir") +
				File.separatorChar + "sources" +
				File.separatorChar + "006.jpg";
		System.out.println(readPath);
		File file = new File(readPath);
		FileInputStream fis = new FileInputStream(file);
		byte[] data = new byte[fis.available()];
		fis.read(data);
		fis.close();
		System.out.println("文件原始大小: " + data.length);
		//test gzip
		byte[] ret1 = GzipUtil.gzip(data);
		System.out.println("压缩后大小: " + ret1.length);
		byte[] ret2 = GzipUtil.ungzip(ret1);
		System.out.println("解压后大小: " + ret2.length);
		
		String writePath = System.getProperty("user.dir") +
				File.separatorChar + "receive" +
				File.separatorChar + "006.jpg";
		FileOutputStream fos = new FileOutputStream(writePath);
		fos.write(ret2);
		fos.close();
		
	}
	
}