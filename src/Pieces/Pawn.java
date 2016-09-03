package Pieces;
import Other.Board;
import Other.Move;

public class Pawn extends Piece
{
	public Pawn(int teamNumber) {
		super(teamNumber);
	}

	public boolean isValidMove(Move move, Board board)
	{
		boolean isValid;
		boolean isOnBoard = onBoard(move, board);
		int yDelta = move.getEndY() - move.getStartY();
		boolean isForward = checkForward(move, yDelta);
		boolean firstMove = (move.getStartY() == 1 || move.getStartY() == board.getLength()-2);
		int xMovement = Math.abs(move.getEndX() - move.getStartX());
		int yMovement = Math.abs(yDelta);
		if(!isOnBoard || !isForward)
		{
			isValid = false;
		}
		else if(xMovement == 0 && (yMovement == 1 || (yMovement == 2 && firstMove)))
		{
			isValid = hasNoLeaps(move, board); //Check that the Pawn only moves 1 or if its the first move, 2
		}
		else if(xMovement == 1 && yMovement == 1)
		{
			isValid = opponentPiece(move, board);
		}
		else{
			isValid = false;
		}
		return isValid;
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
		if(currentPiece != null && currentPiece.getTeam() != team)
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
}