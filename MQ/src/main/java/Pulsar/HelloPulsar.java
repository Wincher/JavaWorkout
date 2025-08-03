package Pulsar;

import org.apache.pulsar.client.api.*;

/**
 * docker run -d \
 *   --name pulsar-standalone -p 6650:6650 -p 8080:8080 \
 *   --memory 4g --memory-swap 4g -e "PULSAR_MEM=-Xms2g -Xmx2g" \
 *   apachepulsar/pulsar:2.7.0 bin/pulsar standalone
 *  according to: 深入解析ApachePulsar第二章
 */
public class HelloPulsar {

    final private static String pulsar_service_url= "pulsar://cvm00:6650";


    public static void main(String[] args) throws PulsarClientException {
        /* check stats with
         * curl -s http://cvm00:8080/admin/v2/tenants
         * curl -s http://cvm00:8080/admin/v2/namespaces/public
         * curl -s http://cvm00:8080/admin/v2/namespaces/public/default
         * curl -s http://cvm00:8080/admin/v2/non-persistent/public/default
         * curl -s http://cvm00:8080/admin/v2/persistent/public/default/my-partitioned-topic/stats
         */
        producer();
//        consumer();
    }

    private static void producer() throws PulsarClientException {
        PulsarClient client = PulsarClient.builder()
                .serviceUrl(pulsar_service_url)
                .build();

        Producer<byte[]> producer = client.newProducer()
                .topic("my-partitioned-topic")
                //ProducerAccessMode 2.8才加入也就是这本书用的client包最低也是2.8的
                .messageRoutingMode(MessageRoutingMode.SinglePartition) // 设置 SinglePartition 模式
                .create();
        // 无 Key 的消息，发送到固定分区
        producer.send("Message without key".getBytes());

        // 有 Key 的消息，可能仍发送到固定分区
        producer.newMessage()
                .key("my-key")
                .value("Message with key".getBytes())
                .send();

        producer.close();
        client.close();
    }

    private static void consumer() throws PulsarClientException {
        PulsarClient client = PulsarClient.builder()
                .serviceUrl(pulsar_service_url)
                .build();
        Consumer<byte[]> consumer = client.newConsumer()
                .topic("my-partitioned-topic")
                .subscriptionName("my-subscription")
                .subscribe();
        while (true) {
            Message msg = consumer.receive();
            System.out.printf("Message received: %s%n", new String(msg.getData()));
            consumer.acknowledge(msg);
        }
//        consumer.close();
//        client.close();
    }
}
