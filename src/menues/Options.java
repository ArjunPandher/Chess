package menues;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Options implements ActionListener {

	public static int pieceType = 1;
	public static int menuType = 1;
	
	public static int mins = 99;
	public static int secs = 99;
	
	public static Color boardColour1 = Color.WHITE;
	public static Color boardColour2 = Color.GRAY;
	public static Color backgroundColour = null;
	
	public JFrame optionsMenu = new JFrame ();
	public JPanel opPane = new JPanel ();
	
	public JLabel title;
	public JButton pieceOptions;
	public JButton boardOptions;
	public JButton menuOptions;
	public JButton backButton;
	
	public Options ()
	{
		opPane.setLayout(new BoxLayout (opPane, BoxLayout.PAGE_AXIS));
		opPane.setAlignmentX(opPane.CENTER_ALIGNMENT);
		opPane.setMinimumSize(new Dimension (600, 500));
		opPane.setMaximumSize(new Dimension (600, 500));
		opPane.setBackground(backgroundColour);
		
		title = new JLabel();
		title.setText("Options Menu");
		title.setAlignmentX(title.CENTER_ALIGNMENT);
		opPane.add(title);
		
		pieceOptions = new JButton();
		pieceOptions.addActionListener(this);
		pieceOptions.setActionCommand("Pieces");
		pieceOptions.setText("Chess Pieces");
		pieceOptions.setAlignmentX(Component.CENTER_ALIGNMENT);
		pieceOptions.setAlignmentY(Component.TOP_ALIGNMENT);
		opPane.add(pieceOptions);
		
		boardOptions = new JButton();
		boardOptions.addActionListener(this);
		boardOptions.setActionCommand("Boards");
		boardOptions.setText("Chess Boards");
		boardOptions.setAlignmentX(Component.CENTER_ALIGNMENT);
		boardOptions.setAlignmentY(Component.TOP_ALIGNMENT);
		opPane.add(boardOptions);
		
		menuOptions = new JButton();
		menuOptions.addActionListener(this);
		menuOptions.setActionCommand("Menues");
		menuOptions.setText("Menu Options");
		menuOptions.setAlignmentX(Component.CENTER_ALIGNMENT);
		menuOptions.setAlignmentY(Component.TOP_ALIGNMENT);
		opPane.add(menuOptions);
		
		backButton = new JButton();
		backButton.addActionListener(this);
		backButton.setActionCommand("Back");
		backButton.setText("Back");
		backButton.setAlignmentX(backButton.CENTER_ALIGNMENT);
		backButton.setAlignmentY(backButton.BOTTOM_ALIGNMENT);
		opPane.add(backButton);
		
		optionsMenu.setContentPane(opPane);
		optionsMenu.setSize(400, 300);
		optionsMenu.setBackground(Options.backgroundColour);
		optionsMenu.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		
		String eventName = event.getActionCommand();
		
		if (eventName.contentEquals("Pieces"))
		{
			String choice = "temp";
			JOptionPane boardFrame;
			
			boardFrame = new JOptionPane();
			String[] choices = {"1", "2"};
			choice = (String) JOptionPane.showInputDialog(boardFrame, 
					"Choose a Chess Piece",
					"Choice",
					JOptionPane.QUESTION_MESSAGE,
					null,
					choices,
					choices[0]);
			pieceType = Integer.parseInt(choice);
		}
		
		if (eventName.contentEquals("Boards"))
		{
			String choice = "temp";
			JOptionPane boardFrame;
			
			boardFrame = new JOptionPane();
			String[] choices = {"Tile 1", "Tile 2"};
			choice = (String) JOptionPane.showInputDialog(boardFrame, 
					"What tile would you like to modify?",
					"Choice",
					JOptionPane.QUESTION_MESSAGE,
					null,
					choices,
					choices[0]);
			
			if (choice.equals("Tile 1"))
			{
				String choice1 = "temp";
				JOptionPane boardChoiceFrame;

				boardChoiceFrame = new JOptionPane();
				String[] options = {"White (Normal)", "Red", "Blue", "Yellow", "Cyan", "Dark Gray", "Light Gray", "Magenta", "Orange", "Pink"};
				choice1 = (String) JOptionPane.showInputDialog(boardChoiceFrame, 
						"Choose a colour",
						"Choice",
						JOptionPane.QUESTION_MESSAGE,
						null,
						options,
						options[0]);
				
				if (choice1.equals("White (Normal)"))
				{
					boardColour1 = Color.WHITE;
				}
				else if (choice1.equals("Red"))
				{
					boardColour1 = Color.RED;
				}
				else if (choice1.equals("Blue"))
				{
					boardColour1 = Color.BLUE;
				}
				else if (choice1.equals("Yellow"))
				{
					boardColour1 = Color.YELLOW;
				}
				else if (choice1.equals("Cyan"))
				{
					boardColour1 = Color.CYAN;
				}
				else if (choice1.equals("Dark Gray"))
				{
					boardColour1 = Color.DARK_GRAY;
				}
				else if (choice1.equals("Light Gray"))
				{
					boardColour1 = Color.LIGHT_GRAY;
				}
				else if (choice1.equals("Magenta"))
				{
					boardColour1 = Color.MAGENTA;
				}
				else if (choice1.equals("Orange"))
				{
					boardColour1 = Color.ORANGE;
				}
				else if (choice1.equals("Pink"))
				{
					boardColour1 = Color.PINK;
				}
				
			}
			else if (choice.equals("Tile 2"))
			{
				String choice2 = "temp";
				JOptionPane boardChoiceFrame;

				boardChoiceFrame = new JOptionPane();
				String[] options = {"Gray (Normal)", "Red", "Blue", "Yellow", "Cyan", "Dark Gray", "Light Gray", "Magenta", "Orange", "Pink"};
				choice2 = (String) JOptionPane.showInputDialog(boardChoiceFrame, 
						"Choose a colour",
						"Choice",
						JOptionPane.QUESTION_MESSAGE,
						null,
						options,
						options[0]);
				
				if (choice2.equals("Gray (Normal)"))
				{
					boardColour2 = Color.GRAY;
				}
				else if (choice2.equals("Red"))
				{
					boardColour2 = Color.RED;
				}
				else if (choice2.equals("Blue"))
				{
					boardColour2 = Color.BLUE;
				}
				else if (choice2.equals("Yellow"))
				{
					boardColour2 = Color.YELLOW;
				}
				else if (choice2.equals("Cyan"))
				{
					boardColour2 = Color.CYAN;
				}
				else if (choice2.equals("Dark Gray"))
				{
					boardColour2 = Color.DARK_GRAY;
				}
				else if (choice2.equals("Light Gray"))
				{
					boardColour2 = Color.LIGHT_GRAY;
				}
				else if (choice2.equals("Magenta"))
				{
					boardColour2 = Color.MAGENTA;
				}
				else if (choice2.equals("Orange"))
				{
					boardColour2 = Color.ORANGE;
				}
				else if (choice2.equals("Pink"))
				{
					boardColour2 = Color.PINK;
				}
			}
		}
		
		if (eventName.contentEquals("Menues"))
		{
			String choice;
			JOptionPane backgroundFrame;
			
			backgroundFrame = new JOptionPane();
			String[] choices = {"White", "Gray", "Red", "Blue", "Yellow", "Cyan", "Dark Gray", "Light Gray", "Magenta", "Orange", "Pink"};
			choice = (String) JOptionPane.showInputDialog(backgroundFrame, 
					"What colour would you like to set your menues to?",
					"Choice",
					JOptionPane.QUESTION_MESSAGE,
					null,
					choices,
					choices[0]);
			
			if (choice.equals("White"))
			{
				backgroundColour = Color.WHITE;
			}
			else if (choice.equals("Gray"))
			{
				backgroundColour = Color.GRAY;
			}
			else if (choice.equals("Red"))
			{
				backgroundColour = Color.RED;
			}
			else if (choice.equals("Blue"))
			{
				backgroundColour = Color.BLUE;
			}
			else if (choice.equals("Yellow"))
			{
				backgroundColour = Color.YELLOW;
			}
			else if (choice.equals("Cyan"))
			{
				backgroundColour = Color.CYAN;
			}
			else if (choice.equals("Dark Gray"))
			{
				backgroundColour = Color.DARK_GRAY;
			}
			else if (choice.equals("Light Gray"))
			{
				backgroundColour = Color.LIGHT_GRAY;
			}
			else if (choice.equals("Magenta"))
			{
				backgroundColour = Color.MAGENTA;
			}
			else if (choice.equals("Orange"))
			{
				backgroundColour = Color.ORANGE;
			}
			else if (choice.equals("Pink"))
			{
				backgroundColour = Color.PINK;
			}
		}
		
		if (eventName.equals("Back"))
		{
			optionsMenu.setVisible(false);
			Main mainMenu = new Main();
		}
		
	}
}
