package WordCountDemo.bolt;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;

import java.util.*;

/**
 * Created by wincher on 02/11/2017.
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
