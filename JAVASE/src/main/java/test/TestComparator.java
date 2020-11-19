package test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wincher
 * @date 2019-09-19
 * <p> test <p>
 */
public class TestComparator {
  
  private static Map<Integer, Integer> map = new ConcurrentHashMap();
  public static void main(String[] args) {
    Node[] nodes = new Node[3];
    nodes[0] = new Node(1 ,3);
    nodes[1] = new Node(2 ,5);
    nodes[2] = new Node(3 ,1);
//    nodes[3] = new Node(4 ,3);
//    nodes[4] = new Node(3 ,3);
//    nodes[5] = new Node(6 ,2);
//    nodes[6] = new Node(4 ,1);
//    nodes[7] = new Node(8 ,6);
//    nodes[8] = new Node(9 ,2);
//    nodes[9] = new Node(10 ,3);
    Arrays.sort(nodes, new NodeComparator());
    for ( Node n: nodes) {
      System.out.println(n.key + ": " + n.value);
    }
  }
  
  static class Node {
    public int key;
    public int value;
  
    public Node(int key, int value) {
      this.key = key;
      this.value = value;
    }
  }
  
  static class NodeComparator implements Comparator<Node> {
    @Override
    public int compare(Node o1, Node o2) {
      System.out.println(o1.key + ":" + o1.value + " " + o2.key + ":" + o2.value);
      if (o1.value > o2.value) {
        return 1;
      }
      if (o1.value < o2.value){
        return -1;
      }
      if(map != null && map.containsKey(o1.key) && map.containsKey(o2.key)) {
        int order1 = map.get(o1.key);
        int order2 = map.get(o2.key);
    
        if (order1<order2) return -1;
        else if (order1>order2) return 1;
        else return 0;
      } else {
        return 0;
      }
    }
  }
}
