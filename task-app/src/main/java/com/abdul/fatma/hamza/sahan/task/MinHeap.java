/**
 * @file MinHeap.java
 * @brief A MinHeap implementation for managing assignments by deadlines.
 *
 * This class uses a priority queue to implement a MinHeap structure,
 * prioritizing assignments based on their deadlines (year, month, day).
 *
 * @version 1.0
 * @date 2024-12-24
 * @author User
 */

package com.abdul.fatma.hamza.sahan.task;

import java.util.PriorityQueue;

/**
 * @class MinHeap
 * @brief A MinHeap implementation for managing task assignments.
 *
 * This class provides a MinHeap structure using Java's `PriorityQueue`.
 * It ensures that assignments are ordered by their deadlines, with
 * the earliest deadline always at the top.
 */
public class MinHeap {

    /**
     * @brief Priority queue for storing assignments.
     *
     * The priority queue orders assignments by their deadlines
     * (year, month, day) in ascending order.
     */
    private PriorityQueue<Assignment> heap;

    /**
     * @brief Constructs an empty MinHeap.
     *
     * Initializes the priority queue and defines the comparator
     * for ordering assignments by their deadlines.
     */
    public MinHeap() {
        heap = new PriorityQueue<>((a, b) -> {
            // Sort by year, then month, then day
            if (a.getYear() != b.getYear()) return a.getYear() - b.getYear();
            if (a.getMonth() != b.getMonth()) return a.getMonth() - b.getMonth();
            return a.getDay() - b.getDay();
        });
    }

    /**
     * @brief Inserts a new assignment into the MinHeap.
     *
     * Adds the given assignment to the heap, maintaining the order
     * based on deadlines.
     *
     * @param assignment The assignment to be added to the heap.
     */
    public void insert(Assignment assignment) {
        heap.add(assignment);
    }

    /**
     * @brief Extracts the assignment with the earliest deadline.
     *
     * Removes and returns the assignment with the smallest (earliest) deadline.
     *
     * @return The assignment with the earliest deadline, or `null` if the heap is empty.
     */
    public Assignment extractMin() {
        return heap.poll();
    }

    /**
     * @brief Checks if the heap is empty.
     *
     * @return `true` if the heap contains no assignments, otherwise `false`.
     */
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    /**
     * @brief Prints all assignments in the heap.
     *
     * Displays the details of all assignments currently in the heap,
     * including their names and deadlines.
     */
    public void printHeap() {
        for (Assignment a : heap) {
            System.out.println("Task Name: " + a.getName() +
                    ", Deadline: " + a.getDay() + "/" + a.getMonth() + "/" + a.getYear());
        }
    }
}
