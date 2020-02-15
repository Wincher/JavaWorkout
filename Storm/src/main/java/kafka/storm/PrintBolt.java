package kafka.storm;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;

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
