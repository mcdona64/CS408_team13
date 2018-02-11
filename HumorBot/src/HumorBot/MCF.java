package HumorBot;
//Imports
import HumorBotGUI.*;
import java.util.*;
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
	/**
	 * This function will return the hand that is given to the user.
	 */
	public void getHand(){
		
	}
	
	//AI and important Functions
	/**
	 * Deep stuff, this will come way later down the road
	 */
	public void makeDecision(){
		
	}
	
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
	
	private void initializeWebCrawler(){
		this.crawler = new Web(/*this,*/ "http://www.pretendyoure.xyz/zy/");
		this.crawler.grabWebpage();
	}
	
}
