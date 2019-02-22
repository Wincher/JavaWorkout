package helloworld.bolt;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.FileWriter;

/**
 * Created by wincher on 27/10/2017.
 */
public class WriteBolt extends BaseBasicBolt {
	
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(WriteBolt.class);
	private FileWriter writer;
	
	@Override
	public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
		String text = tuple.getStringByField("write");
		try {
			if (null == writer) {
				if (System.getProperty("os.name").equals("Mac OS X")) {
					writer = new FileWriter("/Users/wincher/Downloads/" + this);
				}
				log.info("[write]: 写入文件" );
				writer.write(text);
				writer.write("\n");
				writer.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
	
	}
}
