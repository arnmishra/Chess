package Model;

import Model.Pieces.Piece;

public class Move
{
	private int startX; // Move's starting X coordinate.
	private int endX; // Move's ending X coordinate.
	private int startY; // Move's starting Y coordinate.
	private int endY; // Move's ending X coordinate.
	private int team; // Team that makes move.
	private Piece removed; //Piece that is replaced by move, null if none
	
	/**
	 * Constructor to initialize move object.
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @param team
	 */
	public Move(int startX, int startY, int endX, int endY, int team){
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.team = team;
	}
	
	/**
	 * Getter for starting X coordinate.
	 * @return starting X coordinate.
	 */
	public int getStartX()
	{
		return this.startX;
	}
	
	/**
	 * Getter for end X coordinate.
	 * @return end X coordinate.
	 */
	public int getEndX()
	{
		return this.endX;
	}
	
	/**
	 * Getter for starting Y coordinate.
	 * @return starting Y coordinate.
	 */
	public int getStartY()
	{
		return this.startY;
	}
	
	/**
	 * Getter for end Y coordinate.
	 * @return end Y coordinate.
	 */
	public int getEndY()
	{
		return this.endY;
	}
	
	/**
	 * Getter for move's team number.
	 * @return move's team number.
	 */
	public int getTeamNumber()
	{
		return this.team;
	}
	
	/**
	 * Getter for removed piece.
	 * @return piece that was removed by move
	 */
	public Piece getRemovedPiece()
	{
		return this.removed;
	}
	
	/**
	 * Setter for removed piece.
	 * @param piece removed by move
	 */
	public void setRemovedPiece(Piece removed)
	{
		this.removed = removed;
	}
}