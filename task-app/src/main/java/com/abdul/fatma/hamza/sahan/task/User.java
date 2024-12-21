package com.abdul.fatma.hamza.sahan.task;
import java.io.*;


public class User {
    public int id;
    public String name;
    public String surname;
    public String email;
    public String password;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Kullanıcıyı dosyaya yazma
    public void writeToFile(RandomAccessFile raf) throws IOException {
        raf.writeInt(id);
        raf.writeUTF(name);
        raf.writeUTF(surname);
        raf.writeUTF(email);
        raf.writeUTF(password);
    }

    // Kullanıcıyı dosyadan okuma
    public void readFromFile(RandomAccessFile raf) throws IOException {
        this.id = raf.readInt();
        this.name = raf.readUTF();
        this.surname = raf.readUTF();
        this.email = raf.readUTF();
        this.password = raf.readUTF();
    }




}