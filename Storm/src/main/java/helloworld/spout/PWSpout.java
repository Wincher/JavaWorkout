package helloworld.spout;

import org.apache.storm.spout.ISpout;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author wincher
 * @date   27/10/2017.
 */
public class PWSpout extends BaseRichSpout {
	
	private static final long serialVersionUID = 1L;
	private SpoutOutputCollector collector;
	private static final Map<Integer, String> map = new HashMap<Integer,String>();
	
	static {
		map.put(0, "java");
		map.put(1, "php");
		map.put(2, "groovy");
		map.put(3, "python");
		map.put(4, "ruby");
	}
	
	
	@Override
	public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
		//对spout进行初始化
		this.collector = spoutOutputCollector;
		System.out.println(this.collector);
	}
	
	/**
	 * 轮询tuple
	 * @see ISpout#nextTuple()
	 */
	@Override
	public void nextTuple() {
		//随机发送一个单词
		final Random r = new Random();
		int num = r.nextInt(5);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.collector.emit(new Values(map.get(num)));
	}
	
	/**
	 * 声明发送数据的field
	 * @param outputFieldsDeclarer
	 */
	@Override
	public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
		//进行声明
		outputFieldsDeclarer.declare(new Fields("print"));
	}
}
