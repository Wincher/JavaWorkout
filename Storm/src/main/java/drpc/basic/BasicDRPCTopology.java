package drpc.basic;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.LocalDRPC;
import backtype.storm.StormSubmitter;
import backtype.storm.drpc.LinearDRPCTopologyBuilder;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

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
	
	public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException {
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
