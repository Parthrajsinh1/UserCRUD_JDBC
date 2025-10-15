# README.md

> **JDBC Console Application — User CRUD (Create, Read, Update, Delete)**

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

## Troubleshooting — Common Issues & Fixes

| Issue                                  | Cause                | Fix                                         |
| -------------------------------------- | -------------------- | ------------------------------------------- |
| `java.sql.SQLException: Access denied` | Wrong DB credentials | Check `uname` and `pass`                    |
| `No suitable driver found`             | JDBC JAR missing     | Add `mysql-connector-java.jar` to classpath |
| `Unknown database 'practice'`          | DB not created       | Run `CREATE DATABASE practice;`             |
| Program stuck at input                 | Scanner issue        | Ensure you consume `sc.nextLine()` properly |

---

 or as preferred)
