package com.abdul.fatma.hamza.sahan.task;

public class taskApp {

    public static void main(String[] args) {
        String pathFileUsers = "users.bin"; // Kullanıcı verilerini saklayacak dosya

        // Task sınıfının bir örneğini oluşturuyoruz
        Task task = new Task();

        // mainMenu fonksiyonunu nesne üzerinden çağırıyoruz
        task.mainMenu(pathFileUsers);  // task.java'daki mainMenu fonksiyonunu çağırıyoruz
    }
}

