package code.ActionListen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import code.Board;
import code.Board3;
import code.GUI.PlayingBoard;

public class Location3Action implements ActionListener{

	Board3 bo;
	int num;
	PlayingBoard p;
	String clue;
	
	public Location3Action(Board3 b, int i,PlayingBoard play, String c) {
		bo = b;
		num = i;
		p = play;
		clue = c;
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(p.getSpymaster() == 0) {
			if(!(bo.currentTeamAgent(num)) || bo.checkWin()) { // THIS LINE DECS THE COUNT ITSELF!!!
				if(bo.checkWin()) {
					p.WinningTeamMsg3();	
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
