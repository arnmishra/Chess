package Model.Pieces;
import java.util.ArrayList;
import java.util.List;

import Model.Board;
import Model.Move;
import Model.Team;

/**
 * Rook class to describe Rook's possible moves.
 * @author arnavmishra
 *
 */
public class Rook extends Piece
{
	/**
	 * Constructor to initialize Rook on a team and coordinate.
	 * @param team
	 */
	public Rook(Team team, int xValue, int yValue) {
		super(team, xValue, yValue);
	}

	/**
	 * Function to check whether a rook's move is valid.
	 * @param move
	 * @param board
	 * @return Whether the move is valid.
	 */
	public boolean isValidMove(Move move, Board board)
	{
		boolean isOnBoard = onAvailableSquare(move, board);
		int xMovement = Math.abs(move.getEndX() - move.getStartX());
		int yMovement = Math.abs(move.getEndY() - move.getStartY());
		if((xMovement == 0 && yMovement == 0) || (xMovement != 0 && yMovement != 0))
		{
			return false; //Not valid if rook doesn't move or if it moves diagonally
		}
		else if(isOnBoard)
		{
			return hasNoLeaps(move, board); //Check that the Rook only moved in one direction
		}
		else{
			return false;
		}
	}

	/**
	 * The rook can't jump over any pieces since it only moves one space.
	 * @param move
	 * @param board
	 * @return whether the rook leaps over pieces.
	 */
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
		else if(endY < startY)
		{
			return traverseColumn(startX, endY, startY, positions);
		}
		return false;
	}

	/**
	 * Function to get all valid possible moves for the King in any direction.
	 * @param board
	 * @return all possible valid moves.
	 */
	@Override
	public List<Move> findAllMoves(Board board) {
		int xValue = this.getXValue();
		int yValue = this.getYValue();
		List<Move> possibleMoves = new ArrayList<Move>();
		possibleMoves.addAll(getMoves(board, 1, 0, xValue, yValue)); // Check right movement.
		possibleMoves.addAll(getMoves(board, -1, 0, xValue, yValue)); // Check left movement.
		possibleMoves.addAll(getMoves(board, 0, 1, xValue, yValue));  // Check downward movement.
		possibleMoves.addAll(getMoves(board, 0, -1, xValue, yValue)); // Check upward movement.
		return possibleMoves;
	}
}