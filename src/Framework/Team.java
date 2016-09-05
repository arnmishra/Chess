package Framework;

import java.util.*;
import Framework.Pieces.*;

public class Team
{
	private boolean inCheck;
	private int teamNumber;
	private List<Piece> pieces = new ArrayList<Piece>();
	
	public Team(int teamNumber)
	{
		this.teamNumber = teamNumber;
	}
	
	public int getTeamNumber()
	{
		return this.teamNumber;
	}
	
	public boolean getInCheck()
	{
		return this.inCheck;
	}
	
	public void setInCheck(boolean inCheck)
	{
		this.inCheck = inCheck;
	}
	
	public void addPieces(Piece[] piece)
	{
		pieces.addAll(Arrays.asList(piece));
	}
	
	public void removePiece(Piece piece)
	{
		pieces.remove(piece);
	}
	
	public List<Piece> getPieces()
	{
		return pieces;
	}
}