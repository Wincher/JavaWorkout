package WordCountDemo.spout;

import util.Utils;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import java.util.Map;

/**
 * Created by wincher on 02/11/2017.
 */
public class WordSpout implements IRichSpout {
	
	private static final long serialVersionUID = 1L;
	
	private SpoutOutputCollector collector;
	
	private int index = 0;
	
	private String[] sentences = {
			"Winter is Coming",
			"Hear Me Roar",
			"Ours is the Fury",
			"Fire and Blood",
			"As High as Honor",
			"Family, Duty, Honor",
			"Growing Strong",
			"Our Blades are Sharp",
			"The Sun of Winter",
			"We Do Not Sow",
			"Unbowed, Unbent, Unbroken"
	};
	
	@Override
	public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
		this.collector = spoutOutputCollector;
	}
	
	@Override
	public void close() {
		System.out.println("== word spout stop... ==");
	}
	
	@Override
	public void activate() {
	
	}
	
	@Override
	public void deactivate() {
	
	}
	
	@Override
	public void nextTuple() {
		this.collector.emit(new Values(sentences[index]));
		index++;
		if (index >= sentences.length) {
			index = 0;
		}
		Utils.waitForSeconds(1);
	}
	
	@Override
	public void ack(Object o) {
	
	}
	
	@Override
	public void fail(Object o) {
	
	}
	
	@Override
	public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
		outputFieldsDeclarer.declare(new Fields("sentence"));
	}
	
	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}
}
