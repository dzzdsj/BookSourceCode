package net.jcip.ch2;

import java.math.BigInteger;
import javax.servlet.*;

import net.jcip.annotations.*;

/**
 * SynchronizedFactorizer
 *
 * Servlet that caches last result, but with unnacceptably poor concurrency
 *
 * @author Brian Goetz and Tim Peierls
 */
//如果使用同步来协调对某个变量的访问，那么在访问这个变量的所有地方（而不仅仅是写入时），都要使用同步，且必须是同一把锁。
@ThreadSafe
public class SynchronizedFactorizer extends GenericServlet implements Servlet {
//    注意：这两个内置状态变量需要由同一个锁保护。在这里是SynchronizedFactorizer对象的内置锁。
    @GuardedBy("this") private BigInteger lastNumber;
    @GuardedBy("this") private BigInteger[] lastFactors;
    //对整个方法体添加了同步锁，性能不好
    public synchronized void service(ServletRequest req,
                                     ServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        if (i.equals(lastNumber))
            encodeIntoResponse(resp, lastFactors);
        else {
            BigInteger[] factors = factor(i);
            lastNumber = i;
            lastFactors = factors;
            encodeIntoResponse(resp, factors);
        }
    }

    void encodeIntoResponse(ServletResponse resp, BigInteger[] factors) {
    }

    BigInteger extractFromRequest(ServletRequest req) {
        return new BigInteger("7");
    }

    BigInteger[] factor(BigInteger i) {
        // Doesn't really factor
        return new BigInteger[] { i };
    }
}

