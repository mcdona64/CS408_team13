package HumorBot;
//Imports
import HumorBotGUI.*;
import java.util.*;
import java.lang.*;
/**
 * @author pxeros
 * This class is the main control flow for our AI
 */
public class MCF {
	//Here is our hand
	private ArrayList<WhiteCard> hand = new ArrayList<WhiteCard>();
	//This is the username
	private String username;
	//This is the web interface we will be using
	private Web crawler;
	//This is the number of point we have (or black Cards we have won)
	private int points;
	//This is the current blackCard on the table
	private BlackCard currentCard;
	
	//Constructors
	public MCF(String username){
		this.username = username;
	}
	
	public MCF(){
		this.username = "placeholder";
	}
	
	//getters
	public String getUserName(){
		return this.username;
	}
	
	//Setters
	public void setCurrentCard(BlackCard card){
		this.currentCard = card;
	}
	/**
	 * This function will return the hand that is given to the user.
	 */
	public void updateHand(){
		this.hand = this.crawler.getHand();
		for(int i = 0; i < this.hand.size(); i++){
			//TODO implement database stuff
			long weight = 0;
			long avWeight = 0;
			this.hand.get(i).setWeight(weight);
			this.hand.get(i).setAverage(avWeight);
		}
	}
	
	//AI and important Functions
	/**
	 * Deep stuff, this will come way later down the road
	 */
	public void makeDecision(){
		
	}
	
	//These are the hand assessors for each individual situation
	/**
	 * This is assessing the best hand on a normal round 
	 * @return the index of the best card,
	 */
	public int assessHandNormal(){
		long max = 0;
		int best_choice = 0;
		for(int i = 0; i < this.hand.size(); i++){
			WhiteCard curr = this.hand.get(i);
			if(curr.getWeight() > max){
				max = curr.getWeight();
				best_choice = i;
			}else if(curr.getWeight() == max){
				WhiteCard prevMax = this.hand.get(best_choice);
				best_choice = (prevMax.getAverageWeight() >= curr.getAverageWeight())? best_choice: i;
			}
		}
		return best_choice;
	}
	
	/**
	 * This is the function that will be used for determining what card wil be thrown away on a throwaway round
	 * @return
	 */
	public int assessHandThrowaway(){
		long min = this.hand.get(0).getAverageWeight();
		int best_choice = 0;
		for(int i = 1; i < this.hand.size(); i++){
			WhiteCard curr = this.hand.get(i);
			if(curr.getAverageWeight() < min){
				min = curr.getWeight();
				best_choice = i;
			}else if(curr.getAverageWeight() == min){
				WhiteCard prevMax = this.hand.get(best_choice);
				best_choice = (prevMax.getWeight() >= curr.getWeight())? best_choice: i;
			}
		}
		return best_choice;
	}
	
	public ArrayList<Integer> assessHandMultipleBlanks(int numBlanks){
		//TODO Implement
		return new ArrayList<Integer>();
	}
	
	//These are the functions that we need 
	private void initializeWebCrawler(){
		this.crawler = new Web("http://www.pretendyoure.xyz/zy/");
		this.crawler.grabWebpage();
		//TODO add getting to the webpage
	}
	
}
