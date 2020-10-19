package test;

import java.util.Stack;

/**
 * @author wincher
 * @date 2019-11-18
 * <p> test <p>
 */
public class TestStack {
	public static void main(String[] args) {
		Stack<Integer> st = new Stack<>();
		st.push(1);
		st.push(2);
		System.out.println(st.firstElement());
		if (!st.empty())  System.out.println(st.pop());
		if (!st.empty())  System.out.println(st.pop());
	}
	
}
