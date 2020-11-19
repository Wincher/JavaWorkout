package mock.diy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wincher
 * <p> mock.diy <p>
 */
public class Mocky {
	
	private static Map<Invocation, Object> results = new HashMap<>();
	private static Invocation lastInvocation;

	public static <T> T mocky(Class<T> clazz) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(clazz);
		enhancer.setCallback(new MockyInterceptor());
		return (T)enhancer.create();
	}
	
	private static class MockyInterceptor implements MethodInterceptor {
		
		@Override
		public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
			Invocation invocation = new Invocation(o, method, objects, methodProxy);
			lastInvocation = invocation;
			return results.get(invocation);
		}
	}
	
	public static <T> When<T> when(T o) {
		return new When<T>();
	}
	
	public static class When<T> {
		public void thenReturn(T resultObj) {
			results.put(lastInvocation, resultObj);
		}
	}
}
