/**
 * @file Task.java
 * @brief Task class for a task management system.
 *
 * This class provides core functionality for creating, managing, and analyzing tasks.
 * It allows users to categorize tasks, set priorities, add reminders, and perform
 * algorithmic analyses on tasks.
 *
 * @author User
 * @date 2024-12-24
 * @version 1.0
 */

package com.abdul.fatma.hamza.sahan.task;

import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;

/**
 * @class Task
 * @brief A core class for task management.
 *
 * The Task class manages user interactions, initiates the notification system,
 * and supports various operational modes such as test mode.
 */

public class Task {

    /** Scanner object for user input. */
    public Scanner scanner;
    /** PrintStream object for output. */
    public PrintStream out;
    /**
     * @brief Manages notification settings and reminders for tasks.
     *
     * The `notificationManager` field handles user notification preferences, including
     * delivery methods (e.g., SMS, Email, Phone Call) and reminder scheduling.
     * It is used to configure, send, and manage task notifications.
     */
    private NotificationManager notificationManager;


    /**
     * @brief Constructs a `Task` object with input and output streams.
     *
     * Initializes a `Task` object using the provided `Scanner` for user input
     * and `PrintStream` for output display. It also initializes the
     * `NotificationManager` for managing task-related notifications.
     *
     * @param scanner A `Scanner` object for reading user input.
     * @param out A `PrintStream` object for displaying output to the user.
     */
    public Task(Scanner scanner, PrintStream out) {
        this.scanner = scanner;
        this.out = out;
        this.notificationManager = new NotificationManager(scanner, out);
    }


    /**
     * @brief Test mode flag.
     *
     * Used to control specific behaviors during testing scenarios.
     */
    public boolean isTestMode = false;



    /** @brief The size of the hash table. */
    public static final int TABLE_SIZE = 100;

    /**
     * @brief Hash table for storing users.
     *
     * An array of linked lists used to store `User` objects in hash table format.
     * Each index of the array represents a bucket for handling hash collisions.
     */
    public static LinkedList<User>[] hashTable = new LinkedList[TABLE_SIZE];

    /**
     * @brief List of tasks in the system.
     *
     * Stores task details in an `ArrayList` for quick access and management.
     */
    private static ArrayList<TaskInfo> taskList = new ArrayList<>();

    /**
     * @brief The current count of tasks in the system.
     *
     * Tracks the total number of tasks currently stored.
     */
    private static int taskCount = 0;

    /**
     * @brief The maximum number of tasks allowed in the system.
     *
     * Defines an upper limit on the number of tasks that can be managed simultaneously.
     */
    private static final int MAX_TASKS = 100;

    /**
     * @brief Head node of a doubly linked list of tasks.
     *
     * Points to the first node in a doubly linked list of tasks.
     */
    public static TaskNode head = null;

    /**
     * @brief Tail node of a doubly linked list of tasks.
     *
     * Points to the last node in a doubly linked list of tasks.
     */
    public static TaskNode tail = null;

    /**
     * @brief XOR Linked List for task management.
     *
     * An `XORLinkedList` instance for efficient memory usage when managing tasks
     * in a doubly linked list structure.
     */
    private static XORLinkedList xorLinkedList = new XORLinkedList();

    /**
     * @brief Min-heap for managing task deadlines.
     *
     * A priority queue (`MinHeap`) to prioritize tasks based on their deadlines.
     */
    public PriorityQueue<Assignment> deadlineHeap = new PriorityQueue<>();

    /**
     * @brief Overflow area for user storage.
     *
     * An `ArrayList` used as an overflow area when the hash table exceeds its capacity.
     * Supports up to `OVERFLOW_SIZE` additional users.
     */
    private static final int OVERFLOW_SIZE = 20;

    /**
     * @brief List of overflow users.
     *
     * Stores `User` objects that cannot fit into the main hash table.
     */
    private static ArrayList<User> overflowArea = new ArrayList<>(OVERFLOW_SIZE);








    /**
     * @brief Doubly linked list for task management.
     *
     * A data structure used for managing tasks efficiently.
     */
    private static DoubleLinkedList taskDoublyLinkedList = new DoubleLinkedList();

    /**
     * @brief Clears the console screen.
     *
     * Uses ANSI escape codes to clear the console and reset the cursor.
     */
    public void clearScreen() {
        out.print("\033[H\033[2J");
        out.flush();
    }

    /**
     * @brief Waits for user confirmation to continue.
     *
     * Prompts the user to press Enter to proceed.
     * Skips waiting if in test mode.
     *
     * @return Returns true when the user is ready to continue.
     */

    public  boolean enterToContinue() {
        out.println("Press enter to continue...");
        if (!isTestMode) {
            scanner.nextLine();
        }
        return true;
    }

    /**
     * @brief Reads an integer input from the user.
     *
     * This method captures an integer value entered by the user.
     * If the input is invalid, it handles the error gracefully,
     * clears the input buffer, and returns a specific error code.
     *
     * @return The integer input from the user, or -2 in case of an invalid input.
     */

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



    /**
     * @brief Handles invalid user input errors.
     *
     * Displays an error message when the user provides invalid input.
     * Ensures clarity by prompting the user to enter a valid number.
     */

    public void handleInputError() {
        out.println("Invalid input. Please enter a number."); // Hata mesajı
    }

    /**
     * @brief Displays the opening screen menu.
     *
     * Clears the console and presents the main menu options to the user.
     * The menu includes options for login, registration, and program exit.
     *
     * The user is prompted to make a selection by entering a number.
     */
    public void openingScreenMenu() {
        clearScreen(); // clearScreen metodu çağrılır
        out.println("***************************************");
        out.println("*                                     *");
        out.println("*      WELCOME TO TASK MANAGER!       *");
        out.println("*                                     *");
        out.println("***************************************\n");

        out.println("=============== MAIN MENU ===============");
        out.println("1. Login");
        out.println("2. Register");
        out.println("3. Exit Program");
        out.println("=========================================");
        out.print("Please enter a number: ");

    }
    /**
     * @brief Displays the main menu of the Task Manager application.
     *
     * Clears the console screen and presents the main menu options to the user.
     * The menu includes options for creating tasks, setting deadlines, managing reminders,
     * prioritizing tasks, running algorithms, and exiting the application.
     *
     * The user is prompted to make a selection by entering a number.
     */

    public void printMainMenu() {
        clearScreen(); // clearScreen metodu çağrılır (tanımlamanız gerekecek)
        out.println("========================================");
        out.println("        MAIN MENU - TASK MANAGER       ");
        out.println("========================================");
        out.println("1. Create Task");
        out.println("2. Deadline Settings");
        out.println("3. Reminder System");
        out.println("4. Task Prioritization");
        out.println("5. Algorithms");
        out.println("6. Exit");
        out.println("========================================");
        out.print("Please enter your choice: ");
    }
    /**
     * @brief Displays the Create Task Menu.
     *
     * Clears the console screen and presents a menu for task creation and management.
     * The menu includes options for adding tasks, viewing tasks, categorizing tasks,
     * analyzing dependencies, searching tasks by keyword, and exploring advanced data structures
     * like double linked lists and XOR linked lists.
     *
     * The user is prompted to make a selection by entering a number.
     */

    public void printCreateTaskMenu() {
        clearScreen(); // clearScreen metodu çağrılır
        out.println("========================================");
        out.println("           CREATE TASK MENU            ");
        out.println("========================================");
        out.println("1. Add Task");
        out.println("2. View Tasks");
        out.println("3. Categorize Tasks");
        out.println("4. Dependencies of Functions");
        out.println("5. Analyze SCC");
        out.println("6. Search By Keyword");
        out.println("7. Double Linked List");
        out.println("8. XOR Linked List");
        out.println("9. Exit");
        out.println("========================================");
        out.print("Please enter your choice: ");
    }
    /**
     * @brief Displays the Deadline Settings Menu.
     *
     * Clears the console screen and presents a menu for managing task deadlines.
     * The menu includes options to assign deadlines, view existing deadlines,
     * and filter deadlines within a specific range.
     *
     * The user is prompted to make a selection by entering a number.
     */

    public void printDeadlineSettingsMenu() {
        clearScreen(); // clearScreen metodu çağrılır
        out.println("========================================");
        out.println("       DEADLINE SETTINGS MENU          ");
        out.println("========================================");
        out.println("1. Assign Deadline");
        out.println("2. View Deadlines");
        out.println("3. View Deadlines In Range");
        out.println("4. Exit");
        out.println("========================================");
        out.print("Please enter your choice: ");
    }
    /**
     * @brief Displays the Reminder System Menu.
     *
     * Clears the console screen and presents a menu for managing reminders and notifications.
     * The menu includes options to set reminders, configure notification settings,
     * and exit the menu.
     *
     * The user is prompted to make a selection by entering a number.
     */

    public void printReminderSystemMenu() {
        clearScreen(); // clearScreen metodu çağrılır
        out.println("========================================");
        out.println("        REMINDER SYSTEM MENU            ");
        out.println("========================================");
        out.println("1. Set Reminders");
        out.println("2. Notification Settings");
        out.println("3. Exit");
        out.println("========================================");
        out.print("Please enter your choice: ");
    }
    /**
     * @brief Displays the Task Prioritization Menu.
     *
     * Clears the console screen and presents a menu for prioritizing tasks.
     * The menu includes options to mark task importance, order tasks by importance,
     * and exit the menu.
     *
     * The user is prompted to make a selection by entering a number.
     */

    public void printTaskPrioritizationMenu() {
        clearScreen(); // clearScreen metodu çağrılır
        out.println("========================================");
        out.println("       TASK PRIORITIZATION MENU         ");
        out.println("========================================");
        out.println("1. Mark Task Importance");
        out.println("2. Importance Ordering");
        out.println("3. Exit");
        out.println("========================================");
        out.print("Please enter your choice: ");
    }
    /**
     * @brief Displays the Algorithms Menu.
     *
     * Clears the console screen and presents a menu for exploring various algorithms.
     * The menu includes hashing techniques, overflow handling strategies,
     * and advanced algorithmic approaches for task management.
     *
     * The user is prompted to make a selection by entering a number.
     */

    public void printAlgorithmsMenu() {
        clearScreen(); // clearScreen metodu çağrılır
        out.println("========================================");
        out.println("              ALGORITHMS MENU            ");
        out.println("========================================");
        out.println("1. Progressive Overflow");
        out.println("2. Linear Probing");
        out.println("3. Quadratic Probing");
        out.println("4. Double Hashing");
        out.println("5. Use of Buckets");
        out.println("6. Linear Quotient");
        out.println("7. Brent's Method");
        out.println("8. Exit to Main Menu");
        out.println("========================================");
        out.print("Please enter your choice: ");
    }

    /**
     * @brief Displays the Main Menu and manages user interactions.
     *
     * This method serves as the entry point for the main menu, providing options for login,
     * registration, and exiting the program. It continuously loops until the user chooses to exit.
     *
     * @param pathFileUsers The file path to the user data storage file.
     */

