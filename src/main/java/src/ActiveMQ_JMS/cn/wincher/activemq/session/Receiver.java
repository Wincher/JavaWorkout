package ActiveMQ_JMS.cn.wincher.activemq.session;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by wincher on 11/09/2017.
 */
public class Receiver {
	
	public static void main(String[] args) throws Exception {
		//第一步：建立ConnectionFactory工厂对象，需要填入用户名，密码，以及要连接的地址，
		// 均使用默认即可，默认端口为"tcp://127.0.0.1:61616"
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				//ActiveMQConnectionFactory.DEFAULT_USER,
				//ActiveMQConnectionFactory.DEFAULT_PASSWORD,
				"wincher","123456",
				"tcp://127.0.0.1:61616");
		
		//第二步：通过ConnectionFactory工厂对象创建一个Connection连接，
		// 并调用Connection的start方法开启连接，Connection默认是关闭的
		Connection connection = connectionFactory.createConnection();
		connection.start();
		
		//第三步：通过Connection对象创建Session会话（上下文环境对象，用于接收消息，
		// 参数配置1为是否启用事务，参数配置2为签收模式，一般我们设置自动签收
		Session session = connection.createSession(Boolean.TRUE, Session.CLIENT_ACKNOWLEDGE);
		
		//第四步：通过Session创建Destination对象，指的是一个客户端用来指定生产消息目标的消费信息来源的对象，
		// 在PTP模式中，Destination被称作Queue即队列：在Publisher
		Destination destination = session.createQueue("first");
		
		//第五步：我们需要通过Session对象创建消息的发送和接收对象（生产者和消费者）MessageProducer/MessageConsumer
		MessageConsumer consumer = session.createConsumer(destination);
		
		while (true) {
			//TextMessage msg = (TextMessage)consumer.receive();
			//if (msg == null) break;
			//System.out.println("Received: " + msg.getText());
			//阻塞接收
			TextMessage msg = (TextMessage)consumer.receive();
			
			//手工签收，与session设置有关
			msg.acknowledge();
			//System.out.println(msg.getString("name"));
			System.out.println("消息数据:" + msg.getText());
		}
	}
}
