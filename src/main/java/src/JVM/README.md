永久区，方法区 存放java原始的类信息，类过多需要设置更大的PermSize
-XXPermSize=64M
-XXMaxPermSize=64M
直接内存
-XX:MaxDirectMemorySize
最大栈空间
-Xss
指定新生代经过多少次回收后进入老年代
-XX:MaxTenuringThreshold
指定对象超过指定大小之后直接晋升老年代
-XX:PretenureSizeThreshold