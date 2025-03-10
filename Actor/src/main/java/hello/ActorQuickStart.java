package hello;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class ActorQuickStart {
    public static void main(String[] args) {
        // 创建 Actor 系统
        final ActorSystem system = ActorSystem.create("hello-akka");

        try {
            // 创建 Printer Actor
            final ActorRef printerActor = system.actorOf(Printer.props(), "printerActor");

            // 创建 Greeter Actor，并将 Printer Actor 的引用传递给它
            final ActorRef greeterActor = system.actorOf(Greeter.props("Hello Akka!", printerActor), "greeterActor");

            // 向 Greeter Actor 发送 Greet 消息
            greeterActor.tell(new Greeter.Greet(), ActorRef.noSender());

            //wait Actor aSync running...
            System.out.println(">>> Press ENTER to exit <<<");
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            system.terminate();
        }
    }
}
