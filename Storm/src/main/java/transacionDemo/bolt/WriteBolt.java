package transacionDemo.bolt;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;

/**
 * Created by wincher on 02/11/2017.
 */
public class WriteBolt implements IRichBolt {
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
