package com.abdul.fatma.hamza.sahan.task;
import java.util.PriorityQueue;

public class MinHeap {
    private PriorityQueue<Assignment> heap;

    public MinHeap() {
        heap = new PriorityQueue<>((a, b) -> {
            // Tarihlere göre sıralama (önce yıl, sonra ay, sonra gün)
            if (a.getYear() != b.getYear()) return a.getYear() - b.getYear();
            if (a.getMonth() != b.getMonth()) return a.getMonth() - b.getMonth();
            return a.getDay() - b.getDay();
        });
    }

    public void insert(Assignment assignment) {
        heap.add(assignment);
    }

    public Assignment extractMin() {
        return heap.poll(); // En küçük elemanı çıkar ve döner
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public void printHeap() {
        for (Assignment a : heap) {
            System.out.println("Task Name: " + a.getName() +
                    ", Deadline: " + a.getDay() + "/" + a.getMonth() + "/" + a.getYear());
        }
    }
}
