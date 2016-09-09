package Framework;
import java.util.*;

import Framework.Move;
import Framework.Team;
import Framework.Pieces.*;

/**
 * The board class describes the details about the board and a snapshot
 * of the current game play including piece positions and team statuses.
 * @author arnavmishra
 *
 */
public class Board
{
	
	private Piece[][] positions; // Holds positions of all pieces on the board.
	private int width; // Holds the width of the board.
	private int length; // Holds the length of the board.
	private Team team0 = new Team(0); // Holds the team0 details (goes first).
	private Team team1 = new Team(1); // Holds the team1 details (goes second).
	
	/**
	 * Board constructor to set up initial board piece positions and the width and length.
	 * @param width
	 * @param length
	 */
	public Board(int width, int length)
	{
		this.width = width;
		this.length = length;
		this.positions = setBoard();
		
	}
	
	/**
	 * Function to put pieces in initial starting positions. 
	 * @return a Piece double array with all the current positions.
	 */
	public Piece[][] setBoard()
	{
		Piece[][] positions = new Piece[this.length][this.width];
		positions[0] = setPrimaryPieces(positions, 0, team0);
		positions[7] = setPrimaryPieces(positions, 7, team1);
		for(int i = 0; i < 8; i++)
		{
			positions[1][i] = new Pawn(team0, i, 1);
			positions[6][i] = new Pawn(team1, i, 6);
		}
		team0.addPieces(positions[0]);
		team0.addPieces(positions[1]);
		team1.addPieces(positions[7]);
		team1.addPieces(positions[6]);
		return positions;
	}
	
	/**
	 * Helper function to initialize all the non-pawns and put them in the right places.
	 * @param positions
	 * @param yValue
	 * @param team
	 * @return Pieces 1-d array with the primary non-pawn pieces for a team.
	 */
	public Piece[] setPrimaryPieces(Piece[][] positions, int yValue, Team team)
	{
		positions[yValue][0] = new Rook(team, 0, yValue);
		positions[yValue][1] = new Knight(team, 1, yValue);
		positions[yValue][2] = new Bishop(team, 2, yValue);
		positions[yValue][3] = new Queen(team, 3, yValue);
		positions[yValue][4] = new King(team, 4, yValue);
		positions[yValue][5] = new Bishop(team, 5, yValue);
		positions[yValue][6] = new Knight(team, 6, yValue);
		positions[yValue][7] = new Rook(team, 7, yValue);
		return positions[yValue];
	}
	
	/**
	 * Get width of board.
	 * @return width
	 */
	public int getWidth()
	{
		return this.width;
	}
	
	/**
	 * Set width of board.
	 * @param width
	 */
	public void setWidth(int width)
	{
		this.width = width;
	}
	
	/**
	 * Get length of board.
	 * @return length
	 */
	public int getLength()
	{
		return this.length;
	}
	
	/**
	 * Set length of board.
	 * @param length
	 */
	public void setLength(int length)
	{
		this.length = length;
	}
	
	/**
	 * Get positions on entire chess board.
	 * @return positions
	 */
	
	public Piece[][] getPositions()
	{
		return this.positions;
	}
	
	/**
	 * Set the new positions of a chess board after a move.
	 * @param move
	 */
	public Piece setPositions(Move move)
	{
		int startX = move.getStartX();
		int startY = move.getStartY();
		int endX = move.getEndX();
		int endY = move.getEndY();
		
		Piece replaced = positions[endY][endX];
		if(replaced != null)
		{
			Team team = replaced.getTeam();
			team.removePiece(replaced);
		}
		Piece moved = positions[startY][startX];
		Piece removed = positions[endY][endX];
		moved.setCoordinates(endX, endY);
		positions[endY][endX] = positions[startY][startX];
		positions[startY][startX] = null;
		return removed;
	}
	
	/**
	 * Undo the last move. This method is used to reverse a move after checking if it
	 * protects the King from a check. This is relevant when checking for checkmates
	 * to ensure that there is at least one move available for the team.
	 * @param move
	 * @param removed
	 */
	public void unsetPositions(Move move, Piece removed)
	{
		int startX = move.getStartX();
		int startY = move.getStartY();
		int endX = move.getEndX();
		int endY = move.getEndY();		
		if(removed != null)
		{
			Team team = removed.getTeam();
			team.addPiece(removed);
		}
		
		Piece moved = positions[endY][endX];
		moved.setCoordinates(startX, startY);
		
		positions[startY][startX] = moved;
		positions[endY][endX] = removed;
	}
	
