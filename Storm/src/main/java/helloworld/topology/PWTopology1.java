package helloworld.topology;

import backtype.storm.Config;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;
import helloworld.bolt.PrintBolt;
import helloworld.bolt.WriteBolt;
import helloworld.spout.PWSpout;

/**
 * Created by wincher on 27/10/2017.
 */
public class PWTopology1 {
	
	public static void main(String[] args) throws InterruptedException, AlreadyAliveException, InvalidTopologyException {
		
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
