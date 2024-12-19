package com.abdul.fatma.hamza.sahan.task;

import org.junit.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.PriorityQueue;
import static org.junit.Assert.*;

public class taskAppTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    private String userFile = "usersTest.bin";
    private String taskFile = "tasksTest.bin";
    private String deadlineFile = "deadlinesTest.bin";

    @Before
    public void setUp() throws Exception {
        System.setOut(new PrintStream(outContent));
        System.setIn(originalIn);
        createTestUserFile();
        createTestTaskFile();
        createTestDeadlineFile();
    }

    @After
    public void tearDown() throws Exception {
        System.setOut(originalOut);
        System.setIn(originalIn);
        deleteFile(userFile);
        deleteFile(taskFile);
        deleteFile(deadlineFile);
    }

    private void deleteFile(String filePath) throws IOException {
        Files.deleteIfExists(Paths.get(filePath));
    }

    private void createTestUserFile() throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(userFile, "rw")) {
            User user1 = new User();
            user1.setId(1);
            user1.setName("John");
            user1.setSurname("Doe");
            user1.setEmail("john@example.com");
            user1.setPassword("password123");
            user1.writeToFile(raf);

            User user2 = new User();
            user2.setId(2);
            user2.setName("Jane");
            user2.setSurname("Doe");
            user2.setEmail("jane@example.com");
            user2.setPassword("password456");
            user2.writeToFile(raf);
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

    private void createTestDeadlineFile() throws IOException {
        PriorityQueue<Assignment> deadlineHeap = new PriorityQueue<>();
        deadlineHeap.add(new Assignment("Assignment 1", 15, 12, 2024));
        deadlineHeap.add(new Assignment("Assignment 2", 1, 1, 2025));

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(deadlineFile))) {
            while (!deadlineHeap.isEmpty()) {
                oos.writeObject(deadlineHeap.poll());
            }
        }
    }

    @Test
    public void testTaskAppMainMenu() throws IOException {
        // Arrange: Simulate user input to navigate the main menu and exit
        String userInput = "4\n"; // Exit the main menu
        ByteArrayInputStream inContent = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(inContent);

        // Act: Call the main method
        taskApp.main(new String[]{});

        // Assert: Verify console output
        String output = outContent.toString();
        assertTrue(output.contains("MAIN MENU"));
        assertTrue(output.contains("Exit Program"));
    }

    @Test
    public void testAssignDeadline() throws IOException {
        // Arrange: PriorityQueue for deadlines
        PriorityQueue<Assignment> deadlineHeap = new PriorityQueue<>();
        Assignment.assign_deadline(deadlineHeap);

        // Act: Check if a deadline was added
        assertFalse(deadlineHeap.isEmpty());

        // Assert: Verify top element in the heap
        Assignment top = deadlineHeap.peek();
        assertNotNull(top);
        assertEquals("Assignment 1", top.getName()); // Example assertion for the test
    }
}