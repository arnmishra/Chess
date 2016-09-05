package Framework.Pieces;
import Framework.Board;
import Framework.Move;
import Framework.Team;

/**
Parent Piece Class
*/
public abstract class Piece
{
	private Team team; // Team number 0 or 1.
	private int xValue;
	private int yValue;
	
	/**
	 * Constructor that sets piece's team
	 * 
	 * @param teamNumber
	 */
	public Piece(Team team)
	{
		this.team = team;
	}
	
	public int getXValue()
	{
		return this.xValue;
	}
	
	public void setXValue(int xValue)
	{
		this.xValue = xValue;
	}
	
	public int getYValue()
	{
		return this.yValue;
	}
	
	public void setYValue(int yValue)
	{
		this.yValue = yValue;
	}
	
	/**
	 * Get what team this piece is on
	 * 
	 * @return team number
	 */
	public Team getTeam()
	{
		return this.team;
	}
	
	public int getTeamNumber()
	{
		return this.team.getTeamNumber();
	}
	
	/**
	 * Check if move is valid
	 * 
	 * @param move
	 * @param board
	 * @return whether it is valid
	 */
	public abstract boolean isValidMove(Move move, Board board);
	
	public boolean onAvailableSquare(Move move, Board board)
	{
		int endX = move.getEndX();
		int endY = move.getEndY();
		if(board.getWidth() <= endX)
		{
			System.out.print("Off width of board: ");
			return false; // Ensure that this move doesn't put the piece off the board. 
		}
		else if(board.getLength() <= endY)
		{
			System.out.print("Off length of board: ");
			return false; // Ensure that this move doesn't put the piece off the board. 
		}
		
		Piece[][] positions = board.getPositions();
		if(positions[endY][endX] != null)
		{
			int startX = move.getStartX();
			int startY = move.getStartY();
			int replacedPieceTeam = positions[endY][endX].getTeamNumber();
			int currentPieceTeam = positions[startY][startX].getTeamNumber();
			if(replacedPieceTeam == currentPieceTeam)
			{
				System.out.print("End coordinates occupied by same team: ");
				return false;
			}
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
	
	public boolean traverseDiagonal(int startX, int endX, int startY, int endY, Piece[][] positions)
	{
		int xDirection = getDirection(startX, endX);
		int yDirection = getDirection(startY, endY);
		//System.out.println(startX + " " + endX + " " + startY + " " + endY + " " + xDirection + " " + yDirection);
		int i = startY + yDirection;
		int j = startX + xDirection;
		while(endY != i || endX != j)
		{
			if(positions[i][j] != null)
			{
				return false;
			}
			i += yDirection;
			j += xDirection;
		}
		return true;
	}
	
	public int getDirection(int start, int end)
	{
		if(start - end < 0)
		{
			return 1;
		}
		else
		{
			return -1;
		}
	}
	
	public abstract boolean hasNoLeaps(Move move, Board board);
	
	public boolean findCheck(Move move, Board board)
	{
		return false;
	}
	
}