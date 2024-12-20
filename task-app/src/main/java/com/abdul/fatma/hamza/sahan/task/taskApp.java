package com.abdul.fatma.hamza.sahan.task;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class taskApp {

    public static void main(String[] args) {
        String pathFileUsers = "users.bin"; // Kullanıcı verilerini saklayacak dosya
        Scanner inputScanner = new Scanner(System.in);

        // Task sınıfının bir örneğini oluşturuyoruz
        Task task = new Task(inputScanner, System.out);

        // mainMenu fonksiyonunu nesne üzerinden çağırıyoruz
        task.mainMenu(pathFileUsers);  // task.java'daki mainMenu fonksiyonunu çağırıyoruz
    }
}

