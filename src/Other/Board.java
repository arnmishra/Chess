package Other;
import Pieces.Piece;

public class Board
{
	
	protected Piece[][] positions; // Holds positions of all pieces on the board.
	protected int width;
	protected int length;
	
	/**
	 * Get width of board
	 * @return width
	 */
	public int getWidth()
	{
		return this.width;
	}
	
	/**
	 * Set width of board
	 * @param width
	 */
	public void setWidth(int width)
	{
		this.width = width;
	}
	
	/**
	 * Get length of board
	 * @return length
	 */
	public int getLength()
	{
		return this.length;
	}
	
	/**
	 * Set length of board
	 * @param length
	 */
	public void setLength(int length)
	{
		this.length = length;
	}
	
	/**
	 * Get positions on entire chess board
	 * @return positions
	 */
	
	public Piece[][] getPositions()
	{
		return this.positions;
	}
	
	/**
	 * Set the new positions of a chess board after a move
	 * @param move
	 */
	public void setPositions(Move move)
	{
		int startX = move.getStartX();
		int startY = move.getStartY();
		int endX = move.getEndX();
		int endY = move.getEndY();
		
		positions[endY][endX] = positions[startY][startX];
		positions[startY][startX] = null;
	}
}