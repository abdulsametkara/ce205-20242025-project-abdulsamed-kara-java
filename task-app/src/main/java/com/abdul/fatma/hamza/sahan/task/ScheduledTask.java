/**
 * @file ScheduledTask.java
 * @brief Represents a scheduled task with a specific date.
 *
 * This class encapsulates the details of a scheduled task, including its name 
 * and the date (day, month, year) on which it is scheduled.
 *
 * @version 1.0
 * @date 2024-12-24
 * @author User
 */

package com.abdul.fatma.hamza.sahan.task;

/**
 * @class ScheduledTask
 * @brief A class representing a task scheduled on a specific date.
 *
 * This class holds information about a scheduled task, including its name and 
 * scheduled date (day, month, year). It provides getter methods for accessing 
 * task properties.
 */
public class ScheduledTask {

    /** @brief The name or title of the scheduled task. */
    private String name;

    /** @brief The day of the scheduled task (1-31). */
    private int day;

    /** @brief The month of the scheduled task (1-12). */
    private int month;

    /** @brief The year of the scheduled task. */
    private int year;

    /**
     * @brief Constructs a `ScheduledTask` with the given details.
     *
     * @param name The name or title of the task.
     * @param day The day of the scheduled task (1-31).
     * @param month The month of the scheduled task (1-12).
     * @param year The year of the scheduled task.
     */
    public ScheduledTask(String name, int day, int month, int year) {
        this.name = name;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    /**
     * @brief Retrieves the name of the scheduled task.
     *
     * @return The name of the task.
     */
    public String getName() {
        return name;
    }

    /**
     * @brief Retrieves the day of the scheduled task.
     *
     * @return The day of the task (1-31).
     */
    public int getDay() {
        return day;
    }

    /**
     * @brief Retrieves the month of the scheduled task.
     *
     * @return The month of the task (1-12).
     */
    public int getMonth() {
        return month;
    }

    /**
     * @brief Retrieves the year of the scheduled task.
     *
     * @return The year of the task.
     */
    public int getYear() {
        return year;
    }
}
