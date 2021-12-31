package mock.demo;

import java.time.LocalDateTime;

/**
 * @author wincher
 * <p> mock <p>
 */
public class Service {

	public String getDate() {
		return LocalDateTime.now().toString();
	}
}
