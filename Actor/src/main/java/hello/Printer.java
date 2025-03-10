package hello;

import akka.actor.AbstractActor;
import akka.actor.Props;

public class Printer extends AbstractActor {

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(String.class, message -> {
					// 收到 String 消息后，打印到控制台
					System.out.println("Printer: " + message);
				})
				.build();
	}

	// 创建 Printer Actor 的 Props
	public static Props props() {
		return Props.create(Printer.class, () -> new Printer());
	}
}