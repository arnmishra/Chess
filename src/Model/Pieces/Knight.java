package Model.Pieces;
import java.util.ArrayList;
import java.util.List;

import Model.Board;
import Model.Move;
import Model.Team;

/**
 * Knight class to describe Knight's possible moves.
 * @author arnavmishra
 *
 */
public class Knight extends Piece
{
	/**
	 * Constructor to initialize Knight on a team and coordinate.
	 * @param team
	 */
	public Knight(Team team, int xValue, int yValue) {
		super(team, xValue, yValue);
	}

	/**
	 * Function to check whether a knight's move is valid.
	 * @param move
	 * @param board
	 * @return Whether the move is valid.
	 */
	public boolean isValidMove(Move move, Board board)
	{
		boolean isOnBoard = onAvailableSquare(move, board);
		int xMovement = Math.abs(move.getEndX() - move.getStartX());
		int yMovement = Math.abs(move.getEndY() - move.getStartY());
		if(isOnBoard && ((xMovement == 1 && yMovement == 2) || (xMovement == 2 && yMovement == 1)))
		{
			return hasNoPieceInMovementRoute(move, board); //Check that the Knight moved appropriately
		}
		else{
			return false;
		}
	}

	/**
	 * The knight is allowed to jump over pieces.
	 * @param move
	 * @param board
	 * @return whether the knight leaps over pieces.
	 */
	@Override
	public boolean hasNoPieceInMovementRoute(Move move, Board board) {
		// Leaps are allowed for Knights
		return true;
	}
	
	/**
	 * Function to get all valid possible moves for the Knight in any direction.
	 * @param board
	 * @return all possible valid moves.
	 */
	@Override
	public List<Move> findAllMoves(Board board) {
		int xValue = this.getXValue();
		int yValue = this.getYValue();
		List<Move> possibleMoves = new ArrayList<Move>();
		
		// Knights have 8 possible moves that have to be validated.
		int [][] possibleMovementDeltas = new int[][]{{1, 2}, {1, -2}, {-1, 2}, {-1, -2}, {2, 1}, {2, -1}, {-2, 1}, {-2, -1}};
		
		for(int i = 0; i < possibleMovementDeltas.length; i++)
		{
			int changeX = xValue + possibleMovementDeltas[i][0];
			int changeY = yValue + possibleMovementDeltas[i][1];
			Move newMove = new Move(xValue, yValue, changeX, changeY, this.getTeamNumber());
			boolean isValid = isValidMove(newMove, board); // Check if this move is valid, if so, add to list of moves.
			if(isValid)
			{
				boolean isCheck = board.isTeamInCheckAfterMove(newMove);
				if(!isCheck)
				{
					possibleMoves.add(newMove);
				}
			}
		}
		
		return possibleMoves;
	}
}