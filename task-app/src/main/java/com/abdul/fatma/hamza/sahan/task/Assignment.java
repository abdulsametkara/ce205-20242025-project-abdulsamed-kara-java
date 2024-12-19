package com.abdul.fatma.hamza.sahan.task;

import java.io.*;
import java.util.Scanner;
import java.util.PriorityQueue;

public class Assignment implements Comparable<Assignment>, Serializable {
    private static final long serialVersionUID = 1L; // Serialization sürümü

    private String name;
    private int day, month, year;

    public Assignment(String name, int day, int month, int year) {
        this.name = name;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    // Getter ve Setter Metodları
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public int compareTo(Assignment other) {
        if (this.year != other.year) return this.year - other.year;
        if (this.month != other.month) return this.month - other.month;
        return this.day - other.day;
    }

    // Dosyaya yazma fonksiyonu
    public void writeToFile(String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName, true))) {
            oos.writeObject(this);
        } catch (IOException e) {
            System.out.println("Error: Could not save deadline to file. " + e.getMessage());
        }
    }

    // assign_deadline fonksiyonu
    public static void assign_deadline(PriorityQueue<Assignment> deadlineHeap) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Task Name: ");
        String taskName = scanner.nextLine();

        System.out.print("Enter Deadline (day month year): ");
        int day, month, year;
        try {
            day = scanner.nextInt();
            month = scanner.nextInt();
            year = scanner.nextInt();

            if (day < 1 || day > 31 || month < 1 || month > 12 || year < 1900) {
                System.out.println("Invalid date! Please enter a valid date.");
                return;
            }
        } catch (Exception e) {
            System.out.println("Invalid input! Please try again.");
            scanner.nextLine(); // Temizleme
            return;
        }

        // Yeni bir Assignment oluştur ve MinHeap'e ekle
        Assignment assignment = new Assignment(taskName, day, month, year);
        deadlineHeap.add(assignment); // PriorityQueue kullanıyoruz
        assignment.writeToFile("deadlines.bin");

        System.out.println("Deadline assigned and saved successfully!");
    }
}
