/*
 * Upside Down Chess
 * The starting positions of both players seem normal, but the Pawns of both players are one space away from promoting!
 * HINT: Start off the game with a Knight movement.
 */

package mainGame;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

import menues.*;

public class UpsideDown implements ActionListener {

	// image icons imported for each player's pieces, pretty self explanatory
	public Icon bQueen = new ImageIcon("src/pieces/bQueen" + Options.pieceType + ".png");
	public Icon bKing = new ImageIcon("src/pieces/bKing" + Options.pieceType + ".png");
	public Icon bRook = new ImageIcon("src/pieces/bRook" + Options.pieceType + ".png");	
	public Icon bBishop = new ImageIcon("src/pieces/bBishop" + Options.pieceType + ".png");
	public Icon bKnight = new ImageIcon("src/pieces/bKnight" + Options.pieceType + ".png");
	public Icon bPawn = new ImageIcon("src/pieces/bPawn" + Options.pieceType + ".png");

	public Icon wQueen = new ImageIcon("src/pieces/wQueen" + Options.pieceType + ".png");
	public Icon wKing = new ImageIcon("src/pieces/wKing" + Options.pieceType + ".png");
	public Icon wRook = new ImageIcon("src/pieces/wRook" + Options.pieceType + ".png");	
	public Icon wBishop = new ImageIcon("src/pieces/wBishop" + Options.pieceType + ".png");
	public Icon wKnight = new ImageIcon("src/pieces/wKnight" + Options.pieceType + ".png");
	public Icon wPawn = new ImageIcon("src/pieces/wPawn" + Options.pieceType + ".png");

	// colours for the board tiles
	public Color colour1 = Options.boardColour1;
	public Color colour2 = Options.boardColour2;

	// Java swing setup
	public static JFrame chessGame = new JFrame();
	public JPanel gamePanel = new JPanel();

	public static ChessPiece boardPieces [] [] = new ChessPiece [8] [8];	// Data for the board
	public static JButton boardTiles [] [] = new JButton [8] [8];			// Visual representation for the board

	public JPanel boardPanel;
	public JPanel statsPanel;

	// temporary x and y values for where pieces are on the board
	public int xInt = -1;
	public int yInt = -1;
	public int xIntKill = -1;
	public int yIntKill = -1;
	public int xIntMove = -1;
	public int yIntMove = -1;

	public int count = 0;		// counting value for number of times the king is in check
	public int kingX = -1;		// saved value for the king's x position
	public int kingY = -1;		// saved value for the king's y position

	// points for both players
	public int wPoints = 0;
	public int bPoints = 0;

	// temporary chess pieces for checking if a move is legal
	public ChessPiece temp;

	// current player's turn
	public char playerTurn = 'w';

	// type of game being played
	public String gameType = "UpsideDown";
	// the type of piece the player chooses to promote to
	public static String newPieceType = "";
	// for preventing the program from continuing until the player has chosen a piece
	public static final Object monitor = new Object();
	public static boolean monitorState = false;

	public boolean check = false;				// if true, king is in check
	public boolean checkMate = false;			// if true, king is in checkmate
	public boolean staleMate = false;			// if true, king is in stalemate
	public static boolean wasRanked = false;	// if true, the game is a ranked game of chess

	int bugTest = 0;

	public UpsideDown ()
	{
		// the following code is all about setting up the board
		gamePanel.setLayout(new BoxLayout (gamePanel, BoxLayout.PAGE_AXIS));

		boardPanel = new JPanel ();
		boardPanel.setLayout(new GridLayout (8, 8));

		for (int i = 0; i < boardTiles.length; i++)
		{
			for (int j = 0; j < boardTiles[i].length; j++)
			{
				// properly setting up all JButtons
				boardTiles [i] [j] = new JButton();
				boardTiles [i] [j].setSize(60, 60);
				boardTiles [i] [j].setActionCommand(j + "x" + i);
				boardTiles [i] [j].addActionListener(this);

				// setting the tiles to alternate between the two colours the user chooses
				if (i % 2 == 1)
				{
					if (j % 2 == 1)
					{
						boardTiles[i] [j].setBackground(colour1);
					}
					else
					{
						boardTiles[i] [j].setBackground(colour2);
					}
				}
				else
				{
					if (j % 2 == 0)
					{
						boardTiles[i] [j].setBackground(colour1);
					}
					else
					{
						boardTiles[i] [j].setBackground(colour2);
					}
				}
				// adding the tile to the jPanel
				boardPanel.add(boardTiles [i] [j]);
			}
		}

		// setting the board up with the setup for Upside-Down Chess
		
		boardPieces[7] [0] = new ChessPiece ('b', "rook");
		boardTiles [7] [0].setIcon(bRook);

		boardPieces[7] [1] = new ChessPiece ('b', "knight");
		boardTiles [7] [1].setIcon(bKnight);

		boardPieces[7] [2] = new ChessPiece ('b', "bishop");
		boardTiles [7] [2].setIcon(bBishop);

		boardPieces[7] [3] = new ChessPiece ('b', "queen");
		boardTiles [7] [3].setIcon(bQueen);

		boardPieces[7] [4] = new ChessPiece ('b', "king");
		boardTiles [7] [4].setIcon(bKing);

		boardPieces[7] [5] = new ChessPiece ('b', "bishop");
		boardTiles [7] [5].setIcon(bBishop);

		boardPieces[7] [6] = new ChessPiece ('b', "knight");
		boardTiles [7] [6].setIcon(bKnight);

		boardPieces[7] [7] = new ChessPiece ('b', "rook");
		boardTiles [7] [7].setIcon(bRook);


		for (int x = 0; x < boardPieces.length; x++)
		{
			boardPieces[6] [x] = new ChessPiece ('b', "pawn");
			boardTiles [6] [x].setIcon(bPawn);

			boardPieces[1] [x] = new ChessPiece ('w', "pawn");
			boardTiles [1] [x].setIcon(wPawn);
		}


		boardPieces[0] [0] = new ChessPiece ('w', "rook");
		boardTiles [0] [0].setIcon(wRook);

		boardPieces[0] [1] = new ChessPiece ('w', "knight");
		boardTiles [0] [1].setIcon(wKnight);

		boardPieces[0] [2] = new ChessPiece ('w', "bishop");
		boardTiles [0] [2].setIcon(wBishop);

		boardPieces[0] [3] = new ChessPiece ('w', "queen");
		boardTiles [0] [3].setIcon(wQueen);

		boardPieces[0] [4] = new ChessPiece ('w', "king");
		boardTiles [0] [4].setIcon(wKing);

		boardPieces[0] [5] = new ChessPiece ('w', "bishop");
		boardTiles [0] [5].setIcon(wBishop);

		boardPieces[0] [6] = new ChessPiece ('w', "knight");
		boardTiles [0] [6].setIcon(wKnight);

		boardPieces[0] [7] = new ChessPiece ('w', "rook");
		boardTiles [0] [7].setIcon(wRook);

		// adding all of the buttons to the content pane
		gamePanel.add(boardPanel);

		// final JFrame setup
		chessGame.setContentPane(gamePanel);
		chessGame.setSize(640, 640);
		chessGame.setResizable(false);
		chessGame.setVisible(true);

		ExtraInfo information = new ExtraInfo (gameType);
		ExtraInfo.wTime.start();
	}

	// looking to see if there were any pawns that could possibly have been taken by an enpassant capture
	// last turn, and setting the enpassant variable back to false
	private void enPassantReset ()
	{
		for (int y = 0; y < boardPieces.length; y++)
		{
			for (int x = 0; x < boardPieces.length; x++)
			{
				try
				{
					// setting the enpassant value of all pawns to false
					if (boardPieces [y] [x].owner == playerTurn && boardPieces [y] [x].type.equals("pawn"))
					{
						boardPieces [y] [x].enpassant = false;
					}
				}
				catch (NullPointerException NPEx)
				{}
			}
		}
	}

	// clears all green tiles that have been lit up by clicking on a piece
	private void greenTileClear()
	{
		for (int i = 0; i < boardTiles.length; i++)
		{
			for (int j = 0; j < boardTiles [i].length; j++)
			{
				// setting tiles back to their original colour
				if (i % 2 == 1)
				{
					if (j % 2 == 1)
					{
						boardTiles[i] [j].setBackground(colour1);
					}
					else
					{
						boardTiles[i] [j].setBackground(colour2);
					}
				}
				else
				{
					if (j % 2 == 0)
					{
						boardTiles[i] [j].setBackground(colour1);
					}
					else
					{
						boardTiles[i] [j].setBackground(colour2);
					}
				}

				// resetting action commands
				boardTiles[i] [j].setActionCommand(null);
				boardTiles[i] [j].setActionCommand(j + "x" + i);
			}
		}
	}

	// lights up tiles the pieces can move to
	private void tileMoveShow ()
	{

		// if the piece moves like a king
		if (boardPieces [yInt] [xInt].kiMove == true)
		{
			kingMovePiece();
		}

		// if the piece moves like a rook
		if (boardPieces [yInt] [xInt].rMove == true)
		{
			rookMovePiece();
		}

		// if the piece moves like a bishop
		if (boardPieces [yInt] [xInt].bMove == true)
		{
			bishopMovePiece();
		}

		// if the piece moves like a knight
		if (boardPieces [yInt] [xInt].knMove == true)
		{
			knightMovePiece();
		}

		// if the piece moves like a pawn
		if (boardPieces [yInt] [xInt].pMove == true)
		{
			pawnMovePiece();
		}
	}

