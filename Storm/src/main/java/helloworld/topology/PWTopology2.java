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
public class PWTopology2 {
	public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException {
		Config cfg = new Config();
		cfg.setNumWorkers(2);
		cfg.setDebug(false);
		TopologyBuilder builder = new TopologyBuilder();
		//设置bolt的并行度和任务数(产生2个执行器和两个任务)
		builder.setSpout("helloworld/spout",new PWSpout(), 2);//.setNumberTasks(2);
		//设置bolt的并行度和任务书：（产生2个执行器和4个任务）
		builder.setBolt("print-helloworld.bolt", new PrintBolt(), 2).shuffleGrouping("helloworld/spout").setNumTasks(4);
		//设置bolt的并行度和任务书：（产生6个执行器和6个任务）
		builder.setBolt("write-helloworld.bolt", new WriteBolt(), 6).shuffleGrouping("print-helloworld.bolt");
		
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
