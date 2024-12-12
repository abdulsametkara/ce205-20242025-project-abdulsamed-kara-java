package com.abdul.fatma.hamza.sahan.task;

public class TaskNode {
    public TaskInfo task;
    public TaskNode next;
    public TaskNode prev;

    public TaskNode(TaskInfo task) {
        this.task = task;
        this.next = null;
        this.prev = null;
    }
}