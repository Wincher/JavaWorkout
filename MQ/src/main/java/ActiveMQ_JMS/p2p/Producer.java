package ActiveMQ_JMS.p2p;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author wincher
 * @date   13/09/2017.
 */
public class Producer {
	// 单例模式
	
	// 1连接工厂
	private ConnectionFactory connectionFactory;
	// 2连接对象
	private Connection connection;
	// 3Session对象
	private Session session;
	// 4生产者
	private MessageProducer messageProducer;
	
	public Producer() {
		try {
			this.connectionFactory = new ActiveMQConnectionFactory(
					"wincher","123456","tcp://127.0.0.1:61616");
			this.connection = this.connectionFactory.createConnection();
			this.connection.start();
			this.session = this.connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
			messageProducer = session.createProducer(null);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	public Session getSession() {
		return this.session;
	}
	
	public void send1(/*String queueName,Message message */) {
		try {
			Destination destination = this.session.createQueue("first");
			Message msg1 = this.session.createMapMessage();
			msg1.setStringProperty("name","zhangsan");
			msg1.setStringProperty("age","23");
			msg1.setStringProperty("color","blue");
			msg1.setIntProperty("sal",2000);
			Message msg2 = this.session.createMapMessage();
			msg2.setStringProperty("name","lisi");
			msg2.setStringProperty("age","22");
			msg2.setStringProperty("color","green");
			msg2.setIntProperty("sal",2600);
			Message msg3 = this.session.createMapMessage();
			msg3.setStringProperty("name","wangwu");
			msg3.setStringProperty("age","28");
			msg3.setStringProperty("color","blue");
			msg3.setIntProperty("sal",3600);
			Message msg4 = this.session.createMapMessage();
			msg4.setStringProperty("name","zhaoliu");
			msg4.setStringProperty("age","21");
			msg4.setStringProperty("color","purse");
			msg4.setIntProperty("sal",4888);
			
			this.messageProducer.send(destination,msg1,DeliveryMode.NON_PERSISTENT,2,1000*60*10L);
			this.messageProducer.send(destination,msg2,DeliveryMode.NON_PERSISTENT,3,1000*60*10L);
			this.messageProducer.send(destination,msg3,DeliveryMode.NON_PERSISTENT,6,1000*60*10L);
			this.messageProducer.send(destination,msg4,DeliveryMode.NON_PERSISTENT,9,1000*60*10L);
			
			if (connection != null) {
				connection.close();
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	public void send2() {
		try {
			Destination destination = session.createQueue("first");
			TextMessage msg = session.createTextMessage("this is a string!");
			messageProducer.send(destination,msg, DeliveryMode.NON_PERSISTENT,9,1000*60*10L);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Producer p = new Producer();
		p.send1();
//		p.send2();
	}
}
