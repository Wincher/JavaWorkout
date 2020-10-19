package WordCountDemo.bolt;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;

import java.util.*;

/**
 * @author wincher
 * @date   02/11/2017.
 */
public class WordReportBolt implements IRichBolt {
	
	private HashMap<String, Long> counts = new HashMap<String, Long>();
	
	@Override
	public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
	}
	
	@Override
	public void execute(Tuple tuple) {
		String word = tuple.getStringByField("word");
		Long count = tuple.getLongByField("count");
		this.counts.put(word, count);
	}
	
	@Override
	public void cleanup() {
		System.out.println("------ FINAL COUNTS ------");
		List<String> keys = new ArrayList<String>();
		keys.addAll(this.counts.keySet());
		Collections.sort(keys);
		for (String key : keys) {
			System.out.println(key + " : " + this.counts.get(key));
		}
		
		System.out.println("----------------------");
	}
	
	@Override
	public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
	
	}
	
	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}
}
