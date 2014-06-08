package database;

import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class Email {
	String recipient = "";
	String subject = "";
	String body = "";
    
	public Email(String recipient, String subject, String body) {
		this.recipient = recipient;
		this.subject = subject;
		this.body = body;
	}
    
	public Email() {
        
	}
	
    
	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void sendEmail() throws AddressException, MessagingException {
        
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "true");
		props.put("mail.smtp.port", 587);
		props.put("mail.smtp.socketFactory.port", 587);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.transport.protocol", "smtp");
		Session mailSession = null;
        
		mailSession = Session.getInstance(props,
                                          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                                                  "stucat007@gmail.com",
                                                  "theuniversityofsheffield");
            }
        });
        
		Transport transport = mailSession.getTransport();
        
		MimeMessage message = new MimeMessage(mailSession);
        
		message.setSubject(subject);
		message.setFrom(new InternetAddress("stucat007@gmail.com"));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(
                                                                           recipient));
		message.setContent(body, "text/html");
		transport.connect();
        
		transport.sendMessage(message,
                              message.getRecipients(Message.RecipientType.TO));
		transport.close();
        
	}
    
	public String generatePassword() {
		String password = "";
		for (int i = 0; i < 5; i++) {
			password += String.valueOf((int) (Math.random() * 10));
		}
		return password;
	}
    
	public void sendEmailForUploadArticle(String recipient, String name, String password)
    throws AddressException, MessagingException {
		this.recipient = recipient;
		this.subject = "Registration Successfull";
		this.body = "Dear "
        + name
        + ",<br>Thank you for uploading your article.<br>Your Password is: "
        + password;
		sendEmail();
	}
    
	public void sendEmailForUploadArticle(String recipient, String name)
    throws AddressException, MessagingException {
		this.recipient = recipient;
		this.subject = "Registration Successfull";
		this.body = "Dear "
        + name
        + ",<br/><br/>Thank you for uploading your article ";
		sendEmail();
	}
	
	
	public void sendEmailForUpdateForm(String recipient, String name)
		    throws AddressException, MessagingException {
				this.recipient = recipient;
				this.subject = "Review Successfully Updated";
				this.body = "Dear "
		        + name
		        + ",\n\nThank you for updating your review ";
				sendEmail();
			}
	
	public void sendSecretToEditor(String recipient, String name, String message)
		    throws AddressException, MessagingException {
				this.recipient = recipient;
				this.subject = "Secret Message";
				this.body = "Dear editor"
		        + "\r\n Here is a secret message from "+name+" and the content is \r\n"+message;
				sendEmail();
			}
	
	public void sendReminderToReviewer(String recipient) throws AddressException, MessagingException {
			this.recipient = recipient;
			this.subject = "Reviewer reminder";
			this.body = "Dear Reviewer"
	        + "<p/> Here is a remider from the system <p/> As a reviewer, you should start your review now. <p/> Thanks";
			sendEmail();
	}
}
