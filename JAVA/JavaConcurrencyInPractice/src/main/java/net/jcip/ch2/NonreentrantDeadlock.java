package net.jcip.ch2;

/**
 * NonreentrantDeadlock
 * <p/>
 * Code that would deadlock if intrinsic locks were not reentrant
 *
 * @author Brian Goetz and Tim Peierls
 */
//内置锁是可重入的。这个特性简化了开发复杂度。不然以下代码将产生死锁。
class Widget {
    public synchronized void doSomething() {
    }
}

class LoggingWidget extends Widget {
    public synchronized void doSomething() {
        System.out.println(toString() + ": calling doSomething");
//        如果内置锁不可重入，那么这里调用父类同步方法时将死锁。因为这个锁已经被持有了。
        super.doSomething();
    }
}
