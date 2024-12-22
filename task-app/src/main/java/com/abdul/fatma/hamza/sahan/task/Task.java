package com.abdul.fatma.hamza.sahan.task;
import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;


public class Task {

    /** Scanner object for user input. */
    public Scanner scanner;
    /** PrintStream object for output. */
    public PrintStream out;
    private NotificationManager notificationManager; // NotificationManager nesnesi eklendi

    public Task(Scanner scanner, PrintStream out) {
        this.scanner = scanner;
        this.out = out;
        this.notificationManager = new NotificationManager(scanner, out); // Nesne başlatıldı

    }
    public boolean isTestMode = false;



    public static final int TABLE_SIZE = 100;  // Hash tablosunun boyutu
    public static LinkedList<User>[] hashTable = new LinkedList[TABLE_SIZE];
    private static ArrayList<TaskInfo> taskList = new ArrayList<>();
    private static int taskCount = 0;  // Global olarak tanımlandı
    private static final int MAX_TASKS = 100;
    public static TaskNode head = null;
    public static TaskNode tail = null;
    private static XORLinkedList xorLinkedList = new XORLinkedList();
    public PriorityQueue<Assignment> deadlineHeap = new PriorityQueue<>();
    private static final int OVERFLOW_SIZE = 20;
    private static ArrayList<User> overflowArea = new ArrayList<>(OVERFLOW_SIZE);








    private static DoubleLinkedList taskDoublyLinkedList = new DoubleLinkedList();

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

    public void mainMenu(String pathFileUsers) {
        int choice;

        while (true) {
            clearScreen(); // Ekranı temizle
            openingScreenMenu(); // Açılış menüsünü göster
            choice = getInput(); // Kullanıcıdan seçim al

            if (choice == -2) {
                handleInputError(); // Hata durumunda mesaj ver
                enterToContinue(); // Devam etmek için bekle
                continue;
            }

            switch (choice) {
                case 1:
                    clearScreen();
                    if (loginUserMenu(pathFileUsers)) {
                        userOptionsMenu(); // Kullanıcı giriş yaptıysa ana menüyü göster
                    }
                    break;

                case 2:
                    clearScreen();
                    registerUserMenu(pathFileUsers); // Kullanıcı kayıt menüsünü göster
                    break;

                case 3:
                    out.println("Exit Program");
                    return; // Programdan çık
                default:
                    out.println("Invalid choice. Please try again.");
                    enterToContinue(); // Geçersiz seçimde devam etmek için bekle
                    break;
            }
        }
    }

    public void userOptionsMenu() {
        int choice;

        while (true) {
            printMainMenu(); // Ana menüyü gösterir
            choice = getInput(); // Kullanıcı girişini alır

            if (choice == -2) {
                handleInputError(); // Giriş hatası varsa işlem yapılır
                enterToContinue(); // Devam etmek için beklenir
                continue;
            }

            switch (choice) {
                case 1:
                    createTaskMenu(); // createTaskMenu fonksiyonunu çağırır
                    break;
                case 2:
                    deadlineSettingsMenu(); // deadlineSettingsMenu fonksiyonunu çağırır
                    break;
                case 3:
                    reminderSystemMenu(); // reminderSystemMenu fonksiyonunu çağırır
                    break;
                case 4:
                    taskPrioritizationMenu(); // taskPrioritizationMenu fonksiyonunu çağırır
                    break;
                case 5:
                    algorithmsMenu(); // algorithmsMenu fonksiyonunu çağırır
                    break;
                case 6:
                    return; // Programdan çıkar
                default:
                    clearScreen(); // Geçersiz giriş için ekran temizlenir
                    out.println("Invalid choice. Please try again.");
                    enterToContinue(); // Devam etmek için beklenir
                    break;
            }
        }
    }

