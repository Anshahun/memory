package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NioServer {
	public static void main(String[] args) throws Exception {
		ServerSocketChannel socketChannel = ServerSocketChannel.open();
		socketChannel.bind(new InetSocketAddress(1000));
		socketChannel.configureBlocking(false);
		Selector selector = Selector.open();
		socketChannel.register(selector, SelectionKey.OP_ACCEPT);
		while(true){
			int selectCount = selector.select();
			if(selectCount==0){
				continue;
			}
			Iterator<SelectionKey> it = selector.selectedKeys().iterator();
			while(it.hasNext()){
				SelectionKey key = it.next();
				it.remove();
				if(key.isAcceptable()){
					System.out.println("accept==========");
					SocketChannel channel = socketChannel.accept();
					channel.configureBlocking(false);
					channel.register(selector,SelectionKey.OP_READ);
				}else if(key.isReadable()){
					//System.out.println("read============");
					SocketChannel channel = (SocketChannel) key.channel();
					ByteBuffer buffer = ByteBuffer.allocate(50);
					channel.read(buffer);
					System.out.println(new String(buffer.array()));
					buffer.clear();
					ByteBuffer buffer2 = ByteBuffer.allocate(50);
					buffer2.put("Server response".getBytes());
					System.out.println(new String(buffer2.array()));
					buffer2.flip();
					channel.write(buffer2);
				}
				
			}
		}
	}
}
