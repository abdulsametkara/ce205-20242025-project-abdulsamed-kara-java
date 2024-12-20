package com.abdul.fatma.hamza.sahan.task;
import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;





public class Task {


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
        System.out.print("\033[H\033[2J"); // ANSI escape kodları
        System.out.flush(); // Terminalde ekranın gerçekten temizlenmesi için flush edilir
    }

    public static void enterToContinue() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press Enter to continue...");
        scanner.nextLine();  // Kullanıcıdan bir tuşa basmasını bekler
    }


    public int getInput(Scanner scanner) {
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            scanner.nextLine(); // Geçersiz girişleri temizler
            handleInputError(); // Hata mesajını yazdırır
            return -2; // Hata durumunda -2 döner
        }
    }


    public void handleInputError() {
        System.out.println("Invalid input. Please enter a number."); // Hata mesajı
    }


    public void openingScreenMenu() {
        clearScreen(); // clearScreen metodu çağrılır
        System.out.println("***************************************");
        System.out.println("*                                     *");
        System.out.println("*      WELCOME TO TASK MANAGER!       *");
        System.out.println("*                                     *");
        System.out.println("***************************************\n");

        System.out.println("=============== MAIN MENU ===============");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit Program");
        System.out.println("=========================================");
        System.out.print("Please enter a number: ");

    }

    public void printMainMenu() {
        clearScreen(); // clearScreen metodu çağrılır (tanımlamanız gerekecek)
        System.out.println("========================================");
        System.out.println("        MAIN MENU - TASK MANAGER       ");
        System.out.println("========================================");
        System.out.println("1. Create Task");
        System.out.println("2. Deadline Settings");
        System.out.println("3. Reminder System");
        System.out.println("4. Task Prioritization");
        System.out.println("5. Algorithms");
        System.out.println("6. Exit");
        System.out.println("========================================");
        System.out.print("Please enter your choice: ");
    }

    public void printCreateTaskMenu() {
        clearScreen(); // clearScreen metodu çağrılır
        System.out.println("========================================");
        System.out.println("           CREATE TASK MENU            ");
        System.out.println("========================================");
        System.out.println("1. Add Task");
        System.out.println("2. View Tasks");
        System.out.println("3. Categorize Tasks");
        System.out.println("4. Dependencies of Functions");
        System.out.println("5. Analyze SCC");
        System.out.println("6. Search By Keyword");
        System.out.println("7. Double Linked List");
        System.out.println("8. XOR Linked List");
        System.out.println("9. Exit");
        System.out.println("========================================");
        System.out.print("Please enter your choice: ");
    }

    public void printDeadlineSettingsMenu() {
        clearScreen(); // clearScreen metodu çağrılır
        System.out.println("========================================");
        System.out.println("       DEADLINE SETTINGS MENU          ");
        System.out.println("========================================");
        System.out.println("1. Assign Deadline");
        System.out.println("2. View Deadlines");
        System.out.println("3. View Deadlines In Range");
        System.out.println("4. Exit");
        System.out.println("========================================");
        System.out.print("Please enter your choice: ");
    }

    public void printReminderSystemMenu() {
        clearScreen(); // clearScreen metodu çağrılır
        System.out.println("========================================");
        System.out.println("        REMINDER SYSTEM MENU            ");
        System.out.println("========================================");
        System.out.println("1. Set Reminders");
        System.out.println("2. Notification Settings");
        System.out.println("3. Exit");
        System.out.println("========================================");
        System.out.print("Please enter your choice: ");
    }

    public void printTaskPrioritizationMenu() {
        clearScreen(); // clearScreen metodu çağrılır
        System.out.println("========================================");
        System.out.println("       TASK PRIORITIZATION MENU         ");
        System.out.println("========================================");
        System.out.println("1. Mark Task Importance");
        System.out.println("2. Importance Ordering");
        System.out.println("3. Exit");
        System.out.println("========================================");
        System.out.print("Please enter your choice: ");
    }

    public void printAlgorithmsMenu() {
        clearScreen(); // clearScreen metodu çağrılır
        System.out.println("========================================");
        System.out.println("              ALGORITHMS MENU            ");
        System.out.println("========================================");
        System.out.println("1. Progressive Overflow");
        System.out.println("2. Linear Probing");
        System.out.println("3. Quadratic Probing");
        System.out.println("4. Double Hashing");
        System.out.println("5. Use of Buckets");
        System.out.println("6. Linear Quotient");
        System.out.println("7. Brent's Method");
        System.out.println("8. Exit to Main Menu");
        System.out.println("========================================");
        System.out.print("Please enter your choice: ");
    }

    public void mainMenu(String pathFileUsers) {
        int choice;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            clearScreen(); // Ekranı temizle
            openingScreenMenu(); // Açılış menüsünü göster
            choice = getInput(scanner); // Kullanıcıdan seçim al

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
                    System.out.println("Exit Program");
                    return; // Programdan çık
                default:
                    System.out.println("Invalid choice. Please try again.");
                    enterToContinue(); // Geçersiz seçimde devam etmek için bekle
                    break;
            }
        }
    }

    public void userOptionsMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        while (true) {
            printMainMenu(); // Ana menüyü gösterir
            choice = getInput(scanner); // Kullanıcı girişini alır

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
                    System.out.println("Invalid choice. Please try again.");
                    enterToContinue(); // Devam etmek için beklenir
                    break;
            }
        }
    }

    public void createTaskMenu() {
        int maxTasks = 100;
        Scanner scanner = new Scanner(System.in);
        int choice;

        TaskQueue taskQueue = new TaskQueue(); // TaskQueue'yu burada tanımladık


        while (true) {
            printCreateTaskMenu(); // Menü gösterilir
            choice = getInput(scanner); // Kullanıcı girişini alır

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
                    System.out.print("Enter the task ID to view its dependencies: ");
                    int taskId = scanner.nextInt();

                    if (taskId > 0 && taskId <= taskList.size()) {
                        printDependencies(taskList, taskId); // Bağımlılıkları yazdır
                    } else {
                        System.out.println("Invalid task ID.");
                    }
                    enterToContinue(); // Kullanıcıya devam için bekletme
                    break;
                case 5:
                    StringBuilder output = new StringBuilder();
                    SCCAnalyzer analyzer = new SCCAnalyzer();

                    // SCC analizi yap
                    analyzer.analyzeSCC(taskList, output);

                    // Sonuçları yazdır
                    System.out.println(output.toString());
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
                    System.out.println("Invalid choice. Please try again.");
                    enterToContinue();
                    break;
            }

        }

    }

    public void deadlineSettingsMenu() {
        int maxTasks = 100;
        Scanner scanner = new Scanner(System.in);
        int choice;

        while (true) {
            printDeadlineSettingsMenu(); // Menü gösterilir
            choice = getInput(scanner); // Kullanıcı girişini alır

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
                    System.out.println("Invalid choice. Please try again.");
                    enterToContinue();
                    break;
            }

        }

    }

    public void reminderSystemMenu() {
        int maxTasks = 100;
        Scanner scanner = new Scanner(System.in);
        int choice;

        while (true) {
            printReminderSystemMenu(); // Menü gösterilir
            choice = getInput(scanner); // Kullanıcı girişini alır

            if (choice == -2) {
                handleInputError(); // Giriş hatası durumunda işlem yapılır
                enterToContinue();
                continue;
            }

            switch (choice) {
                case 1:
                    //setReminders
                    break;
                case 2:
                    //notificationSettings
                    enterToContinue();
                    break;
                case 3:
                    return; // Menüden çıkış
                default:
                    clearScreen();
                    System.out.println("Invalid choice. Please try again.");
                    enterToContinue();
                    break;
            }

        }

    }

    public void taskPrioritizationMenu() {
        int maxTasks = 100;
        Scanner scanner = new Scanner(System.in);
        int choice;

        while (true) {
            printTaskPrioritizationMenu(); // Menü gösterilir
            choice = getInput(scanner); // Kullanıcı girişini alır

            if (choice == -2) {
                handleInputError(); // Giriş hatası durumunda işlem yapılır
                enterToContinue();
                continue;
            }

            switch (choice) {
                case 1:
                    //markTaskImportance
                    break;
                case 2:
                    //
                    enterToContinue();
                    break;
                case 3:
                    return; // Menüden çıkış
                default:
                    clearScreen();
                    System.out.println("Invalid choice. Please try again.");
                    enterToContinue();
                    break;
            }

        }

    }

    public void algorithmsMenu() {
        int maxTasks = 100;
        Scanner scanner = new Scanner(System.in);
        int choice;

        while (true) {
            printAlgorithmsMenu(); // Menü gösterilir
            choice = getInput(scanner); // Kullanıcı girişini alır

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
                    //linearProbingDemo
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
                    System.out.println("Invalid choice. Please try again.");
                    enterToContinue();
                    break;
            }

        }

    }

    public int loginUser(User loginUser, String pathFileUsers) {

        User loggedUser; // Sınıf genelinde erişilebilecek bir değişken

        File file = new File(pathFileUsers);
        if (!file.exists()) {
            System.out.println("Failed to open user file.");
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
                System.out.println("Login successful.");
                loggedUser = foundUser; // Başarılı giriş
                return 1;
            } else {
                System.out.println("Incorrect email or password.");
                return 0;
            }
        } catch (IOException e) {
            System.out.println("Error reading user file.");
            return 0;
        }
    }

    public boolean loginUserMenu(String pathFileUsers) {
        Scanner scanner = new Scanner(System.in);
        User userInput = new User();

        System.out.print("Enter email: ");
        userInput.setEmail(scanner.nextLine());

        System.out.print("Enter password: ");
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

    public static int registerUser(User user, String pathFileUser) {
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
                        System.out.println("User already exists.");
                        raf.close();
                        return 0;
                    }
                } else {
                    System.out.println("The file is empty, creating a new one.");
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

            System.out.println("User registered successfully: Welcome " + user.name + " " + user.surname);
            raf.close();  // Dosyayı kapat

            return 1;
        } catch (IOException e) {
            e.printStackTrace();  // Hata mesajını daha ayrıntılı yazdırmak için
            System.out.println("Error processing user file.");
            return 0;
        }
    }

    public static int registerUserMenu(String pathFileUsers) {
        Scanner scanner = new Scanner(System.in);
        User newUser = new User();

        // Adı al
        System.out.print("Enter Name: ");
        newUser.setName(scanner.nextLine());

        // Soyadı al
        System.out.print("Enter Surname: ");
        newUser.setSurname(scanner.nextLine());

        // E-posta al
        System.out.print("Enter email: ");
        newUser.setEmail(scanner.nextLine());

        // Şifre al
        System.out.print("Enter password: ");
        newUser.setPassword(scanner.nextLine());

        // Kullanıcıyı kaydet
        return registerUser(newUser, pathFileUsers);
    }

    public static int addTask(ArrayList<TaskInfo> taskList, DoubleLinkedList taskDoublyLinkedList, int taskCount, int maxTasks) {
        if (taskCount >= maxTasks) {
            System.out.println("Task list is full. Cannot add more tasks.");
            return 0;
        }

        // Yeni görevin ID'sini mevcut görevler arasında en yüksek ID'nin bir fazlası olarak belirle
        int newId = getNewTaskId(taskList);

        TaskInfo newTask = new TaskInfo();
        newTask.setId(newId); // Yeni görevin ID'sini ayarla

        Scanner scanner = new Scanner(System.in);

        // Görev bilgilerini alıyoruz
        System.out.print("Enter Task Name: ");
        newTask.setName(scanner.nextLine());

        System.out.print("Enter Task Description: ");
        newTask.setDescription(scanner.nextLine());

        System.out.print("Enter Task Category: ");
        newTask.setCategory(scanner.nextLine());

        System.out.print("Enter Due Date (YYYY-MM-DD): ");
        newTask.setDueDate(scanner.nextLine());

        System.out.print("Enter number of dependencies: ");
        int dependencyCount = scanner.nextInt();
        newTask.setDependencyCount(dependencyCount);
        scanner.nextLine(); // Consume newline left by nextInt()

        // Bağımlılıkları alıyoruz
        for (int i = 0; i < newTask.getDependencyCount(); i++) {
            System.out.print("Enter dependency task ID for dependency " + (i + 1) + ": ");
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

        System.out.println("Task added and saved successfully!");
        return 1;
    }



    public static void saveTasks(ArrayList<TaskInfo> taskList) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream("tasks.bin"))) {
            dos.writeInt(taskList.size()); // Görev sayısını yaz
            for (TaskInfo task : taskList) {
                dos.writeInt(task.getId());                // ID
                dos.writeUTF(task.getName());              // Name
                dos.writeUTF(task.getDescription());       // Description
                dos.writeUTF(task.getCategory());          // Category
                dos.writeUTF(task.getDueDate());           // Due Date
                dos.writeInt(task.getDependencyCount());   // Dependency Count
                for (int dep : task.getDependencies()) {
                    dos.writeInt(dep);                     // Dependencies
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }


    public static int loadTasks(ArrayList<TaskInfo> taskList) {
        File file = new File("tasks.bin");
        if (!file.exists()) {
            System.out.println("No previous tasks found.");
            return 0;
        }

        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            int taskCount = raf.readInt();  // Dosyadan görev sayısını oku
            System.out.println("Task count read from file: " + taskCount);  // Log ekledik

            for (int i = 0; i < taskCount; i++) {
                TaskInfo task = new TaskInfo();
                task.readFromFile(raf);  // Dosyadan her bir görevi oku
                System.out.println("Task loaded: ID=" + task.getId() + ", Name=" + task.getName());  // Log ekledik
                taskList.add(task);  // Görevi listeye ekle
            }

            System.out.println(taskCount + " tasks loaded successfully!");
            return taskCount;
        } catch (IOException e) {
            System.out.println("Error reading tasks file: " + e.getMessage());
            return 0;
        }
    }






    public void navigateTaskList(DoubleLinkedList taskList) {
        if (taskList.head == null) {
            System.out.println("No tasks available for navigation.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        TaskNode current = taskList.head; // Listenin başından başlıyoruz
        int choice;

        while (true) {
            System.out.println("\nCurrent Task:");
            System.out.println("ID: " + current.task.getId());
            System.out.println("Name: " + current.task.getName());
            System.out.println("Description: " + current.task.getDescription());
            System.out.println("Category: " + current.task.getCategory());
            System.out.println("Due Date: " + current.task.getDueDate());

            System.out.println("\n1. Next\n2. Previous\n3. Exit");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();

            if (choice == 1) { // Bir sonraki göreve git
                if (current.next != null) {
                    current = current.next;
                } else {
                    System.out.println("This is the last task.");
                }
            } else if (choice == 2) { // Bir önceki göreve git
                if (current.prev != null) {
                    current = current.prev;
                } else {
                    System.out.println("This is the first task.");
                }
            } else if (choice == 3) { // Gezintiden çık
                break;
            } else { // Geçersiz seçim
                System.out.println("Invalid choice. Try again.");
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

    public static void viewTask(ArrayList<TaskInfo> taskList) {
        if (taskList.isEmpty()) {
            System.out.println("No tasks found. The task list is empty.");
            enterToContinue();
            return;
        }

        System.out.println("\n--- List of Tasks ---");

        // taskList içindeki tüm görevleri sırasıyla yazdırıyoruz
        for (TaskInfo task : taskList) {
            System.out.println("ID: " + task.getId());
            System.out.println("Name: " + task.getName());
            System.out.println("Description: " + task.getDescription());
            System.out.println("Category: " + task.getCategory());
            System.out.println("Due Date: " + task.getDueDate());
            System.out.println("---------------------------");
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

    public static void categorizeTask(ArrayList<TaskInfo> taskList) {
        Scanner scanner = new Scanner(System.in);

        // Kategoriyi kullanıcıdan al
        System.out.print("Enter category to filter: ");
        String category = scanner.nextLine().trim();

        boolean found = false;

        System.out.println("\n--- Tasks in Category '" + category + "' ---");

        // taskList içindeki görevleri kontrol et
        for (TaskInfo task : taskList) {
            if (task.getCategory().equalsIgnoreCase(category)) {
                System.out.println("ID: " + task.getId());
                System.out.println("Name: " + task.getName());
                System.out.println("Description: " + task.getDescription());
                System.out.println("Due Date: " + task.getDueDate());
                System.out.println("---------------------------");
                found = true;
            }
        }

        if (!found) {
            System.out.println("No tasks found in this category.");
        }

        enterToContinue(); // Kullanıcıya devam etmek için bekleme ekranı
    }

    public static void printDependencies(ArrayList<TaskInfo> taskList, int startTaskId) {
        boolean[] visited = new boolean[taskList.size()]; // Ziyaret edilen görevler için dizi

        System.out.println("Dependencies for Task " + startTaskId + ":");
        printDependenciesUtil(taskList, startTaskId, visited); // Yardımcı fonksiyonu çağır
    }

    private static void printDependenciesUtil(ArrayList<TaskInfo> taskList, int taskId, boolean[] visited) {
        if (visited[taskId - 1]) {
            return; // Görev zaten ziyaret edildiyse döngüyü önlemek için dur
        }

        visited[taskId - 1] = true; // Görevi ziyaret edildi olarak işaretle

        // Görevi bul ve bağımlılıklarını yazdır
        for (TaskInfo task : taskList) {
            if (task.getId() == taskId) {
                for (int depId : task.getDependencies()) {
                    if (depId != 0) { // Sıfır olmayan bağımlılıkları yazdır
                        System.out.println("Task " + taskId + " depends on Task " + depId);
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

    public static void searchTasksByKeyword(ArrayList<TaskInfo> taskList) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the keyword to search in task descriptions: ");
        String keyword = scanner.nextLine();

        boolean found = false;

        System.out.println("\nTasks containing the keyword '" + keyword + "' in their descriptions:");
        System.out.println("----------------------------------------------------");

        for (TaskInfo task : taskList) {
            if (KMPsearch(task.getDescription(), keyword)) {
                System.out.println("ID: " + task.getId());
                System.out.println("Name: " + task.getName());
                System.out.println("Description: " + task.getDescription());
                System.out.println("Category: " + task.getCategory());
                System.out.println("Due Date: " + task.getDueDate());
                System.out.println("----------------------------------------------------");
                found = true;
            }
        }

        if (!found) {
            System.out.println("No tasks found with the keyword '" + keyword + "'.");
        }
    }


    public void assign_deadline() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Task Name: ");
        String taskName = scanner.nextLine();

        System.out.print("Enter Deadline (day month year): ");
        int day = scanner.nextInt();
        int month = scanner.nextInt();
        int year = scanner.nextInt();

        if (day < 1 || day > 31 || month < 1 || month > 12 || year < 1900) {
            System.out.println("Invalid date! Please enter a valid date.");
            return;
        }

        // Yeni Assignment oluştur ve heap'e ekle
        Assignment assignment = new Assignment(taskName, day, month, year);
        deadlineHeap.add(assignment);

        // Dosyaya kaydetme işlemi
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("deadlines.bin", true))) {
            oos.writeObject(assignment);
            System.out.println("Deadline assigned and saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving deadline: " + e.getMessage());
        }
    }

    public int viewDeadlines() {
        if (deadlineHeap.isEmpty()) {
            System.out.println("No deadlines to display.");
            return -1;
        }

        System.out.println("\n--- Upcoming Deadlines (Sorted by Date) ---");
        System.out.println("-------------------------------------------");

        // Geçici heap oluşturmak için mevcut heap'i kopyalıyoruz
        PriorityQueue<Assignment> tempHeap = new PriorityQueue<>(deadlineHeap);

        int taskCount = 0;
        while (!tempHeap.isEmpty()) {
            Assignment deadline = tempHeap.poll(); // Min elemanı çıkar
            System.out.printf("%d. Task: %s - Deadline: %02d/%02d/%04d\n",
                    ++taskCount,
                    deadline.getName(),
                    deadline.getDay(),
                    deadline.getMonth(),
                    deadline.getYear());
        }

        if (taskCount == 0) {
            System.out.println("No deadlines to display.");
        }

        System.out.println("-------------------------------------------");
        enterToContinue(); // Devam etmek için kullanıcıdan girdi al
        return 1;
    }   

}