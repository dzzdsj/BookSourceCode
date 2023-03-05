package net.jcip.ch3;

/**
 * SafeListener
 * <p/>
 * Using a factory method to prevent the this reference from escaping during construction
 *
 * @author Brian Goetz and Tim Peierls
 */
public class SafeListener {
    private final EventListener listener;
//    如果需要在构造函数中完成一些对象的构建，为了避免在正确构建完成前，其他线程能够看到，
    //需要将构造函数私有化，并对外提供一个初始化方法newInstance
    private SafeListener() {
        listener = new EventListener() {
            public void onEvent(Event e) {
                doSomething(e);
            }
        };
    }

    public static SafeListener newInstance(EventSource source) {
        SafeListener safe = new SafeListener();
        source.registerListener(safe.listener);
        return safe;
    }

    void doSomething(Event e) {
    }


    interface EventSource {
        void registerListener(EventListener e);
    }

    interface EventListener {
        void onEvent(Event e);
    }

    interface Event {
    }
}

