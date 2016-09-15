package Model.Pieces;
import java.util.ArrayList;
import java.util.List;

import Model.Board;
import Model.Move;
import Model.Team;

/**
 * Pawn class to describe Pawn's possible moves.
 * @author arnavmishra
 *
 */
public class Pawn extends Piece
{
	/**
	 * Constructor to initialize Pawn on a team and coordinate.
	 * @param team
	 */
	public Pawn(Team team, int xValue, int yValue) {
		super(team, xValue, yValue);
	}

	/**
	 * Function to check whether a pawn's move is valid.
	 * @param move
	 * @param board
	 * @return Whether the move is valid.
	 */
	public boolean isValidMove(Move move, Board board)
	{
		boolean isValid;
		boolean isOnBoard = onAvailableSquare(move, board);
		int yDelta = move.getEndY() - move.getStartY();
		
		boolean isForward = checkForward(move, yDelta); // Check that the movement is forward.
		boolean firstMove = (move.getStartY() == 1 || move.getStartY() == board.getLength()-2); // Check row for first move.
		boolean noPiece = checkNoPiece(move, board); // Check if there is a piece already on the position being moved to.
		
		int xMovement = Math.abs(move.getEndX() - move.getStartX());
		int yMovement = Math.abs(yDelta);
		
		if(!isOnBoard || !isForward) // Check if pawn is on the board and is moving forward.
		{
			isValid = false;
		}
		else if(noPiece && xMovement == 0 && (yMovement == 1 || (yMovement == 2 && firstMove)))
		{
			isValid = hasNoLeaps(move, board); // Check that the Pawn only moves 1 or if its the first move, 2
		}
		else if(xMovement == 1 && yMovement == 1)
		{
			isValid = opponentPiece(move, board); // Check that there is an opposing piece if the pawn moves diagonal.
		}
		else
		{
			isValid = false;
		}
		return isValid;
	}
	
	/**
	 * Helper function to confirm that there is no piece where the pawn is moving
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
	 * Check that the pawn is moving forward depending on which team it is on.
	 * @param move
	 * @param yDelta
	 * @return whether the pawn is moving forward
	 */
	public boolean checkForward(Move move, int yDelta)
	{
		int team = move.getTeamNumber();
		if((team == 0 && yDelta > 0) || (team == 1 && yDelta < 0))
		{
			return true;
		}
		return false;
	}

	/**
	 * Check that the pawn is taking an opponent piece if it is moving diagonally.
	 * @param move
	 * @param board
	 * @return whether there is an opponent piece diagonally
	 */
	public boolean opponentPiece(Move move, Board board)
	{
		Piece[][] positions = board.getPositions();
		int endX = move.getEndX();
		int endY = move.getEndY();
		int team = move.getTeamNumber();
		Piece currentPiece = positions[endY][endX];
		if(currentPiece != null && currentPiece.getTeamNumber() != team)
		{
			return true;
		}
		return false;
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
	 * Function to get all valid possible moves for the Pawn in any direction.
	 * @param board
	 * @return all possible valid moves.
	 */
	@Override
	public List<Move> findAllMoves(Board board) {
		int xValue = this.getXValue();
		int yValue = this.getYValue();
		
		List<Move> possibleMoves = new ArrayList<Move>();
		
		int yDirection;
		if(this.getTeamNumber() == 0)
		{
			yDirection = 1;
		}
		else
		{
			yDirection = -1;
		}
		
		possibleMoves.addAll(getMoves(board, 0, yDirection, xValue, yValue)); // Check forward movement.
		possibleMoves.addAll(getMoves(board, 1, yDirection, xValue, yValue)); // Check diagonal movement for right.
		possibleMoves.addAll(getMoves(board, -1, yDirection, xValue, yValue)); // Check diagonal movement for left.
		
		return possibleMoves;
	}
}