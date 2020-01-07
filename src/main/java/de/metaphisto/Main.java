/*
 * Released to the public domain.
 */
package de.metaphisto;

import java.nio.ByteBuffer;

/**
 * This is a sample for an application that allocates memory
 */
public class Main {

    public static void main(String... args) throws InterruptedException {
        new Main().start();
    }

    public void start() throws InterruptedException {
        for (int i = 0; i < 10000; i++) {
            System.out.println(i);
            ByteBuffer.allocateDirect(10);
            Thread.sleep(5000);
        }
    }
}
