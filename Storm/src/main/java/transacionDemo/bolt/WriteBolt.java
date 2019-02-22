package transacionDemo.bolt;

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
public class WriteBolt implements IRichBolt{
	private OutputCollector collector;
	
	@Override
	public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
		this.collector = outputCollector;
	}
	
	@Override
	public void execute(Tuple tuple) {
		String word = tuple.getStringByField("word");
		try {
			System.out.println(word);
		} catch (Exception e) {
			e.printStackTrace();
		}
		collector.emit(tuple, new Values(word));
		collector.ack(tuple);
	}
	
	@Override
	public void cleanup() {
	
	}
	
	@Override
	public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
	}
	
	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}
}