	// if the piece moves like a king
	private void kingMovePiece ()
	{
		// down
		// catching index out of bounds exceptions
		try 
		{
			// if there is a piece in the king's way
			if (boardPieces [yInt + 1] [xInt] != null)
			{
				// if there is an enemy piece in the king's way, the king can take
				if (boardPieces [yInt + 1] [xInt].owner != boardPieces [yInt] [xInt].owner)
				{
					// pretending that the piece was actually moved, then seeing if the king is in check
					temp = boardPieces [yInt + 1] [xInt];
					boardPieces [yInt + 1] [xInt] = boardPieces [yInt] [xInt];
					boardPieces [yInt] [xInt] = null;
					findKing();
					// if the king is not in check					
					if (!threatCheck (kingX, kingY))
					{
						boardTiles [yInt + 1] [xInt].setBackground(Color.GREEN);
						boardTiles [yInt + 1] [xInt].setActionCommand("k" + (xInt) + "x" + (yInt + 1));
						// moving the piece back to the original point
						boardPieces [yInt] [xInt] = boardPieces [yInt + 1] [xInt];
						boardPieces [yInt + 1] [xInt] = temp;
					}
					else
					{
						// moving the piece back to the original point
						boardPieces [yInt] [xInt] = boardPieces [yInt + 1] [xInt];
						boardPieces [yInt + 1] [xInt] = temp;
					}
				}
			}
			// if there isn't a piece in the king's way
			else
			{
				// pretending that the piece was actually moved, then seeing if the king is in check
				temp = boardPieces [yInt] [xInt];
				boardPieces [yInt + 1] [xInt] = boardPieces [yInt] [xInt];
				boardPieces [yInt] [xInt] = null;
				findKing();
				// if the king is not in check
				if (!threatCheck (kingX, kingY))
				{
					boardTiles [yInt + 1] [xInt].setBackground(Color.GREEN);
					boardTiles [yInt + 1] [xInt].setActionCommand("m" + (xInt) + "x" + (yInt + 1));
					// moving the piece back to the original point
					boardPieces [yInt] [xInt] = temp;
					boardPieces [yInt + 1] [xInt] = null;
				}
				else
				{
					// moving the piece back to the original point
					boardPieces [yInt] [xInt] = temp;
					boardPieces [yInt + 1] [xInt] = null;
				}
			}
		} 
		catch (ArrayIndexOutOfBoundsException AIOOBEx)
		{}

		// up
		// catching index out of bounds exceptions
		try 
		{
			// if there is a piece in the king's way
			if (boardPieces [yInt - 1] [xInt] != null)
			{
				// if there is an enemy piece in the king's way, the king can take
				if (boardPieces [yInt - 1] [xInt].owner != boardPieces [yInt] [xInt].owner)
				{
					// pretending that the piece was actually moved, then seeing if the king is in check
					temp = boardPieces [yInt - 1] [xInt];
					boardPieces [yInt - 1] [xInt] = boardPieces [yInt] [xInt];
					boardPieces [yInt] [xInt] = null;
					findKing();
					// if the king is not in check					
					if (!threatCheck (kingX, kingY))
					{
						boardTiles [yInt - 1] [xInt].setBackground(Color.GREEN);
						boardTiles [yInt - 1] [xInt].setActionCommand("k" + (xInt) + "x" + (yInt - 1));
						// moving the piece back to the original point
						boardPieces [yInt] [xInt] = boardPieces [yInt - 1] [xInt];
						boardPieces [yInt - 1] [xInt] = temp;
					}
					else
					{
						// moving the piece back to the original point
						boardPieces [yInt] [xInt] = boardPieces [yInt - 1] [xInt];
						boardPieces [yInt - 1] [xInt] = temp;
					}
				}
			}
			// if there isn't a piece in the king's way
			else
			{
				// pretending that the piece was actually moved, then seeing if the king is in check
				temp = boardPieces [yInt] [xInt];
				boardPieces [yInt - 1] [xInt] = boardPieces [yInt] [xInt];
				boardPieces [yInt] [xInt] = null;
				findKing();
				// if the king is not in check
				if (!threatCheck (kingX, kingY))
				{
					boardTiles [yInt - 1] [xInt].setBackground(Color.GREEN);
					boardTiles [yInt - 1] [xInt].setActionCommand("m" + (xInt) + "x" + (yInt - 1));
					// moving the piece back to the original point
					boardPieces [yInt] [xInt] = temp;
					boardPieces [yInt - 1] [xInt] = null;
				}
				else
				{
					// moving the piece back to the original point
					boardPieces [yInt] [xInt] = temp;
					boardPieces [yInt - 1] [xInt] = null;
				}
			}
		}
		catch (ArrayIndexOutOfBoundsException AIOOBEx)
		{}

		// right
		// catching index out of bounds exceptions
		try 
		{
			// if there is a piece in the king's way
			if (boardPieces [yInt] [xInt + 1] != null)
			{
				// if there is an enemy piece in the king's way, the king can take
				if (boardPieces [yInt] [xInt + 1].owner != boardPieces [yInt] [xInt].owner)
				{
					// pretending that the piece was actually moved, then seeing if the king is in check
					temp = boardPieces [yInt] [xInt + 1];
					boardPieces [yInt] [xInt + 1] = boardPieces [yInt] [xInt];
					boardPieces [yInt] [xInt] = null;
					findKing();
					// if the king is not in check					
					if (!threatCheck (kingX, kingY))
					{
						boardTiles [yInt] [xInt + 1].setBackground(Color.GREEN);
						boardTiles [yInt] [xInt + 1].setActionCommand("k" + (xInt + 1) + "x" + (yInt));
						// moving the piece back to the original point
						boardPieces [yInt] [xInt] = boardPieces [yInt] [xInt + 1];
						boardPieces [yInt] [xInt + 1] = temp;
					}
					else
					{
						// moving the piece back to the original point
						boardPieces [yInt] [xInt] = boardPieces [yInt] [xInt + 1];
						boardPieces [yInt] [xInt + 1] = temp;
					}
				}
			}
			// if there isn't a piece in the king's way
			else
			{
				// pretending that the piece was actually moved, then seeing if the king is in check
				temp = boardPieces [yInt] [xInt];
				boardPieces [yInt] [xInt + 1] = boardPieces [yInt] [xInt];
				boardPieces [yInt] [xInt] = null;
				findKing();
				// if the king is not in check
				if (!threatCheck (kingX, kingY))
				{
					boardTiles [yInt] [xInt + 1].setBackground(Color.GREEN);
					boardTiles [yInt] [xInt + 1].setActionCommand("m" + (xInt + 1) + "x" + (yInt));
					// moving the piece back to the original point
					boardPieces [yInt] [xInt] = temp;
					boardPieces [yInt] [xInt + 1] = null;
				}
				else
				{
					// moving the piece back to the original point
					boardPieces [yInt] [xInt] = temp;
					boardPieces [yInt] [xInt + 1] = null;
				}
			}
		} 
		catch (ArrayIndexOutOfBoundsException AIOOBEx)
		{}

		// left
		// catching index out of bounds exceptions
		try 
		{
			// if there is a piece in the king's way
			if (boardPieces [yInt] [xInt - 1] != null)
			{
				// if there is an enemy piece in the king's way, the king can take
				if (boardPieces [yInt] [xInt - 1].owner != boardPieces [yInt] [xInt].owner)
				{
					// pretending that the piece was actually moved, then seeing if the king is in check
					temp = boardPieces [yInt] [xInt - 1];
					boardPieces [yInt] [xInt - 1] = boardPieces [yInt] [xInt];
					boardPieces [yInt] [xInt] = null;
					findKing();
					// if the king is not in check					
					if (!threatCheck (kingX, kingY))
					{
						boardTiles [yInt] [xInt - 1].setBackground(Color.GREEN);
						boardTiles [yInt] [xInt - 1].setActionCommand("k" + (xInt - 1) + "x" + (yInt));
						// moving the piece back to the original point
						boardPieces [yInt] [xInt] = boardPieces [yInt] [xInt - 1];
						boardPieces [yInt] [xInt - 1] = temp;
					}
					else
					{
						// moving the piece back to the original point
						boardPieces [yInt] [xInt] = boardPieces [yInt] [xInt - 1];
						boardPieces [yInt] [xInt - 1] = temp;
					}
				}
			}
			// if there isn't a piece in the king's way
			else
			{
				// pretending that the piece was actually moved, then seeing if the king is in check
				temp = boardPieces [yInt] [xInt];
				boardPieces [yInt] [xInt - 1] = boardPieces [yInt] [xInt];
				boardPieces [yInt] [xInt] = null;
				findKing();
				// if the king is not in check
				if (!threatCheck (kingX, kingY))
				{
					boardTiles [yInt] [xInt - 1].setBackground(Color.GREEN);
					boardTiles [yInt] [xInt - 1].setActionCommand("m" + (xInt - 1) + "x" + (yInt));
					// moving the piece back to the original point
					boardPieces [yInt] [xInt] = temp;
					boardPieces [yInt] [xInt - 1] = null;
				}
				else
				{
					// moving the piece back to the original point
					boardPieces [yInt] [xInt] = temp;
					boardPieces [yInt] [xInt - 1] = null;
				}
			}
		} 
		catch (ArrayIndexOutOfBoundsException AIOOBEx)
		{}

		// bottom right
		// catching index out of bounds exceptions
		try 
		{
			// if there is a piece in the king's way
			if (boardPieces [yInt + 1] [xInt + 1] != null)
			{
				// if there is an enemy piece in the king's way, the king can take
				if (boardPieces [yInt + 1] [xInt + 1].owner != boardPieces [yInt] [xInt].owner)
				{
					// pretending that the piece was actually moved, then seeing if the king is in check
					temp = boardPieces [yInt + 1] [xInt + 1];
					boardPieces [yInt + 1] [xInt + 1] = boardPieces [yInt] [xInt];
					boardPieces [yInt] [xInt] = null;
					findKing();
					// if the king is not in check					
					if (!threatCheck (kingX, kingY))
					{
						boardTiles [yInt + 1] [xInt + 1].setBackground(Color.GREEN);
						boardTiles [yInt + 1] [xInt + 1].setActionCommand("k" + (xInt + 1) + "x" + (yInt + 1));
						// moving the piece back to the original point
						boardPieces [yInt] [xInt] = boardPieces [yInt + 1] [xInt + 1];
						boardPieces [yInt + 1] [xInt + 1] = temp;
					}
					else
					{
						// moving the piece back to the original point
						boardPieces [yInt] [xInt] = boardPieces [yInt + 1] [xInt + 1];
						boardPieces [yInt + 1] [xInt + 1] = temp;
					}
				}
			}
			// if there isn't a piece in the king's way
			else
			{
				// pretending that the piece was actually moved, then seeing if the king is in check
				temp = boardPieces [yInt] [xInt];
				boardPieces [yInt + 1] [xInt + 1] = boardPieces [yInt] [xInt];
				boardPieces [yInt] [xInt] = null;
				findKing();
				// if the king is not in check
				if (!threatCheck (kingX, kingY))
				{
					boardTiles [yInt + 1] [xInt + 1].setBackground(Color.GREEN);
					boardTiles [yInt + 1] [xInt + 1].setActionCommand("m" + (xInt + 1) + "x" + (yInt + 1));
					// moving the piece back to the original point
					boardPieces [yInt] [xInt] = temp;
					boardPieces [yInt + 1] [xInt + 1] = null;
				}
				else
				{
					// moving the piece back to the original point
					boardPieces [yInt] [xInt] = temp;
					boardPieces [yInt + 1] [xInt + 1] = null;
				}
			}
		} 
		catch (ArrayIndexOutOfBoundsException AIOOBEx)
		{}

		// bottom left
		// catching index out of bounds exceptions
		try 
		{
			// if there is a piece in the king's way
			if (boardPieces [yInt + 1] [xInt - 1] != null)
			{
				// if there is an enemy piece in the king's way, the king can take
				if (boardPieces [yInt + 1] [xInt - 1].owner != boardPieces [yInt] [xInt].owner)
				{
					// pretending that the piece was actually moved, then seeing if the king is in check
					temp = boardPieces [yInt + 1] [xInt - 1];
					boardPieces [yInt + 1] [xInt - 1] = boardPieces [yInt] [xInt];
					boardPieces [yInt] [xInt] = null;
					findKing();
					// if the king is not in check					
					if (!threatCheck (kingX, kingY))
					{
						boardTiles [yInt + 1] [xInt - 1].setBackground(Color.GREEN);
						boardTiles [yInt + 1] [xInt - 1].setActionCommand("k" + (xInt - 1) + "x" + (yInt + 1));
						// moving the piece back to the original point
						boardPieces [yInt] [xInt] = boardPieces [yInt + 1] [xInt - 1];
						boardPieces [yInt + 1] [xInt - 1] = temp;
					}
					else
					{
						// moving the piece back to the original point
						boardPieces [yInt] [xInt] = boardPieces [yInt + 1] [xInt - 1];
						boardPieces [yInt + 1] [xInt - 1] = temp;
					}
				}
			}
			// if there isn't a piece in the king's way
			else
			{
				// pretending that the piece was actually moved, then seeing if the king is in check
				temp = boardPieces [yInt] [xInt];
				boardPieces [yInt + 1] [xInt - 1] = boardPieces [yInt] [xInt];
				boardPieces [yInt] [xInt] = null;
				findKing();
				// if the king is not in check
				if (!threatCheck (kingX, kingY))
				{
					boardTiles [yInt + 1] [xInt - 1].setBackground(Color.GREEN);
					boardTiles [yInt + 1] [xInt - 1].setActionCommand("m" + (xInt - 1) + "x" + (yInt + 1));
					// moving the piece back to the original point
					boardPieces [yInt] [xInt] = temp;
					boardPieces [yInt + 1] [xInt - 1] = null;
				}
				else
				{
					// moving the piece back to the original points
					boardPieces [yInt] [xInt] = temp;
					boardPieces [yInt + 1] [xInt - 1] = null;
				}
			}
		} 
		catch (ArrayIndexOutOfBoundsException AIOOBEx)
		{}

		// top right
		// catching index out of bounds exceptions
		try 
		{
			// if there is a piece in the king's way
			if (boardPieces [yInt - 1] [xInt + 1] != null)
			{
				// if there is an enemy piece in the king's way, the king can take
				if (boardPieces [yInt - 1] [xInt + 1].owner != boardPieces [yInt] [xInt].owner)
				{
					// pretending that the piece was actually moved, then seeing if the king is in check
					temp = boardPieces [yInt - 1] [xInt + 1];
					boardPieces [yInt - 1] [xInt + 1] = boardPieces [yInt] [xInt];
					boardPieces [yInt] [xInt] = null;
					findKing();
					// if the king is not in check					
					if (!threatCheck (kingX, kingY))
					{
						boardTiles [yInt - 1] [xInt + 1].setBackground(Color.GREEN);
						boardTiles [yInt - 1] [xInt + 1].setActionCommand("k" + (xInt + 1) + "x" + (yInt - 1));
						// moving the piece back to the original point
						boardPieces [yInt] [xInt] = boardPieces [yInt - 1] [xInt + 1];
						boardPieces [yInt - 1] [xInt + 1] = temp;
					}
					else
					{
						// moving the piece back to the original point
						boardPieces [yInt] [xInt] = boardPieces [yInt - 1] [xInt + 1];
						boardPieces [yInt - 1] [xInt + 1] = temp;
					}
				}
			}
			// if there isn't a piece in the king's way
			else
			{
				// pretending that the piece was actually moved, then seeing if the king is in check
				temp = boardPieces [yInt] [xInt];
				boardPieces [yInt - 1] [xInt + 1] = boardPieces [yInt] [xInt];
				boardPieces [yInt] [xInt] = null;
				findKing();
				// if the king is not in check
				if (!threatCheck (kingX, kingY))
				{
					boardTiles [yInt - 1] [xInt + 1].setBackground(Color.GREEN);
					boardTiles [yInt - 1] [xInt + 1].setActionCommand("m" + (xInt + 1) + "x" + (yInt - 1));
					// moving the piece back to the original point
					boardPieces [yInt] [xInt] = temp;
					boardPieces [yInt - 1] [xInt + 1] = null;
				}
				else
				{
					// moving the piece back to the original point
					boardPieces [yInt] [xInt] = temp;
					boardPieces [yInt - 1] [xInt + 1] = null;
				}
			}
		} 
		catch (ArrayIndexOutOfBoundsException AIOOBEx)
		{}

		// top left
		// catching index out of bounds exceptions
		try 
		{
			// if there is a piece in the king's way
			if (boardPieces [yInt - 1] [xInt - 1] != null)
			{
				// if there is an enemy piece in the king's way, the king can take
				if (boardPieces [yInt - 1] [xInt - 1].owner != boardPieces [yInt] [xInt].owner)
				{
					// pretending that the piece was actually moved, then seeing if the king is in check
					temp = boardPieces [yInt - 1] [xInt - 1];
					boardPieces [yInt - 1] [xInt - 1] = boardPieces [yInt] [xInt];
					boardPieces [yInt] [xInt] = null;
					findKing();
					// if the king is not in check					
					if (!threatCheck (kingX, kingY))
					{
						boardTiles [yInt - 1] [xInt - 1].setBackground(Color.GREEN);
						boardTiles [yInt - 1] [xInt - 1].setActionCommand("k" + (xInt - 1) + "x" + (yInt - 1));
						// moving the piece back to the original point
						boardPieces [yInt] [xInt] = boardPieces [yInt - 1] [xInt - 1];
						boardPieces [yInt - 1] [xInt - 1] = temp;
					}
					else
					{
						// moving the piece back to the original point
						boardPieces [yInt] [xInt] = boardPieces [yInt - 1] [xInt - 1];
						boardPieces [yInt - 1] [xInt - 1] = temp;
					}
				}
			}
			// if there isn't a piece in the king's way
			else
			{
				// pretending that the piece was actually moved, then seeing if the king is in check
				temp = boardPieces [yInt] [xInt];
				boardPieces [yInt - 1] [xInt - 1] = boardPieces [yInt] [xInt];
				boardPieces [yInt] [xInt] = null;
				findKing();
				// if the king is not in check
				if (!threatCheck (kingX, kingY))
				{
					boardTiles [yInt - 1] [xInt - 1].setBackground(Color.GREEN);
					boardTiles [yInt - 1] [xInt - 1].setActionCommand("m" + (xInt - 1) + "x" + (yInt - 1));
					// moving the piece back to the original point
					boardPieces [yInt] [xInt] = temp;
					boardPieces [yInt - 1] [xInt - 1] = null;
				}
				else
				{
					// moving the piece back to the original point
					boardPieces [yInt] [xInt] = temp;
					boardPieces [yInt - 1] [xInt - 1] = null;
				}
			}
		} 
		catch (ArrayIndexOutOfBoundsException AIOOBEx)
		{}

		// CASTLING
		// if a piece can move like a king, that doesn't mean it is a king
		if (boardPieces [yInt] [xInt].type.equals("king"))
		{
			// in order to castle, the king cannot have moved
			if (boardPieces [yInt] [xInt].hasMoved == false)
			{
				// if it is the white player's king
				if (playerTurn == 'b')
				{
					// king side castling
					// catching null pointer exceptions
					try
					{
						// if the relevant rook has not moved
						if (boardPieces [7] [7].type.equals("rook") && boardPieces [7] [7].hasMoved == false)
						{
							// if the tiles in between the king and the relevant rook are empty
							if (boardPieces [7] [5] == null && boardPieces [7] [6] == null)
							{
								// checking if the relevant tiles are threatened in any way
								if (threatCheck(4, 7) == false && threatCheck(5, 7) == false && threatCheck(6, 7) == false)
								{
									boardTiles [7] [6].setBackground(Color.GREEN);
									boardTiles [7] [6].setActionCommand("m6x7casK");
								}
							}
						}
					}
					catch (NullPointerException NPEx)
					{}

					// queen side castling
					// catching null pointer exceptions
					try
					{
						// if the relevant rook has not moved
						if (boardPieces [7] [0].type.equals("rook") && boardPieces [7] [0].hasMoved == false)
						{
							// if the tiles in between the king and the relevant rook are empty
							if (boardPieces [7] [1] == null && boardPieces [7] [2] == null && boardPieces [7] [3] == null)
							{
								// checking if the relevant tiles are threatened in any way
								if (threatCheck(1, 7) == false && threatCheck(2, 7) == false && threatCheck(3, 7) == false && threatCheck(4, 7) == false)
								{
									boardTiles [7] [2].setBackground(Color.GREEN);
									boardTiles [7] [2].setActionCommand("m2x7casQ");
								}
							}
						}
					}
					catch (NullPointerException NPEx)
					{}
				}
				// if it is the black player's king
				else if (playerTurn == 'w')
				{
					// king side castling
					// catching null pointer exceptions
					try
					{
						// if the relevant rook has not moved
						if (boardPieces [0] [7].type.equals("rook") && boardPieces [0] [7].hasMoved == false)
						{
							// if the tiles in between the king and the relevant rook are empty
							if (boardPieces [0] [5] == null && boardPieces [0] [6] == null)
							{
								// checking if the relevant tiles are threatened in any way
								if (threatCheck(4, 0) == false && threatCheck(5, 0) == false && threatCheck(6, 0) == false)
								{
									boardTiles [0] [6].setBackground(Color.GREEN);
									boardTiles [0] [6].setActionCommand("m6x0casK");
								}
							}
						}
					}
					catch (NullPointerException NPEx)
					{}

					// queen side castling
					// catching null pointer exceptions
					try
					{
						// if the relevant rook has not moved
						if (boardPieces [0] [0].type.equals("rook") && boardPieces [0] [0].hasMoved == false)
						{
							// if the tiles in between the king and the relevant rook are empty
							if (boardPieces [0] [1] == null && boardPieces [0] [2] == null && boardPieces [0] [3] == null)
							{
								// checking if the relevant tiles are threatened in any way
								if (threatCheck(1, 0) == false && threatCheck(2, 0) == false && threatCheck(3, 0) == false && threatCheck(4, 0) == false)
								{
									boardTiles [0] [2].setBackground(Color.GREEN);
									boardTiles [0] [2].setActionCommand("m2x0casQ");
								}
							}
						}
					}
					catch (NullPointerException NPEx)
					{}
				}
			}
		}
	}
	// if the piece moves like a rook
	private void rookMovePiece ()
	{
		// this block is for all tiles to the right of the piece
		// catching index out of bounds exceptions
		try 
		{
			for (int x = 1; x < boardTiles.length; x++)
			{
				// if the tile in question has a piece on it
				if (boardPieces [yInt] [xInt + x] != null)
				{
					// if the tile in question has an enemy piece on it
					if (boardPieces [yInt] [xInt + x].owner != boardPieces [yInt] [xInt].owner)
					{
						// pretending that the piece was actually moved, then seeing if the king is still in check
						temp = boardPieces [yInt] [xInt + x];
						boardPieces [yInt] [xInt + x] = boardPieces [yInt] [xInt];
						boardPieces [yInt] [xInt] = null;
						findKing();
						if (!threatCheck (kingX, kingY))
						{
							boardTiles [yInt] [xInt + x].setBackground(Color.GREEN);
							boardTiles [yInt] [xInt + x].setActionCommand("k" + (xInt + x) + "x" + (yInt));
							boardPieces [yInt] [xInt] = boardPieces [yInt] [xInt + x];
							boardPieces [yInt] [xInt + x] = temp;
						}
						else
						{
							boardPieces [yInt] [xInt] = boardPieces [yInt] [xInt + x];
							boardPieces [yInt] [xInt + x] = temp;
						}
						break;
					}
					else
					{
						break;
					}
				}
				// if the tile in question is empty
				else
				{
					// pretending that the piece was actually moved, then seeing if the king is still in check
					temp = boardPieces [yInt] [xInt];
					boardPieces [yInt] [xInt + x] = boardPieces [yInt] [xInt];
					boardPieces [yInt] [xInt] = null;
					findKing();
					if (!threatCheck (kingX, kingY))
					{
						boardTiles [yInt] [xInt + x].setBackground(Color.GREEN);
						boardTiles [yInt] [xInt + x].setActionCommand("m" + (xInt + x) + "x" + (yInt));
						boardPieces [yInt] [xInt] = temp;
						boardPieces [yInt] [xInt + x] = null;
					}
					else
					{
						boardPieces [yInt] [xInt] = temp;
						boardPieces [yInt] [xInt + x] = null;
					}
				}
			}
		} 
		catch (ArrayIndexOutOfBoundsException AIOOBEx)
		{}

		// this block is for all tiles to the left of the piece
		// catching index out of bounds exceptions
		try
		{
			for (int x = 1; x < boardTiles.length; x++)
			{
				// if the tile in question has a piece on it
				if (boardPieces [yInt] [xInt - x] != null)
				{
					// if the tile in question has an enemy piece on it
					if (boardPieces [yInt] [xInt - x].owner != boardPieces [yInt] [xInt].owner)
					{
						// pretending that the piece was actually moved, then seeing if the king is still in check
						temp = boardPieces [yInt] [xInt - x];
						boardPieces [yInt] [xInt - x] = boardPieces [yInt] [xInt];
						boardPieces [yInt] [xInt] = null;
						findKing();
						if (!threatCheck (kingX, kingY))
						{
							boardTiles [yInt] [xInt - x].setBackground(Color.GREEN);
							boardTiles [yInt] [xInt - x].setActionCommand("k" + (xInt - x) + "x" + (yInt));
							boardPieces [yInt] [xInt] = boardPieces [yInt] [xInt - x];
							boardPieces [yInt] [xInt - x] = temp;
						}
						else
						{
							boardPieces [yInt] [xInt] = boardPieces [yInt] [xInt - x];
							boardPieces [yInt] [xInt - x] = temp;
						}
						break;
					}
					else
					{
						break;
					}
				}
				// if the tile in question is empty
				else
				{
					// pretending that the piece was actually moved, then seeing if the king is still in check
					temp = boardPieces [yInt] [xInt];
					boardPieces [yInt] [xInt - x] = boardPieces [yInt] [xInt];
					boardPieces [yInt] [xInt] = null;
					findKing();
					if (!threatCheck (kingX, kingY))
					{
						boardTiles [yInt] [xInt - x].setBackground(Color.GREEN);
						boardTiles [yInt] [xInt - x].setActionCommand("m" + (xInt - x) + "x" + (yInt));
						boardPieces [yInt] [xInt] = temp;
						boardPieces [yInt] [xInt - x] = null;
					}
					else
					{
						boardPieces [yInt] [xInt] = temp;
						boardPieces [yInt] [xInt - x] = null;
					}
				}
			}
		} 
		catch (ArrayIndexOutOfBoundsException AIOOBEx)
		{}

		// this block is for all tiles below the piece
		// catching index out of bounds exceptions
		try
		{
			for (int y = 1; y < boardTiles.length; y++)
			{
				// if the tile in question has a piece on it
				if (boardPieces [yInt + y] [xInt] != null)
				{
					// if the tile in question has an enemy piece on it
					if (boardPieces [yInt + y] [xInt].owner != boardPieces [yInt] [xInt].owner)
					{
						// pretending that the piece was actually moved, then seeing if the king is still in check
						temp = boardPieces [yInt + y] [xInt];
						boardPieces [yInt + y] [xInt] = boardPieces [yInt] [xInt];
						boardPieces [yInt] [xInt] = null;
						findKing();
						if (!threatCheck (kingX, kingY))
						{
							boardTiles [yInt + y] [xInt].setBackground(Color.GREEN);
							boardTiles [yInt + y] [xInt].setActionCommand("k" + (xInt) + "x" + (yInt + y));
							boardPieces [yInt] [xInt] = boardPieces [yInt + y] [xInt];
							boardPieces [yInt + y] [xInt] = temp;
						}
						else
						{
							boardPieces [yInt] [xInt] = boardPieces [yInt + y] [xInt];
							boardPieces [yInt + y] [xInt] = temp;
						}
						break;
					}
					else
					{
						break;
					}
				}
				// if the tile in question is empty
				else
				{
					// pretending that the piece was actually moved, then seeing if the king is still in check
					temp = boardPieces [yInt] [xInt];
					boardPieces [yInt + y] [xInt] = boardPieces [yInt] [xInt];
					boardPieces [yInt] [xInt] = null;
					findKing();
					if (!threatCheck (kingX, kingY))
					{
						boardTiles [yInt + y] [xInt].setBackground(Color.GREEN);
						boardTiles [yInt + y] [xInt].setActionCommand("m" + (xInt) + "x" + (yInt + y));
						boardPieces [yInt] [xInt] = temp;
						boardPieces [yInt + y] [xInt] = null;
					}
					else
					{
						boardPieces [yInt] [xInt] = temp;
						boardPieces [yInt + y] [xInt] = null;
					}
				}
			}
		} 
		catch (ArrayIndexOutOfBoundsException AIOOBEx)
		{}

		// this block is for all tiles above the piece
		// catching index out of bounds exceptions
		try
		{
			for (int y = 1; y < boardTiles.length; y++)
			{
				// if the tile in question has a piece on it
				if (boardPieces [yInt - y] [xInt] != null)
				{
					// if the tile in question has an enemy piece on it
					if (boardPieces [yInt - y] [xInt].owner != boardPieces [yInt] [xInt].owner)
					{
						// pretending that the piece was actually moved, then seeing if the king is still in check
						temp = boardPieces [yInt - y] [xInt];
						boardPieces [yInt - y] [xInt] = boardPieces [yInt] [xInt];
						boardPieces [yInt] [xInt] = null;
						findKing();
						if (!threatCheck (kingX, kingY))
						{
							boardTiles [yInt - y] [xInt].setBackground(Color.GREEN);
							boardTiles [yInt - y] [xInt].setActionCommand("k" + (xInt) + "x" + (yInt - y));
							boardPieces [yInt] [xInt] = boardPieces [yInt - y] [xInt];
							boardPieces [yInt - y] [xInt] = temp;
						}
						else
						{
							boardPieces [yInt] [xInt] = boardPieces [yInt - y] [xInt];
							boardPieces [yInt - y] [xInt] = temp;
						}
						break;
					}
					else
					{
						break;
					}
				}
				// if the tile in question is empty
				else
				{
					// pretending that the piece was actually moved, then seeing if the king is still in check
					temp = boardPieces [yInt] [xInt];
					boardPieces [yInt - y] [xInt] = boardPieces [yInt] [xInt];
					boardPieces [yInt] [xInt] = null;
					findKing();
					if (!threatCheck (kingX, kingY))
					{
						boardTiles [yInt - y] [xInt].setBackground(Color.GREEN);
						boardTiles [yInt - y] [xInt].setActionCommand("m" + (xInt) + "x" + (yInt - y));
						boardPieces [yInt] [xInt] = temp;
						boardPieces [yInt - y] [xInt] = null;
					}
					else
					{
						boardPieces [yInt] [xInt] = temp;
						boardPieces [yInt - y] [xInt] = null;
					}
				}
			}
		} 
		catch (ArrayIndexOutOfBoundsException AIOOBEx)
		{}
	}
	// if the piece moves like a bishop
	private void bishopMovePiece ()
	{
		// diagonal down and to the right
		try 
		{
			for (int i = 1; i < boardTiles.length; i++)
			{
				if (boardPieces [yInt + i] [xInt + i] != null)
				{
					if (boardPieces [yInt + i] [xInt + i].owner != boardPieces [yInt] [xInt].owner)
					{
						// pretending that the piece was actually moved, then seeing if the king is still in check
						temp = boardPieces [yInt + i] [xInt + i];
						boardPieces [yInt + i] [xInt + i] = boardPieces [yInt] [xInt];
						boardPieces [yInt] [xInt] = null;
						findKing();
						if (!threatCheck (kingX, kingY))
						{
							boardTiles [yInt + i] [xInt + i].setBackground(Color.GREEN);
							boardTiles [yInt + i] [xInt + i].setActionCommand("k" + (xInt + i) + "x" + (yInt + i));
							boardPieces [yInt] [xInt] = boardPieces [yInt + i] [xInt + i];
							boardPieces [yInt + i] [xInt + i] = temp;
						}
						else
						{
							boardPieces [yInt] [xInt] = boardPieces [yInt + i] [xInt + i];
							boardPieces [yInt + i] [xInt + i] = temp;
						}
						break;
					}
					else
					{
						break;
					}
				}
				else
				{
					// pretending that the piece was actually moved, then seeing if the king is still in check
					temp = boardPieces [yInt] [xInt];
					boardPieces [yInt + i] [xInt + i] = boardPieces [yInt] [xInt];
					boardPieces [yInt] [xInt] = null;
					findKing();
					if (!threatCheck (kingX, kingY))
					{
						boardTiles [yInt + i] [xInt + i].setBackground(Color.GREEN);
						boardTiles [yInt + i] [xInt + i].setActionCommand("m" + (xInt + i) + "x" + (yInt + i));
						boardPieces [yInt] [xInt] = temp;
						boardPieces [yInt + i] [xInt + i] = null;
					}
					else
					{
						boardPieces [yInt] [xInt] = temp;
						boardPieces [yInt + i] [xInt + i] = null;
					}
				}
			}
		} 
		catch (ArrayIndexOutOfBoundsException AIOOBEx)
		{}

		// diagonal down and to the left
		try 
		{
			for (int i = 1; i < boardTiles.length; i++)
			{
				if (boardPieces [yInt + i] [xInt - i] != null)
				{
					if (boardPieces [yInt + i] [xInt - i].owner != boardPieces [yInt] [xInt].owner)
					{
						// pretending that the piece was actually moved, then seeing if the king is still in check
						temp = boardPieces [yInt + i] [xInt - i];
						boardPieces [yInt + i] [xInt - i] = boardPieces [yInt] [xInt];
						boardPieces [yInt] [xInt] = null;
						findKing();
						if (!threatCheck (kingX, kingY))
						{
							boardTiles [yInt + i] [xInt - i].setBackground(Color.GREEN);
							boardTiles [yInt + i] [xInt - i].setActionCommand("k" + (xInt - i) + "x" + (yInt + i));
							boardPieces [yInt] [xInt] = boardPieces [yInt + i] [xInt - i];
							boardPieces [yInt + i] [xInt - i] = temp;
						}
						else
						{
							boardPieces [yInt] [xInt] = boardPieces [yInt + i] [xInt - i];
							boardPieces [yInt + i] [xInt - i] = temp;
						}
						break;
					}
					else
					{
						break;
					}
				}
				else
				{
					// pretending that the piece was actually moved, then seeing if the king is still in check
					temp = boardPieces [yInt] [xInt];
					boardPieces [yInt + i] [xInt - i] = boardPieces [yInt] [xInt];
					boardPieces [yInt] [xInt] = null;
					findKing();
					if (!threatCheck (kingX, kingY))
					{
						boardTiles [yInt + i] [xInt - i].setBackground(Color.GREEN);
						boardTiles [yInt + i] [xInt - i].setActionCommand("m" + (xInt - i) + "x" + (yInt + i));
						boardPieces [yInt] [xInt] = temp;
						boardPieces [yInt + i] [xInt - i] = null;
					}
					else
					{
						boardPieces [yInt] [xInt] = temp;
						boardPieces [yInt + i] [xInt - i] = null;
					}
				}
			}
		} 
		catch (ArrayIndexOutOfBoundsException AIOOBEx)
		{}

		// diagonal up and to the right
		try 
		{
			for (int i = 1; i < boardTiles.length; i++)
			{
				if (boardPieces [yInt - i] [xInt + i] != null)
				{
					if (boardPieces [yInt - i] [xInt + i].owner != boardPieces [yInt] [xInt].owner)
					{
						// pretending that the piece was actually moved, then seeing if the king is still in check
						temp = boardPieces [yInt - i] [xInt + i];
						boardPieces [yInt - i] [xInt + i] = boardPieces [yInt] [xInt];
						boardPieces [yInt] [xInt] = null;
						findKing();
						if (!threatCheck (kingX, kingY))
						{
							boardTiles [yInt - i] [xInt + i].setBackground(Color.GREEN);
							boardTiles [yInt - i] [xInt + i].setActionCommand("k" + (xInt + i) + "x" + (yInt - i));
							boardPieces [yInt] [xInt] = boardPieces [yInt - i] [xInt + i];
							boardPieces [yInt - i] [xInt + i] = temp;
						}
						else
						{
							boardPieces [yInt] [xInt] = boardPieces [yInt - i] [xInt + i];
							boardPieces [yInt - i] [xInt + i] = temp;
						}
						break;
					}
					else
					{
						break;
					}
				}
				else
				{
					// pretending that the piece was actually moved, then seeing if the king is still in check
					temp = boardPieces [yInt] [xInt];
					boardPieces [yInt - i] [xInt + i] = boardPieces [yInt] [xInt];
					boardPieces [yInt] [xInt] = null;
					findKing();
					if (!threatCheck (kingX, kingY))
					{
						boardTiles [yInt - i] [xInt + i].setBackground(Color.GREEN);
						boardTiles [yInt - i] [xInt + i].setActionCommand("m" + (xInt + i) + "x" + (yInt - i));
						boardPieces [yInt] [xInt] = temp;
						boardPieces [yInt - i] [xInt + i] = null;
					}
					else
					{
						boardPieces [yInt] [xInt] = temp;
						boardPieces [yInt - i] [xInt + i] = null;
					}
				}
			}
		} 
		catch (ArrayIndexOutOfBoundsException AIOOBEx)
		{}

		// diagonal up and to the left
		try 
		{
			for (int i = 1; i < boardTiles.length; i++)
			{
				if (boardPieces [yInt - i] [xInt - i] != null)
				{
					if (boardPieces [yInt - i] [xInt - i].owner != boardPieces [yInt] [xInt].owner)
					{
						// pretending that the piece was actually moved, then seeing if the king is still in check
						temp = boardPieces [yInt - i] [xInt - i];
						boardPieces [yInt - i] [xInt - i] = boardPieces [yInt] [xInt];
						boardPieces [yInt] [xInt] = null;
						findKing();
						if (!threatCheck (kingX, kingY))
						{
							boardTiles [yInt - i] [xInt - i].setBackground(Color.GREEN);
							boardTiles [yInt - i] [xInt - i].setActionCommand("k" + (xInt - i) + "x" + (yInt - i));
							boardPieces [yInt] [xInt] = boardPieces [yInt - i] [xInt - i];
							boardPieces [yInt - i] [xInt - i] = temp;
						}
						else
						{
							boardPieces [yInt] [xInt] = boardPieces [yInt - i] [xInt - i];
							boardPieces [yInt - i] [xInt - i] = temp;
						}
						break;
					}
					else
					{
						break;
					}
				}
				else
				{
					// pretending that the piece was actually moved, then seeing if the king is still in check
					temp = boardPieces [yInt] [xInt];
					boardPieces [yInt - i] [xInt - i] = boardPieces [yInt] [xInt];
					boardPieces [yInt] [xInt] = null;
					findKing();
					if (!threatCheck (kingX, kingY))
					{
						boardTiles [yInt - i] [xInt - i].setBackground(Color.GREEN);
						boardTiles [yInt - i] [xInt - i].setActionCommand("m" + (xInt - i) + "x" + (yInt - i));
						boardPieces [yInt] [xInt] = temp;
						boardPieces [yInt - i] [xInt - i] = null;
					}
					else
					{
						boardPieces [yInt] [xInt] = temp;
						boardPieces [yInt - i] [xInt - i] = null;
					}
				}
			}
		} 
		catch (ArrayIndexOutOfBoundsException AIOOBEx)
		{}
	}
	// if the piece moves like a knight
	private void knightMovePiece ()
	{
		// 2 down, 1 right
		try 
		{
			if (boardPieces [yInt + 2] [xInt + 1] != null)
			{
				if (boardPieces [yInt + 2] [xInt + 1].owner != boardPieces [yInt] [xInt].owner)
				{
					// pretending that the piece was actually moved, then seeing if the king is still in check
					temp = boardPieces [yInt + 2] [xInt + 1];
					boardPieces [yInt + 2] [xInt + 1] = boardPieces [yInt] [xInt];
					boardPieces [yInt] [xInt] = null;
					findKing();
					if (!threatCheck (kingX, kingY))
					{
						boardTiles [yInt + 2] [xInt + 1].setBackground(Color.GREEN);
						boardTiles [yInt + 2] [xInt + 1].setActionCommand("k" + (xInt + 1) + "x" + (yInt + 2));
						boardPieces [yInt] [xInt] = boardPieces [yInt + 2] [xInt + 1];
						boardPieces [yInt + 2] [xInt + 1] = temp;
					}
					else
					{
						boardPieces [yInt] [xInt] = boardPieces [yInt + 2] [xInt + 1];
						boardPieces [yInt + 2] [xInt + 1] = temp;
					}
				}
			}
			else
			{
				// pretending that the piece was actually moved, then seeing if the king is still in check
				temp = boardPieces [yInt] [xInt];
				boardPieces [yInt + 2] [xInt + 1] = boardPieces [yInt] [xInt];
				boardPieces [yInt] [xInt] = null;
				findKing();
				if (!threatCheck (kingX, kingY))
				{
					boardTiles [yInt + 2] [xInt + 1].setBackground(Color.GREEN);
					boardTiles [yInt + 2] [xInt + 1].setActionCommand("m" + (xInt + 1) + "x" + (yInt + 2));
					boardPieces [yInt] [xInt] = temp;
					boardPieces [yInt + 2] [xInt + 1] = null;
				}
				else
				{
					boardPieces [yInt] [xInt] = temp;
					boardPieces [yInt + 2] [xInt + 1] = null;
				}
			}
		} 
		catch (ArrayIndexOutOfBoundsException AIOOBEx)
		{}

		// 2 down, 1 left
		try 
		{
			if (boardPieces [yInt + 2] [xInt - 1] != null)
			{
				if (boardPieces [yInt + 2] [xInt - 1].owner != boardPieces [yInt] [xInt].owner)
				{
					// pretending that the piece was actually moved, then seeing if the king is still in check
					temp = boardPieces [yInt + 2] [xInt - 1];
					boardPieces [yInt + 2] [xInt - 1] = boardPieces [yInt] [xInt];
					boardPieces [yInt] [xInt] = null;
					findKing();
					if (!threatCheck (kingX, kingY))
					{
						boardTiles [yInt + 2] [xInt - 1].setBackground(Color.GREEN);
						boardTiles [yInt + 2] [xInt - 1].setActionCommand("k" + (xInt - 1) + "x" + (yInt + 2));
						boardPieces [yInt] [xInt] = boardPieces [yInt + 2] [xInt - 1];
						boardPieces [yInt + 2] [xInt - 1] = temp;
					}
					else
					{
						boardPieces [yInt] [xInt] = boardPieces [yInt + 2] [xInt - 1];
						boardPieces [yInt + 2] [xInt - 1] = temp;
					}
				}
			}
			else
			{
				// pretending that the piece was actually moved, then seeing if the king is still in check
				temp = boardPieces [yInt] [xInt];
				boardPieces [yInt + 2] [xInt - 1] = boardPieces [yInt] [xInt];
				boardPieces [yInt] [xInt] = null;
				findKing();
				if (!threatCheck (kingX, kingY))
				{
					boardTiles [yInt + 2] [xInt - 1].setBackground(Color.GREEN);
					boardTiles [yInt + 2] [xInt - 1].setActionCommand("m" + (xInt - 1) + "x" + (yInt + 2));
					boardPieces [yInt] [xInt] = temp;
					boardPieces [yInt + 2] [xInt - 1] = null;
				}
				else
				{
					boardPieces [yInt] [xInt] = temp;
					boardPieces [yInt + 2] [xInt - 1] = null;
				}
			}
		} 
		catch (ArrayIndexOutOfBoundsException AIOOBEx)
		{}

		// 2 up, 1 right
		try 
		{
			if (boardPieces [yInt - 2] [xInt + 1] != null)
			{
				if (boardPieces [yInt - 2] [xInt + 1].owner != boardPieces [yInt] [xInt].owner)
				{
					// pretending that the piece was actually moved, then seeing if the king is still in check
					temp = boardPieces [yInt - 2] [xInt + 1];
					boardPieces [yInt - 2] [xInt + 1] = boardPieces [yInt] [xInt];
					boardPieces [yInt] [xInt] = null;
					findKing();
					if (!threatCheck (kingX, kingY))
					{
						boardTiles [yInt - 2] [xInt + 1].setBackground(Color.GREEN);
						boardTiles [yInt - 2] [xInt + 1].setActionCommand("k" + (xInt + 1) + "x" + (yInt - 2));
						boardPieces [yInt] [xInt] = boardPieces [yInt - 2] [xInt + 1];
						boardPieces [yInt - 2] [xInt + 1] = temp;
					}
					else
					{
						boardPieces [yInt] [xInt] = boardPieces [yInt - 2] [xInt + 1];
						boardPieces [yInt - 2] [xInt + 1] = temp;
					}
				}
			}
			else
			{
				// pretending that the piece was actually moved, then seeing if the king is still in check
				temp = boardPieces [yInt] [xInt];
				boardPieces [yInt - 2] [xInt + 1] = boardPieces [yInt] [xInt];
				boardPieces [yInt] [xInt] = null;
				findKing();
				if (!threatCheck (kingX, kingY))
				{
					boardTiles [yInt - 2] [xInt + 1].setBackground(Color.GREEN);
					boardTiles [yInt - 2] [xInt + 1].setActionCommand("m" + (xInt + 1) + "x" + (yInt - 2));
					boardPieces [yInt] [xInt] = temp;
					boardPieces [yInt - 2] [xInt + 1] = null;
				}
				else
				{
					boardPieces [yInt] [xInt] = temp;
					boardPieces [yInt - 2] [xInt + 1] = null;
				}
			}
		} 
		catch (ArrayIndexOutOfBoundsException AIOOBEx)
		{}

		// 2 up, 1 left
		try 
		{
			if (boardPieces [yInt - 2] [xInt - 1] != null)
			{
				if (boardPieces [yInt - 2] [xInt - 1].owner != boardPieces [yInt] [xInt].owner)
				{
					// pretending that the piece was actually moved, then seeing if the king is still in check
					temp = boardPieces [yInt - 2] [xInt - 1];
					boardPieces [yInt - 2] [xInt - 1] = boardPieces [yInt] [xInt];
					boardPieces [yInt] [xInt] = null;
					findKing();
					if (!threatCheck (kingX, kingY))
					{
						boardTiles [yInt - 2] [xInt - 1].setBackground(Color.GREEN);
						boardTiles [yInt - 2] [xInt - 1].setActionCommand("k" + (xInt - 1) + "x" + (yInt - 2));
						boardPieces [yInt] [xInt] = boardPieces [yInt - 2] [xInt - 1];
						boardPieces [yInt - 2] [xInt - 1] = temp;
					}
					else
					{
						boardPieces [yInt] [xInt] = boardPieces [yInt - 2] [xInt - 1];
						boardPieces [yInt - 2] [xInt - 1] = temp;
					}
				}
			}
			else
			{
				// pretending that the piece was actually moved, then seeing if the king is still in check
				temp = boardPieces [yInt] [xInt];
				boardPieces [yInt - 2] [xInt - 1] = boardPieces [yInt] [xInt];
				boardPieces [yInt] [xInt] = null;
				findKing();
				if (!threatCheck (kingX, kingY))
				{
					boardTiles [yInt - 2] [xInt - 1].setBackground(Color.GREEN);
					boardTiles [yInt - 2] [xInt - 1].setActionCommand("m" + (xInt - 1) + "x" + (yInt - 2));
					boardPieces [yInt] [xInt] = temp;
					boardPieces [yInt - 2] [xInt - 1] = null;
				}
				else
				{
					boardPieces [yInt] [xInt] = temp;
					boardPieces [yInt - 2] [xInt - 1] = null;
				}
			}
		} 
		catch (ArrayIndexOutOfBoundsException AIOOBEx)
		{}


		// 1 down, 2 right
		try 
		{
			if (boardPieces [yInt + 1] [xInt + 2] != null)
			{
				if (boardPieces [yInt + 1] [xInt + 2].owner != boardPieces [yInt] [xInt].owner)
				{
					// pretending that the piece was actually moved, then seeing if the king is still in check
					temp = boardPieces [yInt + 1] [xInt + 2];
					boardPieces [yInt + 1] [xInt + 2] = boardPieces [yInt] [xInt];
					boardPieces [yInt] [xInt] = null;
					findKing();
					if (!threatCheck (kingX, kingY))
					{
						boardTiles [yInt + 1] [xInt + 2].setBackground(Color.GREEN);
						boardTiles [yInt + 1] [xInt + 2].setActionCommand("k" + (xInt + 2) + "x" + (yInt + 1));
						boardPieces [yInt] [xInt] = boardPieces [yInt + 1] [xInt + 2];
						boardPieces [yInt + 1] [xInt + 2] = temp;
					}
					else
					{
						boardPieces [yInt] [xInt] = boardPieces [yInt + 1] [xInt + 2];
						boardPieces [yInt + 1] [xInt + 2] = temp;
					}
				}
			}
			else
			{
				// pretending that the piece was actually moved, then seeing if the king is still in check
				temp = boardPieces [yInt] [xInt];
				boardPieces [yInt + 1] [xInt + 2] = boardPieces [yInt] [xInt];
				boardPieces [yInt] [xInt] = null;
				findKing();
				if (!threatCheck (kingX, kingY))
				{
					boardTiles [yInt + 1] [xInt + 2].setBackground(Color.GREEN);
					boardTiles [yInt + 1] [xInt + 2].setActionCommand("m" + (xInt + 2) + "x" + (yInt + 1));
					boardPieces [yInt] [xInt] = temp;
					boardPieces [yInt + 1] [xInt + 2] = null;
				}
				else
				{
					boardPieces [yInt] [xInt] = temp;
					boardPieces [yInt + 1] [xInt + 2] = null;
				}
			}
		} 
		catch (ArrayIndexOutOfBoundsException AIOOBEx)
		{}

		// 1 down, 2 left
		try 
		{
			if (boardPieces [yInt + 1] [xInt - 2] != null)
			{
				if (boardPieces [yInt + 1] [xInt - 2].owner != boardPieces [yInt] [xInt].owner)
				{
					// pretending that the piece was actually moved, then seeing if the king is still in check
					temp = boardPieces [yInt + 1] [xInt - 2];
					boardPieces [yInt + 1] [xInt - 2] = boardPieces [yInt] [xInt];
					boardPieces [yInt] [xInt] = null;
					findKing();
					if (!threatCheck (kingX, kingY))
					{
						boardTiles [yInt + 1] [xInt - 2].setBackground(Color.GREEN);
						boardTiles [yInt + 1] [xInt - 2].setActionCommand("k" + (xInt - 2) + "x" + (yInt + 1));
						boardPieces [yInt] [xInt] = boardPieces [yInt + 1] [xInt - 2];
						boardPieces [yInt + 1] [xInt - 2] = temp;
					}
					else
					{
						boardPieces [yInt] [xInt] = boardPieces [yInt + 1] [xInt - 2];
						boardPieces [yInt + 1] [xInt - 2] = temp;
					}
				}
			}
			else
			{
				// pretending that the piece was actually moved, then seeing if the king is still in check
				temp = boardPieces [yInt] [xInt];
				boardPieces [yInt + 1] [xInt - 2] = boardPieces [yInt] [xInt];
				boardPieces [yInt] [xInt] = null;
				findKing();
				if (!threatCheck (kingX, kingY))
				{
					boardTiles [yInt + 1] [xInt - 2].setBackground(Color.GREEN);
					boardTiles [yInt + 1] [xInt - 2].setActionCommand("m" + (xInt - 2) + "x" + (yInt + 1));
					boardPieces [yInt] [xInt] = temp;
					boardPieces [yInt + 1] [xInt - 2] = null;
				}
				else
				{
					boardPieces [yInt] [xInt] = temp;
					boardPieces [yInt + 1] [xInt - 2] = null;
				}
			}
		} 
		catch (ArrayIndexOutOfBoundsException AIOOBEx)
		{}

		// 1 up, 2 right
		try 
		{
			if (boardPieces [yInt - 1] [xInt + 2] != null)
			{
				if (boardPieces [yInt - 1] [xInt + 2].owner != boardPieces [yInt] [xInt].owner)
				{
					// pretending that the piece was actually moved, then seeing if the king is still in check
					temp = boardPieces [yInt - 1] [xInt + 2];
					boardPieces [yInt - 1] [xInt + 2] = boardPieces [yInt] [xInt];
					boardPieces [yInt] [xInt] = null;
					findKing();
					if (!threatCheck (kingX, kingY))
					{
						boardTiles [yInt - 1] [xInt + 2].setBackground(Color.GREEN);
						boardTiles [yInt - 1] [xInt + 2].setActionCommand("k" + (xInt + 2) + "x" + (yInt - 1));
						boardPieces [yInt] [xInt] = boardPieces [yInt - 1] [xInt + 2];
						boardPieces [yInt - 1] [xInt + 2] = temp;
					}
					else
					{
						boardPieces [yInt] [xInt] = boardPieces [yInt - 1] [xInt + 2];
						boardPieces [yInt - 1] [xInt + 2] = temp;
					}
				}
			}
			else
			{
				// pretending that the piece was actually moved, then seeing if the king is still in check
				temp = boardPieces [yInt] [xInt];
				boardPieces [yInt - 1] [xInt + 2] = boardPieces [yInt] [xInt];
				boardPieces [yInt] [xInt] = null;
				findKing();
				if (!threatCheck (kingX, kingY))
				{
					boardTiles [yInt - 1] [xInt + 2].setBackground(Color.GREEN);
					boardTiles [yInt - 1] [xInt + 2].setActionCommand("m" + (xInt + 2) + "x" + (yInt - 1));
					boardPieces [yInt] [xInt] = temp;
					boardPieces [yInt - 1] [xInt + 2] = null;
				}
				else
				{
					boardPieces [yInt] [xInt] = temp;
					boardPieces [yInt - 1] [xInt + 2] = null;
				}
			}
		} 
		catch (ArrayIndexOutOfBoundsException AIOOBEx)
		{}

		// 1 up, 2 left
		try 
		{
			if (boardPieces [yInt - 1] [xInt - 2] != null)
			{
				if (boardPieces [yInt - 1] [xInt - 2].owner != boardPieces [yInt] [xInt].owner)
				{
					// pretending that the piece was actually moved, then seeing if the king is still in check
					temp = boardPieces [yInt - 1] [xInt + 2];
					boardPieces [yInt - 1] [xInt + 2] = boardPieces [yInt] [xInt];
					boardPieces [yInt] [xInt] = null;
					findKing();
					if (!threatCheck (kingX, kingY))
					{
						boardTiles [yInt - 1] [xInt + 2].setBackground(Color.GREEN);
						boardTiles [yInt - 1] [xInt + 2].setActionCommand("k" + (xInt + 2) + "x" + (yInt - 1));
						boardPieces [yInt] [xInt] = boardPieces [yInt - 1] [xInt + 2];
						boardPieces [yInt - 1] [xInt + 2] = temp;
					}
					else
					{
						boardPieces [yInt] [xInt] = boardPieces [yInt - 1] [xInt + 2];
						boardPieces [yInt - 1] [xInt + 2] = temp;
					}
				}
			}
			else
			{
				// pretending that the piece was actually moved, then seeing if the king is still in check
				temp = boardPieces [yInt] [xInt];
				boardPieces [yInt - 1] [xInt - 2] = boardPieces [yInt] [xInt];
				boardPieces [yInt] [xInt] = null;
				findKing();
				if (!threatCheck (kingX, kingY))
				{
					boardTiles [yInt - 1] [xInt - 2].setBackground(Color.GREEN);
					boardTiles [yInt - 1] [xInt - 2].setActionCommand("m" + (xInt - 2) + "x" + (yInt - 1));
					boardPieces [yInt] [xInt] = temp;
					boardPieces [yInt - 1] [xInt - 2] = null;
				}
				else
				{
					boardPieces [yInt] [xInt] = temp;
					boardPieces [yInt - 1] [xInt - 2] = null;
				}
			}
		} 
		catch (ArrayIndexOutOfBoundsException AIOOBEx)
		{}
	}
	// if the piece moves like a pawn
	private void pawnMovePiece ()
	{
		// if it is a white pawn
		if (boardPieces [yInt] [xInt].owner == 'w')
		{
			// movement
			try
			{
				if (boardPieces [yInt - 1] [xInt] == null) 
				{
					// pretending that the piece was actually moved, then seeing if the king is still in check
					temp = boardPieces [yInt] [xInt];
					boardPieces [yInt - 1] [xInt] = boardPieces [yInt] [xInt];
					boardPieces [yInt] [xInt] = null;
					findKing();
					if (!threatCheck (kingX, kingY))
					{
						boardTiles [yInt - 1] [xInt].setBackground(Color.GREEN);
						boardTiles [yInt - 1] [xInt].setActionCommand("m" + (xInt) + "x" + (yInt - 1));
						boardPieces [yInt] [xInt] = temp;
						boardPieces [yInt - 1] [xInt] = null;
					}
					else
					{
						boardPieces [yInt] [xInt] = temp;
						boardPieces [yInt - 1] [xInt] = null;
					}
				}
			}
			catch (ArrayIndexOutOfBoundsException AIOOBEx)
			{}

			try
			{
				if (boardPieces [yInt] [xInt].hasMoved == false && boardPieces [yInt - 2] [xInt] == null && boardPieces [yInt - 1] [xInt] == null)
				{
					// pretending that the piece was actually moved, then seeing if the king is still in check
					temp = boardPieces [yInt] [xInt];
					boardPieces [yInt - 2] [xInt] = boardPieces [yInt] [xInt];
					boardPieces [yInt] [xInt] = null;
					findKing();
					if (!threatCheck (kingX, kingY))
					{
						boardTiles [yInt - 2] [xInt].setBackground(Color.GREEN);
						boardTiles [yInt - 2] [xInt].setActionCommand("m" + (xInt) + "x" + (yInt - 2) + "en");
						boardPieces [yInt] [xInt] = temp;
						boardPieces [yInt - 2] [xInt] = null;
					}
					else
					{
						boardPieces [yInt] [xInt] = temp;
						boardPieces [yInt - 2] [xInt] = null;
					}
				}
			} 
			catch (ArrayIndexOutOfBoundsException AIOOBEx)
			{}

			// taking
			try
			{
				if (boardPieces [yInt - 1] [xInt - 1] != null && boardPieces [yInt - 1] [xInt - 1].owner != boardPieces [yInt] [xInt].owner)
				{
					// pretending that the piece was actually moved, then seeing if the king is still in check
					temp = boardPieces [yInt - 1] [xInt - 1];
					boardPieces [yInt - 1] [xInt - 1] = boardPieces [yInt] [xInt];
					boardPieces [yInt] [xInt] = null;
					// if the king is no longer in check, we can end the method knowing the king has not been checkmated
					findKing();
					if (!threatCheck (kingX, kingY))
					{
						boardTiles [yInt - 1] [xInt - 1].setBackground(Color.GREEN);
						boardTiles [yInt - 1] [xInt - 1].setActionCommand("k" + (xInt - 1) + "x" + (yInt - 1));
						boardPieces [yInt] [xInt] = boardPieces [yInt - 1] [xInt - 1];
						boardPieces [yInt - 1] [xInt - 1] = temp;
					}
					else
					{
						boardPieces [yInt] [xInt] = boardPieces [yInt - 1] [xInt - 1];
						boardPieces [yInt - 1] [xInt - 1] = temp;
					}
				}
			}
			catch (ArrayIndexOutOfBoundsException AIOOBEx)
			{}

			try
			{
				if (boardPieces [yInt - 1] [xInt + 1] != null && boardPieces [yInt - 1] [xInt + 1].owner != boardPieces [yInt] [xInt].owner)
				{
					// pretending that the piece was actually moved, then seeing if the king is still in check
					temp = boardPieces [yInt - 1] [xInt + 1];
					boardPieces [yInt - 1] [xInt + 1] = boardPieces [yInt] [xInt];
					boardPieces [yInt] [xInt] = null;
					// if the king is no longer in check, we can end the method knowing the king has not been checkmated
					findKing();
					if (!threatCheck (kingX, kingY))
					{
						boardTiles [yInt - 1] [xInt + 1].setBackground(Color.GREEN);
						boardTiles [yInt - 1] [xInt + 1].setActionCommand("k" + (xInt + 1) + "x" + (yInt - 1));
						boardPieces [yInt] [xInt] = boardPieces [yInt - 1] [xInt + 1];
						boardPieces [yInt - 1] [xInt + 1] = temp;
					}
					else
					{
						boardPieces [yInt] [xInt] = boardPieces [yInt - 1] [xInt + 1];
						boardPieces [yInt - 1] [xInt + 1] = temp;
					}
				}
			}
			catch (ArrayIndexOutOfBoundsException AIOOBEx)
			{}

			// enpassant capture
			try
			{
				if (boardPieces [yInt] [xInt + 1].enpassant == true && boardPieces [yInt] [xInt + 1].owner != playerTurn)
				{
					// pretending that the piece was actually moved, then seeing if the king is in check
					temp = boardPieces [yInt] [xInt + 1];
					boardPieces [yInt - 1] [xInt + 1] = boardPieces [yInt] [xInt];
					boardPieces [yInt] [xInt] = null;
					findKing();
					if (!threatCheck (kingX, kingY))
					{
						boardTiles [yInt - 1] [xInt + 1].setBackground(Color.GREEN);
						boardTiles [yInt - 1] [xInt + 1].setActionCommand("k" + (xInt + 1) + "x" + (yInt - 1) + "ekr");		// ekr stands for enpassant kill right
						boardPieces [yInt] [xInt] = boardPieces [yInt - 1] [xInt + 1];
						boardPieces [yInt] [xInt + 1] = temp;
					}
					else
					{
						boardPieces [yInt] [xInt] = boardPieces [yInt - 1] [xInt + 1];
						boardPieces [yInt] [xInt + 1] = temp;
					}
				}
			}
			catch (ArrayIndexOutOfBoundsException AIOOBEx)
			{}
			catch (NullPointerException NPEx)
			{}

			try
			{
				if (boardPieces [yInt] [xInt - 1].enpassant == true && boardPieces [yInt] [xInt - 1].owner != playerTurn)
				{
					System.out.println("LEFT EN");
					// pretending that the piece was actually moved, then seeing if the king is in check
					temp = boardPieces [yInt] [xInt - 1];
					boardPieces [yInt - 1] [xInt - 1] = boardPieces [yInt] [xInt];
					boardPieces [yInt] [xInt] = null;
					findKing();
					if (!threatCheck (kingX, kingY))
					{
						boardTiles [yInt - 1] [xInt - 1].setBackground(Color.GREEN);
						boardTiles [yInt - 1] [xInt - 1].setActionCommand("k" + (xInt - 1) + "x" + (yInt - 1) + "ekl");		// ekl stands for enpassant kill left
						boardPieces [yInt] [xInt] = boardPieces [yInt - 1] [xInt - 1];
						boardPieces [yInt] [xInt - 1] = temp;
					}
					else
					{
						boardPieces [yInt] [xInt] = boardPieces [yInt - 1] [xInt - 1];
						boardPieces [yInt] [xInt - 1] = temp;
					}
				}
			}
			catch (ArrayIndexOutOfBoundsException AIOOBEx)
			{}
			catch (NullPointerException NPEx)
			{}
		}

		// if it is a black pawn
		else if (boardPieces [yInt] [xInt].owner == 'b')
		{
			// movement
			try
			{
				if (boardPieces [yInt + 1] [xInt] == null)
				{
					// pretending that the piece was actually moved, then seeing if the king is still in check
					temp = boardPieces [yInt] [xInt];
					boardPieces [yInt + 1] [xInt] = boardPieces [yInt] [xInt];
					boardPieces [yInt] [xInt] = null;
					findKing();
					if (!threatCheck (kingX, kingY))
					{
						boardTiles [yInt + 1] [xInt].setBackground(Color.GREEN);
						boardTiles [yInt + 1] [xInt].setActionCommand("m" + (xInt) + "x" + (yInt + 1));
						boardPieces [yInt] [xInt] = temp;
						boardPieces [yInt + 1] [xInt] = null;
					}
					else
					{
						boardPieces [yInt] [xInt] = temp;
						boardPieces [yInt + 1] [xInt] = null;
					}
				}
				if (boardPieces [yInt] [xInt].hasMoved == false && boardPieces [yInt + 2] [xInt] == null && boardPieces [yInt + 1] [xInt] == null)
				{
					// pretending that the piece was actually moved, then seeing if the king is still in check
					temp = boardPieces [yInt] [xInt];
					boardPieces [yInt + 2] [xInt] = boardPieces [yInt] [xInt];
					boardPieces [yInt] [xInt] = null;
					findKing();
					if (!threatCheck (kingX, kingY))
					{
						boardTiles [yInt + 2] [xInt].setBackground(Color.GREEN);
						boardTiles [yInt + 2] [xInt].setActionCommand("m" + (xInt) + "x" + (yInt + 2) + "en");
						boardPieces [yInt] [xInt] = temp;
						boardPieces [yInt + 2] [xInt] = null;
					}
					else
					{
						boardPieces [yInt] [xInt] = temp;
						boardPieces [yInt + 2] [xInt] = null;
					}
				}
			}
			catch (ArrayIndexOutOfBoundsException AIOOBEx)
			{}

			// taking
			try
			{
				if (boardPieces [yInt + 1] [xInt - 1] != null && boardPieces [yInt + 1] [xInt - 1].owner != boardPieces [yInt] [xInt].owner)
				{
					// pretending that the piece was actually moved, then seeing if the king is still in check
					temp = boardPieces [yInt + 1] [xInt - 1];
					boardPieces [yInt + 1] [xInt - 1] = boardPieces [yInt] [xInt];
					boardPieces [yInt] [xInt] = null;
					// if the king is no longer in check, we can end the method knowing the king has not been checkmated
					findKing();
					if (!threatCheck (kingX, kingY))
					{
						boardTiles [yInt + 1] [xInt - 1].setBackground(Color.GREEN);
						boardTiles [yInt + 1] [xInt - 1].setActionCommand("k" + (xInt - 1) + "x" + (yInt + 1));
						boardPieces [yInt] [xInt] = boardPieces [yInt + 1] [xInt - 1];
						boardPieces [yInt + 1] [xInt - 1] = temp;
					}
					else
					{
						boardPieces [yInt] [xInt] = boardPieces [yInt + 1] [xInt - 1];
						boardPieces [yInt + 1] [xInt - 1] = temp;
					}
				}
			}
			catch (ArrayIndexOutOfBoundsException AIOOBEx)
			{}
			try
			{
				if (boardPieces [yInt + 1] [xInt + 1] != null && boardPieces [yInt + 1] [xInt + 1].owner != boardPieces [yInt] [xInt].owner)
				{
					// pretending that the piece was actually moved, then seeing if the king is still in check
					temp = boardPieces [yInt + 1] [xInt + 1];
					boardPieces [yInt + 1] [xInt + 1] = boardPieces [yInt] [xInt];
					boardPieces [yInt] [xInt] = null;
					// if the king is no longer in check, we can end the method knowing the king has not been checkmated
					findKing();
					if (!threatCheck (kingX, kingY))
					{
						boardTiles [yInt + 1] [xInt + 1].setBackground(Color.GREEN);
						boardTiles [yInt + 1] [xInt + 1].setActionCommand("k" + (xInt + 1) + "x" + (yInt + 1));
						boardPieces [yInt] [xInt] = boardPieces [yInt + 1] [xInt + 1];
						boardPieces [yInt + 1] [xInt + 1] = temp;
					}
					else
					{
						boardPieces [yInt] [xInt] = boardPieces [yInt + 1] [xInt + 1];
						boardPieces [yInt + 1] [xInt + 1] = temp;
					}
				}
			}
			catch (ArrayIndexOutOfBoundsException AIOOBEx)
			{}
			// enpassant
			try
			{
				if (boardPieces [yInt] [xInt + 1].enpassant == true && boardPieces [yInt] [xInt + 1].owner != playerTurn)
				{
					// pretending that the piece was actually moved, then seeing if the king is still in check
					temp = boardPieces [yInt + 1] [xInt + 1];
					boardPieces [yInt + 1] [xInt + 1] = boardPieces [yInt] [xInt];
					boardPieces [yInt] [xInt] = null;
					findKing();
					if (!threatCheck (kingX, kingY))
					{
						boardTiles [yInt + 1] [xInt + 1].setBackground(Color.GREEN);
						boardTiles [yInt + 1] [xInt + 1].setActionCommand("k" + (xInt + 1) + "x" + (yInt + 1) + "ekr");		// ekr stands for enpassant kill right
						boardPieces [yInt] [xInt] = boardPieces [yInt + 1] [xInt + 1];
						boardPieces [yInt + 1] [xInt + 1] = temp;
					}
					else
					{
						boardPieces [yInt] [xInt] = boardPieces [yInt + 1] [xInt + 1];
						boardPieces [yInt + 1] [xInt + 1] = temp;
					}
				}
			}
			catch (ArrayIndexOutOfBoundsException AIOOBEx)
			{}
			catch (NullPointerException NPEx)
			{}

			try 
			{
				if (boardPieces [yInt] [xInt - 1].enpassant == true && boardPieces [yInt] [xInt - 1].owner != playerTurn)
				{
					System.out.println("LEFT EN");
					// pretending that the piece was actually moved, then seeing if the king is still in check
					temp = boardPieces [yInt + 1] [xInt - 1];
					boardPieces [yInt + 1] [xInt - 1] = boardPieces [yInt] [xInt];
					boardPieces [yInt] [xInt] = null;
					findKing();
					if (!threatCheck (kingX, kingY))
					{
						boardTiles [yInt + 1] [xInt - 1].setBackground(Color.GREEN);
						boardTiles [yInt + 1] [xInt - 1].setActionCommand("k" + (xInt - 1) + "x" + (yInt + 1) + "ekl");		// ekl stands for enpassant kill left
						boardPieces [yInt] [xInt] = boardPieces [yInt + 1] [xInt - 1];
						boardPieces [yInt + 1] [xInt - 1] = temp;
					}
					else
					{
						boardPieces [yInt] [xInt] = boardPieces [yInt + 1] [xInt - 1];
						boardPieces [yInt + 1] [xInt - 1] = temp;
					}
				}
			}
			catch (ArrayIndexOutOfBoundsException AIOOBEx)
			{}
			catch (NullPointerException NPEx)
			{}
		}
	}

