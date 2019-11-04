package menues;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

public class FindAUser implements ActionListener{

	public JFrame findAUserMenu = new JFrame();
	public JPanel findAUserPane = new JPanel();
	
	public JLabel titleLabel;
	
	public JTextField entry;
	
	public JButton checkButton;
	public JButton backButton;
	
	public FindAUser ()
	{
		findAUserPane.setLayout(new BoxLayout (findAUserPane, BoxLayout.PAGE_AXIS));
		findAUserPane.setBackground(Options.backgroundColour);
		
		titleLabel = new JLabel();
		titleLabel.setText("Enter a username below:");
		titleLabel.setAlignmentX(titleLabel.CENTER_ALIGNMENT);
		findAUserPane.add(titleLabel);
		
		entry = new JTextField();
		findAUserPane.add(entry);
		
		checkButton = new JButton();
		checkButton.addActionListener(this);
		checkButton.setActionCommand("Check");
		checkButton.setText("Check");
		checkButton.setAlignmentX(checkButton.CENTER_ALIGNMENT);
		findAUserPane.add(checkButton);
		
		backButton = new JButton();
		backButton.addActionListener(this);
		backButton.setActionCommand("Back");
		backButton.setText("Back");
		backButton.setAlignmentX(backButton.CENTER_ALIGNMENT);
		findAUserPane.add(backButton);
		
		findAUserMenu.setContentPane(findAUserPane);
		findAUserMenu.setSize(300, 150);
		findAUserMenu.setBackground(Options.backgroundColour);
		findAUserMenu.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		
		String eventName = event.getActionCommand();
		
		if (eventName.equals("Back"))
		{
			findAUserMenu.setVisible(false);
			try {
				Stats statsMenu = new Stats();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if (eventName.equals("Check"))
		{
			JOptionPane displayPane = new JOptionPane();
			
			File userFile = new File ("src/userInfo/" + entry.getText() + ".txt");
			
			BufferedReader br = null;
			
			try 
			{
				br = new BufferedReader (new FileReader (userFile));
			} 
			catch (FileNotFoundException e) 
			{
				JOptionPane.showMessageDialog(null, 
						"Username does not exist", 
						"Error Message", 
						JOptionPane.ERROR_MESSAGE);
			}
			
			try 
			{
				br.readLine();
				br.readLine();
			} 
			catch (IOException e) 
			{}
			
			try 
			{
				JOptionPane.showMessageDialog(null, 
						entry.getText() + " has an elo of " + br.readLine(), 
						"Display message",
						JOptionPane.PLAIN_MESSAGE);
			} 
			catch (IOException e) 
			{}
		}
	}
}
