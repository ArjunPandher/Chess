package mainGame;

public class ChessPiece {
	
	public boolean kiMove = false;		// movement in a box, like a King
	public boolean rMove = false;		// movement in straight lines, like a rook
	public boolean bMove = false;		// movement in diagonal lines, like a bishop
	public boolean knMove = false;		// movement two spaces x axis, one space y axis, like a knight
	public boolean pMove = false;		// movement forwards two spaces for the first turn, one space there
										// after, capturing forward left and forward right, like a pawn
	public boolean isKing = false;
	public boolean kingCheck = false;
	public boolean hasMoved = false;
	public boolean enpassant;
	
	public char owner;
	public String type;
	public int pointVal;
	
	public ChessPiece (char ownerIn, String typeIn)
	{	
		// storing parameters into variable accessible from other classes
		owner = ownerIn;
		type = typeIn;
		
		if (type.equals("queen"))
		{
			rMove = true;
			bMove = true;
			pointVal = 9;
		}
		
		if (type.equals("king"))
		{
			kiMove = true;
			isKing = true;
		}
		
		if (type.equals("rook"))
		{
			rMove = true;
			pointVal = 5;
		}
		
		if (type.equals("bishop"))
		{
			bMove = true;
			pointVal = 3;
		}
		
		if (type.equals("knight"))
		{
			knMove = true;
			pointVal = 3;
		}
		
		if (type.equals("pawn"))
		{
			pMove = true;
			enpassant = false;
			pointVal = 1;
		}
		
		if (isKing == true)
		{
			kingCheck = false;
		}
	
	}
	
}
