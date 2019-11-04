package menues;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Arrays;

import javax.swing.*;

public class Sign implements ActionListener{

	public JFrame signMenu = new JFrame();
	public JPanel signPane = new JPanel();

	public JPanel subPanel;

	public JLabel titleLabel;
	public JLabel enterUsername;
	public JLabel enterPassword;
	public JLabel confirmPassword;

	public JTextField usernameBox;
	public JPasswordField password1Box;
	public JPasswordField password2Box;

	public JButton set;
	public JButton back;

	public Sign ()
	{
		signPane.setLayout(new BoxLayout (signPane, BoxLayout.PAGE_AXIS));
		signPane.setBackground(Options.backgroundColour);

		titleLabel = new JLabel();
		titleLabel.setText("Sign Up");
		titleLabel.setAlignmentX(titleLabel.CENTER_ALIGNMENT);
		signPane.add(titleLabel);

		subPanel = new JPanel();
		subPanel.setLayout(new GridLayout (3, 2));

		enterUsername = new JLabel();
		enterUsername.setText("Enter your username:");
		enterUsername.setAlignmentX(enterUsername.CENTER_ALIGNMENT);
		subPanel.add(enterUsername);

		usernameBox = new JTextField(20);
		subPanel.add(usernameBox);

		enterPassword = new JLabel();
		enterPassword.setText("Enter your password:");
		enterPassword.setAlignmentX(enterPassword.CENTER_ALIGNMENT);
		subPanel.add(enterPassword);

		password1Box = new JPasswordField(20);
		password1Box.setEchoChar('*');
		subPanel.add(password1Box);

		confirmPassword = new JLabel();
		confirmPassword.setText("Confirm your password:");
		confirmPassword.setAlignmentX(confirmPassword.CENTER_ALIGNMENT);
		subPanel.add(confirmPassword);

		password2Box = new JPasswordField(20);
		password2Box.setEchoChar('*');
		subPanel.add(password2Box);

		signPane.add(subPanel);

		set = new JButton();
		set.addActionListener(this);
		set.setActionCommand("Set");
		set.setText("Set");
		set.setPreferredSize(new Dimension (50, 100));
		set.setAlignmentX(set.CENTER_ALIGNMENT);
		signPane.add(set);

		back = new JButton();
		back.addActionListener(this);
		back.setActionCommand("Back");
		back.setText("Back");
		back.setPreferredSize(new Dimension (50, 100));
		back.setAlignmentX(back.CENTER_ALIGNMENT);
		signPane.add(back);

		signMenu.setContentPane(signPane);
		signMenu.setSize(400, 200);
		signMenu.setBackground(Options.backgroundColour);
		signMenu.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		String eventName = event.getActionCommand();
		String username = usernameBox.getText();
		char[] password1 = password1Box.getPassword();
		char[] password2 = password2Box.getPassword();

		if (eventName.equals("Back"))
		{
			signMenu.setVisible(false);
			Main mainMenu = new Main();
		}

		if (eventName.equals("Set"))
		{
			JOptionPane errorFrame = new JOptionPane ();
			if (username.contains(" ") || username.contains(".") || username.contains("`") || username.contains(":"))
			{
				JOptionPane.showMessageDialog(null, 
						"Please do not use special symbols or spaces in your username.", 
						"Error Message", 
						JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				File file = new File ("src/userInfo/" + username + ".txt");
				if (file.exists())
				{
					JOptionPane.showMessageDialog(null, 
							"Username already exists.", 
							"Error Message", 
							JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					if (password1.length <= 3)
					{
						JOptionPane.showMessageDialog(null, 
								"Your password must be at least 4 characters long", 
								"Error Message", 
								JOptionPane.ERROR_MESSAGE);
					}
					else
					{
						for (int i = 0; i < password1.length; i++)
						{
							if (password1 [i] == ' ')
							{
								JOptionPane.showMessageDialog(null, 
										"You cannot have spaces in your password", 
										"Error Message", 
										JOptionPane.ERROR_MESSAGE);
								return;
							}
						}
						if (Arrays.equals(password1, password2))
						{
							try {
								PrintWriter writer = new PrintWriter(file);
								writer.println(username);
								writer.println(password1);
								writer.println("1000");
								writer.close();
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							}
							JOptionPane.showMessageDialog(null, 
									"New Account Created!", 
									"Notification",
									JOptionPane.PLAIN_MESSAGE);
							signMenu.setVisible(false);
							Main mainMenu = new Main ();
						}
						else
						{
							System.out.println(password1);
							System.out.println(password2);
							JOptionPane.showMessageDialog(null, 
									"Passwords do not match", 
									"Error Message", 
									JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		}
	}
}

/*
	String s = "temp";
	JOptionPane promoteFrame;
	do
	{
		promoteFrame = new JOptionPane();
		String[] options = {"Queen", "Knight", "Rook", "Bishop"};
		s = (String) JOptionPane.showInputDialog(promoteFrame, 
				"What piece would you like to make the pawn?",
				"Choice",
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				options[0]);
	} while (s == null || (!s.equalsIgnoreCase("Queen") && !s.equalsIgnoreCase("Knight") && !s.equalsIgnoreCase("Rook") && !s.equalsIgnoreCase("Bishop")));
 */

