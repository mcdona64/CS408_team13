package HumorBot;
//Imports

import HumorBotGUI.*;
import org.apache.commons.lang3.ObjectUtils;

import java.util.*;
import java.lang.*;

/**
 * @author pxeros
 * This class is the main control flow for our AI
 */
public class MCF {
    final long THROWAWAY_MAX = 10;
    final long THROWAWAY_AVERAGE = 5;
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
    //This is the database Interface we will be using
    private DatabaseInterface databaseInterface;
    //This is for automation purposes:
    private boolean automation;
    /**
     * This variable is our flags, the are rather important, 6 is kindof a placeholder for now.
     * Here is what it might stand for:
     * {Online Mode, Spectator Mode, assortedOption1, assortedOption2, assortedOption3, assortedOption4}
     */
    private boolean[] flags = new boolean[2];


    //Constructors
    public MCF(String username) {
        this.username = username;
    }

    public MCF() {
        this.username = "placeholder";
    }

    //getters
    public String getUserName() {
        return this.username;
    }

    public void break_automation() {
        this.automation = false;
    }

    /**
     * This sets the flags, so we know what options and mode we are in, etc.
     *
     * @param flags
     */
    public void setFlags(boolean[] flags) {
        for (int i = 0; i < this.flags.length; i++)
            this.flags[i] = flags[i];
    }

