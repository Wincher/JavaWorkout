package trident.strategy;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.generated.StormTopology;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import storm.trident.Stream;
import storm.trident.TridentTopology;
import storm.trident.testing.FixedBatchSpout;

/**
 * Created by wincher on 07/11/2017.
 */
public class StrategyTopology {
	
	public static StormTopology buildTopology() {
		TridentTopology topology = new TridentTopology();
		//设定数据源
		FixedBatchSpout spout = new FixedBatchSpout(
				new Fields("sub"), //声明输入的域字段为"sub"
				4, //设置批处理大小
				//设置数据源内容
				new Values("hadoop"),
				new Values("storm"),
				new Values("mysql"),
				new Values("redis"),
				new Values("kafka")
		);
		//指定是否循环
		spout.setCycle(true);
		//指定输入源spout
		Stream inputStream = topology.newStream("spout", spout);
		/**
		 * 要实现流spout - bolt的模式在trident里使用each来实现
		 * each方法参数:
		 * 1.输入数据源参数名称:"sub"
		 * 2.需要流转执行的function对象(也就是bolt对象): new WriteFunction()
		 * 3.指定function对象里的输出参数名称，没有则不输出任何内容
		 */
		inputStream
				//随机分组:shuffle
				//.shuffle()
				//分区分组:partitionBy
				.partitionBy(new Fields("sub"))
				//全局分组:partitionBy
				//.global()
				//广播分组:partitionBy
				//.broadcast()
				.each(new Fields("sub"), new WriteFunction(), new Fields()).parallelismHint(1);
		return topology.build();
	}
	
	public static void main(String[] args) throws InterruptedException, AlreadyAliveException, InvalidTopologyException {
		Config conf = new Config();
		//设置batch最大处理
		conf.setNumWorkers(2);
		conf.setMaxSpoutPending(20);
		if (0 == args.length) {
			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology("trident-strategy", conf, buildTopology());
			Thread.sleep(5000);
			cluster.shutdown();
		} else {
			StormSubmitter.submitTopology(args[0], conf, buildTopology());
		}
	}
	
}
