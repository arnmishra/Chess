package Model.Pieces;
import java.util.ArrayList;
import java.util.List;

import Model.Board;
import Model.Move;
import Model.Team;

/**
 * Queen class to describe Queen's possible moves.
 * @author arnavmishra
 *
 */
public class Queen extends Piece
{
	/**
	 * Constructor to initialize Queen on a team and coordinate.
	 * @param team
	 */
	public Queen(Team team, int xValue, int yValue) {
		super(team, xValue, yValue);
	}

	/**
	 * Function to check whether a queen's move is valid.
	 * @param move
	 * @param board
	 * @return Whether the move is valid.
	 */
	public boolean isValidMove(Move move, Board board)
	{
		boolean isOnBoard = onAvailableSquare(move, board);
		int xMovement = Math.abs(move.getEndX() - move.getStartX());
		int yMovement = Math.abs(move.getEndY() - move.getStartY());
		if(isOnBoard && (xMovement == 0 || yMovement == 0 || xMovement == yMovement))
		{
			return hasNoPieceInMovementRoute(move, board); //Check that the Queen only moved in one direction or went diagonally
		}
		else{
			return false;
		}
	}

	/**
	 * The queen can't jump over any pieces since it only moves one space.
	 * @param move
	 * @param board
	 * @return whether the queen leaps over pieces.
	 */
	@Override
	public boolean hasNoPieceInMovementRoute(Move move, Board board) {
		Piece[][] positions = board.getPositions();
		int startX = move.getStartX();
		int endX = move.getEndX();
		int startY = move.getStartY();
		int endY = move.getEndY();
		boolean noLeaps = false;
		if(endX > startX)
		{
			if(endY == startY) // For right straight movement.
			{
				noLeaps = traverseRow(startY, startX, endX, positions);
				
			}
			else // For right diagonal movement.
			{
				noLeaps = traverseDiagonal(startX, endX, startY, endY, positions);
			}
		}
		else if(endX < startX)
		{
			if(endY == startY) // For left straight movement.
			{
				noLeaps = traverseRow(startY, endX, startX, positions);
				
			}
			else // For left diagonal movement.
			{
				noLeaps = traverseDiagonal(startX, endX, startY, endY, positions);
			}
		}
		else
		{
			if(endY > startY) // For downward straight movement.
			{
				noLeaps = traverseColumn(startX, startY, endY, positions);
			}
			else if(endY < startY) // For upward straight movement.
			{
				noLeaps = traverseColumn(startX, endY, startY, positions);
			}
		}
		return noLeaps;
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
		possibleMoves.addAll(getMoves(board, 1, 1, xValue, yValue)); // Check movement towards bottom right.
		possibleMoves.addAll(getMoves(board, 1, -1, xValue, yValue)); // Check movement towards top right.
		possibleMoves.addAll(getMoves(board, -1, 1, xValue, yValue)); // Check movement towards bottom left.
		possibleMoves.addAll(getMoves(board, -1, -1, xValue, yValue)); // Check movement towards top left.
		
		return possibleMoves;
	}
}