	// finding x and y coordinates of the king, pretty self explanatory
	private void findKing()
	{
		for (int y = 0; y < boardPieces.length; y++)
		{
			for (int x = 0; x < boardPieces [y].length; x++)
			{
				try
				{
					if (boardPieces [y] [x].isKing == true && boardPieces [y] [x].owner == playerTurn)
					{
						kingX = x;
						kingY = y;
					}
				}
				catch (NullPointerException NPEx)
				{}
			}
		}
	}

	// checking if any kings need to be put in the checked status
	private void kingCheck ()
	{
		count = 0;
		kingX = -1;
		kingY = -1;

		findKing();

		check = threatCheck (kingX, kingY);
	}

	// checking for possible moves any piece on the board can make to get out of checkmate
	private void checkmateCheck()
	{
		checkMate = true;
		for (int yPass = 0; yPass < boardPieces.length; yPass++)
		{
			for (int xPass = 0; xPass < boardPieces[yPass].length; xPass++)
			{
				try 
				{
					checkMateCheckKing(xPass, yPass);

					checkMateCheckRook(xPass, yPass);
					
					checkMateCheckBishop(xPass, yPass);
					
					checkMateCheckKnight(xPass, yPass);
					
					checkMateCheckPawn(xPass, yPass);

					if (checkMate == false)
					{
						return;
					}
				}
				catch (NullPointerException NPEx)
				{}
			}
		}

		System.out.println("BAIT THE " + checkMate);
		return;
		
		// something causes the program to return before checking every piece
	}
	// checking if moving the king can get the king out of check
	private void checkMateCheckKing(int xPass, int yPass)
	{
		// if the piece being checked is a king
		if (boardPieces [yPass] [xPass].kiMove && boardPieces [yPass] [xPass].owner == playerTurn)
		{
			// down
			// catching index out of bounds exceptions
			try 
			{
				if (boardPieces [yPass + 1] [xPass] != null)
				{
					if (boardPieces [yPass + 1] [xPass].owner != boardPieces [yPass] [xPass].owner)
					{
						// pretending that the piece was actually moved, then seeing if the king is still in check
						temp = boardPieces [yPass + 1] [xPass];
						boardPieces [yPass + 1] [xPass] = boardPieces [yPass] [xPass];
						boardPieces [yPass] [xPass] = null;
						// if the king is no longer in check, we can end the method knowing the king has not been checkmated
						findKing();
						if (!threatCheck (kingX, kingY))
						{
							checkMate = false;
							boardPieces [yPass] [xPass] = boardPieces [yPass + 1] [xPass];
							boardPieces [yPass + 1] [xPass] = temp;
							return;
						}
						boardPieces [yPass] [xPass] = boardPieces [yPass + 1] [xPass];
						boardPieces [yPass + 1] [xPass] = temp;
					}
				}
				else
				{
					// pretending that the piece was actually moved, then seeing if the king is still in check
					temp = boardPieces [yPass] [xPass];
					boardPieces [yPass + 1] [xPass] = boardPieces [yPass] [xPass];
					boardPieces [yPass] [xPass] = null;
					// if the king is no longer in check, we can end the method knowing the king has not been checkmated
					findKing();
					if (!threatCheck (kingX, kingY))
					{
						checkMate = false;
						boardPieces [yPass] [xPass] = temp;
						boardPieces [yPass + 1] [xPass] = null;
						return;
					}
					boardPieces [yPass] [xPass] = temp;
					boardPieces [yPass + 1] [xPass] = null;
				}
			} 
			catch (ArrayIndexOutOfBoundsException AIOOBEx)
			{}

			// up
			// catching index out of bounds exceptions
			try 
			{
				if (boardPieces [yPass - 1] [xPass] != null)
				{
					if (boardPieces [yPass - 1] [xPass].owner != boardPieces [yPass] [xPass].owner)
					{
						// pretending that the piece was actually moved, then seeing if the king is still in check
						temp = boardPieces [yPass - 1] [xPass];
						boardPieces [yPass - 1] [xPass] = boardPieces [yPass] [xPass];
						boardPieces [yPass] [xPass] = null;
						// if the king is no longer in check, we can end the method knowing the king has not been checkmated
						findKing();
						if (!threatCheck (kingX, kingY))
						{
							checkMate = false;
							boardPieces [yPass] [xPass] = boardPieces [yPass - 1] [xPass];
							boardPieces [yPass - 1] [xPass] = temp;
							return;
						}
						boardPieces [yPass] [xPass] = boardPieces [yPass - 1] [xPass];
						boardPieces [yPass - 1] [xPass] = temp;
					}
				}
				else
				{
					// pretending that the piece was actually moved, then seeing if the king is still in check
					temp = boardPieces [yPass] [xPass];
					boardPieces [yPass - 1] [xPass] = boardPieces [yPass] [xPass];
					boardPieces [yPass] [xPass] = null;
					// if the king is no longer in check, we can end the method knowing the king has not been checkmated
					findKing();
					if (!threatCheck (kingX, kingY))
					{
						checkMate = false;
						boardPieces [yPass] [xPass] = temp;
						boardPieces [yPass - 1] [xPass] = null;
						return;
					}
					boardPieces [yPass] [xPass] = temp;
					boardPieces [yPass - 1] [xPass] = null;
				}
			} 
			catch (ArrayIndexOutOfBoundsException AIOOBEx)
			{}

			// right
			// catching index out of bounds exceptions
			try 
			{
				if (boardPieces [yPass] [xPass + 1] != null)
				{
					if (boardPieces [yPass] [xPass + 1].owner != boardPieces [yPass] [xPass].owner)
					{
						// pretending that the piece was actually moved, then seeing if the king is still in check
						temp = boardPieces [yPass] [xPass + 1];
						boardPieces [yPass] [xPass + 1] = boardPieces [yPass] [xPass];
						boardPieces [yPass] [xPass] = null;
						// if the king is no longer in check, we can end the method knowing the king has not been checkmated
						findKing();
						if (!threatCheck (kingX, kingY))
						{
							checkMate = false;
							boardPieces [yPass] [xPass] = boardPieces [yPass] [xPass + 1];
							boardPieces [yPass] [xPass + 1] = temp;
							return;
						}
						boardPieces [yPass] [xPass] = boardPieces [yPass] [xPass + 1];
						boardPieces [yPass] [xPass + 1] = temp;
					}
				}
				else
				{
					// pretending that the piece was actually moved, then seeing if the king is still in check
					temp = boardPieces [yPass] [xPass];
					boardPieces [yPass] [xPass + 1] = boardPieces [yPass] [xPass];
					boardPieces [yPass] [xPass] = null;
					// if the king is no longer in check, we can end the method knowing the king has not been checkmated
					findKing();
					if (!threatCheck (kingX, kingY))
					{
						checkMate = false;
						boardPieces [yPass] [xPass] = temp;
						boardPieces [yPass] [xPass + 1] = null;
						return;
					}
					boardPieces [yPass] [xPass] = temp;
					boardPieces [yPass] [xPass + 1] = null;
				}
			} 
			catch (ArrayIndexOutOfBoundsException AIOOBEx)
			{}

			// left
			// catching index out of bounds exceptions
			try 
			{
				if (boardPieces [yPass] [xPass - 1] != null)
				{
					if (boardPieces [yPass] [xPass - 1].owner != boardPieces [yPass] [xPass].owner)
					{
						// pretending that the piece was actually moved, then seeing if the king is still in check
						temp = boardPieces [yPass] [xPass - 1];
						boardPieces [yPass] [xPass - 1] = boardPieces [yPass] [xPass];
						boardPieces [yPass] [xPass] = null;
						// if the king is no longer in check, we can end the method knowing the king has not been checkmated
						findKing();
						if (!threatCheck (kingX, kingY))
						{
							System.out.println("asgfaegeagagvadhgba");
							checkMate = false;
							boardPieces [yPass] [xPass] = boardPieces [yPass] [xPass - 1];
							boardPieces [yPass] [xPass - 1] = temp;
							return;
						}
						boardPieces [yPass] [xPass] = boardPieces [yPass] [xPass - 1];
						boardPieces [yPass] [xPass - 1] = temp;
					}
				}
				else
				{
					// pretending that the piece was actually moved, then seeing if the king is still in check
					temp = boardPieces [yPass] [xPass];
					boardPieces [yPass] [xPass - 1] = boardPieces [yPass] [xPass];
					boardPieces [yPass] [xPass] = null;
					// if the king is no longer in check, we can end the method knowing the king has not been checkmated
					findKing();
					if (!threatCheck (kingX, kingY))
					{
						checkMate = false;
						boardPieces [yPass] [xPass] = temp;
						boardPieces [yPass] [xPass - 1] = null;
						return;
					}
					boardPieces [yPass] [xPass] = temp;
					boardPieces [yPass] [xPass - 1] = null;
				}
			} 
			catch (ArrayIndexOutOfBoundsException AIOOBEx)
			{}

			// bottom right
			// catching index out of bounds exceptions
			try 
			{
				if (boardPieces [yPass + 1] [xPass + 1] != null)
				{
					if (boardPieces [yPass + 1] [xPass + 1].owner != boardPieces [yPass] [xPass].owner)
					{
						// pretending that the piece was actually moved, then seeing if the king is still in check
						temp = boardPieces [yPass + 1] [xPass + 1];
						boardPieces [yPass + 1] [xPass + 1] = boardPieces [yPass] [xPass];
						boardPieces [yPass] [xPass] = null;
						// if the king is no longer in check, we can end the method knowing the king has not been checkmated
						findKing();
						if (!threatCheck (kingX, kingY))
						{
							checkMate = false;
							boardPieces [yPass] [xPass] = boardPieces [yPass + 1] [xPass + 1];
							boardPieces [yPass + 1] [xPass + 1] = temp;
							return;
						}
						boardPieces [yPass] [xPass] = boardPieces [yPass + 1] [xPass + 1];
						boardPieces [yPass + 1] [xPass + 1] = temp;
					}
				}
				else
				{
					// pretending that the piece was actually moved, then seeing if the king is still in check
					temp = boardPieces [yPass] [xPass];
					boardPieces [yPass + 1] [xPass + 1] = boardPieces [yPass] [xPass];
					boardPieces [yPass] [xPass] = null;
					// if the king is no longer in check, we can end the method knowing the king has not been checkmated
					findKing();
					if (!threatCheck (kingX, kingY))
					{
						checkMate = false;
						boardPieces [yPass] [xPass] = temp;
						boardPieces [yPass + 1] [xPass + 1] = null;
						return;
					}
					boardPieces [yPass] [xPass] = temp;
					boardPieces [yPass + 1] [xPass + 1] = null;
				}
			} 
			catch (ArrayIndexOutOfBoundsException AIOOBEx)
			{}

			// bottom left
			// catching index out of bounds exceptions
			try 
			{
				if (boardPieces [yPass + 1] [xPass - 1] != null)
				{
					if (boardPieces [yPass + 1] [xPass - 1].owner != boardPieces [yPass] [xPass].owner)
					{
						// pretending that the piece was actually moved, then seeing if the king is still in check
						temp = boardPieces [yPass + 1] [xPass - 1];
						boardPieces [yPass + 1] [xPass - 1] = boardPieces [yPass] [xPass];
						boardPieces [yPass] [xPass] = null;
						// if the king is no longer in check, we can end the method knowing the king has not been checkmated
						findKing();
						if (!threatCheck (kingX, kingY))
						{
							checkMate = false;
							boardPieces [yPass] [xPass] = boardPieces [yPass + 1] [xPass - 1];
							boardPieces [yPass + 1] [xPass - 1] = temp;
							return;
						}
						boardPieces [yPass] [xPass] = boardPieces [yPass + 1] [xPass - 1];
						boardPieces [yPass + 1] [xPass - 1] = temp;
					}
				}
				else
				{
					// pretending that the piece was actually moved, then seeing if the king is still in check
					temp = boardPieces [yPass] [xPass];
					boardPieces [yPass + 1] [xPass - 1] = boardPieces [yPass] [xPass];
					boardPieces [yPass] [xPass] = null;
					// if the king is no longer in check, we can end the method knowing the king has not been checkmated
					findKing();
					if (!threatCheck (kingX, kingY))
					{
						checkMate = false;
						boardPieces [yPass] [xPass] = temp;
						boardPieces [yPass + 1] [xPass - 1] = null;
						return;
					}
					boardPieces [yPass] [xPass] = temp;
					boardPieces [yPass + 1] [xPass - 1] = null;
				}
			} 
			catch (ArrayIndexOutOfBoundsException AIOOBEx)
			{}

			// top right
			// catching index out of bounds exceptions
			try 
			{
				if (boardPieces [yPass - 1] [xPass + 1] != null)
				{
					if (boardPieces [yPass - 1] [xPass + 1].owner != boardPieces [yPass] [xPass].owner)
					{
						// pretending that the piece was actually moved, then seeing if the king is still in check
						temp = boardPieces [yPass - 1] [xPass + 1];
						boardPieces [yPass - 1] [xPass + 1] = boardPieces [yPass] [xPass];
						boardPieces [yPass] [xPass] = null;
						// if the king is no longer in check, we can end the method knowing the king has not been checkmated
						findKing();
						if (!threatCheck (kingX, kingY))
						{
							checkMate = false;
							boardPieces [yPass] [xPass] = boardPieces [yPass - 1] [xPass + 1];
							boardPieces [yPass - 1] [xPass + 1] = temp;
							return;
						}
						boardPieces [yPass] [xPass] = boardPieces [yPass - 1] [xPass + 1];
						boardPieces [yPass - 1] [xPass + 1] = temp;
					}
				}
				else
				{
					// pretending that the piece was actually moved, then seeing if the king is still in check
					temp = boardPieces [yPass] [xPass];
					boardPieces [yPass - 1] [xPass + 1] = boardPieces [yPass] [xPass];
					boardPieces [yPass] [xPass] = null;
					// if the king is no longer in check, we can end the method knowing the king has not been checkmated
					findKing();
					if (!threatCheck (kingX, kingY))
					{
						checkMate = false;
						boardPieces [yPass] [xPass] = temp;
						boardPieces [yPass - 1] [xPass + 1] = null;
						return;
					}
					boardPieces [yPass] [xPass] = temp;
					boardPieces [yPass - 1] [xPass + 1] = null;
				}
			} 
			catch (ArrayIndexOutOfBoundsException AIOOBEx)
			{}

			// top left
			// catching index out of bounds exceptions
			try 
			{
				if (boardPieces [yPass - 1] [xPass - 1] != null)
				{
					if (boardPieces [yPass - 1] [xPass - 1].owner != boardPieces [yPass] [xPass].owner)
					{
						// pretending that the piece was actually moved, then seeing if the king is still in check
						temp = boardPieces [yPass - 1] [xPass - 1];
						boardPieces [yPass - 1] [xPass - 1] = boardPieces [yPass] [xPass];
						boardPieces [yPass] [xPass] = null;
						// if the king is no longer in check, we can end the method knowing the king has not been checkmated
						findKing();
						if (!threatCheck (kingX, kingY))
						{
							checkMate = false;
							boardPieces [yPass] [xPass] = boardPieces [yPass - 1] [xPass - 1];
							boardPieces [yPass - 1] [xPass - 1] = temp;
							return;
						}
						boardPieces [yPass] [xPass] = boardPieces [yPass - 1] [xPass - 1];
						boardPieces [yPass - 1] [xPass - 1] = temp;
					}
				}
				else
				{
					// pretending that the piece was actually moved, then seeing if the king is still in check
					temp = boardPieces [yPass] [xPass];
					boardPieces [yPass - 1] [xPass - 1] = boardPieces [yPass] [xPass];
					boardPieces [yPass] [xPass] = null;
					// if the king is no longer in check, we can end the method knowing the king has not been checkmated
					findKing();
					if (!threatCheck (kingX, kingY))
					{
						checkMate = false;
						boardPieces [yPass] [xPass] = temp;
						boardPieces [yPass - 1] [xPass - 1] = null;
						return;
					}
					boardPieces [yPass] [xPass] = temp;
					boardPieces [yPass - 1] [xPass - 1] = null;
				}
			} 
			catch (ArrayIndexOutOfBoundsException AIOOBEx)
			{}
		}
	}
	// checking if moving a rook/queen can get the king out of check
	private void checkMateCheckRook(int xPass, int yPass)
	{
		// if the piece being checked is a rook
		if (boardPieces [yPass] [xPass].rMove && boardPieces [yPass] [xPass].owner == playerTurn)
		{
			// this block is for all tiles to the right of the piece
			// catching index out of bounds exceptions
			try 
			{
				for (int x = 1; x < boardTiles.length; x++)
				{
					// if the tile in question has a piece on it
					if (boardPieces [yPass] [xPass + x] != null)
					{
						// if the tile in question has an enemy piece on it
						if (boardPieces [yPass] [xPass + x].owner != boardPieces [yPass] [xPass].owner)
						{
							// pretending that the piece was actually moved, then seeing if the king is still in check
							temp = boardPieces [yPass] [xPass + x];
							boardPieces [yPass] [xPass + x] = boardPieces [yPass] [xPass];
							boardPieces [yPass] [xPass] = null;
							findKing();
							if (!threatCheck (kingX, kingY))
							{
								checkMate = false;
								boardPieces [yPass] [xPass] = boardPieces [yPass] [xPass + x];
								boardPieces [yPass] [xPass + x] = temp;
								return;
							}
							boardPieces [yPass] [xPass] = boardPieces [yPass] [xPass + x];
							boardPieces [yPass] [xPass + x] = temp;
							break;
						}
						else
						{
							break;
						}
					}
					// if the tile in question is empty
					else
					{
						// pretending that the piece was actually moved, then seeing if the king is still in check
						temp = boardPieces [yPass] [xPass];
						boardPieces [yPass] [xPass + x] = boardPieces [yPass] [xPass];
						boardPieces [yPass] [xPass] = null;
						findKing();
						if (!threatCheck (kingX, kingY))
						{
							checkMate = false;
							boardPieces [yPass] [xPass] = temp;
							boardPieces [yPass] [xPass + x] = null;
							return;
						}
						boardPieces [yPass] [xPass] = temp;
						boardPieces [yPass] [xPass + x] = null;
					}
				}
			} 
			catch (ArrayIndexOutOfBoundsException AIOOBEx)
			{}

			// this block is for all tiles to the left of the piece
			// catching index out of bounds exceptions
			try 
			{
				for (int x = 1; x < boardTiles.length; x++)
				{
					// if the tile in question has a piece on it
					if (boardPieces [yPass] [xPass - x] != null)
					{
						// if the tile in question has an enemy piece on it
						if (boardPieces [yPass] [xPass - x].owner != boardPieces [yPass] [xPass].owner)
						{
							// pretending that the piece was actually moved, then seeing if the king is still in check
							temp = boardPieces [yPass] [xPass - x];
							boardPieces [yPass] [xPass - x] = boardPieces [yPass] [xPass];
							boardPieces [yPass] [xPass] = null;
							findKing();
							if (!threatCheck (kingX, kingY))
							{
								checkMate = false;
								boardPieces [yPass] [xPass] = boardPieces [yPass] [xPass - x];
								boardPieces [yPass] [xPass - x] = temp;
								return;
							}
							boardPieces [yPass] [xPass] = boardPieces [yPass] [xPass - x];
							boardPieces [yPass] [xPass - x] = temp;
							break;
						}
						else
						{
							break;
						}
					}
					// if the tile in question is empty
					else
					{
						// pretending that the piece was actually moved, then seeing if the king is still in check
						temp = boardPieces [yPass] [xPass];
						boardPieces [yPass] [xPass - x] = boardPieces [yPass] [xPass];
						boardPieces [yPass] [xPass] = null;
						findKing();
						if (!threatCheck (kingX, kingY))
						{
							checkMate = false;
							boardPieces [yPass] [xPass] = temp;
							boardPieces [yPass] [xPass - x] = null;
							return;
						}
						boardPieces [yPass] [xPass] = temp;
						boardPieces [yPass] [xPass - x] = null;
					}
				}
			} 
			catch (ArrayIndexOutOfBoundsException AIOOBEx)
			{}

			// this block is for all tiles below the piece
			// catching index out of bounds exceptions
			try 
			{
				for (int y = 1; y < boardTiles.length; y++)
				{
					// if the tile in question has a piece on it
					if (boardPieces [yPass + y] [xPass] != null)
					{
						// if the tile in question has an enemy piece on it
						if (boardPieces [yPass + y] [xPass].owner != boardPieces [yPass] [xPass].owner)
						{
							// pretending that the piece was actually moved, then seeing if the king is still in check
							temp = boardPieces [yPass + y] [xPass];
							boardPieces [yPass + y] [xPass] = boardPieces [yPass] [xPass];
							boardPieces [yPass] [xPass] = null;
							findKing();
							if (!threatCheck (kingX, kingY))
							{
								checkMate = false;
								boardPieces [yPass] [xPass] = boardPieces [yPass + y] [xPass];
								boardPieces [yPass + y] [xPass] = temp;
								return;
							}
							boardPieces [yPass] [xPass] = boardPieces [yPass + y] [xPass];
							boardPieces [yPass + y] [xPass] = temp;
							break;
						}
						else
						{
							break;
						}
					}
					// if the tile in question is empty
					else
					{
						// pretending that the piece was actually moved, then seeing if the king is still in check
						temp = boardPieces [yPass] [xPass];
						boardPieces [yPass + y] [xPass] = boardPieces [yPass] [xPass];
						boardPieces [yPass] [xPass] = null;
						findKing();
						if (!threatCheck (kingX, kingY))
						{
							checkMate = false;
							boardPieces [yPass] [xPass] = temp;
							boardPieces [yPass + y] [xPass] = null;
							return;
						}
						boardPieces [yPass] [xPass] = temp;
						boardPieces [yPass + y] [xPass] = null;
					}
				}
			} 
			catch (ArrayIndexOutOfBoundsException AIOOBEx)
			{}

			// this block is for all tiles above the piece
			// catching index out of bounds exceptions
			try 
			{
				for (int y = 1; y < boardTiles.length; y++)
				{
					// if the tile in question has a piece on it
					if (boardPieces [yPass - y] [xPass] != null)
					{
						// if the tile in question has an enemy piece on it
						if (boardPieces [yPass - y] [xPass].owner != boardPieces [yPass] [xPass].owner)
						{
							// pretending that the piece was actually moved, then seeing if the king is still in check
							temp = boardPieces [yPass - y] [xPass];
							boardPieces [yPass - y] [xPass] = boardPieces [yPass] [xPass];
							boardPieces [yPass] [xPass] = null;
							findKing();
							if (!threatCheck (kingX, kingY))
							{
								checkMate = false;
								boardPieces [yPass] [xPass] = boardPieces [yPass - y] [xPass];
								boardPieces [yPass - y] [xPass] = temp;
								return;
							}
							boardPieces [yPass] [xPass] = boardPieces [yPass - y] [xPass];
							boardPieces [yPass - y] [xPass] = temp;
							break;
						}
						else
						{
							break;
						}
					}
					// if the tile in question is empty
					else
					{
						// pretending that the piece was actually moved, then seeing if the king is still in check
						temp = boardPieces [yPass] [xPass];
						boardPieces [yPass - y] [xPass] = boardPieces [yPass] [xPass];
						boardPieces [yPass] [xPass] = null;
						findKing();
						if (!threatCheck (kingX, kingY))
						{
							checkMate = false;
							boardPieces [yPass] [xPass] = temp;
							boardPieces [yPass - y] [xPass] = null;
							return;
						}
						boardPieces [yPass] [xPass] = temp;
						boardPieces [yPass - y] [xPass] = null;
					}
				}
			} 
			catch (ArrayIndexOutOfBoundsException AIOOBEx)
			{}
		}
	}
	// checking if moving a bishop/queen can get the king out of check
	private void checkMateCheckBishop(int xPass, int yPass)
	{
		// if the piece being checked is a bishop
		if (boardPieces [yPass] [xPass].bMove && boardPieces [yPass] [xPass].owner == playerTurn)
		{
			// this block is for all tiles to the bottom right of the piece
			// catching index out of bounds exceptions
			try 
			{
				for (int x = 1; x < boardTiles.length; x++)
				{
					// if the tile in question has a piece on it
					if (boardPieces [yPass + x] [xPass + x] != null)
					{
						// if the tile in question has an enemy piece on it
						if (boardPieces [yPass + x] [xPass + x].owner != boardPieces [yPass] [xPass].owner)
						{
							// pretending that the piece was actually moved, then seeing if the king is still in check
							temp = boardPieces [yPass + x] [xPass + x];
							boardPieces [yPass + x] [xPass + x] = boardPieces [yPass] [xPass];
							boardPieces [yPass] [xPass] = null;
							findKing();
							if (!threatCheck (kingX, kingY))
							{
								checkMate = false;
								boardPieces [yPass] [xPass] = boardPieces [yPass + x] [xPass + x];
								boardPieces [yPass + x] [xPass + x] = temp;
								return;
							}
							boardPieces [yPass] [xPass] = boardPieces [yPass + x] [xPass + x];
							boardPieces [yPass + x] [xPass + x] = temp;
							break;
						}
						else
						{
							break;
						}
					}
					// if the tile in question is empty
					else
					{
						// pretending that the piece was actually moved, then seeing if the king is still in check
						temp = boardPieces [yPass] [xPass];
						boardPieces [yPass + x] [xPass + x] = boardPieces [yPass] [xPass];
						boardPieces [yPass] [xPass] = null;
						findKing();
						if (!threatCheck (kingX, kingY))
						{
							checkMate = false;
							boardPieces [yPass] [xPass] = temp;
							boardPieces [yPass + x] [xPass + x] = null;
							return;
						}
						boardPieces [yPass] [xPass] = temp;
						boardPieces [yPass + x] [xPass + x] = null;
					}
				}
			} 
			catch (ArrayIndexOutOfBoundsException AIOOBEx)
			{}

			// this block is for all tiles to the bottom left of the piece
			// catching index out of bounds exceptions
			try 
			{
				for (int x = 1; x < boardTiles.length; x++)
				{
					// if the tile in question has a piece on it
					if (boardPieces [yPass + x] [xPass - x] != null)
					{
						// if the tile in question has an enemy piece on it
						if (boardPieces [yPass + x] [xPass - x].owner != boardPieces [yPass] [xPass].owner)
						{
							// pretending that the piece was actually moved, then seeing if the king is still in check
							temp = boardPieces [yPass + x] [xPass - x];
							boardPieces [yPass + x] [xPass - x] = boardPieces [yPass] [xPass];
							boardPieces [yPass] [xPass] = null;
							findKing();
							if (!threatCheck (kingX, kingY))
							{
								checkMate = false;
								System.out.println("CHECKMATE IS " + checkMate);
								boardPieces [yPass] [xPass] = boardPieces [yPass + x] [xPass - x];
								boardPieces [yPass + x] [xPass - x] = temp;
								return;
							}
							boardPieces [yPass] [xPass] = boardPieces [yPass + x] [xPass - x];
							boardPieces [yPass + x] [xPass - x] = temp;
							break;
						}
						else
						{
							break;
						}
					}
					// if the tile in question is empty
					else
					{
						// pretending that the piece was actually moved, then seeing if the king is still in check
						temp = boardPieces [yPass] [xPass];
						boardPieces [yPass + x] [xPass - x] = boardPieces [yPass] [xPass];
						boardPieces [yPass] [xPass] = null;
						findKing();
						if (!threatCheck (kingX, kingY))
						{
							checkMate = false;
							boardPieces [yPass] [xPass] = temp;
							boardPieces [yPass + x] [xPass - x] = null;
							return;
						}
						boardPieces [yPass] [xPass] = temp;
						boardPieces [yPass + x] [xPass - x] = null;
					}
				}
			} 
			catch (ArrayIndexOutOfBoundsException AIOOBEx)
			{}

			// this block is for all tiles to the top right of the piece
			// catching index out of bounds exceptions
			try 
			{
				for (int x = 1; x < boardTiles.length; x++)
				{
					// if the tile in question has a piece on it
					if (boardPieces [yPass - x] [xPass + x] != null)
					{
						// if the tile in question has an enemy piece on it
						if (boardPieces [yPass - x] [xPass + x].owner != boardPieces [yPass] [xPass].owner)
						{
							// pretending that the piece was actually moved, then seeing if the king is still in check
							temp = boardPieces [yPass - x] [xPass + x];
							boardPieces [yPass - x] [xPass + x] = boardPieces [yPass] [xPass];
							boardPieces [yPass] [xPass] = null;
							findKing();
							if (!threatCheck (kingX, kingY))
							{
								checkMate = false;
								boardPieces [yPass] [xPass] = boardPieces [yPass - x] [xPass + x];
								boardPieces [yPass - x] [xPass + x] = temp;
								return;
							}
							boardPieces [yPass] [xPass] = boardPieces [yPass - x] [xPass + x];
							boardPieces [yPass - x] [xPass + x] = temp;
							break;
						}
						else
						{
							break;
						}
					}
					// if the tile in question is empty
					else
					{
						// pretending that the piece was actually moved, then seeing if the king is still in check
						temp = boardPieces [yPass] [xPass];
						boardPieces [yPass - x] [xPass + x] = boardPieces [yPass] [xPass];
						boardPieces [yPass] [xPass] = null;
						findKing();
						if (!threatCheck (kingX, kingY))
						{
							checkMate = false;
							boardPieces [yPass] [xPass] = temp;
							boardPieces [yPass - x] [xPass + x] = null;
							
							
							
							
							
							
							
							return;
						}
						boardPieces [yPass] [xPass] = temp;
						boardPieces [yPass - x] [xPass + x] = null;
					}
				}
			} 
			catch (ArrayIndexOutOfBoundsException AIOOBEx)
			{}

			// this block is for all tiles to the top left of the piece
			// catching index out of bounds exceptions
			try 
			{
				for (int x = 1; x < boardTiles.length; x++)
				{
					// if the tile in question has a piece on it
					if (boardPieces [yPass - x] [xPass - x] != null)
					{
						// if the tile in question has an enemy piece on it
						if (boardPieces [yPass - x] [xPass - x].owner != boardPieces [yPass] [xPass].owner)
						{
							// pretending that the piece was actually moved, then seeing if the king is still in check
							temp = boardPieces [yPass - x] [xPass - x];
							boardPieces [yPass - x] [xPass - x] = boardPieces [yPass] [xPass];
							boardPieces [yPass] [xPass] = null;
							findKing();
							if (!threatCheck (kingX, kingY))
							{
								checkMate = false;
								boardPieces [yPass] [xPass] = boardPieces [yPass - x] [xPass - x];
								boardPieces [yPass - x] [xPass - x] = temp;
								return;
							}
							boardPieces [yPass] [xPass] = boardPieces [yPass - x] [xPass - x];
							boardPieces [yPass - x] [xPass - x] = temp;
							break;
						}
						else
						{
							break;
						}
					}
					// if the tile in question is empty
					else
					{
						// pretending that the piece was actually moved, then seeing if the king is still in check
						temp = boardPieces [yPass] [xPass];
						boardPieces [yPass - x] [xPass - x] = boardPieces [yPass] [xPass];
						boardPieces [yPass] [xPass] = null;
						findKing();
						if (!threatCheck (kingX, kingY))
						{
							checkMate = false;
							boardPieces [yPass] [xPass] = temp;
							boardPieces [yPass - x] [xPass - x] = null;
							return;
						}
						boardPieces [yPass] [xPass] = temp;
						boardPieces [yPass - x] [xPass - x] = null;
					}
				}
			} 
			catch (ArrayIndexOutOfBoundsException AIOOBEx)
			{}
		}
	}
	// checking if moving a knight can get the king out of check
	private void checkMateCheckKnight(int xPass, int yPass)
	{
		// if the piece being checked is a knight
		if (boardPieces [yPass] [xPass].knMove && boardPieces [yPass] [xPass].owner == playerTurn)
		{
			// 2 down, 1 right
			try 
			{
				// if the tile in question has a piece on it
				if (boardPieces [yPass + 2] [xPass + 1] != null)
				{
					// if the tile in question has an enemy piece on it
					if (boardPieces [yPass + 2] [xPass + 1].owner != boardPieces [yPass] [xPass].owner)
					{
						// pretending that the piece was actually moved, then seeing if the king is still in check
						temp = boardPieces [yPass + 2] [xPass + 1];
						boardPieces [yPass + 2] [xPass + 1] = boardPieces [yPass] [xPass];
						boardPieces [yPass] [xPass] = null;
						findKing();
						if (!threatCheck (kingX, kingY))
						{
							checkMate = false;
							boardPieces [yPass] [xPass] = boardPieces [yPass + 2] [xPass + 1];
							boardPieces [yPass + 2] [xPass + 1] = temp;
							return;
						}
						boardPieces [yPass] [xPass] = boardPieces [yPass + 2] [xPass + 1];
						boardPieces [yPass + 2] [xPass + 1] = temp;
					}
				}
				// if the tile in question is empty
				else
				{
					// pretending that the piece was actually moved, then seeing if the king is still in check
					temp = boardPieces [yPass] [xPass];
					boardPieces [yPass + 2] [xPass + 1] = boardPieces [yPass] [xPass];
					boardPieces [yPass] [xPass] = null;
					findKing();
					if (!threatCheck (kingX, kingY))
					{
						checkMate = false;
						boardPieces [yPass] [xPass] = temp;
						boardPieces [yPass + 2] [xPass + 1] = null;
						return;
					}
					boardPieces [yPass] [xPass] = temp;
					boardPieces [yPass + 2] [xPass + 1] = null;
				}
			} 
			catch (ArrayIndexOutOfBoundsException AIOOBEx)
			{}

			// 2 down, 1 left
			try 
			{
				// if the tile in question has a piece on it
				if (boardPieces [yPass + 2] [xPass - 1] != null)
				{
					// if the tile in question has an enemy piece on it
					if (boardPieces [yPass + 2] [xPass - 1].owner != boardPieces [yPass] [xPass].owner)
					{
						// pretending that the piece was actually moved, then seeing if the king is still in check
						temp = boardPieces [yPass + 2] [xPass - 1];
						boardPieces [yPass + 2] [xPass - 1] = boardPieces [yPass] [xPass];
						boardPieces [yPass] [xPass] = null;
						findKing();
						if (!threatCheck (kingX, kingY))
						{
							checkMate = false;
							boardPieces [yPass] [xPass] = boardPieces [yPass + 2] [xPass - 1];
							boardPieces [yPass + 2] [xPass - 1] = temp;
							return;
						}
						boardPieces [yPass] [xPass] = boardPieces [yPass + 2] [xPass - 1];
						boardPieces [yPass + 2] [xPass - 1] = temp;
					}
				}
				// if the tile in question is empty
				else
				{
					// pretending that the piece was actually moved, then seeing if the king is still in check
					temp = boardPieces [yPass] [xPass];
					boardPieces [yPass + 2] [xPass - 1] = boardPieces [yPass] [xPass];
					boardPieces [yPass] [xPass] = null;
					findKing();
					if (!threatCheck (kingX, kingY))
					{
						checkMate = false;
						boardPieces [yPass] [xPass] = temp;
						boardPieces [yPass + 2] [xPass - 1] = null;
						return;
					}
					boardPieces [yPass] [xPass] = temp;
					boardPieces [yPass + 2] [xPass - 1] = null;
				}
			} 
			catch (ArrayIndexOutOfBoundsException AIOOBEx)
			{}

			// 2 up, 1 right
			try 
			{
				// if the tile in question has a piece on it
				if (boardPieces [yPass - 2] [xPass + 1] != null)
				{
					// if the tile in question has an enemy piece on it
					if (boardPieces [yPass - 2] [xPass + 1].owner != boardPieces [yPass] [xPass].owner)
					{
						// pretending that the piece was actually moved, then seeing if the king is still in check
						temp = boardPieces [yPass - 2] [xPass + 1];
						boardPieces [yPass - 2] [xPass + 1] = boardPieces [yPass] [xPass];
						boardPieces [yPass] [xPass] = null;
						findKing();
						if (!threatCheck (kingX, kingY))
						{
							checkMate = false;
							boardPieces [yPass] [xPass] = boardPieces [yPass - 2] [xPass + 1];
							boardPieces [yPass - 2] [xPass + 1] = temp;
							return;
						}
						boardPieces [yPass] [xPass] = boardPieces [yPass - 2] [xPass + 1];
						boardPieces [yPass - 2] [xPass + 1] = temp;
					}
				}
				// if the tile in question is empty
				else
				{
					// pretending that the piece was actually moved, then seeing if the king is still in check
					temp = boardPieces [yPass] [xPass];
					boardPieces [yPass - 2] [xPass + 1] = boardPieces [yPass] [xPass];
					boardPieces [yPass] [xPass] = null;
					findKing();
					if (!threatCheck (kingX, kingY))
					{
						checkMate = false;
						boardPieces [yPass] [xPass] = temp;
						boardPieces [yPass - 2] [xPass + 1] = null;
						return;
					}
					boardPieces [yPass] [xPass] = temp;
					boardPieces [yPass - 2] [xPass + 1] = null;
				}
			} 
			catch (ArrayIndexOutOfBoundsException AIOOBEx)
			{}

			// 2 up, 1 left
			try 
			{
				// if the tile in question has a piece on it
				if (boardPieces [yPass - 2] [xPass - 1] != null)
				{
					// if the tile in question has an enemy piece on it
					if (boardPieces [yPass - 2] [xPass - 1].owner != boardPieces [yPass] [xPass].owner)
					{
						// pretending that the piece was actually moved, then seeing if the king is still in check
						temp = boardPieces [yPass - 2] [xPass - 1];
						boardPieces [yPass - 2] [xPass - 1] = boardPieces [yPass] [xPass];
						boardPieces [yPass] [xPass] = null;
						findKing();
						if (!threatCheck (kingX, kingY))
						{
							checkMate = false;
							boardPieces [yPass] [xPass] = boardPieces [yPass - 2] [xPass - 1];
							boardPieces [yPass - 2] [xPass - 1] = temp;
							return;
						}
						boardPieces [yPass] [xPass] = boardPieces [yPass - 2] [xPass - 1];
						boardPieces [yPass - 2] [xPass - 1] = temp;
					}
				}
				// if the tile in question is empty
				else
				{
					// pretending that the piece was actually moved, then seeing if the king is still in check
					temp = boardPieces [yPass] [xPass];
					boardPieces [yPass - 2] [xPass - 1] = boardPieces [yPass] [xPass];
					boardPieces [yPass] [xPass] = null;
					findKing();
					if (!threatCheck (kingX, kingY))
					{
						checkMate = false;
						boardPieces [yPass] [xPass] = temp;
						boardPieces [yPass - 2] [xPass - 1] = null;
						return;
					}
					boardPieces [yPass] [xPass] = temp;
					boardPieces [yPass - 2] [xPass - 1] = null;
				}
			} 
			catch (ArrayIndexOutOfBoundsException AIOOBEx)
			{}

			// 1 down, 2 right
			try 
			{
				// if the tile in question has a piece on it
				if (boardPieces [yPass + 1] [xPass + 2] != null)
				{
					// if the tile in question has an enemy piece on it
					if (boardPieces [yPass + 1] [xPass + 2].owner != boardPieces [yPass] [xPass].owner)
					{
						// pretending that the piece was actually moved, then seeing if the king is still in check
						temp = boardPieces [yPass + 1] [xPass + 2];
						boardPieces [yPass + 1] [xPass + 2] = boardPieces [yPass] [xPass];
						boardPieces [yPass] [xPass] = null;
						findKing();
						if (!threatCheck (kingX, kingY))
						{
							checkMate = false;
							boardPieces [yPass] [xPass] = boardPieces [yPass + 1] [xPass + 2];
							boardPieces [yPass + 1] [xPass + 2] = temp;
							return;
						}
						boardPieces [yPass] [xPass] = boardPieces [yPass + 1] [xPass + 2];
						boardPieces [yPass + 1] [xPass + 2] = temp;
					}
				}
				// if the tile in question is empty
				else
				{
					// pretending that the piece was actually moved, then seeing if the king is still in check
					temp = boardPieces [yPass] [xPass];
					boardPieces [yPass + 1] [xPass + 2] = boardPieces [yPass] [xPass];
					boardPieces [yPass] [xPass] = null;
					findKing();
					if (!threatCheck (kingX, kingY))
					{
						checkMate = false;
						boardPieces [yPass] [xPass] = temp;
						boardPieces [yPass + 1] [xPass + 2] = null;
						return;
					}
					boardPieces [yPass] [xPass] = temp;
					boardPieces [yPass + 1] [xPass + 2] = null;
				}
			} 
			catch (ArrayIndexOutOfBoundsException AIOOBEx)
			{}

			// 1 down, 2 left
			try 
			{
				// if the tile in question has a piece on it
				if (boardPieces [yPass + 1] [xPass - 2] != null)
				{
					// if the tile in question has an enemy piece on it
					if (boardPieces [yPass + 1] [xPass - 2].owner != boardPieces [yPass] [xPass].owner)
					{
						// pretending that the piece was actually moved, then seeing if the king is still in check
						temp = boardPieces [yPass + 1] [xPass - 2];
						boardPieces [yPass + 1] [xPass - 2] = boardPieces [yPass] [xPass];
						boardPieces [yPass] [xPass] = null;
						findKing();
						if (!threatCheck (kingX, kingY))
						{
							checkMate = false;
							boardPieces [yPass] [xPass] = boardPieces [yPass + 1] [xPass - 2];
							boardPieces [yPass + 1] [xPass - 2] = temp;
							return;
						}
						boardPieces [yPass] [xPass] = boardPieces [yPass + 1] [xPass - 2];
						boardPieces [yPass + 1] [xPass - 2] = temp;
					}
				}
				// if the tile in question is empty
				else
				{
					// pretending that the piece was actually moved, then seeing if the king is still in check
					temp = boardPieces [yPass] [xPass];
					boardPieces [yPass + 1] [xPass - 2] = boardPieces [yPass] [xPass];
					boardPieces [yPass] [xPass] = null;
					findKing();
					if (!threatCheck (kingX, kingY))
					{
						checkMate = false;
						boardPieces [yPass] [xPass] = temp;
						boardPieces [yPass + 1] [xPass - 2] = null;
						return;
					}
					boardPieces [yPass] [xPass] = temp;
					boardPieces [yPass + 1] [xPass - 2] = null;
				}
			} 
			catch (ArrayIndexOutOfBoundsException AIOOBEx)
			{}

			// 1 up, 2 right
			try 
			{
				// if the tile in question has a piece on it
				if (boardPieces [yPass - 1] [xPass + 2] != null)
				{
					// if the tile in question has an enemy piece on it
					if (boardPieces [yPass - 1] [xPass + 2].owner != boardPieces [yPass] [xPass].owner)
					{
						// pretending that the piece was actually moved, then seeing if the king is still in check
						temp = boardPieces [yPass - 1] [xPass + 2];
						boardPieces [yPass - 1] [xPass + 2] = boardPieces [yPass] [xPass];
						boardPieces [yPass] [xPass] = null;
						findKing();
						if (!threatCheck (kingX, kingY))
						{
							checkMate = false;
							boardPieces [yPass] [xPass] = boardPieces [yPass - 1] [xPass + 2];
							boardPieces [yPass - 1] [xPass + 2] = temp;
							return;
						}
						boardPieces [yPass] [xPass] = boardPieces [yPass - 1] [xPass + 2];
						boardPieces [yPass - 1] [xPass + 2] = temp;
					}
				}
				// if the tile in question is empty
				else
				{
					// pretending that the piece was actually moved, then seeing if the king is still in check
					temp = boardPieces [yPass] [xPass];
					boardPieces [yPass - 1] [xPass + 2] = boardPieces [yPass] [xPass];
					boardPieces [yPass] [xPass] = null;
					findKing();
					if (!threatCheck (kingX, kingY))
					{
						checkMate = false;
						boardPieces [yPass] [xPass] = temp;
						boardPieces [yPass - 1] [xPass + 2] = null;
						return;
					}
					boardPieces [yPass] [xPass] = temp;
					boardPieces [yPass - 1] [xPass + 2] = null;
				}
			} 
			catch (ArrayIndexOutOfBoundsException AIOOBEx)
			{}

			// 1 up, 2 left
			try 
			{
				// if the tile in question has a piece on it
				if (boardPieces [yPass - 1] [xPass - 2] != null)
				{
					// if the tile in question has an enemy piece on it
					if (boardPieces [yPass - 1] [xPass - 2].owner != boardPieces [yPass] [xPass].owner)
					{
						// pretending that the piece was actually moved, then seeing if the king is still in check
						temp = boardPieces [yPass - 1] [xPass - 2];
						boardPieces [yPass - 1] [xPass - 2] = boardPieces [yPass] [xPass];
						boardPieces [yPass] [xPass] = null;
						findKing();
						if (!threatCheck (kingX, kingY))
						{
							checkMate = false;
							boardPieces [yPass] [xPass] = boardPieces [yPass - 1] [xPass - 2];
							boardPieces [yPass - 1] [xPass - 2] = temp;
							return;
						}
						boardPieces [yPass] [xPass] = boardPieces [yPass - 1] [xPass - 2];
						boardPieces [yPass - 1] [xPass - 2] = temp;
					}
				}
				// if the tile in question is empty
				else
				{
					// pretending that the piece was actually moved, then seeing if the king is still in check
					temp = boardPieces [yPass] [xPass];
					boardPieces [yPass - 1] [xPass - 2] = boardPieces [yPass] [xPass];
					boardPieces [yPass] [xPass] = null;
					findKing();
					if (!threatCheck (kingX, kingY))
					{
						checkMate = false;
						boardPieces [yPass] [xPass] = temp;
						boardPieces [yPass - 1] [xPass - 2] = null;
						return;
					}
					boardPieces [yPass] [xPass] = temp;
					boardPieces [yPass - 1] [xPass - 2] = null;
				}
			} 
			catch (ArrayIndexOutOfBoundsException AIOOBEx)
			{}
		}
	}
	// checking if moving a pawn can get the king out of check
	private void checkMateCheckPawn(int xPass, int yPass)
	{
		// if the piece being checked is a pawn
		if (boardPieces [yPass] [xPass].pMove && boardPieces [yPass] [xPass].owner == playerTurn)
		{
			// if the piece being checked is a white pawn
			if (boardPieces [yPass] [xPass].owner == 'w')
			{
				// catching index out of bounds exceptions
				try
				{
					// checking if moving the pawn one piece forward will get the king out of check
					if (boardPieces [yPass - 1] [xPass] == null) 
					{
						// pretending that the piece was actually moved, then seeing if the king is still in check
						temp = boardPieces [yPass] [xPass];
						boardPieces [yPass - 1] [xPass] = boardPieces [yPass] [xPass];
						boardPieces [yPass] [xPass] = null;
						findKing();
						if (!threatCheck (kingX, kingY))
						{
							checkMate = false;
							boardPieces [yPass] [xPass] = temp;
							boardPieces [yPass - 1] [xPass] = null;
							return;
						}
						boardPieces [yPass] [xPass] = temp;
						boardPieces [yPass - 1] [xPass] = null;
					}
					
					// checking if moving the pawn two pieces forward will get the king out of check
					if (boardPieces [yPass] [xPass].hasMoved == false && boardPieces [yPass - 2] [xPass] == null && boardPieces [yPass - 1] [xPass] == null)
					{
						// pretending that the piece was actually moved, then seeing if the king is still in check
						temp = boardPieces [yPass] [xPass];
						boardPieces [yPass - 2] [xPass] = boardPieces [yPass] [xPass];
						boardPieces [yPass] [xPass] = null;
						findKing();
						if (!threatCheck (kingX, kingY))
						{
							checkMate = false;
							boardPieces [yPass] [xPass] = temp;
							boardPieces [yPass - 2] [xPass] = null;
							return;
						}
						boardPieces [yPass] [xPass] = temp;
						boardPieces [yPass - 2] [xPass] = null;
					}
					
					// checking if taking pieces with the pawn will put the king out of check
					if (boardPieces [yPass - 1] [xPass - 1] != null && boardPieces [yPass - 1] [xPass - 1].owner != boardPieces [yPass] [xPass].owner)
					{
						// pretending that the piece was actually moved, then seeing if the king is still in check
						temp = boardPieces [yPass - 1] [xPass - 1];
						boardPieces [yPass - 1] [xPass - 1] = boardPieces [yPass] [xPass];
						boardPieces [yPass] [xPass] = null;
						findKing();
						if (!threatCheck (kingX, kingY))
						{
							checkMate = false;
							boardPieces [yPass] [xPass] = boardPieces [yPass - 1] [xPass - 1];
							boardPieces [yPass - 1] [xPass - 1] = temp;
							return;
						}
						boardPieces [yPass] [xPass] = boardPieces [yPass - 1] [xPass - 1];
						boardPieces [yPass - 1] [xPass - 1] = temp;
					}
					if (boardPieces [yPass - 1] [xPass + 1] != null && boardPieces [yPass - 1] [xPass + 1].owner != boardPieces [yPass] [xPass].owner)
					{
						// pretending that the piece was actually moved, then seeing if the king is still in check
						temp = boardPieces [yPass - 1] [xPass + 1];
						boardPieces [yPass - 1] [xPass + 1] = boardPieces [yPass] [xPass];
						boardPieces [yPass] [xPass] = null;
						findKing();
						if (!threatCheck (kingX, kingY))
						{
							checkMate = false;
							boardPieces [yPass] [xPass] = boardPieces [yPass - 1] [xPass + 1];
							boardPieces [yPass - 1] [xPass + 1] = temp;
							return;
						}
						boardPieces [yPass] [xPass] = boardPieces [yPass - 1] [xPass + 1];
						boardPieces [yPass - 1] [xPass + 1] = temp;
					}
					
					// checking if taking a piece using the enpassant capture will put the king out of check
					if (boardPieces [yPass] [xPass - 1] != null && boardPieces [yPass] [xPass - 1].owner != boardPieces [yPass] [xPass].owner && boardPieces [yPass] [xPass - 1].enpassant == true)
					{
						if (boardPieces [yPass - 1] [xPass - 1] == null)
						{
							// pretending that the piece was actually moved, then seeing if the king is still in check
							temp = boardPieces [yPass - 1] [xPass - 1];
							boardPieces [yPass - 1] [xPass - 1] = boardPieces [yPass] [xPass];
							boardPieces [yPass] [xPass] = null;
							findKing();
							if (!threatCheck (kingX, kingY))
							{
								checkMate = false;
								boardPieces [yPass] [xPass] = boardPieces [yPass - 1] [xPass - 1];
								boardPieces [yPass - 1] [xPass - 1] = temp;
								return;
							}
							boardPieces [yPass] [xPass] = boardPieces [yPass - 1] [xPass - 1];
							boardPieces [yPass - 1] [xPass - 1] = temp;
						}
					}
					if (boardPieces [yPass] [xPass + 1] != null && boardPieces [yPass] [xPass + 1].owner != boardPieces [yPass] [xPass].owner && boardPieces [yPass] [xPass + 1].enpassant == true)
					{
						if (boardPieces [yPass - 1] [xPass + 1] == null)
						{
							// pretending that the piece was actually moved, then seeing if the king is still in check
							temp = boardPieces [yPass - 1] [xPass + 1];
							boardPieces [yPass - 1] [xPass + 1] = boardPieces [yPass] [xPass];
							boardPieces [yPass] [xPass] = null;
							findKing();
							if (!threatCheck (kingX, kingY))
							{
								checkMate = false;
								boardPieces [yPass] [xPass] = boardPieces [yPass - 1] [xPass + 1];
								boardPieces [yPass - 1] [xPass + 1] = temp;
								return;
							}
							boardPieces [yPass] [xPass] = boardPieces [yPass - 1] [xPass + 1];
							boardPieces [yPass - 1] [xPass + 1] = temp;
						}
					}
				} 
				catch (ArrayIndexOutOfBoundsException AIOOBEx)
				{}
			}
			// if the piece being checked is a black pawn
			else if (boardPieces [yPass] [xPass].owner == 'b')
			{
				// catching index out of bounds exceptions
				try
				{
					// checking if moving the pawn one piece forward will get the king out of check
					if (boardPieces [yPass + 1] [xPass] == null) 
					{
						// pretending that the piece was actually moved, then seeing if the king is still in check
						temp = boardPieces [yPass] [xPass];
						boardPieces [yPass + 1] [xPass] = boardPieces [yPass] [xPass];
						boardPieces [yPass] [xPass] = null;
						findKing();
						if (!threatCheck (kingX, kingY))
						{
							checkMate = false;
							boardPieces [yPass] [xPass] = temp;
							boardPieces [yPass + 1] [xPass] = null;
							return;
						}
						boardPieces [yPass] [xPass] = temp;
						boardPieces [yPass + 1] [xPass] = null;
					}
					
					// checking if moving the pawn two pieces forward will get the king out of check
					if (boardPieces [yPass] [xPass].hasMoved == false && boardPieces [yPass + 2] [xPass] == null && boardPieces [yPass + 1] [xPass] == null)
					{
						// pretending that the piece was actually moved, then seeing if the king is still in check
						temp = boardPieces [yPass] [xPass];
						boardPieces [yPass + 2] [xPass] = boardPieces [yPass] [xPass];
						boardPieces [yPass] [xPass] = null;
						findKing();
						if (!threatCheck (kingX, kingY))
						{
							checkMate = false;
							boardPieces [yPass] [xPass] = temp;
							boardPieces [yPass + 2] [xPass] = null;
							return;
						}
						boardPieces [yPass] [xPass] = temp;
						boardPieces [yPass + 2] [xPass] = null;
					}
					
					// checking if taking a piece with the pawn will get the king out of check
					if (boardPieces [yPass + 1] [xPass - 1] != null && boardPieces [yPass + 1] [xPass - 1].owner != boardPieces [yPass] [xPass].owner)
					{
						// pretending that the piece was actually moved, then seeing if the king is still in check
						temp = boardPieces [yPass + 1] [xPass - 1];
						boardPieces [yPass + 1] [xPass - 1] = boardPieces [yPass] [xPass];
						boardPieces [yPass] [xPass] = null;
						findKing();
						if (!threatCheck (kingX, kingY))
						{
							checkMate = false;
							boardPieces [yPass] [xPass] = boardPieces [yPass + 1] [xPass - 1];
							boardPieces [yPass + 1] [xPass - 1] = temp;
							return;
						}
						boardPieces [yPass] [xPass] = boardPieces [yPass + 1] [xPass - 1];
						boardPieces [yPass + 1] [xPass - 1] = temp;
					}
					if (boardPieces [yPass + 1] [xPass + 1] != null && boardPieces [yPass + 1] [xPass + 1].owner != boardPieces [yPass] [xPass].owner)
					{
						// pretending that the piece was actually moved, then seeing if the king is still in check
						temp = boardPieces [yPass + 1] [xPass + 1];
						boardPieces [yPass + 1] [xPass + 1] = boardPieces [yPass] [xPass];
						boardPieces [yPass] [xPass] = null;
						findKing();
						if (!threatCheck (kingX, kingY))
						{
							checkMate = false;
							boardPieces [yPass] [xPass] = boardPieces [yPass + 1] [xPass + 1];
							boardPieces [yPass + 1] [xPass + 1] = temp;
							return;
						}
						boardPieces [yPass] [xPass] = boardPieces [yPass + 1] [xPass + 1];
						boardPieces [yPass + 1] [xPass + 1] = temp;
					}
					
					// checking if taking a piece using the enpassant capture will put the king out of check
					if (boardPieces [yPass] [xPass - 1] != null && boardPieces [yPass] [xPass - 1].owner != boardPieces [yPass] [xPass].owner && boardPieces [yPass] [xPass - 1].enpassant == true)
					{
						if (boardPieces [yPass + 1] [xPass - 1] == null)
						{
							// pretending that the piece was actually moved, then seeing if the king is still in check
							temp = boardPieces [yPass + 1] [xPass - 1];
							boardPieces [yPass + 1] [xPass - 1] = boardPieces [yPass] [xPass];
							boardPieces [yPass] [xPass] = null;
							findKing();
							if (!threatCheck (kingX, kingY))
							{
								checkMate = false;
								boardPieces [yPass] [xPass] = boardPieces [yPass + 1] [xPass - 1];
								boardPieces [yPass + 1] [xPass - 1] = temp;
								return;
							}
							boardPieces [yPass] [xPass] = boardPieces [yPass + 1] [xPass - 1];
							boardPieces [yPass + 1] [xPass - 1] = temp;
						}
					}
					if (boardPieces [yPass] [xPass + 1] != null && boardPieces [yPass] [xPass + 1].owner != boardPieces [yPass] [xPass].owner && boardPieces [yPass] [xPass + 1].enpassant == true)
					{
						if (boardPieces [yPass + 1] [xPass + 1] == null)
						{
							// pretending that the piece was actually moved, then seeing if the king is still in check
							temp = boardPieces [yPass + 1] [xPass + 1];
							boardPieces [yPass + 1] [xPass + 1] = boardPieces [yPass] [xPass];
							boardPieces [yPass] [xPass] = null;
							findKing();
							if (!threatCheck (kingX, kingY))
							{
								checkMate = false;
								boardPieces [yPass] [xPass] = boardPieces [yPass + 1] [xPass + 1];
								boardPieces [yPass + 1] [xPass + 1] = temp;
								return;
							}
							boardPieces [yPass] [xPass] = boardPieces [yPass + 1] [xPass + 1];
							boardPieces [yPass + 1] [xPass + 1] = temp;
						}
					}
				} 
				catch (ArrayIndexOutOfBoundsException AIOOBEx)
				{}
			}
		}
	}
	
