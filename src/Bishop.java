public class Bishop extends Piece
{
	public boolean isValidMove(Move move, Board board)
	{
		boolean isValid = super.isValidMove(move, board);
		int xMovement = Math.abs(move.getEndX() - move.getStartX());
		int yMovement = Math.abs(move.getEndY() - move.getStartY());
		if(isValid && xMovement == yMovement)
		{
			return true; //Check that the Bishop only moved diagonally
		}
		else{
			return false;
		}
	}
}