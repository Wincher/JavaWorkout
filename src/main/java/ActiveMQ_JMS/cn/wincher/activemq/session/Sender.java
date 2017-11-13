package ActiveMQ_JMS.cn.wincher.activemq.session;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by wincher on 11/09/2017.
 * 如果session开启事务，session需要手动commit才能提交
 */
public class Sender {
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
		//consumer的策略会以publisher为准，为了可读性建议consumer的session初始化与publisher一致
		Session session = connection.createSession(Boolean.TRUE, Session.CLIENT_ACKNOWLEDGE);
		
		//第四步：通过Session创建Destination对象，指的是一个客户端用来指定生产消息目标的消费信息来源的对象，
		// 在PTP模式中，Destination被称作Queue即队列：在Publisher
		Destination destination = session.createQueue("first");
		
		//第五步：我们需要通过Session对象创建消息的发送和接收对象（生产者和消费者）MessageProducer/MessageConsumer.
		//参数为此生产者发送数据的目的地，可以在发送的时候再次指定更加灵活所以此处设置为null
		MessageProducer producer = session.createProducer(null);
		
		//第六步：我们可以使MessageProducer的setDeliveryMode方法为其设置持久化特性和非持久化特性(DeliveryMode)，****
		//producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		
		//第七步：使用JMS规范的TextMessage形式创建数据（通过Session对象），并用MessageProducer的send方法发送数据，
		//同理客户端使用receive方法进行接收
		
		for (int i = 0; i < 10; i++) {
			TextMessage msg = session.createTextMessage("Message content " + i);
			//msg.setText("这个方法也可以");;
			//第一个参数 目标地址
			//第二个参数 具体数据信息
			//第三个参数 传递数据的模式
			//第四个参数 优先级
			//第五个参数 消息的过期时间
			producer.send(destination, msg);
			System.out.println(msg.getText());
		}
		
		session.commit();
		
//		for (int i = 0; i < 5; i++) {
//			MapMessage map = session.createMapMessage();
//			map.setString("name","john");
//			map.setString("age","20");
//			map.setString("address","beijing");
//			producer.send(destination, map, DeliveryMode.NON_PERSISTENT, 0, 1000L*1000);
//		}
		
		if (connection != null) {
			connection.close();
		}
	}
}
