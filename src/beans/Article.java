package beans;
import java.util.ArrayList;
import java.util.Date;


public class Article {
	private int id;
	private String title;
	private String abst;
	private String userId;
	private int review_count;
	
	private String pdfPath;
	private String volume;
	private String edition;
	private String reviewed;
	private String status;
	private Date createdAt;
	private User mainUser;//this is implemented to get main user details when we set the article object.
	private ArrayList<Keyword> keywords;
	private ArrayList<User> users;
	private ArrayList<ReviewForm> forms;
	
	public Article(String title,String abst,String userId,String pdfPath) {
		this.title = title;
		this.abst = abst;
		this.userId = userId;
		this.pdfPath = pdfPath;
	}
	public Article() {
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAbst() {
		return abst;
	}
	public void setAbst(String abst) {
		this.abst = abst;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getReview_count() {
		return review_count;
	}
	public void setReview_count(int review_count) {
		this.review_count = review_count;
	}
	public String getPdfPath() {
		return pdfPath;
	}
	public void setPdfPath(String pdfPath) {
		this.pdfPath = pdfPath;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getEdition() {
		return edition;
	}
	public void setEdition(String edition) {
		this.edition = edition;
	}
	public String getReviewed() {
		return reviewed;
	}
	public void setReviewed(String reviewed) {
		this.reviewed = reviewed;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public ArrayList<User> getUsers() {
		return users;
	}
	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}
	public ArrayList<ReviewForm> getForms() {
		return forms;
	}
	public void setForms(ArrayList<ReviewForm> forms) {
		this.forms = forms;
	}
	public User getMainUser() {
		return mainUser;
	}
	public void setMainUser(User mainUser) {
		this.mainUser = mainUser;
	}
	public ArrayList<Keyword> getKeywords() {
		return keywords;
	}
	public void setKeywords(ArrayList<Keyword> keywords) {
		this.keywords = keywords;
	}
	
	
	
}

