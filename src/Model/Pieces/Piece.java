package Model.Pieces;
import java.util.*;

import Model.Board;
import Model.Move;
import Model.Team;

/**
Parent Piece Class to hold metadata
*/
public abstract class Piece
{
	private Team team; // Team number 0 or 1.
	private int xValue; // Hold the x coordinate of the piece.
	private int yValue; // Hold the y coordinate of the piece.
	
	/**
	 * Constructor that sets piece's team
	 * 
	 * @param teamNumber
	 */
	public Piece(Team team, int xValue, int yValue)
	{
		this.team = team;
		this.xValue = xValue;
		this.yValue = yValue;
	}
	
	/**
	 * Getter for the x coordinate of the piece.
	 * @return X-coordinate
	 */
	public int getXValue()
	{
		return this.xValue;
	}
	
	/**
	 * Setter for both x and y coordinate of the piece after movement.
	 * @param xValue
	 * @param yValue
	 */
	public void setCoordinates(int xValue, int yValue)
	{
		this.xValue = xValue;
		this.yValue = yValue;
	}
	
	/** 
	 * Getter for the y coordinate of the piece.
	 * @return Y-Coordinate
	 */
	public int getYValue()
	{
		return this.yValue;
	}
	
	/**
	 * Get what team this piece is on.
	 * 
	 * @return team object
	 */
	public Team getTeam()
	{
		return this.team;
	}
	
	/**
	 * Get the piece's team number.
	 * @return team number
	 */
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
	
	/**
	 * Check if the move goes to an available square that is on the board.
	 * @param move
	 * @param board
	 * @return true/false whether it is available
	 */
	public boolean onAvailableSquare(Move move, Board board)
	{
		int endX = move.getEndX();
		int endY = move.getEndY();
		if(board.getWidth() <= endX || endX < 0)
		{
			return false; // Ensure that this move doesn't put the piece off the board. 
		}
		else if(board.getLength() <= endY || endY < 0)
		{
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
				return false; // Ensure that the piece doesn't take another piece of its own team
			}
		}
		return true;
	}
	
	/**
	 * Function to check that there are no pieces being jumped over in the row.
	 * @param row
	 * @param smallX
	 * @param bigX
	 * @param positions
	 * @return if there are leaps
	 */
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
	
	/**
	 * Function to check that there are no pieces being jumped over in the column.
	 * @param row
	 * @param smallX
	 * @param bigX
	 * @param positions
	 * @return if there are leaps
	 */
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
	
	/**
	 * Function to check that there are no pieces being jumped over in the diagonal.
	 * @param row
	 * @param smallX
	 * @param bigX
	 * @param positions
	 * @return if there are leaps
	 */
	public boolean traverseDiagonal(int startX, int endX, int startY, int endY, Piece[][] positions)
	{
		int xDirection = getDirection(startX, endX);
		int yDirection = getDirection(startY, endY);
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
	
	/**
	 * Check what direction the movement is in.
	 * @param start
	 * @param end
	 * @return 1 for increasing coordinate, -1 for decreasing coordinate
	 */
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
	
	/**
	 * Abstract class for pieces to determine whether there is a piece in the way.
	 * @param move
	 * @param board
	 * @return Whether the piece jumps over other pieces.
	 */
	public abstract boolean hasNoLeaps(Move move, Board board);
	
	/**
	 * Abstract class for pieces to find all moves they can make.
	 * @param board
	 * @return List of all possible moves in any direction.
	 */
	public abstract List<Move> findAllMoves(Board board);
	
	/**
	 * Function to get all possible moves for a piece in a specific direction according to the current board.
	 * @param board
	 * @param xDirection
	 * @param yDirection
	 * @param xValue
	 * @param yValue
	 * @return All moves possible by a piece in a direction.
	 */
	public List<Move> getMoves(Board board, int xDirection, int yDirection, int xValue, int yValue)
	{
		int width = board.getWidth();
		int length = board.getLength();
		int changeX = xValue + xDirection;
		int changeY = yValue + yDirection;
		List<Move> possibleMoves = new ArrayList<Move>();
		while(changeX < width && changeX >=0 && changeY < length && changeY >= 0)
		{
			Move newMove = new Move(xValue, yValue, changeX, changeY, this.getTeamNumber());
			boolean isValid = isValidMove(newMove, board);
			if(isValid)
			{
				boolean isCheck = board.isTeamInCheck(newMove);
				if(!isCheck)
				{
					possibleMoves.add(newMove);
				}
			}
			changeX += xDirection;
			changeY += yDirection;
		}
		return possibleMoves;
	}
	
}