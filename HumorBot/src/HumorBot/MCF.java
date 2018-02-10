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
	/**
	 * This is the constructor 
	 */
	public MCF(){}
	
	public void getHand(){
		
	}
	
	private void initializeWebCrawler(){
		this.crawler = new Web("http://www.pretendyoure.xyz/zy/");
		this.crawler.grabWebpage();
	}
	
}
