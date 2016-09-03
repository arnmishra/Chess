public class Pawn extends Piece
{
	public boolean isValidMove(Move move, Board board)
	{
		boolean isValid = super.isValidMove(move, board);
		boolean firstMove = (move.getStartY() == 1 || move.getStartY() == board.length-2);
		int xMovement = Math.abs(move.getEndX() - move.getStartX());
		int yMovement = Math.abs(move.getEndY() - move.getStartY());
		if(isValid && xMovement == 0 && (yMovement == 1 || (yMovement == 2 && firstMove)))
		{
			return true; //Check that the Pawn only moves 1 or if its the first move, 2
		}
		else{
			return false;
		}
	}
}