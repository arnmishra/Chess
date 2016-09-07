package Framework.Pieces;
import java.util.ArrayList;
import java.util.List;

import Framework.Board;
import Framework.Move;
import Framework.Team;

public class Rook extends Piece
{
	public Rook(Team team) {
		super(team);
	}

	public boolean isValidMove(Move move, Board board)
	{
		boolean isOnBoard = onAvailableSquare(move, board);
		int xMovement = Math.abs(move.getEndX() - move.getStartX());
		int yMovement = Math.abs(move.getEndY() - move.getStartY());
		if(isOnBoard && (xMovement == 0 ^ yMovement == 0))
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
		int startY = move.getStartY();
		int endY = move.getEndY();
		if(endX > startX)
		{
			return traverseRow(startY, startX, endX, positions);
		}
		else if(startX < endX)
		{
			return traverseRow(startY, endX, startX, positions);
		}
		else if(endY > startY)
		{
			return traverseColumn(startX, startY, endY, positions);
		}
		else
		{
			return traverseColumn(startX, endY, startY, positions);
		}
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
		
		return possibleMoves;
	}
}