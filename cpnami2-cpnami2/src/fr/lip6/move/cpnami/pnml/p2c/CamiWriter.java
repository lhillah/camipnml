
package fr.lip6.move.cpnami.pnml.p2c;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;

import fr.lip6.move.pnml.framework.utils.logging.LogMaster;

public final class CamiWriter implements Runnable {
	private static final String STOP = "STOP";
	private static final int BUFFERSIZEKB = 4;
	public static final int BUFFERSIZE = BUFFERSIZEKB * 1024;
	private static final String FILE_ENCODING = "UTF-8";
	private BlockingQueue<String> queue;
	private final FileOutputStream fos;
	private final FileChannel fc;

	public CamiWriter(BlockingQueue<String> queue, String destination)
			throws FileNotFoundException {
		this.queue = queue;
		fos = new FileOutputStream(new File(destination));
		fc = fos.getChannel();
	}

	@Override
	public void run() {
		Logger log =  LogMaster.getLogger(CamiWriter.class.getCanonicalName() + "#"
				+ Thread.currentThread().getId());
		ByteBuffer bytebuf = ByteBuffer.allocateDirect(BUFFERSIZE);
		String msg;
		try {
			msg = queue.take();
			while (!STOP.equalsIgnoreCase(msg)) {
				bytebuf.put(msg.getBytes(Charset.forName(FILE_ENCODING)));
				bytebuf.flip();
				fc.write(bytebuf);
				bytebuf.clear();
				msg = queue.take();
			}
			fc.force(true);
			fc.close();
			fos.close();
		} catch (InterruptedException | IOException e) {
			log.error(e.getMessage());
		}

	}

}
