package menues;

import java.awt.EventQueue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Help implements ActionListener{

	private JFrame frame;

	public Help() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 500, 461);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel titleLabel = new JLabel("How to play Chess:");
		titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		titleLabel.setBounds(155, 0, 177, 23);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(titleLabel);
		
		JTextPane mainTextPane = new JTextPane();
		mainTextPane.setFont(new Font("Tahoma", Font.PLAIN, 10));
		mainTextPane.setBounds(109, 38, 355, 97);
		mainTextPane.setText("Ah, the age old game of chess. Played for centuries all over the globe, Chess is one of the most popular passtimes worldwide. Once you and a friend have created an account, you two may play with each other! After choosing a game mode, the white player (usually) goes first. Choose any piece, and all the possible legal moves it can make will light up. After clicking on the coloured tile, the piece you have previously chosen shall move there. Then, the program shall switch turns, and the next player shall go.");
		mainTextPane.setEditable(false);
		frame.getContentPane().add(mainTextPane);
		
		JLabel generalGuideLabel = new JLabel("General Guide:");
		generalGuideLabel.setFont(new Font("Tahoma", Font.PLAIN, 10));
		generalGuideLabel.setBounds(22, 77, 76, 14);
		frame.getContentPane().add(generalGuideLabel);
		
		JLabel chessPiecesLabel = new JLabel("Chess Pieces:");
		chessPiecesLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		chessPiecesLabel.setBounds(192, 146, 87, 14);
		frame.getContentPane().add(chessPiecesLabel);
		
		JLabel pawnLabel = new JLabel("Pawn");
		pawnLabel.setBounds(38, 216, 38, 14);
		frame.getContentPane().add(pawnLabel);
		
		JLabel bishopLabel = new JLabel("Bishop");
		bishopLabel.setBounds(35, 293, 44, 14);
		frame.getContentPane().add(bishopLabel);
		
		JLabel knightLabel = new JLabel("Knight");
		knightLabel.setBounds(38, 370, 44, 14);
		frame.getContentPane().add(knightLabel);
		
		JLabel queenLabel = new JLabel("Queen");
		queenLabel.setBounds(257, 216, 38, 14);
		frame.getContentPane().add(queenLabel);
		
		JLabel kingLabel = new JLabel("King");
		kingLabel.setBounds(262, 293, 31, 14);
		frame.getContentPane().add(kingLabel);
		
		JLabel rookLabel = new JLabel("Rook");
		rookLabel.setBounds(262, 370, 31, 14);
		frame.getContentPane().add(rookLabel);
		
		JLabel pawnPic = new JLabel("");
		pawnPic.setIcon(new ImageIcon(Help.class.getResource("/pieces/bPawn1.png")));
		pawnPic.setBounds(22, 165, 60, 53);
		frame.getContentPane().add(pawnPic);
		
		JLabel bishopPic = new JLabel("");
		bishopPic.setIcon(new ImageIcon(Help.class.getResource("/pieces/wBishop1.png")));
		bishopPic.setBounds(22, 241, 60, 53);
		frame.getContentPane().add(bishopPic);
		
		JLabel knightPic = new JLabel("");
		knightPic.setIcon(new ImageIcon(Help.class.getResource("/pieces/bKnight1.png")));
		knightPic.setBounds(22, 317, 60, 53);
		frame.getContentPane().add(knightPic);
		
		JLabel queenPic = new JLabel("");
		queenPic.setIcon(new ImageIcon(Help.class.getResource("/pieces/wQueen1.png")));
		queenPic.setBounds(244, 165, 60, 53);
		frame.getContentPane().add(queenPic);
		
		JLabel kingPic = new JLabel("");
		kingPic.setIcon(new ImageIcon(Help.class.getResource("/pieces/bKing1.png")));
		kingPic.setBounds(244, 241, 60, 53);
		frame.getContentPane().add(kingPic);
		
		JLabel rookPic = new JLabel("");
		rookPic.setIcon(new ImageIcon(Help.class.getResource("/pieces/wRook1.png")));
		rookPic.setBounds(244, 317, 60, 53);
		frame.getContentPane().add(rookPic);
		
		JTextPane pawnText = new JTextPane();
		pawnText.setText("The pawn can only move in one direction, and can only take pieces one space diagonally in the direction it moves in.");
		pawnText.setEditable(false);
		pawnText.setFont(new Font("Tahoma", Font.PLAIN, 9));
		pawnText.setBounds(90, 171, 144, 53);
		frame.getContentPane().add(pawnText);
		
		JTextPane bishopText = new JTextPane();
		bishopText.setText("The bishop can move diagonally in any direction.");
		bishopText.setEditable(false);
		bishopText.setFont(new Font("Tahoma", Font.PLAIN, 9));
		bishopText.setBounds(90, 257, 144, 28);
		frame.getContentPane().add(bishopText);
		
		JTextPane knightText = new JTextPane();
		knightText.setText("The knight moves in an 'L' shape, and is the only piece that can jump over other pieces.");
		knightText.setEditable(false);
		knightText.setFont(new Font("Tahoma", Font.PLAIN, 9));
		knightText.setBounds(90, 331, 144, 39);
		frame.getContentPane().add(knightText);
		
		JTextPane queenText = new JTextPane();
		queenText.setText("The Queen is your best piece, and is basically like a Bishop and Rook combined! Keep it safe!");
		queenText.setEditable(false);
		queenText.setFont(new Font("Tahoma", Font.PLAIN, 9));
		queenText.setBounds(307, 179, 144, 39);
		frame.getContentPane().add(queenText);
		
		JTextPane kingText = new JTextPane();
		kingText.setText("The King can only move in a box around itself. If your King is in check and you can't get it out of check, you lose the game!");
		kingText.setEditable(false);
		kingText.setFont(new Font("Tahoma", Font.PLAIN, 9));
		kingText.setBounds(307, 250, 144, 50);
		frame.getContentPane().add(kingText);
		
		JTextPane rookText = new JTextPane();
		rookText.setText("The Rook can move horizontally and vertically in any direction. Considered to be the second most powerful piece in the game, if not for the Queen.");
		rookText.setEditable(false);
		rookText.setFont(new Font("Tahoma", Font.PLAIN, 9));
		rookText.setBounds(307, 317, 144, 61);
		frame.getContentPane().add(rookText);
		
		JButton backButton = new JButton("Back");
		backButton.addActionListener(this);
		backButton.setActionCommand("Back");
		backButton.setBounds(191, 395, 104, 23);
		frame.getContentPane().add(backButton);
		
		frame.setBackground(Options.backgroundColour);
		frame.setResizable(false);
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		
		String eventName = event.getActionCommand();
		
		if (eventName.equals("Back"))
		{
			frame.setVisible(false);
			Main mainMenu = new Main();
		}
	}
}
