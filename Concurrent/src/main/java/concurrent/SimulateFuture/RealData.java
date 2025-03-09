package concurrent.SimulateFuture;

/**
 * @author wincher
 * @date 31/08/2017
 */
public class RealData implements Data{
	
	private String result;
	
	public RealData(String queryStr) {
		System.out.println("execute: " + queryStr + ", it may cost some time...");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("search over, get the result...");
		result = queryStr + ": result: *** ***";
	}
	
	@Override
	public String getRequest() {
		return result;
	}
}
