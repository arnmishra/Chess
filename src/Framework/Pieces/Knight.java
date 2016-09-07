package Framework.Pieces;
import java.util.ArrayList;
import java.util.List;

import Framework.Board;
import Framework.Move;
import Framework.Team;

public class Knight extends Piece
{
	public Knight(Team team) {
		super(team);
	}

	public boolean isValidMove(Move move, Board board)
	{
		boolean isOnBoard = onAvailableSquare(move, board);
		int xMovement = Math.abs(move.getEndX() - move.getStartX());
		int yMovement = Math.abs(move.getEndY() - move.getStartY());
		if(isOnBoard && ((xMovement == 1 && yMovement == 2) || (xMovement == 2 && yMovement == 1)))
		{
			return hasNoLeaps(move, board); //Check that the Knight moved appropriately
		}
		else{
			return false;
		}
	}

	@Override
	public boolean hasNoLeaps(Move move, Board board) {
		// Leaps are allowed for Knights
		return true;
	}
	
	@Override
	public List<Move> findAllMoves(Board board) {
		int xValue = this.getXValue();
		int yValue = this.getYValue();
		List<Move> possibleMoves = new ArrayList<Move>();
		
		int [] possibleXChanges = new int[]{1, 1, -1, -1, 2, 2, -2, -2};
		int [] possibleYChanges = new int[]{2, -2, 2, -2, 1, -1, 1, -1};
		
		for(int i = 0; i < possibleXChanges.length; i++)
		{
			int changeX = xValue + possibleXChanges[i];
			int changeY = yValue + possibleYChanges[i];
			Move newMove = new Move(xValue, yValue, changeX, changeY, this.getTeamNumber());
			boolean isValid = isValidMove(newMove, board);
			if(isValid)
			{
				possibleMoves.add(newMove);
			}
		}
		
		
		return possibleMoves;
	}
}