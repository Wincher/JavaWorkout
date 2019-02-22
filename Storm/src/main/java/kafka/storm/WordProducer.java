package kafka.storm;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Properties;

/**
 * Created by wincher on 08/11/2017.
 */
public class WordProducer {
	public static void main(String[] args) {
		Properties prop = new Properties();
		prop.put("metadata.broker.list", "192.168.0.111:9092");
		prop.put("serializer.class", "kafka.serializer.StringEncoder");
		prop.put("request.required.acks", "1");
		
		ProducerConfig config = new ProducerConfig(prop);
		Producer<String, String> producer = new Producer<String, String>(config);
		
		for (String word : PEPPARED_ARTICLE.split("\\s")) {
			KeyedMessage<String, String> data = new KeyedMessage<String, String>("words_topic", word);
			producer.send(data);
		}
		
		System.out.println("Produced data");
		producer.close();
	}
	
	private static final String PEPPARED_ARTICLE =
					"They were perfect for each other.\n" +
					"\tThey shared a favorite movie,favorite book,favorite meal and favorite color.\n" +
					"\tShe cracked up at the kind of corny jokes he loved to tell.\n" +
					"\tHe adored violin music; she had been playing since she was six years old.\n" +
					"\tThe two were compatible at every possible level.\n" +
					"\tOn May 23,2010,they met in line at a supermarket.She was out buying groceries," +
					"but let him cut ahead as he only had a few items and was in a hurry.\n" +
					"\tHe thanked her, paid, and left.They never spoke again.\n";
	
}
