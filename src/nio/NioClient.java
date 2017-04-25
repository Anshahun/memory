package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;

public class NioClient {
	public static void main(String[] args) throws Exception {
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.configureBlocking(false);
		Selector selector = Selector.open();
		socketChannel.register(selector, SelectionKey.OP_CONNECT);
		socketChannel.connect(new InetSocketAddress("127.0.0.1", 1000));
		while (true) {
			selector.select();
			Iterator<SelectionKey> it = selector.selectedKeys().iterator();
			while (it.hasNext()) {
				SelectionKey key = it.next();
				it.remove();
				if (key.isConnectable()) {
					SocketChannel channel = (SocketChannel) key.channel();
					// 如果正在连接，则完成连接
					if (channel.isConnectionPending()) {
						channel.finishConnect();
					}
					// 设置成非阻塞
					channel.configureBlocking(false);
					ByteBuffer buffer = ByteBuffer.allocate(50);
					channel.write(ByteBuffer.wrap("向服务端发送数据".getBytes()));
					// 在和服务端连接成功之后，为了可以接收到服务端的信息，需要给通道设置读的权限。
					channel.register(selector, SelectionKey.OP_READ);
				}else if(key.isReadable()){
					SocketChannel channel = (SocketChannel) key.channel();
					ByteBuffer buffer = ByteBuffer.allocate(50);
 					channel.read(buffer);
 					System.out.println(new String(buffer.array()));					
				}
			}
		}

	}

}
