package hello;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

public class Greeter extends AbstractActor {


	private final String message;
	private final ActorRef printerActor;

	public Greeter(String message, ActorRef printerActor) {
		this.message = message;
		this.printerActor = printerActor;
	}

	// 定义接收消息的类型
	static public class Greet {
		public Greet() {
		}
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(Greet.class, greet -> {
					// 收到 Greet 消息后，向 Printer Actor 发送 greeting 消息
					System.out.println(this.getClass().getName() + ": " + message);
					printerActor.tell(message, getSelf());
				})
				.build();
	}

	// 创建 Greeter Actor 的 Props
	public static Props props(String message, ActorRef printerActor) {
		return Props.create(Greeter.class, () -> new Greeter(message, printerActor));
	}
}
