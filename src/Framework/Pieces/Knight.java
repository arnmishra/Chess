package Framework.Pieces;
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
}