	// checking for threats on a certain piece, keeping in mind the player's turn
	private boolean threatCheck (int x, int y)
	{		
		// pawns only attack/move in one way, so depending on the player's turn we focus on different spots
		if (playerTurn == 'w')
		{
			try
			{
				if (boardPieces [y - 1] [x - 1].type.equals("pawn") && boardPieces [y - 1] [x - 1].owner != playerTurn)
				{
					return true;
				}
			}
			catch (NullPointerException NPEx)
			{}
			catch (ArrayIndexOutOfBoundsException AIOOBEx)
			{}

			try
			{
				if (boardPieces [y - 1] [x + 1].type.equals("pawn") && (boardPieces [y - 1] [x + 1].owner != boardPieces [y] [x].owner))
				{
					return true;
				}
			}
			catch (NullPointerException NPEx)
			{}
			catch (ArrayIndexOutOfBoundsException AIOOBEx)
			{}
		}
		else if (playerTurn == 'b')
		{
			try
			{
				if (boardPieces [y + 1] [x - 1].type.equals("pawn") && boardPieces [y + 1] [x - 1].owner != playerTurn)
				{
					return true;
				}
			}
			catch (NullPointerException NPEx)
			{}
			catch (ArrayIndexOutOfBoundsException AIOOBEx)
			{}

			try
			{
				if (boardPieces [y + 1] [x + 1].type.equals("pawn") && (boardPieces [y + 1] [x + 1].owner != boardPieces [y] [x].owner))
				{
					return true;
				}
			}
			catch (NullPointerException NPEx)
			{}
			catch (ArrayIndexOutOfBoundsException AIOOBEx)
			{}
		}

		// if there is a piece that moves like a rook on the same rank or file as 
		// the tile, not being blocked by another piece, then the tile is threatened
		for (int i = 1; i < boardPieces.length; i++)
		{
			// checking file below
			try
			{
				if (boardPieces [y + i] [x] != null)
				{
					if (boardPieces [y + i] [x].owner == playerTurn)
					{
						break;
					}
					else
					{
						if (boardPieces [y + i] [x].rMove == true)
						{
							return true;
						}
						else
						{
							break;
						}
					}
				}
			}
			catch (ArrayIndexOutOfBoundsException AIOOBEx)
			{}
		}

		for (int i = 1; i < boardPieces.length; i++)
		{
			// checking file above
			try
			{
				if (boardPieces [y - i] [x] != null)
				{
					if (boardPieces [y - i] [x].owner == playerTurn)
					{
						break;
					}
					else
					{
						if (boardPieces [y - i] [x].rMove == true)
						{
							return true;
						}
						else
						{
							break;
						}
					}
				}
			}
			catch (ArrayIndexOutOfBoundsException AIOOBEx)
			{}
		}

		for (int i = 1; i < boardPieces.length; i++)
		{
			// checking file right
			try
			{
				if (boardPieces [y] [x + i] != null)
				{
					if (boardPieces [y] [x + i].owner == playerTurn)
					{
						break;
					}
					else
					{
						if (boardPieces [y] [x + i].rMove == true)
						{
							return true;
						}
						else
						{
							break;
						}
					}
				}
			}
			catch (ArrayIndexOutOfBoundsException AIOOBEx)
			{}
		}

		for (int i = 1; i < boardPieces.length; i++)
		{
			// checking file left
			try
			{
				if (boardPieces [y] [x - i] != null)
				{
					if (boardPieces [y] [x - i].owner == playerTurn)
					{
						break;
					}
					else
					{
						if (boardPieces [y] [x - i].rMove == true)
						{
							return true;
						}
						else
						{
							break;
						}
					}
				}
			}
			catch (ArrayIndexOutOfBoundsException AIOOBEx)
			{}
		}

		// if there is a piece that moves like a bishop on the same diagonal as
		// the king, not being blocked by another piece, then the king is in check
		for (int i = 1; i < boardPieces.length; i++)
		{
			// checking the diagonal down and to the right
			try 
			{
				if (boardPieces [y + i] [x + i] != null)
				{
					if (boardPieces [y + i] [x + i].owner == playerTurn)
					{
						break;
					}
					else
					{
						if (boardPieces [y + i] [x + i].bMove == true)
						{
							return true;
						}
						else
						{
							break;
						}
					}
				}
			}
			catch (ArrayIndexOutOfBoundsException AIOOBEx)
			{}
		}

		for (int i = 1; i < boardPieces.length; i++)
		{
			// checking the diagonal down and to the left
			try 
			{
				if (boardPieces [y + i] [x - i] != null)
				{
					if (boardPieces [y + i] [x - i].owner == playerTurn)
					{
						break;
					}
					else
					{
						if (boardPieces [y + i] [x - i].bMove == true)
						{
							return true;
						}
						else
						{
							break;
						}
					}
				}
			}
			catch (ArrayIndexOutOfBoundsException AIOOBEx)
			{}
		}

		for (int i = 1; i < boardPieces.length; i++)
		{
			// checking the diagonal up and to the right
			try 
			{
				if (boardPieces [y - i] [x + i] != null)
				{
					if (boardPieces [y - i] [x + i].owner == playerTurn)
					{
						break;
					}
					else
					{
						if (boardPieces [y - i] [x + i].bMove == true)
						{
							return true;
						}
						else
						{
							break;
						}
					}
				}
			}
			catch (ArrayIndexOutOfBoundsException AIOOBEx)
			{}
		}

		for (int i = 1; i < boardPieces.length; i++)
		{
			// checking the diagonal up and to the left
			try 
			{
				if (boardPieces [y - i] [x - i] != null)
				{
					if (boardPieces [y - i] [x - i].owner == playerTurn)
					{
						break;
					}
					else
					{
						if (boardPieces [y - i] [x - i].bMove == true)
						{
							return true;
						}
						else
						{
							break;
						}
					}
				}
			}
			catch (ArrayIndexOutOfBoundsException AIOOBEx)
			{}
		}

		// if a knight is within it's L shaped distance away from the king, the king is in check
		// i don't actually need this stuff in the for loop im just keeping it here to keep everything together

		// down 2 right 1
		try
		{
			if (boardPieces [y + 2] [x + 1] != null)
			{
				if (boardPieces [y + 2] [x + 1].owner != playerTurn)
				{
					if (boardPieces [y + 2] [x + 1].knMove == true)
					{
						return true;
					}
				}
			}
		}
		catch (ArrayIndexOutOfBoundsException AIOOBEx)
		{}

		// down 2 left 1
		try
		{
			if (boardPieces [y + 2] [x - 1] != null)
			{
				if (boardPieces [y + 2] [x - 1].owner != playerTurn)
				{
					if (boardPieces [y + 2] [x - 1].knMove == true)
					{
						return true;
					}
				}
			}
		}
		catch (ArrayIndexOutOfBoundsException AIOOBEx)
		{}

		// up 2 right 1
		try
		{
			if (boardPieces [y - 2] [x + 1] != null)
			{
				if (boardPieces [y - 2] [x + 1].owner != playerTurn)
				{
					if (boardPieces [y - 2] [x + 1].knMove == true)
					{
						return true;
					}
				}
			}
		}
		catch (ArrayIndexOutOfBoundsException AIOOBEx)
		{}

		// up 2 left 1
		try
		{
			if (boardPieces [y - 2] [x - 1] != null)
			{
				if (boardPieces [y - 2] [x - 1].owner != playerTurn)
				{
					if (boardPieces [y - 2] [x - 1].knMove == true)
					{
						return true;
					}
				}
			}
		}
		catch (ArrayIndexOutOfBoundsException AIOOBEx)
		{}

		// down 1 right 2
		try
		{
			if (boardPieces [y + 1] [x + 2] != null)
			{
				if (boardPieces [y + 1] [x + 2].owner != playerTurn)
				{
					if (boardPieces [y + 1] [x + 2].knMove == true)
					{
						return true;
					}
				}
			}
		}
		catch (ArrayIndexOutOfBoundsException AIOOBEx)
		{}

		// down 1 left 2
		try
		{
			if (boardPieces [y + 1] [x - 2] != null)
			{
				if (boardPieces [y + 1] [x - 2].owner != playerTurn)
				{
					if (boardPieces [y + 1] [x - 2].knMove == true)
					{
						return true;
					}
				}
			}
		}
		catch (ArrayIndexOutOfBoundsException AIOOBEx)
		{}

		// up 1 right 2
		try
		{
			if (boardPieces [y - 1] [x + 2] != null)
			{
				if (boardPieces [y - 1] [x + 2].owner != playerTurn)
				{
					if (boardPieces [y - 1] [x + 2].knMove == true)
					{
						return true;
					}
				}
			}
		}
		catch (ArrayIndexOutOfBoundsException AIOOBEx)
		{}

		// up 1 left 2
		try
		{
			if (boardPieces [y - 1] [x - 2] != null)
			{
				if (boardPieces [y - 1] [x - 2].owner != playerTurn)
				{
					if (boardPieces [y - 1] [x - 2].knMove == true)
					{
						return true;
					}
				}
			}
		}
		catch (ArrayIndexOutOfBoundsException AIOOBEx)
		{}

		// Kings
		// down
		try
		{
			if (boardPieces [y + 1] [x] != null)
			{
				if (boardPieces [y + 1] [x].owner != playerTurn)
				{
					if (boardPieces [y + 1] [x].kiMove == true)
					{
						return true;
					}
				}
			}
		}
		catch (ArrayIndexOutOfBoundsException AIOOBEx)
		{}

		// up
		try
		{
			if (boardPieces [y - 1] [x] != null)
			{
				if (boardPieces [y - 1] [x].owner != playerTurn)
				{
					if (boardPieces [y - 1] [x].kiMove == true)
					{
						return true;
					}
				}
			}
		}
		catch (ArrayIndexOutOfBoundsException AIOOBEx)
		{}

		// right
		try
		{
			if (boardPieces [y] [x + 1] != null)
			{
				if (boardPieces [y] [x + 1].owner != playerTurn)
				{
					if (boardPieces [y] [x + 1].kiMove == true)
					{
						return true;
					}
				}
			}
		}
		catch (ArrayIndexOutOfBoundsException AIOOBEx)
		{}

		// left
		try
		{
			if (boardPieces [y] [x - 1] != null)
			{
				if (boardPieces [y] [x - 1].owner != playerTurn)
				{
					if (boardPieces [y] [x - 1].kiMove == true)
					{
						return true;
					}
				}
			}
		}
		catch (ArrayIndexOutOfBoundsException AIOOBEx)
		{}

		// bottom right
		try
		{
			if (boardPieces [y + 1] [x + 1] != null)
			{
				if (boardPieces [y + 1] [x + 1].owner != playerTurn)
				{
					if (boardPieces [y + 1] [x + 1].kiMove == true)
					{
						return true;
					}
				}
			}
		}
		catch (ArrayIndexOutOfBoundsException AIOOBEx)
		{}

		// bottom left
		try
		{
			if (boardPieces [y + 1] [x - 1] != null)
			{
				if (boardPieces [y + 1] [x - 1].owner != playerTurn)
				{
					if (boardPieces [y + 1] [x - 1].kiMove == true)
					{
						return true;
					}
				}
			}
		}
		catch (ArrayIndexOutOfBoundsException AIOOBEx)
		{}

		// top right
		try
		{
			if (boardPieces [y - 1] [x + 1] != null)
			{
				if (boardPieces [y - 1] [x + 1].owner != playerTurn)
				{
					if (boardPieces [y - 1] [x + 1].kiMove == true)
					{
						return true;
					}
				}
			}
		}
		catch (ArrayIndexOutOfBoundsException AIOOBEx)
		{}

		// top left
		try
		{
			if (boardPieces [y - 1] [x - 1] != null)
			{
				if (boardPieces [y - 1] [x - 1].owner != playerTurn)
				{
					if (boardPieces [y - 1] [x - 1].kiMove == true)
					{
						return true;
					}
				}
			}
		}
		catch (ArrayIndexOutOfBoundsException AIOOBEx)
		{}

		return false;
	}

