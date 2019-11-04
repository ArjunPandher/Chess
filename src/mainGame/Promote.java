package mainGame;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import menues.Options;

public class Promote implements ActionListener{

	public JFrame promoteFrame = new JFrame ();
	public JPanel contentPane = new JPanel ();
	
	public String gameTypePass;
	public String pieces [] = {"queen", "rook", "bishop", "knight"};
	public JComboBox box;
	
	public Promote (String gameType)
	{
		gameTypePass = gameType;
		
		contentPane.setLayout(new BoxLayout (contentPane, BoxLayout.PAGE_AXIS));
		contentPane.setBackground(Options.backgroundColour);
		
		JLabel title = new JLabel ();
		title.setText("Choose a piece:");
		title.setAlignmentX(title.CENTER_ALIGNMENT);
		contentPane.add(title);
		
		box = new JComboBox (pieces);
		box.setSelectedIndex(0);
		contentPane.add(box);
		
		JButton set = new JButton ();
		set.addActionListener(this);
		set.setActionCommand("Set");
		set.setText("Set");
		set.setPreferredSize(new Dimension(25, 50));
		set.setAlignmentX(set.CENTER_ALIGNMENT);
		contentPane.add(set);
		
		promoteFrame.setContentPane(contentPane);
		promoteFrame.setSize(new Dimension (200, 150));
		promoteFrame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		String eventName = event.getActionCommand();
		
		if (eventName.equals("Set"))
		{
			if (gameTypePass.equals("Chess"))
			{
				Chess.newPieceType = pieces [box.getSelectedIndex()];
			}
		}
		
	}
}
