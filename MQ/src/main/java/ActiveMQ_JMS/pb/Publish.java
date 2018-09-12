package ActiveMQ_JMS.pb;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by wincher on 13/09/2017.
 */
public class Publish {
	
	private ConnectionFactory factory;
	private Connection connection;
	private Session session;
	private MessageProducer producer;
	
	public Publish() {
		try {
			factory = new ActiveMQConnectionFactory("wincher","123456","tcp://127.0.0.1:61616");
			connection = factory.createConnection();
			connection.start();
			session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
			producer = session.createProducer(null);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	public void sendMessage() throws Exception{
		Destination destination = session.createTopic("topic1");
		TextMessage textMessage = session.createTextMessage("content");
		producer.send(destination, textMessage);
	}
	
	public static void main(String[] args) throws Exception {
		Publish p = new Publish();
		p.sendMessage();
	}
}
