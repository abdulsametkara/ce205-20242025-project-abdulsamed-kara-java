/**

 @file TaskTest.java
 @brief This file contains the test cases for the Task class.
 @details This file includes test methods to validate the functionality of the Task class. It uses JUnit for unit testing.
 */
package com.abdul.fatma.hamza.sahan.task;

import org.junit.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.junit.Assert.*;
import java.util.Scanner;


public class TaskTest {

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  private final InputStream originalIn = System.in;

  private String taskFile = "tasksTest.bin";

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
    createTestTaskFile();
  }

  @After
  public void tearDown() throws Exception {
    System.setOut(originalOut);
    System.setIn(originalIn);
    deleteFile(taskFile);
  }

  private void deleteFile(String filePath) throws IOException {
    Files.deleteIfExists(Paths.get(filePath));
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
    Task task = new Task(); // 'Task' sınıfını doğru bir şekilde kullanın

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
  public void testEnterToContinue() throws Exception {
    // Arrange: Kullanıcı girişini simüle etmek için ByteArrayInputStream kullanıyoruz
    String simulatedUserInput = "\n"; // Kullanıcıdan 'Enter' tuşuna basma simülasyonu
    System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));

    // Act: enterToContinue metodunu çağır
    Task.enterToContinue(); // 'Task' sınıfındaki fonksiyonu çağırıyoruz

    // Assert: Konsol çıktısının doğru olduğuna dair kontrol
    String output = outContent.toString();
    assertTrue(output.contains("Press Enter to continue..."));
  }
  @Test
  public void testGetInputValid() {
    // Arrange: Geçerli giriş simüle edilmiştir
    String simulatedUserInput = "5\n"; // Kullanıcıdan 5 girişi
    System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));

    Scanner scanner = new Scanner(System.in);
    Task task = new Task();

    // Act: getInput metodunu çağır
    int result = task.getInput(scanner);

    // Assert: Sonucun doğru olup olmadığını kontrol et
    assertEquals(5, result); // Geçerli girişte 5 dönecek
  }

  @Test
  public void testHandleInputError() {
    // Arrange: Task sınıfını örnek olarak oluştur
    Task task = new Task();

    // Act: handleInputError metodunu çağır
    task.handleInputError();

    // Assert: Konsol çıktısının doğru olduğuna dair kontrol et
    String output = outContent.toString();
    assertTrue(output.contains("Invalid input. Please enter a number.")); // Mesajın çıktıda bulunup bulunmadığını kontrol et
  }
  @Test
  public void testPrintMainMenu() {
    // Arrange: Task sınıfını örnek olarak oluştur
    Task task = new Task();

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
    Task task = new Task();

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
    Task task = new Task();

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
    Task task = new Task();

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
    Task task = new Task();

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
    Task task = new Task();

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
  public void testMainMenuExitChoice() {
    // Arrange: Çıkış seçeneğini almak için simülasyon
    String simulatedUserInput = "3\n"; // Çıkış
    System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));

    Task task = new Task();
    String pathFileUsers = "usersTest.bin"; // Kendi dosya yolunu kullanabilirsiniz

    // Act: mainMenu metodunu çağır
    task.mainMenu(pathFileUsers);

    // Assert: Çıkış mesajı ve programın sonlandırılması
    String output = outContent.toString();
    assertTrue(output.contains("Exit Program"));
  }

}
