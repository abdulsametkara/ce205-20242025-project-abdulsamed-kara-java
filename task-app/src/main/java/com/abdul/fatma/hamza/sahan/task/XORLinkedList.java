/**
 * @file XORLinkedList.java
 * @brief An XOR Linked List implementation for managing tasks.
 *
 * This class implements an XOR Linked List for efficient memory usage 
 * while managing `TaskInfo` objects. It supports task addition, navigation,
 * and loading tasks from a file.
 *
 * @version 1.0
 * @date 2024-12-24
 * @author User
 */

package com.abdul.fatma.hamza.sahan.task;

import java.io.*;
import java.util.Scanner;

/**
 * @class XORLinkedList
 * @brief A memory-efficient XOR Linked List for task management.
 *
 * The `XORLinkedList` class uses XOR pointers to manage tasks in a doubly linked list.
 * It supports adding tasks, loading tasks from a file, and navigating through the list.
 */
public class XORLinkedList {

    /** @brief The head node of the XOR Linked List. */
    private XORNode xorHead = null;

    /** @brief The tail node of the XOR Linked List. */
    private XORNode xorTail = null;

    /**
     * @brief XORs two node references.
     *
     * Performs an XOR operation on two `XORNode` references. This is used to 
     * simulate doubly linked list behavior using XOR pointers.
     *
     * @param a The first node.
     * @param b The second node.
     * @return The result of XORing the two node pointers.
     */
    private XORNode xor(XORNode a, XORNode b) {
        return (XORNode) (a == null ? b : b == null ? a : new XORNode(a.getTaskInfo()));
    }

    /**
     * @brief Adds a task to the XOR Linked List.
     *
     * Creates a new node with the given `TaskInfo` and adds it to the end 
     * of the XOR Linked List.
     *
     * @param task The task to add to the list.
     */
    public void addTaskToXORList(TaskInfo task) {
        XORNode newNode = new XORNode(task);
        if (xorHead == null) {
            xorHead = xorTail = newNode;
        } else {
            xorTail.xorPtr = xor(xorTail.xorPtr, newNode);
            newNode.xorPtr = xorTail;
            xorTail = newNode;
        }
    }

    /**
     * @brief Loads tasks into the XOR Linked List from a file.
     *
     * Reads tasks from a binary file and adds them to the XOR Linked List.
     *
     * @param filename The name of the file containing task data.
     */
    public void loadTasksToXORList(String filename) {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(filename))) {
            int taskCount = dis.readInt();
            System.out.println("Number of tasks to load: " + taskCount);

            for (int i = 0; i < taskCount; i++) {
                TaskInfo task = new TaskInfo();
                task.setId(dis.readInt());
                task.setName(dis.readUTF());
                task.setDescription(dis.readUTF());
                task.setCategory(dis.readUTF());
                task.setDueDate(dis.readUTF());

                int dependencyCount = dis.readInt();
                task.setDependencyCount(dependencyCount);
                for (int j = 0; j < dependencyCount; j++) {
                    task.getDependencies()[j] = dis.readInt();
                }

                addTaskToXORList(task);
            }
            System.out.println("Tasks loaded into XOR Linked List successfully!");
        } catch (EOFException e) {
            System.out.println("Reached end of file unexpectedly: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
    }

    /**
     * @brief Navigates through the XOR Linked List.
     *
     * Allows the user to navigate the tasks in the XOR Linked List interactively.
     * Users can move forward, backward, or exit navigation.
     */
    public void navigateXORList() {
        XORNode current = xorHead;
        XORNode prev = null;
        XORNode next;

        if (current == null) {
            System.out.println("No tasks found in the list.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("ID: " + current.task.getId());
            System.out.println("Name: " + current.task.getName());
            System.out.println("Description: " + current.task.getDescription());
            System.out.println("Category: " + current.task.getCategory());
            System.out.println("Due Date: " + current.task.getDueDate());
            System.out.println("---------------------------");

            System.out.println("\n1. Next\n2. Previous\n0. Exit");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();

            if (choice == 1) {
                next = xor(prev, current.xorPtr);
                prev = current;
                current = next;
            } else if (choice == 2) {
                next = xor(current.xorPtr, prev);
                prev = current;
                current = next;
            }

            if (current == null) {
                System.out.println("No more tasks in this direction.");
                break;
            }
        } while (choice != 0);

        System.out.println("Exiting navigation.");
    }
}
