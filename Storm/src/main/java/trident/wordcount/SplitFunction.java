package trident.wordcount;

import backtype.storm.tuple.Values;
import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.tuple.TridentTuple;

/**
 * Created by wincher on 07/11/2017.
 */
public class SplitFunction extends BaseFunction {
	@Override
	public void execute(TridentTuple tridentTuple, TridentCollector tridentCollector) {
		String subjects = tridentTuple.getStringByField("subjects");
		for (String sub: subjects.split(" ")) {
			tridentCollector.emit(new Values(sub));
		}
	}
}
