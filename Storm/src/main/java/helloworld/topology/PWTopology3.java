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
import org.apache.storm.tuple.Fields;

/**
 * Created by wincher on 27/10/2017.
 */
public class PWTopology3 {
	public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException, InvalidTopologyException, AuthorizationException {
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
