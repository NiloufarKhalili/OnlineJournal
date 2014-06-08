package actionForListener;

import java.sql.DriverManager;
import java.sql.SQLException;
import com.mysql.jdbc.Connection;

import database.Form;

public class SubmitForm implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String DB = "jdbc:mysql://stusql.dcs.shef.ac.uk/team107?user=team107&password=8b8ba518";
			try {
				conn = (Connection) DriverManager.getConnection(DB);
				Form form = new Form(conn);
				form.updateStatusForScheduler();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
	}
}
