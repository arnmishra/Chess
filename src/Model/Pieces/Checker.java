package Model.Pieces;
import java.util.ArrayList;
import java.util.List;

import Model.Board;
import Model.Move;
import Model.Team;

/**
 * Pawn class to describe Checker's possible moves.
 * A Checker can move 1 space diagonally in any direction or if there is a piece 
 * diagonally adjacent to it, it can jump over that piece, thus moving 2 diagonally 
 * (similar to a Checker would in the game Checkers). Unlike checkers, to kill pieces,
 * the checker moves diagonally 1 space like a pawn would. To put the king in check, the
 * Checker must be 1 square diagonal from the King.
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
			return true;
		}
		else if(isOnBoard && yMovement == 2 && xMovement == 2)
		{
			 // Check to make sure a piece is being jumped over and there is no opponent piece at the destination
			isValid = !hasNoPieceInMovementRoute(move, board) && checkNoPieceAtDestination(move, board);
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
	public boolean checkNoPieceAtDestination(Move move, Board board)
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
	public boolean hasNoPieceInMovementRoute(Move move, Board board) {
		Piece[][] positions = board.getPositions();
		int startX = move.getStartX();
		int endX = move.getEndX();
		int startY = move.getStartY();
		int endY = move.getEndY();
		return traverseDiagonal(startX, endX, startY, endY, positions);
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