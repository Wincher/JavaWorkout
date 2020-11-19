package test;

import java.nio.IntBuffer;

public class NioBuffer {
    public static void main(String[] args) {
        IntBuffer intBuffer = IntBuffer.allocate(2);
        intBuffer.put(1);
        intBuffer.put(2);
        System.err.println("position: " + intBuffer.position());

        intBuffer.rewind();
        System.err.println("position: " + intBuffer.position());
        intBuffer.put(2);
        intBuffer.put(3);
        System.err.println("position: " + intBuffer.position());


        intBuffer.flip();
        System.err.println("position: " + intBuffer.position());
        System.out.println(intBuffer.get());
        System.err.println("position: " + intBuffer.position());
        System.out.println(intBuffer.get());
        System.err.println("position: " + intBuffer.position());

        intBuffer.rewind();
        System.err.println("position: " + intBuffer.position());
    }
}
