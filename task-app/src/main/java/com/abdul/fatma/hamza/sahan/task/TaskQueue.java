/**
 * @file TaskQueue.java
 * @brief A queue implementation for managing tasks.
 *
 * This class provides a queue structure for storing and managing `TaskInfo` objects.
 * It supports standard queue operations such as enqueue, dequeue, checking emptiness,
 * and retrieving the size of the queue.
 *
 * @version 1.0
 * @date 2024-12-24
 * @author User
 */

package com.abdul.fatma.hamza.sahan.task;

import java.util.LinkedList;

/**
 * @class TaskQueue
 * @brief A queue structure for managing tasks.
 *
 * The `TaskQueue` class provides a queue-based approach to handle `TaskInfo` objects.
 * It allows tasks to be added (`enqueue`) and removed (`dequeue`) in a First-In-First-Out (FIFO) manner.
 */
public class TaskQueue {

    /** @brief A linked list serving as the underlying data structure for the queue. */
    private LinkedList<TaskInfo> queue = new LinkedList<>();

    /**
     * @brief Adds a task to the end of the queue.
     *
     * This method inserts a `TaskInfo` object at the end of the queue.
     *
     * @param task The task to be added to the queue.
     */
    public void enqueue(TaskInfo task) {
        queue.addLast(task);
    }

    /**
     * @brief Removes and retrieves the task at the front of the queue.
     *
     * If the queue is empty, it returns `null`.
     *
     * @return The task at the front of the queue, or `null` if the queue is empty.
     */
    public TaskInfo dequeue() {
        if (queue.isEmpty()) {
            return null; // Return null if the queue is empty
        }
        return queue.removeFirst();
    }

    /**
     * @brief Checks if the queue is empty.
     *
     * @return `true` if the queue is empty, otherwise `false`.
     */
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    /**
     * @brief Retrieves the number of tasks in the queue.
     *
     * @return The number of tasks currently in the queue.
     */
    public int size() {
        return queue.size();
    }
}
