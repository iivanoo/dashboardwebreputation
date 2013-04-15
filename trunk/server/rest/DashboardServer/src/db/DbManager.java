package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbManager {

	private static String dbUrl = "jdbc:mysql:///dashboard";
	private static String dbUser = "root";
	private static String dbPassword = "";

	public static Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("org.gjt.mm.mysql.Driver");
			connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
		} catch (ClassNotFoundException e) {
			System.out.println("Error - JDBC Driver not Found: " + e.getMessage());
		} catch (SQLException e) {
			System.out.println("Error - " + e.getMessage());
		}
		return connection;
	}
}
