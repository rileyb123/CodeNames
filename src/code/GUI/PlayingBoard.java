package code.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.*;


import code.Board;
import code.Board3;
import code.ActionListen.EndTurnActionListener;
import code.ActionListen.Location3Action;
import code.ActionListen.LocationActionListener;
import code.ActionListen.QuitActionListener;
import code.ActionListen.Start3Action;
import code.ActionListen.StartActionListener;
import code.ActionListen.TextActionListener;

public class PlayingBoard {
	
	private Board mainBoard;
	private Board3 main3Board;
	private JPanel play,mainPanel,infoPanel,msg;
	private JLabel holdCount,holdClue,startMsg;
	private JFrame window;
	private int spymaster;// int for what portion of turn it is, 1 for spymaster, 0 for regular team
	private int numTeams;//2 2 teams , 3 3 teams
	
	//Constructor which takes in a board
	//Sets spymaster to 1 as the first part of a turn is always the spymaster
	/*
	 * Creates a PlayingBoard object 
	 * @param A Board to extract info from
	 * @return None
	 */
	public PlayingBoard(Board b,Board3 b3) {
		numTeams = 2;
		main3Board = b3;
		mainBoard = b;
		spymaster = 1;
		window = new JFrame("CodeNames");//main JFrame
		mainPanel = new JPanel();//main JPanel
		infoPanel = new JPanel();//info Panel will hold clue, team turn, etc
	}
	
	//Starts and sets up the board
	/*
	 * Initializes the beginning of the game
	 * @param None
	 * @return void
	 */
	public void StartGUI() {
		
		//Creates and sets the menu bar
		Dimension d = new Dimension(110,45);
		JMenuBar fileBar = new JMenuBar();//Creates bar for menu 
		JMenu Start = new JMenu("File");//Creates menu called file
		JMenuItem NewGame = new JMenuItem("Start New 2 Player Game"); //Item in menu for starting a new game
		JMenuItem New3Game = new JMenuItem("Start New 3 Player Game");
		JMenuItem Exit = new JMenuItem("Exit");//Item in menu for exiting game
		Start.setPreferredSize(d);
		NewGame.setPreferredSize(d);
		Exit.setPreferredSize(d);
		New3Game.setPreferredSize(d);
		Exit.addActionListener(new QuitActionListener());
		NewGame.addActionListener(new StartActionListener(this));
		New3Game.addActionListener(new Start3Action(this));
		Start.add(NewGame);
		Start.add(New3Game);
		Start.add(Exit);
		fileBar.add(Start);
		fileBar.setSize(50,50);
		window.setJMenuBar(fileBar);
		//Creates and sets the menu bar
		
		//Organizes JPanel and JFrame
		window.setExtendedState(JFrame.MAXIMIZED_BOTH);//Makes window fullscreen
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		//Organizes JPanel and JFrame
		
		//Creates play JPanel
		JPanel play = createBoard();
		mainPanel.add(play);
		window.add(mainPanel);
		//Creates play JPanel
		
		//Finishes organizing
		window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		//Finishes organizing
		
	}
	
	//Used to create the very first board state; a new game board
	/*
	 * Creates the game board with the codenames
	 * @param None
	 * @return The JPanel that contains the game board and buttons
	 */
	public JPanel createBoard() {
		
		Font f = new Font("Times New Roman", Font.PLAIN, 17);
		//sets JPanels, clue and count input, and messages
		play = new JPanel();
		msg = new JPanel();
		JLabel countMsg,clueMsg;
		JTextField countEnt, clueEnt;
		JButton Both = new JButton("Submit");
		countMsg = new JLabel("Count:" );
		clueMsg = new JLabel("Clue: ");
		countEnt = new JTextField("", 10);
		clueEnt = new JTextField("", 10);
		Both.addActionListener(new TextActionListener(countEnt,clueEnt,mainBoard,this,main3Board));
		startMsg = new JLabel("Red Team's Turn"); 
		startMsg.setForeground(Color.RED);
		//sets JPanels, clue and count input, and messages
		
		//Adds elements to msg panel
		msg.add(startMsg);
		msg.add(countMsg);
		msg.add(countEnt);
		msg.add(clueMsg);
		msg.add(clueEnt);
		msg.add(Both);
		//Adds elements to msg panel
		
		//Sets JLabels and end turn button and adds to infoPanel and mainPanel
		JLabel Clue = new JLabel("Clue: ");
		Clue.setFont(f);
		JLabel Count = new JLabel("Count: ");
		Count.setFont(f);
		holdClue = new JLabel("");
		holdClue.setFont(f);
		holdCount = new JLabel("");
		holdCount.setFont(f);
		JButton End = new JButton("End Turn");
		End.addActionListener(new EndTurnActionListener(this,mainBoard,main3Board));
		infoPanel.add(Count);
		infoPanel.add(holdCount);
		infoPanel.add(Clue);
		infoPanel.add(holdClue);
		infoPanel.add(End);
		mainPanel.add(msg);
	    mainPanel.add(infoPanel);
		//Sets JLabels and end turn button and adds to infoPanel and mainPanel
		 
		UpdateLocs(" "," ");
		return play;
		
	}
	
