package com.abdul.fatma.hamza.sahan.task;

import java.util.*;
import java.io.PrintStream;

/**
 * @brief Manages notification reminders and settings.
 */
public class NotificationManager {
    private final Scanner scanner;
    private final PrintStream out;

    /** Sparse matrix for notification storage (taskId -> date -> method) */
    private final Map<Integer, Map<Integer, Integer>> notificationMatrix = new HashMap<>();

    /** Notification method (0: None, 1: SMS, 2: Email, 3: Phone Call) */
    private int notificationMethod = 0;

    public NotificationManager(Scanner scanner, PrintStream out) {
        this.scanner = scanner;
        this.out = out;
    }

    public boolean isTestMode = false;


    /**
     * @brief Sets a reminder based on user input duration.
     */
    public void setReminders() {
        clearScreen();

        out.println("Enter the reminder duration:");
        out.print("Days: ");
        int days = getInput();
        out.print("Hours: ");
        int hours = getInput();
        out.print("Minutes: ");
        int minutes = getInput();
        out.print("Seconds: ");
        int seconds = getInput();

        int totalSeconds = seconds + (minutes * 60) + (hours * 3600) + (days * 86400);

        if (totalSeconds <= 0) {
            out.println("Invalid duration. Please enter a positive duration.");
            enterToContinue();
            return;
        }

        out.printf("Setting reminder for %d seconds...\n", totalSeconds);

        // Countdown Simulation
        for (int remaining = totalSeconds; remaining > 0; remaining--) {
            clearScreen();
            out.printf("Time remaining: %02d:%02d:%02d:%02d\n",
                    remaining / 86400,             // Days
                    (remaining % 86400) / 3600,    // Hours
                    (remaining % 3600) / 60,       // Minutes
                    remaining % 60);              // Seconds

            platformSleep(1);
        }

        out.println("Time's up! Reminder triggered.");
        enterToContinue();
    }

    /**
     * @brief Configures notification settings.
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
     * @brief Sleeps for the given duration in seconds.
     *
     * @param seconds The duration in seconds.
     */
    private void platformSleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000L); // Milliseconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void clearScreen() {
        out.print("\033[H\033[2J"); // ANSI escape kodları
        out.flush(); // Terminalde ekranın gerçekten temizlenmesi için flush edilir
    }

    public  boolean enterToContinue() {
        out.println("Press enter to continue...");
        if (!isTestMode) {
            scanner.nextLine();
        }
        return true;
    }



    public int getInput() {
        try {
            int input = scanner.nextInt();
            scanner.nextLine(); // Satır sonunu temizle
            return input;
        } catch (Exception e) {
            scanner.nextLine(); // Hatalı girişleri temizle
            handleInputError(); // Hata mesajını yazdır
            return -2; // Hata durumunda -2 döner
        }
    }

    public void handleInputError() {
        out.println("Invalid input. Please enter a number."); // Hata mesajı
    }

}
