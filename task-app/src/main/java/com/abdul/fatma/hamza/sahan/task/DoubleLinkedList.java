package com.abdul.fatma.hamza.sahan.task;

public class DoubleLinkedList {

    TaskNode head;
    TaskNode tail;

    public void TaskDoublyLinkedList() {
        this.head = null;
        this.tail = null;
    }

    // Görevi çift bağlı listeye ekleme (listenin sonuna ekler)
    public void addTaskToLinkedList(TaskInfo task) {
        TaskNode newNode = new TaskNode(task);

        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }
}
