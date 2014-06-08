package beans;

public class Error {
	private String error;
	private int id;

	public Error() {

	}
	
	public Error(String error) {
		super();
		this.error = error;
	}

	public Error(int id,String error) {
		super();
		this.error = error;
		this.id = id;
	}


	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
