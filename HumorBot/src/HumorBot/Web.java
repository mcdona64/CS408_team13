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
	private ArrayList<WhiteCard> whiteCardList;
	private ArrayList<WhiteCard> gameHand;
	
	private ArrayList<String> fileNames = new ArrayList<String>();
	
	public Web(MCF mcf) {
		this.mcf = mcf;
		//fileNames.add("index.html");
	}
	
	public Web() {
		//fileNames.add("index.html");
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

	public void grabWebpage(String webURL) {
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
					System.out.println(beginIndex);
					System.out.println(endIndex);
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
		this.grabWebpage(gameURL);
	}
	
	
	public void parseBlackCards(String fn) {
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
						System.out.println(beginIndex);
						System.out.println(endIndex);
						blackCard = buffer.substring(beginIndex+1, endIndex);
						System.out.println(blackCard);
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
	}
	
	
	//<div class="game_hand"> contains cards in hand
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
		try {
			while((buffer = br.readLine()) != null) {
				if(buffer.contains("game_white_cards game_right_side_cards\"><")) {
					System.out.println("White Cards in play");
					flag_whiteCardsInPlay = true;
					if(buffer.contains("card_text")) {
						int beginIndex = buffer.indexOf(">");
						int endIndex = buffer.indexOf("</");
						System.out.println(beginIndex);
						System.out.println(endIndex);
						
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
		w.grabWebpage("http://www.pretendyoure.xyz/zy/");
		//Choose 1, 2, 3 for getToGame();
		//Need to implement getting farther to actually reaching a game
		w.getToGame(2);
		w.parseBlackCards("findWhiteCards.html");
		//w.parseWhiteCards("findWhiteCards.html");
	}
	
}
