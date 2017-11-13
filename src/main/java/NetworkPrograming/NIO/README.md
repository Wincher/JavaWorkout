### NIO 非阻塞IO
    实际还是同步的
    NIO几个概念
    Buffer 缓冲区
    Channel 管道、通道
    Selector 选择器、多路复用器
    NIO的本质就是避免原始的TCP建立连接使用三次握手的操作，
    减少连接的开销