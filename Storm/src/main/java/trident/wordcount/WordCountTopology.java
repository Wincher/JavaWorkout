package trident.wordcount;

import org.apache.kafka.common.metrics.stats.Count;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.trident.Stream;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.testing.FixedBatchSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

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
		// need to test after update not work
//		Stream stream = inputStream.shuffle()
//				.each(new Fields("subjects"), new SplitFunction(), new Fields("sub"))
//				//进行分组操作：参数为分组字段subject，比较类似于FieldGroup
//				.groupBy(new Fields("sub"))
//				//对分组后的数据进行聚合操作：1为聚合方法，参数2为输出字段名称
//				//## need to test
//				.aggregate(new Count(), new Fields("count"))
//				//继续使用each调用下一个function(bolt)，输入参数为subject和count，第二个参数为new Result() 也就是执行函数，第三个参数为没有输出
//				.each(new Fields("sub", "count"), new ResultFunction(), new Fields())
//				.parallelismHint(1);
		return topology.build();
	}
	
	public static void main(String[] args) throws Exception {
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
