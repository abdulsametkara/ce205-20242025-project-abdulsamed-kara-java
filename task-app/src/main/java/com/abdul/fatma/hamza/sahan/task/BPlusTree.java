package com.abdul.fatma.hamza.sahan.task;
import java.util.*;
import java.util.Scanner;


public class BPlusTree {    BPlusTreeNode root;

    public BPlusTree() {
        this.root = new BPlusTreeNode(true);
    }

    public int getDateKey(int day, int month, int year) {
        return year * 10000 + month * 100 + day;
    }

    public void insertInLeaf(BPlusTreeNode leaf, int key, ScheduledTask task) {
        int i = 0;
        while (i < leaf.keys.size() && leaf.keys.get(i) < key) {
            i++;
        }
        leaf.keys.add(i, key);
        leaf.tasks.add(i, task);
    }

    public void insertInBPlusTree(ScheduledTask task) {
        int key = getDateKey(task.getDay(), task.getMonth(), task.getYear());
        BPlusTreeNode root = this.root;

        if (root.keys.size() < 3) { // MAX_KEYS assumed to be 3 for simplicity
            insertInLeaf(root, key, task);
        } else {
            System.out.println("Node splitting required. Implement split logic here.");
        }
    }

    public void searchInDateRange(BPlusTreeNode node, int startKey, int endKey) {
        if (node == null) return;

        int i = 0;
        while (i < node.keys.size() && node.keys.get(i) < startKey) {
            i++;
        }

        if (node.isLeaf) {
            while (i < node.keys.size() && node.keys.get(i) <= endKey) {
                ScheduledTask task = node.tasks.get(i);
                System.out.printf("Task: %s, Deadline: %02d/%02d/%04d\n",
                        task.getName(),
                        task.getDay(),
                        task.getMonth(),
                        task.getYear());
                i++;
            }
            if (node.next != null && node.keys.get(node.keys.size() - 1) <= endKey) {
                searchInDateRange(node.next, startKey, endKey);
            }
        } else {
            while (i < node.children.size()) {
                searchInDateRange(node.children.get(i), startKey, endKey);
                i++;
            }
        }
    }

    public void viewDeadlinesInRange() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter start date (day month year): ");
        int startDay = scanner.nextInt();
        int startMonth = scanner.nextInt();
        int startYear = scanner.nextInt();

        System.out.print("Enter end date (day month year): ");
        int endDay = scanner.nextInt();
        int endMonth = scanner.nextInt();
        int endYear = scanner.nextInt();

        int startKey = getDateKey(startDay, startMonth, startYear);
        int endKey = getDateKey(endDay, endMonth, endYear);

        System.out.printf("\n--- Tasks between %02d/%02d/%04d and %02d/%02d/%04d ---\n",
                startDay, startMonth, startYear, endDay, endMonth, endYear);

        searchInDateRange(this.root, startKey, endKey);
    }
}