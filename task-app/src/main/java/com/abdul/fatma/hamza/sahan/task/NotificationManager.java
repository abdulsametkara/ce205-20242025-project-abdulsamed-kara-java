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
