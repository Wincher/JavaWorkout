package TestAlg;

/**
 * @author wincher
 * @date 2019-11-06
 * <p> TestAlg <p>
 */
public class LC21 {
	public static void main(String[] args) {
		ListNode a1 = new ListNode(1);
		ListNode a2 = new ListNode(2);
		ListNode a3 = new ListNode(4);
		ListNode a4 = new ListNode(1);
		ListNode a5 = new ListNode(3);
		ListNode a6 = new ListNode(4);
		
		a1.next = a2;
		a2.next = a3;
		a4.next = a5;
		a5.next = a6;
		
		
		ListNode current = mergeTwoLists(a1, a4);
		do {
			System.out.println(current.val);
			current = current.next;
		} while (null != current.next);
		System.out.println(current.val);
		
	}
	
	public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
		if (null == l1) return l2;
		if (null == l2) return l1;
		ListNode origin = l1.val > l2.val?l2:l1;
		while (null != l1 && null != l2) {
			ListNode l1Next = l1.next;
			ListNode l2Next = l2.next;
			if (l1.val <= l2.val) {
				l1.next = l2;
				l2.next = l1Next;
			} else {
				l2.next = l1;
				l1.next = l2Next;
			}
			l1 = l1Next;
			l2 = l2Next;
		}
		return origin;
	}
	
	static class ListNode {
		int val;
		ListNode next;
		ListNode(int x) { val = x; }
	}
}



