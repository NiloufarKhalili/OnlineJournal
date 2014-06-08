package controller;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class MyDB {
	
	private String userName = "root";
	private String password = "";
	private String url = "jdbc:mysql://127.0.0.1:3306/test";
	private Connection connection;
	
	public MyDB()
	{
		Statement stm = null;
		try {
			Class.forName ("com.mysql.jdbc.Driver").newInstance ();
			connection = (Connection) DriverManager.getConnection(url, userName, password);
			if(connection != null)
			System.out.println("Database connection established");
			//create table
			stm = (Statement) connection.createStatement();
			String sqlString = "CREATE TABLE IF NOT EXISTS new_table ("
					+ "id INT NOT NULL AUTO_INCREMENT,"
					+ "name VARCHAR(45) NULL," + "email VARCHAR(45) NULL,"
					+ "PRIMARY KEY (id),"
					+ "UNIQUE INDEX id_UNIQUE (id ASC));";
			stm.executeUpdate(sqlString);
			stm.close();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void insert(String name, String email) throws SQLException
	{
		Statement smtStatement = (Statement) connection.createStatement();
		String sqlString ="INSERT INTO new_table (name,email) values ('"+name+"','"+email+"')";
		System.out.println(sqlString);
		smtStatement.execute(sqlString);
		smtStatement.close();
	}
	
	public void selectall() throws SQLException 
	{
		Statement s = (Statement) connection.createStatement();
		s.executeQuery("SELECT id, name, email FROM new_table");
		ResultSet rs = s.getResultSet();
		int count = 0;
		while (rs.next()) {
			int idVal = rs.getInt("id");
			String nameVal = rs.getString("name");
			String emailVal = rs.getString("email");
			System.out.println("id = " + idVal + ", name = " + nameVal
					+ ", category = " + emailVal);
			++count;
		}
		rs.close();
		s.close();
		System.out.println(count + " rows were retrieved");
	}
	public void droptable(String tablename) throws SQLException
	{
		Statement s = (Statement) connection.createStatement();
		s.executeUpdate("DROP TABLE IF EXISTS "+tablename);
		s.close();
	}
	
	public static void main(String[] args) throws SQLException
	{
		MyDB myDB = new MyDB();
		myDB.insert("njy", "njy@123.com");
		myDB.selectall();
		myDB.droptable("new_table");
		myDB.selectall();
	}

}