    /**
     * Setter for Hand
     *
     * @param hand
     */
    public void setHand(ArrayList<WhiteCard> hand) {
        this.hand.clear();
        for (int i = 0; i < hand.size(); i++) {
            try {
                WhiteCard curr = databaseInterface.getWhiteCard(hand.get(i).getAnswer());
                if (curr != null)
                    this.hand.add(curr);
                else {
                    this.databaseInterface.addWhiteCard(new WhiteCard(hand.get(i).getAnswer()));
                    i--;
                }
            } catch (ConnectionNotEstablishedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * this is a hard setter that exists only for testing purposes
     *
     * @param hand, the hardcoded hand
     */
    public void hardCodeHand(ArrayList<WhiteCard> hand) {
        this.hand = hand;
    }

    /**
     * This an average function, it gets the average of our hand.
     *
     * @param average = a flag to see if we want to get the average of the AverageWeights
     * @return the average
     */
    private long getCardAverage(boolean average) {
        long av = 0;
        for (int i = 0; i < this.hand.size(); i++) {
            if (!average) {
                av += this.hand.get(i).getWeight();
            } else {
                av += this.hand.get(i).getAverageWeight();
            }
        }
        return av / this.hand.size();
    }

    //Setters
    public void setCurrentCard(BlackCard card) {
        this.currentCard = card;
    }
    /**
     * This function will return the hand that is given to the user.
     */
    /*public void updateHand(){
        this.hand = this.crawler.getHand();
		for(int i = 0; i < this.hand.size(); i++){
			//TODO implement database stuff
			long weight = 0;
			long avWeight = 0;
			this.hand.get(i).setWeight(weight);
			this.hand.get(i).setAverage(avWeight);
		}
	}*/

    //AI and important Functions

    /**
     * Deep stuff, this will come way later down the road
     */
    public int[] makeDecision(String blackCard) {
        //get the number of blanks
        int g = 0;
        for (int i = 0; i < blackCard.length(); i++) {
            if (blackCard.charAt(i) == '_') {
                g++;
            }
        }
        this.setCurrentCard(new BlackCard(g, blackCard));
        if (this.flags[0])
            setHand(this.crawler.getHand());
        for (int i = 0; i < this.hand.size(); i++) {
            try {
                this.hand.set(i, new WhiteCard(this.hand.get(i).getAnswer(), this.databaseInterface.getWeight(this.hand.get(i), this.currentCard),
                        this.databaseInterface.getAverageWeight(this.hand.get(i))));
            } catch (ConnectionNotEstablishedException e) {
                e.printStackTrace();
            } catch (NullPointerException n) {
                try {
                    this.databaseInterface.addWhiteCard(this.hand.get(i));
                    i--;
                } catch (ConnectionNotEstablishedException e) {
                    e.printStackTrace();
                } catch (NullPointerException kill) {
                    System.out.println("either hand or database is uninitialzed, which is no good");
                    kill.printStackTrace();
                }
            }
        }
        try {
            setCurrentCard(this.databaseInterface.getBlackCard(this.currentCard));
            this.currentCard.setBlanks(g);
        } catch (ConnectionNotEstablishedException e) {
            e.printStackTrace();
        } catch (NullPointerException f) {
            try {
                this.databaseInterface.addBlackCard(this.currentCard);
            } catch (ConnectionNotEstablishedException r) {
                r.printStackTrace();
            }
        }
        long averageWeight = getCardAverage(false);
        long best_weight = getCardMaxWeight();
        //Not a throaway round
        if (best_weight >= THROWAWAY_MAX || averageWeight >= THROWAWAY_AVERAGE) {
            if (this.currentCard.getBlanks() <= 1) {
                if (!this.flags[0]) {
                    this.crawler.chooseAnswer(assessHandNormal());
                } else {
                    int[] thing = {assessHandNormal()};
                    return thing;
                }
            } else {
                try {
                    if (!this.flags[0]) {
                        this.crawler.chooseAnswer(assessHandMultipleBlanks(this.currentCard.getBlanks(), false));
                    } else {
                        Integer[] thing = (Integer[]) assessHandMultipleBlanks(this.currentCard.getBlanks(), false).toArray();
                        int[] hanso = new int[thing.length];
                        for (int k = 0; k < thing.length; k++) {
                            hanso[k] = Integer.getInteger("humina", thing[k]);
                        }
                        return hanso;
                    }
                } catch (CardCountException e) {
                    e.printStackTrace();
                }
            }
        }//throwaway round
        else {
            if (this.currentCard.getBlanks() <= 1) {
                if (this.flags[0]) {
                    this.crawler.chooseAnswer(assessHandNormal());
                } else {
                    int[] thing = {assessHandNormal()};
                    return thing;
                }
            } else {
                try {
                    if (this.flags[0]) {
                        this.crawler.chooseAnswer(assessHandMultipleBlanks(this.currentCard.getBlanks(), true));
                    } else {
                        Object[] thing = assessHandMultipleBlanks(this.currentCard.getBlanks(), true).toArray();
                        int[] hanso = new int[thing.length];
                        for (int k = 0; k < thing.length; k++) {
                            if (thing[k] instanceof Integer)
                                hanso[k] = Integer.getInteger("humina", (Integer) thing[k]);
                        }
                        return hanso;
                    }
                } catch (CardCountException e) {
                    e.printStackTrace();
                }
            }
        }
        return new int[0];
    }

    private long getCardMaxWeight() {
        long best = this.hand.get(0).getWeight();
        for (int i = 1; i < this.hand.size(); i++) {
            if (this.hand.get(i).getWeight() >= best) {
                best = this.hand.get(i).getWeight();
            }
        }
        return best;
    }

    //These are the hand assessors for each individual situation

    /**
     * This is assessing the best hand on a normal round
     *
     * @return the index of the best card,
     */
    public int assessHandNormal() {
        long max = 0;
        int best_choice = 0;
        for (int i = 0; i < this.hand.size(); i++) {
            WhiteCard curr = this.hand.get(i);
            if (curr.getWeight() > max) {
                max = curr.getWeight();
                best_choice = i;
            } else if (curr.getWeight() == max) {
                WhiteCard prevMax = this.hand.get(best_choice);
                best_choice = (prevMax.getAverageWeight() >= curr.getAverageWeight()) ? best_choice : i;
            }
        }
        return best_choice;
    }

    /**
     * This is the function that will be used for determining what card wil be thrown away on a throwaway round
     *
     * @return
     */
    public int assessHandThrowaway() {
        long min = this.hand.get(0).getAverageWeight();
        int best_choice = 0;
        for (int i = 1; i < this.hand.size(); i++) {
            WhiteCard curr = this.hand.get(i);
            if (curr.getAverageWeight() < min) {
                min = curr.getAverageWeight();
                best_choice = i;
            } else if (curr.getAverageWeight() == min) {
                WhiteCard prevMin = this.hand.get(best_choice);
                best_choice = (prevMin.getAverageWeight() <= curr.getAverageWeight()) ? best_choice : i;
            }
        }
        return best_choice;
    }

    /**
     * this is poorly made, but until we get a better method, it will have to do.
     *
     * @param numBlanks
     * @param throaway
     * @return
     */
    public ArrayList<Integer> assessHandMultipleBlanks(int numBlanks, boolean throaway) throws CardCountException {
        if (numBlanks >= this.hand.size()) {
            throw new CardCountException("Too many blanks in hand");
        } else if (numBlanks <= 1) {
            throw new CardCountException("Only 1 or no blanks found, as opposed to the multiple blanks expected");
        }
        ArrayList<Integer> choices = new ArrayList<Integer>();
        if (throaway) {
            for (int i = 0; i < numBlanks; i++) {
                ArrayList<WhiteCard> prevHand = new ArrayList<WhiteCard>();
                for (int e = 0; e < this.hand.size(); e++)
                    prevHand.add(this.hand.get(e));
                for (int j = 0; j < choices.size(); j++) {
                    int f;
                    for (f = 0; f < this.hand.size(); f++) {
                        if (this.hand.get(f).equals(prevHand.get((int) choices.get(j)))) {
                            break;
                        }
                    }
                    this.hand.remove(f);
                }
                int toSend = assessHandThrowaway();
                //choices.add(prevHand.indexOf(this.hand.get(toSend)));
                for (int j = 0; j < prevHand.size(); j++) {
                    if (prevHand.get(j).equals(this.hand.get(toSend))) {
                        choices.add(j);
                        break;
                    }
                }
                //System.out.println(choices.get(choices.size() - 1));
                this.hand = prevHand;
            }
        } else {
            try {
                int bestWeight = -1;

                WhiteCard[] g = new WhiteCard[numBlanks];
                int numArrays = (int) Math.pow(this.hand.size(), numBlanks);
                for (int i = 0; i < numArrays; i++) {
                    // Calculate the correct item for each position in the array
                    for (int j = 0; j < numBlanks; j++) {
                        // This is the period with which this position changes, i.e.
                        // a period of 5 means the value changes every 5th array
                        int period = (int) Math.pow(this.hand.size(), numBlanks - j - 1);
                        // Get the correct item and set it
                        int index = i / period % this.hand.size();
                        g[j] = this.hand.get(index);
                    }
                    int[] l = this.databaseInterface.getBestPermitation(this.currentCard, g, numBlanks);
                    WhiteCard[] goodg = new WhiteCard[numBlanks];
                    for (int f = 0; f < l.length; f++) {
                        goodg[f] = this.hand.get(getIndex(g[l[f]]));
                    }
                    int smifned;
                    if ((smifned = this.databaseInterface.getWeight(goodg, this.currentCard)) >= bestWeight) {
                        bestWeight = smifned;
                        for (int j = 0; j < l.length; j++) {
                            choices.add(l[j]);
                        }
                    }
                }


            } catch (ConnectionNotEstablishedException f) {
                f.printStackTrace();
            } catch (NullPointerException n) {
                System.out.println("Database interface not initialized");
                n.printStackTrace();
            }
        }
        return choices;
    }

    public int getIndex(WhiteCard card) {
        for (int i = 0; i < this.hand.size(); i++) {
            if (this.hand.get(i).equals(card)) {
                return i;
            }
        }
        return -1;
    }

    public void initalize(boolean[] flags) {
        this.automation = true;
        setFlags(flags);
        try {
            this.databaseInterface = new DatabaseInterface();
        } catch (ConnectionNotEstablishedException e) {
            //e.printStackTrace();
            System.out.println("ConnectionNotEstablishedException error caught");
        }
        if (this.flags[0]) {
            initializeWebCrawler();
        }
        if (this.flags[1]) {
            System.out.println("Spectator Mode: has been initlialized.");
        }
    }


    //These are the functions that we need
    public void initializeWebCrawler() {
        if (this.crawler != null) {
            this.crawler.close();
        }
        this.crawler = new Web("http://www.pretendyoure.xyz/zy/");
        this.crawler.grabWebpage();
        this.crawler.getToGame(1);
        this.crawler.setNickName(this.username);
        this.crawler.getToGamePage(this.username);
    }

    /**
     * this will be the function that is automating what we do.
     * Don't know how that will be done. but when we do it will be a doozy.
     */
    public void doStuff() {
        try {
            for (; ; ) {
                this.automation = true;
                try {
                    for (; ; ) {
                        //Connect to a game Think I may be missing things
                        this.crawler.parseLobbies();
                        if (this.crawler.getAvailableLobbies().size() == 0) {
                            throw new Game_Over_Excpetion();
                        }
                        Lobby curr_Lobby = null;
                        for (int i = 0; i < this.crawler.getAvailableLobbies().size(); i++) {
                            if (this.flags[1]) {
                                if (this.crawler.getAvailableLobbies().get(i).getSpectatorCount() >= this.crawler.getAvailableLobbies().get(i).getMaxSpectators()) {
                                    continue;
                                } else {
                                    curr_Lobby = this.crawler.getAvailableLobbies().get(i);
                                    this.crawler.joinLobby(curr_Lobby.getGameNum(), false);
                                    break;
                                }
                            } else {
                                if (this.crawler.getAvailableLobbies().get(i).getPlayerCount() >= this.crawler.getAvailableLobbies().get(i).getMaxPlayers()) {
                                    continue;
                                } else {
                                    curr_Lobby = this.crawler.getAvailableLobbies().get(i);
                                    this.crawler.joinLobby(curr_Lobby.getGameNum(), true);
                                    break;
                                }
                            }
                        }
                        if (curr_Lobby == null) {
                            throw new Exit_Automation_Exception("No games with space found in this server");
                        }
                        //Now for the actual play
                        for (; ; ) {
                            if (!this.automation)
                                throw new Exit_Automation_Exception("Automation Exited");
                            if (!this.flags[1]) {
                                //updates handled in makeDecision
                                this.crawler.waitForCardsInHand();
                                this.crawler.saveWebpage("current");
                                this.crawler.parseCards("current");
                                this.setHand(this.crawler.getHand());
                                if (!this.crawler.currentState()) {
                                    if (!this.automation)
                                        throw new Exit_Automation_Exception("Automation Exited");
                                    ArrayList<WhiteCard> on_table = this.crawler.getWhiteCardList();
                                    Random r = new Random();
                                    int win = r.nextInt(on_table.size()) % this.currentCard.getBlanks();
                                    this.crawler.czarChoose(win);
                                } else {
                                    if (this.hand.size() > 0)
                                        this.makeDecision(this.crawler.getBlackCard());
                                }
                            }
                            if (!this.automation)
                                throw new Exit_Automation_Exception("Automation Exited");
                            System.out.println("Waiting");
                            Thread t = new Thread(){
                                @Override
                                public void run() {
                                    crawler.seeWinningCardSelected();
                                }
                            };
                            t.start();
                            for(;;){
                                if(t.isInterrupted()){
                                    break;
                                }
                                if (!this.automation)
                                    throw new Exit_Automation_Exception("Automation Exited");
                            }
                            this.crawler.parseCards("current");
                            if (!this.automation)
                                throw new Exit_Automation_Exception("Automation Exited");
                            try {
                                if (this.currentCard.getBlanks() <= 1)
                                    this.databaseInterface.adjustWeights(this.crawler.getWinningHand().get(0), this.crawler.getBlackCard());
                                else {
                                    String[] result = new String[this.currentCard.getBlanks()];
                                    ArrayList<WhiteCard> thing = this.crawler.getWinningHand();
                                    for (int i = 0; i < this.currentCard.getBlanks(); i++) {
                                        result[i] = thing.get(i).getAnswer();
                                    }
                                    this.databaseInterface.adjustWeights(result, this.currentCard.getQuestion(), this.currentCard.getBlanks());
                                }
                            } catch (ConnectionNotEstablishedException u) {
                                u.printStackTrace();
                                return;
                            }
                        }
                    }
                } catch (Game_Over_Excpetion r) {
                    System.out.println("Game ended, finding new Game");
                } catch (NullPointerException t) {
                    throw new Exit_Automation_Exception("Null pointer: Something is not right...");
                }
            }
        } catch (Exit_Automation_Exception e) {
            e.cheeseIt(this.flags[1]);
            this.crawler.leaveGame();
            this.crawler.close();
        }
    }

    public void handleWin(int[] pos) {
        try {
            if (pos.length == 1) {
                this.databaseInterface.adjustWeights(this.hand.get(pos[0]), this.currentCard.getQuestion());
            } else {
                String[] h = new String[pos.length];
                for (int i = 0; i < pos.length; i++) {
                    h[i] = this.hand.get(pos[i]).getAnswer();
                }
                this.databaseInterface.adjustWeights(h, this.currentCard.getQuestion(), this.currentCard.getBlanks());
            }
        } catch (ConnectionNotEstablishedException t) {
            t.printStackTrace();
        }
    }

    /**
     * FUNCTIONS FOR AUTOMATIONS:
     * wait for winning card: seeWinningCardSelected()
     * When check if card (TO be done)
     * Select winning card as card Czar czarChoose()
     */

    /**
     * FOR TESTING PURPOSES ONLY
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("We have switched to junit babe.");
    }
}
