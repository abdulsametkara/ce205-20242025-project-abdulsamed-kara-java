/**
 * @file BPlusTree.java
 * @brief Implements a B+ Tree for managing scheduled tasks.
 *
 * This class provides functionality for inserting, searching, and managing tasks
 * using a B+ Tree data structure. Tasks are sorted and efficiently retrieved based
 * on their deadlines.
 *
 * @version 1.0
 * @date 2024-12-24
 * @author User
 */

package com.abdul.fatma.hamza.sahan.task;

import java.util.*;
import java.util.Scanner;

/**
 * @class BPlusTree
 * @brief A self-balancing B+ Tree for managing scheduled tasks.
 *
 * The `BPlusTree` class provides efficient insertion, searching, and viewing of tasks
 * sorted by their deadlines. Tasks are stored in leaf nodes of the tree, and searching
 * supports range queries.
 */
public class BPlusTree {

    /** @brief The root node of the B+ Tree. */
    BPlusTreeNode root;

    /**
     * @brief Constructs a `BPlusTree` instance.
     *
     * Initializes the tree with an empty leaf node as the root.
     */
    public BPlusTree() {
        this.root = new BPlusTreeNode(true); // Root starts as a leaf node
    }

    /**
     * @brief Converts a date into a unique integer key.
     *
     * The date is represented as an integer in the format `YYYYMMDD`, enabling
     * quick comparison and sorting of deadlines.
     *
     * @param day The day of the deadline (1-31).
     * @param month The month of the deadline (1-12).
     * @param year The year of the deadline.
     * @return An integer key representing the date.
     */
    public int getDateKey(int day, int month, int year) {
        return year * 10000 + month * 100 + day;
    }

    /**
     * @brief Inserts a task into a leaf node.
     *
     * Adds a task at the correct position in the leaf node based on its deadline key.
     *
     * @param leaf The leaf node where the task will be inserted.
     * @param key The unique date key representing the task's deadline.
     * @param task The `ScheduledTask` object to be inserted.
     */
    public void insertInLeaf(BPlusTreeNode leaf, int key, ScheduledTask task) {
        int i = 0;
        while (i < leaf.keys.size() && leaf.keys.get(i) < key) {
            i++;
        }
        leaf.keys.add(i, key);
        leaf.tasks.add(i, task);
    }

    /**
     * @brief Inserts a task into the B+ Tree.
     *
     * Inserts a task into the root node if there is space. If the node is full,
     * further splitting logic would need to be implemented.
     *
     * @param task The `ScheduledTask` object to be inserted.
     */
    public void insertInBPlusTree(ScheduledTask task) {
        int key = getDateKey(task.getDay(), task.getMonth(), task.getYear());
        BPlusTreeNode root = this.root;

        if (root.keys.size() < 3) { // Simplified: Assumes max 3 keys per node
            insertInLeaf(root, key, task);
        } else {
            System.out.println("Node splitting required. Implement split logic here.");
        }
    }

    /**
     * @brief Searches for tasks within a date range in the B+ Tree.
     *
     * Traverses the tree nodes to find and print tasks whose deadlines fall
     * within the specified range.
     *
     * @param node The current node being searched.
     * @param startKey The start date key of the search range.
     * @param endKey The end date key of the search range.
     */
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

    /**
     * @brief Prompts the user to enter a date range and displays tasks within that range.
     *
     * Takes user input for a start and end date, converts them to date keys,
     * and searches the tree for tasks within the range.
     */
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
