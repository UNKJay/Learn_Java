package hust.cs.javacourse.homework8.p2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 一个线程安全同步队列,模拟多线程环境下的生产者消费者机制
 * 一个生产者线程通过 produce 方法向队列里产生元素
 * 一个消费者线程通过 consume 方法从队列里消费元素
 * @param <T> 元素类型
 */
public class SyncQueue2<T> {
    /**
     * 保存队列元素
     */
    private ArrayList<T> list = new ArrayList<>();
    private static Lock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();

    /**
     * 生产数据
     * @param elements 生产出的元素列表,需要将该列表元素放入队列
     * @throws InterruptedException
     */
    public void produce(List<T> elements) {
        lock.lock();
        list.addAll(elements);
        System.out.println("Produce elements:" + elements.toString() );
        condition.signalAll();
        lock.unlock();
    }
    /**
     * 消费数据
     * @return 从队列中取出的数据
     * @throws InterruptedException
     */
    public List<T> consume(){
        List<T> conList = null;
        lock.lock();
        try {
            while (list.isEmpty()) {
                condition.await();
            }
            conList = new ArrayList<>(list);
            list.clear();
            System.out.println("Consume elements: " + conList.toString());
            condition.signalAll();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            lock.unlock();
        }
        return conList;
    }
}
