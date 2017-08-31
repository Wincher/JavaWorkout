package MultiThread.MultiThread14;

/**
 * Created by wincher on 31/08/2017.
 *
 * Future模式的例子，先返回真实数据的包装类，等真实数据费时操作返回后包装类才可以得到真实数据的值
 */
public class Main {
	
	public static void main(String[] args) {
		FutureClient fc = new FutureClient();
		Data data = fc.request("Request params");
		System.out.println("request send successfully...");
		System.out.println("do some other things..");
		
		String result = data.getRequest();
		System.out.println(result);
	}
}
