package Model.Pieces;
import java.util.ArrayList;
import java.util.List;

import Model.Board;
import Model.Move;
import Model.Team;

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
		boolean moveHorizontal = (xMovement == 1 && yMovement == 0);
		boolean moveVertical = (yMovement == 1 && xMovement == 0);
		boolean moveDiagonal = (xMovement == 1 && yMovement == 1);
		if(isOnBoard && (moveHorizontal || moveVertical || moveDiagonal))
		{
			return hasNoPieceInMovementRoute(move, board); //Check that the King only moved 1 space
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
	public boolean hasNoPieceInMovementRoute(Move move, Board board) {
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
			boolean isCheck = board.isTeamInCheck(newMove);
			if(!isCheck)
			{
				possibleMoves.add(newMove);
			}
		}
		return possibleMoves;
	}
}