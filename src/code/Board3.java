package code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

public class Board3 {

	private Location[] loc = new Location[25]; //25 locations instances in array
	private ArrayList<String> allGameWords,codeNames,people;//arraylists for allcodes,25 random codenames,and people
	private int team;// 0 means red teams turn, 1 means blue teams turn, 2 means green
	private int count, numOfRed, numOfBlue, numOfA, numOfGreen;//count and ints for tracking remaining people
	private boolean InRed, InBlue, InGreen;//1 is in game still, 0 isn't
	
	/*
	 * Board class constructor.  
	 * Creates 25 location instances.
	 * @param None
	 * @return No return type
	 */
	public Board3() {
		InRed = true ; InBlue = true ; InGreen = true; 
		numOfRed = 6; numOfBlue = 5; numOfGreen = 5; numOfA = 2;
		
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
				if(i < 6)
					people1.add("R");
				else if(i < 11)
					people1.add("B");
				else if(i < 18)
					people1.add("I");
				else if(i < 23 )
					people1.add("G");
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
	
	public boolean currentTeamAgent(int Loc) {
		count -= 1;
		loc[Loc].revealed = true;
		if(loc[Loc].getPerson().equals("R"))
			numOfRed--;
		else if(loc[Loc].getPerson().equals("B")) 
			numOfBlue--;
		else if(loc[Loc].getPerson().equals("A")) {
			numOfA--;
			if(team == 0)
				InRed = false;
			else if(team == 1)
				InBlue = false;
			else
				InGreen = false;
		}
			
		else
			numOfGreen--;
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
		if(team == 2) {
			if(loc[Loc].getPerson().equals("G"))
				return true;
			else 
				return false;
		}
		return false;
	}
	
	private void setCount(int cnt) {
		count = cnt;
		
	}

	public boolean checkWin() {
		if(InBlue && numOfBlue == 0)
			return true;
		else if(InGreen  && numOfGreen == 0)
			return true;
		else if(InRed && numOfRed == 0)
			return true;
		else if(numOfA == 0)
			return true;
		else 
			return false;
	}
	
	public int winningTeam() {
		if(InRed )
			return 0;
		else if(InBlue )
			return 1;
		else
			return 2;
	}
	
	public void switchTurn() {
		if(team == 0) {
			if(InBlue)
				team = 1;
			else
				team = 2;
		}
			else if(team == 1) {
				if(InGreen)
					team = 2;
				else
					team = 0;
	}
			else if(team == 2) {
				if(InRed)
					team = 0;
				else
					team = 1;
	}
	}

	public int getCurrentTeam() {
		
		return team;
	}
	public void aRevealed() {
		if(team == 0)
			InRed = false;
		else if(team == 1) 
			InBlue = false;
		else
			InGreen = false;
	}
	
	public Location[] getLocs() {
		return loc;
	}

	
	//ALL getters/setters after this for easier testing/access
	public int getCount() {
		return count;
	}
	
	public boolean redIn() {
		return InRed;
	}
	public boolean BlueIn() {
		return InRed;
	}
	public boolean GreenIn() {
		return InGreen;
	}
	public int numRed() {
		return numOfRed;
	}
	public int numBlue() {
		return numOfBlue;
	}
	public int numGreen() {
		return numOfGreen;
	}
	public int getAssass() {
		return numOfA;
	}
	public void decAssa() {
		numOfA--;
	}
	public void decRed() {
		numOfRed--;
	}
	public void decBlue() {
		numOfBlue--;
	}
	public void decGreen() {
		numOfGreen--;
	}
}
