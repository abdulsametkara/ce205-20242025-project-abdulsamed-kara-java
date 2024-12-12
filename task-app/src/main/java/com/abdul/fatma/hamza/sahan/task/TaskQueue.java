package com.abdul.fatma.hamza.sahan.task;
import java.util.LinkedList;


public class TaskQueue {
    private LinkedList<TaskInfo> queue = new LinkedList<>();

    // Kuyruğa görev ekleme
    public void enqueue(TaskInfo task) {
        queue.addLast(task);
    }

    // Kuyruktan görev çıkarma
    public TaskInfo dequeue() {
        if (queue.isEmpty()) {
            return null;  // Kuyruk boşsa null döner
        }
        return queue.removeFirst();
    }

    // Kuyruk boş mu kontrol etme
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    // Kuyruktaki görevlerin sayısını döner
    public int size() {
        return queue.size();
    }
}
