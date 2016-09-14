package Framework.Pieces;
import java.util.ArrayList;
import java.util.List;

import Framework.Board;
import Framework.Move;
import Framework.Team;

/**
 * Pawn class to describe Checker's possible moves.
 * A Checker can move 1 space diagonally in any direction or if there is a piece 
 * diagonally adjacent to it, it can jump over that piece, thus moving 2 diagonally 
 * (as a Checker would in the game Checkers)
 * @author arnavmishra
 *
 */
public class Checker extends Piece
{
	/**
	 * Constructor to initialize Checker on a team and coordinate.
	 * @param team
	 */
	public Checker(Team team, int xValue, int yValue) {
		super(team, xValue, yValue);
	}

	/**
	 * Function to check whether a checker's move is valid.
	 * @param move
	 * @param board
	 * @return Whether the move is valid.
	 */
	public boolean isValidMove(Move move, Board board)
	{
		boolean isValid;
		boolean isOnBoard = onAvailableSquare(move, board);
		
		int xMovement = Math.abs(move.getEndX() - move.getStartX());
		int yMovement = Math.abs(move.getEndY() - move.getStartY());
		
		if(isOnBoard && xMovement == 1 && yMovement == 1) // Check if pawn is on the board and is moving forward.
		{
			return checkNoPiece(move, board); // Check that there is no piece in the destination location
		}
		else if(isOnBoard && yMovement == 2 && xMovement == 2)
		{
			isValid = !hasNoLeaps(move, board); // Check to make sure a piece is being jumped over
		}
		else
		{
			isValid = false;
		}
		return isValid;
	}
	
	/**
	 * Helper function to confirm that there is no piece where the checker is moving
	 * for forward movement.
	 * @param move
	 * @param board
	 * @return whether there is already a piece at the destination
	 */
	public boolean checkNoPiece(Move move, Board board)
	{
		Piece[][] positions = board.getPositions();
		int endX = move.getEndX();
		int endY = move.getEndY();
		Piece currentPiece = positions[endY][endX];
		if(currentPiece != null)
		{
			return false;
		}
		return true;
	}

	
	/**
	 * Check that the pawn doesn't jump over any piece.
	 * @param move
	 * @param board
	 * @return whether the pawn leaps over pieces.
	 */
	@Override
	public boolean hasNoLeaps(Move move, Board board) {
		Piece[][] positions = board.getPositions();
		int startY = move.getStartY();
		int endY = move.getEndY();
		int column = move.getStartX();
		if(endY > startY)
		{
			return traverseColumn(column, startY, endY, positions);
		}
		else if(endY < startY)
		{
			return traverseColumn(column, endY, startY, positions);
		}
		return false;
	}
	
	/**
	 * Function to get all valid possible moves for the Checker in any direction.
	 * @param board
	 * @return all possible valid moves.
	 */
	@Override
	public List<Move> findAllMoves(Board board) {
		int xValue = this.getXValue();
		int yValue = this.getYValue();
		
		List<Move> possibleMoves = new ArrayList<Move>();
		
		possibleMoves.addAll(getMoves(board, 1, 1, xValue, yValue)); // Check up right movement.
		possibleMoves.addAll(getMoves(board, -1, 1, xValue, yValue)); // Check up left movement.
		possibleMoves.addAll(getMoves(board, 1, -1, xValue, yValue)); // Check down right movement.
		possibleMoves.addAll(getMoves(board, -1, -1, xValue, yValue)); // Check down left movement.
		
		return possibleMoves;
	}
}