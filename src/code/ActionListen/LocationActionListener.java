package code.ActionListen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import code.Board;
import code.GUI.PlayingBoard;

public class LocationActionListener implements ActionListener{

	Board bo;
	int num;
	PlayingBoard p;
	String clue;
	
	/*
	 * Creates new LocationActionListener object
	 * @param A Board, int, PlayingBoard and a String to use in its methods
	 * @return None
	 */
	public LocationActionListener(Board b, int i,PlayingBoard play, String c) {
		bo = b;
		num = i;
		p = play;
		clue = c;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		/*Code for every location, when location is pressed first makes sure it is the
		second part of teams turn, it then checks whether the person is the current teams agent or if the
		board is in a winning state. It switches teams turn if any conditions are met which require
		the turn to be switched
		 */
		if(p.getSpymaster() == 0) {	
		if(!(bo.currentTeamAgent(num)) || bo.checkWinningState()) { // THIS LINE DECS THE COUNT ITSELF!!!
			if(bo.checkWinningState()) {
				p.WinningTeamMsg();	
			}
			bo.switchTurn();
			p.switchTurnState();
			p.UpdateLocs("","");
		}
		
		else if(bo.getCount() < 0) {
			bo.switchTurn();
			p.switchTurnState();
			p.UpdateLocs("","");
		}
		else{
			p.UpdateLocs(clue,String.valueOf(bo.getCount()) );
		}
		}
		
}
	
}