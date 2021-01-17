package code.playGame;

import code.Board;
import code.Board3;
import code.GUI.PlayingBoard;

public class PlayGame {

	/*
	 * Main method that runs the game
	 * @param An array of Strings that contains arguments
	 * @return void
	 */
	public static void main(String[] args) {
		Board b = new Board();
		b.newGame();
		Board3 b3 = new Board3();
		PlayingBoard play = new PlayingBoard(b,b3);
		play.StartGUI();
	}
}
