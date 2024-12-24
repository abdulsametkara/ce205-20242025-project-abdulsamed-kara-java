/**
 * @file TaskNode.java
 * @brief Represents a node in a doubly linked list of tasks.
 *
 * This class defines a node structure for use in a doubly linked list, where each node
 * contains task information (`TaskInfo`) and pointers to the next and previous nodes.
 *
 * @version 1.0
 * @date 2024-12-24
 * @author User
 */

package com.abdul.fatma.hamza.sahan.task;

/**
 * @class TaskNode
 * @brief A node in a doubly linked list for managing tasks.
 *
 * Each `TaskNode` contains a reference to a task (`TaskInfo`) and pointers to both 
 * the next and previous nodes in the list. This structure supports efficient traversal
 * in both directions.
 */
public class TaskNode {

    /** @brief The task information stored in this node. */
    public TaskInfo task;

    /** @brief Pointer to the next node in the doubly linked list. */
    public TaskNode next;

    /** @brief Pointer to the previous node in the doubly linked list. */
    public TaskNode prev;

    /**
     * @brief Constructs a `TaskNode` with the given task.
     *
     * Initializes a node with the provided task and sets `next` and `prev` pointers to `null`.
     *
     * @param task The task information to store in this node.
     */
    public TaskNode(TaskInfo task) {
        this.task = task;
        this.next = null;
        this.prev = null;
    }
}
