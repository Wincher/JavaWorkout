package helloworld.bolt;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by wincher on 27/10/2017.
 */
public class PrintBolt extends BaseBasicBolt {
	
	
	private static final Log log = LogFactory.getLog(PrintBolt.class);
	private static final long serialVersionUID = 1L;
	@Override
	public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
		//获取上一个组件锁声明的Field
		String print = tuple.getStringByField("print");
		log.info("[print]:" + print);
//		System.out.println("Name of input word is: " + word);
		//传递给下一个bilt
		basicOutputCollector.emit(new Values(print));
	}
	
	@Override
	public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
		outputFieldsDeclarer.declare(new Fields("write"));
	}
}
