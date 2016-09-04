package Framework.Pieces;
import Framework.Board;
import Framework.Move;

public class Queen extends Piece
{
	public Queen(int teamNumber) {
		super(teamNumber);
	}

	public boolean isValidMove(Move move, Board board)
	{
		boolean isOnBoard = onAvailableSquare(move, board);
		int xMovement = Math.abs(move.getEndX() - move.getStartX());
		int yMovement = Math.abs(move.getEndY() - move.getStartY());
		if(isOnBoard && (xMovement == 0 || yMovement == 0 || xMovement == yMovement))
		{
			return hasNoLeaps(move, board); //Check that the Queen only moved in one direction or went diagonally
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
		boolean noLeaps;
		if(endX > startX)
		{
			if(endY == startY)
			{
				noLeaps = traverseRow(startY, startX, endX, positions);
				
			}
			else if(endY > startY)
			{
				noLeaps = traverseDiagonal(startX, endX, startY, endY, positions);
			}
			else
			{
				noLeaps = traverseDiagonal(startX, endX, endY, startY, positions);
			}
		}
		else if(endX < startX)
		{
			if(endY == startY)
			{
				noLeaps = traverseRow(startY, endX, startX, positions);
				
			}
			else if(endY > startY)
			{
				noLeaps = traverseDiagonal(endX, startX, startY, endY, positions);
			}
			else
			{
				noLeaps = traverseDiagonal(endX, startX, endY, startY, positions);
			}
		}
		else
		{
			if(endY > startY)
			{
				noLeaps = traverseColumn(startX, startY, endY, positions);
			}
			else
			{
				noLeaps = traverseColumn(startX, endY, startY, positions);
			}
		}
		return noLeaps;
	}
}