/*
 * Released to the public domain.
 */
package de.metaphisto;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * This is a sample for an application that allocates memory
 */
public class Main {

    public static void main(String... args) throws InterruptedException, IOException {
        new Main().start();
    }

    private void start() throws InterruptedException, IOException {
        for (int i = 0; i < 10000; i++) {
            System.out.println(i);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(10);
            Thread.sleep(5000);

            try (RandomAccessFile tempFile = new RandomAccessFile("temp.txt", "rw")) {
                for (byte j = 0; j < 10; j++) {
                    tempFile.write(j);
                }
            }

            try (RandomAccessFile tempFile = new RandomAccessFile("temp.txt", "r")) {
                FileChannel channel = tempFile.getChannel();
                MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
                byte[] content = new byte[byteBuffer.capacity()];
                map.get(content);
                System.out.print("Reading via memory-mapped buffer: ");
                for (byte b : content) {
                    System.out.print(b);
                }
                System.out.println();

            }

            File tempFile = new File("temp.txt");
            tempFile.delete();
        }
    }
}
