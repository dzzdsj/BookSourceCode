package net.jcip.ch2;

import java.math.BigInteger;
import javax.servlet.*;

import net.jcip.annotations.*;

/**
 * CachedFactorizer
 * <p/>
 * Servlet that caches its last request and result
 *
 * @author Brian Goetz and Tim Peierls
 */
//有不止一个状态变量，不能简单的使用原子变量解决竞态条件问题了
//获取与释放锁也会有开销，在尽量短的代码段添加同步，同时也不能拆分的太细（避免把原子操作拆分到多个同步代码块）
//执行需要长时间的操作时，一定不要持有锁
@ThreadSafe
public class CachedFactorizer extends GenericServlet implements Servlet {
    @GuardedBy("this") private BigInteger lastNumber;
    @GuardedBy("this") private BigInteger[] lastFactors;
//    由于已经使用了同步代码块，不再需要使用原子变量了。
    @GuardedBy("this") private long hits;
    @GuardedBy("this") private long cacheHits;

    public synchronized long getHits() {
        return hits;
    }

    public synchronized double getCacheHitRatio() {
        return (double) cacheHits / (double) hits;
    }

    public void service(ServletRequest req, ServletResponse resp) {
//   局部变量分配在栈上，不需要同步加锁
        BigInteger i = extractFromRequest(req);
        BigInteger[] factors = null;
//        拆分出的两个同步块持有的是同一个锁（对象内置锁）
        synchronized (this) {
            ++hits;
            if (i.equals(lastNumber)) {
                ++cacheHits;
                factors = lastFactors.clone();
            }
        }
        if (factors == null) {
//      耗时长的操作（计算密集型、IO阻塞型等）一定不要加锁，这个操作正好状态无关，给移出同步块。
            factors = factor(i);
            synchronized (this) {
                lastNumber = i;
                lastFactors = factors.clone();
            }
        }
        encodeIntoResponse(resp, factors);
    }

    void encodeIntoResponse(ServletResponse resp, BigInteger[] factors) {
    }

    BigInteger extractFromRequest(ServletRequest req) {
        return new BigInteger("7");
    }

    BigInteger[] factor(BigInteger i) {
        // Doesn't really factor
        return new BigInteger[]{i};
    }
}
