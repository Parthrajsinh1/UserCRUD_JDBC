# README.md

> **JDBC Console Application — User CRUD (Create, Read, Update, Delete)**

---

## Table of Contents

1. [Project Overview](#project-overview)
2. [Prerequisites](#prerequisites)
3. [Database & Table Setup](#database--table-setup)
4. [Configuration](#configuration)
5. [Compile & Run (Step by Step)](#compile--run-step-by-step)
6. [Detailed Code Walkthrough](#detailed-code-walkthrough)
7. [Security & Quality Improvements (Recommendations)](#security--quality-improvements-recommendations)
8. [Practical Extensions / Architecture Suggestions](#practical-extensions--architecture-suggestions)
9. [Troubleshooting — Common Issues & Fixes](#troubleshooting--common-issues--fixes)
10. [Testing, CI & Docker](#testing-ci--docker)
11. [Further Notes & Resources](#further-notes--resources)
12. [Author / License](#author--license)

---

## Project Overview

This project is a simple **Java console application** that performs basic CRUD operations on a MySQL `users` table using JDBC. The application provides a text-based menu with the following features:

* **1. Insert User** — Add a new user (username, password)
* **2. Read Users** — Display all users (uid, username, password)
* **3. Update Password** — Update a user’s password by `uid`
* **4. Delete User** — Remove a user by `uid`
* **5. Exit** — Close the application

> Note: `PreparedStatement` is used for Insert/Update/Delete to prevent SQL Injection vulnerabilities.

---

## Prerequisites

* **Java JDK 8+** (recommended: JDK 11 or newer)
* **MySQL Server** (local or remote)
* **MySQL JDBC Connector JAR** (e.g., `mysql-connector-java-8.x.x.jar`) must be in the classpath
* **IDE** (IntelliJ, Eclipse) or command line tools

Optional:

* Maven/Gradle for dependency management
* Docker (to run a local MySQL instance)

---

## Database & Table Setup

Run the following SQL commands to prepare the environment:

```sql
CREATE DATABASE practice;
USE practice;

CREATE TABLE users (
    uid INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL
);
```

---

## Configuration

In `Program01.java`, update the database connection details:

```java
String url = "jdbc:mysql://localhost:3306/practice";
String uname = "root";
String pass = "password";
```

Replace `root` and `password` with your MySQL username and password.

---

## Compile & Run (Step by Step)

1. Save the file as `Program01.java` inside `com/jdbc/basics/`.
2. Compile:

   ```bash
   javac -cp .;mysql-connector-java-8.x.x.jar com/jdbc/basics/Program01.java
   ```
3. Run:

   ```bash
   java -cp .;mysql-connector-java-8.x.x.jar com.jdbc.basics.Program01
   ```
4. Follow the console menu to perform CRUD operations.

---

## Detailed Code Walkthrough

### 1. Connection Setup

```java
Connection con = DriverManager.getConnection(url, uname, pass);
```

* Connects to MySQL using JDBC.
* Driver class registration (`Class.forName`) is no longer mandatory in JDBC 4.0+.

### 2. Insert Operation

```java
String insert = "INSERT INTO users(username, password) VALUES(?, ?)";
PreparedStatement pst = con.prepareStatement(insert);
pst.setString(1, username);
pst.setString(2, password);
```

* Uses `PreparedStatement` to insert safely.

### 3. Read Operation

```java
ResultSet rs = st.executeQuery("SELECT * FROM users");
```

* Iterates through result set and prints all users.

### 4. Update Operation

```java
String update = "UPDATE users SET password=? WHERE uid=?";
```

* Updates user password based on `uid`.

### 5. Delete Operation

```java
String delete = "DELETE FROM users WHERE uid=?";
```

* Deletes user based on `uid`.

---

## Security & Quality Improvements (Recommendations)

* **Password Storage**: Never store plain text passwords. Use hashing (e.g., BCrypt, SHA-256 + salt).
* **Error Handling**: Replace `throws Exception` with proper `try-catch` blocks.
* **Resource Management**: Use `try-with-resources` for `Connection`.
* **Input Validation**: Ensure inputs (especially username/password) are sanitized.
* **Logging**: Replace `System.out.println` with a logging framework (SLF4J, Log4j).

---

## Practical Extensions / Architecture Suggestions

* Implement **DAO Layer** (Data Access Object) to separate logic from database operations.
* Add **User Authentication System** with hashed passwords.
* Introduce a **GUI** (Swing/JavaFX) or a **REST API** (Spring Boot).
* Use **Maven/Gradle** for dependency management.
* Add **Unit Tests** for each CRUD method.

---

## Troubleshooting — Common Issues & Fixes

| Issue                                  | Cause                | Fix                                         |
| -------------------------------------- | -------------------- | ------------------------------------------- |
| `java.sql.SQLException: Access denied` | Wrong DB credentials | Check `uname` and `pass`                    |
| `No suitable driver found`             | JDBC JAR missing     | Add `mysql-connector-java.jar` to classpath |
| `Unknown database 'practice'`          | DB not created       | Run `CREATE DATABASE practice;`             |
| Program stuck at input                 | Scanner issue        | Ensure you consume `sc.nextLine()` properly |

---

## Testing, CI & Docker

* **Testing**: Use JUnit + H2 in-memory DB for testing CRUD logic.
* **CI/CD**: Integrate with GitHub Actions to run tests automatically.
* **Docker**: Use a MySQL Docker container for consistent environment.

Example `docker-compose.yml`:

```yaml
version: '3.8'
services:
  db:
    image: mysql:8.0
    container_name: mysql_db
    restart: always
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: practice
    ports:
      - "3306:3306"
```

---

## Further Notes & Resources

* [JDBC Official Documentation](https://docs.oracle.com/javase/tutorial/jdbc/)
* [MySQL Connector/J Documentation](https://dev.mysql.com/doc/connector-j/8.0/en/)
* [Effective Java (Book by Joshua Bloch)](https://www.oreilly.com/library/view/effective-java-3rd/9780134686097/)

---

## Author / License

* Author: Parthrajsinh Gol
* License: Open Source (MIT or as preferred)
