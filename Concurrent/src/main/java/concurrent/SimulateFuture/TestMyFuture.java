package concurrent.SimulateFuture;

/**
 * @author wincher
 * @date 31/08/2017
 *
 * Future模式的例子，先返回真实数据的包装类，等真实数据费时操作返回后包装类才可以得到真实数据的值
 */
public class TestMyFuture {
	
	public static void main(String[] args) {
		FutureClient fc = new FutureClient();
		Data data = fc.request("select * from *** limit 10");
		System.out.println("do some other business operation..");
		String result = data.getRequest();
		System.out.println(result);
	}
}
