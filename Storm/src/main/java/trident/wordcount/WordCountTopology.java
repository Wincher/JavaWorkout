package trident.wordcount;

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
import storm.trident.operation.builtin.Count;
import storm.trident.testing.FixedBatchSpout;

/**
 * 注释请参照example下的文件
 * Created by wincher on 07/11/2017.
 */
public class WordCountTopology {
	
	public static StormTopology buildTopology() {
		TridentTopology topology = new TridentTopology();
		FixedBatchSpout spout = new FixedBatchSpout(new Fields("subjects"), 4,
				new Values("java java python ruby c++"),
				new Values("go java python ruby c"),
				new Values("java java perl ruby c++"),
				new Values("java shell python ruby c++"));
		spout.setCycle(false);
		
		Stream inputStream = topology.newStream("spout", spout);
		Stream stream = inputStream.shuffle()
				.each(new Fields("subjects"), new SplitFunction(), new Fields("sub"))
				//进行分组操作：参数为分组字段subject，比较类似于FieldGroup
				.groupBy(new Fields("sub"))
				//对分组后的数据进行聚合操作：1为聚合方法，参数2为输出字段名称
				.aggregate(new Count(), new Fields("count"))
				//继续使用each调用下一个function(bolt)，输入参数为subject和count，第二个参数为new Result() 也就是执行函数，第三个参数为没有输出
				.each(new Fields("sub", "count"), new ResultFunction(), new Fields())
				.parallelismHint(1);
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