	//Used when new game menu button is pressed, restores game board to new state
	/*
	 * When the new game button is pressed, creates a new game and resets the GUI
	 * @param None
	 * @return void
	 */
	public void newGameGUI() {
		mainPanel.removeAll();
		infoPanel.removeAll();
		Board b = new Board();
		b.newGame();
		Board3 b3 = new Board3();
		b3.newGame();
		mainBoard = b;
		main3Board = b3;
		play = createBoard();
		mainPanel.add(play);
		window.repaint();;
		window.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
	}
	
	//Method which updates the locations, it also switches the current team labels 
	/*
	 * Updates Location objects and switches the current team label
	 * @param A string that contains the clue input and the count input
	 * @return void
	 */
	public void UpdateLocs(String clue, String count) {
		
		Font f = new Font("Times New Roman", Font.BOLD, 18);
		Font LocF = new Font("Times New Roman", Font.BOLD, 15);
		//Starting msg for team
		if(numTeams == 2) {
		if(mainBoard.getCurrentTeam() == 0) {
			startMsg.setText("Red Team's Turn");
			startMsg.setFont(f);
			startMsg.setForeground(Color.RED);
		} else   {
			startMsg.setText("Blue Team's Turn");
			startMsg.setFont(f);
			startMsg.setForeground(Color.BLUE);
		}
		//Starting msg for team
		
		play.removeAll();
		
		//During each player's turn
		for(int i = 0; i < 25; i++) {
			
			JButton code;
			JPanel location = new JPanel();
			holdClue.setText(clue);
			holdClue.setFont(f);
			holdCount.setText(count);
			holdCount.setFont(f);
			
			if(mainBoard.getLocs()[i].getRevealed()) {
				code = new JButton(mainBoard.getLocs()[i].getPerson());
			}else {
				if(spymaster == 1) {
					code = new JButton(mainBoard.getLocs()[i].getCodename() + "  " + mainBoard.getLocs()[i].getPerson());
				}else {
					code = new JButton(mainBoard.getLocs()[i].getCodename());
					code.addActionListener(new LocationActionListener(mainBoard, i,this,clue));
				}
			}
			
			Dimension d = new Dimension(180,150);
			code.setPreferredSize(d);
			code.setFont(LocF);
			location.add(code);
			play.add(location);
			
		}}
		//During each player's turn
		else {
			if(main3Board.getCurrentTeam() == 0) {
				
				startMsg.setText("Red Team's Turn");
				startMsg.setFont(f);
				startMsg.setForeground(Color.RED);
				
			}else if(main3Board.getCurrentTeam() == 1)  {
				
				startMsg.setText("Blue Team's Turn");
				startMsg.setFont(f);
				startMsg.setForeground(Color.BLUE);
				
			}else {
				startMsg.setText("Green Team's Turn");
				startMsg.setFont(f);
				startMsg.setForeground(Color.GREEN);
			}
				
			//Starting msg for team
			
			play.removeAll();
			
			//During each player's turn
			for(int i = 0; i < 25; i++) {
				
				JButton code;
				JPanel location = new JPanel();
				holdClue.setText(clue);
				holdClue.setFont(f);
				holdCount.setText(count);
				holdCount.setFont(f);
				
				if(main3Board.getLocs()[i].getRevealed()) {
					code = new JButton(main3Board.getLocs()[i].getPerson());
				}else {
					if(spymaster == 1) {
						code = new JButton(main3Board.getLocs()[i].getCodename() + "  " + main3Board.getLocs()[i].getPerson());
					}else {
						code = new JButton(main3Board.getLocs()[i].getCodename());
						code.addActionListener(new Location3Action(main3Board, i,this,clue));
					}
				}
				
				Dimension d = new Dimension(180,150);
				code.setPreferredSize(d);
				code.setFont(LocF);
				location.add(code);
				play.add(location);
				
			}}
		
		
		play.setLayout(new GridLayout(5,5));
		window.repaint();
		window.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
	}
	
