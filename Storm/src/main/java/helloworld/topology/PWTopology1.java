package helloworld.topology;

import helloworld.bolt.PrintBolt;
import helloworld.bolt.WriteBolt;
import helloworld.spout.PWSpout;
import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.topology.TopologyBuilder;

/**
 * Created by wincher on 27/10/2017.
 */
public class PWTopology1 {
	
	public static void main(String[] args) throws InterruptedException, AlreadyAliveException, InvalidTopologyException, AuthorizationException {
		
		Config cfg = new Config();
		cfg.setNumWorkers(2);
		cfg.setDebug(true);
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("helloworld/spout", new PWSpout());
		builder.setBolt("print-helloworld.bolt", new PrintBolt()).shuffleGrouping("helloworld/spout");
		builder.setBolt("write-helloworld.bolt", new WriteBolt()).shuffleGrouping("print-helloworld.bolt");
		
		
		//1 local mode
//		LocalCluster cluster = new LocalCluster();
//		cluster.submitTopology("top1", cfg, builder.createTopology());
//		Thread.sleep(10000);
//		cluster.killTopology("top1");
//		cluster.shutdown();
		
		
		//2 cluster mode
		StormSubmitter.submitTopology("top1", cfg, builder.createTopology());
	}
}
