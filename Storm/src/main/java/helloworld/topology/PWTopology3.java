package helloworld.topology;

import backtype.storm.Config;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import helloworld.bolt.PrintBolt;
import helloworld.bolt.WriteBolt;
import helloworld.spout.PWSpout;

/**
 * Created by wincher on 27/10/2017.
 */
public class PWTopology3 {
	public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException {
		Config cfg = new Config();
		cfg.setNumWorkers(2);
		cfg.setDebug(false);
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("helloworld/spout",new PWSpout(), 4);
		builder.setBolt("print-helloworld.bolt", new PrintBolt(), 4).shuffleGrouping("helloworld/spout").setNumTasks(4);
		//设置字段分组
		builder.setBolt("write-helloworld.bolt", new WriteBolt(), 8).fieldsGrouping("print-helloworld.bolt", new Fields("write"));
		//设置广播分组
//		builder.setBolt("write-helloworld.bolt", new WriteBolt(), 4).allGrouping("print-helloworld.bolt");
		//设置全局分组
//		builder.setBolt("write-helloworld.bolt", new WriteBolt(), 4).globalGrouping("print-helloworld.bolt");
		
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
