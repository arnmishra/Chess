package Framework.Pieces;
import Framework.Board;
import Framework.Move;

public class Rook extends Piece
{
	public Rook(int teamNumber) {
		super(teamNumber);
	}

	public boolean isValidMove(Move move, Board board)
	{
		boolean isOnBoard = onBoard(move, board);
		int xMovement = Math.abs(move.getEndX() - move.getStartX());
		int yMovement = Math.abs(move.getEndY() - move.getStartY());
		if(isOnBoard && (xMovement == 0 || yMovement == 0))
		{
			return hasNoLeaps(move, board); //Check that the Rook only moved in one direction
		}
		else{
			return false;
		}
	}

	@Override
	public boolean hasNoLeaps(Move move, Board board)  {
		Piece[][] positions = board.getPositions();
		int startX = move.getStartX();
		int endX = move.getEndX();
		int row = move.getStartY();
		if(endX > startX)
		{
			return traverseRow(row, startX, endX, positions);
		}
		else
		{
			return traverseRow(row, endX, startX, positions);
		}
	}
}