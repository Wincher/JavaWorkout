package trident.example;

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
import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.testing.FixedBatchSpout;
import storm.trident.tuple.TridentTuple;

/**
 * Created by wincher on 04/11/2017.
 */
public class TridentFunction {
	
	//继承BaseFunction类，重写execute方法
	public static class SumFunction extends BaseFunction {
		
		@Override
		public void execute(TridentTuple tridentTuple, TridentCollector tridentCollector) {
			System.out.println("传入的内容： " + tridentTuple);
			//获取a，b两个域
			int a = tridentTuple.getInteger(0);
			int b = tridentTuple.getInteger(1);
			int sum = a + b;
			//发射数据
			tridentCollector.emit(new Values(sum));
		}
	}
	
	//继承BaseFunction类，重写execute方法
	public static class Result extends BaseFunction {
		@Override
		public void execute(TridentTuple tridentTuple, TridentCollector tridentCollector) {
			
			Integer a = tridentTuple.getIntegerByField("a");
			Integer b = tridentTuple.getIntegerByField("b");
			Integer c = tridentTuple.getIntegerByField("c");
			Integer d = tridentTuple.getIntegerByField("d");
			System.out.println("a" + a + ", b: " + b + ", c: " + c + ", d: " + d);
			Integer sum = tridentTuple.getIntegerByField("sum");
			System.out.println("sum: " + sum);
		}
	}
	
	public static StormTopology buildTopology() {
		TridentTopology topology = new TridentTopology();
		//设定数据源
		//声明输入的域字端位abcd， 批处理大小为4
		FixedBatchSpout spout = new FixedBatchSpout(new Fields("a", "b", "c", "d"), 4,
				//设置数据内容，测试数据
				new Values(1, 4, 7, 10),
				new Values(1, 1, 3, 11),
				new Values(2, 2, 7, 1),
				new Values(2, 5, 7, 2));
		//指定是否循环
		spout.setCycle(false);
		//指定输入源spout
		Stream inputStream = topology.newStream("spout", spout);
		/**
		 * 要实现spout - bolt 的模式， 在trident里是使用each来做的
		 * each方法参数
		 * 1. 输入数据源参数名称： a b c d
		 * 2. 需要流转执行的function对象(也就是bolt对象) new SumFunction()
		 * 3. 指定function对象里的输出参数名称： sum
		 */
		inputStream.each(new Fields("a", "b", "c", "d"), new SumFunction(), new Fields("sum"))
				/**
				 * 继续使用each调用下一个function(bolt)
				 * 第一个输入参数为: a b c d sum
				 * 第二个输入参数为 new Result() 也就是执行函数，第三个参数为没有输出
				 */
				.each(new Fields("a", "b", "c", "d", "sum"), new Result(), new Fields());
		
		return topology.build(); //利用这种方式，返回StormTopology对象，进行提交
	}
	
	public static void main(String[] args) throws InterruptedException, AlreadyAliveException, InvalidTopologyException {
		Config conf = new Config();
		//设置batch最大处理
		conf.setNumWorkers(2);
		conf.setMaxSpoutPending(20);
		if ( 0 == args.length) {
			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology("trident-funtion", conf, buildTopology());
			Thread.sleep(10000);
			cluster.shutdown();
		} else {
			StormSubmitter.submitTopology(args[0], conf, buildTopology());
		}
	}
}
