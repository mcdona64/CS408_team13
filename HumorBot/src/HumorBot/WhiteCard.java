package HumorBot;

public class WhiteCard {
	private String answer;
	private long weight;
	private long average;
	private String cardID;
	
	public WhiteCard(String answer, long weight, long average){
		this.answer = answer;
		this.weight = weight;
		this.average = average;

	}
	
	public WhiteCard(String answer, long weight){
		this.answer = answer;
		this.weight = weight;
		;
	}
	
	/**
	 * This constructor should only be used for a new/unrecognized card
	 * @param answer
	 */
	public WhiteCard(String answer){
		this.answer = answer;
		this.weight = 0;

	}
	
	public long getWeight(){
		return this.weight;
	}
	
	public long getAverageWeight(){
		return this.average;
	}
	
	public String getAnswer(){
		return this.answer;
	}

	public String getCardID() {return this.cardID; }

	public void setAverage(long average){
		this.average = average;
	}
	
	public void setWeight(long weight){
		this.weight = weight;
	}

	public boolean equals(WhiteCard card){
		return this.answer.equals(card.getAnswer());
	}

	public void setCardID(String cardID) {
		this.cardID = cardID;
	}
}
