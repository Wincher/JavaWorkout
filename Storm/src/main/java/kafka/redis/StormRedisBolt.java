package kafka.redis;

import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.IBasicBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by wincher on 09/11/2017.
 */
public class StormRedisBolt implements IBasicBolt {
	private static final long serialVersionUID = 2L;
	private RedisOperations redisOperations = null;
	private String redisIp = null;
	private int port;

	public StormRedisBolt(String host, int port) {
		this.redisIp = host;
		this.port = port;
	}
	
	@Override
	public void prepare(Map map, TopologyContext topologyContext) {
		redisOperations = new RedisOperations(redisIp, port);
	}
	
	@Override
	public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
		Map<String, Object> record = new HashMap<String, Object>();
		record.put("firstName", tuple.getValueByField("firstName"));
		record.put("lastName", tuple.getValueByField("lastName"));
		record.put("companyName", tuple.getValueByField("companyName"));
		redisOperations.insert(record, UUID.randomUUID().toString());
	}
	
	@Override
	public void cleanup() {
	
	}
	
	@Override
	public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
	
	}
	
	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}
}
