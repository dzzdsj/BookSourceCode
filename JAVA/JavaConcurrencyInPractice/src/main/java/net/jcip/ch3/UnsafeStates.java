package net.jcip.ch3;

/**
 * UnsafeStates
 * <p/>
 * Allowing internal mutable state to escape
 *
 * @author Brian Goetz and Tim Peierls
 */
class UnsafeStates {
    private String[] states = new String[]{
        "AK", "AL" /*...*/
    };
//从非私有方法返回了数组对象的引用，因此原来私有的数组对象states及其内部的其他对象都被不经意间发布了。
//    这导致持有该引用的任何代码都可以遍历这个集合了。
    public String[] getStates() {
        return states;
    }
}
