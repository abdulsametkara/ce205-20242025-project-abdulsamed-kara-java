package com.abdul.fatma.hamza.sahan.task;
import java.util.*;

public class BPlusTreeNode {
    boolean isLeaf;
    List<Integer> keys;
    List<ScheduledTask> tasks;
    List<BPlusTreeNode> children;
    BPlusTreeNode next;

    public BPlusTreeNode(boolean isLeaf) {
        this.isLeaf = isLeaf;
        this.keys = new ArrayList<>();
        this.tasks = new ArrayList<>();
        this.children = new ArrayList<>();
        this.next = null;
    }
}
