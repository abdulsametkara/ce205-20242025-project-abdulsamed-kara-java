package com.abdul.fatma.hamza.sahan.task;
import java.io.*;
import java.util.Scanner;
import java.io.EOFException;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.FileInputStream;

public class XORLinkedList {
    private XORNode xorHead = null;  // XOR Linked List'in başı
    private XORNode xorTail = null;  // XOR Linked List'in sonu


    private XORNode xor(XORNode a, XORNode b) {
        // XOR mantığını uygula: İki node'un hafıza adresleri üzerinde XOR işlemi yapar
        return (XORNode) (a == null ? b : b == null ? a : new XORNode(a.getTaskInfo()));
    }



    public void addTaskToXORList(TaskInfo task) {
        XORNode newNode = new XORNode(task);
        if (xorHead == null) {
            xorHead = xorTail = newNode; // İlk düğüm ekleniyor
        } else {
            xorTail.xorPtr = xor(xorTail.xorPtr, newNode);
            newNode.xorPtr = xorTail;
            xorTail = newNode; // Yeni düğüm sona eklenir
        }
    }


    public void loadTasksToXORList(String filename) {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(filename))) {
            int taskCount = dis.readInt();  // Görev sayısını oku
            System.out.println("Number of tasks to load: " + taskCount);

            for (int i = 0; i < taskCount; i++) {
                TaskInfo task = new TaskInfo();
                task.setId(dis.readInt());                // ID
                task.setName(dis.readUTF());              // Name
                task.setDescription(dis.readUTF());       // Description
                task.setCategory(dis.readUTF());          // Category
                task.setDueDate(dis.readUTF());           // Due Date

                int dependencyCount = dis.readInt();      // Dependency Count
                task.setDependencyCount(dependencyCount);
                for (int j = 0; j < dependencyCount; j++) {
                    task.getDependencies()[j] = dis.readInt();
                }

                addTaskToXORList(task);  // Görevi XOR Linked List'e ekle
            }
            System.out.println("Tasks loaded into XOR Linked List successfully!");
        } catch (EOFException e) {
            System.out.println("Reached end of file unexpectedly: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
    }




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