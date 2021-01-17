package tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.Test;

import code.Board;
import code.Board3;
import code.Location;

public class codenameTests {
	  
	   /*
	    * Tests that the correct team (team that won) 
	    * is returned when assassin is revealed
	    */
	   @Test
	   public void testTeamNotLose() {
		   Board test = new Board();
		   assertEquals("If the red team revealed the assassin, the blue team should win",1,test.teamNotLose());
		   test.switchTurn();
		   assertEquals("If the blue team revealed the assassin, the red team should win",0,test.teamNotLose());
		  
	   }
	   
	   /*
	    * Tests that When any of the three states is on the board
	    * true is returned and false is returned otherwise.
	    */
	   @Test
	   public void winningStateTest() {
		   Board test = new Board();
		   Board test1 = new Board();
		   Board test2 = new Board();
		   assertFalse(test.checkWinningState());//Check when board is not in winning state
		   test.decNumofA();
		   assertTrue(test.checkWinningState());//Check when the assasin has been revealed
		   for(int i = 9; i > 0; i--) {
			   test1.decNumofRed();
		   }
		   assertTrue(test1.checkWinningState()); //Check when all red agents have been revealed
		   for(int i = 8; i > 0; i--) {
			   test2.decNumofBlue();
		   }
		   assertTrue(test2.checkWinningState());//Check when all blue agents have been revealed
	   }
	   
	   /*
	    * Tests that the list of codenames is created with 25 distinct codenames
	    */
	   @Test
	   public void testCodenameList() {
		   Board test = new Board();
		   boolean codeTest = true;
		  for(int i = 0; i<test.getCodenames().size();i++) {
			  for(int j = 0; j<test.getCodenames().size();j++) {
				  if(test.getCodenames().get(i).equals(test.getCodenames().get(j)) && j != i && test.getCodenames().get(i)!=null)
					  codeTest = false;
			  }
		  }
		   assertTrue(codeTest);
	   }
	   
	   /*
	    * Tests that when game is started 25 locations are given a person,codename and not revealed
	    */
	   @Test
	   public void testGameStart() {
		   Board test = new Board();
		   test.newGame();
		   int numLoc = 0;
			for(int i =0;i<test.getLocs().length;i++) {
				if(test.getLocs()[i].getCodename() != null&&test.getLocs()[i].getPerson()!= null&&!(test.getLocs()[i].getRevealed()))
					numLoc++;
			}
			assertEquals("When the game starts every location should have a codename,person, and not be revealed",25,numLoc);
	   }
	   
	   /*
	    * Tests that count is decremented
	    * Also tests that when a location is picked it is revealed
	    * Also tests that the correct value is returned when a team reveals its own agent 
	    * or the other teams agent
	    */
	   @Test 
	   public void testCurrentTeamAgent() {
		   Board test = new Board();
		   test.newGame();
		   test.setCount(5);
		   test.getLocs()[0] = new Location("code" , "R");//sets a location to have known red agent
		   assertTrue(test.currentTeamAgent(0));//tests when its red teams turn and red agent revealed returns true 
		   assertEquals("The method should decrease the count by 1",4,test.getCount());//test the count is decremented
		   assertTrue(test.getLocs()[0].getRevealed());//Tests that the chosen location is revealed
		   test.switchTurn();//switches to blue team
		   assertFalse(test.currentTeamAgent(0));//checks that method returns false when its blue team and red agent revealed
		   test.getLocs()[0] = new Location("code" , "B");//sets a location to have known blue agent
		   assertTrue(test.currentTeamAgent(0));//tests when its blue teams turn and red agent revealed returns true 
		   test.switchTurn();//switches to red team
		   assertFalse(test.currentTeamAgent(0));//checks that method returns false when its red team and blue agent revealed
	   }
	   
	   /*
	    * Tests that the file is properly read 
	    */
		@Test
		public void testReadFile()
		{ 
			Board test = new Board();
			ArrayList<String> testList = new ArrayList<>();
			try {
			for(String word : Files.readAllLines(Paths.get("src/GameWords.txt"))) {
				testList.add(word);
			}
		}catch(IOException e) {
		}
			assertEquals(testList,test.readFile("src/GameWords.txt"));
			assertTrue(test.readFile("src/GameWords.txt").contains("AZTEC"));
			assertTrue(test.readFile("src/GameWords.txt").contains("BACK"));
			assertTrue(test.readFile("src/GameWords.txt").contains("BELT"));
			assertTrue(test.readFile("src/GameWords.txt").contains("FIRE"));
		
		}
		
		/*
		 * Tests that the proper number of people are created
		 * 9 red, 8 blue, 7 innocent and 1 assassin
		 */
		@Test
		public void testRandomPeople()
		{
			Board test = new Board();
			int numRed = 0, numBlue = 0, numIn = 0, numAssas = 0;
			ArrayList<String> testee = test.randomPeople();
			for(int i = 0; i < testee.size(); i++) {
				if(testee.get(i).equals("R"))
					numRed++;
				else if(testee.get(i).equals("B"))
					numBlue++;
				else if(testee.get(i).equals("I"))
					numIn++;
				else
					numAssas++;
			}
			assertEquals(9, numRed);
			assertEquals(8, numBlue);
			assertEquals(7, numIn);
			assertEquals(1, numAssas);
			assertEquals(25,testee.size());
			
		}
		
		/*
		 * Tests whether or not the board class has 25 location instances.
		 */
		@Test
		public void test25LocationInstances(){
			Board test = new Board();
			assertEquals("The Board class should cotain 25 Location instances",25,test.getLocs().length);
		}
		
		/*
		 * Tests that the proper value is returned when a clue is or 
		 * isn't valid.
		 */
		@Test
		public void testvalidClue() {
			Board test = new Board();
			test.newGame();
			String c = "xxxxzzzzqqq"; //random string that isn't in list of possible codenames.
			int cnt = 0;
			assertTrue(test.validClue(c, cnt));//test for a valid clue not equal to any codename
			assertFalse(test.validClue(null, cnt));//test when clue is null
			assertFalse(test.validClue(test.getLocs()[0].getCodename(),cnt));//test when clue equals codename and location isn't revealed
			test.getLocs()[0].setRevealedTrue();//set location 0 to be revealed
			assertTrue(test.validClue(test.getLocs()[0].getCodename(), cnt));//check with clue that equals codename in reveled location
			}
		
		
}
