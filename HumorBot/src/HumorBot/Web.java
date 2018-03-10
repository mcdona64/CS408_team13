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
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.ObjectUtils;
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
import org.openqa.selenium.support.ui.Sleeper;
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
	private ArrayList<Lobby> availableLobbies = new ArrayList<Lobby>();

	private String parseFileName = "current";

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

	public ArrayList<Lobby> getLobbyList() { return this.lobbyList; }

	public ArrayList<Lobby> getAvailableLobbies() { return this.availableLobbies; }

	public ArrayList<WhiteCard> getWinningHand() { return this.winningHand;	}

	public ArrayList<WhiteCard> getWhiteCardList() { return this.whiteCardList; }

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
		File f = new File(filePath + fn + ".html");
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
		
		File f = new File(filePath + fn + ".html");
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

	public void clearLists(){
		this.whiteCardList.clear();
		this.gameHand.clear();
		this.winningHand.clear();
	}

	public void parseCards(String fn){
		this.saveWebpage(fn);
		this.clearLists();
		this.parseBlackCards(fn);
		this.parseWhiteCards(fn);
	}


	public void setNickName(String name){
		//Wait for the right moment to send in your nickname
		this.nickname = name;
	}

	public String getNickName() {
		return this.nickname;
	}

	public boolean getToGamePage(String nickname) {
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
		WebDriverWait wait = new WebDriverWait(wd, 30);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@type='button']"))).click();



		WebElement name = driver.findElement(By.id("nickname"));
		name.sendKeys(nickname);
		WebElement button_2 = driver.findElement(By.id("nicknameconfirm"));
		button_2.click();

		this.saveWebpage("usernameInput");

		File f = new File(filePath + "lobby.html");
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(f));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String buffer = "";
		try {
			while ((buffer = br.readLine()) != null){
				if(buffer.contains("Nickname is already in use") || buffer.contains("Nickname must contain only upper and lower case letters, numbers, or underscores, must be 3 to 30 characters long, and must not start with a number")){
					System.out.println("Invalid Username");
					return false;
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

		return true;

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

	public void parseLobbies() {
		saveWebpage("lobby");
		File f = new File(filePath + "lobby.html");
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(f));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String buffer = "";
		int gameNum = 0;
		String lobbyHost = "";
		int playerCount = 0;
		int maxPlayers = 0;
		int spectatorCount = 0;
		int maxSpectators = 0;
		boolean lobbyStatus = false;
		boolean password = false;

		try {
			while((buffer = br.readLine()) != null) {
				if(buffer.contains("div id=\"gamelist_lobby_") && !buffer.contains("div id=\"gamelist_lobby_template")){
					int beginIndex = buffer.indexOf("aria-label=\"");
					int endIndex = buffer.indexOf("\">");
					String str = buffer.substring(beginIndex+("aria-label=\"").length(), endIndex);
					//System.out.println(str);
					//parse string and break it up into components

					//gameNum
					beginIndex = buffer.indexOf("lobby_");
					endIndex = buffer.indexOf("\" class");
					gameNum = Integer.parseInt(buffer.substring(beginIndex+("lobby_").length(), endIndex));


					//lobbyHost
					beginIndex = 0;
					endIndex = str.indexOf("'s game");
					lobbyHost = str.substring(beginIndex, endIndex);

					//playerCount
					beginIndex = str.indexOf("with ");
					endIndex = str.indexOf(" of");
					playerCount = Integer.parseInt(str.substring(beginIndex + ("with ").length(), endIndex));

					//maxPlayers
					beginIndex = str.indexOf("of ");
					endIndex = str.indexOf(" player");
					maxPlayers = Integer.parseInt(str.substring(beginIndex + ("of ").length(), endIndex));

					//spectatorCount
					beginIndex = str.indexOf("and ");
					endIndex = str.lastIndexOf(" of");
					spectatorCount = Integer.parseInt(str.substring(beginIndex + ("and ").length(), endIndex));

					//maxSpectators
					beginIndex = str.lastIndexOf("of ");
					endIndex = str.indexOf("spectator");
					maxSpectators = Integer.parseInt(str.substring(beginIndex + ("of ").length(), endIndex));

					//lobbyStatus
					if(str.contains("In Progress")){
						lobbyStatus = true;
					} else {
						lobbyStatus = false;
					}

					//password
					if(str.contains("Does not have a password")){
						password = false;
						availableLobbies.add(new Lobby(gameNum, lobbyHost, playerCount, maxPlayers, spectatorCount, maxSpectators, lobbyStatus, password));
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
	 * checks if bot can enter game and then enters game
	 * @param gameNum integer of game number of available lobbies
	 * @param play True if bot is joining game | False if spectating
	 * @return
	 */
	public void joinLobby(int gameNum, boolean play){
		for(Lobby e : availableLobbies){
			if(e.getGameNum()==gameNum){
				if(play){
					//joining a game
					//check if there is room
					if(e.getPlayerCount() < e.getMaxPlayers() && !e.hasPassword()){
						join(gameNum, play);
					} else {
						System.out.println("Lobby full");
					}
				} else {
					//spectating a game
					//check if there is room for spectators
					if(e.getMaxSpectators() > 0 && e.getSpectatorCount() < e.getMaxSpectators() && !e.hasPassword()){
						join(gameNum, play);
					} else {
						System.out.println("Spectating not available");
					}
				}
			}
		}
	}

	public void printAvailableLobbies(){
		for(Lobby e : availableLobbies){
			e.print();
		}
	}

	public void printAvailableLobbyNums(){
		for(Lobby e : availableLobbies){
			if(e.getLobbyStatus()) {
				System.out.print(e.getGameNum() + " | ");
			}
		}
	}

	public void printBlackCard(){
		System.out.println("Print Black Card:");
		System.out.println(this.b.getQuestion());
		System.out.println("-----------------------");
	}

	public void printHand(){
		System.out.println("Print White Cards in Hand:");
		for(WhiteCard e : this.gameHand){
			System.out.println(e.getAnswer());
		}
		System.out.println("-----------------------");
	}

	public void printWhiteCardsInPlay(){
		System.out.println("Print White Cards in Play:");
		for(WhiteCard e : this.whiteCardList){
			System.out.println(e.getAnswer());
		}
		System.out.println("-----------------------");
	}

	public void printWinningCards(){
		System.out.println("Print Winning Hand:");
		for(WhiteCard e : this.winningHand){
			System.out.println(e.getAnswer());
		}
		System.out.println("-----------------------");
	}

	public void printCardIDs(){
		System.out.println("Print White Cards in Hand CardIDs:");
		for(WhiteCard e : this.gameHand){
			System.out.println(e.getAnswer() + " | " + e.getCardID());
		}
		System.out.println("-----------------------");
	}

	public void printInPlayCardIDs(){
		System.out.println("Print White Cards in Play CardIDs:");
		for(WhiteCard e : this.whiteCardList){
			System.out.println(e.getAnswer() + " | " + e.getCardID());
		}
		System.out.println("-----------------------");
	}

	/**
	 * Join a specific game
	 * @param game game lobby index
	 * @param play true for play. false for spectate
	 */
	public void join(int game, boolean play){
		WebDriverWait wait = new WebDriverWait(wd, 30);
		if(play){
			System.out.println("Join Game");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='main']//*[@id='game_list']//*[@id='gamelist_lobby_" + game + "']//*[@class='gamelist_lobby_join']"))).click();
			waitForCardsInHand();
		} else {
			System.out.println("Spectate Game");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='main']//*[@id='game_list']//*[@id='gamelist_lobby_" + game + "']//*[@class='gamelist_lobby_spectate']"))).click();
		}

	}

	public boolean inGame(){
		if(wd.findElements(By.xpath("//*[@id='main_holder']//*")).size()==0){
			System.out.println("Not in game");
			return false;
		} else {
			System.out.println("In game");
			return true;
		}
	}

	public void leaveGame(){
		if(inGame()){
			wd.findElement(By.xpath("//*[@id='leave_game']")).click();
			String parentWindow = wd.getWindowHandle();

			wd.switchTo().alert().accept();
			wd.switchTo().window(parentWindow);

		} else {
			System.out.println("Not in game so can't leave a game");
		}

	}

	public boolean waitForCardsInHand(){
		WebDriverWait wait = new WebDriverWait(wd, 120);
		int i = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[@class='game_hand_cards']//*[@class='card_holder']"))).size();
		parseCards(parseFileName);
		System.out.println(i);
		return i > 0;
	}

	/**
	 * This is the function that actually chooses the answer in the web browser
	 * @param index winning index
	 */
	public void chooseAnswer(int index){
		String id = "";
		parseCards(parseFileName);
		int i = 0;
		for(WhiteCard e : this.gameHand){
			id = e.getCardID().substring(e.getCardID().indexOf("_") + 1);
			i = Integer.parseInt(id);
			if(i==index){
				WebDriverWait wait = new WebDriverWait(wd, 30);
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='game_hand_cards']//*[@id='" + e.getCardID() + "']"))).click();
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='main_holder']//*[@class='confirm_card']"))).click();
				return;
			}
		}
		System.out.println("Card index not available");
	}

	public void czarChoose(int index){
		String id = "";
		parseCards(parseFileName);
		int i = 0;
		for(WhiteCard e : this.whiteCardList){
			id = e.getCardID().substring(e.getCardID().indexOf("_") + 1);
			i = Integer.parseInt(id);
			if(i==index){
				WebDriverWait wait = new WebDriverWait(wd, 30);
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='game_right_side']//*[@id='" + e.getCardID() + "']"))).click();
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='main_holder']//*[@class='confirm_card']"))).click();
			}
		}

	}

	public void pollRound(){
		WebDriverWait wait = new WebDriverWait(wd, 120);
		while(wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[@class='game_right_side']//*"))).size()==0){

		}
		System.out.println("Cards are available");

	}

	public boolean seeWinningCardSelected(){
		WebDriverWait wait = new WebDriverWait(wd, 420);
		wait.pollingEvery(2, TimeUnit.SECONDS);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[@class='game_right_side_box game_white_card_wrapper']//*[@class='card whitecard selected']")));
		return true;
	}

	public void webInit(String username){
		this.setUrl("http://www.pretendyoure.xyz/zy/");
		this.grabWebpage();
		this.getToGame(1);
		this.setNickName(username);
		boolean flag = this.getToGamePage(this.getNickName());
		if(!flag) {
			System.out.println("Invalid Username or Could not enter lobby");
		}
		this.parseLobbies();

	}

	/**
	 * Finds current state of player when in play mode
	 * @return true if player | false if card czar
	 */
	public boolean currentState(){
		this.saveWebpage("checkState");
		File f = new File(filePath + "checkState.html");
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(f));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String buffer = "";

		try {
			while ((buffer = br.readLine()) != null) {
				if(buffer.contains("You are the Card Czar")){
					return false;
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
		return true;
	}



	/**
	 * "             " except in the case of multiple blanks
	 * @param indecies array list of winning indecies
	 */
	public void chooseAnswer(ArrayList<Integer> indecies){
		for(int i = 0; i < indecies.size(); i++){
			chooseAnswer(Integer.parseInt("dunnowhyneedname", indecies.get(i)));
		}
	}

	public void close(){
		if(inGame()){
			leaveGame();
		}
		WebDriverWait wait = new WebDriverWait(wd, 30);
		int i = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[@id='logout']"))).size();
		if(i==0){
			wd.close();
		} else {
			wd.findElement(By.xpath("//*[@id='logout']")).click();
			String parentWindow = wd.getWindowHandle();
			wd.switchTo().alert().accept();
			wd.switchTo().window(parentWindow);
			wd.close();
		}
	}

	public static void main(String[] args) {
		Web w = new Web();


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


		boolean t = true;

		while(t) {
			if(!w.wd.toString().contains("null")) {
				System.out.println("Enter: 'p' to save Web page\n"
						+ "Enter: 'l' to list available lobbies\n"
						+ "Enter: 'j' to join a game\n"
						+ "Enter: 'c' to check if in game\n"
						+ "Enter: 'parse' to parse cards\n"
						+ "Enter: 'ids' to print card ids\n"
						+ "Enter: 'leave' to leave a game\n"
						+ "Enter: 'stop' to close window");
				String s = in.nextLine();
				int i;
				switch (s) {
					case "stop":
						w.close();
						t = false;
						break;
					case "p":
						w.saveWebpage("manualSave");
						break;
					case "l":
						w.parseLobbies();
						w.printAvailableLobbies();
						System.out.println("Game Nums");
						w.printAvailableLobbyNums();
						break;
					case "j":
						System.out.println("Enter an available game lobby number:");
						i = in.nextInt();
						w.joinLobby(i, true);

						break;
					case "s":
						System.out.println("Enter an available game lobby number:");
						i = in.nextInt();
						w.joinLobby(i, false);
						break;
					case "c":
						w.inGame();
						break;
					case "leave":
						w.leaveGame();
						break;
					case "parse":
						System.out.println("Parse white and black cards");
						w.parseCards(w.parseFileName);
						System.out.println("Print Cards");
						w.printBlackCard();
						w.printHand();
						w.printWhiteCardsInPlay();
						w.printWinningCards();
						System.out.println("Cards Parsed");
						break;
					case "ids":
						System.out.println("Get Card IDs");
						w.printCardIDs();
						break;
					case "wait":
						w.waitForCardsInHand();
						break;
					case "choose":
						System.out.println("Choose card given ID:");
						w.parseCards(w.parseFileName);
						w.printCardIDs();
						i = in.nextInt();
						w.chooseAnswer(i);
						break;
					case "czar":
						System.out.println("Choose card given ID:");
						w.parseCards(w.parseFileName);
						w.printInPlayCardIDs();
						i = in.nextInt();
						w.czarChoose(i);
						break;
					case "wait for":
						w.seeWinningCardSelected();
						break;

				}
			} else {
				System.out.println("Browser is closed");
				t = false;
			}

		}

	}
	
	
}
