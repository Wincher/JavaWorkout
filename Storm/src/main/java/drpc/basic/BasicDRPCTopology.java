package drpc.basic;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.LocalDRPC;
import org.apache.storm.StormSubmitter;
import org.apache.storm.drpc.LinearDRPCTopologyBuilder;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

/**
 * Created by wincher on 03/11/2017.
 */
public class BasicDRPCTopology {
	
	public static class ExclaimBolt extends BaseBasicBolt {
		
		@Override
		public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
			String input = tuple.getString(1);
			
			basicOutputCollector.emit(new Values(tuple.getValue(0), input + "!"));
		}
		
		@Override
		public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
			outputFieldsDeclarer.declare(new Fields("id" + "result"));
		}
	}
	
	public static void main(String[] args) throws Exception {
		//创建drpc实例
		LinearDRPCTopologyBuilder builder = new LinearDRPCTopologyBuilder("exclamation");
		
		builder.addBolt(new ExclaimBolt(), 3);
		Config conf = new Config();
		if ( null == args || args.length == 0) {
			LocalDRPC drpc = new LocalDRPC();
			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology("drpc-demo", conf, builder.createLocalTopology(drpc));
			for (String word : new String[]{"hello", "goodbye", "please"}) {
				System.out.println("Result for \"" + "\":" + drpc.execute("exclamation", word));
			}
			
			cluster.shutdown();
			drpc.shutdown();
		} else {
			conf.setNumWorkers(3);
			StormSubmitter.submitTopology(args[0], conf, builder.createRemoteTopology());
		}
	}
}
