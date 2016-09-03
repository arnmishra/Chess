package Pieces;
import Other.Board;
import Other.Move;

/**
Parent Piece Class
*/
public abstract class Piece
{
	protected int team; // Team number 0 or 1.
	
	/**
	 * Get what team this piece is on
	 * 
	 * @return team number
	 */
	public int getTeam()
	{
		return this.team;
	}
	
	/**
	 * Constructor that sets piece's team
	 * 
	 * @param teamNumber
	 */
	public Piece(int teamNumber)
	{
		this.team = teamNumber;
	}
	
	/**
	 * Check if move is valid
	 * 
	 * @param move
	 * @param board
	 * @return whether it is valid
	 */
	public abstract boolean isValidMove(Move move, Board board);
	
	public boolean onBoard(Move move, Board board)
	{
		int endX = move.getEndX();
		int endY = move.getEndY();
		if(board.getWidth() < endX)
		{
			return false; // Ensure that this move doesn't put the piece off the board. 
		}
		else if(board.getLength() > endY)
		{
			return false; // Ensure that this move doesn't put the piece off the board. 
		}
		return true;
	}
	
	public boolean traverseRow(int row, int smallX, int bigX, Piece[][] positions)
	{
		for(int i = smallX + 1; i < bigX; i++)
		{
			if(positions[row][i] != null)
			{
				return false;
			}
		}
		return true;
	}
	
	public boolean traverseColumn(int column, int smallY, int bigY, Piece[][] positions)
	{
		for(int i = smallY + 1; i < bigY; i++)
		{
			if(positions[i][column] != null)
			{
				return false;
			}
		}
		return true;
	}
	
	public boolean traverseDiagonal(int smallX, int bigX, int smallY, int bigY, Piece[][] positions)
	{
		for(int i = smallY + 1; i < bigY; i++)
		{
			for(int j = smallX + 1; i < bigX; i++)
			{
				if(positions[i][j] != null)
					{
						return false;
					}
			}
		}
		return true;
	}
	
	public abstract boolean hasNoLeaps(Move move, Board board);
	
}