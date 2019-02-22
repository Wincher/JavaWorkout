package kafka.storm;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.IBasicBolt;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;

/**
 * Created by wincher on 08/11/2017.
 */
public class PrintBolt extends BaseBasicBolt {
	
	
	@Override
	public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
		String sentece = tuple.getString(0);
		System.out.println("Received Sentence: " + sentece);
	}
	
	@Override
	public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
		//do not emit anything
	}
}