	// checking if a pawn can promote
	private void promoteCheck ()
	{
		if (boardPieces [yIntMove] [xIntMove] != null && boardPieces [yIntMove] [xIntMove].type.equals("pawn"))
		{
			if ( (boardPieces [yIntMove] [xIntMove].owner == 'w' && yIntMove == 0) || (boardPieces [yIntMove] [xIntMove].owner == 'b' && yIntMove == 7))
			{
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

				if (s.equals("Queen"))
				{
					boardPieces [yIntMove] [xIntMove].type = "queen";
					boardPieces [yIntMove] [xIntMove].pMove = false;
					boardPieces [yIntMove] [xIntMove].bMove = true;
					boardPieces [yIntMove] [xIntMove].rMove = true;

					if (boardPieces [yIntMove] [xIntMove].owner == 'w')
					{
						boardTiles [yIntMove] [xIntMove].setIcon(wQueen);
					}
					else if (boardPieces [yIntMove] [xIntMove].owner == 'b')
					{
						boardTiles [yIntMove] [xIntMove].setIcon(bQueen);
					}
				}
				else if (s.equals("Knight"))
				{
					boardPieces [yIntMove] [xIntMove].type = "knight";
					boardPieces [yIntMove] [xIntMove].pMove = false;
					boardPieces [yIntMove] [xIntMove].knMove = true;

					if (boardPieces [yIntMove] [xIntMove].owner == 'w')
					{
						boardTiles [yIntMove] [xIntMove].setIcon(wKnight);
					}
					else if (boardPieces [yIntMove] [xIntMove].owner == 'b')
					{
						boardTiles [yIntMove] [xIntMove].setIcon(bKnight);
					}
				}
				else if (s.equals("Rook"))
				{
					boardPieces [yIntMove] [xIntMove].type = "rook";
					boardPieces [yIntMove] [xIntMove].pMove = false;
					boardPieces [yIntMove] [xIntMove].rMove = true;

					if (boardPieces [yIntMove] [xIntMove].owner == 'w')
					{
						boardTiles [yIntMove] [xIntMove].setIcon(wRook);
					}
					else if (boardPieces [yIntMove] [xIntMove].owner == 'b')
					{
						boardTiles [yIntMove] [xIntMove].setIcon(bRook);
					}
				}
				else if (s.equalsIgnoreCase("Bishop"))
				{
					boardPieces [yIntMove] [xIntMove].type = "bishop";
					boardPieces [yIntMove] [xIntMove].pMove = false;
					boardPieces [yIntMove] [xIntMove].bMove = true;

					if (boardPieces [yIntMove] [xIntMove].owner == 'w')
					{
						boardTiles [yIntMove] [xIntMove].setIcon(wBishop);
					}
					else if (boardPieces [yIntMove] [xIntMove].owner == 'b')
					{
						boardTiles [yIntMove] [xIntMove].setIcon(bBishop);
					}
				}
				boardPieces [yIntMove] [xIntMove].hasMoved = true;
			}
		}
	}

