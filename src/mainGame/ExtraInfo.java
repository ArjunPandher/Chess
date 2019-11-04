package mainGame;

import java.awt.event.*;
import java.text.*;
import java.awt.*;
import javax.swing.*;

import menues.Options;

public class ExtraInfo {

	public static Timer bTime;
	public static Timer wTime;
	
	public String gameTypePub;
	
	public static int bMins;
	public static int wMins;
	public static int bSec;
	public static int wSec;
	
	public Icon bIcon = new ImageIcon("src/pieces/bKnight" + Options.pieceType + ".png");;
	public Icon wIcon = new ImageIcon("src/pieces/wKnight" + Options.pieceType + ".png");;
	public JLabel bIconLabel;
	public JLabel wIconLabel;
	
	public static JLabel bPoints;
	public static JLabel wPoints;
	
	public JLabel bTimeLabel;
	public JLabel wTimeLabel;
	
	public static JLabel activePlayerTurn;
	
	public static JFrame infoFrame = new JFrame ();
	public static JPanel contentPane = new JPanel ();
	
	public static DecimalFormat df1 = new DecimalFormat ("00");
	
	public ExtraInfo (String gameType)
	{
		gameTypePub = gameType;
	
		bMins = Options.mins;
		wMins = Options.mins;
		bSec = Options.secs;
		wSec = Options.secs;
		
		contentPane.setLayout(new FlowLayout());
		contentPane.setBackground(Options.backgroundColour);
		
		wIconLabel = new JLabel (wIcon);
		contentPane.add(wIconLabel);
		
		wPoints = new JLabel ();
		wPoints.setText("00");
		contentPane.add(wPoints);
		
		wTimeLabel = new JLabel ();
		wTimeLabel.setText(df1.format(wMins) + ":" + df1.format(wSec));
		contentPane.add(wTimeLabel);
		
		activePlayerTurn = new JLabel ();
		activePlayerTurn.setText("White Player's turn!");
		contentPane.add(activePlayerTurn);
		
		bTimeLabel = new JLabel ();
		bTimeLabel.setText(df1.format(bMins) + ":" + df1.format(bSec));
		contentPane.add(bTimeLabel);
		
		bPoints = new JLabel ();
		bPoints.setText("00");
		contentPane.add(bPoints);
		
		bIconLabel = new JLabel (bIcon);
		contentPane.add(bIconLabel);
		
		infoFrame.setContentPane(contentPane);
		infoFrame.setSize(640, 100);
		infoFrame.setBackground(Options.backgroundColour);
		infoFrame.setVisible(true);
		
		bTime = new Timer (1000, countActionB);
		wTime = new Timer (1000, countActionW);
	}
	
	public static void updatePoints (int wPoint, int bPoint)
	{
		wPoints.setText(df1.format(wPoint));
		bPoints.setText(df1.format(bPoint));
	}
	
	ActionListener countActionB = new ActionListener () {
		public void actionPerformed (ActionEvent countDownB)
		{
			if (bSec == 0)
			{
				if (bMins > 0)
				{
					bMins--;
					bSec = 59;
				}
				else
				{
					if (gameTypePub.equals("Chess"))
					{
						Chess.endGame("time", 'b');
						bTime.stop();
					}
					else if (gameTypePub.equals("Chess960"))
					{
						Chess960.endGame("time", 'b');
						bTime.stop();
					}
					else if (gameTypePub.equals("Dunsay"))
					{
						Dunsay.endGame("time", 'b');
						bTime.stop();
					}
					else if (gameTypePub.equals("EndGame"))
					{
						EndGame.endGame("time", 'b');
						bTime.stop();
					}
					else if (gameTypePub.equals("Horde"))
					{
						Horde.endGame("time", 'b');
						bTime.stop();
					}
					else if (gameTypePub.equals("LightBrigade"))
					{
						LightBrigade.endGame("time", 'b');
						bTime.stop();
					}
					else if (gameTypePub.equals("ReallyBad"))
					{
						ReallyBad.endGame("time", 'b');
						bTime.stop();
					}
					else if (gameTypePub.equals("Revolt"))
					{
						Revolt.endGame("time", 'b');
						bTime.stop();
					}
					else if (gameTypePub.equals("UpsideDown"))
					{
						UpsideDown.endGame("time", 'b');
						bTime.stop();
					}
					else if (gameTypePub.equals("Weak"))
					{
						Weak.endGame("time", 'b');
						bTime.stop();
					}
				}
			}
			else
			{
				bSec--;
			}
			
			bTimeLabel.setText(df1.format(bMins) + ":" + df1.format(bSec));
		}
	};
	
	ActionListener countActionW = new ActionListener () {
		public void actionPerformed (ActionEvent countDownW)
		{
			if (wSec == 0)
			{
				if (wMins > 0)
				{
					wMins--;
					wSec = 59;
				}
				else
				{
					if (gameTypePub.equals("Chess"))
					{
						Chess.endGame("time", 'w');
						wTime.stop();
					}
					else if (gameTypePub.equals("Chess960"))
					{
						Chess960.endGame("time", 'w');
						wTime.stop();
					}
					else if (gameTypePub.equals("Dunsay"))
					{
						Dunsay.endGame("time", 'w');
						wTime.stop();
					}
					else if (gameTypePub.equals("EndGame"))
					{
						EndGame.endGame("time", 'w');
						wTime.stop();
					}
					else if (gameTypePub.equals("Horde"))
					{
						Horde.endGame("time", 'w');
						wTime.stop();
					}
					else if (gameTypePub.equals("LightBrigade"))
					{
						LightBrigade.endGame("time", 'w');
						wTime.stop();
					}
					else if (gameTypePub.equals("ReallyBad"))
					{
						ReallyBad.endGame("time", 'w');
						wTime.stop();
					}
					else if (gameTypePub.equals("Revolt"))
					{
						Revolt.endGame("time", 'w');
						wTime.stop();
					}
					else if (gameTypePub.equals("UpsideDown"))
					{
						UpsideDown.endGame("time", 'w');
						wTime.stop();
					}
					else if (gameTypePub.equals("Weak"))
					{
						Weak.endGame("time", 'w');
						wTime.stop();
					}
				}
			}
			else
			{
				wSec--;
			}
			wTimeLabel.setText(df1.format(wMins) + ":" + df1.format(wSec));
		}
	};
}
