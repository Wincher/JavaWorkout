# zk

## how to monitor 
```bash
docker run --name zk00  -p 2181:2181 --restart always -d zookeeper

curl -O https://issues.apache.org/jira/secure/attachment/12436620/ZooInspector.zip
unzip ZooInspector.zip
cd build
java -jar zookeeper-dev-ZooInspector.jar
```