    public void mainMenu(String pathFileUsers) {
        int choice;

        while (true) {
            clearScreen();
            openingScreenMenu();
            choice = getInput();

            if (choice == -2) {
                handleInputError();
                enterToContinue();
                continue;
            }

            switch (choice) {
                case 1:
                    clearScreen();
                    if (loginUserMenu(pathFileUsers)) {
                        userOptionsMenu();
                    }
                    break;

                case 2:
                    clearScreen();
                    registerUserMenu(pathFileUsers);
                    break;

                case 3:
                    out.println("Exit Program");
                    return;
                default:
                    out.println("Invalid choice. Please try again.");
                    enterToContinue();
                    break;
            }
        }
    }

    /**
     * @brief Displays the User Options Menu and manages user interactions.
     *
     * This method serves as the main menu for logged-in users, providing options for creating tasks,
     * managing deadlines, setting reminders, prioritizing tasks, exploring algorithms,
     * and exiting the user session.
     *
     * The method operates in a loop until the user chooses to exit.
     */

    public void userOptionsMenu() {
        int choice;

        while (true) {
            printMainMenu();
            choice = getInput();

            if (choice == -2) {
                handleInputError();
                enterToContinue();
                continue;
            }

            switch (choice) {
                case 1:
                    createTaskMenu();
                    break;
                case 2:
                    deadlineSettingsMenu();
                    break;
                case 3:
                    reminderSystemMenu();
                    break;
                case 4:
                    taskPrioritizationMenu();
                    break;
                case 5:
                    algorithmsMenu();
                    break;
                case 6:
                    return;
                default:
                    clearScreen();
                    out.println("Invalid choice. Please try again.");
                    enterToContinue();
                    break;
            }
        }
    }

    /**
     * @brief Displays the Create Task Menu and manages task-related operations.
     *
     * This method provides an interface for task creation and management.
     * It includes options to add tasks, view tasks, categorize them,
     * analyze dependencies, perform SCC analysis, search tasks by keywords,
     * and navigate using double and XOR linked lists.
     *
     * The method operates in a loop until the user chooses to exit.
     */

    public void createTaskMenu() {
        int maxTasks = 100;
        int choice;

        TaskQueue taskQueue = new TaskQueue();


        while (true) {
            printCreateTaskMenu();
            choice = getInput();

            if (choice == -2) {
                handleInputError();
                enterToContinue();
                continue;
            }

            switch (choice) {
                case 1:
                    taskCount = addTask(taskList, taskDoublyLinkedList, taskCount, maxTasks);
                    enterToContinue();
                    break;
                case 2:
                    viewTask(taskList);

                    enterToContinue();
                    break;
                case 3:
                    categorizeTask(taskList);
                    enterToContinue();
                    break;
                case 4:
                    out.print("Enter the task ID to view its dependencies: ");
                    int taskId = scanner.nextInt();

                    if (taskId > 0 && taskId <= taskList.size()) {
                        printDependencies(taskList, taskId);
                    } else {
                        out.println("Invalid task ID.");
                    }
                    enterToContinue();
                    break;
                case 5:
                    StringBuilder output = new StringBuilder();
                    SCCAnalyzer analyzer = new SCCAnalyzer();


                    analyzer.analyzeSCC(taskList, output);


                    out.println(output.toString());
                    enterToContinue();
                    break;

                case 6:
                    searchTasksByKeyword(taskList);
                    enterToContinue();
                    break;
                case 7:
                    navigateTaskList(taskDoublyLinkedList);
                    enterToContinue();
                    break;
                case 8:
                    XORLinkedList xorList = new XORLinkedList();
                    xorList.loadTasksToXORList("tasks.bin");
                    xorList.navigateXORList();
                    enterToContinue();
                    break;
                case 9:
                    return;
                default:
                    clearScreen();
                    out.println("Invalid choice. Please try again.");
                    enterToContinue();
                    break;
            }

        }

    }

    /**
     * @brief Displays the Deadline Settings Menu and manages deadline-related operations.
     *
     * This method provides an interface for managing task deadlines, including assigning deadlines,
     * viewing existing deadlines, and filtering deadlines within a specific range.
     *
     * The method operates in a loop until the user chooses to exit.
     */

    public void deadlineSettingsMenu() {
        int maxTasks = 100;
        int choice;

        while (true) {
            printDeadlineSettingsMenu();
            choice = getInput();

            if (choice == -2) {
                handleInputError();
                enterToContinue();
                continue;
            }

            switch (choice) {
                case 1:
                    assign_deadline();
                    break;
                case 2:
                    viewDeadlines();
                    enterToContinue();
                    break;
                case 3:
                    BPlusTree tree = new BPlusTree();
                    tree.viewDeadlinesInRange();
                    enterToContinue();
                    break;
                case 4:
                    return;
                default:
                    clearScreen();
                    out.println("Invalid choice. Please try again.");
                    enterToContinue();
                    break;
            }

        }

    }

    /**
     * @brief Displays the Reminder System Menu and manages reminder-related operations.
     *
     * This method provides an interface for managing reminders and notification settings.
     * It includes options to set reminders, configure notification preferences,
     * and exit the reminder system menu.
     *
     * The method operates in a loop until the user chooses to exit.
     */

    public void reminderSystemMenu() {
        int maxTasks = 100;

        int choice;

        while (true) {
            printReminderSystemMenu();
            choice = getInput();

            if (choice == -2) {
                handleInputError();
                enterToContinue();
                continue;
            }

            switch (choice) {
                case 1:
                    setReminders();
                    break;
                case 2:
                    notificationManager.notificationSettings();
                    break;
                case 3:
                    return;
                default:
                    clearScreen();
                    out.println("Invalid choice. Please try again.");
                    enterToContinue();
                    break;
            }

        }

    }

    /**
     * @brief Displays the Task Prioritization Menu and manages task prioritization operations.
     *
     * This method provides an interface for prioritizing tasks based on their importance.
     * It includes options to mark task importance, reorder tasks by priority,
     * and exit the prioritization menu.
     *
     * The method operates in a loop until the user chooses to exit.
     */

    public void taskPrioritizationMenu() {
        int maxTasks = 100;

        int choice;

        while (true) {
            printTaskPrioritizationMenu();
            choice = getInput();

            if (choice == -2) {
                handleInputError();
                enterToContinue();
                continue;
            }

            switch (choice) {
                case 1:
                    markTaskImportance(taskList);
                    break;
                case 2:
                    importanceOrdering(taskList);
                    enterToContinue();
                    break;
                case 3:
                    return;
                default:
                    clearScreen();
                    out.println("Invalid choice. Please try again.");
                    enterToContinue();
                    break;
            }

        }

    }
    /**
     * @brief Displays the Algorithms Menu and manages algorithm-related operations.
     *
     * This method provides an interface for exploring and demonstrating various
     * algorithmic techniques related to task management.
     * It includes hashing methods, overflow handling techniques, and optimization strategies.
     *
     * The method operates in a loop until the user chooses to exit.
     */
    public void algorithmsMenu() {
        int maxTasks = 100;

        int choice;

        while (true) {
            printAlgorithmsMenu();
            choice = getInput();

            if (choice == -2) {
                handleInputError();
                enterToContinue();
                continue;
            }

            switch (choice) {
                case 1:
                    progressiveOverflowDemo();
                    enterToContinue();
                    break;
                case 2:
                    linearProbingDemo();
                    enterToContinue();
                    break;
                case 3:
                    clearScreen();
                    quadraticProbingDemo();
                    enterToContinue();
                    break;

                case 4:
                    clearScreen();
                    doubleHashingDemo();
                    enterToContinue();
                    break;
                case 5:
                    //useOfBuckets
                    enterToContinue();
                    break;
                case 6:
                    clearScreen();
                    linearQuotientDemo();
                    enterToContinue();
                    break;
                case 7:
                    clearScreen();
                    brentsMethodDemo();
                    enterToContinue();
                    break;
                case 8:
                    return;
                default:
                    clearScreen();
                    out.println("Invalid choice. Please try again.");
                    enterToContinue();
                    break;
            }

        }

    }

