/**
 * @file taskApp.java
 * @brief Entry point for the Task Management Application.
 *
 * This class serves as the entry point for the task management system. It initializes
 * the necessary resources, such as input/output streams and the `Task` class, and
 * starts the main menu for user interaction.
 *
 * @version 1.0
 * @date 2024-12-24
 * @author User
 */

package com.abdul.fatma.hamza.sahan.task;

import java.util.Scanner;

/**
 * @class taskApp
 * @brief The main application class for task management.
 *
 * This class initializes the application by creating an instance of the `Task` class
 * and invoking its main menu. It sets up file paths, input sources, and output streams.
 */
public class taskApp {

    /**
     * @brief The entry point for the Task Management Application.
     *
     * Initializes the necessary resources, creates an instance of the `Task` class,
     * and starts the main menu interface for managing tasks.
     *
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        /** @brief Path to the user data file. */
        String pathFileUsers = "users.bin"; // File to store user data

        /** @brief Scanner for user input. */
        Scanner inputScanner = new Scanner(System.in);

        /** @brief Create an instance of the Task class. */
        Task task = new Task(inputScanner, System.out);

        /** @brief Start the main menu of the Task class. */
        task.mainMenu(pathFileUsers);  // Calls the mainMenu method from Task.java
    }
}
