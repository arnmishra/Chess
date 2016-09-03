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
}