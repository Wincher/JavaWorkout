# HttpFileServer 

> 没有特殊的意义,就是用Netty创建一个HTTP服务器接收请求,Handler处理,返回HTTP response的demo

## request
request with file
> curl -i -F "file=@./src/main/resources/pic00.jpg" http://localhost:8765/