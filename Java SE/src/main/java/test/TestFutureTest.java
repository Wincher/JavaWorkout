package test;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author wincher
 * @date 2020/8/18
 * <p> test <p>
 */
public class TestFutureTest {
	
	@Test
	public void hello() throws InterruptedException, ExecutionException {
		Future<String> string = new FutureTask( () -> {
			TimeUnit.SECONDS.sleep(3);
			return "a";
		});
		System.out.println(string.isDone());
		//todo: check why this wait so long
		TimeUnit.SECONDS.sleep(5);
		System.out.println(string.get());
		
	}
}
