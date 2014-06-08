package database;

/**
 * 
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import beans.User;

public class Account {
	private Connection conn;

	public Account(Connection conn) {
		this.conn = conn;
	}

	public boolean login(String email, String password) throws SQLException {
		String sql = "select count(*) as count from users where email=? and password=?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, email);
		stmt.setString(2, password);
		ResultSet rs = stmt.executeQuery();

		int count = 0;
		if (rs.next()) {
			count = rs.getInt("count");
			//count+=1;
		}
		rs.close();
		if (count == 0) {
			return false;
		} else {
			// Statement stst = conn.createStatement();
			// stst.execute("UPDATE users SET last_sign_in_at = CURRENT_TIMESTAMP WHERE first_name= "+);
			return true;
		}
	}

	public void create(String email, String password, String firstname,
			String lastname) throws SQLException {
		String sql = "insert into users(email, password,first_name,last_name) values(?, ?, ?, ?) ";

		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, email);
		stmt.setString(2, password);
		stmt.setString(3, firstname);
		stmt.setString(4, lastname);
		stmt.executeUpdate();
		stmt.close();

	}

	public ResultSet getRecords() throws SQLException {
		Statement stst = conn.createStatement();
		ResultSet resultSet = stst.executeQuery("select * from users");
		return resultSet;

	}

	/**
	 * This method is implemented to return the user's role.
	 * 
	 * @param email
	 * @return
	 * @throws SQLException
	 */

	public String getUserRole(String email) throws SQLException {
		String sql = "select * from users where email=?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, email);
		ResultSet rs = stmt.executeQuery();
		String role = "";
		if (rs.next()) {
			role = (rs.getString("role"));
			return role;
		}
		return role;
	}

	public User getUserByEmail(String email) throws SQLException {
		User user = new User();
		String sql = "select * from users where email=?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, email);
		ResultSet rs = stmt.executeQuery();
		String role = "";
		if (rs.next()) {
			role = (rs.getString("role"));
			if (role.length() > 0) {
				user.setEmail(email);
				user.setFirstname(rs.getString("first_name"));
				user.setLastname(rs.getString("last_name"));
				user.setRole(rs.getString("role"));
				user.setId(rs.getInt("id"));
			}
		}
		return user;
	}

	public int getUserId(String email) throws SQLException {

		String sql = "select * from users where email=?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, email);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			int id = Integer.valueOf(rs.getString("id"));
			return id;
		}
		return 0;

	}

	public void changeRole(String role, int id) throws SQLException {
		String sql = "update users SET role = '" + role + "' where id = " + id;
		Statement stst = conn.createStatement();
		stst.executeUpdate(sql);
		if(stst != null) stst.close();

		// stst.addBatch("update bankAccount SET checkingBalance = checkingBalance - "+big+" where id = "+id);
	}

	public User getUserById(int userId) throws SQLException {
		User user = new User();
		Statement stst = conn.createStatement();
		ResultSet rs = stst.executeQuery("select * from users where id = "
				+ userId);
		while (rs.next()) {
			user.setId(rs.getInt("id"));
			user.setFirstname(rs.getString("first_name"));
			user.setLastname(rs.getString("last_name"));
			user.setEmail(rs.getString("email"));
			user.setRole(rs.getString("role"));
			user.setAffiliation(rs.getString("affiliation"));
		}
		rs.close();
		return user;
	}

	public List<User> getEditorList() throws SQLException {
		List<User> userList = new ArrayList<User>();
		Statement stst = conn.createStatement();
		ResultSet rs = stst
				.executeQuery("select * from users where role = 'editor'");
		while (rs.next()) {
			User user = new User();
			user.setId(rs.getInt("id"));
			user.setFirstname(rs.getString("first_name"));
			user.setLastname(rs.getString("last_name"));
			user.setEmail(rs.getString("email"));
			userList.add(user);
		}
		return userList;
	}

	public boolean exists(String email) throws SQLException {
		String sql = "select count(*) as count from users where email=? ";

		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, email);
		ResultSet rs = stmt.executeQuery();

		int count = 0;
		if (rs.next()) {
			count = rs.getInt("count");
			System.out.println(count);
		}
		rs.close();
		if (count == 0) {
			return false;
		} else {

			return true;
		}
	}

	public ArrayList<User> getEditors() throws SQLException {
		Statement stst = conn.createStatement();
		ResultSet rs = stst
				.executeQuery("select * from users where role = 'editor'");

		return getUsersFromResultSet(rs);
	}

	public ArrayList<User> getUsersFromResultSet(ResultSet rs)
			throws SQLException {
		ArrayList<User> users = new ArrayList<User>();
		while (rs.next()) {
			User user = new User();
			user.setId(rs.getInt("id"));
			user.setFirstname(rs.getString("first_name"));
			user.setLastname(rs.getString("last_name"));
			user.setEmail(rs.getString("email"));
			user.setRole(rs.getString("role"));
			user.setAffiliation(rs.getString("affiliation"));
			users.add(user);

		}
		rs.close();
		return users;
	}
//	public static void main(String[] args) throws ClassNotFoundException, SQLException {
//		Class.forName("com.mysql.jdbc.Driver");
//		String DB = "jdbc:mysql://stusql.dcs.shef.ac.uk/team107?user=team107&password=8b8ba518";
//		Connection con = DriverManager.getConnection(DB);
//		ArticleTable article = new ArticleTable(con);
//		System.out.println(new Account(con).exists("iman.khnadiv@gmail.com"));
//		System.out.println(new Account(con).login("iman.khadiv@gmail.com", "letmmein"));
//	}


}