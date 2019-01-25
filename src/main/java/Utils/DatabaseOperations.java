package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class DatabaseOperations {

	private static String DBName="db-dev-dopayroll";
	private static String url="rds-mysql-dopayroll.cbkxr13csdtr.us-east-1.rds.amazonaws.com";
	private static String port="3306";
	private static String username="mysqladmin";
	private static String password="tuXe==aJa7hu";
		
		
		private static Connection connection = null;
		
		public static Connection connectDB(/*String url, String dbname, String port, String username, String password*/) throws SQLException, ClassNotFoundException {
			
			Class.forName("com.mysql.jdbc.Driver");

			String conn = "jdbc:mysql://"+url+":"+port+"/"+DBName;

			if(connection !=null && !connection.isClosed())
				connection.close();

			connection = DriverManager.getConnection(conn,username,password);
			return connection;
		}
	
		public static void closeDatabaseConnection() throws SQLException {
			if(connection !=null && !connection.isClosed())
				connection.close();

			connection = null;
		}
		public static ResultSet executeQuery(String queryString) throws SQLException {
			Statement stm = connection.createStatement();
			ResultSet rs = stm.executeQuery(queryString);
			return rs;
		}
		
		public static boolean execute(String queryString) throws SQLException {
			Statement stm = connection.createStatement();
			boolean result = stm.execute(queryString);
			return result;
		}
}
