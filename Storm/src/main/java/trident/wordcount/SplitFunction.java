package trident.wordcount;

import org.apache.storm.trident.operation.BaseFunction;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.tuple.TridentTuple;
import org.apache.storm.tuple.Values;

/**
 * @author wincher
 * @date   07/11/2017.
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
