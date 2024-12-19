package com.abdul.fatma.hamza.sahan.task;

public class XORNode {
    TaskInfo task;  // Task verisi
    XORNode xorPtr; // XOR pointer

    // Yapıcı metot: TaskInfo ile yeni bir XORNode oluşturur
    public XORNode(TaskInfo task) {
        this.task = task;
        this.xorPtr = null;
    }

    // Task verisini döndürmek için bir getter
    public TaskInfo getTaskInfo() {
        return this.task;
    }
}