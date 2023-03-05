package net.jcip.ch3;

/**
 * NoVisibility
 * <p/>
 * Sharing variables without synchronization
 *
 * @author Brian Goetz and Tim Peierls
 */

public class NoVisibility {
    private static boolean ready;
    private static int number;

    private static class ReaderThread extends Thread {
        public void run() {
            while (!ready)
                Thread.yield();
            System.out.println(number);
        }
    }

    //无法保证main线程写入内存的值对读线程readerthread是可见的
    public static void main(String[] args) {
        new ReaderThread().start();
         number = 42;
        ready = true;
    }
}