    /**
     * @brief Logs in a user using Huffman decoding for email and password.
     *
     * This method checks the user's credentials against stored users in the file.
     * It decodes the email and password using Huffman decoding before validation.
     *
     * @param loginUser The User object attempting to log in.
     * @param pathFileUsers The path to the user database file.
     * @return int Returns 1 if login is successful, otherwise 0.
     */
    public int loginUser(User loginUser, String pathFileUsers) {
        File file = new File(pathFileUsers);
        if (!file.exists()) {
            out.println("Failed to open user file.");
            return 0;
        }

        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            int userCount = raf.readInt();
            ArrayList<User> users = new ArrayList<>();

            for (int i = 0; i < userCount; i++) {
                User tempUser = new User();
                tempUser.readFromFile(raf);
                users.add(tempUser);
            }

            for (User user : users) {
                insertUserToHashTable(user);
            }

            // Huffman dosyasından kontrol
            try (DataInputStream huffDis = new DataInputStream(new FileInputStream("user.huf"))) {
                while (huffDis.available() > 0) {
                    int id = huffDis.readInt();
                    int emailLength = huffDis.readInt();
                    byte[] emailBytes = new byte[emailLength];
                    huffDis.readFully(emailBytes);
                    String decodedEmail = HuffmanCoding.huffmanDecode(new String(emailBytes));

                    int passwordLength = huffDis.readInt();
                    byte[] passwordBytes = new byte[passwordLength];
                    huffDis.readFully(passwordBytes);
                    String decodedPassword = HuffmanCoding.huffmanDecode(new String(passwordBytes));

                    if (decodedEmail.equals(loginUser.getEmail()) && decodedPassword.equals(loginUser.getPassword())) {
                        out.println("╔══════════════════════════════════════════════════╗");
                        out.println("║ SUCCESS: Login successful!                      ║");
                        out.println("╚══════════════════════════════════════════════════╝");
                        return 1;
                    }
                }
            }

            out.println("╔══════════════════════════════════════════════════╗");
            out.println("║ ERROR: Incorrect email or password.             ║");
            out.println("╚══════════════════════════════════════════════════╝");

            return 0;

        } catch (IOException e) {
            out.println("Error reading user file.");
            return 0;
        }
    }

    /**
     * @brief Displays the User Login Menu and handles user authentication.
     *
     * This method prompts the user to enter their email and password,
     * and then attempts to authenticate the user by calling the `loginUser` method.
     *
     * @param pathFileUsers The file path to the user data storage file.
     *
     * @return Returns `true` if the login is successful, otherwise returns `false`.
     */
    public boolean loginUserMenu(String pathFileUsers) {

        User userInput = new User();

        out.print("Enter email: ");
        userInput.setEmail(scanner.nextLine());

        out.print("Enter password: ");
        userInput.setPassword(scanner.nextLine());


        return loginUser(userInput, pathFileUsers) == 1;
    }
    /**
     * @brief Generates a hash value for a given email address.
     *
     * This method calculates a hash value by summing the ASCII values of each character
     * in the email string and then applying a modulo operation with the table size.
     *
     * @param email The email address used as input for the hash calculation.
     *
     * @return An integer hash value representing the index in the hash table.
     */
    public static int hashFunction(String email) {
        int hash = 0;
        for (int i = 0; i < email.length(); i++) {
            hash = (hash + email.charAt(i)) % TABLE_SIZE;
        }
        return hash;
    }

    /**
     * @brief Inserts a user into the hash table based on their email address.
     *
     * This method calculates the hash index using the user's email and inserts the user
     * into the appropriate position in the hash table. If no list exists at the index,
     * a new linked list is created.
     *
     * @param user The User object containing user information to be inserted into the hash table.
     */
    public static void insertUserToHashTable(User user) {
        int index = hashFunction(user.getEmail());


        if (hashTable[index] == null) {
            hashTable[index] = new LinkedList<>();
        }

        hashTable[index].add(user);
    }

    /**
     * @brief Searches for a user in the hash table using linear probing.
     *
     * @param email The email of the user.
     * @param password The password of the user.
     * @return User The user object if found, otherwise null.
     */
    public static User searchUserInHashTable(String email, String password) {
        int index = hashFunction(email);
        LinkedList<User> userList = hashTable[index];

        // Bağlı liste üzerinde arama yapıyoruz
        if (userList != null) {
            for (User user : userList) {
                if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                    return user;  // Kullanıcı bulundu
                }
            }
        }
        return null;  // Kullanıcı bulunamadı
    }

    /**
     * @brief Registers a new user with Huffman encoding for email and password.
     *
     * This method registers a new user by checking if the user already exists,
     * encoding their email and password using Huffman coding, and saving them
     * into a binary file (`users.bin`) and a Huffman-encoded file (`user.huf`).
     *
     * @param user The User object containing the user's details.
     * @param pathFileUser The path to the user database file.
     * @return int Returns 1 if registration is successful, otherwise 0.
     */
    public int registerUser(User user, String pathFileUser) {
        File file = new File(pathFileUser);
        int userCount = 0;
        ArrayList<User> users = new ArrayList<>();

        try {
            if (file.exists()) {
                RandomAccessFile raf = new RandomAccessFile(file, "rw");
                if (raf.length() > 0) {
                    userCount = raf.readInt();

                    for (int i = 0; i < userCount; i++) {
                        User tempUser = new User();
                        tempUser.readFromFile(raf);
                        users.add(tempUser);
                    }

                    for (User u : users) {
                        insertUserToHashTable(u);
                    }

                    if (searchUserInHashTable(user.getEmail(), user.getPassword()) != null) {
                        out.println("User already exists.");
                        raf.close();
                        return 0;
                    }
                }
                raf.close();
            } else {
                file.createNewFile();
            }

            // Yeni kullanıcı ekleniyor
            user.setId(userCount + 1);
            insertUserToHashTable(user);

            // Huffman Kodlama
            String encodedEmail = HuffmanCoding.huffmanEncode(user.getEmail());
            String encodedPassword = HuffmanCoding.huffmanEncode(user.getPassword());

            // Huffman dosyasına yazma
            try (DataOutputStream huffDos = new DataOutputStream(new FileOutputStream("user.huf", true))) {
                huffDos.writeInt(user.getId());
                huffDos.writeInt(encodedEmail.length());
                huffDos.writeBytes(encodedEmail);
                huffDos.writeInt(encodedPassword.length());
                huffDos.writeBytes(encodedPassword);
            }

            users.add(user);
            userCount++;

            try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
                raf.seek(0);
                raf.writeInt(userCount);
                for (User u : users) {
                    u.writeToFile(raf);
                }
            }

            out.println("╔══════════════════════════════════════════════════╗");
            out.println("║ SUCCESS: User registered successfully!           ║");
            out.println("║ Welcome, " + user.getName() + " " + user.getSurname() + "             ║");
            out.println("╚══════════════════════════════════════════════════╝");

            return 1;

        } catch (IOException e) {
            e.printStackTrace();
            out.println("Error processing user file.");
            return 0;
        }
    }
    /**
     * @brief Displays the User Registration Menu and handles user registration.
     *
     * This method prompts the user to enter their name, surname, email, and password.
     * The collected information is used to create a new user object, which is then
     * passed to the `registerUser` method for storage and processing.
     *
     * @param pathFileUsers The file path where user data will be stored.
     *
     * @return Returns an integer result from the `registerUser` method, indicating
     * whether the registration was successful or if any errors occurred.
     */

    public int registerUserMenu(String pathFileUsers) {

        User newUser = new User();


        out.print("Enter Name: ");
        newUser.setName(scanner.nextLine());


        out.print("Enter Surname: ");
        newUser.setSurname(scanner.nextLine());


        out.print("Enter email: ");
        newUser.setEmail(scanner.nextLine());


        out.print("Enter password: ");
        newUser.setPassword(scanner.nextLine());


        return registerUser(newUser, pathFileUsers);
    }

    /**
     * @brief Adds a new task to the task list and doubly linked list.
     *
     * This method collects task information from the user, creates a new task with a unique ID,
     * and adds it to both the task list and doubly linked list.
     * It also handles task dependencies and ensures task data is saved properly.
     *
     * @param taskList An ArrayList containing existing tasks.
     * @param taskDoublyLinkedList A DoubleLinkedList structure for managing tasks.
     * @param taskCount The current count of tasks.
     * @param maxTasks The maximum allowed number of tasks.
     *
     * @return Returns `1` if the task is successfully added, or `0` if the task limit is reached.
     */
    public int addTask(ArrayList<TaskInfo> taskList, DoubleLinkedList taskDoublyLinkedList, int taskCount, int maxTasks) {
        if (taskCount >= maxTasks) {
            out.println("Task list is full. Cannot add more tasks.");
            return 0;
        }


        int newId = getNewTaskId(taskList);

        TaskInfo newTask = new TaskInfo();
        newTask.setId(newId);


        out.print("Enter Task Name: ");
        newTask.setName(scanner.nextLine());

        out.print("Enter Task Description: ");
        newTask.setDescription(scanner.nextLine());

        out.print("Enter Task Category: ");
        newTask.setCategory(scanner.nextLine());

        out.print("Enter Due Date (YYYY-MM-DD): ");
        newTask.setDueDate(scanner.nextLine());

        out.print("Enter number of dependencies: ");
        int dependencyCount = scanner.nextInt();
        newTask.setDependencyCount(dependencyCount);
        scanner.nextLine();

        for (int i = 0; i < newTask.getDependencyCount(); i++) {
            out.print("Enter dependency task ID for dependency " + (i + 1) + ": ");
            newTask.getDependencies()[i] = scanner.nextInt();
            scanner.nextLine();
        }

        taskList.add(newTask);

        taskDoublyLinkedList.addTaskToLinkedList(newTask);

        taskCount++;


        saveTasks(taskList);

        out.println("Task added and saved successfully!");
        return 1;
    }

    /**
     * @brief Saves the task list to a binary file.
     *
     * This method serializes the task list and writes it to a binary file named `tasks.bin`.
     * Each task's details, including ID, name, description, category, due date,
     * dependency count, importance ID, and dependencies, are saved sequentially.
     *
     * @param taskList An ArrayList containing tasks to be saved.
     *
     * @note If an IOException occurs during the save process, an error message is displayed.
     */

    public void saveTasks(ArrayList<TaskInfo> taskList) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream("tasks.bin"))) {
            dos.writeInt(taskList.size());
            for (TaskInfo task : taskList) {
                dos.writeInt(task.getId());                // ID
                dos.writeUTF(task.getName());              // Name
                dos.writeUTF(task.getDescription());       // Description
                dos.writeUTF(task.getCategory());          // Category
                dos.writeUTF(task.getDueDate());           // Due Date
                dos.writeInt(task.getDependencyCount());   // Dependency Count
                dos.writeInt(task.getImportanceId());      // Importance ID
                for (int dep : task.getDependencies()) {
                    dos.writeInt(dep);                     // Dependencies
                }
            }
        } catch (IOException e) {
            out.println("Error saving tasks: " + e.getMessage());
        }
    }

    /**
     * @brief Loads tasks from a binary file into the task list.
     *
     * This method reads tasks from a binary file (`tasks.bin`) and populates the given task list.
     * Each task is deserialized using the `readFromFile` method of the `TaskInfo` class.
     *
     * @param taskList An ArrayList to store the loaded tasks.
     *
     * @return The number of tasks successfully loaded. Returns `0` if no tasks are found or an error occurs.
     *
     * @note If the file `tasks.bin` does not exist or an IOException occurs, an appropriate message is displayed.
     */

    public int loadTasks(ArrayList<TaskInfo> taskList) {
        File file = new File("tasks.bin");
        if (!file.exists()) {
            out.println("No previous tasks found.");
            return 0;
        }

        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            int taskCount = raf.readInt();
            out.println("Task count read from file: " + taskCount);

            for (int i = 0; i < taskCount; i++) {
                TaskInfo task = new TaskInfo();
                task.readFromFile(raf);
                out.println("Task loaded: ID=" + task.getId() + ", Name=" + task.getName());
                taskList.add(task);
            }

            out.println(taskCount + " tasks loaded successfully!");
            return taskCount;
        } catch (IOException e) {
            out.println("Error reading tasks file: " + e.getMessage());
            return 0;
        }
    }


    /**
     * @brief Navigates through tasks in a doubly linked list.
     *
     * This method allows the user to browse tasks in a doubly linked list one by one.
     * Users can move forward, backward, or exit the navigation at any time.
     *
     * @param taskList The doubly linked list containing tasks to navigate.
     *
     * @note If the task list is empty, an appropriate message is displayed, and the method returns immediately.
     */



    public void navigateTaskList(DoubleLinkedList taskList) {
        if (taskList.head == null) {
            out.println("No tasks available for navigation.");
            return;
        }


        TaskNode current = taskList.head;
        int choice;

        while (true) {
            out.println("\nCurrent Task:");
            out.println("ID: " + current.task.getId());
            out.println("Name: " + current.task.getName());
            out.println("Description: " + current.task.getDescription());
            out.println("Category: " + current.task.getCategory());
            out.println("Due Date: " + current.task.getDueDate());

            out.println("\n1. Next\n2. Previous\n3. Exit");
            out.print("Choose an option: ");
            choice = scanner.nextInt();

            if (choice == 1) {
                if (current.next != null) {
                    current = current.next;
                } else {
                    out.println("This is the last task.");
                }
            } else if (choice == 2) {
                if (current.prev != null) {
                    current = current.prev;
                } else {
                    out.println("This is the first task.");
                }
            } else if (choice == 3) {
                break;
            } else {
                out.println("Invalid choice. Try again.");
            }
        }
    }

    /**
     * @brief Adds a new task to the end of the doubly linked list.
     *
     * This method creates a new node with the provided task and appends it to the tail
     * of the doubly linked list. If the list is empty, the new node becomes both
     * the head and tail of the list.
     *
     * @param newTask The task information to be added to the list.
     *
     * @note If the list is empty, the new node becomes the head and tail.
     */

    public static void addTaskToList(TaskInfo newTask) {
        TaskNode newNode = new TaskNode(newTask);
        newNode.next = null;
        newNode.prev = tail;

        if (tail != null) {
            tail.next = newNode;
        } else {
            head = newNode;
        }
        tail = newNode;
    }
    /**
     * @brief Displays all tasks in the task list.
     *
     * This method iterates through the provided task list and displays task details,
     * including ID, name, description, category, and due date.
     * If the task list is empty, an appropriate message is displayed.
     *
     * @param taskList An ArrayList containing tasks to be displayed.
     *
     * @note If the task list is empty, the user is notified, and the method returns immediately.
     * @note After displaying tasks, the method pauses execution and waits for user confirmation to continue.
     */



    public void viewTask(ArrayList<TaskInfo> taskList) {
        if (taskList.isEmpty()) {
            out.println("No tasks found. The task list is empty.");
            enterToContinue();
            return;
        }

        out.println("\n--- List of Tasks ---");


        for (TaskInfo task : taskList) {
            out.println("ID: " + task.getId());
            out.println("Name: " + task.getName());
            out.println("Description: " + task.getDescription());
            out.println("Category: " + task.getCategory());
            out.println("Due Date: " + task.getDueDate());
            out.println("---------------------------");
        }

        enterToContinue();
    }

    /**
     * @brief Generates a new unique task ID.
     *
     * This method iterates through the provided task list and determines
     * the maximum task ID. It then returns a new unique ID by incrementing
     * the maximum found ID by one.
     *
     * @param taskList An `ArrayList` of `TaskInfo` objects representing the current tasks.
     * @return A new unique task ID, which is one greater than the maximum existing task ID.
     */
    public static int getNewTaskId(ArrayList<TaskInfo> taskList) {
        int maxId = 0;

        // Iterate through the task list to find the maximum ID
        for (TaskInfo task : taskList) {
            if (task.getId() > maxId) {
                maxId = task.getId();
            }
        }

        return maxId + 1; // Return a new unique ID
    }


    /**
     * @brief Displays tasks filtered by a specific category.
     *
     * This method prompts the user to enter a category, then filters and displays
     * tasks from the task list that match the given category.
     *
     * @param taskList An ArrayList containing tasks to be filtered.
     *
     * @note If no tasks match the given category, a message is displayed.
     * @note After displaying tasks, the method pauses execution and waits for user confirmation to continue.
     */

    public void categorizeTask(ArrayList<TaskInfo> taskList) {



        out.print("Enter category to filter: ");
        String category = scanner.nextLine().trim();

        boolean found = false;

        out.println("\n--- Tasks in Category '" + category + "' ---");


        for (TaskInfo task : taskList) {
            if (task.getCategory().equalsIgnoreCase(category)) {
                out.println("ID: " + task.getId());
                out.println("Name: " + task.getName());
                out.println("Description: " + task.getDescription());
                out.println("Due Date: " + task.getDueDate());
                out.println("---------------------------");
                found = true;
            }
        }

        if (!found) {
            out.println("No tasks found in this category.");
        }

        enterToContinue();
    }
    /**
     * @brief Displays the dependencies of a specific task.
     *
     * This method prints all the dependencies of a task with the given task ID.
     * It uses a utility method (`printDependenciesUtil`) to recursively explore
     * and display task dependencies while avoiding cycles using a `visited` array.
     *
     * @param taskList An ArrayList containing all tasks.
     * @param startTaskId The ID of the task whose dependencies are to be displayed.
     *
     * @note The method uses a `visited` array to prevent infinite loops in case of circular dependencies.
     */
    public void printDependencies(ArrayList<TaskInfo> taskList, int startTaskId) {
        boolean[] visited = new boolean[taskList.size()]; // Ziyaret edilen görevler için dizi

        out.println("Dependencies for Task " + startTaskId + ":");
        printDependenciesUtil(taskList, startTaskId, visited); // Yardımcı fonksiyonu çağır
    }

    /**
     * @brief Recursively prints the dependencies of a task.
     *
     * This private utility method explores and displays the dependencies of a specific task
     * while preventing circular dependencies using a `visited` array.
     *
     * @param taskList An ArrayList containing all tasks.
     * @param taskId The ID of the task whose dependencies are being processed.
     * @param visited A boolean array tracking visited tasks to prevent infinite loops in circular dependencies.
     *
     * @note If a task has already been visited, the method returns immediately to prevent circular dependency loops.
     */

    private void printDependenciesUtil(ArrayList<TaskInfo> taskList, int taskId, boolean[] visited) {
        if (visited[taskId - 1]) {
            return; // Görev zaten ziyaret edildiyse döngüyü önlemek için dur
        }

        visited[taskId - 1] = true; // Görevi ziyaret edildi olarak işaretle

        // Görevi bul ve bağımlılıklarını yazdır
        for (TaskInfo task : taskList) {
            if (task.getId() == taskId) {
                for (int depId : task.getDependencies()) {
                    if (depId != 0) { // Sıfır olmayan bağımlılıkları yazdır
                        out.println("Task " + taskId + " depends on Task " + depId);
                        printDependenciesUtil(taskList, depId, visited); // Bağımlılıkları yinelemeli olarak yazdır
                    }
                }
            }
        }
    }
    /**
     * @brief Computes the prefix table for the Knuth-Morris-Pratt (KMP) algorithm.
     *
     * This method generates a prefix table (also known as the "longest prefix suffix" table)
     * for a given pattern. The prefix table is used in the KMP algorithm to optimize
     * the pattern matching process by avoiding unnecessary character comparisons.
     *
     * @param pattern The string pattern for which the prefix table is generated.
     * @param prefixTable An integer array to store the computed prefix table values.
     *
     * @note The prefix table helps in determining the next positions to compare
     * after a mismatch occurs during pattern matching.
     */
    public static void computePrefixTable(String pattern, int[] prefixTable) {
        int length = 0;
        prefixTable[0] = 0;

        for (int i = 1; i < pattern.length(); i++) {
            while (length > 0 && pattern.charAt(i) != pattern.charAt(length)) {
                length = prefixTable[length - 1];
            }
            if (pattern.charAt(i) == pattern.charAt(length)) {
                length++;
            }
            prefixTable[i] = length;
        }
    }
    /**
     * @brief Searches for a pattern in a text using the Knuth-Morris-Pratt (KMP) algorithm.
     *
     * This method implements the KMP algorithm to efficiently search for a pattern within a text.
     * It utilizes the prefix table to skip unnecessary comparisons, optimizing the search process.
     *
     * @param text The text in which the pattern will be searched.
     * @param pattern The pattern to search for in the text.
     *
     * @return Returns `true` if the pattern is found in the text, otherwise returns `false`.
     *
     * @note The method relies on the `computePrefixTable` function to build the prefix table
     * for the given pattern before starting the search.
     */
    public static boolean KMPsearch(String text, String pattern) {
        int textLength = text.length();
        int patternLength = pattern.length();

        int[] prefixTable = new int[patternLength];
        computePrefixTable(pattern, prefixTable);

        int i = 0; // Text index
        int j = 0; // Pattern index

        while (i < textLength) {
            if (pattern.charAt(j) == text.charAt(i)) {
                j++;
                i++;
            }
            if (j == patternLength) {
                return true; // Pattern found
            } else if (i < textLength && pattern.charAt(j) != text.charAt(i)) {
                if (j != 0) {
                    j = prefixTable[j - 1];
                } else {
                    i++;
                }
            }
        }
        return false; // Pattern not found
    }
    /**
     * @brief Searches for tasks containing a specific keyword in their descriptions.
     *
     * This method allows users to search for tasks whose descriptions contain a given keyword.
     * The Knuth-Morris-Pratt (KMP) string matching algorithm is used for efficient keyword searching.
     *
     * @param taskList An ArrayList containing tasks to be searched.
     *
     * @note If no tasks match the keyword, an appropriate message is displayed.
     * @note The search is case-sensitive.
     */
    public void searchTasksByKeyword(ArrayList<TaskInfo> taskList) {

        out.print("Enter the keyword to search in task descriptions: ");
        String keyword = scanner.nextLine();

        boolean found = false;

        out.println("\nTasks containing the keyword '" + keyword + "' in their descriptions:");
        out.println("----------------------------------------------------");

        for (TaskInfo task : taskList) {
            if (KMPsearch(task.getDescription(), keyword)) {
                out.println("ID: " + task.getId());
                out.println("Name: " + task.getName());
                out.println("Description: " + task.getDescription());
                out.println("Category: " + task.getCategory());
                out.println("Due Date: " + task.getDueDate());
                out.println("----------------------------------------------------");
                found = true;
            }
        }

        if (!found) {
            out.println("No tasks found with the keyword '" + keyword + "'.");
        }
    }
    /**
     * @brief Assigns a deadline to a task and saves it to a file.
     *
     * This method prompts the user to enter a task name and a deadline date (day, month, year).
     * It validates the entered date and, if valid, creates an `Assignment` object,
     * adds it to a heap structure, and saves it to a binary file (`deadlines.bin`).
     *
     * @note If the entered date is invalid, an error message is displayed, and the method returns.
     * @note The deadline information is appended to the file `deadlines.bin`.
     */

    public void assign_deadline() {


        out.print("Enter Task Name: ");
        String taskName = scanner.nextLine();

        out.print("Enter Deadline (day month year): ");
        int day = scanner.nextInt();
        int month = scanner.nextInt();
        int year = scanner.nextInt();

        if (day < 1 || day > 31 || month < 1 || month > 12 || year < 1900) {
            out.println("Invalid date! Please enter a valid date.");
            return;
        }

        // Yeni Assignment oluştur ve heap'e ekle
        Assignment assignment = new Assignment(taskName, day, month, year);
        deadlineHeap.add(assignment);

        // Dosyaya kaydetme işlemi
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("deadlines.bin", true))) {
            oos.writeObject(assignment);
            out.println("Deadline assigned and saved successfully!");
        } catch (IOException e) {
            out.println("Error saving deadline: " + e.getMessage());
        }
    }

    /**
     * @brief Displays upcoming deadlines in sorted order.
     *
     * This method displays all deadlines in ascending order of their dates using a temporary priority queue.
     * Deadlines are presented with task names and their respective dates.
     *
     * @return Returns `1` if deadlines are displayed successfully, `-1` if no deadlines are present.
     *
     * @note The method uses a temporary priority queue to preserve the original heap structure.
     * @note If there are no deadlines, an appropriate message is displayed.
     */

    public int viewDeadlines() {
        if (deadlineHeap.isEmpty()) {
            out.println("No deadlines to display.");
            return -1;
        }

        out.println("\n--- Upcoming Deadlines (Sorted by Date) ---");
        out.println("-------------------------------------------");

        // Geçici heap oluşturmak için mevcut heap'i kopyalıyoruz
        PriorityQueue<Assignment> tempHeap = new PriorityQueue<>(deadlineHeap);


        int taskCount = 0;
        while (!tempHeap.isEmpty()) {
            Assignment deadline = tempHeap.poll(); // Min elemanı çıkar
            out.printf("%d. Task: %s - Deadline: %02d/%02d/%04d\n",
                    ++taskCount,
                    deadline.getName(),
                    deadline.getDay(),
                    deadline.getMonth(),
                    deadline.getYear());
        }

        if (taskCount == 0) {
            out.println("No deadlines to display.");
        }

        out.println("-------------------------------------------");
        enterToContinue(); // Devam etmek için kullanıcıdan girdi al
        return 1;
    }


    /**
     * @brief Sets a countdown reminder based on user input.
     *
     * This method asks the user to input a duration in days, hours, minutes, and seconds.
     * It then starts a countdown and displays the remaining time until the reminder triggers.
     *
     * @return Returns true if the reminder was successfully set, otherwise false.
     */
    public boolean setReminders() {
        clearScreen();

        int days, hours, minutes, seconds;

        out.println("Enter the reminder duration:");

        out.print("Days: ");
        days = getInput();

        out.print("Hours: ");
        hours = getInput();

        out.print("Minutes: ");
        minutes = getInput();

        out.print("Seconds: ");
        seconds = getInput();

        // Toplam süreyi saniyeye dönüştür
        int totalSeconds = seconds + minutes * 60 + hours * 3600 + days * 86400;

        if (totalSeconds <= 0) {
            out.println("Invalid duration. Please enter a positive duration.");
            enterToContinue();
            return false;
        }

        out.printf("Setting reminder for %d seconds...\n", totalSeconds);

        // Geri sayım döngüsü
        try {
            for (int remaining = totalSeconds; remaining > 0; --remaining) {
                clearScreen();
                out.printf("Time remaining: %02d:%02d:%02d:%02d\n",
                        remaining / 86400,           // Days
                        (remaining % 86400) / 3600,  // Hours
                        (remaining % 3600) / 60,     // Minutes
                        remaining % 60);            // Seconds

                platformSleep(1); // 1 saniye bekle
            }
        } catch (InterruptedException e) {
            out.println("Reminder interrupted: " + e.getMessage());
            return false;
        }

        out.println("Time's up! Reminder triggered.");
        enterToContinue();
        return true;
    }

    /**
     * @brief Platform-independent sleep function.
     *
     * This method pauses the program execution for the given number of seconds.
     *
     * @param seconds The number of seconds to sleep.
     */
    private void platformSleep(int seconds) throws InterruptedException {
        TimeUnit.SECONDS.sleep(seconds);
    }

    /**
     * @brief Marks the importance level of a specific task.
     *
     * This method allows users to assign an importance level (Low, Medium, High) to a specific task.
     * The user selects a task by entering its name and then assigns an importance level by entering an ID.
     *
     * @param taskList An ArrayList containing tasks to be updated.
     *
     * @note The method validates both the task name and the importance ID.
     * @note Valid importance IDs are: 1 (Low), 2 (Medium), 3 (High).
     * @note After marking the importance, the updated task list is saved using `saveTasks`.
     */

    public void markTaskImportance(ArrayList<TaskInfo> taskList) {
        clearScreen();

        if (taskList.isEmpty()) {
            out.println("No tasks available. Please add tasks first.");
            enterToContinue();
            return;
        }

        out.println("Tasks loaded:");
        for (TaskInfo task : taskList) {
            String importanceStr = switch (task.getImportanceId()) {
                case 1 -> "Low";
                case 2 -> "Medium";
                case 3 -> "High";
                default -> "Unmarked";
            };
            out.printf("ID: %d, Name: %s, Importance: %s%n",
                    task.getId(), task.getName(), importanceStr);
        }

        // Kullanıcıdan Task Adı Al
        out.print("Enter the name of the task to mark importance: ");
        String taskName = scanner.nextLine();

        TaskInfo selectedTask = findTaskByName(taskList, taskName);

        if (selectedTask == null) {
            out.println("Task not found! Please enter a valid task name.");
            enterToContinue();
            return;
        }

        // Önem Derecesi Al
        int importanceId;
        while (true) {
            out.print("Enter the importance ID (1: Low, 2: Medium, 3: High): ");
            if (scanner.hasNextInt()) {
                importanceId = scanner.nextInt();
                scanner.nextLine(); // Satır sonunu temizle
                if (importanceId >= 1 && importanceId <= 3) {
                    break;
                }
            }
            out.println("Invalid importance ID! Please enter 1, 2, or 3.");
            scanner.nextLine(); // Geçersiz girişi temizle
        }

        // Önem Derecesini Güncelle
        selectedTask.setImportanceId(importanceId);
        saveTasks(taskList);

        out.printf("Importance level of '%s' marked successfully as %d.%n",
                selectedTask.getName(), importanceId);
        enterToContinue();
    }
    /**
     * @brief Finds a task by its name in the task list.
     *
     * This method searches through the task list to find a task with a matching name,
     * ignoring case sensitivity.
     *
     * @param taskList An ArrayList containing tasks to search.
     * @param name The name of the task to search for.
     *
     * @return Returns the `TaskInfo` object if a matching task is found, otherwise returns `null`.
     *
     * @note The search is case-insensitive.
     * @note If no task matches the given name, `null` is returned.
     */

    private TaskInfo findTaskByName(ArrayList<TaskInfo> taskList, String name) {
        for (TaskInfo task : taskList) {
            if (task.getName().equalsIgnoreCase(name)) {
                return task;
            }
        }
        return null; // Task bulunamadı
    }

    /**
     * @brief Displays tasks ordered by their importance level.
     *
     * This function sorts the task list by importance levels (High, Medium, Low) and displays them
     * in a tabular format for better readability.
     *
     * @param taskList The list of tasks to be displayed in order of importance.
     */
    public void importanceOrdering(ArrayList<TaskInfo> taskList) {
        clearScreen();

        if (taskList.isEmpty()) {
            out.println("No tasks available to display.");
            enterToContinue();
            return;
        }

        // Görevleri Önem Derecesine Göre Sırala (High → Medium → Low)
        taskList.sort((task1, task2) -> Integer.compare(task1.getImportanceId(), task2.getImportanceId()));

        out.println("┌──────────┬────────────────────┬──────────────────────┬────────────┬─────────────┐");
        out.println("│  ID      │ Task Name          │ Description          │ Category   │ Importance  │");
        out.println("├──────────┼────────────────────┼──────────────────────┼────────────┼─────────────┤");

        for (TaskInfo task : taskList) {
            String importanceStr = switch (task.getImportanceId()) {
                case 1 -> "Low";
                case 2 -> "Medium";
                case 3 -> "High";
                default -> "Unmarked";
            };

            out.printf("│ %-8d │ %-18s │ %-20s │ %-10s │ %-11s │%n",
                    task.getId(),
                    task.getName(),
                    task.getDescription(),
                    task.getCategory(),
                    importanceStr);
        }

        out.println("└──────────┴────────────────────┴──────────────────────┴────────────┴─────────────┘");
        enterToContinue();
    }
    /**
     * @brief Demonstrates user management using Linear Probing in hash tables.
     *
     * This method provides an interactive menu for demonstrating Linear Probing.
     * Users can:
     * - Add a user using linear probing collision resolution.
     * - Search for a user in the hash table.
     * - Display all users stored in the hash table.
     *
     * @note The menu provides options to add, search, display, or exit the demo.
     * @note Invalid input is handled gracefully, and the user is prompted to retry.
     */


    public void linearProbingDemo() {
        int choice;

        while (true) {
            clearScreen();
            out.println("╔══════════════════════════════════════════════════╗");
            out.println("║            LINEAR PROBING DEMO                   ║");
            out.println("╚══════════════════════════════════════════════════╝");
            out.println("1. Add User");
            out.println("2. Search User");
            out.println("3. Display Users");
            out.println("4. Exit");
            out.print("Enter your choice: ");

            choice = getInput();

            if (choice == -2) {
                handleInputError();
                enterToContinue();
                continue;
            }

            switch (choice) {
                case 1:
                    addUserWithLinearProbing();
                    break;
                case 2:
                    searchUserWithLinearProbing();
                    break;
                case 3:
                    displayUsersWithLinearProbing();
                    break;
                case 4:
                    out.println("Exiting Linear Probing Demo...");
                    return; // Menüden çıkış
                default:
                    out.println("Invalid choice. Please try again.");
                    enterToContinue();
            }
        }
    }

    /**
     * @brief Adds a new user to the hash table using Linear Probing.
     *
     * This method collects user details such as name, surname, email, and password,
     * and inserts the user into the hash table using Linear Probing to resolve collisions.
     *
     * @note If a user with the same email already exists, the operation is aborted.
     * @note Linear Probing is used to find the next available slot in case of collisions.
     */

    private void addUserWithLinearProbing() {
        clearScreen();
        out.println("╔══════════════════════════════════════════════════╗");
        out.println("║            USER REGISTRATION MENU                ║");
        out.println("╚══════════════════════════════════════════════════╝");

        User user = new User();

        out.print("➤ Enter Name       : ");
        user.setName(scanner.nextLine());

        out.print("➤ Enter Surname    : ");
        user.setSurname(scanner.nextLine());

        out.print("➤ Enter Email      : ");
        user.setEmail(scanner.nextLine());

        out.print("➤ Enter Password   : ");
        user.setPassword(scanner.nextLine());

        int index = hashFunction(user.getEmail());
        int originalIndex = index;

        int probes = 0;

        // Linear Probing ile uygun boş indeks bul
        while (hashTable[index] != null) {
            for (User u : hashTable[index]) {
                if (u.getEmail().equals(user.getEmail())) {
                    out.println("╔══════════════════════════════════════════════════╗");
                    out.println("║ ERROR: User already exists at index " + index + ". ║");
                    out.println("╚══════════════════════════════════════════════════╝");
                    return;
                }
            }

        }

        if (hashTable[index] == null) {
            hashTable[index] = new LinkedList<>();
        }

        hashTable[index].add(user);

        out.println("╔══════════════════════════════════════════════════╗");
        out.println("║ SUCCESS: User added at index " + index + " using Linear Probing. ║");
        out.println("╚══════════════════════════════════════════════════╝");

        enterToContinue();
    }

    /**
     * @brief Searches for a user in the hash table using Linear Probing.
     *
     * This method allows users to search for a specific user by providing their email and password.
     * It uses Linear Probing to handle potential collisions during the search process.
     *
     * @note If the user is found, their name and surname are displayed.
     * @note If the user is not found, an appropriate error message is shown.
     */
    private void searchUserWithLinearProbing() {
        clearScreen();
        out.println("╔══════════════════════════════════════════════════╗");
        out.println("║                USER SEARCH MENU                  ║");
        out.println("╚══════════════════════════════════════════════════╝");

        out.print("➤ Enter Email to search: ");
        String email = scanner.nextLine();

        out.print("➤ Enter Password       : ");
        String password = scanner.nextLine();

        int index = hashFunction(email);
        int originalIndex = index;
        int probes = 0;

        while (hashTable[index] != null) {
            for (User u : hashTable[index]) {
                if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
                    out.println("╔══════════════════════════════════════════════════╗");
                    out.println("║ USER FOUND IN HASH TABLE                         ║");
                    out.println("╠══════════════════════════════════════════════════╣");
                    out.println("║ Name     : " + u.getName());
                    out.println("║ Surname  : " + u.getSurname());
                    out.println("╚══════════════════════════════════════════════════╝");
                    enterToContinue();
                    return;
                }
            }

            index = (index + 1) % TABLE_SIZE;
            probes++;

            if (index == originalIndex || probes >= TABLE_SIZE) {
                break;
            }
        }

        out.println("╔══════════════════════════════════════════════════╗");
        out.println("║ ERROR: User not found in Hash Table.             ║");
        out.println("╚══════════════════════════════════════════════════╝");
        enterToContinue();
    }

    /**
     * @brief Displays all users stored in the hash table using Linear Probing.
     *
     * This method iterates through the hash table and displays all users stored at each index.
     * If an index contains multiple users (due to collision resolution via Linear Probing),
     * they are listed sequentially.
     *
     * @note Empty indexes are skipped, and only non-empty indexes are displayed.
     */
    private void displayUsersWithLinearProbing() {
        clearScreen();
        out.println("╔══════════════════════════════════════════════════╗");
        out.println("║              DISPLAY ALL USERS                   ║");
        out.println("╚══════════════════════════════════════════════════╝");

        for (int i = 0; i < TABLE_SIZE; i++) {
            if (hashTable[i] != null && !hashTable[i].isEmpty()) {
                out.println("╔═══ HASH TABLE INDEX: " + i + " ══════════════════════════╗");
                for (User u : hashTable[i]) {
                    out.println("║ Name  : " + u.getName());
                    out.println("║ Email : " + u.getEmail());
                    out.println("╠──────────────────────────────────────────────────╣");
                }
            }
        }

        out.println("╚══════════════════════════════════════════════════╝");
        enterToContinue();
    }


    /**
     * @brief Demonstrates user management using Progressive Overflow in hash tables.
     *
     * This method provides an interactive menu for demonstrating Progressive Overflow handling.
     * Users can:
     * - Add a user to the hash table.
     * - Search for a user in the hash table.
     * - Display all users in the hash table.
     *
     * @note Progressive Overflow is used to handle collisions by finding alternative slots systematically.
     * @note Invalid inputs are handled gracefully, and the user is prompted to retry.
     */
    public void progressiveOverflowDemo() {
        int choice;

        while (true) {
            clearScreen();
            out.println("╔══════════════════════════════════════════════════╗");
            out.println("║           PROGRESSIVE OVERFLOW DEMO              ║");
            out.println("╚══════════════════════════════════════════════════╝");
            out.println("1. Add User");
            out.println("2. Search User");
            out.println("3. Display Users");
            out.println("4. Exit");
            out.print("Enter your choice: ");

            choice = getInput();

            if (choice == -2) {
                handleInputError();
                enterToContinue();
                continue;
            }

            switch (choice) {
                case 1:
                    addUserToHashTable();
                    break;
                case 2:
                    searchUserInHashTable();
                    break;
                case 3:
                    displayUsersFromHashTable();
                    break;
                case 4:
                    out.println("Exiting Progressive Overflow Demo...");
                    return; // Menüden çıkış
                default:
                    out.println("Invalid choice. Please try again.");
                    enterToContinue();
            }
        }
    }

    /**
     * @brief Adds a new user to the hash table using Progressive Overflow strategy.
     *
     * This method collects user details (name, surname, email, and password) and attempts to add the user
     * to the hash table. If the calculated index is already occupied, Progressive Overflow is used
     * to find an alternative slot.
     *
     * @note If a user with the same email already exists at the computed index, the operation is aborted.
     * @note The hash table uses a `LinkedList` at each index to handle collisions.
     */


    private void addUserToHashTable() {
        clearScreen();
        out.println("╔══════════════════════════════════════════════════╗");
        out.println("║               USER REGISTRATION MENU             ║");
        out.println("╠══════════════════════════════════════════════════╣");
        out.println("║ Please provide the following details to register ║");
        out.println("╚══════════════════════════════════════════════════╝");

        User user = new User();

        out.print("➤ Enter Name       : ");
        user.setName(scanner.nextLine());

        out.print("➤ Enter Surname    : ");
        user.setSurname(scanner.nextLine());

        out.print("➤ Enter Email      : ");
        user.setEmail(scanner.nextLine());

        out.print("➤ Enter Password   : ");
        user.setPassword(scanner.nextLine());

        int index = hashFunction(user.getEmail());

        // Eğer indeks boşsa, yeni bir LinkedList oluştur
        if (hashTable[index] == null) {
            hashTable[index] = new LinkedList<>();
        }

        // Aynı email var mı kontrol et
        for (User u : hashTable[index]) {
            if (u.getEmail().equals(user.getEmail())) {
                out.println("User already exists at index " + index + " in hash table.");
                return;
            }
        }

        // Hash tablosuna ekle
        if (hashTable[index].size() < TABLE_SIZE) {
            hashTable[index].add(user);
            out.println("\n╔══════════════════════════════════════════════════╗");
            out.println("║ SUCCESS: User added to Main Hash Table.          ║");
            out.println("║ Index: " + index + "                                    ║");
            out.println("╚══════════════════════════════════════════════════╝");
        } else {

        }

        enterToContinue();
    }

    /**
     * @brief Searches for a user in the hash table using Progressive Overflow strategy.
     *
     * This method allows users to search for a specific user by providing their email and password.
     * The search begins at the computed hash index and examines all linked entries at that index.
     *
     *
     * @note If the user is found, their name and surname are displayed.
     * @note If the user is not found, an appropriate error message is shown.
     */

    private void searchUserInHashTable() {
        clearScreen();
        out.println("╔══════════════════════════════════════════════════╗");
        out.println("║                 USER SEARCH MENU                 ║");
        out.println("╠══════════════════════════════════════════════════╣");
        out.println("║ Please provide the email to search for a user.   ║");
        out.println("╚══════════════════════════════════════════════════╝");

        out.print("➤ Enter Email to search: ");
        String email = scanner.nextLine();

        out.print("Enter Password: ");
        String password = scanner.nextLine();

        int index = hashFunction(email);

        // Hash tablosunda ara
        if (hashTable[index] != null) {
            for (User u : hashTable[index]) {
                if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
                    out.println("\n╔════════════════════════════════════════════════╗");
                    out.println("║ USER FOUND IN MAIN TABLE                         ║");
                    out.println("╠══════════════════════════════════════════════════╣");
                    out.println("║ Name     : " + u.getName());
                    out.println("║ Surname  : " + u.getSurname());
                    out.println("╚══════════════════════════════════════════════════╝");
                    enterToContinue();
                    return;
                }
            }
        }

        out.println("\n╔════════════════════════════════════════════════╗");
        out.println("║ ERROR: User not found in Main Table or Overflow. ║");
        out.println("╚══════════════════════════════════════════════════╝");
        enterToContinue();
    }
    /**
     * @brief Displays all users stored in the hash table.
     *
     * This method iterates through each index of the hash table and displays all users
     * stored at each index, including any overflow entries.
     * Each user's name and email are printed in a formatted structure.
     *
     * @note Empty indexes are skipped, and only non-empty indexes are displayed.
     */

    private void displayUsersFromHashTable() {
        clearScreen();
        out.println("╔══════════════════════════════════════════════════╗");
        out.println("║               DISPLAY ALL USERS                  ║");
        out.println("╠══════════════════════════════════════════════════╣");
        for (int i = 0; i < TABLE_SIZE; i++) {
            if (hashTable[i] != null && !hashTable[i].isEmpty()) {
                out.println("╠═══ MAIN TABLE INDEX: " + i + " ═══════════════════════════╣");
                for (User u : hashTable[i]) {
                    out.println("║ Name     : " + u.getName());
                    out.println("║ Email    : " + u.getEmail());
                    out.println("╠──────────────────────────────────────────────────╣");                }
            }
        }

        enterToContinue();
    }

    /**
     * @brief Demonstrates user management using Quadratic Probing in hash tables.
     *
     * This method provides an interactive menu for demonstrating Quadratic Probing.
     * Users can:
     * - Add a user using Quadratic Probing collision resolution.
     * - Search for a user in the hash table.
     * - Display all users stored in the hash table.
     *
     * @note Quadratic Probing resolves collisions by exploring slots using a quadratic sequence.
     * @note Invalid inputs are handled gracefully, and the user is prompted to retry.
     */

    public void quadraticProbingDemo() {
        int choice;

        while (true) {
            clearScreen();
            out.println("╔══════════════════════════════════════════════════╗");
            out.println("║           QUADRATIC PROBING DEMO                 ║");
            out.println("╚══════════════════════════════════════════════════╝");
            out.println("1. Add User");
            out.println("2. Search User");
            out.println("3. Display Users");
            out.println("4. Exit");
            out.print("Enter your choice: ");

            choice = getInput();

            if (choice == -2) { handleInputError(); enterToContinue(); continue; }

            switch (choice) {
                case 1:
                    addUserWithQuadraticProbing();
                    break;
                case 2:
                    searchUserWithQuadraticProbing();
                    break;
                case 3:
                    displayUsersWithQuadraticProbing();
                    break;
                case 4:
                    out.println("Exiting Quadratic Probing Demo...");
                    return; // Menüden çıkış
                default:
                    out.println("Invalid choice. Please try again.");
                    enterToContinue();
            }
        }
    }
    /**
     * @brief Adds a new user to the hash table using Quadratic Probing.
     *
     * This method collects user details (name, surname, email, and password) and attempts to add the user
     * to the hash table using Quadratic Probing to resolve collisions. If the computed index is occupied,
     * the method searches alternative slots using a quadratic sequence.
     *
     * @note If a user with the same email already exists, the operation is aborted.
     * @note Quadratic Probing uses the formula: (index + i^2) % TABLE_SIZE to find an empty slot.
     */

    private void addUserWithQuadraticProbing() {
        clearScreen();
        out.println("╔══════════════════════════════════════════════════╗");
        out.println("║            USER REGISTRATION MENU                ║");
        out.println("╚══════════════════════════════════════════════════╝");

        User user = new User();

        out.print("➤ Enter Name       : ");
        user.setName(scanner.nextLine());

        out.print("➤ Enter Surname    : ");
        user.setSurname(scanner.nextLine());

        out.print("➤ Enter Email      : ");
        user.setEmail(scanner.nextLine());

        out.print("➤ Enter Password   : ");
        user.setPassword(scanner.nextLine());

        int index = hashFunction(user.getEmail());
        int i = 1;

        // Quadratic probing ile uygun bir boş indeks bul
        while (hashTable[index] != null) {
            LinkedList<User> bucket = hashTable[index];
            if (bucket != null) {
                for (User u : bucket) {
                    if (u.getEmail().equals(user.getEmail())) {
                        out.println("╔══════════════════════════════════════════════════╗");
                        out.println("║ ERROR: User already exists at index " + index +".║");
                        out.println("╚══════════════════════════════════════════════════╝");
                        return;
                    }
                }
            }

        }

        if (hashTable[index] == null) {
            hashTable[index] = new LinkedList<>();
        }

        hashTable[index].add(user);

        out.println("╔═════════════════════════════════════════════════════════════════════╗");
        out.println("║ SUCCESS: User added at index " + index + " using Quadratic Probing. ║");
        out.println("╚═════════════════════════════════════════════════════════════════════╝");

        enterToContinue();
    }
    /**
     * @brief Searches for a user in the hash table using Quadratic Probing.
     *
     * This method allows users to search for a specific user by providing their email and password.
     * If a collision occurs, Quadratic Probing is used to resolve it and continue searching
     * at subsequent slots determined by the quadratic formula.
     *
     * @note If the user is found, their name and surname are displayed.
     * @note If the user is not found after probing, an error message is displayed.
     */

    private void searchUserWithQuadraticProbing() {
        clearScreen();
        out.println("╔══════════════════════════════════════════════════╗");
        out.println("║                USER SEARCH MENU                  ║");
        out.println("╚══════════════════════════════════════════════════╝");

        out.print("➤ Enter Email to search: ");
        String email = scanner.nextLine();

        out.print("➤ Enter Password       : ");
        String password = scanner.nextLine();

        int index = hashFunction(email);
        int i = 1;

        while (hashTable[index] != null) {
            for (User u : hashTable[index]) {
                if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
                    out.println("╔══════════════════════════════════════════════════╗");
                    out.println("║ USER FOUND IN HASH TABLE                         ║");
                    out.println("╠══════════════════════════════════════════════════╣");
                    out.println("║ Name     : " + u.getName());
                    out.println("║ Surname  : " + u.getSurname());
                    out.println("╚══════════════════════════════════════════════════╝");
                    enterToContinue();
                    return;
                }
            }

            index = (index + i * i) % TABLE_SIZE;
            i++;

            if (i >= TABLE_SIZE) {
                break;
            }
        }

        out.println("╔══════════════════════════════════════════════════╗");
        out.println("║ ERROR: User not found in Hash Table.             ║");
        out.println("╚══════════════════════════════════════════════════╝");
        enterToContinue();
    }

    /**
     * @brief Displays all users stored in the hash table using Quadratic Probing.
     *
     * This method iterates through the hash table and displays all users stored at each index.
     * If an index contains multiple users (due to collision resolution via Quadratic Probing),
     * they are listed sequentially.
     *
     * @note Empty indexes are skipped, and only non-empty indexes are displayed.
     */

    private void displayUsersWithQuadraticProbing() {
        clearScreen();
        out.println("╔══════════════════════════════════════════════════╗");
        out.println("║              DISPLAY ALL USERS                   ║");
        out.println("╚══════════════════════════════════════════════════╝");

        for (int i = 0; i < TABLE_SIZE; i++) {
            if (hashTable[i] != null && !hashTable[i].isEmpty()) {
                out.println("╔═══ HASH TABLE INDEX: " + i + " ══════════════════════════╗");
                for (User u : hashTable[i]) {
                    out.println("║ Name  : " + u.getName());
                    out.println("║ Email : " + u.getEmail());
                    out.println("╠──────────────────────────────────────────────────╣");
                }
            }
        }

        out.println("╚══════════════════════════════════════════════════╝");
        enterToContinue();
    }
    /**
     * @brief Demonstrates user management using Double Hashing in hash tables.
     *
     * This method provides an interactive menu for demonstrating Double Hashing.
     * Users can:
     * - Add a user using Double Hashing collision resolution.
     * - Search for a user in the hash table.
     * - Display all users stored in the hash table.
     *
     * @note Double Hashing uses a secondary hash function to resolve collisions.
     * @note Invalid inputs are handled gracefully, and the user is prompted to retry.
     */

    public void doubleHashingDemo() {
        int choice;

        while (true) {
            clearScreen();
            out.println("╔══════════════════════════════════════════════════╗");
            out.println("║              DOUBLE HASHING DEMO                 ║");
            out.println("╚══════════════════════════════════════════════════╝");
            out.println("1. Add User");
            out.println("2. Search User");
            out.println("3. Display Users");
            out.println("4. Exit");
            out.print("Enter your choice: ");

            choice = getInput();

            if (choice == -2) {
                handleInputError();
                enterToContinue();
                continue;
            }

            switch (choice) {
                case 1:
                    addUserWithDoubleHashing();
                    break;
                case 2:
                    searchUserWithDoubleHashing();
                    break;
                case 3:
                    displayUsersWithDoubleHashing();
                    break;
                case 4:
                    out.println("Exiting Double Hashing Demo...");
                    return; // Menüden çıkış
                default:
                    out.println("Invalid choice. Please try again.");
                    enterToContinue();
            }
        }
    }

    /**
     * @brief Adds a new user to the hash table using Double Hashing.
     *
     * This method collects user details (name, surname, email, and password) and attempts to add
     * the user to the hash table using Double Hashing to resolve collisions.
     * If the computed index is occupied, a secondary hash function is used to determine the next slot.
     *
     * @note If a user with the same email already exists, the operation is aborted.
     * @note Double Hashing uses the formula: (index + i * stepSize) % TABLE_SIZE to find an empty slot.
     */

    public void addUserWithDoubleHashing() {
        clearScreen();
        out.println("╔══════════════════════════════════════════════════╗");
        out.println("║            USER REGISTRATION MENU                ║");
        out.println("╚══════════════════════════════════════════════════╝");

        User user = new User();

        out.print("➤ Enter Name       : ");
        user.setName(scanner.nextLine());

        out.print("➤ Enter Surname    : ");
        user.setSurname(scanner.nextLine());

        out.print("➤ Enter Email      : ");
        user.setEmail(scanner.nextLine());

        out.print("➤ Enter Password   : ");
        user.setPassword(scanner.nextLine());

        int index = hashFunction(user.getEmail());
        int stepSize = hashFunction2(user.getEmail());
        int i = 0;

        // Double hashing ile uygun bir boş indeks bul
        while (hashTable[index] != null) {
            LinkedList<User> bucket = hashTable[index];
            if (bucket != null) {
                for (User u : bucket) {
                    if (u.getEmail().equals(user.getEmail())) {
                        out.println("╔══════════════════════════════════════════════════╗");
                        out.println("║ ERROR: User already exists at index " + index +".║");
                        out.println("╚══════════════════════════════════════════════════╝");
                        return;
                    }
                }
            }

        }

        if (hashTable[index] == null) {
            hashTable[index] = new LinkedList<>();
        }

        hashTable[index].add(user);

        out.println("╔══════════════════════════════════════════════════════════════════╗");
        out.println("║ SUCCESS: User added at index " + index + " using Double Hashing. ║");
        out.println("╚══════════════════════════════════════════════════════════════════╝");

        enterToContinue();
    }

    /**
     * @brief Searches for a user in the hash table using Double Hashing.
     *
     * This method allows users to search for a specific user by providing their email and password.
     * If a collision occurs, Double Hashing is used to resolve it and continue searching
     * at subsequent slots determined by the secondary hash function.
     *
     * @note If the user is found, their name and surname are displayed.
     * @note If the user is not found after probing, an error message is displayed.
     */

    private void searchUserWithDoubleHashing() {
        clearScreen();
        out.println("╔══════════════════════════════════════════════════╗");
        out.println("║                USER SEARCH MENU                  ║");
        out.println("╚══════════════════════════════════════════════════╝");

        out.print("➤ Enter Email to search: ");
        String email = scanner.nextLine();

        out.print("➤ Enter Password       : ");
        String password = scanner.nextLine();

        int index = hashFunction(email);
        int stepSize = hashFunction2(email);
        int i = 0;

        while (hashTable[index] != null) {
            for (User u : hashTable[index]) {
                if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
                    out.println("╔══════════════════════════════════════════════════╗");
                    out.println("║ USER FOUND IN HASH TABLE                         ║");
                    out.println("╠══════════════════════════════════════════════════╣");
                    out.println("║ Name     : " + u.getName());
                    out.println("║ Surname  : " + u.getSurname());
                    out.println("╚══════════════════════════════════════════════════╝");
                    enterToContinue();
                    return;
                }
            }

            index = (index + stepSize) % TABLE_SIZE;
            i++;

            if (i >= TABLE_SIZE) {
                break;
            }
        }

        out.println("╔══════════════════════════════════════════════════╗");
        out.println("║ ERROR: User not found in Hash Table.             ║");
        out.println("╚══════════════════════════════════════════════════╝");
        enterToContinue();
    }

    /**
     * @brief Displays all users stored in the hash table using Double Hashing.
     *
     * This method iterates through the hash table and displays all users stored at each index.
     * If an index contains multiple users (due to collision resolution via Double Hashing),
     * they are listed sequentially.
     *
     * @note Empty indexes are skipped, and only non-empty indexes are displayed.
     */

    private void displayUsersWithDoubleHashing() {
        clearScreen();
        out.println("╔══════════════════════════════════════════════════╗");
        out.println("║              DISPLAY ALL USERS                   ║");
        out.println("╚══════════════════════════════════════════════════╝");

        for (int i = 0; i < TABLE_SIZE; i++) {
            if (hashTable[i] != null && !hashTable[i].isEmpty()) {
                out.println("╔═══ HASH TABLE INDEX: " + i + " ══════════════════════════╗");
                for (User u : hashTable[i]) {
                    out.println("║ Name  : " + u.getName());
                    out.println("║ Email : " + u.getEmail());
                    out.println("╠──────────────────────────────────────────────────╣");
                }
            }
        }

        out.println("╚══════════════════════════════════════════════════╝");
        enterToContinue();
    }

    /**
     * @brief Demonstrates user management using Linear Quotient Hashing.
     *
     * This method provides an interactive menu for demonstrating Linear Quotient Hashing.
     * Users can:
     * - Add a user using Linear Quotient collision resolution.
     * - Search for a user in the hash table.
     * - Display all users stored in the hash table.
     *
     * @note Linear Quotient Hashing resolves collisions by systematically probing subsequent slots.
     * @note Invalid inputs are handled gracefully, and the user is prompted to retry.
     */

    public void linearQuotientDemo() {
        int choice;

        while (true) {
            clearScreen();
            out.println("╔══════════════════════════════════════════════════╗");
            out.println("║              LINEAR QUOTIENT DEMO                ║");
            out.println("╚══════════════════════════════════════════════════╝");
            out.println("1. Add User");
            out.println("2. Search User");
            out.println("3. Display Users");
            out.println("4. Exit");
            out.print("Enter your choice: ");

            choice = getInput();

            if (choice == -2) {
                handleInputError();
                enterToContinue();
                continue;
            }

            switch (choice) {
                case 1:
                    addUserWithLinearQuotient();
                    break;
                case 2:
                    searchUserWithLinearQuotient();
                    break;
                case 3:
                    displayUsersWithLinearQuotient();
                    break;
                case 4:
                    out.println("Exiting Linear Quotient Demo...");
                    return; // Menüden çıkış
                default:
                    out.println("Invalid choice. Please try again.");
                    enterToContinue();
            }
        }
    }

    /**
     * @brief Adds a new user to the hash table using Linear Quotient Hashing.
     *
     * This method collects user details (name, surname, email, and password) and attempts to add
     * the user to the hash table using Linear Quotient collision resolution. If the computed index
     * is occupied, a fixed step size is added to find an alternative slot.
     *
     * @note If a user with the same email already exists, the operation is aborted.
     * @note Linear Quotient uses a fixed step size to resolve collisions.
     */

    private void addUserWithLinearQuotient() {
        clearScreen();
        out.println("╔══════════════════════════════════════════════════╗");
        out.println("║            USER REGISTRATION MENU                ║");
        out.println("╚══════════════════════════════════════════════════╝");

        User user = new User();

        out.print("➤ Enter Name       : ");
        user.setName(scanner.nextLine());

        out.print("➤ Enter Surname    : ");
        user.setSurname(scanner.nextLine());

        out.print("➤ Enter Email      : ");
        user.setEmail(scanner.nextLine());

        out.print("➤ Enter Password   : ");
        user.setPassword(scanner.nextLine());

        int index = hashFunction(user.getEmail());
        int stepSize = 3; // Linear Quotient sabit adım değeri
        int originalIndex = index;

        // Linear Quotient ile uygun bir boş indeks bul
        while (hashTable[index] != null) {
            LinkedList<User> bucket = hashTable[index];
            if (bucket != null) {
                for (User u : bucket) {
                    if (u.getEmail().equals(user.getEmail())) {
                        out.println("╔══════════════════════════════════════════════════╗");
                        out.println("║ ERROR: User already exists at index " + index +".║");
                        out.println("╚══════════════════════════════════════════════════╝");
                        return;
                    }
                }
            }


        }

        if (hashTable[index] == null) {
            hashTable[index] = new LinkedList<>();
        }

        hashTable[index].add(user);

        out.println("╔═══════════════════════════════════════════════════════════════════╗");
        out.println("║ SUCCESS: User added at index " + index + " using Linear Quotient. ║");
        out.println("╚═══════════════════════════════════════════════════════════════════╝");

        enterToContinue();
    }

    /**
     * @brief Searches for a user in the hash table using Linear Quotient Hashing.
     *
     * This method allows users to search for a specific user by providing their email and password.
     * If a collision occurs, Linear Quotient Hashing uses a fixed step size to resolve collisions
     * and continues searching in subsequent slots.
     *
     * @note If the user is found, their name and surname are displayed.
     * @note If the user is not found after probing, an error message is displayed.
     */

    private void searchUserWithLinearQuotient() {
        clearScreen();
        out.println("╔══════════════════════════════════════════════════╗");
        out.println("║                USER SEARCH MENU                  ║");
        out.println("╚══════════════════════════════════════════════════╝");

        out.print("➤ Enter Email to search: ");
        String email = scanner.nextLine();

        out.print("➤ Enter Password       : ");
        String password = scanner.nextLine();

        int index = hashFunction(email);
        int stepSize = 3;
        int originalIndex = index;

        while (hashTable[index] != null) {
            for (User u : hashTable[index]) {
                if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
                    out.println("╔══════════════════════════════════════════════════╗");
                    out.println("║ USER FOUND IN HASH TABLE                         ║");
                    out.println("╠══════════════════════════════════════════════════╣");
                    out.println("║ Name     : " + u.getName());
                    out.println("║ Surname  : " + u.getSurname());
                    out.println("╚══════════════════════════════════════════════════╝");
                    enterToContinue();
                    return;
                }
            }

            index = (index + stepSize) % TABLE_SIZE;

            if (index == originalIndex) {
                break;
            }
        }

        out.println("╔══════════════════════════════════════════════════╗");
        out.println("║ ERROR: User not found in Hash Table.             ║");
        out.println("╚══════════════════════════════════════════════════╝");
        enterToContinue();
    }

    /**
     * @brief Displays all users stored in the hash table using Linear Quotient Hashing.
     *
     * This method iterates through each index of the hash table and displays all users
     * stored at each index. If an index contains multiple users (due to Linear Quotient
     * collision resolution), they are displayed sequentially.
     *
     * @note Empty indexes are skipped, and only non-empty indexes are displayed.
     */

    private void displayUsersWithLinearQuotient() {
        clearScreen();
        out.println("╔══════════════════════════════════════════════════╗");
        out.println("║              DISPLAY ALL USERS                   ║");
        out.println("╚══════════════════════════════════════════════════╝");

        for (int i = 0; i < TABLE_SIZE; i++) {
            if (hashTable[i] != null && !hashTable[i].isEmpty()) {
                out.println("╔═══ HASH TABLE INDEX: " + i + " ══════════════════════════╗");
                for (User u : hashTable[i]) {
                    out.println("║ Name  : " + u.getName());
                    out.println("║ Email : " + u.getEmail());
                    out.println("╠──────────────────────────────────────────────────╣");
                }
            }
        }

        out.println("╚══════════════════════════════════════════════════╝");
        enterToContinue();
    }

    /**
     * @brief Demonstrates user management using Brent's Method in hash tables.
     *
     * This method provides an interactive menu for demonstrating Brent's Method.
     * Users can:
     * - Add a user using Brent's Method for collision resolution.
     * - Search for a user in the hash table.
     * - Display all users stored in the hash table.
     *
     * @note Brent's Method minimizes the number of probes during collision resolution
     *       by optimizing the reallocation strategy.
     * @note Invalid inputs are handled gracefully, and the user is prompted to retry.
     */

    public void brentsMethodDemo() {
        int choice;

        while (true) {
            clearScreen();
            out.println("╔══════════════════════════════════════════════════╗");
            out.println("║               BRENT'S METHOD DEMO                ║");
            out.println("╚══════════════════════════════════════════════════╝");
            out.println("1. Add User");
            out.println("2. Search User");
            out.println("3. Display Users");
            out.println("4. Exit");
            out.print("Enter your choice: ");

            choice = getInput();

            if (choice == -2) {
                handleInputError();
                enterToContinue();
                continue;
            }

            switch (choice) {
                case 1:
                    addUserWithBrentsMethod();
                    break;
                case 2:
                    searchUserWithBrentsMethod();
                    break;
                case 3:
                    displayUsersWithBrentsMethod();
                    break;
                case 4:
                    out.println("Exiting Brent's Method Demo...");
                    return; // Menüden çıkış
                default:
                    out.println("Invalid choice. Please try again.");
                    enterToContinue();
            }
        }
    }

    /**
     * @brief Adds a new user to the hash table using Brent's Method.
     *
     * This method collects user details (name, surname, email, and password) and attempts to add
     * the user to the hash table using Brent's Method. Brent's Method optimizes collision resolution
     * by minimizing the total number of probes required during reallocation.
     *
     * @note If a user with the same email already exists, the operation is aborted.
     * @note Brent's Method evaluates two potential positions during collision resolution and selects the optimal one.
     */

    private void addUserWithBrentsMethod() {
        clearScreen();
        out.println("╔══════════════════════════════════════════════════╗");
        out.println("║            USER REGISTRATION MENU                ║");
        out.println("╚══════════════════════════════════════════════════╝");

        User user = new User();

        out.print("➤ Enter Name       : ");
        user.setName(scanner.nextLine());

        out.print("➤ Enter Surname    : ");
        user.setSurname(scanner.nextLine());

        out.print("➤ Enter Email      : ");
        user.setEmail(scanner.nextLine());

        out.print("➤ Enter Password   : ");
        user.setPassword(scanner.nextLine());

        int index = hashFunction(user.getEmail());
        int stepSize = secondHashFunction(user.getEmail());
        int originalIndex = index;

        // Brent's Method ile uygun bir boş indeks bul
        int i = 1;
        int trialIndex1, trialIndex2;
        boolean placed = false;

        while (hashTable[index] != null) {
            for (User u : hashTable[index]) {
                if (u.getEmail().equals(user.getEmail())) {
                    out.println("╔══════════════════════════════════════════════════╗");
                    out.println("║ ERROR: User already exists at index " + index +".║");
                    out.println("╚══════════════════════════════════════════════════╝");
                    return;
                }
            }

            trialIndex1 = (index + i * stepSize) % TABLE_SIZE; // İlk alternatif konum
            trialIndex2 = (originalIndex + i) % TABLE_SIZE;    // İkinci alternatif konum

            if (hashTable[trialIndex1] == null) {
                index = trialIndex1;
                placed = true;
                break;
            } else if (hashTable[trialIndex2] == null) {
                index = trialIndex2;
                placed = true;
                break;
            }

        }

        if (hashTable[index] == null) {
            hashTable[index] = new LinkedList<>();
        }

        hashTable[index].add(user);

        out.println("╔══════════════════════════════════════════════════════════════════╗");
        out.println("║ SUCCESS: User added at index " + index + " using Brent's Method. ║");
        out.println("╚══════════════════════════════════════════════════════════════════╝");

        enterToContinue();
    }

    /**
     * @brief Searches for a user in the hash table using Brent's Method.
     *
     * This method allows users to search for a specific user by providing their email and password.
     * Brent's Method uses a combination of primary and secondary hash functions to optimize
     * collision resolution during the search process.
     *
     * @note If the user is found, their name and surname are displayed.
     * @note If the user is not found after probing, an error message is displayed.
     */

    private void searchUserWithBrentsMethod() {
        clearScreen();
        out.println("╔══════════════════════════════════════════════════╗");
        out.println("║                USER SEARCH MENU                  ║");
        out.println("╚══════════════════════════════════════════════════╝");

        out.print("➤ Enter Email to search: ");
        String email = scanner.nextLine();

        out.print("➤ Enter Password       : ");
        String password = scanner.nextLine();

        int index = hashFunction(email);
        int stepSize = secondHashFunction(email);

        int i = 0;
        while (hashTable[index] != null) {
            for (User u : hashTable[index]) {
                if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
                    out.println("╔══════════════════════════════════════════════════╗");
                    out.println("║ USER FOUND IN HASH TABLE                         ║");
                    out.println("╠══════════════════════════════════════════════════╣");
                    out.println("║ Name     : " + u.getName());
                    out.println("║ Surname  : " + u.getSurname());
                    out.println("╚══════════════════════════════════════════════════╝");
                    enterToContinue();
                    return;
                }
            }

            index = (index + stepSize) % TABLE_SIZE;
            i++;
            if (i >= TABLE_SIZE) {
                break;
            }
        }

        out.println("╔══════════════════════════════════════════════════╗");
        out.println("║ ERROR: User not found in Hash Table.             ║");
        out.println("╚══════════════════════════════════════════════════╝");
        enterToContinue();
    }

    /**
     * @brief Displays all users stored in the hash table using Brent's Method.
     *
     * This method iterates through the hash table and displays all users stored at each index.
     * If an index contains multiple users (due to Brent's Method collision resolution),
     * they are displayed sequentially.
     *
     * @note Empty indexes are skipped, and only non-empty indexes are displayed.
     */

    private void displayUsersWithBrentsMethod() {
        clearScreen();
        out.println("╔══════════════════════════════════════════════════╗");
        out.println("║              DISPLAY ALL USERS                   ║");
        out.println("╚══════════════════════════════════════════════════╝");

        for (int i = 0; i < TABLE_SIZE; i++) {
            if (hashTable[i] != null && !hashTable[i].isEmpty()) {
                out.println("╔═══ HASH TABLE INDEX: " + i + " ══════════════════════════╗");
                for (User u : hashTable[i]) {
                    out.println("║ Name  : " + u.getName());
                    out.println("║ Email : " + u.getEmail());
                    out.println("╠──────────────────────────────────────────────────╣");
                }
            }
        }

        out.println("╚══════════════════════════════════════════════════╝");
        enterToContinue();
    }

    /**
     * @brief Secondary hash function for double hashing.
     *
     * This method calculates a secondary hash value for the given email string.
     * It sums the ASCII values of each character in the email and calculates a modulus
     * with (TABLE_SIZE - 1). This value is used as a step size in double hashing.
     *
     * @param email The email string to be hashed.
     * @return int The secondary hash value for the given email.
     *
     * Example Usage:
     * {@code
     *     int index = hashFunction2("example@example.com");
     * }
     */
    public static int hashFunction2(String email) {
        int hash = 0;
        for (int i = 0; i < email.length(); i++) {
            hash = (hash + email.charAt(i)) % (TABLE_SIZE - 1);
        }
        return (TABLE_SIZE - 1 - hash);
    }

    /**
     * @brief Alternative secondary hash function for double hashing.
     *
     * This method calculates an alternative secondary hash value for the given email string.
     * It multiplies the current hash value by 31 and adds the ASCII value of each character,
     * then calculates the modulus with TABLE_SIZE to ensure the hash value remains within bounds.
     *
     * @param email The email string to be hashed.
     * @return int The secondary hash value for the given email.
     *
     * Example Usage:
     * {@code
     *     int index = secondHashFunction("example@example.com");
     * }
     */
    public static int secondHashFunction(String email) {
        int hash = 0;
        for (int i = 0; i < email.length(); i++) {
            hash = (hash * 31 + email.charAt(i)) % TABLE_SIZE;
        }
        return (TABLE_SIZE - 1 - hash);
    }

}