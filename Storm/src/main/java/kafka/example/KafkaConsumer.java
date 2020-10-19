package kafka.example;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.serializer.StringDecoder;
import kafka.serializer.StringEncoder;
import kafka.utils.VerifiableProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author wincher
 * @date   08/11/2017.
 */
public class KafkaConsumer {
	
	public static final String topic = "test";
	
	public static void main(String[] args) {
		Properties properties = new Properties();
		properties.put("zookeeper.connect", "192.168.0.111:2181,192.168.0.112:2181,192.168.0.113:2181");
		//group代表一个消费组
		properties.put("group.id", "group1");
		//zk连接超时
		properties.put("zookeeper.session.timeout.ms", "4000");
		properties.put("zookeeper.sync.time.ms", "200");
		properties.put("auto.commit.interval.ms", "1000");
		properties.put("auto.offset.reset", "smallest");
		//序列化类
		properties.put("serializer.class", StringEncoder.class.getName());//kafka.serializer.StringEncoder
		
		ConsumerConfig config = new ConsumerConfig(properties);
		ConsumerConnector consumer = Consumer.createJavaConsumerConnector(config);
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put(topic, 1);
		
		StringDecoder keyDecoder = new StringDecoder(new VerifiableProperties());
		StringDecoder valueDecoder = new StringDecoder(new VerifiableProperties());
		
		Map<String, List<KafkaStream<String, String>>> consumerMap =
				consumer.createMessageStreams(topicCountMap, keyDecoder, valueDecoder);
		
		KafkaStream<String, String> stream = consumerMap.get(topic).get(0);
		ConsumerIterator<String, String> it = stream.iterator();
		
		while (it.hasNext()) {
			System.out.println(it.next().message());
		}
		
	}
}
