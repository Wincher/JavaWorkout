package kafka.redis;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;

import java.util.concurrent.TimeUnit;

/**
 * Created by wincher on 09/11/2017.
 */
public class Topology {
	
	public static void main(String[] args) throws Exception {
		TopologyBuilder builder = new TopologyBuilder();
		
		builder.setSpout("spout", new SampleSpout(), 2);
		builder.setBolt("bolt", new StormRedisBolt("192.168.0.111", 6379), 2).shuffleGrouping("spout");
		
		Config conf = new Config();
		conf.setDebug(true);
		
		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("StormRedisTopology", conf, builder.createTopology());
		try {
			TimeUnit.SECONDS.sleep(15);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		cluster.killTopology("StormRedisTopology");
		cluster.shutdown();
		
	}
}
