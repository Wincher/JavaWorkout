package NIO.File;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;

/**
 * @author wincher
 * @date   17/09/2017.
 *
 * 代码出自：深入分析Java Web技术内幕 2.4.4 NIO的数据访问方式
 */
public class FileChannel {
	public static void main(String[] args) {
		String currentDirectory = System.getProperty("user.dir");
		System.out.println("当前工作目录: " + currentDirectory);
		String filename = currentDirectory +"/AboutIO/src/main/java/NIO/File/test.db";
		//buffer大小,正常会大一些eg.1024,这里为了展示MappedByteBuffer的读取设置的小一些
		int BUFFER_SIZE = 4;
		long fileLength = new File(filename).length();
		int bufferCount = (int) (fileLength/BUFFER_SIZE) + 1;
		MappedByteBuffer[] buffers = new MappedByteBuffer[bufferCount];
		long remaining = fileLength;
		for (int i = 0; i < bufferCount; i++) {
			try (RandomAccessFile file = new RandomAccessFile(filename, "r")) {
				buffers[i] = file.getChannel().map(java.nio.channels.FileChannel.MapMode.READ_ONLY,
						i * BUFFER_SIZE,
						(int)Math.min(remaining, BUFFER_SIZE));
			} catch (IOException e) {
				e.printStackTrace();
			}
			remaining -= BUFFER_SIZE;
		}

		for (int i = 0; i < buffers.length; i++) {
			MappedByteBuffer buffer = buffers[i];
			System.out.println("Reading from MappedByteBuffer " + (i + 1));
			// 读取 buffer 中的所有数据
			while (buffer.hasRemaining()) {
				System.out.print((char) buffer.get()); // 假设数据是文本
			}
		}
		
	}
}
