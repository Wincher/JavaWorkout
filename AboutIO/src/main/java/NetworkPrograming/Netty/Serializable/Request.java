package NetworkPrograming.Netty.Serializable;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author wincher
 * @date   20/09/2017.
 */
@Setter
@Getter
public class Request implements Serializable{

	//切记serial s是小写的
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String message;
	private byte[] attachment;
	
	public Request(String id, String message) {
		this.id = id;
		this.message = message;
	}

}
