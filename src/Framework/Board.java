package Framework;
import Framework.Pieces.*;

public class Board
{
	
	private Piece[][] positions; // Holds positions of all pieces on the board.
	private int width;
	private int length;
	private boolean zeroCheck;
	private boolean oneCheck;
	
	public Board(int width, int length)
	{
		this.width = width;
		this.length = length;
		this.positions = setBoard();
		
	}
	
	public Piece[][] setBoard()
	{
		Piece[][] positions = new Piece[this.length][this.width];
		positions[0] = setPrimaryPieces(positions[0], 0);
		positions[7] = setPrimaryPieces(positions[7], 1);
		for(int i = 0; i < 8; i++)
		{
			positions[1][i] = new Pawn(0);
			positions[6][i] = new Pawn(1);
		}
		return positions;
	}
	
	public Piece[] setPrimaryPieces(Piece[] primaryRow, int team)
	{
		primaryRow[0] = new Rook(team);
		primaryRow[1] = new Knight(team);
		primaryRow[2] = new Bishop(team);
		primaryRow[3] = new King(team);
		primaryRow[4] = new Queen(team);
		primaryRow[5] = new Bishop(team);
		primaryRow[6] = new Knight(team);
		primaryRow[7] = new Rook(team);
		return primaryRow;
	}
	
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
	
	public boolean getZeroCheck()
	{
		return this.zeroCheck;
	}
	
	public boolean getOneCheck()
	{
		return this.oneCheck;
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