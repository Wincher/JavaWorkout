package trident.strategy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.storm.trident.operation.BaseFunction;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.tuple.TridentTuple;

/**
 * Created by wincher on 07/11/2017.
 */
public class WriteFunction extends BaseFunction {
	
	public static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(WriteFunction.class);
	
	@Override
	public void execute(TridentTuple tridentTuple, TridentCollector tridentCollector) {
		String text = tridentTuple.getStringByField("sub");
		System.out.println(Thread.currentThread().getName() + "-------------" + text);
		
	}
}
