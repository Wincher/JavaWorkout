常用的一些kafka操作

0.创建topic命令
* ./kafka-topics.sh --create --zookeeper 192.168.0.111:2181 --replication-factor 3 --partitions 3 --topic test

0.查看topic列表命令
* ./kafka-topics.sh  --zookeeper 192.168.0.111:2181 --list

0. 接收数据指令 --zokeeper localhost过时了更换了bootstrap-server，需要在后面加端口
* ./kafka-console-consumer.sh --bootstrap-server localhost:2181 --topic test --from-beginning


* 遇见一些问题，请注意版本之间的协调，注意很多依赖重复的log4j需要去掉重复的
* 使用kafkaspout需要创建topic