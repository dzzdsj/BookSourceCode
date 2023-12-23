package net.jcip.ch3;

import net.jcip.annotations.*;

/**
 * MutableInteger
 * <p/>
 * Non-thread-safe mutable integer holder
 *
 * @author Brian Goetz and Tim Peierls
 */

@NotThreadSafe
public class MutableInteger {
    private int value;
//由于读写未加同步，读线程可能得到的是一个失效（过去的）的值
//    仅对set方法进行同步是不够的，调用get方法的线程仍会看到失效值
    public int get() {
        return value;
    }

    public void set(int value) {
        this.value = value;
    }
}








