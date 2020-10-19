package ActiveMQ_JMS.pb;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author wincher
 * @date   13/09/2017.
 */
public class Consumer2 {
	
	
	private ConnectionFactory factory;
	private Connection connection;
	private Session session;
	private MessageConsumer consumer;
	
	private Consumer2() {
		try {
			factory = new ActiveMQConnectionFactory("wincher","123456","tcp://127.0.0.1:61616");
			connection = factory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	public void receive() throws JMSException {
		Destination destination = session.createTopic("topic2");
		consumer = session.createConsumer(destination);
		consumer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message message) {
				try {
					if (message instanceof TextMessage) {
						System.out.println("c2 receive message:---------");
						TextMessage m = (TextMessage) message;
						System.out.println(m.getText());
						
					}
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void main(String[] args) throws JMSException {
		Consumer2 c1 = new Consumer2();
		c1.receive();
	}
}
