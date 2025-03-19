package NetworkPrograming.Netty.Serializable;

import io.netty.handler.codec.marshalling.*;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

/**
 * @author wincher
 * @date   20/09/2017.
 */
public class MarshallingCodeFactory {
	/**
	 * 创建Jboss Marshalling解码器MarshallingDecoder
	 * @return MarshallingDecoder
	 */
	public static MarshallingDecoder buildMarshallingDecoder() {
		System.out.println("MarshallingCodeFactory.buildMarshallingDecoder() called!");
		final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
		//final MarshallerFactory marshallerFactory = Marshalling.getMarshallerFactory("serial", new SimpleClassLoader(Thread.currentThread().getContextClassLoader())); // 尝试显式指定 MarshallerFactory
		if (marshallerFactory == null) {
			System.err.println("Marshalling.getProvidedMarshallerFactory(\"serial\") returned null!");
		}
		final MarshallingConfiguration configuration = new MarshallingConfiguration();
		configuration.setVersion(5);
		UnmarshallerProvider provider = new DefaultUnmarshallerProvider(marshallerFactory, configuration);
		return new MarshallingDecoder(provider, 1024*1024*1);
	}

	/**
	 * 创建Jboss Marshalling编码器的MarshallingEncoder
	 * @return MarshallingEncoder
	 */
	public static MarshallingEncoder buildMarshallingEncoder() {
		System.out.println("MarshallingCodeFactory.buildMarshallingEncoder() called!");
		//这里搞了半天就marshallingCodeFactory就是为null,因为版本的问题jboss-marshalling和jboss-marshalling-serial版本的问题,更新到最新就是null,以后有需要再处理
		final MarshallerFactory marshallingCodeFactory = Marshalling.getProvidedMarshallerFactory("serial");
		if (marshallingCodeFactory == null) {
			System.err.println("Marshalling.getProvidedMarshallerFactory(\"serial\") returned null!");
		}
		final MarshallingConfiguration configuration = new MarshallingConfiguration();
		configuration.setVersion(5);
		MarshallerProvider provider = new DefaultMarshallerProvider(marshallingCodeFactory, configuration);
		return new MarshallingEncoder(provider);

	}
}
