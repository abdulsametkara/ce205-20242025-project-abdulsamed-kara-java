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
import java.util.Scanner;
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

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  private final InputStream originalIn = System.in;
  Scanner inputScanner = new Scanner(System.in);

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
    String input = "4\n1\n\n9\n6\n3\n";
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
/*  @Test
  public void testCreateTaskMenuXORLinkedList() {
    // Arrange
    String input = "8\n1\n2\n0\n\n9\n6\n3\n";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simulated user input
    System.setOut(new PrintStream(outContent)); // Capture output

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.createTaskMenu();
    // Cleanup
    System.setIn(originalIn);
  }*/



  @Test
  public void testMainMenu_ValidInputs() {
    // Arrange
    String simulatedInput = "1\njohn.doe@example.com\nsecurepassword1\n6\n3\n";
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

//  @Test
//  public void testViewDeadlineInRangeTest() {
//    // Arrange
//    String input = "3\n12 12 2222\n13 12 2222\n\n4\n6\n3\n";
//    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
//    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//
//    System.setIn(inputStream); // Simulated user input
//    System.setOut(new PrintStream(outContent)); // Capture output
//
//    Task task = new Task(new Scanner(System.in), System.out);
//
//    // Act
//    task.deadlineSettingsMenu();
//    // Cleanup
//    System.setIn(originalIn);
//  }
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
    String simulatedInput = "Category 1\n\n\n9\n6\n3\n"; // 99 -> Invalid choice, 3 -> Exit Program
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.categorizeTask(taskList);
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
    String simulatedInput = "1\nt\nt\nt\nt\n\n2\nt\nt\n\n3\n\n4\n\n8\n6\n3\n"; // Geçersiz giriş -> abc, sonra çıkış
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.algorithmsMenu();

  }

  /*@Test
  public void testAlgorithmsMenuProbing() {
    // Arrange
    String simulatedInput = "2\naa\naa\naa\naa\n\n\n8\n6\n3\n"; // Geçersiz giriş -> abc, sonra çıkış
    InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setIn(inputStream); // Simüle edilmiş giriş
    System.setOut(new PrintStream(outContent)); // Çıktıyı yakala

    Task task = new Task(new Scanner(System.in), System.out);

    // Act
    task.algorithmsMenu();

  }*/

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




}
