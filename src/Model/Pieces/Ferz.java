package Model.Pieces;
import java.util.ArrayList;
import java.util.List;

import Model.Board;
import Model.Move;
import Model.Team;

/**
 * Ferz class to describe Ferz's possible moves.
 * @author arnavmishra
 *
 */
public class Ferz extends Piece
{
	/**
	 * Constructor to initialize Ferz on a team and coordinate.
	 * @param team
	 */
	public Ferz(Team team, int xValue, int yValue) {
		super(team, xValue, yValue);
	}

	/**
	 * Function to check whether a ferz's move is valid.
	 * @param move
	 * @param board
	 * @return Whether the move is valid.
	 */
	public boolean isValidMove(Move move, Board board)
	{
		boolean isOnBoard = onAvailableSquare(move, board);
		int xMovement = Math.abs(move.getEndX() - move.getStartX());
		int yMovement = Math.abs(move.getEndY() - move.getStartY());
		if(isOnBoard && xMovement == 1 && yMovement == 1)
		{
			return hasNoPieceInMovementRoute(move, board); //Check that the Ferz only moved 1 space diagonally
		}
		else{
			return false;
		}
	}

	/**
	 * The ferz can't jump over any pieces since it only moves one space.
	 * @param move
	 * @param board
	 * @return whether the ferz leaps over pieces.
	 */
	@Override
	public boolean hasNoPieceInMovementRoute(Move move, Board board) {
		return true;
	}
	
	/**
	 * Function to get all valid possible moves for the Ferz in any direction.
	 * @param board
	 * @return all possible valid moves.
	 */
	@Override
	public List<Move> findAllMoves(Board board) {
		int xValue = this.getXValue();
		int yValue = this.getYValue();
		
		List<Move> possibleMoves = new ArrayList<Move>();
		
		possibleMoves = addMoveToPossibleMoves(board, xValue, yValue, 1, 1, possibleMoves); // Check movement towards bottom right.
		possibleMoves = addMoveToPossibleMoves(board, xValue, yValue, 1, -1, possibleMoves); // Check movement towards top right.
		possibleMoves = addMoveToPossibleMoves(board, xValue, yValue, -1, 1, possibleMoves); // Check movement towards bottom left.
		possibleMoves = addMoveToPossibleMoves(board, xValue, yValue, -1, -1, possibleMoves); // Check movement towards top left.
		
		return possibleMoves;
	}
	
	/**
	 * Helper function to add a piece to the possible moves list after confirming validity
	 * @param board
	 * @param xValue
	 * @param yValue
	 * @param xDiff
	 * @param yDiff
	 * @param possibleMoves
	 * @return list with or without new move depending on whether it is possible
	 */
	public List<Move> addMoveToPossibleMoves(Board board, int xValue, int yValue, int xDiff, int yDiff, List<Move> possibleMoves)
	{
		Move newMove = new Move(xValue, yValue, xValue + xDiff, yValue + yDiff, this.getTeamNumber());
		if(isValidMove(newMove, board))
		{
			boolean isCheck = board.isTeamInCheckAfterMove(newMove);
			if(!isCheck)
			{
				possibleMoves.add(newMove);
			}
		}
		return possibleMoves;
	}
}