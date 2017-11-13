package dubbo.dubbox;

import com.alibaba.dubbo.common.serialize.support.SerializationOptimizer;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wincher on 25/10/2017.
 */

//如果需要dubbo使用序列化框架需要使用这个类来注册到dubbo系统中
public class SerializationOptmizerImpl implements SerializationOptimizer {
	@Override
	public Collection<Class> getSerializableClasses() {
		List<Class> classes = new LinkedList<Class>();
		classes.add(Sample.class);
		classes.add(User.class);
		return classes;
	}
}
