package kafka.example;


import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import kafka.serializer.StringEncoder;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Created by wincher on 08/11/2017.
 */
public class KafkaProducer {
	
	public static final String topic = "test";
	public static void main(String[] args) throws InterruptedException {
		Properties properties = new Properties();
		properties.put("zookeeper.connect", "192.168.0.111:2181,192.168.0.112:2181,192.168.0.113:2181");
		properties.put("serializer.class", StringEncoder.class.getName());
		properties.put("metadata.broker.list", "192.168.0.111:9092"); //declare kafka broker
		properties.put("request.required.acks", "1");
		
		Producer producer = new Producer<Integer, String>(new ProducerConfig(properties));
		for (int i = 0; i < 10; i++) {
			producer.send(new KeyedMessage<Integer, String>(topic, "hello kafka " + i));
			System.out.println("send message " + "hello kafka " + i);
			TimeUnit.SECONDS.sleep(1);
		}
		producer.close();
		
	}
}
