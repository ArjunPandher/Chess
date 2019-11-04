package menues;

import java.awt.Dimension;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class Stats implements ActionListener{

	public static int [] elos;
	public static String [] usernames;
	
	public JFrame statsMenu = new JFrame();
	public JPanel statsPane = new JPanel();
	
	public JButton leaderButton;
	public JButton findAUserButton;
	public JButton backButton;
	
	public JLabel titleLabel;
	
	public Stats () throws IOException
	{
		statsPane.setLayout(new BoxLayout (statsPane, BoxLayout.PAGE_AXIS));
		statsPane.setBackground(Options.backgroundColour);
		
		titleLabel = new JLabel();
		titleLabel.setText("Stats Menu");
		titleLabel.setAlignmentX(titleLabel.CENTER_ALIGNMENT);
		statsPane.add(titleLabel);
		
		leaderButton = new JButton();
		leaderButton.addActionListener(this);
		leaderButton.setActionCommand("Leaderboard");
		leaderButton.setText("Leaderboard");
		leaderButton.setPreferredSize(new Dimension(100, 150));
		leaderButton.setAlignmentX(leaderButton.CENTER_ALIGNMENT);
		statsPane.add(leaderButton);
		
		findAUserButton = new JButton();
		findAUserButton.addActionListener(this);
		findAUserButton.setActionCommand("Find A User");
		findAUserButton.setText("Find A User");
		findAUserButton.setPreferredSize(new Dimension(100, 150));
		findAUserButton.setAlignmentX(findAUserButton.CENTER_ALIGNMENT);
		statsPane.add(findAUserButton);
		
		backButton = new JButton();
		backButton.addActionListener(this);
		backButton.setActionCommand("Back");
		backButton.setText("Back");
		backButton.setPreferredSize(new Dimension(100, 150));
		backButton.setAlignmentX(backButton.CENTER_ALIGNMENT);
		statsPane.add(backButton);
		
		statsMenu.setContentPane(statsPane);
		statsMenu.setSize(300, 200);
		statsMenu.setBackground(Options.backgroundColour);
		statsMenu.setVisible(true);
		
		updateValues();
	}
	
	public static void updateValues () throws IOException
	{
		File dir = new File ("src/userInfo");
		File [] directoryListing = dir.listFiles();
		elos = new int [directoryListing.length];
		usernames = new String [directoryListing.length];
		
		for (int i = 0; i < directoryListing.length; i++)
		{
			BufferedReader br = new BufferedReader (new FileReader (directoryListing [i]));
			
			usernames [i] = br.readLine();
			br.readLine();
			elos [i] = Integer.parseInt(br.readLine());
		}
		
		sort();
	}
	
	// sorting the arrays using selection sort
	public static void sort ()
	{
		for (int i = 0; i < elos.length - 1; i++)
		{
			int max = i;
			for (int j = i + 1; j < elos.length; j++)
			{
				if (elos [j] > elos [max])
				{
					max = j;
				}
			}
			if (max != i)
			{
				int tempInt = elos [i];
				elos [i] = elos [max];
				elos [max] = tempInt;
				
				String tempStr = usernames [i];
				usernames [i] = usernames [max];
				usernames [max] = tempStr;
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		String eventName = event.getActionCommand();
		
		if (eventName.equals("Find A User"))
		{
			statsMenu.setVisible(false);
			FindAUser findAUserMenu = new FindAUser();
		}
		
		if (eventName.equals("Leaderboard"))
		{
			statsMenu.setVisible(false);
			Leaderboard leaderBoardMenu = new Leaderboard();
		}
		
		if (eventName.equals("Back"))
		{
			statsMenu.setVisible(false);
			Main mainMenu = new Main();
		}
	}
}
