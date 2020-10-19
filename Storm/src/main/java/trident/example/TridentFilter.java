package trident.example;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.thrift.TException;
import org.apache.storm.trident.Stream;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.operation.BaseFilter;
import org.apache.storm.trident.operation.BaseFunction;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.testing.FixedBatchSpout;
import org.apache.storm.trident.tuple.TridentTuple;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

/**
 * @author wincher
 * @date   05/11/2017.
 */
public class TridentFilter {
	//继承BaseFilter类，重新isKeep方法
	public static class CheckFilter extends BaseFilter {
		@Override
		public boolean isKeep(TridentTuple tridentTuple) {
			int a = tridentTuple.getInteger(0);
			int b = tridentTuple.getInteger(1);
			if ( 0 == (a + b) % 2) {
				return true;
			}
			return false;
		}
	}
	
	//继承BaseFunction类，重写execute方法
	public static class Result extends BaseFunction {
		
		@Override
		public void execute(TridentTuple tridentTuple, TridentCollector tridentCollector) {
			//获取tuple输入内容
			Integer a = tridentTuple.getIntegerByField("a");
			Integer b = tridentTuple.getIntegerByField("b");
			Integer c = tridentTuple.getIntegerByField("c");
			Integer d = tridentTuple.getIntegerByField("d");
			System.out.println("a" + a + ", b: " + b + ", c: " + c + ", d: " + d);
		}
	}
	
	public static StormTopology buildTopology() {
		TridentTopology topology = new TridentTopology();
		//设定数据源
		FixedBatchSpout spout = new FixedBatchSpout(
				new Fields("a", "b", "c", "d"),
				4,
				new Values(1, 4, 7, 10),
				new Values(1, 1, 3, 11),
				new Values(2, 2, 7, 1),
				new Values(2, 5, 7, 2));
		spout.setCycle(false);
		Stream inputStream = topology.newStream("spout", spout);
		inputStream.each(new Fields("a", "b", "c", "d"), new CheckFilter())
				.each(new Fields("a", "b", "c", "d"), new Result(), new Fields());
		return topology.build();
	}
	
	public static void main(String[] args) throws Exception {
		Config config = new Config();
		config.setNumWorkers(2);
		config.setMaxSpoutPending(20);
		if ( 0 == args.length) {
			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology("trident-funtion", config, buildTopology());
			Thread.sleep(10000);
			cluster.shutdown();
		} else {
			StormSubmitter.submitTopology(args[0], config, buildTopology());
		}
	}
}
