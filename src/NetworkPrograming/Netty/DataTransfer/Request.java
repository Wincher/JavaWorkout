package NetworkPrograming.Netty.DataTransfer;

import java.io.Serializable;

/**
 * Created by wincher on 22/09/2017.
 */
public class Request implements Serializable {
	
	private static final long SerialVersionUID = 1L;
	
	private String id;
	private String name;
	private String requestMessage;
	
	public Request() {
	}
	
	public Request(String id, String name, String requestMessage) {
		this.id = id;
		this.name = name;
		this.requestMessage = requestMessage;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getRequestMessage() {
		return requestMessage;
	}
	
	public void setRequestMessage(String requestMessage) {
		this.requestMessage = requestMessage;
	}
}
