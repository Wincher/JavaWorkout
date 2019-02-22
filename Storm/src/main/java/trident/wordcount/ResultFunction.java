package trident.wordcount;

import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.tuple.TridentTuple;

/**
 * Created by wincher on 07/11/2017.
 */
public class ResultFunction extends BaseFunction {
	@Override
	public void execute(TridentTuple tridentTuple, TridentCollector tridentCollector) {
		String sub = tridentTuple.getStringByField("sub");
		Long count = tridentTuple.getLongByField("count");
		System.out.println(sub + " appears: " + count + " times");
	}
}
