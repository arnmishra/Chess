/**
Parent Piece Class
*/
public class Piece
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
	 * Set the team for this piece
	 * 
	 * @param teamNumber
	 */
	public void setTeam(int teamNumber)
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
	public boolean isValidMove(Move move, Board board)
	{
		int endX = move.getEndX();
		int endY = move.getEndY();
		if(board.positions[endY][endX] != null)
		{
			return false; // Ensure that no other piece holds this position. 
		}
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
}