package menues;

import mainGame.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

public class Main implements ActionListener {

	public JFrame mainMenu = new JFrame();		// JFrame for the main menu
	public JPanel mmPane = new JPanel();		// content pane for the main menu
	
	public JPanel col1 = new JPanel();			// panel for the first column in the menu
	public JPanel col2 = new JPanel();			// panel for the second column in the menu
	public JPanel col3 = new JPanel();			// panel for the third column in the menu
	
	// items in column 1
	public JButton loginButton;						// button that allows the user to load a profile
	
	// items in column 2
	public JLabel title;						// title of menu
	public JButton playButton;					// button that starts a new game of chess
	public JButton optionsButton;				// button that opens up the options menu
	public JButton helpButton;					// button that opens up the help menu
	
	// items in column 3
	public JButton statsButton;						// button that allows the user to create a profile
	
	public Main()
	{
		mmPane.setLayout(new BoxLayout (mmPane, BoxLayout.LINE_AXIS));
		
		col1.setLayout(new BoxLayout (col1, BoxLayout.PAGE_AXIS));
		col1.setAlignmentX(col1.CENTER_ALIGNMENT);
		col1.setAlignmentY(col1.CENTER_ALIGNMENT);
		col1.setMinimumSize(new Dimension (200, 500));
		col1.setMaximumSize(new Dimension (200, 500));
		col1.setBackground(Options.backgroundColour);
		
		col2.setLayout(new BoxLayout (col2, BoxLayout.PAGE_AXIS));
		col2.setAlignmentX(col2.CENTER_ALIGNMENT);
		col2.setAlignmentY(col2.CENTER_ALIGNMENT);
		col2.setMinimumSize(new Dimension (200, 500));
		col2.setMaximumSize(new Dimension (200, 500));
		col2.setBackground(Options.backgroundColour);
		
		col3.setLayout(new BoxLayout (col3, BoxLayout.PAGE_AXIS));	
		col3.setAlignmentX(col3.CENTER_ALIGNMENT);
		col3.setAlignmentY(col3.CENTER_ALIGNMENT);
		col3.setMinimumSize(new Dimension (200, 500));
		col3.setMaximumSize(new Dimension (200, 500));
		col3.setBackground(Options.backgroundColour);
		
		loginButton = new JButton();
		loginButton.addActionListener(this);
		loginButton.setActionCommand("Signup");
		loginButton.setText("Sign Up");
		loginButton.setAlignmentX(loginButton.CENTER_ALIGNMENT);
		loginButton.setAlignmentY(loginButton.TOP_ALIGNMENT);
		col1.add(loginButton);
		
		mmPane.add(col1);
		
		title = new JLabel();
		title.setText("Chess!!!");
		title.setAlignmentX(title.CENTER_ALIGNMENT);
		col2.add(title);
		
		playButton = new JButton();
		playButton.addActionListener(this);
		playButton.setActionCommand("Play");
		playButton.setText("Play Game");
		playButton.setAlignmentX(playButton.CENTER_ALIGNMENT);
		col2.add(playButton);
		
		optionsButton = new JButton();
		optionsButton.addActionListener(this);
		optionsButton.setActionCommand("Options");
		optionsButton.setText("Options");
		optionsButton.setAlignmentX(optionsButton.CENTER_ALIGNMENT);
		col2.add(optionsButton);
		
		
		helpButton = new JButton();
		helpButton.addActionListener(this);
		helpButton.setActionCommand("Help");
		helpButton.setText("Help");
		helpButton.setAlignmentX(helpButton.CENTER_ALIGNMENT);
		col2.add(helpButton);
		
		mmPane.add(col2);	
		
		statsButton = new JButton();
		statsButton.addActionListener(this);
		statsButton.setActionCommand("Stats");
		statsButton.setText("Stats");
		statsButton.setAlignmentX(statsButton.CENTER_ALIGNMENT);
		statsButton.setAlignmentY(statsButton.TOP_ALIGNMENT);
		col3.add(statsButton);
		
		mmPane.add(col3);
		mmPane.setBackground(Options.backgroundColour);
		
		mainMenu.setContentPane(mmPane);
		mainMenu.setSize(500, 175);
		mainMenu.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent event) {
		
		String eventName = event.getActionCommand();
		
		if (eventName.equals("Options"))
		{
			mainMenu.setVisible(false);
			Options optionsMenu = new Options();
		}
		
		if (eventName.equals("Play"))
		{
			mainMenu.setVisible(false);
			Login loginMenu = new Login();
		}
		
		if (eventName.equals("Help"))
		{
			mainMenu.setVisible(false);
			Help helpMenu = new Help();
		}
		
		if (eventName.equals("Signup"))
		{
			mainMenu.setVisible(false);
			Sign signupMenu = new Sign();
		}
		
		if (eventName.equals("Stats"))
		{
			mainMenu.setVisible(false);
			try {
				Stats statsMenu = new Stats();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/*
		mainMenu.setVisible(false);
		Play playMenu = new Play();
	 */
}
