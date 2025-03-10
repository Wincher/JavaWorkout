package NetworkPrograming.NIO.Buffer;

import java.nio.IntBuffer;

/**
 * @author wincher
 * @date   15/09/2017.
 */
public class TestBuffer {
	public static void main(String[] args) {
		//1基本操作
		/*
		//创建指定长度的缓冲区
		IntBuffer buf = IntBuffer.allocate(10);
		buf.put(13); //position: 0 -> 1
		buf.put(21); //position: 1 -> 2
		buf.put(35); //position: 2 -> 3
		
		//把为止复位为0，也就是position为止： 3 -> 0
		buf.flip();
		System.out.println("after flip: " + buf);
		System.out.println("容量为: " + buf.capacity()); //容量一旦初始化后不允许改变（wrap方法包裹数组除外）
		System.out.println("限制为: " + buf.limit()); //由于只装载了三个元素，所以可读取活着操作的元素为3，则limit = 3
		
		System.out.println("get element of index 1: " + buf.get(1));
		System.out.println("get(index) method,position位置不改变；" + buf);
		buf.put(1,4);
		System.out.println("put(index, change) method, position位置不变：" + buf);
		
		for (int i = 0; i < buf.limit(); i++) {
			//调用get方法会使其缓冲区位置(position)向后递增一位
			System.out.println(buf.get());
		}
		System.out.println("After iterator buf:" + buf);
		*/
		
		/*
		//2. wrap 方法使用
		// wrap 方法会包裹一个数组：一般这种用法不会先初始化缓存对象的长度，
		//因为没有意义，最后还会被wrap锁包裹的数组覆盖掉
		int[] arr = new int[]{1,2,5};
		IntBuffer buf1 = IntBuffer.wrap(arr);
		System.out.println(buf1);
		
		IntBuffer buf2 = IntBuffer.wrap(arr, 0, 2);
		//这样使用表示容量为数组arr的长度，但是可操作的元素只有实际进入缓存区的元素长度
		System.out.println(buf2);
		*/
		
		//3 other method
		IntBuffer buf1 = IntBuffer.allocate(10);
		int[] arr = new int[]{1,2,5};
		buf1.put(arr);
		System.out.println(buf1);
		
		//一种复制方法
		IntBuffer buf3 = buf1.duplicate();
		System.out.println(buf3);
		
		//设置buf1的位置属性
		//buf1.position(0);
		buf1.flip();
		System.out.println(buf1);
		
		System.out.println("readable data: " + buf1.remaining());
		
		int[] arr2 = new int[buf1.remaining()];
		//将缓冲区数据放入arr2数组中去
		buf1.get(arr2);
		for (int i: arr2) {
			System.out.println(Integer.toString(i) + ",");
		}
		
	}
}
