public class King extends Piece
{
	public boolean isValidMove(Move move, Board board)
	{
		boolean isValid = super.isValidMove(move, board);
		int xMovement = Math.abs(move.getEndX() - move.getStartX());
		int yMovement = Math.abs(move.getEndY() - move.getStartY());
		if(isValid && (xMovement == 1 || yMovement == 1))
		{
			return true; //Check that the King only moved 1 space
		}
		else{
			return false;
		}
	}
}