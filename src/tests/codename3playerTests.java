package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import code.Board;
import code.Board3;

public class codename3playerTests {

	@Test
	public void testBoard3Winning()
	{
		 Board3 test = new Board3();
		   Board3 test1 = new Board3();
		   Board3 test2 = new Board3();
		   Board3 test3 = new Board3();
		   assertFalse(test.checkWin());//Check when board is not in winning state
		   test.decAssa(); test.decAssa();
		   assertTrue(test.checkWin());//Check when the assasins has been revealed
		   for(int i = 6; i > 0; i--) {
			   test1.decRed();
		   }
		   assertTrue(test1.checkWin()); //Check when all red agents have been revealed
		   for(int i = 5; i > 0; i--) {
			   test2.decBlue();
		   }
		   assertTrue(test2.checkWin());//Check when all blue agents have been revealed
		   for(int i = 5; i > 0; i--) {
			   test3.decGreen();
		   }
		   assertTrue(test3.checkWin());//Check when all green agents have been revealed
		
	}
	
	 /*
	  * Tests that the people are created in the correct amount\
	  * 6 red, 6 blue, 6 green, 7 innocent, and 2 assassins 
	  */
	@Test
	public void testRandomPeople()
	{
		Board3 test = new Board3();
		int numRed = 0, numBlue = 0, numGreen = 0, numIn = 0, numAssas = 0;
		ArrayList<String> testee = test.randomPeople();
		for(int i = 0; i < testee.size(); i++) {
			if(testee.get(i).equals("R"))
				numRed++;
			else if(testee.get(i).equals("B"))
				numBlue++;
			else if(testee.get(i).equals("I"))
				numIn++;
			else if(testee.get(i).equals("G"))
				numGreen++;
			else
				numAssas++;
		}
		assertEquals(6, numRed);
		assertEquals(5, numBlue);
		assertEquals(7, numIn);
		assertEquals(5,numGreen);
		assertEquals(2, numAssas);
		assertEquals(25,testee.size());
		
	}
	
	@Test
	public void testBothAssasins() {
		Board3 test = new Board3(); 
		Board3 test1 = new Board3();
		Board3 test2 = new Board3();
		test.aRevealed();
		test.switchTurn();
		test.aRevealed();
		assertEquals(2,test.winningTeam()); //Tests that green wins when red and blue revealed assassins
		
		test1.aRevealed();
		test1.switchTurn();
		test1.switchTurn();
		test1.aRevealed();
		assertEquals(1,test1.winningTeam());//Tests that blue wins under correct conditions
		
		test2.switchTurn();
		test2.aRevealed();
		test2.switchTurn();
		test2.aRevealed();
		assertEquals(0,test2.winningTeam());//Tests that red wins under correct conditions
	}
	
	//Tests the normal flow of turns without any teams being out
	@Test
	public void testNextTeamTurnNormal() {
		Board3 test = new Board3();
		test.switchTurn();
		assertEquals(1,test.getCurrentTeam());
		test.switchTurn();
		assertEquals(2,test.getCurrentTeam());
		test.switchTurn();
		assertEquals(0,test.getCurrentTeam());
	}
	
	//Tests when a team is out from revealing assassin
	@Test
	public void testNextTeamComplicated() {
		Board3 test = new Board3();
		Board3 test2 = new Board3();
		Board3 test3 = new Board3();
		
		test.aRevealed();
		test.switchTurn();
		assertEquals(1,test.getCurrentTeam());
		test.switchTurn();
		assertEquals(2,test.getCurrentTeam());
		test.switchTurn();
		assertEquals(1,test.getCurrentTeam());//Red revealed assassin so it should go back to blue here
		
		test2.switchTurn();
		test2.aRevealed();
		test2.switchTurn();
		assertEquals(2,test2.getCurrentTeam());
		test2.switchTurn();
		assertEquals(0,test2.getCurrentTeam());
		test2.switchTurn();
		assertEquals(2,test2.getCurrentTeam());//Blue revealed assassin should go back to green here
		
		test3.switchTurn();
		test3.switchTurn();
		test3.aRevealed();
		test3.switchTurn();
		assertEquals(0,test3.getCurrentTeam());
		test3.switchTurn();
		assertEquals(1,test3.getCurrentTeam());
		test3.switchTurn();
		assertEquals(0,test3.getCurrentTeam());//Green revealed assassin should go back to red here
		
	}
}