    public void createTaskMenu() {
        int maxTasks = 100;
        int choice;

        TaskQueue taskQueue = new TaskQueue(); // TaskQueue'yu burada tanımladık


        while (true) {
            printCreateTaskMenu(); // Menü gösterilir
            choice = getInput(); // Kullanıcı girişini alır

            if (choice == -2) {
                handleInputError(); // Giriş hatası durumunda işlem yapılır
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
                    // TaskQueue'yu parametre olarak geçiriyoruz
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
                        printDependencies(taskList, taskId); // Bağımlılıkları yazdır
                    } else {
                        out.println("Invalid task ID.");
                    }
                    enterToContinue(); // Kullanıcıya devam için bekletme
                    break;
                case 5:
                    StringBuilder output = new StringBuilder();
                    SCCAnalyzer analyzer = new SCCAnalyzer();

                    // SCC analizi yap
                    analyzer.analyzeSCC(taskList, output);

                    // Sonuçları yazdır
                    out.println(output.toString());
                    enterToContinue();
                    break;

                case 6:
                    searchTasksByKeyword(taskList); // Görev listesindeki anahtar kelime arama fonksiyonunu çağır
                    enterToContinue();
                    break;
                case 7:
                    navigateTaskList(taskDoublyLinkedList); // Çift bağlı liste üzerinde gezinme fonksiyonunu çağır
                    enterToContinue();
                    break;
                case 8:
                    XORLinkedList xorList = new XORLinkedList();
                    xorList.loadTasksToXORList("tasks.bin");
                    xorList.navigateXORList();
                    enterToContinue();
                    break;
                case 9:
                    return; // Menüden çıkış
                default:
                    clearScreen();
                    out.println("Invalid choice. Please try again.");
                    enterToContinue();
                    break;
            }

        }

    }

    public void deadlineSettingsMenu() {
        int maxTasks = 100;
        int choice;

        while (true) {
            printDeadlineSettingsMenu(); // Menü gösterilir
            choice = getInput(); // Kullanıcı girişini alır

            if (choice == -2) {
                handleInputError(); // Giriş hatası durumunda işlem yapılır
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
                    return; // Menüden çıkış
                default:
                    clearScreen();
                    out.println("Invalid choice. Please try again.");
                    enterToContinue();
                    break;
            }

        }

    }

    public void reminderSystemMenu() {
        int maxTasks = 100;

        int choice;

        while (true) {
            printReminderSystemMenu(); // Menü gösterilir
            choice = getInput(); // Kullanıcı girişini alır

            if (choice == -2) {
                handleInputError(); // Giriş hatası durumunda işlem yapılır
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

    public void taskPrioritizationMenu() {
        int maxTasks = 100;

        int choice;

        while (true) {
            printTaskPrioritizationMenu(); // Menü gösterilir
            choice = getInput(); // Kullanıcı girişini alır

            if (choice == -2) {
                handleInputError(); // Giriş hatası durumunda işlem yapılır
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
                    return; // Menüden çıkış
                default:
                    clearScreen();
                    out.println("Invalid choice. Please try again.");
                    enterToContinue();
                    break;
            }

        }

    }

    public void algorithmsMenu() {
        int maxTasks = 100;

        int choice;

        while (true) {
            printAlgorithmsMenu(); // Menü gösterilir
            choice = getInput(); // Kullanıcı girişini alır

            if (choice == -2) {
                handleInputError(); // Giriş hatası durumunda işlem yapılır
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
                    return; // Menüden çıkış
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


    public boolean loginUserMenu(String pathFileUsers) {

        User userInput = new User();

        out.print("Enter email: ");
        userInput.setEmail(scanner.nextLine());

        out.print("Enter password: ");
        userInput.setPassword(scanner.nextLine());

        // loginUser sonucunu değerlendirip boolean olarak dönüyoruz
        return loginUser(userInput, pathFileUsers) == 1; // 1 başarıyı temsil ediyorsa
    }

    public static int hashFunction(String email) {
        int hash = 0;
        for (int i = 0; i < email.length(); i++) {
            hash = (hash + email.charAt(i)) % TABLE_SIZE;  // Modül ile hash değeri hesaplanır
        }
        return hash;
    }


    public static void insertUserToHashTable(User user) {
        int index = hashFunction(user.getEmail());

        // Yeni kullanıcıyı eklemek için bağlı liste başına yerleştiriyoruz
        if (hashTable[index] == null) {
            hashTable[index] = new LinkedList<>();
        }

        hashTable[index].add(user); // Kullanıcıyı ilgili index'teki listeye ekliyoruz
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


    public int registerUserMenu(String pathFileUsers) {

        User newUser = new User();

        // Adı al
        out.print("Enter Name: ");
        newUser.setName(scanner.nextLine());

        // Soyadı al
        out.print("Enter Surname: ");
        newUser.setSurname(scanner.nextLine());

        // E-posta al
        out.print("Enter email: ");
        newUser.setEmail(scanner.nextLine());

        // Şifre al
        out.print("Enter password: ");
        newUser.setPassword(scanner.nextLine());

        // Kullanıcıyı kaydet
        return registerUser(newUser, pathFileUsers);
    }

    public int addTask(ArrayList<TaskInfo> taskList, DoubleLinkedList taskDoublyLinkedList, int taskCount, int maxTasks) {
        if (taskCount >= maxTasks) {
            out.println("Task list is full. Cannot add more tasks.");
            return 0;
        }

        // Yeni görevin ID'sini mevcut görevler arasında en yüksek ID'nin bir fazlası olarak belirle
        int newId = getNewTaskId(taskList);

        TaskInfo newTask = new TaskInfo();
        newTask.setId(newId); // Yeni görevin ID'sini ayarla



        // Görev bilgilerini alıyoruz
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
        scanner.nextLine(); // Consume newline left by nextInt()

        // Bağımlılıkları alıyoruz
        for (int i = 0; i < newTask.getDependencyCount(); i++) {
            out.print("Enter dependency task ID for dependency " + (i + 1) + ": ");
            newTask.getDependencies()[i] = scanner.nextInt();
            scanner.nextLine(); // Consume newline
        }

        // Yeni görevi taskList'e ekliyoruz
        taskList.add(newTask);

        // Yeni görevi taskDoublyLinkedList'e ekliyoruz
        taskDoublyLinkedList.addTaskToLinkedList(newTask);

        // taskCount'u artırıyoruz
        taskCount++;

        // Görevleri kaydediyoruz
        saveTasks(taskList);

        out.println("Task added and saved successfully!");
        return 1;
    }



    public void saveTasks(ArrayList<TaskInfo> taskList) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream("tasks.bin"))) {
            dos.writeInt(taskList.size()); // Görev sayısını yaz
            for (TaskInfo task : taskList) {
                dos.writeInt(task.getId());                // ID
                dos.writeUTF(task.getName());              // Name
                dos.writeUTF(task.getDescription());       // Description
                dos.writeUTF(task.getCategory());          // Category
                dos.writeUTF(task.getDueDate());           // Due Date
                dos.writeInt(task.getDependencyCount());   // Dependency Count
                dos.writeInt(task.getImportanceId());      // Importance ID (Yeni Eklendi)
                for (int dep : task.getDependencies()) {
                    dos.writeInt(dep);                     // Dependencies
                }
            }
        } catch (IOException e) {
            out.println("Error saving tasks: " + e.getMessage());
        }
    }



    public int loadTasks(ArrayList<TaskInfo> taskList) {
        File file = new File("tasks.bin");
        if (!file.exists()) {
            out.println("No previous tasks found.");
            return 0;
        }

        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            int taskCount = raf.readInt();  // Dosyadan görev sayısını oku
            out.println("Task count read from file: " + taskCount);  // Log ekledik

            for (int i = 0; i < taskCount; i++) {
                TaskInfo task = new TaskInfo();
                task.readFromFile(raf);  // Dosyadan her bir görevi oku
                out.println("Task loaded: ID=" + task.getId() + ", Name=" + task.getName());  // Log ekledik
                taskList.add(task);  // Görevi listeye ekle
            }

            out.println(taskCount + " tasks loaded successfully!");
            return taskCount;
        } catch (IOException e) {
            out.println("Error reading tasks file: " + e.getMessage());
            return 0;
        }
    }






    public void navigateTaskList(DoubleLinkedList taskList) {
        if (taskList.head == null) {
            out.println("No tasks available for navigation.");
            return;
        }


        TaskNode current = taskList.head; // Listenin başından başlıyoruz
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

            if (choice == 1) { // Bir sonraki göreve git
                if (current.next != null) {
                    current = current.next;
                } else {
                    out.println("This is the last task.");
                }
            } else if (choice == 2) { // Bir önceki göreve git
                if (current.prev != null) {
                    current = current.prev;
                } else {
                    out.println("This is the first task.");
                }
            } else if (choice == 3) { // Gezintiden çık
                break;
            } else { // Geçersiz seçim
                out.println("Invalid choice. Try again.");
            }
        }
    }


    // addTaskToList: Görevi çift bağlı listeye ekler
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

    public void viewTask(ArrayList<TaskInfo> taskList) {
        if (taskList.isEmpty()) {
            out.println("No tasks found. The task list is empty.");
            enterToContinue();
            return;
        }

        out.println("\n--- List of Tasks ---");

        // taskList içindeki tüm görevleri sırasıyla yazdırıyoruz
        for (TaskInfo task : taskList) {
            out.println("ID: " + task.getId());
            out.println("Name: " + task.getName());
            out.println("Description: " + task.getDescription());
            out.println("Category: " + task.getCategory());
            out.println("Due Date: " + task.getDueDate());
            out.println("---------------------------");
        }

        enterToContinue();  // Kullanıcıya devam etmesi için bekletme
    }

    public static int getNewTaskId(ArrayList<TaskInfo> taskList) {
        int maxId = 0;

        // Mevcut görevlerin en yüksek ID'sini bul
        for (TaskInfo task : taskList) {
            if (task.getId() > maxId) {
                maxId = task.getId();
            }
        }

        // Yeni ID en yüksek ID'nin bir fazlası olmalı
        return maxId + 1;
    }

    public void categorizeTask(ArrayList<TaskInfo> taskList) {


        // Kategoriyi kullanıcıdan al
        out.print("Enter category to filter: ");
        String category = scanner.nextLine().trim();

        boolean found = false;

        out.println("\n--- Tasks in Category '" + category + "' ---");

        // taskList içindeki görevleri kontrol et
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

        enterToContinue(); // Kullanıcıya devam etmek için bekleme ekranı
    }

    public void printDependencies(ArrayList<TaskInfo> taskList, int startTaskId) {
        boolean[] visited = new boolean[taskList.size()]; // Ziyaret edilen görevler için dizi

        out.println("Dependencies for Task " + startTaskId + ":");
        printDependenciesUtil(taskList, startTaskId, visited); // Yardımcı fonksiyonu çağır
    }

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
     * @brief Demonstrates user hash table management with linear probing.
     * This menu provides options for adding, searching, and displaying users.
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
     * @brief Adds a user to the hash table using linear probing for collision resolution.
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
     * @brief Searches for a user in the hash table using linear probing.
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
     * @brief Displays all users from the hash table using linear probing.
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
     * @brief Demonstrates user hash table management with progressive overflow handling.
     * This menu provides options for adding, searching, and displaying users.
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
     * @brief Adds a user to the hash table using progressive overflow handling.
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
     * @brief Searches for a user in the hash table or overflow area.
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
                    out.println("\n╔══════════════════════════════════════════════════╗");
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

        out.println("\n╔══════════════════════════════════════════════════╗");
        out.println("║ ERROR: User not found in Main Table or Overflow. ║");
        out.println("╚══════════════════════════════════════════════════╝");
        enterToContinue();
    }

    /**
     * @brief Displays all users from the hash table and overflow area.
     */
    private void displayUsersFromHashTable() {
        clearScreen();
        out.println("╔══════════════════════════════════════════════════╗");
        out.println("║               DISPLAY ALL USERS                  ║");
        out.println("╠══════════════════════════════════════════════════╣");
        for (int i = 0; i < TABLE_SIZE; i++) {
            if (hashTable[i] != null && !hashTable[i].isEmpty()) {
                out.println("╠═══ MAIN TABLE INDEX: " + i + " ═════════════════════════════╣");
                for (User u : hashTable[i]) {
                    out.println("║ Name     : " + u.getName());
                    out.println("║ Email    : " + u.getEmail());
                    out.println("╠──────────────────────────────────────────────────╣");                }
            }
        }

        enterToContinue();
    }

    /**
     * @brief Demonstrates user hash table management with quadratic probing handling.
     * This menu provides options for adding, searching, and displaying users.
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
     * @brief Adds a user to the hash table using quadratic probing handling.
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
                        out.println("║ ERROR: User already exists at index " + index + ". ║");
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

        out.println("╔══════════════════════════════════════════════════╗");
        out.println("║ SUCCESS: User added at index " + index + " using Quadratic Probing. ║");
        out.println("╚══════════════════════════════════════════════════╝");

        enterToContinue();
    }

    /**
     * @brief Searches for a user in the hash table using quadratic probing.
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
     * @brief Displays all users from the hash table using quadratic probing.
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
     * @brief Demonstrates user hash table management with double hashing for collision resolution.
     * This menu provides options for adding, searching, and displaying users.
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
     * @brief Adds a user to the hash table using double hashing for collision resolution.
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
                        out.println("║ ERROR: User already exists at index " + index + ". ║");
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

        out.println("╔══════════════════════════════════════════════════╗");
        out.println("║ SUCCESS: User added at index " + index + " using Double Hashing. ║");
        out.println("╚══════════════════════════════════════════════════╝");

        enterToContinue();
    }

    /**
     * @brief Searches for a user in the hash table using double hashing.
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
     * @brief Displays all users from the hash table using double hashing.
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
     * @brief Demonstrates user hash table management with linear quotient collision resolution.
     * This menu provides options for adding, searching, and displaying users.
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
     * @brief Adds a user to the hash table using linear quotient collision resolution.
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
                        out.println("║ ERROR: User already exists at index " + index + ". ║");
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

        out.println("╔══════════════════════════════════════════════════╗");
        out.println("║ SUCCESS: User added at index " + index + " using Linear Quotient. ║");
        out.println("╚══════════════════════════════════════════════════╝");

        enterToContinue();
    }

    /**
     * @brief Searches for a user in the hash table using linear quotient.
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
     * @brief Displays all users from the hash table using linear quotient.
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
     * @brief Demonstrates user hash table management with Brent's Method collision resolution.
     * This menu provides options for adding, searching, and displaying users.
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
     * @brief Adds a user to the hash table using Brent's Method for collision resolution.
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
                    out.println("║ ERROR: User already exists at index " + index + ". ║");
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

        out.println("╔══════════════════════════════════════════════════╗");
        out.println("║ SUCCESS: User added at index " + index + " using Brent's Method. ║");
        out.println("╚══════════════════════════════════════════════════╝");

        enterToContinue();
    }

    /**
     * @brief Searches for a user in the hash table using Brent's Method.
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
     * @brief Displays all users from the hash table using Brent's Method.
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