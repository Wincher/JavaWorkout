package ActiveMQ_JMS.cn.wincher.activemq.p2p;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by wincher on 13/09/2017.
 */
public class Consumer {
	
	public final String SELECTOR_0 = "age < 25";
	public final String SELECTOR_1 = "color = 'blue'";
	public final String SELECTOR_2 = "color = 'blue' AND sal > 2000";
	public final String SELECTOR_3 = "receiver = 'A'";
	// 单例模式
	
	// 1连接工厂
	private ConnectionFactory connectionFactory;
	// 2连接对象
	private Connection connection;
	// 3Session对象
	private Session session;
	// 4生产者
	private MessageConsumer messageConsumer;
	
	private Destination destination;
	
	public Consumer() {
		try {
			this.connectionFactory = new ActiveMQConnectionFactory(
					"wincher","123456","tcp://127.0.0.1:61616");
			this.connection = this.connectionFactory.createConnection();
			this.connection.start();
			this.session = this.connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue("first");
			
			//selector遵从sql92规则，筛选property
			messageConsumer = session.createConsumer(destination,SELECTOR_1);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	public void receiver() {
		try {
			messageConsumer.setMessageListener(new MessageListener() {
				@Override
				public void onMessage(Message message) {
					try {
						if (message instanceof TextMessage) {
						
						}
						if (message instanceof MapMessage) {
							MapMessage ret = (MapMessage) message;
							System.out.println(ret.toString());
							System.out.println(ret.getStringProperty("name"));
							System.out.println(ret.getStringProperty("age"));
							
						}
					} catch (JMSException e) {
						e.printStackTrace();
					}
					
				}
			});
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Consumer c = new Consumer();
		c.receiver();
	}
}
