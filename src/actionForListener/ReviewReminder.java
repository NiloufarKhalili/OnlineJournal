package actionForListener;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import beans.User;

import com.mysql.jdbc.Connection;

import database.Email;
import database.Form;

public class ReviewReminder implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String DB = "jdbc:mysql://stusql.dcs.shef.ac.uk/team107?user=team107&password=8b8ba518";
			try {
				conn = (Connection) DriverManager.getConnection(DB);
				// TODO send email to reviwer to download the article
				Form form =  new Form(conn);
				List<User> users = new ArrayList<User>();
				users = form.getUsersForReminderSchduler();
				Email email = new Email();
				for (User user : users) {
					try {
						email.sendReminderToReviewer(user.getEmail());
					} catch (AddressException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (MessagingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
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
