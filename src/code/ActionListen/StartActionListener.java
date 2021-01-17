package code.ActionListen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import code.GUI.PlayingBoard;

public class StartActionListener implements ActionListener {

	PlayingBoard p;
	
	/*
	 * Creates new StartActionListener object
	 * @param A PlayingBoard to use in its methods
	 * @return None
	 */
	public StartActionListener(PlayingBoard play) {
		p = play; 
		
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		p.setNumTeams2();
		//Starts new game, makes sure it starts as spymasters turn
		if(p.getSpymaster()==0)
			p.switchTurnState();
		p.newGameGUI();
		
		
		
	}

}
