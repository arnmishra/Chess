public class Rook extends Piece
{
	public boolean isValidMove(Move move, Board board)
	{
		boolean isValid = super.isValidMove(move, board);
		int xMovement = Math.abs(move.getEndX() - move.getStartX());
		int yMovement = Math.abs(move.getEndY() - move.getStartY());
		if(isValid && (xMovement == 0 || yMovement == 0))
		{
			return true; //Check that the Rook only moved in one direction
		}
		else{
			return false;
		}
	}
}