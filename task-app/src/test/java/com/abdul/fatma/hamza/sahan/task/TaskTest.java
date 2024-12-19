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

}
