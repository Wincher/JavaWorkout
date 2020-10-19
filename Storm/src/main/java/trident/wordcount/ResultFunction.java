package trident.wordcount;

import org.apache.storm.trident.operation.BaseFunction;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.tuple.TridentTuple;

/**
 * @author wincher
 * @date   07/11/2017.
 */
public class ResultFunction extends BaseFunction {
	@Override
	public void execute(TridentTuple tridentTuple, TridentCollector tridentCollector) {
		String sub = tridentTuple.getStringByField("sub");
		Long count = tridentTuple.getLongByField("count");
		System.out.println(sub + " appears: " + count + " times");
	}
}
