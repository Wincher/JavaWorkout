package multithread.multithread14;

/**
 * @author wincher
 * @date 31/08/2017
 */
public class RealData implements Data{
	
	private String result;
	
	public RealData(String queryStr) {
		System.out.println("depend on" + queryStr + "search, it may cost some time...");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("search over, get the result...");
		result = "search over";
	}
	
	@Override
	public String getRequest() {
		return result;
	}
}
