package HumorBot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Web {
	private MCF mcf;
	private String url;
	private static String filePath = "./web/";
	private String fileName = "index.html";
	private String fullFileName = filePath + fileName;
	private String blackCardString;
	private ArrayList<WhiteCard> whiteCardList = new ArrayList<WhiteCard>();
	private ArrayList<WhiteCard> gameHand = new ArrayList<WhiteCard>();
	
	private ArrayList<String> fileNames = new ArrayList<String>();
	
	public Web(String url){
		this.url = url;
		//fileNames.add("index.html");
	}
	
	public Web() {
		//fileNames.add("index.html");
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getBlackCard() {
		return this.blackCardString;
	}
	
	public ArrayList<WhiteCard> getHand(){
		return this.gameHand;
	}

	public void grabWebpage() {
		String webURL = this.url;
		URL u;
		InputStream is = null;
		BufferedReader br;
		String line;
		String newFileName = "tempIndex.html";
		
		File f = new File(fullFileName);
		
		try {
			u = new URL(webURL);
			URLConnection connection = u.openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
			connection.connect();
			br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			BufferedWriter writer = new BufferedWriter(new FileWriter(fullFileName));
			while((line = br.readLine()) != null) {
				writer.write(line);
				writer.newLine();
				//System.out.println(line);
				if(line.contains("<title>")) {
					newFileName = webURL;
					newFileName = newFileName.replace("http://", "");
					newFileName = newFileName.replace("https://", "");
					newFileName = newFileName.replaceAll("/", "_");
					newFileName += ".txt";
					fileNames.add(newFileName);
					newFileName = filePath + newFileName;
					
				}
			}
			writer.close();
			System.out.println("Downloaded webpage.");
			System.out.println(newFileName);
			File file2 = new File(newFileName);
			if(file2.exists()) {
				System.out.println("File exists");
			}
			if(!f.renameTo(file2)) {
				System.out.println("File was not renamed");
			} else {
				System.out.println("File was renamed");
			}
		} catch (MalformedURLException mue) {
			mue.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if(is != null) is.close();
			} catch (IOException ioe) {
				
			}
		}
		
	}
	
	/* Parses first webpage file downloaded
	 * Chooses a server (1, 2, 3)
	 * Able to be taken to game after through button
	 */
	
	public void getToGame(int server) {
		String fn = "index.html";
		for(String str : fileNames) {
			if(str.compareTo("www.pretendyoure.xyz_zy_.txt") == 0) {
				fn = filePath + str;
			}
		}
		
		File file = new File(fn);
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String buffer;
		String gameURL = "";
		try {
			while((buffer = br.readLine()) != null) {
				if(buffer.contains("pyx-" + server)) {
					//System.out.println(buffer);
					int beginIndex = buffer.indexOf("https:");
					int endIndex = buffer.indexOf("\">");
					//System.out.println(beginIndex);
					//System.out.println(endIndex);
					gameURL = buffer.substring(beginIndex, endIndex);
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(gameURL);
		this.setUrl(gameURL);
		this.grabWebpage();
	}
	
	/* Parses for Black Card
	 * Stores string in class variable and returns string
	 */
	public String parseBlackCards(String fn) {
		File f = new File(filePath + fn);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(f));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String buffer;
		String blackCard = "";
		try {
			while((buffer = br.readLine()) != null) {
				if(buffer.contains("\"game_black_card\"")) {
					buffer = br.readLine();
					if(buffer.contains("card_text")) {
						int beginIndex = buffer.indexOf(">");
						int endIndex = buffer.indexOf("</");
						//System.out.println(beginIndex);
						//System.out.println(endIndex);
						blackCard = buffer.substring(beginIndex+1, endIndex);
						//System.out.println(blackCard);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		blackCardString = blackCard;
		return blackCardString;
	}
	
	/* Parses for White Cards in play and in player's hand
	 * Stores values in ArrayLists for use by logic and database
	 */
	public void parseWhiteCards(String fn) {
		//"game_right_side"
		boolean flag_whiteCardsInPlay = false;
		boolean flag_whiteCardsInHand = false;
		File f = new File(filePath + fn);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(f));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String buffer;
		String whiteCardString = "";
		try {
			while((buffer = br.readLine()) != null) {
				if(buffer.contains("The white cards played this round are:")) {
					System.out.println("White Cards in play");
					flag_whiteCardsInPlay = true;
					flag_whiteCardsInHand = false;
				}
				if(buffer.contains("The previous round was won by")) {
					flag_whiteCardsInPlay = false;
					flag_whiteCardsInHand = false;
				}
				if(buffer.contains("game_hand_cards")) {
					System.out.println("White Cards in hand");
					flag_whiteCardsInPlay = false;
					flag_whiteCardsInHand = true;
				}
				if(buffer.contains("\"bottom\"")) {
					System.out.println("Reached end of white cards");
					flag_whiteCardsInPlay = false;
					flag_whiteCardsInHand = false;
					return;
				}
				if(buffer.contains("card_text")) {
					//System.out.println(buffer);
					int beginIndex = buffer.indexOf(">");
					int endIndex = buffer.indexOf("</");
					//System.out.println(beginIndex);
					//System.out.println(endIndex);
					whiteCardString = buffer.substring(beginIndex+1, endIndex);
					//System.out.println(buffer.substring(beginIndex+1, endIndex));
					//System.out.println(whiteCardString);
					if(flag_whiteCardsInPlay) {
						//Add whiteCard obj to ArrayList whiteCardList
						System.out.println("Add White Card to whiteCardList");						
						whiteCardList.add(new WhiteCard(whiteCardString));
					} else if(flag_whiteCardsInHand) {
						//Add whiteCard obj to ArrayList gameHand
						System.out.println("Add White Card to gameHand");
						gameHand.add(new WhiteCard(whiteCardString));
					} else {
						//Do nothing
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Web w = new Web();
		w.setUrl("http://www.pretendyoure.xyz/zy/");
		w.grabWebpage();
		//Choose 1, 2, 3 for getToGame();
		//Need to implement getting farther to actually reaching a game
		w.getToGame(2);
		w.parseBlackCards("findWhiteCards.html");
		System.out.println(w.getBlackCard());
		System.out.println("Find White Cards");
		w.parseWhiteCards("findWhiteCards.html");
		System.out.println("Print White Cards in Play");
		for(WhiteCard e : w.whiteCardList) {
			System.out.println("\t" + e.getAnswer());
		}
		System.out.println("Print White Cards in Hand");
		for(WhiteCard e : w.gameHand) {
			System.out.println("\t" + e.getAnswer());
		}
	}
	
	public void setNickName(String name){
		//Wait for the right moment to send in your nickname
	}
	
}