	// extracts integers from event names, used for debugging
	private int intGet(int location, String eventName)
	{
		int temp;
		temp = Character.getNumericValue(eventName.charAt(location));
		return temp;
	}

	// switches turns
	private void turnSwitch ()
	{
		// if it's the white player's turn, it switches to black
		if (playerTurn == 'w')
		{
			// starting and stopping the appropriate timers
			ExtraInfo.wTime.stop();
			ExtraInfo.bTime.start();
			// value used in other methods
			playerTurn = 'b';
			// changing labels
			ExtraInfo.activePlayerTurn.setText("Black Player's Turn");
		}
		// if it's the black player's turn, it switches to white
		else if (playerTurn == 'b')
		{
			// starting and stopping the appropriate timers
			ExtraInfo.bTime.stop();
			ExtraInfo.wTime.start();
			// value used in other methods
			playerTurn = 'w';
			// changing labels
			ExtraInfo.activePlayerTurn.setText("White Player's Turn");
		}

		// updating the scoreboard
		ExtraInfo.updatePoints(wPoints, bPoints);

		enPassantReset();

		kingCheck();

		if (check)
		{
			checkmateCheck();
			// if checkmate is true, the current player loses
		}
		else
		{
			checkmateCheck();
			// if there are no legal moves, it is a draw
			if (checkMate == true)
			{
				staleMate = true;
				checkMate = false;
			}
		}

		// ending the game
		if (checkMate == true)
		{
			endGame ("mate", playerTurn);
		}
		
		// ending the game
		if (staleMate == true)
		{
			endGame ("stale", 's');
		}
	}

