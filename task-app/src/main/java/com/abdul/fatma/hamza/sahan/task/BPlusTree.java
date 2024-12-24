/**
 * @package com.abdul.fatma.hamza.sahan.task
 * @brief Package that handles task-related functionality for the project.
 *
 * This package includes classes and utilities related to tasks, deadlines, and scheduling.
 * It leverages Java's standard utility classes to manage data structures, handle user input, and
 * support various task management operations such as sorting, searching, and inserting tasks.
 */

/**
 * @import java.util.*
 * @brief Imports all classes from the java.util package.
 *
 * The java.util package provides a collection of classes for handling data structures like
 * lists, sets, and maps, as well as utilities for sorting, searching, and managing data.
 * This package is commonly used in tasks involving data manipulation.
 */

/**
 * @import java.util.Scanner
 * @brief Imports the Scanner class for user input handling.
 *
 * The Scanner class is used to read input from various input sources such as the keyboard,
 * files, or other streams. It is commonly used for reading user input in console-based
 * applications. In this case, it is used to handle input for task creation, deadline assignment, etc.
 */

package com.abdul.fatma.hamza.sahan.task;
import java.util.*;
import java.util.Scanner;

/**
 * @class BPlusTree
 * @brief A class representing a BPlus Tree, a self-balancing tree data structure used for storing sorted data.
 *
 * The `BPlusTree` class allows you to insert tasks based on deadlines, search for tasks within a date range,
 * and view the results. This implementation of BPlus Tree allows efficient searching and insertion.
 *
 * @note The BPlus Tree uses `ScheduledTask` objects as values and stores them in leaf nodes. It supports insertion,
 *       searching within date ranges, and viewing tasks in a specified date range.
 */
public class BPlusTree {   /** @brief The root of the BPlus Tree */
BPlusTreeNode root;

    /**
     * @brief Constructor for BPlusTree
     *
     * Initializes the BPlus Tree with a root node. The root node is assumed to be a leaf node initially.
     */
    public BPlusTree() {
        this.root = new BPlusTreeNode(true);  // Root is initialized as a leaf node
    }

    /**
     * @brief Converts a date into a unique integer key.
     *
     * The date is represented as an integer in the format YYYYMMDD, where:
     * - Year is multiplied by 10000
     * - Month is multiplied by 100
     * - Day is the least significant part of the key
     *
     * @param day The day of the deadline.
     * @param month The month of the deadline.
     * @param year The year of the deadline.
     *
     * @return A unique integer representing the date.
     */
    public int getDateKey(int day, int month, int year) {
        return year * 10000 + month * 100 + day;
    }

    /**
     * @brief Inserts a task in a leaf node.
     *
     * This method inserts the task in the appropriate position in the leaf node based on the key.
     *
     * @param leaf The leaf node where the task will be inserted.
     * @param key The key representing the task's deadline.
     * @param task The task to be inserted.
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
     * @brief Inserts a task into the BPlus Tree.
     *
     * If the root node has space, the task is inserted directly in the root. If the root is full,
     * node splitting logic is required (not yet implemented).
     *
     * @param task The task to be inserted into the tree.
     */
    public void insertInBPlusTree(ScheduledTask task) {
        int key = getDateKey(task.getDay(), task.getMonth(), task.getYear());
        BPlusTreeNode root = this.root;

        if (root.keys.size() < 3) { // Assuming a max of 3 keys per node for simplicity
            insertInLeaf(root, key, task);
        } else {
            System.out.println("Node splitting required. Implement split logic here.");
        }
    }

    /**
     * @brief Recursively searches for tasks within a specified date range in the BPlus Tree.
     *
     * This method searches the tree nodes to find tasks within the given date range (startKey to endKey).
     * It prints each task's name and deadline within the range.
     *
     * @param node The current node being searched.
     * @param startKey The starting date key.
     * @param endKey The ending date key.
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
     * @brief Prompts the user to enter a date range and then searches for tasks within that range.
     *
     * This method reads the start and end dates from the user and calls the `searchInDateRange` method to
     * display all tasks within the specified range.
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