/**
 * @file User.java
 * @brief Represents user information and file storage operations.
 *
 * This class encapsulates user-related information, including user credentials 
 * and personal details. It supports file operations for persisting and retrieving 
 * user data using a `RandomAccessFile`.
 *
 * @version 1.0
 * @date 2024-12-24
 * @author User
 */

package com.abdul.fatma.hamza.sahan.task;

import java.io.*;

/**
 * @class User
 * @brief A class representing a user in the task management system.
 *
 * The `User` class contains essential user information such as ID, name, surname, 
 * email, and password. It also provides functionality for reading and writing user 
 * data to a binary file using `RandomAccessFile`.
 */
public class User {

    /** @brief Unique identifier for the user. */
    public int id;

    /** @brief The first name of the user. */
    public String name;

    /** @brief The surname of the user. */
    public String surname;

    /** @brief The email address of the user. */
    public String email;

    /** @brief The password of the user. */
    public String password;

    // --- Getters and Setters ---

    /**
     * @brief Retrieves the user ID.
     * @return The unique identifier of the user.
     */
    public int getId() {
        return id;
    }

    /**
     * @brief Sets the user ID.
     * @param id The unique identifier for the user.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @brief Retrieves the user's first name.
     * @return The first name of the user.
     */
    public String getName() {
        return name;
    }

    /**
     * @brief Sets the user's first name.
     * @param name The first name of the user.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @brief Retrieves the user's surname.
     * @return The surname of the user.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * @brief Sets the user's surname.
     * @param surname The surname of the user.
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * @brief Retrieves the user's email address.
     * @return The email address of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * @brief Sets the user's email address.
     * @param email The email address of the user.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @brief Retrieves the user's password.
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * @brief Sets the user's password.
     * @param password The password of the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    // --- File Operations ---

    /**
     * @brief Writes user data to a file.
     *
     * Persists user details into a binary file using `RandomAccessFile`.
     *
     * @param raf The `RandomAccessFile` object for writing data.
     * @throws IOException If an I/O error occurs during writing.
     */
    public void writeToFile(RandomAccessFile raf) throws IOException {
        raf.writeInt(id);
        raf.writeUTF(name);
        raf.writeUTF(surname);
        raf.writeUTF(email);
        raf.writeUTF(password);
    }

    /**
     * @brief Reads user data from a file.
     *
     * Populates user attributes by reading data from a binary file using `RandomAccessFile`.
     *
     * @param raf The `RandomAccessFile` object for reading data.
     * @throws IOException If an I/O error occurs during reading.
     */
    public void readFromFile(RandomAccessFile raf) throws IOException {
        this.id = raf.readInt();
        this.name = raf.readUTF();
        this.surname = raf.readUTF();
        this.email = raf.readUTF();
        this.password = raf.readUTF();
    }
}
