package transacionDemo.topology;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import transacionDemo.bolt.WriteBolt;
import transacionDemo.bolt.SplitBolt;
import transacionDemo.spout.MessageSpout;
import util.Utils;

/**
 * @author wincher
 * @date   02/11/2017.
 */
public class MesssageTopology {
	
	public static void main(String[] args) throws Exception {
		//实例化对象
		MessageSpout spout = new MessageSpout();
		SplitBolt splitBolt = new SplitBolt();
		WriteBolt countBolt = new WriteBolt();
		
		//构建拓扑
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("spout", spout);
		
		builder.setBolt("split-bolt", splitBolt, 5).shuffleGrouping("spout");
		builder.setBolt("write-bolt", countBolt, 5).shuffleGrouping("split-bolt");
		
		//local config
		Config config = new Config();
		config.setDebug(false);
		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("message", config, builder.createTopology());
		
		Utils.waitForSeconds(10);
		cluster.killTopology("message");
		cluster.shutdown();
		
		
	}
}
