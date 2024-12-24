/**
 * @file XORNode.java
 * @brief Represents a node in an XOR Linked List.
 *
 * This class defines a node used in an XOR Linked List. Each node contains task data 
 * (`TaskInfo`) and an XOR pointer (`xorPtr`) that encodes references to the previous 
 * and next nodes using a bitwise XOR operation.
 *
 * @version 1.0
 * @date 2024-12-24
 * @author User
 */

package com.abdul.fatma.hamza.sahan.task;

/**
 * @class XORNode
 * @brief A node in an XOR Linked List.
 *
 * The `XORNode` class represents a single node in an XOR Linked List. It stores a task (`TaskInfo`) 
 * and an XOR pointer (`xorPtr`) for efficient memory usage when managing doubly linked lists.
 */
public class XORNode {

    /** @brief Task information stored in this node. */
    TaskInfo task;

    /** @brief XOR pointer for managing links between previous and next nodes. */
    XORNode xorPtr;

    /**
     * @brief Constructs an `XORNode` with the given task.
     *
     * Initializes an XOR node with the provided task information. The XOR pointer is set to `null`.
     *
     * @param task The task information associated with this node.
     */
    public XORNode(TaskInfo task) {
        this.task = task;
        this.xorPtr = null;
    }

    /**
     * @brief Retrieves the task information from the node.
     *
     * @return The `TaskInfo` object stored in this node.
     */
    public TaskInfo getTaskInfo() {
        return this.task;
    }
}
