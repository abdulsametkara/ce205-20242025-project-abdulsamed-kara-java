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
    private static LinkedList<User>[] hashTable = new LinkedList[TABLE_SIZE];
    private static ArrayList<TaskInfo> taskList = new ArrayList<>();
    private static int taskCount = 0;  // Global olarak tanımlandı
    private static final int MAX_TASKS = 100;
    private static TaskNode head = null;
    private static TaskNode tail = null;
    private static XORLinkedList xorLinkedList = new XORLinkedList();
    private PriorityQueue<Assignment> deadlineHeap = new PriorityQueue<>();





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
                    //progressiveOverflowDemo
                    break;
                case 2:
                    linearProbingDemo();
                    enterToContinue();
                    break;
                case 3:
                    //quadraticProbingDemo
                    enterToContinue();
                    break;
                case 4:
                    //doubleHashingDemo
                    enterToContinue();
                    break;
                case 5:
                    //useOfBuckets
                    enterToContinue();
                    break;
                case 6:
                    //linearQuotientDemo
                    enterToContinue();
                    break;
                case 7:
                    //brentsMethodDemo
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

    public int loginUser(User loginUser, String pathFileUsers) {

        User loggedUser; // Sınıf genelinde erişilebilecek bir değişken

        File file = new File(pathFileUsers);
        if (!file.exists()) {
            out.println("Failed to open user file.");
            return 0;
        }

        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            int userCount = raf.readInt();
            User[] users = new User[userCount];

            for (int i = 0; i < userCount; ++i) {
                users[i] = new User();
                users[i].readFromFile(raf); // Kullanıcıyı dosyadan oku
            }

            // Hash tablosunu doldur
            for (int i = 0; i < userCount; ++i) {
                insertUserToHashTable(users[i]); // Hash tablosuna kullanıcıyı ekle
            }

            User foundUser = searchUserInHashTable(loginUser.getEmail(), loginUser.getPassword());
            if (foundUser != null) {
                out.println("Login successful.");
                loggedUser = foundUser; // Başarılı giriş
                return 1;
            } else {
                out.println("Incorrect email or password.");
                return 0;
            }
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
            hash = (hash + email.charAt(i)) % TABLE_SIZE;
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
    public User searchUserInHashTable(String email, String password) {
        int index = hashFunction(email);
        int originalIndex = index;

        for (int i = 0; i < TABLE_SIZE; i++) {
            LinkedList<User> bucket = hashTable[index];

            // Kullanıcıyı arama
            for (User user : bucket) {
                if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                    return user; // Kullanıcı bulundu
                }
            }

            // Bir sonraki indexe geç
            index = (index + 1) % TABLE_SIZE;

            if (index == originalIndex) {
                break;
            }
        }

        return null; // Kullanıcı bulunamadı
    }


    public int registerUser(User user, String pathFileUser) {
        File file = new File(pathFileUser);
        int userCount = 0;
        ArrayList<User> users = new ArrayList<>();  // users listesini başlatıyoruz

        try {
            if (file.exists()) {
                RandomAccessFile raf = new RandomAccessFile(file, "rw");
                if (raf.length() > 0) {  // Dosyanın boyutunun sıfırdan büyük olup olmadığını kontrol ediyoruz
                    userCount = raf.readInt();  // Kullanıcı sayısını oku

                    // Kullanıcıları dosyadan oku ve listeye ekle
                    for (int i = 0; i < userCount; i++) {
                        User tempUser = new User();
                        tempUser.readFromFile(raf);  // Kullanıcıyı dosyadan oku
                        users.add(tempUser);  // Kullanıcıyı listeye ekle
                    }

                    // Hash tablosunu doldur
                    for (int i = 0; i < userCount; ++i) {
                        insertUserToHashTable(users.get(i));  // Kullanıcıyı hash tablosuna ekle
                    }

                    // Kullanıcı zaten var mı kontrol et
                    if (searchUserInHashTable(user.email, user.password) != null) {
                        out.println("User already exists.");
                        raf.close();
                        return 0;
                    }
                } else {
                    out.println("The file is empty, creating a new one.");
                }
                raf.close();  // Dosya kapatılmalı
            } else {
                file.createNewFile();  // Dosya yoksa yeni dosya oluştur
            }

            // Yeni kullanıcıyı dosyaya ekle
            user.id = userCount + 1;  // Yeni kullanıcı ID'si
            insertUserToHashTable(user);  // Hash tablosuna ekle

            // Kullanıcıyı dosyaya ekleyelim
            userCount++;
            users.add(user);

            // Dosyayı açıp kullanıcıları yazalım
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            raf.seek(0);  // Dosyanın başına git
            raf.writeInt(userCount);  // Kullanıcı sayısını dosyaya yaz
            for (int i = 0; i < userCount; i++) {
                users.get(i).writeToFile(raf);  // Kullanıcıyı dosyaya yaz
            }

            out.println("User registered successfully: Welcome " + user.name + " " + user.surname);
            raf.close();  // Dosyayı kapat

            return 1;
        } catch (IOException e) {
            e.printStackTrace();  // Hata mesajını daha ayrıntılı yazdırmak için
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
     * @brief Inserts a user into the hash table using linear probing.
     *
     * @param user The user to be added to the hash table.
     */
    public void insertUserToHashTableWithLinearProbing(User user) {
        int index = hashFunction(user.getEmail());
        int originalIndex = index;

        out.println("Inserting user with email: " + user.getEmail());

        // Linear probing to find an empty spot or existing user
        for (int i = 0; i < TABLE_SIZE; i++) {
            LinkedList<User> bucket = hashTable[index];

            // Kullanıcı zaten mevcut mu kontrol et
            for (User existingUser : bucket) {
                if (existingUser.getEmail().equals(user.getEmail())) {
                    out.println("User already exists at index " + index);
                    return;
                }
            }

            // Eğer bucket boşsa kullanıcıyı ekle
            if (bucket.isEmpty()) {
                bucket.add(user);
                out.println("User added at index " + index);
                return;
            }

            // Bir sonraki indexe geç
            index = (index + 1) % TABLE_SIZE;

            if (index == originalIndex) {
                out.println("Hash table is full, cannot add more users.");
                return;
            }
        }
    }




    /**
     * @brief Demonstrates the usage of linear probing for user hash table management.
     */
    public void linearProbingDemo() {
        out.println("Linear Probing is active for user hash table management.");

        Scanner scanner = new Scanner(System.in);

        User user = new User();

        out.print("Name: ");
        user.setName(scanner.nextLine());

        out.print("Surname: ");
        user.setSurname(scanner.nextLine());

        out.print("Email: ");
        user.setEmail(scanner.nextLine());

        out.print("Password: ");
        user.setPassword(scanner.nextLine());

        insertUserToHashTableWithLinearProbing(user);

        out.println("User insertion complete.");
        enterToContinue();
    }






}