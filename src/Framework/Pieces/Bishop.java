package Framework.Pieces;
import java.util.ArrayList;
import java.util.List;

import Framework.Board;
import Framework.Move;
import Framework.Team;

/**
 * Bishop class to describe Bishop's possible moves.
 * @author arnavmishra
 *
 */
public class Bishop extends Piece
{
	/**
	 * Constructor to initialize Bishop on a team and coordinate.
	 * @param team
	 */
	public Bishop(Team team, int xValue, int yValue) {
		super(team, xValue, yValue);
	}

	/**
	 * Function to check whether a bishop's move is valid.
	 * @param move
	 * @param board
	 * @return Whether the move is valid.
	 */
	public boolean isValidMove(Move move, Board board)
	{
		boolean isOnBoard = onAvailableSquare(move, board);
		int xMovement = Math.abs(move.getEndX() - move.getStartX());
		int yMovement = Math.abs(move.getEndY() - move.getStartY());
		if(isOnBoard && xMovement == yMovement)
		{
			return hasNoLeaps(move, board); //Check that the Bishop only moved diagonally
		}
		else{
			return false;
		}
	}

	/**
	 * Function to check whether the bishop jumps over any pieces
	 * when it moves diagonally.
	 * @param move
	 * @param board
	 * @return whether the bishop leaps over pieces.
	 */
	@Override
	public boolean hasNoLeaps(Move move, Board board) {
		Piece[][] positions = board.getPositions();
		int startX = move.getStartX();
		int endX = move.getEndX();
		int startY = move.getStartY();
		int endY = move.getEndY();
		return traverseDiagonal(startX, endX, startY, endY, positions);
	}

	/**
	 * Function to get all valid possible moves for the bishop in any direction.
	 * @param board
	 * @return all possible valid moves.
	 */
	@Override
	public List<Move> findAllMoves(Board board) {
		int xValue = this.getXValue();
		int yValue = this.getYValue();
		
		List<Move> possibleMoves = new ArrayList<Move>();
		possibleMoves.addAll(getMoves(board, 1, 1, xValue, yValue)); // Check movement towards bottom right.
		possibleMoves.addAll(getMoves(board, 1, -1, xValue, yValue)); // Check movement towards top right.
		possibleMoves.addAll(getMoves(board, -1, 1, xValue, yValue)); // Check movement towards bottom left.
		possibleMoves.addAll(getMoves(board, -1, -1, xValue, yValue)); // Check movement towards top left.
		
		return possibleMoves;
	}
}