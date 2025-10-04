package com.jdbc.basics;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Program01 {

	public static void main(String[] args) throws Exception {

		String url = "jdbc:mysql://localhost:3306/practice";
		String uname = "root";
		String pass = "password";


		// Class.forname("com.mysql.jdbc.Driver"); -> from jdbc 4.0 it is not mandatory to write 
		//-> JDBC Jar is still mandatory in classpath
		Connection con = DriverManager.getConnection(url,uname,pass);
	//	Statement st = con.createStatement();
        Scanner sc = new Scanner(System.in);

		while (true) {
            System.out.println("\n--- USER CRUD MENU ---");
            System.out.println("1. Insert User");
            System.out.println("2. Read Users");
            System.out.println("3. Update Password");
            System.out.println("4. Delete User");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); 

            switch (choice) {
                case 1: // CREATE
                    System.out.print("Enter username: ");
                    String username = sc.nextLine();
                    System.out.print("Enter password: ");
                    String password = sc.nextLine();

                    String insert = "INSERT INTO users(username, password) VALUES(?, ?)";
                    try (PreparedStatement pst = con.prepareStatement(insert)) {
                        pst.setString(1, username);
                        pst.setString(2, password);
                        pst.executeUpdate();
                        System.out.println("User inserted successfully!");
                    }
                    break;

                case 2: // READ
                    String select = "SELECT * FROM users";
                    try (Statement st = con.createStatement();
                         ResultSet rs = st.executeQuery(select)) {
                        System.out.println("UID | Username | Password");
                        while (rs.next()) {
                            System.out.println(rs.getInt("uid") + " | " +
                                               rs.getString("username") + " | " +
                                               rs.getString("password"));
                        }
                    }
                    break;

                case 3: // UPDATE
                    System.out.print("Enter user UID to update: ");
                    int uid = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter new password: ");
                    String newPassword = sc.nextLine();

                    String update = "UPDATE users SET password=? WHERE uid=?";
                    try (PreparedStatement pst = con.prepareStatement(update)) {
                        pst.setString(1, newPassword);
                        pst.setInt(2, uid);
                        int rows = pst.executeUpdate();
                        if (rows > 0) System.out.println("Password updated!");
                        else System.out.println("User not found.");
                    }
                    break;

                case 4: // DELETE
                    System.out.print("Enter user UID to delete: ");
                    int delId = sc.nextInt();

                    String delete = "DELETE FROM users WHERE uid=?";
                    try (PreparedStatement pst = con.prepareStatement(delete)) {
                        pst.setInt(1, delId);
                        int rows = pst.executeUpdate();
                        if (rows > 0) System.out.println("User deleted!");
                        else System.out.println("User not found.");
                    }
                    break;


                case 5: // EXIT
                    System.out.println("Exiting...");
                    con.close();
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

	}


