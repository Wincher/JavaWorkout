package drpc.reach;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.LocalDRPC;
import org.apache.storm.StormSubmitter;
import org.apache.storm.coordination.BatchOutputCollector;
import org.apache.storm.drpc.LinearDRPCTopologyBuilder;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.thrift.TException;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.topology.base.BaseBatchBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.*;

/**
 * Created by wincher on 03/11/2017.
 */
public class ReachTopology {
	
	public static Map<String, List<String>> TWEETERS_DB = new HashMap<String, List<String>>() {
		{
			put("csdn.com/blog/1", Arrays.asList("sally", "tom", "john", "hebe", "helena"));
			put("twitter.com/blog/3", Arrays.asList("bob", "tom", "linda", "hebe", "henry"));
			put("stackoverflow.com/blog/5", Arrays.asList("messi", "tom", "john", "bale", "henry"));
		}
	};
	
	public static Map<String, List<String>> FOLLOWERS_DB = new HashMap<String, List<String>>() {
		{
			put("sally", Arrays.asList("bob", "tom", "john", "wincher", "chris"));
			put("tom", Arrays.asList("bob", "tom", "john", "wincher", "chris"));
			put("hebe", Arrays.asList("bob", "tom", "john", "wincher", "chris"));
			put("helena", Arrays.asList("bob", "tom", "john", "wincher", "chris"));
			put("bob", Arrays.asList("bob", "tom", "john", "wincher", "chris"));
			put("linda", Arrays.asList("bob", "tom", "john", "wincher", "chris"));
			put("henry", Arrays.asList("bob", "tom", "john", "wincher", "chris"));
			put("messi", Arrays.asList("bob", "tom", "john", "wincher", "chris"));
			put("bale", Arrays.asList("bob", "tom", "john", "wincher", "chris"));
		}
	};
	
	public static class GetTweeters extends BaseBasicBolt {
		
		@Override
		public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
			Object id = tuple.getValue(0);
			System.out.println("========" + id);
			String url = tuple.getString(1);
			List<String> tweeters = TWEETERS_DB.get(url);
			if ( null != tweeters) {
				for (String tweeter : tweeters) {
					basicOutputCollector.emit(new Values(id, tweeter));
				}
			}
		}
		
		@Override
		public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
			outputFieldsDeclarer.declare(new Fields("id", "tweeter"));
		}
	}
	
	public static class GetFollowers extends BaseBasicBolt {
		
		@Override
		public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
			Object id = tuple.getValue(0);
			String tweeter = tuple.getString(1);
			List<String> followers = FOLLOWERS_DB.get(tweeter);
			if (null != followers) {
				for (String follower : followers) {
					basicOutputCollector.emit(new Values(id, follower));
				}
			}
		}
		
		@Override
		public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
			outputFieldsDeclarer.declare(new Fields("id", "follower"));
		}
	}
	
	public static class PartiaUniquer extends BaseBatchBolt {
		
		
		BatchOutputCollector _collector;
		Object _id;
		Set<String> _followers = new HashSet<String>();
		
		@Override
		public void prepare(Map map, TopologyContext topologyContext, BatchOutputCollector batchOutputCollector, Object o) {
			_collector = batchOutputCollector;
			_id = o;
		}
		
		@Override
		public void execute(Tuple tuple) {
			_followers.add(tuple.getString(1));
		}
		
		@Override
		public void finishBatch() {
			_collector.emit(new Values(_id, _followers.size()));
		}
		
		@Override
		public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
			outputFieldsDeclarer.declare(new Fields("id", "partial-count"));
		}
	}
	
	public static class CountAggregator extends BaseBatchBolt {
		
		BatchOutputCollector _collector;
		Object _id;
		int _count = 0;
		
		@Override
		public void prepare(Map map, TopologyContext topologyContext, BatchOutputCollector batchOutputCollector, Object o) {
			_collector = batchOutputCollector;
			_id = o;
		}
		
		@Override
		public void execute(Tuple tuple) {
			_count += tuple.getInteger(1);
		}
		
		@Override
		public void finishBatch() {
			_collector.emit(new Values(_id, _count));
		}
		
		@Override
		public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
			outputFieldsDeclarer.declare(new Fields("id", "reach"));
		}
	}
	
	public static LinearDRPCTopologyBuilder constructor() {
		LinearDRPCTopologyBuilder builder = new LinearDRPCTopologyBuilder("reach");
		//获取转发过url的人
		builder.addBolt(new GetTweeters(), 4);
		//获取上面的人的粉丝
		builder.addBolt(new GetFollowers(), 12).shuffleGrouping();
		//对粉丝进行去重
		builder.addBolt(new PartiaUniquer(), 6).fieldsGrouping(new Fields("id", "follower"));
		//最后进行统计人数
		builder.addBolt(new CountAggregator(),3).fieldsGrouping(new Fields("id"));
		return builder;
	}
	
	public static void main(String[] args) throws Exception {
		LinearDRPCTopologyBuilder builder = constructor();
		Config conf = new Config();
		
		if (null != args || 0 != args.length) {
			conf.setMaxSpoutPending(3);
			LocalDRPC drpc = new LocalDRPC();
			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology("reach-drpc", conf, builder.createLocalTopology(drpc));
			
			String[] urlsToTry = new String[] {"csdn.com/blog/1", "twitter.com/blog/3", "stackoverflow.com/blog/5"};
			for (String url : urlsToTry) {
				System.out.println("Reach of " + url + ": " + drpc.execute("reach", url));
			}
			
			cluster.shutdown();
			drpc.shutdown();
		} else {
			conf.setNumWorkers(6);
			StormSubmitter.submitTopology(args[0], conf, builder.createRemoteTopology());
		}
	}
}
