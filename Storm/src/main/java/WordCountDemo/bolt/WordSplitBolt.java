package WordCountDemo.bolt;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wincher on 02/11/2017.
 */
public class WordSplitBolt implements IRichBolt{
	
	private OutputCollector collector;
	
	@Override
	public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
		this.collector = outputCollector;
	}
	
	@Override
	public void execute(Tuple tuple) {
		String sentence = tuple.getStringByField("sentence");
		String[] words = sentence.replaceAll(",", "").split(" ");
		for (String word : words) {
			this.collector.emit(new Values(word));
//			this.collector.ack(tuple);
		}
	}
	
	@Override
	public void cleanup() {
	
	}
	
	@Override
	public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
		outputFieldsDeclarer.declare(new Fields("word"));
	}
	
	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}
}
