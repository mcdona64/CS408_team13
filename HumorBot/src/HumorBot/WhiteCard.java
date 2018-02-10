package HumorBot;

public class WhiteCard {
	private String answer;
	private long weight;
	private long average;
	
	public WhiteCard(String answer, long weight, long average){
		this.answer = answer;
		this.weight = weight;
		this.average = average;
	}
	
	public WhiteCard(String answer, long weight){
		this.answer = answer;
		this.weight = weight;
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
	
	public void setAverage(long average){
		this.average = average;
	}
}
