package kafka.storm;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.kafka.KafkaSpout;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.kafka.StringScheme;
import org.apache.storm.kafka.ZkHosts;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.topology.TopologyBuilder;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author wincher
 * @date   08/11/2017.
 */
public class KafkaTopology {
	
	public static void main(String[] args) throws Exception {
		
		// zookeeper hosts for the Kafka cluster
		ZkHosts zkHosts = new ZkHosts("192.168.0.111:2181");
		//Create the KafkaSpout configuration
		//Second argument -> topic name
		//Third argument  -> zk root for Kafka
		//Forth argument  -> consumer group id
		
		SpoutConfig kafkaConfig = new SpoutConfig(zkHosts, "words_topic", "", "id111");
		//Specify kafka message type
		kafkaConfig.scheme = new SchemeAsMultiScheme(new StringScheme());
		//We want to consume all the first message in the topic every time
		//方便debug，在生产环境应该设置为false not sure used to be `forceFromStart`
		kafkaConfig.useStartOffsetTimeIfOffsetOutOfRange = true;
		//create the topology
		TopologyBuilder builder = new TopologyBuilder();
		//set kafka spout class
		builder.setSpout("KafkaSpout", new KafkaSpout(kafkaConfig), 1);
		//configure bolts ##after update not work
//		builder.setBolt("SentenceBolt", new SentenceBolt(), 1).globalGrouping("KafkaSpout");
//		builder.setBolt("PrintBolt", new PrintBolt(), 1).globalGrouping("SentenceBolt");
		
		LocalCluster cluster = new LocalCluster();
		Config conf = new Config();
		
		cluster.submitTopology("KafkaTopology", conf, builder.createTopology());
		
		try {
			System.out.println("Waiting to consume from kafka");
			TimeUnit.SECONDS.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		cluster.killTopology("KafkaTopology");
		cluster.shutdown();
	}
}
