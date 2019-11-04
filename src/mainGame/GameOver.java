package mainGame;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.sound.sampled.*;

import menues.Main;
import menues.Options;
import menues.Play;

public class GameOver implements ActionListener {

	public JFrame gameOver = new JFrame ();
	public JPanel contentPane = new JPanel ();
	
	public JButton mainMenuButton;
	
	Long currentFrame;
	Clip clip;
	AudioInputStream audioIn;
	static String filePath;
	
	public GameOver (String reason, char loser, boolean wasRanked) throws IOException
	{
		ExtraInfo.bTime.stop();
		ExtraInfo.wTime.stop();
		
		try {
			audioIn = AudioSystem.getAudioInputStream(new File ("src/mainGame/victorySound.wav").getAbsoluteFile());
			clip = AudioSystem.getClip();
			clip.open(audioIn);
		} catch (LineUnavailableException | UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
		
		clip.start();
		
		contentPane.setLayout(new BoxLayout (contentPane, BoxLayout.PAGE_AXIS));
		
		JLabel text = new JLabel ();
		
		// displaying the appropriate 
		if (loser == 'w')
		{
			if (reason.equals("mate"))
			{
				text.setText("Black wins due to checkmate!");
			}
			else if (reason.contentEquals("time"))
			{
				text.setText("Black wins due to time!");
			}
			
			if (wasRanked)
			{
				eloChange (loser);
			}
		}
		else if (loser == 'b')
		{
			if (reason.equals("mate"))
			{
				text.setText("White wins due to checkmate!");
			}
			else if (reason.contentEquals("time"))
			{
				text.setText("White wins due to time!");
			}
			
			if (wasRanked)
			{
				eloChange (loser);
			}
		}
		else if (loser == 's')
		{
			text.setText("Stalemate!");
		}
		text.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(text);
		
		mainMenuButton = new JButton();
		mainMenuButton.addActionListener(this);
		mainMenuButton.setActionCommand("Main Menu");
		mainMenuButton.setText("Main Menu");
		mainMenuButton.setPreferredSize(new Dimension(100, 150));
		mainMenuButton.setAlignmentX(mainMenuButton.CENTER_ALIGNMENT);
		contentPane.add(mainMenuButton);
		
		gameOver.setContentPane(contentPane);
		gameOver.setBackground(Options.backgroundColour);
		gameOver.setSize(new Dimension (200, 150));
		gameOver.setVisible(true);
	}
	
	public void eloChange (char loser) throws IOException
	{
		// getting the files of the two relevant players
		File fileW = new File ("src/userInfo/" + Play.playerW + ".txt");
		File fileB = new File ("src/userInfo/" + Play.playerB + ".txt");
		
		// making readers to read the files
		BufferedReader bfW = new BufferedReader(new FileReader (fileW));
		BufferedReader bfB = new BufferedReader(new FileReader (fileB));
		
		// getting the white player's elo
		String strTempW1 = bfW.readLine();
		String strTempW2 = bfW.readLine();
		String strTempW3 = bfW.readLine();
		int wElo = Integer.parseInt(strTempW3);
		
		// getting the black player's elo
		String strTempB1 = bfB.readLine();
		String strTempB2 = bfB.readLine();
		String strTempB3 = bfB.readLine();
		int bElo = Integer.parseInt(strTempB3);
		
		// adjusting elo according to which player won/lost
		if (loser == 'w')
		{
			wElo -= 50;
			bElo += 50;
		}
		else if (loser == 'b')
		{
			bElo -= 50;
			wElo += 50;
		}
		
		strTempW3 = Integer.toString(wElo);
		strTempB3 = Integer.toString(bElo);
		
		// overwriting old files with new data
		try {
			PrintWriter writer = new PrintWriter(fileW);
			writer.println(strTempW1);
			writer.println(strTempW2);
			writer.println(strTempW3);
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			PrintWriter writer = new PrintWriter(fileB);
			writer.println(strTempB1);
			writer.println(strTempB2);
			writer.println(strTempB3);
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		
		String eventName = event.getActionCommand();
		
		if (eventName.equals("Main Menu"))
		{
			gameOver.setVisible(false);
			ExtraInfo.infoFrame.setVisible(false);
			Main mainMenu = new Main ();
		}
		
	}
}
