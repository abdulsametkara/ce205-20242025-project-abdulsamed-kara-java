package com.abdul.fatma.hamza.sahan.task;

public class XORNode {
    public TaskInfo task;
    public XORNode xorPtr;  // XOR Pointer

    public XORNode(TaskInfo task) {
        this.task = task;
        this.xorPtr = null;  // Initially no XORed pointer
    }
}
