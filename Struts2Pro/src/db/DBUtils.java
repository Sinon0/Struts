package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {
	public static Connection getConnection() throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		String url="jdbc:mysql://localhost:3306/struts";
		String user="root";
		String password="wy25";
		Connection conn=DriverManager.getConnection(url, user, password);
		return conn;
	}
}
