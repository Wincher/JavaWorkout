package kafka.redis;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author wincher
 * @date   09/11/2017.
 */
public class SampleSpout extends BaseRichSpout {
	
	private static final long serialVersionUID = 1L;
	private SpoutOutputCollector spoutOutputCollector;
	
	private static final Map<Integer, String> FIRSTNAMEMAP =new HashMap<Integer, String>();
	static {
		FIRSTNAMEMAP.put(0, "john");
		FIRSTNAMEMAP.put(1, "nick");
		FIRSTNAMEMAP.put(2, "josh");
		FIRSTNAMEMAP.put(3, "jordan");
		FIRSTNAMEMAP.put(4, "curry");
		FIRSTNAMEMAP.put(5, "tom");
	}
	
	private static final Map<Integer, String> LASTNAMEMAP =new HashMap<Integer, String>();
	static {
		LASTNAMEMAP.put(0, "anderson");
		LASTNAMEMAP.put(1, "watson");
		LASTNAMEMAP.put(2, "david");
		LASTNAMEMAP.put(3, "harden");
		LASTNAMEMAP.put(4, "nash");
		LASTNAMEMAP.put(5, "kane");
	}
	private static final Map<Integer, String> COMPANYNAME =new HashMap<Integer, String>();
	static {
		COMPANYNAME.put(0, "abc");
		COMPANYNAME.put(1, "def");
		COMPANYNAME.put(2, "ghi");
		COMPANYNAME.put(3, "jkl");
		COMPANYNAME.put(4, "nmo");
		COMPANYNAME.put(5, "pqr");
	}
	
	@Override
	public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
		this.spoutOutputCollector = spoutOutputCollector;
	}
	
	@Override
	public void nextTuple() {
		final Random rand = new Random();
		int randomNumber = rand.nextInt(5);
		spoutOutputCollector.emit(new Values(FIRSTNAMEMAP.get(randomNumber), LASTNAMEMAP.get(randomNumber), COMPANYNAME.get(randomNumber)));
	}
	
	@Override
	public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
		outputFieldsDeclarer.declare(new Fields("firstName", "lastName", "companyName"));
	}
}
