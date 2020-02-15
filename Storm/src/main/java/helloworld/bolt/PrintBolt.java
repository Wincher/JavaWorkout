package helloworld.bolt;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

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
