package Pieces;
import Other.Board;
import Other.Move;

public class Queen extends Piece
{
	public Queen(int teamNumber) {
		super(teamNumber);
	}

	public boolean isValidMove(Move move, Board board)
	{
		boolean isOnBoard = onBoard(move, board);
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
		boolean checkRow, checkColumn, checkDiagonal;
		if(endX > startX)
		{
			checkRow = traverseRow(startY, startX, endX, positions);
			if(endY > startY)
			{
				checkColumn = traverseColumn(startX, startY, endY, positions);
				checkDiagonal = traverseDiagonal(startX, endX, startY, endY, positions);
			}
			else
			{
				checkColumn = traverseColumn(startX, endY, startY, positions);
				checkDiagonal = traverseDiagonal(startX, endX, endY, startY, positions);
			}
		}
		else
		{
			checkRow = traverseRow(startY, startX, endX, positions);
			if(endY > startY)
			{
				checkColumn = traverseColumn(startX, startY, endY, positions);
				checkDiagonal = traverseDiagonal(endX, startX, startY, endY, positions);
			}
			else
			{
				checkColumn = traverseColumn(startX, endY, startY, positions);
				checkDiagonal = traverseDiagonal(endX, startX, endY, startY, positions);
			}
		}
		return checkRow && checkColumn && checkDiagonal;
	}
}