	//Displays message when a team wins the game
	/*
	 * Displays a message when a team wins
	 * @param None
	 * @return void
	 */
	public void WinningTeamMsg() {
		
		if(mainBoard.numRed() == 0) {
			JOptionPane.showMessageDialog(window, "Red Team Wins!");
			this.newGameGUI();
		}
		else if(mainBoard.numBlue() == 0) {
			JOptionPane.showMessageDialog(window, "Blue Team Wins!");
			this.newGameGUI();
		}
		else if(mainBoard.numA()==0) {
			if(mainBoard.teamNotLose() == 1) {
				JOptionPane.showMessageDialog(window, "Blue Team Wins!");
				this.newGameGUI();
			}
			else if(mainBoard.teamNotLose() == 0) {
				JOptionPane.showMessageDialog(window, "Red Team Wins!");
				this.newGameGUI();
			}
		}
		
			
	}
	
	public void WinningTeamMsg3() {
		
		if(main3Board.getAssass() == 0) {
			if(main3Board.winningTeam() == 0)
				JOptionPane.showMessageDialog(window, "Red Team Wins!");
			else if(main3Board.winningTeam() == 1)
				JOptionPane.showMessageDialog(window, "Blue Team Wins!");
			else
				JOptionPane.showMessageDialog(window, "Green Team Wins!");
		}
		
		else {
			if(main3Board.numRed() == 0 && main3Board.redIn())
				JOptionPane.showMessageDialog(window, "Red Team Wins!");
			else if(main3Board.numBlue() == 0 && main3Board.BlueIn())
				JOptionPane.showMessageDialog(window, "Blue Team Wins!");
			else
				JOptionPane.showMessageDialog(window, "Green Team Wins!");
		}
		
		this.newGameGUI();
		
	}

	
	
	//Clears JText Fields
	/*
	 * Clears JTextFields
	 * @param A JTextField that contains the count and a JTextField that contains the clue
	 * @return void
	 */
	public void clearText(JTextField cnt, JTextField clue) {
		cnt.setText("");
		clue.setText("");
	}
	 
	//Verify that count is an integer
	/*
	 * Check if the input is a count
	 * @param A JTextField that contains the count
	 * @return 1 if valid, 0 if invalid
	 */
	public int checkCountField(JTextField cnt) {
		try{
			Integer.parseInt(cnt.getText());
		}
		catch(NumberFormatException e) {
			cnt.setText("");
			JOptionPane.showMessageDialog(window, "The Count needs to be an integer!","Count Error",JOptionPane.ERROR_MESSAGE);
			return 0;
	}
		return 1;
		}
	 //Error when count is higher than 25 (max num of locations)
	public void highCount(JTextField cnt) {
		cnt.setText("");
		JOptionPane.showMessageDialog(window, "The count can't be greater than 25");
	}
	//Error when count is entered as negative number
	/*
	 * Displays message when input is a negative number
	 * @param A JTextField that contains the count
	 * @return void
	 */
	public void negCount(JTextField cnt) {
		cnt.setText("");
		JOptionPane.showMessageDialog(window, "The Count needs to be an integer greater than or equal to 0!");
	}
	
	//Error when codename is emtpy or equals a codename
	/*
	 * Displays message when the input clue is invalid
	 * @param A JTextField that contains the clue
	 * @return void
	 */
	public void invalidClue(JTextField clue) {
		clue.setText("");
		JOptionPane.showMessageDialog(window, "The Clue Cannot be empty or equal a codename!");
	}
	
	//Switches turn from spymaster portion to rest of team
	/*
	 * Switches from spymaster to team and vice versa
	 * @param None
	 * @return void
	 */
	public void switchTurnState() {
		if(spymaster == 1)
			spymaster = 0;
		else
			spymaster = 1;
	}
	
	//Returns spymaster int, used to see what portions of teams turn it is 
	/*
	 * Returns spymaster to know current portion of turn
	 * @param None
	 * @return The int that is stored in spymaster
	 */
	public int getSpymaster() {
		return spymaster;
	}
	
	public void setNumTeams2() {
		numTeams = 2;
	}
	
	public void setNumTeams3() {
		numTeams = 3;
	}
	
	public int getTeams() {
		return numTeams;
	}

	
	
}
