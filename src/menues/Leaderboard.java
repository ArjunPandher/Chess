package menues;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Leaderboard implements ActionListener{

	public JFrame leaderBoardMenu = new JFrame();
	public JPanel leaderBoardPane = new JPanel();
	
	public JLabel titleLabel;
	public JLabel firstPlace;
	public JLabel secondPlace;
	public JLabel thirdPlace;
	public JLabel fourthPlace;
	public JLabel fifthPlace;
	
	public JButton backButton;
	
	public Leaderboard ()
	{
		leaderBoardPane.setLayout(new BoxLayout (leaderBoardPane, BoxLayout.PAGE_AXIS));
		leaderBoardPane.setBackground(Options.backgroundColour);
		
		titleLabel = new JLabel ();
		titleLabel.setText("Top 5 Players:");
		titleLabel.setAlignmentX(titleLabel.CENTER_ALIGNMENT);
		leaderBoardPane.add(titleLabel);
		
		firstPlace = new JLabel ();
		firstPlace.setText("First place: " + Stats.usernames[0] + " with " + Stats.elos[0] + " elo.");
		firstPlace.setAlignmentX(firstPlace.CENTER_ALIGNMENT);
		leaderBoardPane.add(firstPlace);
		
		secondPlace = new JLabel ();
		secondPlace.setText("Second place: " + Stats.usernames[1] + " with " + Stats.elos[1] + " elo.");
		secondPlace.setAlignmentX(secondPlace.CENTER_ALIGNMENT);
		leaderBoardPane.add(secondPlace);
		
		thirdPlace = new JLabel ();
		thirdPlace.setText("Third place: " + Stats.usernames[2] + " with " + Stats.elos[2] + " elo.");
		thirdPlace.setAlignmentX(thirdPlace.CENTER_ALIGNMENT);
		leaderBoardPane.add(thirdPlace);
		
		fourthPlace = new JLabel ();
		fourthPlace.setText("Fourth place: " + Stats.usernames[3] + " with " + Stats.elos[3] + " elo.");
		fourthPlace.setAlignmentX(fourthPlace.CENTER_ALIGNMENT);
		leaderBoardPane.add(fourthPlace);
		
		fifthPlace = new JLabel ();
		fifthPlace.setText("Fifth place: " + Stats.usernames[4] + " with " + Stats.elos[4] + " elo.");
		fifthPlace.setAlignmentX(fifthPlace.CENTER_ALIGNMENT);
		leaderBoardPane.add(fifthPlace);
		
		backButton = new JButton();
		backButton.addActionListener(this);
		backButton.setActionCommand("Back");
		backButton.setText("Back");
		backButton.setAlignmentX(backButton.CENTER_ALIGNMENT);
		leaderBoardPane.add(backButton);
		
		leaderBoardMenu.setContentPane(leaderBoardPane);
		leaderBoardMenu.setSize(600, 200);
		leaderBoardMenu.setBackground(Options.backgroundColour);
		leaderBoardMenu.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		
		String eventName = event.getActionCommand();
		
		if (eventName.equals("Back"))
		{
			leaderBoardMenu.setVisible(false);
			Main mainMenu = new Main();
		}
	}
}
