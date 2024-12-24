/**
 * @file DoubleLinkedList.java
 * @brief Represents a doubly linked list for managing tasks.
 *
 * This class provides a doubly linked list structure for storing and managing tasks.
 * It supports adding tasks to the end of the list and maintains references to both
 * the head and tail nodes.
 *
 * @author User
 * @date 2024-12-24
 * @version 1.0
 */

package com.abdul.fatma.hamza.sahan.task;

/**
 * @class DoubleLinkedList
 * @brief A doubly linked list for task management.
 *
 * This class manages a list of tasks using a doubly linked list structure.
 * It supports efficient addition of tasks and navigation in both directions
 * through the list.
 */
public class DoubleLinkedList {

    /**
     * @brief Reference to the head node of the doubly linked list.
     *
     * Points to the first node in the list.
     */
    TaskNode head;

    /**
     * @brief Reference to the tail node of the doubly linked list.
     *
     * Points to the last node in the list.
     */
    TaskNode tail;

    /**
     * @brief Initializes an empty doubly linked list.
     *
     * Sets the head and tail pointers to null.
     */
    public void TaskDoublyLinkedList() {
        this.head = null;
        this.tail = null;
    }

    /**
     * @brief Adds a task to the end of the doubly linked list.
     *
     * Creates a new node with the provided task information and appends it
     * to the end of the list. Updates head and tail pointers as necessary.
     *
     * @param task The task information to be added to the list.
     */
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
