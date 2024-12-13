package com.abdul.fatma.hamza.sahan.task;

import java.io.*;

public class TaskInfo {
    private int id;
    private String name;
    private String description;
    private String category;
    private String dueDate;
    private int dependencyCount;
    private int[] dependencies = new int[10];  // Maksimum 10 bağımlılık

    // Getter ve Setter metodları
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDueDate() { return dueDate; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }

    public int getDependencyCount() { return dependencyCount; }
    public void setDependencyCount(int dependencyCount) { this.dependencyCount = dependencyCount; }

    public int[] getDependencies() { return dependencies; }
    public void setDependencies(int[] dependencies) { this.dependencies = dependencies; }

    public void writeToFile(RandomAccessFile raf) throws IOException {
        raf.writeInt(id);  // Görev ID'si
        raf.writeUTF(name);  // Görev adı
        raf.writeUTF(description);  // Görev açıklaması
        raf.writeUTF(category);  // Görev kategorisi
        raf.writeUTF(dueDate);  // Görev bitiş tarihi
        raf.writeInt(dependencyCount);  // Bağımlılık sayısı
        for (int i = 0; i < dependencyCount; i++) {
            raf.writeInt(dependencies[i]);  // Bağımlılıkların ID'leri
        }
    }


    public void readFromFile(RandomAccessFile raf) throws IOException {
        this.id = raf.readInt();  // Görev ID'si
        this.name = raf.readUTF();  // Görev adı
        this.description = raf.readUTF();  // Görev açıklaması
        this.category = raf.readUTF();  // Görev kategorisi
        this.dueDate = raf.readUTF();  // Görev bitiş tarihi
        this.dependencyCount = raf.readInt();  // Bağımlılık sayısı
        for (int i = 0; i < dependencyCount; i++) {
            this.dependencies[i] = raf.readInt();  // Bağımlılıkların ID'leri
        }
    }

}
