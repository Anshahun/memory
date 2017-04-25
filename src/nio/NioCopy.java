package nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

import org.junit.Test;

public class NioCopy {
	
	private String filePath = "C:\\Users\\Administrator\\Desktop\\spy.properties";
	
	@SuppressWarnings("resource")
	@Test
	public void ioCopy() throws Exception{
		FileInputStream fis = new FileInputStream(filePath);
		FileOutputStream fos = new FileOutputStream(new File("C:\\Users\\Administrator\\Desktop\\io_cpy.txt"));
		int len = -1;
		byte[] buf = new byte[512];
		long start = System.currentTimeMillis();
		while((len=fis.read(buf))!=-1){
			fos.write(buf,0,len);
		}
		//fos.flush();
		long end = System.currentTimeMillis();
		System.out.println("io_copy 用时"+(end-start)+"毫秒");
		fos.close();
		fis.close();
	}
	
	@SuppressWarnings("resource")
	@Test
	public void nioCopy() throws Exception{
		FileInputStream fis = new FileInputStream(filePath);
		//FileOutputStream fos = new FileOutputStream(new File("C:\\Users\\Administrator\\Desktop\\nio_cpy.txt"));
		RandomAccessFile fos = new RandomAccessFile(new File("C:\\Users\\Administrator\\Desktop\\nio_cpy.txt"), "rw");
		FileChannel inChannel = fis.getChannel();
		System.out.println(inChannel.size());
		/*MappedByteBuffer inMap = inChannel.map(MapMode.READ_ONLY, 0, inChannel.size());
		FileChannel outChannel = fos.getChannel();
		MappedByteBuffer outMap = outChannel.map(MapMode.READ_WRITE, 0, inMap.capacity());
		long start = System.currentTimeMillis();
		ByteBuffer put = outMap.put(inMap);
		outMap.force();
		long end = System.currentTimeMillis();
		System.out.println("io_copy 用时"+(end-start)+"毫秒");
		fis.close();
		inChannel.close();
		fos.close();
		outChannel.close();*/
	}
	
	public static void main(String[] args) throws Exception {
		NioCopy nc = new NioCopy();
		nc.ioCopy();
		nc.nioCopy();
	}
}
