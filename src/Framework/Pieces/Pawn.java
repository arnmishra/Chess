package Framework.Pieces;
import java.util.ArrayList;
import java.util.List;

import Framework.Board;
import Framework.Move;
import Framework.Team;

public class Pawn extends Piece
{
	public Pawn(Team team) {
		super(team);
	}

	public boolean isValidMove(Move move, Board board)
	{
		boolean isValid;
		boolean isOnBoard = onAvailableSquare(move, board);
		int yDelta = move.getEndY() - move.getStartY();
		boolean isForward = checkForward(move, yDelta);
		boolean firstMove = (move.getStartY() == 1 || move.getStartY() == board.getLength()-2);
		boolean noPiece = checkNoPiece(move, board);
		int xMovement = Math.abs(move.getEndX() - move.getStartX());
		int yMovement = Math.abs(yDelta);
		if(!isOnBoard || !isForward)
		{
			isValid = false;
		}
		else if(noPiece && xMovement == 0 && (yMovement == 1 || (yMovement == 2 && firstMove)))
		{
			isValid = hasNoLeaps(move, board); //Check that the Pawn only moves 1 or if its the first move, 2
		}
		else if(xMovement == 1 && yMovement == 1)
		{
			isValid = opponentPiece(move, board);
		}
		else
		{
			isValid = false;
		}
		return isValid;
	}
	
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
	
	public boolean checkForward(Move move, int yDelta)
	{
		int team = move.getTeam();
		if((team == 0 && yDelta > 0) || (team == 1 && yDelta < 0))
		{
			return true;
		}
		return false;
	}

	public boolean opponentPiece(Move move, Board board)
	{
		Piece[][] positions = board.getPositions();
		int endX = move.getEndX();
		int endY = move.getEndY();
		int team = move.getTeam();
		Piece currentPiece = positions[endY][endX];
		if(currentPiece != null && currentPiece.getTeamNumber() != team)
		{
			return true;
		}
		return false;
	}
	
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
		else
		{
			return traverseColumn(column, endY, startY, positions);
		}
	}
	
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
		
		possibleMoves.addAll(getMoves(board, 0, yDirection, xValue, yValue));
		possibleMoves.addAll(getMoves(board, 1, yDirection, xValue, yValue));
		possibleMoves.addAll(getMoves(board, -1, yDirection, xValue, yValue));
		
		return possibleMoves;
	}
}