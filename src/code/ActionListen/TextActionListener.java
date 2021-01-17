package code.ActionListen;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JTextField;

import code.Board;
import code.Board3;
import code.GUI.PlayingBoard;

public class TextActionListener implements ActionListener {
	
	PlayingBoard p;
	Board3 b3;
	Board b;
	JTextField fcnt,fclue;

	/*
	 * Creates new EndTurnActionListener object
	 * @param A JTextfield for the count, A JTextfield for the clue, 
	 * a Board, and a PlayingBoard
	 * @return None
	 */
	public TextActionListener(JTextField m,JTextField n,Board b1, PlayingBoard p1, Board3 b2) {
		fcnt = m;
		fclue = n;
		b = b1;
		p = p1;
		b3 = b2;
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		
		if(fcnt.getText().equals("Rick") && fclue.getText().equals("Roll")) {
			Desktop d=Desktop.getDesktop();
			try {
				d.browse(new URI("https://www.youtube.com/watch?v=dQw4w9WgXcQ"));
			}
			catch (IOException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}}
		
		//Hertz easter egg 
		else if(fcnt.getText().equals("Hertz") && fclue.getText().equals("Hertz")) {
			Desktop d=Desktop.getDesktop();
			try {
				d.browse(new URI("http://i.imgur.com/mmV6EW7.jpg"));
				d.browse(new URI("https://d1b10bmlvqabco.cloudfront.net/attach/jcp7ft8dxclno/j6y876i9v7715d/jfvt5dfz28ev/WECANDOIT.jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		//Hertz easter egg
		
			
		//Checks that its the spymasters "turn" then validates count and clue
		}else if(p.getSpymaster() == 1 && p.getTeams() == 2) {
			if(p.checkCountField(fcnt) == 1) {
				if(Integer.parseInt(fcnt.getText()) < 0) {
					p.negCount(fcnt);
				}
				else if(Integer.parseInt(fcnt.getText()) > 25)
					p.highCount(fcnt);
				else if(!(b.validClue(fclue.getText(), Integer.parseInt(fcnt.getText())))) {
					p.invalidClue(fclue);
				}else {
					p.switchTurnState();
					p.UpdateLocs(fclue.getText(),fcnt.getText());
					p.clearText(fcnt, fclue);
				}
			}
		}
		else if(p.getSpymaster() == 1 && p.getTeams() == 3) {
			if(p.checkCountField(fcnt) == 1) {
				if(Integer.parseInt(fcnt.getText()) < 0) {
					p.negCount(fcnt);
				}
				else if(Integer.parseInt(fcnt.getText()) > 25)
					p.highCount(fcnt);
				else if(!(b3.validClue(fclue.getText(), Integer.parseInt(fcnt.getText())))) {
					p.invalidClue(fclue);
				}else {
					p.switchTurnState();
					p.UpdateLocs(fclue.getText(),fcnt.getText());
					p.clearText(fcnt, fclue);
				}
		}
	}
}}