	// ending the game
	public static void endGame (String reason, char loser)
	{
		// turning off the chess board
		for (int y = 0; y < boardTiles.length; y++)
		{
			for (int x = 0; x < boardTiles[y].length; x++)
			{
				boardTiles [y][x].setActionCommand(null);
			}
		}

		try {
			chessGame.setVisible(false);
			GameOver gameOverScreen = new GameOver (reason, loser, wasRanked);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// an easier way to print out values
	private void traceStatement (int passVal)
	{
		System.out.println(passVal);
	}

	// when a button is pressed
	@Override
	public void actionPerformed(ActionEvent event) {

		String eventName = event.getActionCommand();

		// System.out.println(eventName.charAt(0));

		System.out.println(eventName);

		// castling
		if (eventName.contains("cas"))
		{
			// extracting x and y values from the action command
			xIntMove = intGet (1, eventName);
			yIntMove = intGet (3, eventName);
			
			// because checks for possible threats to the king's position is already calculated, 
			// we know that if the tile lights up the king will not be in check	

			boardPieces [yIntMove] [xIntMove] = boardPieces [yInt] [xInt];
			boardTiles [yIntMove] [xIntMove].setIcon(boardTiles [yInt] [xInt].getIcon());

			boardPieces [yInt] [xInt] = null;
			boardTiles [yInt] [xInt].setIcon(null);

			// king side castling
			if (xIntMove == 6)
			{
				boardPieces [yIntMove] [5] = boardPieces [yIntMove] [7];
				boardTiles [yIntMove] [5].setIcon(boardTiles [yIntMove] [7].getIcon());

				boardPieces [yIntMove] [7] = null;
				boardTiles [yIntMove] [7].setIcon(null);
			}
			// queen side castling
			else if (xIntMove == 2)
			{
				boardPieces [yIntMove] [3] = boardPieces [yIntMove] [0];
				boardTiles [yIntMove] [3].setIcon(boardTiles [yIntMove] [0].getIcon());

				boardPieces [yIntMove] [0] = null;
				boardTiles [yIntMove] [0].setIcon(null);
			}

			greenTileClear();

			// resetting values
			xIntMove = -1;
			yIntMove = -1;

			xInt = -1;
			yInt = -1;		
			
			turnSwitch();
		}
		// enpassant
		else if (eventName.contains("ek"))
		{
			// extracting x and y values from the action command
			xIntMove = intGet (1, eventName);
			yIntMove = intGet (3, eventName);
			
			// creating a temporary value to swap pieces around with
			ChessPiece temp = new ChessPiece ('n', "null");
			temp = boardPieces [yInt] [xInt];

			// if it is an enpassant kill to the right
			if (eventName.contains("r"))
			{
				// swapping pieces around
				boardPieces [yInt] [xInt] = null;
				boardPieces [yInt] [xInt + 1] = null;
				boardPieces [yIntMove] [xIntMove] = temp;

				if (playerTurn == 'w')
				{
					// incrementing points
					wPoints += 1;
					
					// swapping pieces around
					boardTiles [yIntMove] [xIntMove].setIcon(boardTiles [yInt] [xInt].getIcon());
					boardTiles [yInt] [xInt].setIcon(null);

					boardPieces [yInt] [xInt + 1] = null;
					boardTiles [yInt] [xInt + 1].setIcon(null);
				}
				else if (playerTurn == 'b')
				{
					// incrementing points
					bPoints += 1;
					
					// swapping pieces around
					boardTiles [yIntMove] [xIntMove].setIcon(boardTiles [yInt] [xInt].getIcon());
					boardTiles [yInt] [xInt].setIcon(null);

					boardPieces [yInt] [xInt + 1] = null;
					boardTiles [yInt] [xInt + 1].setIcon(null);
				}
				
				promoteCheck();

				greenTileClear();

				// resetting values
				xIntMove = -1;
				yIntMove = -1;

				xInt = -1;
				yInt = -1;

				turnSwitch();
			}
			// if it is an enpassant kill to the left
			else if (eventName.contains("l"))
			{
				// swapping pieces around
				boardPieces [yInt] [xInt] = null;
				boardPieces [yInt] [xInt + 1] = null;
				boardPieces [yIntMove] [xIntMove] = temp;

				if (playerTurn == 'w')
				{
					// incrementing points
					wPoints += 1;
					
					// swapping pieces around
					boardTiles [yIntMove] [xIntMove].setIcon(boardTiles [yInt] [xInt].getIcon());
					boardTiles [yInt] [xInt].setIcon(null);

					boardPieces [yInt] [xInt - 1] = null;
					boardTiles [yInt] [xInt - 1].setIcon(null);
				}
				else if (playerTurn == 'b')
				{
					// incrementing points
					bPoints += 1;
					
					// swapping pieces around
					boardTiles [yIntMove] [xIntMove].setIcon(boardTiles [yInt] [xInt].getIcon());
					boardTiles [yInt] [xInt].setIcon(null);

					boardPieces [yInt] [xInt - 1] = null;
					boardTiles [yInt] [xInt - 1].setIcon(null);
				}
				
				promoteCheck();

				greenTileClear();

				// resetting values
				xIntMove = -1;
				yIntMove = -1;

				xInt = -1;
				yInt = -1;

				turnSwitch();
			}
		}
		// movement
		else if (eventName.charAt(0) == 'm')
		{
			// extracting x and y values from the action command
			xIntMove = intGet (1, eventName);
			yIntMove = intGet (3, eventName);
			
			boardPieces [yInt] [xInt].hasMoved = true;

			boardPieces [yIntMove] [xIntMove] = boardPieces [yInt] [xInt];
			boardPieces [yInt] [xInt] = null;

			boardTiles [yIntMove] [xIntMove].setIcon(boardTiles [yInt] [xInt].getIcon());
			boardTiles [yInt] [xInt].setIcon(null);

			if (eventName.contains("en"))
			{
				boardPieces [yIntMove] [xIntMove].enpassant = true;
			}

			promoteCheck();

			greenTileClear();

			// resetting values
			xIntMove = -1;
			yIntMove = -1;

			xInt = -1;
			yInt = -1;

			turnSwitch();

		}
		// taking
		else if (eventName.charAt(0) == 'k')
		{
			// extracting x and y values from the action command
			xIntMove = intGet (1, eventName);
			yIntMove = intGet (3, eventName);
			
			try
			{
				boardPieces [yInt] [xInt].hasMoved = true;
			}
			catch (NullPointerException NPEx)
			{}

			// adding points
			if (playerTurn == 'w')
			{
				wPoints += boardPieces [yIntMove] [xIntMove].pointVal;
			}
			else if (playerTurn == 'b')
			{
				bPoints += boardPieces [yIntMove] [xIntMove].pointVal;
			}

			boardPieces [yIntMove] [xIntMove] = boardPieces [yInt] [xInt];
			boardPieces [yInt] [xInt] = null;

			boardTiles [yIntMove] [xIntMove].setIcon(boardTiles [yInt] [xInt].getIcon());
			boardTiles [yInt] [xInt].setIcon(null);

			promoteCheck();

			greenTileClear();

			// resetting values
			xIntMove = -1;
			yIntMove = -1;

			xInt = -1;
			yInt = -1;

			turnSwitch();
		}
		// selecting
		else
		{
			// extracting x and y values from the action command
			int xIntTemp = intGet (0, eventName);
			int yIntTemp = intGet (2, eventName);

			try
			{
				if (boardPieces [yIntTemp] [xIntTemp].owner == playerTurn)
				{
					xInt = xIntTemp;
					yInt = yIntTemp;

					traceStatement (xInt);
					traceStatement (yInt);

					greenTileClear();

					try
					{
						tileMoveShow();
					}
					catch (NullPointerException NPEx)
					{
						System.out.println("Empty tile!");
					}
				}
			}
			catch (NullPointerException NPEx)
			{}
		}
	}
}
