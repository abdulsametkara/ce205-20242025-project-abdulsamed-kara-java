package com.abdul.fatma.hamza.sahan.task;

import java.io.*;
import java.util.Scanner;
import java.util.PriorityQueue;

/**
 * @class Assignment
 * @brief This class represents an assignment with a deadline and provides methods to store and manage deadlines.
 *
 * The Assignment class is used to store task information including the task name and its deadline (day, month, and year).
 * The class implements the Comparable interface to allow sorting by deadline, and the Serializable interface for saving
 * and loading assignments from a file.
 *
 * @note The class supports serialization to enable saving the assignment to a file, and it can also be stored in a
 * PriorityQueue to keep deadlines sorted.
 */

public class Assignment implements Comparable<Assignment>, Serializable {

    /** @brief Serial version UID for serialization compatibility */
    private static final long serialVersionUID = 1L;

    /** @brief Name of the task */
    private String name;

    /** @brief Day of the deadline */
    private int day;

    /** @brief Month of the deadline */
    private int month;

    /** @brief Year of the deadline */
    private int year;

    /**
     * @brief Constructor to initialize a new assignment with the task name and deadline.
     *
     * @param name Name of the task
     * @param day Day of the deadline
     * @param month Month of the deadline
     * @param year Year of the deadline
     */

    public Assignment(String name, int day, int month, int year) {
        this.name = name;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    /**
     * @brief Gets the task name.
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
     * @brief Gets the day of the deadline.
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
     * @brief Gets the month of the deadline.
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
     * @brief Gets the year of the deadline.
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

    /**
     * @brief Compares this assignment with another assignment based on the deadline.
     *
     * The comparison is done first by year, then by month, and finally by day to establish the order.
     *
     * @param other The other assignment to compare with.
     * @return A negative integer, zero, or a positive integer if this assignment is less than, equal to, or greater than the other assignment.
     */

    @Override
    public int compareTo(Assignment other) {
        if (this.year != other.year) return this.year - other.year;
        if (this.month != other.month) return this.month - other.month;
        return this.day - other.day;
    }

    /**
     * @brief Writes the assignment object to a file for persistence.
     *
     * This method uses serialization to write the current assignment object to the specified file.
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

    /**
     * @brief Prompts the user to assign a deadline to a task and stores it in a priority queue.
     *
     * This method takes user input to define a task's name and deadline, and then adds it to the provided priority queue.
     * The deadline is also written to a file for persistence.
     *
     * @param deadlineHeap A priority queue to store assignments sorted by deadline.
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
            scanner.nextLine(); // Temizleme
            return;
        }

        // Yeni bir Assignment oluştur ve MinHeap'e ekle
        Assignment assignment = new Assignment(taskName, day, month, year);
        deadlineHeap.add(assignment); // PriorityQueue kullanıyoruz
        assignment.writeToFile("deadlines.bin");

        System.out.println("Deadline assigned and saved successfully!");
    }
}
