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
    long THROWAWAY_MAX = 10;
    long THROWAWAY_AVERAGE = 5;
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
    /**
     * This variable is our flags, the are rather important, 6 is kindof a placeholder for now.
     * Here is what it might stand for:
     * {Online Mode, Spectator Mode, assortedOption1, assortedOption2, assortedOption3, assortedOption4}
     */
    private boolean[] flags = new boolean[6];


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
                this.hand.add(databaseInterface.getWhiteCard(hand.get(i).getAnswer()));
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
    public void makeDecision() {
        //get the number of blanks
        String blackCard = this.crawler.getBlackCard();
        String[] splitted = blackCard.split("_");
        this.setCurrentCard(new BlackCard(splitted.length - 1, blackCard));
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
            if (this.currentCard.getBlanks() <= 1)
                this.crawler.chooseAnswer(assessHandNormal());
            else {
                try {
                    this.crawler.chooseAnswer(assessHandMultipleBlanks(this.currentCard.getBlanks(), false));
                } catch (CardCountException e) {
                    e.printStackTrace();
                }
            }
        }//throwaway round 
        else {
            if (this.currentCard.getBlanks() <= 1)
                this.crawler.chooseAnswer(assessHandThrowaway());
            else {
                try {
                    this.crawler.chooseAnswer(assessHandMultipleBlanks(this.currentCard.getBlanks(), true));
                } catch (CardCountException e) {
                    e.printStackTrace();
                }
            }
        }
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
                WhiteCard[] g = new WhiteCard[this.hand.size()];
                for (int i = 0; i < this.hand.size(); i++) {
                    g[i] = this.hand.get(i);
                }
                int[] i = this.databaseInterface.getBestPermitation(this.currentCard, g, numBlanks);
                for (int j = 0; j < i.length; j++) {
                    choices.add(i[j]);
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

    public void initalize(boolean[] flags) {
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
    private void initializeWebCrawler() {
        this.crawler = new Web("http://www.pretendyoure.xyz/zy/");
        this.crawler.setNickName(this.username);
        this.crawler.grabWebpage();
        //TODO add getting to the webpage
    }

    /**
     * this will be the function that is automating what we do.
     * Don't know how that will be done. but when we do it will be a doozy.
     */
    public void doStuff() {
        //TODO: Implement, so we can run the web
    }

    /**
     * FOR TESTING PURPOSES ONLY
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("We have switched to junit babe.");
    }
}
