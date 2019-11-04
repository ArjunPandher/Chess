package menues;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import mainGame.*;

public class Play implements ActionListener{

	public JFrame playMenu = new JFrame();
	public JPanel playPane = new JPanel();
	
	public JPanel subPane;
	
	public JLabel minutesLabel;
	public JLabel secondsLabel;
	
	public JButton playButton;
	public JButton backButton;
	
	public JComboBox <String> choices;
	
	public JTextField minutes;
	public JTextField seconds;
	
	public String [] choiceList = {"Chess", "Chess 960", "Really Bad Chess", "Upside-down Chess", "Charge of the Light Brigade", "Dunsay's Chess", "Horde", "End Game"};
	
	public static String playerW;
	public static String playerB;
	
	public Play(String whitePlayer, String blackPlayer)
	{
		playerW = whitePlayer;
		playerB = blackPlayer;
		
		playPane.setLayout(new BoxLayout (playPane, BoxLayout.PAGE_AXIS));
		playPane.setBackground(Options.backgroundColour);
		
		choices = new JComboBox <String> (choiceList);
		choices.setSelectedIndex(0);
		choices.setEditable(false);
		choices.setPreferredSize(new Dimension (100, 50));
		playPane.add(choices);
		
		subPane = new JPanel();
		subPane.setLayout(new GridLayout (2, 2));
		subPane.setBackground(Options.backgroundColour);
		
		minutesLabel = new JLabel();
		minutesLabel.setText("Enter desired number of minutes for each player:");
		minutesLabel.setAlignmentX(minutesLabel.CENTER_ALIGNMENT);
		subPane.add(minutesLabel);
		
		minutes = new JTextField();
		subPane.add(minutes);
		
		secondsLabel = new JLabel();
		secondsLabel.setText("Enter desired number of seconds for each player:");
		secondsLabel.setAlignmentX(secondsLabel.CENTER_ALIGNMENT);
		subPane.add(secondsLabel);
		
		seconds = new JTextField();
		subPane.add(seconds);
		
		playPane.add(subPane);
		
		playButton = new JButton();
		playButton.addActionListener(this);
		playButton.setActionCommand("Play");
		playButton.setText("Play");
		playButton.setPreferredSize(new Dimension(100, 150));
		playButton.setAlignmentX(playButton.CENTER_ALIGNMENT);
		playPane.add(playButton);
		
		backButton = new JButton();
		backButton.addActionListener(this);
		backButton.setActionCommand("Back");
		backButton.setText("Back");
		backButton.setPreferredSize(new Dimension(100, 150));
		backButton.setAlignmentX(backButton.CENTER_ALIGNMENT);
		playPane.add(backButton);
		
		playMenu.setContentPane(playPane);
		playMenu.setSize(600, 200);
		playMenu.setBackground(Options.backgroundColour);
		playMenu.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		String eventName = event.getActionCommand();
		
		if (eventName.equals("Play"))
		{
			JOptionPane errorPane = new JOptionPane();
			try
			{
				int mins = Integer.parseInt(minutes.getText());
				
				try
				{
					int secs = Integer.parseInt(seconds.getText());
					
					Options.mins = mins;
					Options.secs = secs;
					
					if (choices.getSelectedIndex() == 0)
					{
						playMenu.setVisible(false);
						Chess game = new Chess();
					}
					else if (choices.getSelectedIndex() == 1)
					{
						playMenu.setVisible(false);
						Chess960 game = new Chess960();
					}
					else if (choices.getSelectedIndex() == 2)
					{
						playMenu.setVisible(false);
						ReallyBad game = new ReallyBad();
					}
					else if (choices.getSelectedIndex() == 3)
					{
						playMenu.setVisible(false);
						UpsideDown game = new UpsideDown();
					}
					else if (choices.getSelectedIndex() == 4)
					{
						playMenu.setVisible(false);
						LightBrigade game = new LightBrigade();
					}
					else if (choices.getSelectedIndex() == 5)
					{
						playMenu.setVisible(false);
						Dunsay game = new Dunsay();
					}
					else if (choices.getSelectedIndex() == 6)
					{
						playMenu.setVisible(false);
						Horde game = new Horde();
					}
					else if (choices.getSelectedIndex() == 7)
					{
						playMenu.setVisible(false);
						EndGame game = new EndGame();
					}
				}
				catch (NumberFormatException e)
				{
					JOptionPane.showMessageDialog(null, 
							"Please enter an integer for the seconds field.", 
							"Error Message", 
							JOptionPane.ERROR_MESSAGE);
				}
			}
			catch (NumberFormatException e)
			{
				JOptionPane.showMessageDialog(null, 
						"Please enter an integer for the minutes field.", 
						"Error Message", 
						JOptionPane.ERROR_MESSAGE);
			}
		}
		if (eventName.equals("Back"))
		{
			playMenu.setVisible(false);
			Main mainMenu = new Main();
		}
	}
	
}
