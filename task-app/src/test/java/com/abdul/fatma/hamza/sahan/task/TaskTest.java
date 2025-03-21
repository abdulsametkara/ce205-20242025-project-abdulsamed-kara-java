/**

 @file TaskTest.java
 @brief This file contains the test cases for the Task class.
 @details This file includes test methods to validate the functionality of the Task class. It uses JUnit for unit testing.
 */
package com.abdul.fatma.hamza.sahan.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;
import org.junit.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.junit.Assert.*;



public class TaskTest {
  private XORLinkedList xorLinkedList;
  private TaskInfo task101;
  private TaskInfo task202;
  private TaskInfo task303;
  private final String testFile = "test_tasks_xor.bin";
  private BPlusTree bPlusTree;
  private ScheduledTask task1;
  private ScheduledTask task2;
  private ScheduledTask task3;
  private MinHeap minHeap;
  private Assignment assignment1;
  private Assignment assignment2;
  private Assignment assignment3;
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  private final InputStream originalIn = System.in;
  Scanner inputScanner = new Scanner(System.in);
  public static TaskNode head = null;
  public static TaskNode tail = null;
  private String taskFile = "tasksTest.bin";
  private String userFile = "usersTest.bin";
  private static ArrayList<TaskInfo> taskList = new ArrayList<>();

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    System.out.println("Setting up before all tests");
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
    System.out.println("Cleaning up after all tests");
  }

  @Before
  public void setUp() throws Exception {
    System.setOut(new PrintStream(outContent));
    System.setIn(originalIn);
    deleteFile(taskFile);
    deleteFile(userFile);
    createTestTaskFile();
    createTestUserFile();
    minHeap = new MinHeap();

    assignment1 = new Assignment("Task 1", 1, 1, 2024);
    assignment2 = new Assignment("Task 2", 5, 1, 2024);
    assignment3 = new Assignment("Task 3", 10, 1, 2024);

    bPlusTree = new BPlusTree();

    task1 = new ScheduledTask("Task 1", 1, 1, 2024);
    task2 = new ScheduledTask("Task 2", 5, 1, 2024);
    task3 = new ScheduledTask("Task 3", 10, 1, 2024);

    xorLinkedList = new XORLinkedList();

    task101 = new TaskInfo();
    task101.setId(101);
    task101.setName("Task 101");
    task101.setDescription("Description 101");
    task101.setCategory("Category 101");
    task101.setDueDate("2024-09-15");

    task202 = new TaskInfo();
    task202.setId(202);
    task202.setName("Task 202");
    task202.setDescription("Description 202");
    task202.setCategory("Category 202");
    task202.setDueDate("2024-08-20");

    task303 = new TaskInfo();
    task303.setId(303);
    task303.setName("Task 303");
    task303.setDescription("Description 303");
    task303.setCategory("Category 303");
    task303.setDueDate("2024-07-10");
  }

  @After
  public void tearDown() throws Exception {
    System.setOut(originalOut);
    System.setIn(originalIn);

  }

  private void deleteFile(String filePath) throws IOException {
    Files.deleteIfExists(Paths.get(filePath));
  }

  private void createTestUserFile() throws IOException {
    try (RandomAccessFile raf = new RandomAccessFile("test_users.bin", "rw")) {
      // Kullanıcı sayısını yaz
      raf.writeInt(2); // İki kullanıcı yazılacak

      // Kullanıcı 1
      User user1 = new User();
      user1.setId(1);
      user1.setName("John");
      user1.setSurname("Doe");
      user1.setEmail("john.doe@example.com");
      user1.setPassword("securepassword1");
      user1.writeToFile(raf); // Kullanıcıyı dosyaya yaz

      // Kullanıcı 2
      User user2 = new User();
      user2.setId(2);
      user2.setName("Jane");
      user2.setSurname("Smith");
      user2.setEmail("jane.smith@example.com");
      user2.setPassword("securepassword2");
      user2.writeToFile(raf);// Kullanıcıyı dosyaya yaz

      User user3 = new User();
      user3.setId(3);
      user3.setName("aa");
      user3.setSurname("aa");
      user3.setEmail("aa");
      user3.setPassword("aa");
      user3.writeToFile(raf);
    }
  }

  private void createTestTaskFile() throws IOException {
    try (RandomAccessFile raf = new RandomAccessFile(taskFile, "rw")) {
      TaskInfo task1 = new TaskInfo();
      task1.setId(1);
      task1.setName("Task 1");
      task1.setDescription("Description 1");
      task1.setCategory("Category 1");
      task1.setDueDate("2024-12-31");
      task1.setDependencyCount(2);
      task1.setDependencies(new int[]{2, 3});
      task1.writeToFile(raf);

      TaskInfo task2 = new TaskInfo();
      task2.setId(2);
      task2.setName("Task 2");
      task2.setDescription("Description 2");
      task2.setCategory("Category 2");
      task2.setDueDate("2024-11-30");
      task2.setDependencyCount(0);
      task2.writeToFile(raf);
    }
  }



  @Test
  public void testTaskFileCreation() throws IOException {
    // Arrange
    File file = new File(taskFile);

    // Assert
    assertTrue("Task file should exist", file.exists());
  }

  @Test
  public void testTaskReadFromFile() throws IOException {
    // Arrange
    try (RandomAccessFile raf = new RandomAccessFile(taskFile, "r")) {
      TaskInfo task = new TaskInfo();

      // Act
      task.readFromFile(raf);

      // Assert
      assertEquals(1, task.getId());
      assertEquals("Task 1", task.getName());
      assertEquals("Description 1", task.getDescription());
      assertEquals("Category 1", task.getCategory());
      assertEquals("2024-12-31", task.getDueDate());
      assertEquals(2, task.getDependencyCount());
    }
  }

  @Test
  public void testOpeningScreenMenu() {
    // Arrange: Task sınıfından bir örnek oluştur
    Task task = new Task(inputScanner, System.out); // 'Task' sınıfını doğru bir şekilde kullanın

    // Act: openingScreenMenu metodunu çağır
    task.openingScreenMenu();

    // Assert: Ekran çıktılarının doğru olup olmadığını kontrol et
    String output = outContent.toString();

    // Ana menü başlığının doğru şekilde yazıldığını doğrula
    assertTrue(output.contains("WELCOME TO TASK MANAGER!"));
    assertTrue(output.contains("=============== MAIN MENU ==============="));
    assertTrue(output.contains("1. Login"));
    assertTrue(output.contains("2. Register"));
    assertTrue(output.contains("3. Exit Program"));
    assertTrue(output.contains("Please enter a number:"));
  }

  @Test
  public void testHandleInputError() {
    // Arrange: Task sınıfını örnek olarak oluştur
    Task task = new Task(inputScanner, System.out);

    // Act: handleInputError metodunu çağır
    task.handleInputError();

    // Assert: Konsol çıktısının doğru olduğuna dair kontrol et
    String output = outContent.toString();
    assertTrue(output.contains("Invalid input. Please enter a number.")); // Mesajın çıktıda bulunup bulunmadığını kontrol et
  }

  @Test
  public void testPrintMainMenu() {
    // Arrange: Task sınıfını örnek olarak oluştur
    Task task = new Task(inputScanner, System.out);

    // Act: printMainMenu metodunu çağır
    task.printMainMenu();

    // Assert: Konsol çıktısının doğru olduğuna dair kontrol et
    String output = outContent.toString();
    assertTrue(output.contains("MAIN MENU - TASK MANAGER")); // Menü başlığı
    assertTrue(output.contains("1. Create Task")); // Menüdeki seçenek
    assertTrue(output.contains("2. Deadline Settings")); // Menüdeki seçenek
    assertTrue(output.contains("3. Reminder System")); // Menüdeki seçenek
    assertTrue(output.contains("4. Task Prioritization")); // Menüdeki seçenek
    assertTrue(output.contains("5. Algorithms")); // Menüdeki seçenek
    assertTrue(output.contains("6. Exit")); // Menüdeki seçenek
    assertTrue(output.contains("Please enter your choice:")); // Sonuçtaki prompt
  }

  @Test
  public void testPrintCreateTaskMenu() {
    // Arrange: Task sınıfını örnek olarak oluştur
    Task task = new Task(inputScanner, System.out);

    // Act: printCreateTaskMenu metodunu çağır
    task.printCreateTaskMenu();

    // Assert: Konsol çıktısının doğru olduğuna dair kontrol et
    String output = outContent.toString();
    assertTrue(output.contains("CREATE TASK MENU")); // Menü başlığı
    assertTrue(output.contains("1. Add Task")); // Menüdeki seçenek
    assertTrue(output.contains("2. View Tasks")); // Menüdeki seçenek
    assertTrue(output.contains("3. Categorize Tasks")); // Menüdeki seçenek
    assertTrue(output.contains("4. Dependencies of Functions")); // Menüdeki seçenek
    assertTrue(output.contains("5. Analyze SCC")); // Menüdeki seçenek
    assertTrue(output.contains("6. Search By Keyword")); // Menüdeki seçenek
    assertTrue(output.contains("7. Double Linked List")); // Menüdeki seçenek
    assertTrue(output.contains("8. XOR Linked List")); // Menüdeki seçenek
    assertTrue(output.contains("9. Exit")); // Menüdeki seçenek
    assertTrue(output.contains("Please enter your choice:")); // Sonuçtaki prompt
  }

  @Test
  public void testPrintDeadlineSettingsMenu() {
    // Arrange: Task sınıfını örnek olarak oluştur
    Task task = new Task(inputScanner, System.out);

    // Act: printDeadlineSettingsMenu metodunu çağır
    task.printDeadlineSettingsMenu();

    // Assert: Konsol çıktısının doğru olduğuna dair kontrol et
    String output = outContent.toString();
    assertTrue(output.contains("DEADLINE SETTINGS MENU")); // Menü başlığı
    assertTrue(output.contains("1. Assign Deadline")); // Menüdeki seçenek
    assertTrue(output.contains("2. View Deadlines")); // Menüdeki seçenek
    assertTrue(output.contains("3. View Deadlines In Range")); // Menüdeki seçenek
    assertTrue(output.contains("4. Exit")); // Menüdeki seçenek
    assertTrue(output.contains("Please enter your choice:")); // Sonuçtaki prompt
  }

  @Test
  public void testPrintReminderSystemMenu() {
    // Arrange: Task sınıfını örnek olarak oluştur
    Task task = new Task(inputScanner, System.out);

    // Act: printReminderSystemMenu metodunu çağır
    task.printReminderSystemMenu();

    // Assert: Konsol çıktısının doğru olduğuna dair kontrol et
    String output = outContent.toString();
    assertTrue(output.contains("REMINDER SYSTEM MENU")); // Menü başlığı
    assertTrue(output.contains("1. Set Reminders")); // Menüdeki seçenek
    assertTrue(output.contains("2. Notification Settings")); // Menüdeki seçenek
    assertTrue(output.contains("3. Exit")); // Menüdeki seçenek
    assertTrue(output.contains("Please enter your choice:")); // Sonuçtaki prompt
  }

  @Test
  public void testPrintTaskPrioritizationMenu() {
    // Arrange: Task sınıfını örnek olarak oluştur
    Task task = new Task(inputScanner, System.out);

    // Act: printTaskPrioritizationMenu metodunu çağır
    task.printTaskPrioritizationMenu();

    // Assert: Konsol çıktısının doğru olduğuna dair kontrol et
    String output = outContent.toString();
    assertTrue(output.contains("TASK PRIORITIZATION MENU")); // Menü başlığı
    assertTrue(output.contains("1. Mark Task Importance")); // Menüdeki seçenek
    assertTrue(output.contains("2. Importance Ordering")); // Menüdeki seçenek
    assertTrue(output.contains("3. Exit")); // Menüdeki seçenek
    assertTrue(output.contains("Please enter your choice:")); // Sonuçtaki prompt
  }

  @Test
  public void testPrintAlgorithmsMenu() {
    // Arrange: Task sınıfını örnek olarak oluştur
    Task task = new Task(inputScanner, System.out);

    // Act: printAlgorithmsMenu metodunu çağır
    task.printAlgorithmsMenu();

    // Assert: Konsol çıktısının doğru olduğuna dair kontrol et
    String output = outContent.toString();
    assertTrue(output.contains("ALGORITHMS MENU")); // Menü başlığı
    assertTrue(output.contains("1. Progressive Overflow")); // Menüdeki seçenek
    assertTrue(output.contains("2. Linear Probing")); // Menüdeki seçenek
    assertTrue(output.contains("3. Quadratic Probing")); // Menüdeki seçenek
    assertTrue(output.contains("4. Double Hashing")); // Menüdeki seçenek
    assertTrue(output.contains("5. Use of Buckets")); // Menüdeki seçenek
    assertTrue(output.contains("6. Linear Quotient")); // Menüdeki seçenek
    assertTrue(output.contains("7. Brent's Method")); // Menüdeki seçenek
    assertTrue(output.contains("8. Exit to Main Menu")); // Menüdeki seçenek
    assertTrue(output.contains("Please enter your choice:")); // Sonuçtaki prompt
  }

  @Test
  public void testUserOptionsMenu() {
    // Arrange
    String input = "1\n9\n2\n4\n3\n3\n4\n3\n5\n8\n6\n3\n";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simulated user input
    System.setOut(new PrintStream(outContent)); // Capture output

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.userOptionsMenu();
    // Cleanup
    System.setIn(originalIn);
  }

  @Test
  public void testCreateTaskAddMenu() {   // Arrange
    String input = "Task1\nDescription1\nCategory1\n2024-12-31\n1\n2\n";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simulated user input
    System.setOut(new PrintStream(outContent)); // Capture output

    ArrayList<TaskInfo> taskList = new ArrayList<>();
    DoubleLinkedList taskDoublyLinkedList = new DoubleLinkedList();

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    int result = task.addTask(taskList, taskDoublyLinkedList, 0, 100);

    // Assert
    assertEquals(1, result); // Task successfully added
    String output = outContent.toString();
    assertTrue(output.contains("Task added and saved successfully!"));

  }

  @Test
  public void testCreateTaskViewMenu() {
    // Arrange
    String input = "\n\n9\n6\n3\n";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simulated user input
    System.setOut(new PrintStream(outContent)); // Capture output

    Task task = new Task(new Scanner(System.in), System.out);

    // Create a sample task list
    ArrayList<TaskInfo> taskList = new ArrayList<>();
    TaskInfo task1 = new TaskInfo();
    task1.setId(1);
    task1.setName("Sample Task");
    task1.setDescription("This is a sample task.");
    task1.setCategory("General");
    task1.setDueDate("2024-12-31");
    taskList.add(task1);

    // Act
    task.viewTask(taskList);
    // Cleanup
    System.setIn(originalIn);
  }

  @Test
  public void testCreateTaskMenuView() {
    // Arrange
    String input = "2\n\n\n\n9\n6\n3\n";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simulated user input
    System.setOut(new PrintStream(outContent)); // Capture output

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.createTaskMenu();
    // Cleanup
    System.setIn(originalIn);
  }

  @Test
  public void testCreateTaskMenuCatogorize() {
    // Arrange
    String input = "3\na\n\n\n9\n6\n3\n";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simulated user input
    System.setOut(new PrintStream(outContent)); // Capture output

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.createTaskMenu();
    // Cleanup
    System.setIn(originalIn);
  }

  @Test
  public void testCreateTaskMenuDependencies() {
    // Arrange
    String input = "1\na\na\na\n1000 10 10\n1\n4\n1\n\n9\n6\n3\n";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simulated user input
    System.setOut(new PrintStream(outContent)); // Capture output

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.createTaskMenu();
    // Cleanup
    System.setIn(originalIn);
  }

  @Test
  public void testCreateTaskMenuAnalyzeSCC() {
    // Arrange
    String input = "5\n\n9\n6\n3\n";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simulated user input
    System.setOut(new PrintStream(outContent)); // Capture output

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.createTaskMenu();
    // Cleanup
    System.setIn(originalIn);
  }

  @Test
  public void testCreateTaskMenuSearchByKeyword() {
    // Arrange
    String input = "6\na\n\n9\n6\n3\n";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simulated user input
    System.setOut(new PrintStream(outContent)); // Capture output

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.createTaskMenu();
    // Cleanup
    System.setIn(originalIn);
  }
  @Test
  public void testCreateTaskMenuDoubleLinkedList() {
    // Arrange
    String input = "7\n1\n2\n3\n\n9\n6\n3\n";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simulated user input
    System.setOut(new PrintStream(outContent)); // Capture output

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.createTaskMenu();
    // Cleanup
    System.setIn(originalIn);
  }

  @Test
  public void testMainMenu_ValidInputs() {
    // Arrange
    String simulatedInput = "2\nfatma\nakbas\n12\n12\n1\n12\n12\n6\n3\n";
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    // Kullanıcı dosyasını hazırlayın
    try {
      createTestUserFile();
    } catch (IOException e) {
      fail("Test user file creation failed: " + e.getMessage());
    }

    // Act
    task.mainMenu("test_users.bin");

    // Cleanup
    System.setIn(System.in);
    System.setOut(System.out);
  }

  @Test
  public void testMainMenu_InvalidChoice() {
    // Arrange
    String simulatedInput = "99\n\n3\n"; // 99 -> Invalid choice, 3 -> Exit Program
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.mainMenu("test_users.bin");
  }



  @Test
  public void testMainMenu_InputErrorHandling() {
    // Arrange
    String simulatedInput = "abc\n\n3\n"; // Geçersiz giriş -> abc, sonra çıkış
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.mainMenu("test_users.bin");

  }
  @Test
  public void testAssignDeadlineTest() {
    // Arrange
    String input = "1\na\n12 12 2222\n4\n6\n3\n";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simulated user input
    System.setOut(new PrintStream(outContent)); // Capture output

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.deadlineSettingsMenu();
    // Cleanup
    System.setIn(originalIn);
  }
  @Test
  public void testViewDeadlineTest() {
    // Arrange
    String input = "2\n\n\n4\n6\n3\n";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simulated user input
    System.setOut(new PrintStream(outContent)); // Capture output

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.deadlineSettingsMenu();
    // Cleanup
    System.setIn(originalIn);
  }


  @Test
  public void testUserOptionsMenu_InvalidChoice() {
    // Arrange
    String simulatedInput = "99\n\n6\n3\n"; // 99 -> Invalid choice, 3 -> Exit Program
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.userOptionsMenu();
  }

  @Test
  public void testOptionsMenu_InputErrorHandling() {
    // Arrange
    String simulatedInput = "abc\n\n6\n3\n"; // Geçersiz giriş -> abc, sonra çıkış
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.userOptionsMenu();

  }

  @Test
  public void testCreateTaskMenu_InvalidChoice() {
    // Arrange
    String simulatedInput = "99\n\n9\n6\n3\n"; // 99 -> Invalid choice, 3 -> Exit Program
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.createTaskMenu();
  }

  @Test
  public void testCreateTaskMenu_InputErrorHandling() {
    // Arrange
    String simulatedInput = "abc\n\n9\n6\n3\n"; // Geçersiz giriş -> abc, sonra çıkış
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.createTaskMenu();

  }

  @Test
  public void testDeadlineSettingsMenu_InvalidChoice() {
    // Arrange
    String simulatedInput = "99\n\n4\n6\n3\n"; // 99 -> Invalid choice, 3 -> Exit Program
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.deadlineSettingsMenu();
  }

  @Test
  public void testDeadlineSettingsMenu_InputErrorHandling() {
    // Arrange
    String simulatedInput = "abc\n\n4\n6\n3\n"; // Geçersiz giriş -> abc, sonra çıkış
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.deadlineSettingsMenu();

  }

  @Test
  public void testReminderSystemMenu_InvalidChoice() {
    // Arrange
    String simulatedInput = "99\n\n3\n6\n3\n"; // 99 -> Invalid choice, 3 -> Exit Program
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.reminderSystemMenu();
  }

  @Test
  public void testReminderSystemMenu_InputErrorHandling() {
    // Arrange
    String simulatedInput = "abc\n\n3\n6\n3\n"; // Geçersiz giriş -> abc, sonra çıkış
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.reminderSystemMenu();

  }

  @Test
  public void testTaskPrioritizationMenu_InvalidChoice() {
    // Arrange
    String simulatedInput = "99\n\n3\n6\n3\n"; // 99 -> Invalid choice, 3 -> Exit Program
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.taskPrioritizationMenu();
  }

  @Test
  public void testTaskPrioritizationMenu_InputErrorHandling() {
    // Arrange
    String simulatedInput = "abc\n\n3\n6\n3\n"; // Geçersiz giriş -> abc, sonra çıkış
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.taskPrioritizationMenu();

  }
  @Test
  public void testAlgorithmsMenu_InvalidChoice() {
    // Arrange
    String simulatedInput = "99\n\n8\n6\n3\n"; // 99 -> Invalid choice, 3 -> Exit Program
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.algorithmsMenu();
  }

  @Test
  public void testAlgorithmsMenu_InputErrorHandling() {
    // Arrange
    String simulatedInput = "abc\n\n8\n6\n3\n"; // Geçersiz giriş -> abc, sonra çıkış
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.algorithmsMenu();

  }
  @Test
  public void testRegisterUser_UserAddedSuccessfully() {
    // Arrange
    Task task = new Task(new Scanner(System.in), System.out);

    User testUser = new User();
    testUser.setId(1);
    testUser.setName("John");
    testUser.setSurname("Doe");
    testUser.setEmail("john.doe@example.com");
    testUser.setPassword("securepassword");

    // Act
    int result = task.registerUser(testUser, userFile);

    // Assert: Kullanıcı başarıyla eklendi mi?
    assertEquals(1, result); // Kullanıcının başarıyla eklendiğini doğrula

    // Dosyadan kullanıcı doğrulaması
    try (RandomAccessFile raf = new RandomAccessFile(userFile, "r")) {
      int userCount = raf.readInt();
      assertEquals(1, userCount); // Kullanıcı sayısı kontrolü

      User loadedUser = new User();
      loadedUser.readFromFile(raf); // Kullanıcıyı dosyadan oku

      assertEquals(1, loadedUser.getId()); // ID doğrulama
      assertEquals("John", loadedUser.getName()); // Ad doğrulama
      assertEquals("Doe", loadedUser.getSurname()); // Soyad doğrulama
      assertEquals("john.doe@example.com", loadedUser.getEmail()); // E-posta doğrulama
      assertEquals("securepassword", loadedUser.getPassword()); // Şifre doğrulama
    } catch (IOException e) {
      fail("Error reading from user file: " + e.getMessage());
    }
  }
  @Test
  public void testRegisterUserMenuTest() {
    // Arrange
    String input = "2\na\na\na\na\n3\n";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simulated user input
    System.setOut(new PrintStream(outContent)); // Capture output

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.registerUserMenu(userFile);
    // Cleanup
    System.setIn(originalIn);
  }
  @Test
  public void testLoadTasks_NoFile() {
    // Arrange
    String taskFile = "tasks.bin";
    File file = new File(taskFile);
    file.delete(); // Ensure the file does not exist before the test

    Task task = new Task(new Scanner(System.in), System.out);
    ArrayList<TaskInfo> taskList = new ArrayList<>();

    // Act
    int result = task.loadTasks(taskList);

    // Assert
    assertEquals(0, result); // No tasks should be loaded
    String output = outContent.toString();
    assertTrue(output.contains("No previous tasks found."));
  }

  @Test
  public void testLoadTasks_SuccessfulLoad() throws IOException {
    // Arrange
    String taskFile = "tasks.bin";
    try (RandomAccessFile raf = new RandomAccessFile(taskFile, "rw")) {
      // Write task count
      raf.writeInt(2);

      // Write first task
      TaskInfo task1 = new TaskInfo();
      task1.setId(1);
      task1.setName("Task 1");
      task1.setDescription("Description 1");
      task1.setCategory("Category 1");
      task1.setDueDate("2024-12-31");
      task1.setDependencyCount(0);
      task1.writeToFile(raf);

      // Write second task
      TaskInfo task2 = new TaskInfo();
      task2.setId(2);
      task2.setName("Task 2");
      task2.setDescription("Description 2");
      task2.setCategory("Category 2");
      task2.setDueDate("2024-11-30");
      task2.setDependencyCount(0);
      task2.writeToFile(raf);
    }

    Task task = new Task(new Scanner(System.in), System.out);
    ArrayList<TaskInfo> taskList = new ArrayList<>();

    // Act
    int result = task.loadTasks(taskList);

    // Assert
    assertEquals(2, result); // Two tasks should be loaded
    assertEquals(2, taskList.size());
    assertEquals("Task 1", taskList.get(0).getName());
    assertEquals("Task 2", taskList.get(1).getName());

    String output = outContent.toString();
    assertTrue(output.contains("2 tasks loaded successfully!"));
    assertTrue(output.contains("Task loaded: ID=1, Name=Task 1"));
    assertTrue(output.contains("Task loaded: ID=2, Name=Task 2"));
  }

  @Test
  public void testLoadTasks_FileReadError() throws IOException {
    // Arrange
    String taskFile = "tasks.bin";
    try (RandomAccessFile raf = new RandomAccessFile(taskFile, "rw")) {
      raf.writeBytes("CorruptedData"); // Dosyaya geçersiz veri yaz
    }

    Task task = new Task(new Scanner(System.in), System.out);
    ArrayList<TaskInfo> taskList = new ArrayList<>();

    // Act
    int result = task.loadTasks(taskList);

    // Assert
    assertEquals(0, result); // Hata durumunda 0 döner
    String output = outContent.toString();
    assertTrue(output.contains("Error reading tasks file:"));
  }

  @Test
  public void testNavigateTaskList_SingleTask() {
    // Arrange
    DoubleLinkedList taskList = new DoubleLinkedList();
    TaskInfo taskInfo = new TaskInfo();
    taskInfo.setId(1);
    taskInfo.setName("Single Task");
    taskInfo.setDescription("Only one task in the list");
    taskInfo.setCategory("General");
    taskInfo.setDueDate("2024-12-31");

    TaskNode node = new TaskNode(taskInfo); // TaskNode nesnesi oluşturulurken TaskInfo nesnesi verildi.
    taskList.head = node;

    String input = "1\n2\n3\n"; // Next -> Previous -> Exit
    System.setIn(new ByteArrayInputStream(input.getBytes()));

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.navigateTaskList(taskList);

    // Assert
    String output = outContent.toString();
    assertTrue(output.contains("ID: 1"));
    assertTrue(output.contains("Name: Single Task"));
    assertTrue(output.contains("This is the last task."));
    assertTrue(output.contains("This is the first task."));
  }

  @Test
  public void testNavigateTaskList_MultipleTasks_NavigateForwardAndBackward() {
    // Arrange
    DoubleLinkedList taskList = new DoubleLinkedList();

    TaskInfo task1 = new TaskInfo();
    task1.setId(1);
    task1.setName("Task 1");
    task1.setDescription("Description 1");
    task1.setCategory("Category 1");
    task1.setDueDate("2024-12-31");
    TaskNode node1 = new TaskNode(task1);

    TaskInfo task2 = new TaskInfo();
    task2.setId(2);
    task2.setName("Task 2");
    task2.setDescription("Description 2");
    task2.setCategory("Category 2");
    task2.setDueDate("2024-11-30");
    TaskNode node2 = new TaskNode(task2);

    TaskInfo task3 = new TaskInfo();
    task3.setId(3);
    task3.setName("Task 3");
    task3.setDescription("Description 3");
    task3.setCategory("Category 3");
    task3.setDueDate("2024-10-15");
    TaskNode node3 = new TaskNode(task3);

    // Düğümler arası bağlantılar
    node1.next = node2;
    node2.prev = node1;
    node2.next = node3;
    node3.prev = node2;

    taskList.head = node1;

    String input = "1\n1\n2\n2\n3\n"; // Next -> Next -> Previous -> Previous -> Exit
    System.setIn(new ByteArrayInputStream(input.getBytes()));

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.navigateTaskList(taskList);

  }

  @Test
  public void testNavigateTaskList_EmptyList() {
    // Arrange
    DoubleLinkedList taskList = new DoubleLinkedList(); // Boş bir liste oluşturduk

    String input = "3\n"; // Çıkış seçeneği
    System.setIn(new ByteArrayInputStream(input.getBytes()));

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.navigateTaskList(taskList);

    // Assert
    String output = outContent.toString();
    assertTrue(output.contains("No tasks available for navigation."));
  }

  @Test
  public void testAddTaskToList_AddSingleTask() {
    // Arrange
    DoubleLinkedList taskList = new DoubleLinkedList();

    TaskInfo taskInfo = new TaskInfo();
    taskInfo.setId(1);
    taskInfo.setName("Task 1");
    taskInfo.setDescription("Description 1");
    taskInfo.setCategory("Category 1");
    taskInfo.setDueDate("2024-12-31");

    // Act
    taskList.addTaskToLinkedList(taskInfo);

    // Assert
    assertNotNull("Head should not be null after adding a task.", taskList.head);
    assertNotNull("Tail should not be null after adding a task.", taskList.tail);
    assertEquals("Head and Tail should point to the same node for a single task.", taskList.head, taskList.tail);
    assertEquals(1, taskList.head.task.getId());
    assertEquals("Task 1", taskList.head.task.getName());
    assertNull("Next node should be null for a single node.", taskList.head.next);
    assertNull("Previous node should be null for a single node.", taskList.head.prev);
  }

  @Test
  public void testAddTaskToList_AddMultipleTasks() {
    // Arrange
    DoubleLinkedList taskList = new DoubleLinkedList();

    TaskInfo task1 = new TaskInfo();
    task1.setId(1);
    task1.setName("Task 1");
    TaskInfo task2 = new TaskInfo();
    task2.setId(2);
    task2.setName("Task 2");
    TaskInfo task3 = new TaskInfo();
    task3.setId(3);
    task3.setName("Task 3");

    // Act
    taskList.addTaskToLinkedList(task1);
    taskList.addTaskToLinkedList(task2);
    taskList.addTaskToLinkedList(task3);

    // Assert
    assertEquals("Head should point to the first task.", 1, taskList.head.task.getId());
    assertEquals("Tail should point to the last task.", 3, taskList.tail.task.getId());

    // Düğümler arası bağlantılar kontrolü
    assertEquals("Second node should be next of the first node.", taskList.head.next.task.getId(), 2);
    assertEquals("Third node should be next of the second node.", taskList.head.next.next.task.getId(), 3);
    assertEquals("Second node should be prev of the third node.", taskList.tail.prev.task.getId(), 2);
    assertEquals("First node should be prev of the second node.", taskList.head.next.prev.task.getId(), 1);
  }

  @Test
  public void testAddTaskToList_EmptyListToOneTask() {
    // Arrange
    DoubleLinkedList taskList = new DoubleLinkedList();

    TaskInfo task = new TaskInfo();
    task.setId(42);
    task.setName("Test Task");

    // Act
    taskList.addTaskToLinkedList(task);

    // Assert
    assertNotNull("Head should not be null after adding one task.", taskList.head);
    assertNotNull("Tail should not be null after adding one task.", taskList.tail);
    assertEquals("Head and Tail should point to the same task.", taskList.head, taskList.tail);
    assertEquals(42, taskList.head.task.getId());
    assertNull("Next should be null for single node.", taskList.head.next);
    assertNull("Prev should be null for single node.", taskList.head.prev);
  }

  @Test
  public void testCategorizeTask_FoundCategory() {
    String simulatedInput = "1\ndsa\ndsa\ndsa\n1000 10 10\n1\n4\n\n3\ndsa\n\n\n9\n6\n3\n";
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.createTaskMenu();
  }

  @Test
  public void testCategorizeTask_NotFoundCategory() {
    String simulatedInput = "asdf\n\n\n9\n6\n3\n"; // 99 -> Invalid choice, 3 -> Exit Program
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.categorizeTask(taskList);
  }

  @Test
  public void testCategorizeTask_EmptyTaskList() {
    String simulatedInput = " \n\n\n9\n6\n3\n"; // 99 -> Invalid choice, 3 -> Exit Program
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.categorizeTask(taskList);
  }


  @Test
  public void testPrintDependencies_WithoutDependencies() {
    // Arrange
    ArrayList<TaskInfo> taskList = new ArrayList<>();

    TaskInfo task1 = new TaskInfo();
    task1.setId(1);
    task1.setName("Task 1");
    task1.setDependencies(new int[]{}); // Bağımlılık yok
    taskList.add(task1);

    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    Task taskManager = new Task(new Scanner(System.in), System.out);

    // Act
    taskManager.printDependencies(taskList, 1);

    // Assert
    String output = outContent.toString();
    assertTrue(output.contains("Dependencies for Task 1:"));
    assertFalse(output.contains("Task ID:"));
  }

  @Test
  public void testAlgorithmsMenuOverflow() {
    // Arrange
    String simulatedInput = "1\n1\nt\nt\nt\nt\n\n2\nt\nt\n\n3\n\n4\n\n8\n6\n3\n"; // Geçersiz giriş -> abc, sonra çıkış
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.algorithmsMenu();

  }

  @Test
  public void testAlgorithmsMenuOverflowInvalid() {
    // Arrange
    String simulatedInput = "1\n5\n\n4\n\n8\n6\n3\n"; // Geçersiz giriş -> abc, sonra çıkış
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.algorithmsMenu();

  }

  @Test
  public void testAlgorithmsMenuOverflowNotFound() {
    // Arrange
    String simulatedInput = "1\n2\nr\nr\n\n4\n\n8\n6\n3\n"; // Geçersiz giriş -> abc, sonra çıkış
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.algorithmsMenu();

  }

  @Test
  public void testAlgorithmsMenuOverflowAlreadyExists() {
    // Arrange
    String simulatedInput = "1\n1\na\na\na\na\n\n4\n\n8\n6\n3\n"; // Geçersiz giriş -> abc, sonra çıkış
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.algorithmsMenu();

  }

  @Test
  public void testAlgorithmsMenuOverflowHandleInputError() {
    // Arrange
    String simulatedInput = "1\n-2\n\n4\n\n8\n6\n3\n"; // Geçersiz giriş -> abc, sonra çıkış
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.algorithmsMenu();

  }

  @Test
  public void testAlgorithmsMenuLinearProbing() {
    // Arrange
    String simulatedInput = "2\n1\np\np\np\np\n\n2\np\np\n\n3\n\n4\n\n8\n6\n3\n"; // Geçersiz giriş -> abc, sonra çıkış
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.algorithmsMenu();

  }

  @Test
  public void testAlgorithmsMenuLinearProbingInvalid() {
    // Arrange
    String simulatedInput = "2\n5\n\n4\n\n8\n6\n3\n"; // Geçersiz giriş -> abc, sonra çıkış
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.algorithmsMenu();

  }

  @Test
  public void testAlgorithmsMenuLinearProbingNotFound() {
    // Arrange
    String simulatedInput = "2\n2\nr\nr\n\n4\n\n8\n6\n3\n"; // Geçersiz giriş -> abc, sonra çıkış
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.algorithmsMenu();

  }

  @Test
  public void testAlgorithmsMenuLinearProbingAlreadyExists() {
    // Arrange
    String simulatedInput = "2\n1\na\na\na\na\n\n4\n\n8\n6\n3\n"; // Geçersiz giriş -> abc, sonra çıkış
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.algorithmsMenu();

  }

  @Test
  public void testAlgorithmsMenuLinearProbingHandleInputError() {
    // Arrange
    String simulatedInput = "2\n-2\n\n4\n\n8\n6\n3\n"; // Geçersiz giriş -> abc, sonra çıkış
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.algorithmsMenu();

  }

  @Test
  public void testAlgorithmsMenuQuadratic() {
    // Arrange
    String simulatedInput = "3\n1\nk\nk\nk\nk\n\n2\nk\nk\n\n3\n\n4\n\n8\n6\n3\n"; // Geçersiz giriş -> abc, sonra çıkış
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.algorithmsMenu();

  }

  @Test
  public void testAlgorithmsMenuQuadraticInvalid() {
    // Arrange
    String simulatedInput = "3\n5\n\n4\n\n8\n6\n3\n"; // Geçersiz giriş -> abc, sonra çıkış
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.algorithmsMenu();

  }

  @Test
  public void testAlgorithmsMenuQuadraticNotFound() {
    // Arrange
    String simulatedInput = "3\n2\nr\nr\n\n4\n\n8\n6\n3\n"; // Geçersiz giriş -> abc, sonra çıkış
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.algorithmsMenu();

  }

  @Test
  public void testAlgorithmsMenuQuadraticAlreadyExists() {
    // Arrange
    String simulatedInput = "3\n1\na\na\na\na\n\n4\n\n8\n6\n3\n"; // Geçersiz giriş -> abc, sonra çıkış
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.algorithmsMenu();

  }

  @Test
  public void testAlgorithmsMenuQuadraticHandleInputError() {
    // Arrange
    String simulatedInput = "3\n-2\n\n4\n\n8\n6\n3\n"; // Geçersiz giriş -> abc, sonra çıkış
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.algorithmsMenu();

  }

  @Test
  public void testAlgorithmsMenuDoubleHashing() {
    // Arrange
    String simulatedInput = "4\n1\nn\nn\nn\nn\n\n2\nn\nn\n\n3\n\n4\n\n8\n6\n3\n"; // Geçersiz giriş -> abc, sonra çıkış
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.algorithmsMenu();

  }

  @Test
  public void testAlgorithmsMenuDoubleHashingInvalid() {
    // Arrange
    String simulatedInput = "4\n5\n\n4\n\n8\n6\n3\n"; // Geçersiz giriş -> abc, sonra çıkış
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.algorithmsMenu();

  }

  @Test
  public void testAlgorithmsMenuDoubleHashingNotFound() {
    // Arrange
    String simulatedInput = "4\n2\nr\nr\n\n4\n\n8\n6\n3\n"; // Geçersiz giriş -> abc, sonra çıkış
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.algorithmsMenu();

  }

  @Test
  public void testAlgorithmsMenuDoubleHashingAlreadyExists() {
    // Arrange
    String simulatedInput = "4\n1\na\na\na\na\n\n4\n\n8\n6\n3\n"; // Geçersiz giriş -> abc, sonra çıkış
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.algorithmsMenu();

  }

  @Test
  public void testAlgorithmsMenuDoubleHashingHandleInputError() {
    // Arrange
    String simulatedInput = "4\n-2\n\n4\n\n8\n6\n3\n"; // Geçersiz giriş -> abc, sonra çıkış
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.algorithmsMenu();

  }

  @Test
  public void testAlgorithmsMenuLinearQuotient() {
    // Arrange
    String simulatedInput = "6\n1\nz\nz\nz\nz\n\n2\nz\nz\n\n3\n\n4\n\n8\n6\n3\n"; // Geçersiz giriş -> abc, sonra çıkış
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.algorithmsMenu();

  }

  @Test
  public void testAlgorithmsMenuLinearQuotientInvalid() {
    // Arrange
    String simulatedInput = "6\n5\n\n4\n\n8\n6\n3\n"; // Geçersiz giriş -> abc, sonra çıkış
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.algorithmsMenu();

  }

  @Test
  public void testAlgorithmsMenuLinearQuotientNotFound() {
    // Arrange
    String simulatedInput = "6\n2\nr\nr\n\n4\n\n8\n6\n3\n"; // Geçersiz giriş -> abc, sonra çıkış
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.algorithmsMenu();

  }

  @Test
  public void testAlgorithmsMenuLinearQuotientAlreadyExists() {
    // Arrange
    String simulatedInput = "6\n1\na\na\na\na\n\n4\n\n8\n6\n3\n"; // Geçersiz giriş -> abc, sonra çıkış
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.algorithmsMenu();

  }

  @Test
  public void testAlgorithmsMenuLinearQuotientHandleInputError() {
    // Arrange
    String simulatedInput = "6\n-2\n\n4\n\n8\n6\n3\n"; // Geçersiz giriş -> abc, sonra çıkış
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.algorithmsMenu();

  }

  @Test
  public void testAlgorithmsMenuBrentsMethod() {
    // Arrange
    String simulatedInput = "7\n1\ng\ng\ng\ng\n\n2\ng\ng\n\n3\n\n4\n\n8\n6\n3\n"; // Geçersiz giriş -> abc, sonra çıkış
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.algorithmsMenu();

  }

  @Test
  public void testAlgorithmsMenuBrentsMethodInvalid() {
    // Arrange
    String simulatedInput = "7\n5\n\n4\n\n8\n6\n3\n"; // Geçersiz giriş -> abc, sonra çıkış
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.algorithmsMenu();

  }

  @Test
  public void testAlgorithmsMenuBrentsMethodNotFound() {
    // Arrange
    String simulatedInput = "7\n2\nr\nr\n\n4\n\n8\n6\n3\n"; // Geçersiz giriş -> abc, sonra çıkış
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.algorithmsMenu();

  }

  @Test
  public void testAlgorithmsMenuBrentsMethodAlreadyExists() {
    // Arrange
    String simulatedInput = "7\n1\na\na\na\na\n\n4\n\n8\n6\n3\n"; // Geçersiz giriş -> abc, sonra çıkış
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.algorithmsMenu();

  }

  @Test
  public void testAlgorithmsMenuBrentsMethodHandleInputError() {
    // Arrange
    String simulatedInput = "7\n-2\n\n4\n\n8\n6\n3\n"; // Geçersiz giriş -> abc, sonra çıkış
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.algorithmsMenu();

  }

  @Test
  public void testMarkTaskImportanceHigh() {
    // Arrange
    String simulatedInput = "1\n1\na\na\na\n1000 10 10\n1\n4\n\n9\n4\n1\na\n1\n\n3\n6\n3\n";
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    task.userOptionsMenu();
  }


  @Test
  public void testMarkTaskImportanceTaskNotFound() {
    // Arrange
    String simulatedInput = "1\n1\na\na\na\n1000 10 10\n1\n4\n\n9\n4\n1\nasd\n\n3\n6\n3\n";
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    task.userOptionsMenu();
  }

  @Test
  public void testMarkTaskImportanceInvalidImportanceID() {
    // Arrange
    String simulatedInput = "1\n1\na\na\na\n1000 10 10\n1\n4\n\n9\n4\n1\na\n5\n\n1\n\n3\n6\n3\n";
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    task.userOptionsMenu();
  }



  @Test
  public void testMarkTaskImportanceMedium() {
    // Arrange
    String simulatedInput = "1\n1\nb\nb\nb\n1100 11 11\n1\n4\n\n9\n4\n1\nb\n2\n\n3\n6\n3\n";
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    task.userOptionsMenu();
  }

  @Test
  public void testMarkTaskImportanceLow() {
    // Arrange
    String simulatedInput = "1\n1\nc\nc\nc\n1110 9 10\n1\n4\n\n9\n4\n1\nc\n3\n\n3\n6\n3\n";
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    task.userOptionsMenu();
  }

  @Test
  public void testImportanceOrdering() {
    // Arrange
    String simulatedInput = "1\n1\na\na\na\n1000 10 10\n1\n4\n\n9\n4\n2\n\n\n3\n6\n3\n";
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    task.userOptionsMenu();

  }

  @Test
  public void testSetReminders() {
    // Arrange
    String simulatedInput = "3\n1\n0\n0\n0\n1\n\n3\n6\n3\n";
    // Arrange
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    task.userOptionsMenu();
  }

  @Test
  public void testNotificationSettings() {
    // Arrange
    String simulatedInput = "3\n2\n1\n\n2\n2\n\n2\n3\n\n3\n6\n3\n";
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    task.userOptionsMenu();
  }

  @Test
  public void testSetRemindersInvalid() {
    // Arrange
    String simulatedInput = "3\n1\n-2\n-2\n-2\n-2\n\n3\n6\n3\n";
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    task.userOptionsMenu();


  }

  @Test
  public void testLoginUser_IncorrectEmailOrPassword() {
    // Arrange
    String userFilePath = "test_users.bin";
    String huffmanFilePath = "user.huf";

    // Test için geçersiz kullanıcı oluştur
    User loginUser = new User();
    loginUser.setEmail("wrong.email@example.com");
    loginUser.setPassword("wrongpassword");

    // Kullanıcı dosyası oluştur
    try (RandomAccessFile raf = new RandomAccessFile(userFilePath, "rw")) {
      raf.writeInt(1); // Kullanıcı sayısı: 1

      User validUser = new User();
      validUser.setId(1);
      validUser.setName("Valid");
      validUser.setSurname("User");
      validUser.setEmail("valid.email@example.com");
      validUser.setPassword("validpassword");
      validUser.writeToFile(raf);
    } catch (IOException e) {
      fail("Failed to set up user file: " + e.getMessage());
    }

    // Huffman dosyasını oluştur
    try (DataOutputStream huffDos = new DataOutputStream(new FileOutputStream(huffmanFilePath))) {
      String encodedEmail = HuffmanCoding.huffmanEncode("valid.email@example.com");
      String encodedPassword = HuffmanCoding.huffmanEncode("validpassword");

      huffDos.writeInt(1); // ID
      huffDos.writeInt(encodedEmail.length());
      huffDos.writeBytes(encodedEmail);
      huffDos.writeInt(encodedPassword.length());
      huffDos.writeBytes(encodedPassword);
    } catch (IOException e) {
      fail("Failed to set up Huffman file: " + e.getMessage());
    }

    // Çıktıyı yakalamak için
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    int result = task.loginUser(loginUser, userFilePath);

    // Assert
    String output = outContent.toString();
    assertEquals(0, result); // Hatalı giriş bekleniyor
    assertTrue(output.contains("ERROR: Incorrect email or password."));

    // Temizlik
    System.setOut(System.out);
    new File(userFilePath).delete();
    new File(huffmanFilePath).delete();
  }

  @Test
  public void testSearchUserInHashTable_UserFound() {
    // Arrange
    String testEmail = "test.user@example.com";
    String testPassword = "testpassword";

    User testUser = new User();
    testUser.setId(1);
    testUser.setName("Test");
    testUser.setSurname("User");
    testUser.setEmail(testEmail);
    testUser.setPassword(testPassword);

    int index = Task.hashFunction(testEmail);

    if (Task.hashTable[index] == null) {
      Task.hashTable[index] = new LinkedList<>();
    }
    Task.hashTable[index].add(testUser);

    // Act
    User result = Task.searchUserInHashTable(testEmail, testPassword);

    // Assert
    assertNotNull("User should be found in the hash table", result);
    assertEquals("Emails should match", testEmail, result.getEmail());
    assertEquals("Passwords should match", testPassword, result.getPassword());
  }

  @Test
  public void testSearchUserInHashTable_UserNotFound() {
    // Arrange
    String testEmail = "nonexistent.user@example.com";
    String testPassword = "wrongpassword";

    // Act
    User result = Task.searchUserInHashTable(testEmail, testPassword);

    // Assert
    assertNull("User should not be found in the hash table", result);
  }

  @Test
  public void testSearchUserInHashTable_EmptyHashTable() {
    // Arrange
    String testEmail = "empty.table@example.com";
    String testPassword = "emptypassword";

    // Hash tablosunu temizle
    for (int i = 0; i < Task.hashTable.length; i++) {
      Task.hashTable[i] = null;
    }

    // Act
    User result = Task.searchUserInHashTable(testEmail, testPassword);

    // Assert
    assertNull("User should not be found in an empty hash table", result);
  }

  @Test
  public void testRegisterUser_UserAlreadyExists() {
    // Arrange
    String pathFileUser = "test_users.bin";
    File file = new File(pathFileUser);

    // Örnek kullanıcı oluştur
    User existingUser = new User();
    existingUser.setId(1);
    existingUser.setName("Test");
    existingUser.setSurname("User");
    existingUser.setEmail("test.user@example.com");
    existingUser.setPassword("password123");

    User newUser = new User();
    newUser.setId(2);
    newUser.setName("Test");
    newUser.setSurname("User");
    newUser.setEmail("test.user@example.com"); // Aynı email
    newUser.setPassword("password123");

    try {
      // Test dosyasını oluştur ve mevcut kullanıcıyı yaz
      try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
        raf.writeInt(1); // Kullanıcı sayısı

        // Mevcut kullanıcıyı dosyaya yaz
        existingUser.writeToFile(raf);
      }

      // Kullanıcıyı hash tablosuna ekle
      Task.insertUserToHashTable(existingUser);

      // Act
      Task task = new Task(new Scanner(System.in), System.out);
      int result = task.registerUser(newUser, pathFileUser);

      // Assert
      assertEquals(0, result); // Kullanıcı zaten var
      String output = outContent.toString();
      assertTrue(output.contains("User already exists."));

    } catch (IOException e) {
      fail("Test setup failed: " + e.getMessage());
    } finally {
      // Test dosyasını temizle
      file.delete();
    }
  }

  @Test
  public void testRegisterUser_FileDoesNotExist() {
    // Arrange
    String pathFileUser = "non_existing_users.bin";
    File file = new File(pathFileUser);

    if (file.exists()) {
      file.delete();
    }

    User newUser = new User();
    newUser.setId(1);
    newUser.setName("New");
    newUser.setSurname("User");
    newUser.setEmail("new.user@example.com");
    newUser.setPassword("password123");

    // Act
    Task task = new Task(new Scanner(System.in), System.out);
    int result = task.registerUser(newUser, pathFileUser);

    // Assert
    assertEquals(1, result); // Kullanıcı başarıyla kaydedildi
    assertTrue(file.exists()); // Dosya oluşturulmuş olmalı

    // Cleanup
    file.delete();
  }

  @Test
  public void testRegisterUser_EmptyFile() {
    // Arrange
    String pathFileUser = "empty_users.bin";
    File file = new File(pathFileUser);

    try {
      if (file.exists()) {
        file.delete();
      }
      file.createNewFile();

      User newUser = new User();
      newUser.setId(1);
      newUser.setName("New");
      newUser.setSurname("User");
      newUser.setEmail("new.user@example.com");
      newUser.setPassword("password123");

      // Act
      Task task = new Task(new Scanner(System.in), System.out);
      int result = task.registerUser(newUser, pathFileUser);



    } catch (IOException e) {
      fail("Test setup failed: " + e.getMessage());
    } finally {
      // Cleanup
      file.delete();
    }
  }

  @Test
  public void testAddTaskToList_SingleTask() {
    // Arrange
    TaskInfo newTask = new TaskInfo();
    newTask.setId(1);
    newTask.setName("Test Task");
    newTask.setDescription("This is a test task");
    newTask.setCategory("Testing");
    newTask.setDueDate("2024-12-31");

    DoubleLinkedList taskList = new DoubleLinkedList();

    // Act
    taskList.addTaskToLinkedList(newTask);

  }

  @Test
  public void testAddTaskToList_MultipleTasks() {
    // Arrange
    TaskInfo task1 = new TaskInfo();
    task1.setId(1);
    task1.setName("Task 1");

    TaskInfo task2 = new TaskInfo();
    task2.setId(2);
    task2.setName("Task 2");

    DoubleLinkedList taskList = new DoubleLinkedList();

    // Act
    taskList.addTaskToLinkedList(task1);
    taskList.addTaskToLinkedList(task2);

  }

  @Test
  public void testPrintDependenciesUtil_WithValidDependencies() {
    // Arrange
    ArrayList<TaskInfo> taskList = new ArrayList<>();

    TaskInfo task1 = new TaskInfo();
    task1.setId(1);
    task1.setDependencies(new int[]{2, 3});

    TaskInfo task2 = new TaskInfo();
    task2.setId(2);
    task2.setDependencies(new int[]{4});

    TaskInfo task3 = new TaskInfo();
    task3.setId(3);
    task3.setDependencies(new int[]{0}); // Bağımlılık yok

    TaskInfo task4 = new TaskInfo();
    task4.setId(4);
    task4.setDependencies(new int[]{0}); // Bağımlılık yok

    taskList.add(task1);
    taskList.add(task2);
    taskList.add(task3);
    taskList.add(task4);

    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    Task task = new Task(new Scanner(System.in), System.out);

    boolean[] visited = new boolean[taskList.size()];

    // Act
    task.printDependencies(taskList, 1);

  }

  @Test
  public void testPrintDependenciesUtil_WithNoDependencies() {
    // Arrange
    ArrayList<TaskInfo> taskList = new ArrayList<>();

    TaskInfo task1 = new TaskInfo();
    task1.setId(1);
    task1.setDependencies(new int[]{0}); // Bağımlılık yok

    taskList.add(task1);

    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    Task task = new Task(new Scanner(System.in), System.out);

    boolean[] visited = new boolean[taskList.size()];

    // Act
    task.printDependencies(taskList, 1);

    // Assert
    String output = outContent.toString();
    assertFalse(output.contains("depends on Task"));
  }

  @Test
  public void testPrintDependenciesUtil_WithCircularDependency() {
    // Arrange
    ArrayList<TaskInfo> taskList = new ArrayList<>();

    TaskInfo task1 = new TaskInfo();
    task1.setId(1);
    task1.setDependencies(new int[]{2});

    TaskInfo task2 = new TaskInfo();
    task2.setId(2);
    task2.setDependencies(new int[]{1}); // Döngü var: 1 -> 2 -> 1

    taskList.add(task1);
    taskList.add(task2);

    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    Task task = new Task(new Scanner(System.in), System.out);

    boolean[] visited = new boolean[taskList.size()];

    // Act
    task.printDependencies(taskList, 1);

  }

  @Test
  public void testComputePrefixTable_WithValidPattern() {
    // Arrange
    String pattern = "ABABCABAA";
    int[] expectedPrefixTable = {0, 0, 1, 2, 0, 1, 2, 3, 1};
    int[] prefixTable = new int[pattern.length()];

    // Act
    Task.computePrefixTable(pattern, prefixTable);

  }

  @Test
  public void testComputePrefixTable_WithRepeatedCharacters() {
    // Arrange
    String pattern = "AAAA";
    int[] expectedPrefixTable = {0, 1, 2, 3};
    int[] prefixTable = new int[pattern.length()];

    // Act
    Task.computePrefixTable(pattern, prefixTable);

  }

  @Test
  public void testComputePrefixTable_WithNoRepeatingPattern() {
    // Arrange
    String pattern = "ABCDE";
    int[] expectedPrefixTable = {0, 0, 0, 0, 0};
    int[] prefixTable = new int[pattern.length()];

    // Act
    Task.computePrefixTable(pattern, prefixTable);

  }

  @Test
  public void testComputePrefixTable_WithSingleCharacter() {
    // Arrange
    String pattern = "A";
    int[] expectedPrefixTable = {0};
    int[] prefixTable = new int[pattern.length()];

    // Act
    Task.computePrefixTable(pattern, prefixTable);

  }

  @Test
  public void testAddTaskToList_AddToEmptyList() {
    // Arrange
    Task task = new Task(new Scanner(System.in), System.out);
    TaskInfo newTask = new TaskInfo();
    newTask.setId(1);
    newTask.setName("Test Task");
    newTask.setDescription("This is a test task.");
    newTask.setCategory("General");
    newTask.setDueDate("2024-12-31");

    // Act
    task.addTaskToList(newTask);

  }

  @Test
  public void testAddTaskToList_AddToNonEmptyList() {
    // Arrange
    Task task = new Task(new Scanner(System.in), System.out);
    TaskInfo firstTask = new TaskInfo();
    firstTask.setId(1);
    firstTask.setName("First Task");

    TaskInfo secondTask = new TaskInfo();
    secondTask.setId(2);
    secondTask.setName("Second Task");

    // İlk görevi ekle
    task.addTaskToList(firstTask);

    // Act: İkinci görevi ekle
    task.addTaskToList(secondTask);

    // Assert
    assertNotNull("Head should not be null after adding tasks.", task.head);
    assertNotNull("Tail should not be null after adding tasks.", task.tail);
    assertEquals("Head should point to the first task.", 1, task.head.task.getId());
    assertEquals("Tail should point to the second task.", 2, task.tail.task.getId());
    assertEquals("Next of head should point to second task.", task.head.next.task.getId(), 2);
    assertEquals("Previous of tail should point to first task.", task.tail.prev.task.getId(), 1);
    assertNull("Next of tail should be null.", task.tail.next);
  }


  @Test
  public void testViewDeadlines_EmptyHeap() {
    // Arrange
    Task task = new Task(new Scanner(System.in), System.out);
    task.deadlineHeap = new PriorityQueue<>(); // Boş heap

    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    // Act
    int result = task.viewDeadlines();

  }

  @Test
  public void testAssignmentConstructorAndGetters() {
    // Arrange
    Assignment assignment = new Assignment("Test Task", 15, 8, 2024);

    // Assert
    assertEquals("Test Task", assignment.getName());
    assertEquals(15, assignment.getDay());
    assertEquals(8, assignment.getMonth());
    assertEquals(2024, assignment.getYear());
  }

  @Test
  public void testAssignmentSetters() {
    // Arrange
    Assignment assignment = new Assignment("Task", 1, 1, 2023);

    // Act
    assignment.setName("Updated Task");
    assignment.setDay(12);
    assignment.setMonth(12);
    assignment.setYear(2025);

    // Assert
    assertEquals("Updated Task", assignment.getName());
    assertEquals(12, assignment.getDay());
    assertEquals(12, assignment.getMonth());
    assertEquals(2025, assignment.getYear());
  }

  @Test
  public void testAssignmentComparison() {
    // Arrange
    Assignment earlier = new Assignment("Task 1", 1, 1, 2024);
    Assignment later = new Assignment("Task 2", 1, 1, 2025);

    // Assert
    assertTrue(earlier.compareTo(later) < 0); // earlier < later
    assertTrue(later.compareTo(earlier) > 0); // later > earlier
    assertEquals(0, earlier.compareTo(new Assignment("Task 3", 1, 1, 2024))); // same date
  }

  @Test
  public void testAssignDeadline_InvalidDate() {
    // Arrange
    PriorityQueue<Assignment> deadlineHeap = new PriorityQueue<>();
    String simulatedInput = "Invalid Task\n32 13 2024\n";
    System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

    // Act
    Assignment.assign_deadline(deadlineHeap);

    // Assert
    String output = outContent.toString();
    assertTrue(output.contains("Invalid date! Please enter a valid date."));
    assertEquals(0, deadlineHeap.size());
  }

  @Test
  public void testAssignDeadline_InvalidInputType() {
    // Arrange
    PriorityQueue<Assignment> deadlineHeap = new PriorityQueue<>();
    String simulatedInput = "Task Name\ninvalid invalid invalid\n";
    System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

    // Act
    Assignment.assign_deadline(deadlineHeap);

    // Assert
    String output = outContent.toString();
    assertTrue(output.contains("Invalid input! Please try again."));
    assertEquals(0, deadlineHeap.size());
  }

  @Test
  public void testAssignDeadline_ValidInput() {
    // Arrange
    PriorityQueue<Assignment> deadlineHeap = new PriorityQueue<>();
    String simulatedInput = "Valid Task\n15 08 2024\n";
    System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

    // Act
    Assignment.assign_deadline(deadlineHeap);

    // Assert
    String output = outContent.toString();
    assertTrue(output.contains("Deadline assigned and saved successfully!"));
    assertEquals(1, deadlineHeap.size());

    Assignment added = deadlineHeap.poll();
    assertNotNull(added);
    assertEquals("Valid Task", added.getName());
    assertEquals(15, added.getDay());
    assertEquals(8, added.getMonth());
    assertEquals(2024, added.getYear());
  }

  @Test
  public void testWriteToFile() throws IOException {
    // Arrange
    String fileName = "test_deadlines.bin";
    Files.deleteIfExists(Paths.get(fileName));
    Assignment assignment = new Assignment("File Task", 10, 10, 2024);

    // Act
    assignment.writeToFile(fileName);

    // Assert
    assertTrue(Files.exists(Paths.get(fileName)));

    // Dosyadan oku
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
      Assignment loadedAssignment = (Assignment) ois.readObject();
      assertEquals("File Task", loadedAssignment.getName());
      assertEquals(10, loadedAssignment.getDay());
      assertEquals(10, loadedAssignment.getMonth());
      assertEquals(2024, loadedAssignment.getYear());
    } catch (ClassNotFoundException e) {
      fail("Class not found during deserialization.");
    } finally {
      Files.deleteIfExists(Paths.get(fileName));
    }
  }


  // Test 4: Boş XOR Linked List'te Gezinme
  @Test
  public void testNavigateEmptyXORListt() {
    // Arrange
    XORLinkedList xorList = new XORLinkedList();

    // Act
    xorList.navigateXORList();

    // Assert
    String output = outContent.toString();
    assertTrue(output.contains("No tasks found in the list."));
  }


  // Test 1: Yaprak Düğüm Oluşturma
  @Test
  public void testLeafNodeCreation() {
    // Arrange
    BPlusTreeNode leafNode = new BPlusTreeNode(true);

    // Assert
    assertTrue(leafNode.isLeaf);
    assertNotNull(leafNode.keys);
    assertNotNull(leafNode.tasks);
    assertEquals(0, leafNode.keys.size());
    assertEquals(0, leafNode.tasks.size());
    assertNull(leafNode.next);
  }

  // Test 2: İç Düğüm Oluşturma
  @Test
  public void testInternalNodeCreation() {
    // Arrange
    BPlusTreeNode internalNode = new BPlusTreeNode(false);

    // Assert
    assertFalse(internalNode.isLeaf);
    assertNotNull(internalNode.keys);
    assertNotNull(internalNode.children);
    assertEquals(0, internalNode.keys.size());
    assertEquals(0, internalNode.children.size());
  }

  // Test 3: Yaprak Düğüme Anahtar ve Görev Ekleme
  @Test
  public void testAddKeyAndTaskToLeafNode() {
    // Arrange
    BPlusTreeNode leafNode = new BPlusTreeNode(true);
    ScheduledTask task1 = new ScheduledTask("Task 1", 30, 12,2001);
    ScheduledTask task2 = new ScheduledTask("Task 2", 29,11,2002);

    // Act
    leafNode.keys.add(10);
    leafNode.tasks.add(task1);

    leafNode.keys.add(20);
    leafNode.tasks.add(task2);

    // Assert
    assertEquals(2, leafNode.keys.size());
    assertEquals(2, leafNode.tasks.size());
    assertEquals(Integer.valueOf(10), leafNode.keys.get(0));
    assertEquals("Task 1", leafNode.tasks.get(0).getName());
    assertEquals(Integer.valueOf(20), leafNode.keys.get(1));
    assertEquals("Task 2", leafNode.tasks.get(1).getName());
  }

  // Test 4: İç Düğüme Anahtar ve Çocuk Düğüm Ekleme
  @Test
  public void testAddKeyAndChildrenToInternalNode() {
    // Arrange
    BPlusTreeNode internalNode = new BPlusTreeNode(false);
    BPlusTreeNode childNode1 = new BPlusTreeNode(true);
    BPlusTreeNode childNode2 = new BPlusTreeNode(true);

    // Act
    internalNode.keys.add(15);
    internalNode.children.add(childNode1);
    internalNode.children.add(childNode2);

    // Assert
    assertEquals(1, internalNode.keys.size());
    assertEquals(2, internalNode.children.size());
    assertEquals(Integer.valueOf(15), internalNode.keys.get(0));
    assertSame(childNode1, internalNode.children.get(0));
    assertSame(childNode2, internalNode.children.get(1));
  }

  // Test 5: Yaprak Düğümler Arasında Bağlantı Kurma
  @Test
  public void testLeafNodeLinking() {
    // Arrange
    BPlusTreeNode leafNode1 = new BPlusTreeNode(true);
    BPlusTreeNode leafNode2 = new BPlusTreeNode(true);

    // Act
    leafNode1.next = leafNode2;

    // Assert
    assertSame(leafNode2, leafNode1.next);
    assertNull(leafNode2.next);
  }

  // Test 6: Yaprak Düğümler Arasında Gezinme
  @Test
  public void testLeafNodeTraversal() {
    // Arrange
    BPlusTreeNode leafNode1 = new BPlusTreeNode(true);
    BPlusTreeNode leafNode2 = new BPlusTreeNode(true);
    BPlusTreeNode leafNode3 = new BPlusTreeNode(true);

    leafNode1.next = leafNode2;
    leafNode2.next = leafNode3;

    // Act
    BPlusTreeNode current = leafNode1;
    int count = 0;
    while (current != null) {
      count++;
      current = current.next;
    }

    // Assert
    assertEquals(3, count);
  }


  // Test 1: Kuyruğa Görev Ekleme
  @Test
  public void testEnqueueTask() {
    // Arrange
    TaskQueue taskQueue = new TaskQueue();
    TaskInfo task1 = new TaskInfo();
    task1.setId(1);
    task1.setName("Task 1");

    // Act
    taskQueue.enqueue(task1);

    // Assert
    assertEquals(1, taskQueue.size());
    assertFalse(taskQueue.isEmpty());
  }

  // Test 2: Kuyruktan Görev Çıkarma
  @Test
  public void testDequeueTask() {
    // Arrange
    TaskQueue taskQueue = new TaskQueue();
    TaskInfo task1 = new TaskInfo();
    task1.setId(1);
    task1.setName("Task 1");

    taskQueue.enqueue(task1);

    // Act
    TaskInfo dequeuedTask = taskQueue.dequeue();

    // Assert
    assertEquals("Task 1", dequeuedTask.getName());
    assertEquals(0, taskQueue.size());
    assertTrue(taskQueue.isEmpty());
  }

  // Test 3: Boş Kuyruktan Görev Çıkarma
  @Test
  public void testDequeueFromEmptyQueue() {
    // Arrange
    TaskQueue taskQueue = new TaskQueue();

    // Act
    TaskInfo dequeuedTask = taskQueue.dequeue();

    // Assert
    assertNull(dequeuedTask);
    assertTrue(taskQueue.isEmpty());
  }

  // Test 4: Kuyruğun Boş Olup Olmadığını Kontrol Etme
  @Test
  public void testIsEmptyQueue() {
    // Arrange
    TaskQueue taskQueue = new TaskQueue();

    // Act & Assert
    assertTrue(taskQueue.isEmpty());

    TaskInfo task = new TaskInfo();
    taskQueue.enqueue(task);

    assertFalse(taskQueue.isEmpty());
  }

  // Test 5: Kuyruk Boyutunun Doğru Olup Olmadığını Kontrol Etme
  @Test
  public void testQueueSize() {
    // Arrange
    TaskQueue taskQueue = new TaskQueue();

    TaskInfo task1 = new TaskInfo();
    task1.setId(1);
    task1.setName("Task 1");

    TaskInfo task2 = new TaskInfo();
    task2.setId(2);
    task2.setName("Task 2");

    // Act
    taskQueue.enqueue(task1);
    taskQueue.enqueue(task2);

    // Assert
    assertEquals(2, taskQueue.size());

    taskQueue.dequeue();
    assertEquals(1, taskQueue.size());

    taskQueue.dequeue();
    assertEquals(0, taskQueue.size());
  }

  // Test 6: Birden Fazla Görev Ekleme ve Çıkarma
  @Test
  public void testEnqueueAndDequeueMultipleTasks() {
    // Arrange
    TaskQueue taskQueue = new TaskQueue();

    TaskInfo task1 = new TaskInfo();
    task1.setId(1);
    task1.setName("Task 1");

    TaskInfo task2 = new TaskInfo();
    task2.setId(2);
    task2.setName("Task 2");

    TaskInfo task3 = new TaskInfo();
    task3.setId(3);
    task3.setName("Task 3");

    // Act
    taskQueue.enqueue(task1);
    taskQueue.enqueue(task2);
    taskQueue.enqueue(task3);

    // Assert
    assertEquals(3, taskQueue.size());

    assertEquals("Task 1", taskQueue.dequeue().getName());
    assertEquals("Task 2", taskQueue.dequeue().getName());
    assertEquals("Task 3", taskQueue.dequeue().getName());

    assertEquals(0, taskQueue.size());
    assertTrue(taskQueue.isEmpty());
  }

  // Test 1: Ana Menüye Başarıyla Erişim
  @Test
  public void testMainMenuAccess() {
    // Arrange
    String simulatedInput = "3\n"; // 3 -> Exit seçeneği
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    System.setIn(inputStream);

    // Act
    taskApp.main(new String[]{});

    // Assert
    String output = outContent.toString();
    assertTrue(output.contains("WELCOME TO TASK MANAGER!"));
    assertTrue(output.contains("1. Login"));
    assertTrue(output.contains("2. Register"));
    assertTrue(output.contains("3. Exit Program"));
  }




  // Test 4: Kullanıcı Kaydı Senaryosu
  @Test
  public void testUserRegistrationFlow() {
    // Arrange
    String simulatedInput = "2\nJane\nSmith\njane.smith@example.com\nsecurepassword2\n3\n"; // 2 -> Register, ardından kullanıcı bilgileri, ardından çıkış
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    System.setIn(inputStream);

    // Act
    taskApp.main(new String[]{});

  }


  // Test 6: Programdan Çıkış
  @Test
  public void testExitProgram() {
    // Arrange
    String simulatedInput = "3\n"; // Çıkış
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    System.setIn(inputStream);

    // Act
    taskApp.main(new String[]{});


  }


  @Test
  public void testCreateTaskMenuDependenn() {
    // Arrange
    String input = "1\na\na\na\n2222 11 12\n1\n1\n\n4\n1\n9\n6\n3\n"; // 4: Görev bağımlılıklarını görüntüleme seçeneği, 1: Görev ID'si
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Kullanıcı girdisini simüle et
    System.setOut(new PrintStream(outContent)); // Konsol çıktısını yakala

    // Test görev listesini hazırla
    ArrayList<TaskInfo> testTaskList = new ArrayList<>();
    TaskInfo task1 = new TaskInfo();
    task1.setId(1);
    task1.setName("Sample Task 1");
    task1.setDescription("Description 1");
    task1.setCategory("Category 1");
    task1.setDueDate("2024-12-31");
    task1.setDependencyCount(2);
    task1.setDependencies(new int[]{2, 3});

    TaskInfo task2 = new TaskInfo();
    task2.setId(2);
    task2.setName("Sample Task 2");
    task2.setDescription("Description 2");

    TaskInfo task3 = new TaskInfo();
    task3.setId(3);
    task3.setName("Sample Task 3");
    task3.setDescription("Description 3");

    testTaskList.add(task1);
    testTaskList.add(task2);
    testTaskList.add(task3);

    Task task = new Task(new Scanner(System.in), System.out);
    taskList = testTaskList;

    // Act
    task.createTaskMenu();

    // Cleanup
    System.setIn(originalIn);
    System.setOut(originalOut);
  }

  @Test
  public void testInsert() {
    minHeap.insert(assignment1);
    minHeap.insert(assignment2);
    minHeap.insert(assignment3);

    assertFalse(minHeap.isEmpty());
  }

  /**
   * Test: extractMin
   * Kontrol: En erken tarihe sahip görevin doğru şekilde çıkarıldığını doğrula.
   */
  @Test
  public void testExtractMin() {
    minHeap.insert(assignment3); // 10/01/2024
    minHeap.insert(assignment1); // 01/01/2024
    minHeap.insert(assignment2); // 05/01/2024

    Assignment min = minHeap.extractMin();
    assertEquals("Task 1", min.getName());
    assertEquals(1, min.getDay());
    assertEquals(1, min.getMonth());
    assertEquals(2024, min.getYear());
  }

  /**
   * Test: isEmpty
   * Kontrol: Heap boşken isEmpty() metodunun doğru sonuç döndürdüğünü doğrula.
   */
  @Test
  public void testIsEmpty() {
    assertTrue(minHeap.isEmpty());
    minHeap.insert(assignment1);
    assertFalse(minHeap.isEmpty());
  }

  /**
   * Test: printHeap
   * Kontrol: printHeap çıktısının doğru formatta olduğunu doğrula.
   */
  @Test
  public void testPrintHeap() {
    minHeap.insert(assignment1);
    minHeap.insert(assignment2);
    minHeap.insert(assignment3);

    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    minHeap.printHeap();

    String output = outContent.toString();
    assertTrue(output.contains("Task Name: Task 1, Deadline: 1/1/2024"));
    assertTrue(output.contains("Task Name: Task 2, Deadline: 5/1/2024"));
    assertTrue(output.contains("Task Name: Task 3, Deadline: 10/1/2024"));

    System.setOut(System.out);
  }

  /**
   * Test: extractMin - Empty Heap
   * Kontrol: Boş heap'ten eleman çıkarılmaya çalışıldığında null döndüğünü doğrula.
   */
  @Test
  public void testExtractMin_EmptyHeap() {
    Assignment min = minHeap.extractMin();
    assertNull(min);
  }

  /**
   * Test: MinHeap Sıralama
   * Kontrol: En küçük tarihli görevin her zaman en üstte olduğunu doğrula.
   */
  @Test
  public void testMinHeapOrdering() {
    minHeap.insert(assignment2); // 05/01/2024
    minHeap.insert(assignment3); // 10/01/2024
    minHeap.insert(assignment1); // 01/01/2024

    Assignment first = minHeap.extractMin();
    assertEquals("Task 1", first.getName());

    Assignment second = minHeap.extractMin();
    assertEquals("Task 2", second.getName());

    Assignment third = minHeap.extractMin();
    assertEquals("Task 3", third.getName());
  }

  @Test
  public void testGetDateKey() {
    int key = bPlusTree.getDateKey(1, 1, 2024);
    assertEquals(20240101, key);
  }

  /**
   * Test: insertInLeaf
   * Kontrol: Görevlerin doğru sırayla yaprak düğüme eklendiğini doğrula.
   */
  @Test
  public void testInsertInLeaf() {
    BPlusTreeNode leaf = new BPlusTreeNode(true);

    int key1 = bPlusTree.getDateKey(1, 1, 2024);
    int key2 = bPlusTree.getDateKey(5, 1, 2024);

    bPlusTree.insertInLeaf(leaf, key1, task1);
    bPlusTree.insertInLeaf(leaf, key2, task2);

    assertEquals(2, leaf.keys.size());
    assertEquals(20240101, (int) leaf.keys.get(0));
    assertEquals(20240105, (int) leaf.keys.get(1));
    assertEquals("Task 1", leaf.tasks.get(0).getName());
    assertEquals("Task 2", leaf.tasks.get(1).getName());
  }

  /**
   * Test: insertInBPlusTree
   * Kontrol: Görevlerin ağaca doğru şekilde eklendiğini doğrula.
   */
  @Test
  public void testInsertInBPlusTree() {
    bPlusTree.insertInBPlusTree(task1);
    bPlusTree.insertInBPlusTree(task2);
    bPlusTree.insertInBPlusTree(task3);

    assertEquals(3, bPlusTree.root.keys.size());
    assertEquals(20240101, (int) bPlusTree.root.keys.get(0));
    assertEquals(20240105, (int) bPlusTree.root.keys.get(1));
    assertEquals(20240110, (int) bPlusTree.root.keys.get(2));
  }

  /**
   * Test: searchInDateRange
   * Kontrol: Belirli bir tarih aralığında doğru görevlerin döndüğünü doğrula.
   */
  @Test
  public void testSearchInDateRange() {
    bPlusTree.insertInBPlusTree(task1);
    bPlusTree.insertInBPlusTree(task2);
    bPlusTree.insertInBPlusTree(task3);

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));

    bPlusTree.searchInDateRange(bPlusTree.root, 20240101, 20240105);

    String output = outputStream.toString();
    assertTrue(output.contains("Task: Task 1"));
    assertTrue(output.contains("Task: Task 2"));
    assertFalse(output.contains("Task: Task 3"));

    System.setOut(System.out);
  }

  /**
   * Test: viewDeadlinesInRange
   * Kontrol: Kullanıcı girdisi ile tarih aralığında görevlerin doğru görüntülendiğini doğrula.
   */
  @Test
  public void testViewDeadlinesInRange() {
    bPlusTree.insertInBPlusTree(task1);
    bPlusTree.insertInBPlusTree(task2);
    bPlusTree.insertInBPlusTree(task3);

    String input = "1 1 2024\n5 1 2024\n";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));

    bPlusTree.viewDeadlinesInRange();

    String output = outputStream.toString();
    assertTrue(output.contains("Tasks between 01/01/2024 and 05/01/2024"));
    assertTrue(output.contains("Task: Task 1"));
    assertTrue(output.contains("Task: Task 2"));
    assertFalse(output.contains("Task: Task 3"));

    System.setIn(System.in);
    System.setOut(System.out);
  }

  /**
   * Test: insertInBPlusTree - Node Split Uyarısı
   * Kontrol: Maksimum anahtar sınırına ulaşıldığında doğru uyarı verilip verilmediğini doğrula.
   */
  @Test
  public void testNodeSplitRequired() {
    ScheduledTask task4 = new ScheduledTask("Task 4", 15, 1, 2024);

    bPlusTree.insertInBPlusTree(task1);
    bPlusTree.insertInBPlusTree(task2);
    bPlusTree.insertInBPlusTree(task3);
    bPlusTree.insertInBPlusTree(task4);

    String output = new String(outContent.toByteArray());
    assertTrue(output.contains("Node splitting required. Implement split logic here."));
  }

  
  @Test
  public void testNavigateXORList() {
    xorLinkedList.addTaskToXORList(task101);
    xorLinkedList.addTaskToXORList(task202);
    xorLinkedList.addTaskToXORList(task303);

    String input = "1\n1\n2\n2\n0\n"; // İleri -> İleri -> Geri -> Geri -> Çıkış
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);

    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    xorLinkedList.navigateXORList();



    System.setIn(System.in);
    System.setOut(System.out);
  }

  /**
   * Test: navigateXORList - Empty List
   * Kontrol: Boş XOR bağlı liste üzerinde gezinme işlemi doğru uyarı döndürüyor mu.
   */
  @Test
  public void testNavigateEmptyXORList() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    xorLinkedList.navigateXORList();

    String output = outContent.toString();
    assertTrue(output.contains("No tasks found in the list."));

    System.setOut(System.out);
  }

  /**
   * Test: loadTasksToXORList - Invalid File
   * Kontrol: Geçersiz dosya adıyla yükleme işlemi hata döndürüyor mu.
   */
  @Test
  public void testLoadTasksToXORListInvalidFile() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    xorLinkedList.loadTasksToXORList("invalid_file.bin");

    String output = outContent.toString();
    assertTrue(output.contains("Error loading tasks"));

    System.setOut(System.out);
  }

  @Test
  public void testViewTask() {
    // Arrange
    String simulatedInput = "1\n1\na\na\na\n1000 10 10\n1\n4\n\n9\n2\n1\na\n10 10 2010\n2\n\n\n4\n6\n3\n";
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    task.userOptionsMenu();
  }

}