	/**
	 * Method to see if the king will still be in check after the move is made. 
	 * Undo the move after.
	 * @param move
	 * @return whether the king is in check after the move.
	 */
	public boolean isTeamInCheck(Move move)
	{
		Piece removed = positions[move.getEndY()][move.getEndX()];
		this.setPositions(move);
		int turnTeamNumber = move.getTeamNumber();
		List<Piece> checkPieces = getTeamPieces(turnTeamNumber);
		int opposingTeamNumber = toggleTeam(turnTeamNumber);
		List<Piece> opposingPieces = getTeamPieces(opposingTeamNumber);
		Piece checkKing = getKing(checkPieces);
		int kingX = checkKing.getXValue();
		int kingY = checkKing.getYValue();
		boolean isCheck = false;
		for(int i = 0; i < opposingPieces.size(); i++)
		{
			Piece opposingPiece = opposingPieces.get(i);
			int opposingX = opposingPiece.getXValue();
			int opposingY = opposingPiece.getYValue();
			Move checkMove = new Move(opposingX, opposingY, kingX, kingY, opposingTeamNumber);
			isCheck = opposingPiece.isValidMove(checkMove, this);
			if(isCheck)
			{
				break;
			}
		}
		this.unsetPositions(move, removed);
		return isCheck;
	}
	
	/**
	 * Method to see if a move called a checkmate.
	 * @param turnTeamNumber
	 * @return whether the team is in checkmate.
	 */
	public boolean getCheckMate(int turnTeamNumber)
	{
		List<Piece> checkPieces = getTeamPieces(turnTeamNumber);
		boolean isCheck = false;
		for(int i = 0; i < checkPieces.size(); i++)
		{
			Piece checkPiece = checkPieces.get(i);
			List<Move> possibleMoves = checkPiece.findAllMoves(this);
			for(int j = 0; j < possibleMoves.size(); j++)
			{
				isCheck = isTeamInCheck(possibleMoves.get(j));
				if(!isCheck)
				{
					return false;
				}
			}
		}
		
		return true;
	}
	
	/**
	 * Method to see if a team cannot move any piece in which case
	 * the game is in a stale mate.
	 * @param turnTeamNumber
	 * @return
	 */
	public boolean getStaleMate(int turnTeamNumber)
	{
		List<Piece> checkPieces = getTeamPieces(turnTeamNumber);
		for(int i = 0; i < checkPieces.size(); i++)
		{
			Piece checkPiece = checkPieces.get(i);
			List<Move> possibleMoves = checkPiece.findAllMoves(this);
			if(!possibleMoves.isEmpty())
			{
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Helper function to get the King from a list of pieces.
	 * @param pieces
	 * @return King's piece
	 */
	public Piece getKing(List<Piece> pieces)
	{
		for(int i = 0; i < pieces.size(); i++)
		{
			if(pieces.get(i) instanceof King)
			{
				return pieces.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Helper function to change the team number.
	 * @param turnTeamNumber
	 * @return opposite team number.
	 */
	public int toggleTeam(int turnTeamNumber)
	{
		if(turnTeamNumber == 0)
		{
			turnTeamNumber = 1;
		}
		else
		{
			turnTeamNumber = 0;
		}
		return turnTeamNumber;
	}
	
	/**
	 * Helper function to return a team object given a team number.
	 * @param turnTeamNumber
	 * @return team's active pieces.
	 */
	public Team getTeam(int turnTeamNumber)
	{
		if(turnTeamNumber == 0)
		{
			return this.team0;
		}
		else
		{
			return this.team1;
		}
	}
	
	/**
	 * Helper function to return the remaining pieces of a team
	 * @param turnTeamNumber
	 * @return
	 */
	public List<Piece> getTeamPieces(int turnTeamNumber)
	{
		Team team = getTeam(turnTeamNumber);
		return team.getPieces();
	}
	
	/**
	 * Helper function to print out the board.
	 * @param board
	 */
	public void printBoard()
	{
		Piece[][] positions = getPositions();
		System.out.println("   0  1  2  3  4  5  6  7 ");
		System.out.println("  - - - - - - - - - - - - -");
		for(int i = getLength() - 1; i >= 0; i--)
		{
			System.out.print(i + " |");
			for(int j = 0; j < getWidth(); j++)
			{
				printPiece(positions[i][j]);
				System.out.print("|");
			}
			System.out.print(" " + i);
			System.out.println("");
			System.out.println("  - - - - - - - - - - - - -");
		}
		System.out.println("   0  1  2  3  4  5  6  7 ");
	}
	
	/**
	 * Helper function to print out each piece on the board.
	 * @param piece
	 */
	public void printPiece(Piece piece)
	{
		if(piece == null)
		{
			System.out.print("  ");
		}
		else
		{
			int team = piece.getTeamNumber();
			if(piece instanceof Rook)
			{
				System.out.print(team + "R");
			}
			else if(piece instanceof Knight)
			{
				System.out.print(team + "N");
			}
			else if(piece instanceof Bishop)
			{
				System.out.print(team + "B");
			}
			else if(piece instanceof Queen)
			{
				System.out.print(team + "Q");
			}
			else if(piece instanceof King)
			{
				System.out.print(team + "K");
			}
			else if(piece instanceof Pawn)
			{
				System.out.print(team + "P");
			}
		}
	}
}