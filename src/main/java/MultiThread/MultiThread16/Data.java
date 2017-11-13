package MultiThread.MultiThread16;

/**
 * Created by wincher on 31/08/2017.
 */
public class Data {
	
	private String data;
	private String id;
	
	public Data(String id, String data) {
		this.id = id;
		this.data = data;
	}
	
	public String getData() {
		return data;
	}
	
	public void setData(String data) {
		this.data = data;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
}
