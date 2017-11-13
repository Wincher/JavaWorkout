package NIO.File;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;

/**
 * Created by wincher on 17/09/2017.
 *
 * 代码出自：深入分析Java Web技术内幕 2.4.4 NIO的数据访问方式
 */
public class FileChannel {
	public static void main(String[] args) {
		int BUFFER_SIZE = 1024;
		String filename = "test.db";
		long fileLength = new File(filename).length();
		int bufferCount = 1 + (int) (fileLength/BUFFER_SIZE);
		MappedByteBuffer[] buffers = new MappedByteBuffer[bufferCount];
		long remaining = fileLength;
		for (int i = 0; i < bufferCount; i++) {
			try (RandomAccessFile file = new RandomAccessFile(filename, "r")) {
				buffers[i] = file.getChannel().map(java.nio.channels.FileChannel.MapMode.READ_ONLY,
						i * BUFFER_SIZE,
						(int)Math.min(remaining, BUFFER_SIZE));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			remaining -= BUFFER_SIZE;
		}
		
	}
}
