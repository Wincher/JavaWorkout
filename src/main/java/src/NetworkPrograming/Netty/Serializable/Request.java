package NetworkPrograming.Netty.Serializable;


import java.io.Serializable;

/**
 * Created by wincher on 20/09/2017.
 */
public class Request implements Serializable{
	
	private static final long SerialVersionUID = 1L;
	
	private String id;
	private String message;
	private byte[] attachment;
	
	public Request(String id, String message) {
		this.id = id;
		this.message = message;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public byte[] getAttachment() {
		return attachment;
	}
	
	public void setAttachment(byte[] attachment) {
		this.attachment = attachment;
	}
}
