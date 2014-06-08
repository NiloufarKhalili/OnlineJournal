package beans;

public class Keyword {

	private int id;
	private String word;
	
	public Keyword() {
		
	}
	

	public Keyword(int id, String word) {
		super();
		this.id = id;
		this.word = word;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

}
