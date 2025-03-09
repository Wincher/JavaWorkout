package concurrent.SimulateFuture;

/**
 * @author wincher
 * @date 31/08/2017
 */
public class FutureClient {
	
	public Data request(final String queryStr) {
		// 1.需要一个代理对象（Data接口实现类） 先返回发送请求的客户端，告诉它请求已经接收到，可以做其他事情
		final FutureData futureData = new FutureData();
		// 2.创建一个的线程，去加载真实数据，传递给这个代理对象
		new Thread(() -> {
			//3.这个新的线程可以去慢慢地加载真实对象，然后传奇给代理对象
			RealData realData = new RealData(queryStr);
			futureData.setRealData(realData);
		}).start();
		System.out.println("request send successfully...\nwaiting for data...");
		return futureData;
	}
}
