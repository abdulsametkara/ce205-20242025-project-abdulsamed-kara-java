/**
 * @file Assignment.java
 * @brief Represents a task assignment with a deadline.
 *
 * This class encapsulates information about a task's deadline, including day, month,
 * and year. It provides mechanisms for managing deadlines, serialization, and
 * priority-based organization using a `PriorityQueue`.
 *
 * @version 1.0
 * @date 2024-12-24
 * @author User
 */

package com.abdul.fatma.hamza.sahan.task;

import java.io.*;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * @class Assignment
 * @brief Represents an assignment with a deadline.
 *
 * The `Assignment` class provides functionality to manage tasks with deadlines, including
 * comparisons for priority-based organization, serialization for persistence, and user-driven
 * deadline assignment.
 *
 * @note Implements `Comparable` for sorting and `Serializable` for file operations.
 */
public class Assignment implements Comparable<Assignment>, Serializable {

    /** @brief Serial version UID for serialization compatibility. */
    private static final long serialVersionUID = 1L;

    /** @brief Name of the task. */
    private String name;

    /** @brief Day of the deadline (1-31). */
    private int day;

    /** @brief Month of the deadline (1-12). */
    private int month;

    /** @brief Year of the deadline. */
    private int year;

    /**
     * @brief Constructor to initialize a new assignment with the task name and deadline.
     *
     * @param name Name of the task.
     * @param day Day of the deadline.
     * @param month Month of the deadline.
     * @param year Year of the deadline.
     */
    public Assignment(String name, int day, int month, int year) {
        this.name = name;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    // --- Getters and Setters ---

    /**
     * @brief Retrieves the task name.
     * @return The name of the task.
     */
    public String getName() {
        return name;
    }

    /**
     * @brief Sets the task name.
     * @param name The new task name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @brief Retrieves the day of the deadline.
     * @return The day of the deadline.
     */
    public int getDay() {
        return day;
    }

    /**
     * @brief Sets the day of the deadline.
     * @param day The new day of the deadline.
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     * @brief Retrieves the month of the deadline.
     * @return The month of the deadline.
     */
    public int getMonth() {
        return month;
    }

    /**
     * @brief Sets the month of the deadline.
     * @param month The new month of the deadline.
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * @brief Retrieves the year of the deadline.
     * @return The year of the deadline.
     */
    public int getYear() {
        return year;
    }

    /**
     * @brief Sets the year of the deadline.
     * @param year The new year of the deadline.
     */
    public void setYear(int year) {
        this.year = year;
    }

    // --- Comparable Implementation ---

    /**
     * @brief Compares this assignment with another assignment based on the deadline.
     *
     * The comparison is done first by year, then by month, and finally by day.
     *
     * @param other The other assignment to compare with.
     * @return A negative integer, zero, or a positive integer if this assignment
     *         is less than, equal to, or greater than the other assignment.
     */
    @Override
    public int compareTo(Assignment other) {
        if (this.year != other.year) return this.year - other.year;
        if (this.month != other.month) return this.month - other.month;
        return this.day - other.day;
    }

    // --- File Operations ---

    /**
     * @brief Writes the assignment object to a file for persistence.
     *
     * Serializes the current assignment object and writes it to the specified file.
     *
     * @param fileName The name of the file to write the object to.
     */
    public void writeToFile(String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName, true))) {
            oos.writeObject(this);
        } catch (IOException e) {
            System.out.println("Error: Could not save deadline to file. " + e.getMessage());
        }
    }

    // --- Static Method ---

    /**
     * @brief Prompts the user to assign a deadline to a task and stores it in a priority queue.
     *
     * This method interacts with the user to get task details and deadline information.
     * It validates the input and adds the task to a `PriorityQueue`.
     *
     * @param deadlineHeap A `PriorityQueue` to store assignments sorted by deadline.
     */
    public static void assign_deadline(PriorityQueue<Assignment> deadlineHeap) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Task Name: ");
        String taskName = scanner.nextLine();

        System.out.print("Enter Deadline (day month year): ");
        int day, month, year;
        try {
            day = scanner.nextInt();
            month = scanner.nextInt();
            year = scanner.nextInt();

            if (day < 1 || day > 31 || month < 1 || month > 12 || year < 1900) {
                System.out.println("Invalid date! Please enter a valid date.");
                return;
            }
        } catch (Exception e) {
            System.out.println("Invalid input! Please try again.");
            scanner.nextLine(); // Clear buffer
            return;
        }

        // Create a new assignment and add it to the priority queue
        Assignment assignment = new Assignment(taskName, day, month, year);
        deadlineHeap.add(assignment);
        assignment.writeToFile("deadlines.bin");

        System.out.println("Deadline assigned and saved successfully!");
    }
}
