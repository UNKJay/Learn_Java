package hust.cs.javacourse.homework8.p3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReusableThread extends Thread{
    private Runnable runTask = null;
    //保存接受的线程任务
    private static Lock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();


//只定义不带参数的构造函数
    public ReusableThread(){
        super();
    }
    /**
     * 覆盖 Thread 类的 run 方法
     */
    @Override
    public void run() {
        lock.lock();
        try {
            while (true) {
                while (runTask == null) {
                    condition.await();
                }
                runTask.run();
                runTask=null;
                condition.signalAll();
            }
        } catch(InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    /**
     * 提交新的任务
     * @param task 要提交的任务
     */
    public void submit(Runnable task){
        lock.lock();
        try {
            while (runTask != null) {
                condition.await();
            }
            runTask = task;
            condition.signalAll();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
