package TestAlg;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author wincher
 * @date 2019-11-08
 * <p> TestAlg <p>
 */
public class LC88 {
	
	public static void main(String[] args) {
		merge(new int[]{1,2,9,0,0,0}, 3,new int[]{2,5,6}, 3);
	}
	
	public static void merge(int[] nums1, int m, int[] nums2, int n) {
		int[] nums1_copy = new int[m];
		System.arraycopy(nums1, 0,nums1_copy,0,m);
		
		int p = 0, p1 = 0, p2 = 0;
		while ( p1 < m && p2 < n) {
			nums1[p++] = nums1_copy[p1] > nums2[p2]?nums2[p2++]:nums1_copy[p1++];
		}
		System.out.println(m == p1);
		System.out.println(n == p2);
		if (p1 < m) {
			System.arraycopy(nums1_copy, p1, nums1, p, m-p1);
		}
		if (p2 < m) {
			System.arraycopy(nums2, p1, nums1, p, n-p2);
		}
		System.out.println(p);
		
		System.arraycopy(nums2, 0, nums1, m, n);
		Arrays.sort(nums1);
		echo(nums1);
		int index = 0;
		for (int i = 0; i < n; i++) {
			System.out.println("i: " + i);
			for (int j = index; j <= m + i; j++) {
				if (j == m + i) {
					System.out.println("current j:" + j);
					echo(nums1);
					echo(nums2);
				} else if (nums1[j] > nums2[i]) {
					for (int k = m + i -1; k >= j; k--) {
						System.out.println("k: " + k);
						nums1[k+1] = nums1[k];
					}
					echo(nums1);
					echo(nums2);
				} else {
					continue;
				}
				nums1[j] = nums2[i];
				echo(nums1);
				echo(nums2);
				index = j + 1;
				break;
			}
		}
		
		echo(nums1);
		echo(nums2);
	}
	
	private static void echo(int[] nums) {
		Arrays.stream(nums).boxed().collect(Collectors.toList()).stream().forEach(System.out::print);
		System.out.println();
	}
}
