package kafka.storm;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang.StringUtils;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wincher on 08/11/2017.
 */
public class SentenceBolt extends BaseBasicBolt {
	
	//list used for aggregating the words
	private List<String> words = new ArrayList<String>();
	
	@Override
	public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
		//Get the word from the tuple
		String word = tuple.getString(0);
		if (StringUtils.isBlank(word)) {
			return;
		}
		System.out.println("Received Word: " + word);
		//add word to current list of words
		words.add(word);
		
		if (word.endsWith(".")) {
			basicOutputCollector.emit(ImmutableList.of(
					(Object) StringUtils.join(words, " ")));
			words.clear();
		}
	}
	
	@Override
	public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
		outputFieldsDeclarer.declare(new Fields("sentence"));
	}
}
