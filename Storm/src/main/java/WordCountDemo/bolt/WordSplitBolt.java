package WordCountDemo.bolt;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;

/**
 * @author wincher
 * @date   02/11/2017.
 */
public class WordSplitBolt implements IRichBolt {
	
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
