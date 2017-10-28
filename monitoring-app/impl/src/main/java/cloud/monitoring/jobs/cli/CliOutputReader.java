package cloud.monitoring.jobs.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Roman on 28.10.2017 12:49.
 */
public class CliOutputReader implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(CliOutputReader.class);

    public static final int DEFAULT_BUFFER_SIZE = 1024;

    private final InputStream inputStream;
    private final StringBuffer out;
    private final CountDownLatch readingFinishedLatch;
    private final String delim;
    private final byte[] buffer;
    private final int bufferSize;

    public CliOutputReader(InputStream inputStream, StringBuffer out, CountDownLatch readingFinishedLatch, String delim, byte[] buffer, int bufferSize) {
        this.inputStream = inputStream;
        this.out = out;
        this.readingFinishedLatch = readingFinishedLatch;
        this.delim = delim;
        this.buffer = buffer;
        this.bufferSize = bufferSize;
    }

    public CliOutputReader(InputStream in, StringBuffer out, CountDownLatch latch, String delim) {
        this.inputStream = in;
        this.readingFinishedLatch = latch;
        this.out = out;
        this.delim = delim;
        this.bufferSize = DEFAULT_BUFFER_SIZE;
        this.buffer = new byte[bufferSize];
    }

    public CliOutputReader(InputStream in, StringBuffer out, CountDownLatch latch, int bufferSize, String delim) {
        this.out = out;
        this.readingFinishedLatch = latch;
        this.inputStream = in;
        this.delim = delim;
        this.bufferSize = bufferSize;
        this.buffer = new byte[bufferSize];
    }

    @Override
    public void run() {
        LOGGER.trace("[READER - {}] start reading input stream", Thread.currentThread().getName());
        try {
            int totalBytesRead = 0;
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer, 0, bufferSize)) != -1) {
                totalBytesRead += bytesRead;

                String chunk = new String(buffer, 0, bytesRead);
                out.append(chunk);

                if (LOGGER.isTraceEnabled()) {
                    LOGGER.trace("[READER - {}] read bytes {}", Thread.currentThread().getName(), bytesRead);
                }

                if (delim != null && chunk.contains(delim)) {
                    LOGGER.trace("End of command output detected {}. Finish reading input stream", delim);
                    break;
                }
            }

            readingFinishedLatch.countDown();

            LOGGER.debug("[READER - {}] finished reading input stream. total bytes read {}", Thread.currentThread().getName(),
                    totalBytesRead);

        } catch (InterruptedIOException e) {
            LOGGER.info("Reading input stream was interrupted");
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            LOGGER.warn("IOException while reading output", e);
        }
        LOGGER.trace("[READER - {}] exit", Thread.currentThread().getName());

    }
}
