package hust.cs.javacourse.homework6;

import java.util.ArrayList;

public class Test {

    public static void main(String...args) {
        Task t1 = new A();
        Task t2 = new B();
        Task t3 = new C();
        TaskService taskService = new TaskServiceImpl();
        taskService.addTask(t1);
        taskService.addTask(t2);
        taskService.addTask(t3);
        taskService.executeTasks();
    }



}

class A implements Task {
    @Override
    public void execute() {
        System.out.println(" A's execute");
    }
}

class B implements Task {
    @Override
    public void execute() {
        System.out.println(" B's execute");
    }
}

class C implements Task {
    @Override
    public void execute() {
        System.out.println(" C's execute");
    }
}

class TaskServiceImpl implements TaskService {

    ArrayList<Task> tasks = new ArrayList<>();

    @Override
    public void executeTasks() {
        for (Task t:
                tasks) {
            t.execute();
        }
    }

    @Override
    public void addTask(Task t) {
        tasks.add(t);
    }
}