package mock.diy;

import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author wincher
 * <p> mock.diy <p>
 */
public class Invocation {
	private final Object mock;
	private final Method method;
	private final Object[] argruments;
	private final MethodProxy methodProxy;
	
	public Invocation(Object mock, Method method, Object[] args, MethodProxy methodProxy) {
		this.mock = mock;
		this.method = method;
		this.argruments = copyArgs(args);
		this.methodProxy = methodProxy;
	}
	
	private Object[] copyArgs(Object[] args) {
		Object[] newArgs = new Object[args.length];
		System.arraycopy(args, 0, newArgs, 0, args.length);
		return newArgs;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Invocation that = (Invocation) o;
		return Objects.equals(method, that.method) &&
			Arrays.deepEquals(argruments, that.argruments) &&
			Objects.equals(methodProxy, that.methodProxy);
	}
	
	@Override
	public int hashCode() {
		return 1;
	}
}
