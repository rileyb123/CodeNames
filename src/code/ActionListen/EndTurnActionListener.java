package code.ActionListen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import code.Board;
import code.Board3;
import code.GUI.PlayingBoard;

public class EndTurnActionListener implements ActionListener {

	private PlayingBoard p;
	private Board b;
	private Board3 b3;
	
	/*
	 * Creates new EndTurnActionListener object
	 * @param A PlayingBoard and a Board to use in its methods
	 * @return None
	 */
	public EndTurnActionListener(PlayingBoard play, Board b1,Board3 b2) {
		p = play;
		b = b1;
		b3 = b2;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(p.getTeams() == 2) {
		//If it is the second part of a teams turn the turn ends and switches to other team
		if(p.getSpymaster() == 0) {
		b.switchTurn();
		p.switchTurnState();
		p.UpdateLocs("","");
		}}
		else if(p.getSpymaster() == 0){
			b3.switchTurn();
			p.switchTurnState();
			p.UpdateLocs(" ", " ");
		}
		
	}

}
