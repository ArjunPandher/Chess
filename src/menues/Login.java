package menues;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

public class Login implements ActionListener{

	public JFrame loginMenu = new JFrame();
	public JPanel loginPane = new JPanel();
	
	public JLabel titleLabel;
	public JLabel whiteLabel;
	public JLabel blackLabel;
	
	public JTextField username1Box;
	public JTextField username2Box;
	public JPasswordField password1Box;
	public JPasswordField password2Box;
	
	public JPanel subPanel;
	
	public JButton playButton;
	public JButton backButton;
	
	public Login ()
	{
		loginPane.setLayout(new BoxLayout (loginPane, BoxLayout.PAGE_AXIS));
		loginPane.setBackground(Options.backgroundColour);
		
		titleLabel = new JLabel();
		titleLabel.setText("Login");
		titleLabel.setAlignmentX(titleLabel.CENTER_ALIGNMENT);
		loginPane.add(titleLabel);
		
		subPanel = new JPanel();
		subPanel.setLayout(new GridLayout (3, 2));
		
		whiteLabel = new JLabel();
		whiteLabel.setText("White Player:");
		whiteLabel.setAlignmentX(whiteLabel.CENTER_ALIGNMENT);
		subPanel.add(whiteLabel);
		
		blackLabel = new JLabel();
		blackLabel.setText("Black Player:");
		blackLabel.setAlignmentX(blackLabel.CENTER_ALIGNMENT);
		subPanel.add(blackLabel);
		
		username1Box = new JTextField("Username");
		subPanel.add(username1Box);
		
		username2Box = new JTextField("Username");
		subPanel.add(username2Box);
		
		password1Box = new JPasswordField("Password");
		password1Box.setEchoChar('*');
		subPanel.add(password1Box);
		
		password2Box = new JPasswordField("Password");
		password2Box.setEchoChar('*');
		subPanel.add(password2Box);
		
		loginPane.add(subPanel);
		
		playButton = new JButton();
		playButton.addActionListener(this);
		playButton.setActionCommand("Play");
		playButton.setText("Play");
		playButton.setPreferredSize(new Dimension(50, 100));
		playButton.setAlignmentX(playButton.CENTER_ALIGNMENT);
		loginPane.add(playButton);
		
		backButton = new JButton();
		backButton.addActionListener(this);
		backButton.setActionCommand("Back");
		backButton.setText("Back");
		backButton.setPreferredSize(new Dimension(50, 100));
		backButton.setAlignmentX(backButton.CENTER_ALIGNMENT);
		loginPane.add(backButton);
		
		loginMenu.setContentPane(loginPane);
		loginMenu.setSize(400, 200);
		loginMenu.setBackground(Options.backgroundColour);
		loginMenu.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		
		String eventName = event.getActionCommand();
		
		if (eventName.equals("Back"))
		{
			loginMenu.setVisible(false);
			Main mainMenu = new Main();
		}
		
		if (eventName.equals("Play"))
		{
			JOptionPane errorFrame = new JOptionPane ();
			
			String username1 = username1Box.getText();
			String username2 = username2Box.getText();
			char[] password1 = password1Box.getPassword();
			char[] password2 = password2Box.getPassword();
			
			if (username1.equals(username2))
			{
				JOptionPane.showMessageDialog(null, 
						"You cannot play against yourself!", 
						"Error Message", 
						JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				File file1 = new File ("src/userInfo/" + username1 + ".txt");
				File file2 = new File ("src/userInfo/" + username2 + ".txt");
				
				BufferedReader br1 = null;
				try {
					br1 = new BufferedReader(new FileReader (file1));
				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(null, 
							"White Player's username does not exist", 
							"Error Message", 
							JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
				
				BufferedReader br2 = null;
				try {
					br2 = new BufferedReader(new FileReader (file2));
				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(null, 
							"Black Player's username does not exist", 
							"Error Message", 
							JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
				
				String strTemp1 = null;
				String strTemp2 = null;;
	
				// reading to the second line for both strings
				try {
					strTemp1 = br1.readLine();
					strTemp1 = br1.readLine();
	
					strTemp2 = br2.readLine();
					strTemp2 = br2.readLine();
				} catch (IOException e) {
					System.out.println("THIS SHOULDN'T HAPPEN EVER");
					e.printStackTrace();
				}
	
				if (strTemp1.equals(new String (password1)))
				{
					if (strTemp2.equals(new String (password2)))
					{
						loginMenu.setVisible(false);
						Play playMenu = new Play(username1, username2);
					}
					else
					{
						JOptionPane.showMessageDialog(null, 
								"Black Player's password is incorrect", 
								"Error Message", 
								JOptionPane.ERROR_MESSAGE);
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, 
							"White Player's password is incorrect", 
							"Error Message", 
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
}
