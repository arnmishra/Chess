package Framework.Pieces;
import Framework.Board;
import Framework.Move;
import Framework.Team;

public class Bishop extends Piece
{
	public Bishop(Team team) {
		super(team);
	}

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

	@Override
	public boolean hasNoLeaps(Move move, Board board) {
		Piece[][] positions = board.getPositions();
		int startX = move.getStartX();
		int endX = move.getEndX();
		int startY = move.getStartY();
		int endY = move.getEndY();
		return traverseDiagonal(startX, endX, startY, endY, positions);
	}
}