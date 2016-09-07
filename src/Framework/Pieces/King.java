package Framework.Pieces;
import java.util.ArrayList;
import java.util.List;

import Framework.Board;
import Framework.Move;
import Framework.Team;

public class King extends Piece
{
	public King(Team team) {
		super(team);
	}

	public boolean isValidMove(Move move, Board board)
	{
		boolean isOnBoard = onAvailableSquare(move, board);
		int xMovement = Math.abs(move.getEndX() - move.getStartX());
		int yMovement = Math.abs(move.getEndY() - move.getStartY());
		if(isOnBoard && (xMovement == 1 || yMovement == 1))
		{
			return hasNoLeaps(move, board); //Check that the King only moved 1 space
		}
		else{
			return false;
		}
	}

	@Override
	public boolean hasNoLeaps(Move move, Board board) {
		// Kings only move one space so they can't leap
		return true;
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