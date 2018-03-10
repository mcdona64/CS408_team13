package HumorBot;

public class BlackCard {
	private int blanks;
	private String question;
	
	public BlackCard(int blanks, String question){
		this.blanks = blanks;
		this.question = question;
	}
	
	public int getBlanks(){
		return this.blanks;
	}
	
	public String getQuestion(){
		return this.question;
	}

	public void setBlanks(int blanks){
		this.blanks = blanks;
	}
}
