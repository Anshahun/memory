package nio;

import java.io.IOException;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

import javax.swing.plaf.SliderUI;

public class Test2 {
	public static void main(String[] args) throws IOException {
		Selector selector = Selector.open();
		ServerSocketChannel channel = ServerSocketChannel.open();
		channel.configureBlocking(false);
		SelectionKey selectionKey = channel.register(selector, SelectionKey.OP_READ|SelectionKey.OP_WRITE);
		while(true){
			int select = selectionKey.selector().select();		
			if(select==0)
				continue;
			Set<SelectionKey> selectedKeys = selector.selectedKeys();
			Iterator<SelectionKey> it = selectedKeys.iterator();
			while(it.hasNext()){
				SelectionKey next = it.next();
				if(next.isAcceptable()){
					
				}
			}
		}
	}
}
