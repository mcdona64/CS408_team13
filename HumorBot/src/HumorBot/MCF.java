package HumorBot;
//Imports
import HumorBotGUI.*;
import java.util.*;
//Here is the class
public class MCF {
	//Here is our hand
	private ArrayList<WhiteCard> hand = new ArrayList<WhiteCard>();
	//
	public static void main(String[] args) {
		Web w = new Web("http://www.pretendyoure.xyz/zy/");
		w.grabWebpage();
	}
	
}
