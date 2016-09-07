package Framework.Pieces;
import java.util.ArrayList;
import java.util.List;

import Framework.Board;
import Framework.Move;
import Framework.Team;

public class Queen extends Piece
{
	public Queen(Team team) {
		super(team);
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
			else
			{
				noLeaps = traverseDiagonal(startX, endX, startY, endY, positions);
			}
		}
		else if(endX < startX)
		{
			if(endY == startY)
			{
				noLeaps = traverseRow(startY, endX, startX, positions);
				
			}
			else
			{
				noLeaps = traverseDiagonal(startX, endX, startY, endY, positions);
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
	
	@Override
	public List<Move> findAllMoves(Board board) {
		int xValue = this.getXValue();
		int yValue = this.getYValue();
		
		List<Move> possibleMoves = new ArrayList<Move>();
		possibleMoves.addAll(getMoves(board, 1, 0, xValue, yValue));
		possibleMoves.addAll(getMoves(board, -1, 0, xValue, yValue));
		possibleMoves.addAll(getMoves(board, 0, 1, xValue, yValue));
		possibleMoves.addAll(getMoves(board, 0, -1, xValue, yValue));
		possibleMoves.addAll(getMoves(board, 1, 1, xValue, yValue));
		possibleMoves.addAll(getMoves(board, 1, -1, xValue, yValue));
		possibleMoves.addAll(getMoves(board, -1, 1, xValue, yValue));
		possibleMoves.addAll(getMoves(board, -1, -1, xValue, yValue));
		
		return possibleMoves;
	}
}