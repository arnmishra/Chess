package Framework.Pieces;
import Framework.Board;
import Framework.Move;

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
		Piece[][] positions = board.getPositions();
		int startX = move.getStartX();
		int endX = move.getEndX();
		int startY = move.getStartY();
		int endY = move.getEndY();
		if(endX > startX && endY > startY)
		{
			return traverseDiagonal(startX, endX, startY, endY, positions);
		}
		else if(endX > startX && startY > endY)
		{
			return traverseDiagonal(startX, endX, endY, startY, positions);
		}
		else if(startX > endX && startY > endY)
		{
			return traverseDiagonal(endX, startX, endY, startY, positions);
		}
		else
		{
			return traverseDiagonal(endX, startX, startY, endY, positions);
		}
	}
}