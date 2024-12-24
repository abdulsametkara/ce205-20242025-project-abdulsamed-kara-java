/**
 * @file TaskInfo.java
 * @brief Represents detailed information about a task.
 *
 * This class encapsulates the properties and operations of a task, including
 * its metadata, dependencies, importance level, and file I/O operations
 * for persistence.
 *
 * @version 1.0
 * @date 2024-12-24
 * @author User
 */

package com.abdul.fatma.hamza.sahan.task;

import java.io.*;

/**
 * @class TaskInfo
 * @brief A class representing detailed information about a task.
 *
 * The `TaskInfo` class contains essential attributes of a task, such as its ID, name,
 * description, category, due date, dependencies, and importance level. It also
 * supports reading from and writing to a file using `RandomAccessFile`.
 */
public class TaskInfo {

    /** @brief Unique identifier for the task. */
    private int id;

    /** @brief Name of the task. */
    private String name;

    /** @brief Description of the task. */
    private String description;

    /** @brief Category of the task (e.g., Work, Personal). */
    private String category;

    /** @brief Due date of the task in string format (e.g., "2024-12-31"). */
    private String dueDate;

    /** @brief Number of dependencies associated with the task. */
    private int dependencyCount;

    /** @brief Array of dependency task IDs (up to 10 dependencies). */
    private int[] dependencies = new int[10];

    /**
     * @brief Importance level of the task.
     *
     * Valid values:
     * - 0: Unmarked
     * - 1: Low
     * - 2: Medium
     * - 3: High
     */
    private int importanceId = 0;

    // --- Getters and Setters ---

    /**
     * @brief Retrieves the task ID.
     * @return The task ID.
     */
    public int getId() { return id; }

    /**
     * @brief Sets the task ID.
     * @param id The unique identifier for the task.
     */
    public void setId(int id) { this.id = id; }

    /**
     * @brief Retrieves the task name.
     * @return The name of the task.
     */
    public String getName() { return name; }

    /**
     * @brief Sets the task name.
     * @param name The name of the task.
     */
    public void setName(String name) { this.name = name; }

    /**
     * @brief Retrieves the task description.
     * @return The description of the task.
     */
    public String getDescription() { return description; }

    /**
     * @brief Sets the task description.
     * @param description The description of the task.
     */
    public void setDescription(String description) { this.description = description; }

    /**
     * @brief Retrieves the task category.
     * @return The category of the task.
     */
    public String getCategory() { return category; }

    /**
     * @brief Sets the task category.
     * @param category The category of the task.
     */
    public void setCategory(String category) { this.category = category; }

    /**
     * @brief Retrieves the due date of the task.
     * @return The due date of the task.
     */
    public String getDueDate() { return dueDate; }

    /**
     * @brief Sets the due date of the task.
     * @param dueDate The due date of the task.
     */
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }

    /**
     * @brief Retrieves the number of dependencies.
     * @return The dependency count.
     */
    public int getDependencyCount() { return dependencyCount; }

    /**
     * @brief Sets the number of dependencies.
     * @param dependencyCount The number of dependencies.
     */
    public void setDependencyCount(int dependencyCount) { this.dependencyCount = dependencyCount; }

    /**
     * @brief Retrieves the dependency array.
     * @return An array of dependency IDs.
     */
    public int[] getDependencies() { return dependencies; }

    /**
     * @brief Sets the dependency array.
     * @param dependencies An array of dependency IDs.
     */
    public void setDependencies(int[] dependencies) { this.dependencies = dependencies; }

    /**
     * @brief Retrieves the importance ID.
     * @return The importance ID of the task.
     */
    public int getImportanceId() { return importanceId; }

    /**
     * @brief Sets the importance ID.
     *
     * Valid values:
     * - 1: Low
     * - 2: Medium
     * - 3: High
     *
     * @param importanceId The importance level to be set.
     * @throws IllegalArgumentException if the importance ID is invalid.
     */
    public void setImportanceId(int importanceId) {
        if (importanceId >= 1 && importanceId <= 3) {
            this.importanceId = importanceId;
        } else {
            throw new IllegalArgumentException("Invalid importance ID! Must be 1 (Low), 2 (Medium), or 3 (High).");
        }
    }

    // --- File Operations ---

    /**
     * @brief Writes task details to a file.
     *
     * Saves the task's attributes, including ID, name, description, category, due date,
     * dependency count, and dependency IDs into a binary file.
     *
     * @param raf The `RandomAccessFile` object to write data.
     * @throws IOException If an I/O error occurs.
     */
    public void writeToFile(RandomAccessFile raf) throws IOException {
        raf.writeInt(id);
        raf.writeUTF(name);
        raf.writeUTF(description);
        raf.writeUTF(category);
        raf.writeUTF(dueDate);
        raf.writeInt(dependencyCount);
        for (int i = 0; i < dependencyCount; i++) {
            raf.writeInt(dependencies[i]);
        }
    }

    /**
     * @brief Reads task details from a file.
     *
     * Populates the task's attributes by reading data from a binary file.
     *
     * @param raf The `RandomAccessFile` object to read data.
     * @throws IOException If an I/O error occurs.
     */
    public void readFromFile(RandomAccessFile raf) throws IOException {
        this.id = raf.readInt();
        this.name = raf.readUTF();
        this.description = raf.readUTF();
        this.category = raf.readUTF();
        this.dueDate = raf.readUTF();
        this.dependencyCount = raf.readInt();
        for (int i = 0; i < dependencyCount; i++) {
            this.dependencies[i] = raf.readInt();
        }
    }
}
