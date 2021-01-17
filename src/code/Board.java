package code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

public class Board  {
	
	private Location[] loc = new Location[25]; //25 locations instances in array
	private ArrayList<String> allGameWords,codeNames,people;//arraylists for allcodes,25 random codenames,and people
	private int team;// 0 means red teams turn, 1 means blue teams turn.
	private int count, numOfRed, numOfBlue, numOfA;//count and ints for tracking remaining people
	
	/*
	 * Board class constructor.  
	 * Creates 25 location instances.
	 * @param None
	 * @return No return type
	 */
	public Board() {
		numOfRed = 9;
		numOfBlue = 8;
		numOfA = 1;
		
		allGameWords = readFile("src/GameWords.txt");
	    codeNames = pickCodenames();
	    people = randomPeople();
	}
	
	/*
	 * Called when game starts, gives each location a codename and makes 
	 * each location unrevealed(false)
	 * @param None
	 * @return void
	 */
	public void newGame() {
		team = 0;
		for(int i = 0; i < 25; i++) {
			loc[i] = new Location(codeNames.get(i),people.get(i));
		}
	}
	
	/*
	 * Read the text from GameWords.txt and fills readGameWords with those words
	 * @param Name of the file to be read
	 * @return An ArrayList that contains all of the words from the file read
	 */
	public ArrayList<String> readFile(String filename) {
		ArrayList<String> readFileAL = new ArrayList<>();
		try{
			for(String word : Files.readAllLines(Paths.get(filename))) {
				readFileAL.add(word);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		return readFileAL;
	}
	
	/*
	 * Randomly assigns the teams with the appropriate amount of agents, bystanders, and assassins
	 * @param None
	 * @return An ArrayList of Strings with randomly assigned agents, bystanders, and assassins
	 */
	public ArrayList<String> randomPeople(){
		ArrayList<String> people1 = new ArrayList<>();
			for(int i = 0; i < 25; i++) {
				if(i < 9)
					people1.add("R");
				else if(i < 17)
					people1.add("B");
				else if(i < 24)
					people1.add("I");
				else
					people1.add("A");
			}
			Collections.shuffle(people1);
			return people1;
	}
	
	/*
	 * Picks 25 codenames from the list of read words by first creating a copy
	 * of the list of read words, shuffling them, and setting gameWords
	 * to the first 25 words of the shuffled list
	 * @param None
	 * @return An ArrayList that contains 25 random words to be used in-game
	 */
	public ArrayList<String> pickCodenames() {
		ArrayList<String> copy = new ArrayList<>();
		for(String word : allGameWords) {
			copy.add(word);
		}
		Collections.shuffle(copy);
		ArrayList<String> pickNamesAL = new ArrayList<>();
		for(int i = 0; i < 25; i++) {
			pickNamesAL.add(copy.get(i));
		}
		
		return pickNamesAL;
	}
	
	/*
	 * Checks Whether a clue is valid. If a clue is valid returns true. If a clue
	 * is not valid returns false.
	 * @param String (the clue)
	 * @return boolean
	 */
	public boolean validClue(String c,int cnt) {
		if(c == null || c.equals(""))
			return false;
		//c = c.trim();//Removes leading and ending spaces for less "Cheaty" codenames 
		for(Location l : loc) {
			if(l.getCodename().equals(c.toUpperCase())  && !(l.getRevealed()))
				return false;
		}
		setCount(cnt);
		return true;
	}
	
	/*
	 * Decrements count by 1.
	 * Reveals location when codename was selected.
	 * Returns true if location contained current teams agent, false otherwise.
	 * @param an int which represent the index of the selected location in loc array.
	 * @return boolean
	 */
	public boolean currentTeamAgent(int Loc) {
		count -= 1;
		loc[Loc].revealed = true;
		if(loc[Loc].getPerson().equals("R"))
			numOfRed--;
		else if(loc[Loc].getPerson().equals("B")) 
			numOfBlue--;
		else if(loc[Loc].getPerson().equals("A"))
			numOfA--;
		if(team == 0) {
			if(loc[Loc].getPerson().equals("R"))
				return true;
			else 
				return false;
		}
		if(team == 1) {
			if(loc[Loc].getPerson().equals("B"))
				return true;
			else 
				return false;
		}
		return false;
	}
	
	/*
	 * Returns what team won when assasin is revealed. 
	 * 1 is blue team. 0 is red team. team variable is
	 * tracked throughout game.
	 * @param none
	 * @return int, the team that won
	 */
	public int teamNotLose() {
		if(team == 0)
			return 1;
		else 
			return 0;
	}
	
	/*
	 * Check if the board is in a winning state(no more blue agents, 
	 * no more red agents, or if a team revealed the assassin)
	 * @param none
	 * @return A boolean that states if the board is in a winning state
	 */
	public boolean checkWinningState() {
		return numOfBlue == 0 || numOfRed == 0 || numOfA == 0;
	}
	

	/*
	 * Getter method of numOfRed
	 * @param None
	 * @return An int representing the number of current Red agents
	 */
	public int numRed() {
		return numOfRed;
	}
	
	/*
	 * Getter method of numOfBlue
	 * @param None
	 * @return An int representing the number of current Blue agents
	 */
	public int numBlue() {
		return numOfBlue;
	}
	
	/*
	 * Getter method of numOfA
	 * @param None
	 * @return An int representing the number of current assassins
	 */
	public int numA() {
		return numOfA;
	}
	
	/*
	 * Getter for loc variable
	 * @param None
	 * @return The array of Location an their info
	 */
	public Location[] getLocs() {
		return loc;
	}
	
	/*
	 * Setter for easier access to count
	 * @param An int that will be the new count
	 * @return void
	 */
	public void setCount(int cnt) {
		count = cnt;
	}
	
	/*
	 * Getter for count variable
	 * @param None
	 * @return The int that has the number of characters on the board
	 */
	public int getCount() {
		return count;
	}
	
	/*
	 * Getter for Codenames list
	 * @param None
	 * @return The arraylist that contains all the codenames of the characters
	 */
	public ArrayList<String> getCodenames(){
		return codeNames;
	}
	
	/*
	 * Switches what teams turn it is.
	 * @param None
	 * @return void
	 */
	public void switchTurn() {
		if(team == 0)
			team = 1;
		else 
			team = 0;
	}
	
	/*
	 * Method to decrement number of people remaining for Assassians
	 * @param None
	 * @return void
	 */
	public void decNumofA() {
		numOfA--;
	}
	
	/*
	 * Method to decrement number of people remaining for Red team
	 * @param None
	 * @return void
	 */
	public void decNumofRed() {
		numOfRed--;
	}
	
	/*
	 * Method to decrement number of people remaining for Blue team
	 * @param None
	 * @return void
	 */
	public void decNumofBlue() {
		numOfBlue--;
	}
	
	/*
	 * Method to decrement the variable count
	 * @param None
	 * @return void
	 */
	public void decCount() {
		count--;
	}
	
	/*
	 * Return the team whose turn it is currently is
	 * @param None
	 * @return void
	 */
	public int getCurrentTeam() {
			return team;
	}
}
