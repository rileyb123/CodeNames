package code.ActionListen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import code.GUI.PlayingBoard;

public class Start3Action implements ActionListener{

PlayingBoard p;
	
	/*
	 * Creates new StartActionListener object
	 * @param A PlayingBoard to use in its methods
	 * @return None
	 */
	public Start3Action(PlayingBoard play) {
		p = play; 
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		p.setNumTeams3();
		
		if(p.getSpymaster() == 0)
			p.switchTurnState();
		p.newGameGUI();
		
		
	}

}
