package net.jcip.ch3;

/**
 * ThisEscape
 * <p/>
 * Implicitly allowing the this reference to escape
 *
 * @author Brian Goetz and Tim Peierls
 */
//发布内部类的实例时，也隐含地发布了外部类实例
public class ThisEscape {
    public ThisEscape(EventSource source) {
//        不要在构造过程中使this引用逸出（有可能发布一个构造未完成的对象）
//        仅当对象的构造函数返回时，对象才处于可预测和一致的状态
        source.registerListener(new EventListener() {
            public void onEvent(Event e) {
                doSomething(e);
            }
        });
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

