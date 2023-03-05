package net.jcip.ch1;

import net.jcip.annotations.*;

/**
 * UnsafeSequence
 *
 * @author Brian Goetz and Tim Peierls
 */

@NotThreadSafe
public class UnsafeSequence {
    private int value;

    /**
     * Returns a unique value.
     */
    //value++ 自增操作非线程安全
    public int getNext() {
        return value++;
    }
}
