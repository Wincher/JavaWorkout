package drpc.basic;


/**
 * 调用前需要将BasicDRPCTopology作为DRPC服务发不到storm上，storm陪你文件要配置drpc
 * Created by wincher on 03/11/2017.
 * todo:Error:(15, 58) java: cannot access org.apache.thrift7.TException
    bad source file: /Users/wincher/Documents/maven-repository/org/apache/storm/storm-core/0.9.1-incubating/storm-core-0.9.1-incubating.jar(/org/apache/thrift7/TException.java)
    file does not contain class org.apache.thrift7.TException
    Please remove or make sure it appears in the correct subdirectory of the sourcepath.
	编译产生如下错误，找不到解决方法，先不浪费时间
 */
public class DrpcExclam {
	
//	public static void main(String[] args) throws TException, DRPCExecutionException {
//		DRPCClient client = new DRPCClient("192.168.0.111", 3772);
//		for (String word : new String[]{"a", "b", "c"}) {
//			System.out.println(client.execute("exclamation", word));
//		}
//	}
}
