/**
 * @file NotificationManager.java
 * @brief Manages notification reminders and user notification settings.
 *
 * This class provides functionality to configure notification methods, handle user input,
 * and manage notification-related tasks using a sparse matrix structure.
 *
 * @version 1.0
 * @date 2024-12-24
 * @author User
 */

package com.abdul.fatma.hamza.sahan.task;

import java.util.*;
import java.io.PrintStream;

/**
 * @class NotificationManager
 * @brief Manages user notification preferences and settings.
 *
 * The `NotificationManager` class allows users to configure their notification preferences,
 * including options like SMS, Email, and Phone Call. It also manages notifications
 * using a sparse matrix to map tasks to their scheduled notifications.
 */
public class NotificationManager {

    /** @brief Input scanner for reading user input. */
    private final Scanner scanner;

    /** @brief Output stream for displaying messages. */
    private final PrintStream out;

    /**
     * @brief Sparse matrix for notification storage.
     *
     * Maps task IDs to dates and corresponding notification methods.
     * Structure: taskId -> date -> method.
     */
    private final Map<Integer, Map<Integer, Integer>> notificationMatrix = new HashMap<>();

    /**
     * @brief Notification method for reminders.
     *
     * Possible values:
     * - 0: None
     * - 1: SMS
     * - 2: Email
     * - 3: Phone Call
     */
    private int notificationMethod = 0;

    /** @brief Enables or disables test mode for unit testing purposes. */
    public boolean isTestMode = false;

    /**
     * @brief Constructor for NotificationManager.
     *
     * Initializes the `NotificationManager` with an input scanner and output stream.
     *
     * @param scanner Input scanner for user interaction.
     * @param out Output stream for displaying messages.
     */
    public NotificationManager(Scanner scanner, PrintStream out) {
        this.scanner = scanner;
        this.out = out;
    }

    /**
     * @brief Configures user notification settings.
     *
     * Allows users to select their preferred notification method:
     * SMS, Email, or Phone Call.
     */
    public void notificationSettings() {
        clearScreen();
        showCurrentNotificationMethod();

        out.println("Select notification method:");
        out.println("1. SMS");
        out.println("2. E-Mail");
        out.println("3. Phone Call");
        out.print("Enter your choice: ");

        int choice = getInput();
        switch (choice) {
            case 1 -> {
                notificationMethod = 1;
                out.println("Your reminder has been set to SMS.");
            }
            case 2 -> {
                notificationMethod = 2;
                out.println("Your reminder has been set to E-Mail.");
            }
            case 3 -> {
                notificationMethod = 3;
                out.println("Your reminder has been set to Phone Call.");
            }
            default -> {
                out.println("Invalid choice. Please try again.");
                enterToContinue();
                notificationSettings(); // Recursive call for invalid input
                return;
            }
        }

        enterToContinue();
    }

    /**
     * @brief Displays the current notification method.
     *
     * Shows the user the currently selected notification method.
     */
    private void showCurrentNotificationMethod() {
        String methodStr = switch (notificationMethod) {
            case 1 -> "SMS";
            case 2 -> "E-Mail";
            case 3 -> "Phone Call";
            default -> "None";
        };
        out.println("Current notification method: " + methodStr);
    }

    /**
     * @brief Clears the terminal screen.
     *
     * Uses ANSI escape sequences to clear the terminal display.
     */
    public void clearScreen() {
        out.print("\033[H\033[2J"); // ANSI escape codes
        out.flush(); // Ensures the screen is cleared immediately
    }

    /**
     * @brief Waits for the user to press Enter to continue.
     *
     * Pauses program execution until the user presses Enter.
     *
     * @return `true` if the operation was successful.
     */
    public boolean enterToContinue() {
        out.println("Press enter to continue...");
        if (!isTestMode) {
            scanner.nextLine();
        }
        return true;
    }

    /**
     * @brief Handles user input and ensures it is an integer.
     *
     * Reads input from the user and validates that it is an integer.
     *
     * @return The integer entered by the user, or `-2` if the input was invalid.
     */
    public int getInput() {
        try {
            int input = scanner.nextInt();
            scanner.nextLine(); // Clears the newline character
            return input;
        } catch (Exception e) {
            scanner.nextLine(); // Clears invalid input
            handleInputError(); // Displays error message
            return -2; // Error indicator
        }
    }

    /**
     * @brief Handles input errors.
     *
     * Displays an error message when invalid input is detected.
     */
    public void handleInputError() {
        out.println("Invalid input. Please enter a number.");
    }
}
