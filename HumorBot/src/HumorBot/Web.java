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
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.util.StringUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import net.sourceforge.htmlunit.corejs.javascript.Script;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Web {
	private MCF mcf;
	private String url;
	private int server;
	private String nickname;
	private static String filePath = "./web/";
	private String fileName = "index.html";
	private String fullFileName = filePath + fileName;
	private BlackCard b;
	private ArrayList<WhiteCard> whiteCardList = new ArrayList<WhiteCard>();
	private ArrayList<WhiteCard> gameHand = new ArrayList<WhiteCard>();
	private ArrayList<String> fileNames = new ArrayList<String>();
	private ArrayList<String> urls = new ArrayList<String>();
	private ArrayList<WhiteCard> winningHand = new ArrayList<WhiteCard>();
	private ArrayList<Lobby> lobbyList = new ArrayList<Lobby>();

	private WebDriver wd;
	
	public Web(String url){
		this.url = url;
		//fileNames.add("index.html");
	}
	
	public Web() {
		//fileNames.add("index.html");
	}
	
	public void setUrl(String url) { this.url = url; }
	
	public void setServer(int s) {
		this.server = s;
	}
	
	public int getServer() {
		return this.server;
	}

	public String getBlackCard() {
		return this.b.getQuestion();
	}
	
	public ArrayList<WhiteCard> getHand(){ return this.gameHand; }

	public ArrayList<Lobby> getLobbyList() { return lobbyList; }

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
			urls.add(webURL);
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
		this.setServer(server);
		this.grabWebpage();
	}
	
	/* Parses for Black Card
	 * Stores BlackCard string and blanks in a BlackCard
	 */
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
		//Find numBlanks
		int count = StringUtils.countMatches(blackCard, "____");
		
		b = new BlackCard(count, blackCard);
	}
	
	/* Parses for White Cards in play and in player's hand
	 * Stores values in ArrayLists for use by logic and database
	 */
	
	
	public void parseWhiteCards(String fn) {
		//"game_right_side"
		boolean flag_whiteCardsInPlay = false;
		boolean flag_whiteCardsInHand = false;
		boolean flag_winningWhiteCard = false;
		
		File f = new File(filePath + fn);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(f));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String buffer;
		String whiteCardString = "";
		String whiteCardID = "";
		try {
			while((buffer = br.readLine()) != null) {

				if(buffer.contains("The white cards played this round are:")) {
			//		System.out.println("White Cards in play");
					flag_whiteCardsInPlay = true;
					flag_whiteCardsInHand = false;
				}
				if(buffer.contains("The previous round was won by")) {
					flag_whiteCardsInPlay = false;
					flag_whiteCardsInHand = false;
				}
				if(buffer.contains("game_hand_cards")) {
			//		System.out.println("White Cards in hand");
					flag_whiteCardsInPlay = false;
					flag_whiteCardsInHand = true;
				}
				if(buffer.contains("\"bottom\"")) {
			//		System.out.println("Reached end of white cards");
					flag_whiteCardsInPlay = false;
					flag_whiteCardsInHand = false;
					return;
				}
				if(buffer.contains("card whitecard selected")) {
					flag_winningWhiteCard = true;
				//	System.out.println("true");
				} else {
					if(buffer.contains("card_text")) {

					} else {
						flag_winningWhiteCard = false;
					//	System.out.println("false");
					}
				}
				if(buffer.contains("<div id=\"card_") && buffer.contains("<div id=\"white_up_")){
					int beginIndex = buffer.indexOf("<div id=\"");
					int endIndex = buffer.indexOf("\" class");
					System.out.println("CardID");
					whiteCardID = buffer.substring(beginIndex+("<div id=\"").length(), endIndex);

				}
				if(buffer.contains("card_text")){
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
						//System.out.println("Add White Card to whiteCardList");
						whiteCardList.add(new WhiteCard(whiteCardString));
						whiteCardList.get(whiteCardList.size() - 1).setCardID(whiteCardID);
					} else if(flag_whiteCardsInHand) {
						//Add whiteCard obj to ArrayList gameHand
						//System.out.println("Add White Card to gameHand");
						gameHand.add(new WhiteCard(whiteCardString));
						gameHand.get(gameHand.size() - 1).setCardID(whiteCardID);
					} else {
						//Do nothing
					}
					if(flag_winningWhiteCard) {
						//System.out.println("Winning WhiteCard");
						if(winningHand.size() < b.getBlanks()) {
							winningHand.add(new WhiteCard(whiteCardString));
							winningHand.get(winningHand.size() - 1).setCardID(whiteCardID);
						}
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

	public void setNickName(String name){
		//Wait for the right moment to send in your nickname
		this.nickname = name;
	}

	public String getNickName() {
		return this.nickname;
	}

	public void getToGamePage(String nickname) {
		String url = "";
		for (String str : urls) {
			if (str.contains("pyx-" + this.server)) {
				url = str;
			}
		}

		System.out.println(url);

		System.setProperty("webdriver.chrome.driver", "ChromeDriver/chromedriver");
		WebDriver driver = new ChromeDriver();
		wd = driver;
		driver.get(url);
		driver.manage().window().maximize();
		driver.getPageSource();
		WebElement button_1 = driver.findElement(By.cssSelector("input"));
		button_1.click();

		WebElement name = driver.findElement(By.id("nickname"));
		name.sendKeys(nickname);
		WebElement button_2 = driver.findElement(By.id("nicknameconfirm"));
		button_2.click();
		//Should add functionality for pausing and waiting for GUI input for what game to choose.
		//Look into PhantomJS for hiding web browser window
		// v GUI can be updated with information from below v


		//For testing purposes close window
		//driver.close();

		/* games are labeled by:
		 * gamelist_lobby_##
		 * Information that can be viewed in this <div> field:
		 * 	-Number of Players
		 * 	-Number of Spectators
		 * 	-State of game (Not Started / In Progress)
		 * 	-How many card sets
		 * 	-Requires Password or not
		 */
		saveWebpage("gamelist");

	}

	public void saveWebpage(String fileName) {
		//Saves web page for reference if needed
		String src = wd.getPageSource();
				try {
					BufferedWriter bw = new BufferedWriter(new FileWriter(filePath + fileName + ".html"));
					bw.write(src);
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}

	public void parseGames() {
		File f = new File(filePath + "lobby.html");
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(f));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String buffer;
		String gameNum = "";
		String lobbyHost = "";
		String playerCount = "";
		String maxPlayers = "";
		String spectatorCount = "";
		String maxSpectators = "";
		boolean lobbyStatus = false;
		boolean password = false;

		try {
			while((buffer = br.readLine()) != null) {
				if(buffer.contains("div id=\"gamelist_lobby_") && !buffer.contains("div id=\"gamelist_lobby_template")){
					int beginIndex = buffer.indexOf("aria-label=\"");
					int endIndex = buffer.indexOf("\">");
					String str = buffer.substring(beginIndex+("aria-label=\"").length(), endIndex);
					System.out.println(str);
					//parse string and break it up into components

					//gameNum
					beginIndex = buffer.indexOf("lobby_");
					endIndex = buffer.indexOf("\" class");
					gameNum = buffer.substring(beginIndex+("lobby_").length(), endIndex);

					//lobbyHost
					beginIndex = 0;
					endIndex = str.indexOf("'s game");
					lobbyHost = str.substring(beginIndex, endIndex);

					//playerCount
					beginIndex = str.indexOf("with ");
					endIndex = str.indexOf(" of");
					playerCount = str.substring(beginIndex + ("with ").length(), endIndex);

					//maxPlayers
					beginIndex = str.indexOf("of ");
					endIndex = str.indexOf(" player");
					maxPlayers = str.substring(beginIndex + ("of ").length(), endIndex);

					//spectatorCount
					beginIndex = str.indexOf("and ");
					endIndex = str.lastIndexOf(" of");
					spectatorCount = str.substring(beginIndex + ("and ").length(), endIndex);

					//maxSpectators
					beginIndex = str.lastIndexOf("of ");
					endIndex = str.indexOf("spectator");
					maxSpectators = str.substring(beginIndex + ("of ").length(), endIndex);

					//lobbyStatus
					if(str.contains("In Progress")){
						lobbyStatus = true;
					} else {
						lobbyStatus = false;
					}

					//password
					if(str.contains("Does not have a password")){
						password = false;
					} else {
						password = true;
					}
					
				//	System.out.println("gameNum " + gameNum);
				//	System.out.println("lobbyHost " + lobbyHost);
				//	System.out.println("playerCount " + playerCount);
				//	System.out.println("maxPlayers " + maxPlayers);
				//	System.out.println("spectatorCount " + spectatorCount);
				//	System.out.println("maxSpectators " + maxSpectators);
				//	System.out.println("lobbyStatus " + lobbyStatus);
				//	System.out.println("Password " + password);
				//	System.out.println("---------------------");
					lobbyList.add(new Lobby(gameNum, lobbyHost, playerCount, maxPlayers, spectatorCount, maxSpectators, lobbyStatus, password));

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

	/**
	 * Join a specific game
	 * @param game game lobby index
	 * @param play true for play. false for spectate
	 */
	public void joinGame(int game, boolean play){
		WebDriverWait wait = new WebDriverWait(wd, 30);
		if(play){
			System.out.println("Join Game");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='main']//*[@id='game_list']//*[@id='gamelist_lobby_" + game + "']//*[@class='gamelist_lobby_join']"))).click();
		} else {
			System.out.println("Spectate Game");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='main']//*[@id='game_list']//*[@id='gamelist_lobby_" + game + "']//*[@class='gamelist_lobby_spectate']"))).click();
		}

	}

	/**
	 * This is the function that actually chooses the answer in the web browser
	 * @param index winning index
	 */
	public void chooseAnswer(int index){
		//TODO: Implement

	}

	/**
	 * "             " except in the case of multiple blanks
	 * @param indecies array list of winning indecies
	 */
	public void chooseAnswer(ArrayList<Integer> indecies){
		//TODO: Implement
	}

	public void close(){

		wd.close();
	}

	public static void main(String[] args) {
		Web w = new Web();

		w.parseGames();

		/*

		w.setUrl("http://www.pretendyoure.xyz/zy/");
		w.grabWebpage();
		//Choose 1, 2, 3 for getToGame();
		//Need to implement getting farther to actually reaching a game
		w.getToGame(1);


		Scanner in = new Scanner(System.in);
		System.out.println("Please enter name");
		String name = in.nextLine();
		w.setNickName(name);
		w.getToGamePage(w.getNickName());

		//w.parseGames();




		//w.joinGame(2, true);

		boolean t = true;

		while(t) {
			System.out.println("Enter: 'p' to save Web page\n" + "Enter: 'stop' to close window");
			String s = in.nextLine();
			if(s.equals("p")) {
				w.saveWebpage("manualSave");
			} else if(s.equals("stop")) {
				w.wd.close();
				t = false;
			}

		}
		*/
	}
	
	
}
