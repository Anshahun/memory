package nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.junit.Test;

public class test1 {
	public static void main(String[] args) {
		try {
			// RandomAccessFile raf=new RandomAccessFile(new
			// File("D:\\3\\test.txt"), "r");
			/**
			 * model各个参数详解 r 代表以只读方式打开指定文件 rw 以读写方式打开指定文件 rws
			 * 读写方式打开，并对内容或元数据都同步写入底层存储设备 rwd 读写方式打开，对文件内容的更新同步更新至底层存储设备
			 * 
			 * **/
			String path = "C:\\test.txt";
			RandomAccessFile raf = new RandomAccessFile(path, "rw");
			// 获取RandomAccessFile对象文件指针的位置，初始位置是0
			System.out.println("RandomAccessFile文件指针的初始位置:"
					+ raf.getFilePointer());
			raf.seek(3);// 移动文件指针位置
			byte[] buff = new byte[1024];
			/*
			 * //用于保存实际读取的字节数 int hasRead=0; //循环读取
			 * while((hasRead=raf.read(buff))>0){ //打印读取的内容,并将字节转为字符串输入
			 * System.out.println(new String(buff,0,hasRead)); }
			 */
			raf.write("zhongjian".getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("resource")
	@Test
	public void demo2() throws IOException {
		RandomAccessFile raf = new RandomAccessFile("C:/test.txt", "rw");
		FileChannel channel = raf.getChannel();//获得一个channel
		ByteBuffer buffer = ByteBuffer.allocate(3);//分配一个最多能存储3个字节的buffer
		int bytesRead = channel.read(buffer);//往buffer里灌数据,还有buffer.put()可以做相同的事情
		while (bytesRead != -1) {//如果读到数据了
			//System.out.println("read:"+bytesRead);
			buffer.flip();//把buffer从写模式切换到读模式
			if(buffer.hasRemaining()) {//如果limit和position之间有数据
				System.out.print((char) buffer.get());
			}
			//buffer.clear();
			buffer.compact();
			//清空缓存，如果缓存中有剩余数据未被读取，那么数据将会丢失
			//如果Buffer中仍有未读的数据，且后续还需要这些数据，但是此时想要先先写些数据，那么使用compact()方法。
			//compact()方法将所有未读的数据拷贝到Buffer起始处。然后将position设到最后一个未读元素正后面。
			//limit属性依然像clear()方法一样，设置成capacity。现在Buffer准备好写数据了，但是不会覆盖未读的数据。
			bytesRead = channel.read(buffer);
		}
		raf.close();
	}
}
