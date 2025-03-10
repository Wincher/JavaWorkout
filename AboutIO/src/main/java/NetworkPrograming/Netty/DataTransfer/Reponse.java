package NetworkPrograming.Netty.DataTransfer;

import java.io.Serializable;

/**
 * @author wincher
 * @date   22/09/2017.
 */
public class Reponse implements Serializable {
	
	private static final long SerialVersionUID = 1L;
	
	private String id;
	private String name;
	private String responseMessage;
	
	public Reponse(String id, String name, String responseMessage) {
		this.id = id;
		this.name = name;
		this.responseMessage = responseMessage;
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
	
	public String getResponseMessage() {
		return responseMessage;
	}
	
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
}
