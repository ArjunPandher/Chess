package menues;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Start {

	public JFrame startFrame = new JFrame();
	public JPanel startPanel = new JPanel();
	
	public JLabel iconLabel;
	
	public Icon startPicture = new ImageIcon("src/menues/StartScreen.jpg");
	
	public Timer menuTimer;
	
	public static void main(String[] args) {
		runGUI();
	}

	public static void runGUI()
	{
		Start startScreen = new Start();
	}
	
	public Start ()
	{
		startPanel.setLayout(new FlowLayout());
		
		iconLabel = new JLabel();
		iconLabel.setIcon(startPicture);
		startPanel.add(iconLabel);
		
		startFrame.setContentPane(startPanel);
		startFrame.setSize(630, 388);
		startFrame.setResizable(false);
		startFrame.setVisible(true);
		
		menuTimer = new Timer (5000, listener);
		menuTimer.start();
	}
	
	ActionListener listener = new ActionListener () {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			menuTimer.stop();
			startFrame.setVisible(false);
			Main mainMenu = new Main();
		}
		
	};
}
