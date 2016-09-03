package Pieces;
import Other.Board;
import Other.Move;

public class Bishop extends Piece
{
	public Bishop(int teamNumber) {
		super(teamNumber);
	}

	public boolean isValidMove(Move move, Board board)
	{
		boolean isOnBoard = onBoard(move, board);
		int xMovement = Math.abs(move.getEndX() - move.getStartX());
		int yMovement = Math.abs(move.getEndY() - move.getStartY());
		if(isOnBoard && xMovement == yMovement)
		{
			return hasNoLeaps(move, board); //Check that the Bishop only moved diagonally
		}
		else{
			return false;
		}
	}

	@Override
	public boolean hasNoLeaps(Move move, Board board) {
		// TODO Auto-generated method stub
		return false;
	}
}