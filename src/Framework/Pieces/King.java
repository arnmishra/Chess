package Framework.Pieces;
import java.util.ArrayList;
import java.util.List;

import Framework.Board;
import Framework.Move;
import Framework.Team;

/**
 * King class to describe King's possible moves.
 * @author arnavmishra
 *
 */
public class King extends Piece
{
	/**
	 * Constructor to initialize King on a team and coordinate.
	 * @param team
	 */
	public King(Team team, int xValue, int yValue) {
		super(team, xValue, yValue);
	}

	/**
	 * Function to check whether a king's move is valid.
	 * @param move
	 * @param board
	 * @return Whether the move is valid.
	 */
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

	/**
	 * The king can't jump over any pieces since it only moves one space.
	 * @param move
	 * @param board
	 * @return whether the king leaps over pieces.
	 */
	@Override
	public boolean hasNoLeaps(Move move, Board board) {
		return true;
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
		
		possibleMoves = addMoveToPossibleMoves(board, xValue, yValue, 1, 0, possibleMoves); // Check right movement.
		possibleMoves = addMoveToPossibleMoves(board, xValue, yValue, -1, 0, possibleMoves); // Check left movement.
		possibleMoves = addMoveToPossibleMoves(board, xValue, yValue, 0, 1, possibleMoves);  // Check downward movement.
		possibleMoves = addMoveToPossibleMoves(board, xValue, yValue, 0, -1, possibleMoves); // Check upward movement.
		possibleMoves = addMoveToPossibleMoves(board, xValue, yValue, 1, 1, possibleMoves); // Check movement towards bottom right.
		possibleMoves = addMoveToPossibleMoves(board, xValue, yValue, 1, -1, possibleMoves); // Check movement towards top right.
		possibleMoves = addMoveToPossibleMoves(board, xValue, yValue, -1, 1, possibleMoves); // Check movement towards bottom left.
		possibleMoves = addMoveToPossibleMoves(board, xValue, yValue, -1, -1, possibleMoves); // Check movement towards top left.
		
		return possibleMoves;
	}
	
	public List<Move> addMoveToPossibleMoves(Board board, int xValue, int yValue, int xDiff, int yDiff, List<Move> possibleMoves)
	{
		Move newMove = new Move(xValue, yValue, xValue + xDiff, yValue + yDiff, this.getTeamNumber());
		if(isValidMove(newMove, board))
		{
			possibleMoves.add(newMove);
		}
		return possibleMoves;
	}
}