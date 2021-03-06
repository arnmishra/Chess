package Model;
import java.util.*;

import Model.Move;
import Model.Team;
import Model.Pieces.*;

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
		this.positions = new Piece[length][width];
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
	 * Get length of board.
	 * @return length
	 */
	public int getLength()
	{
		return this.length;
	}
	
	/**
	 * Function to put pieces in initial starting positions. 
	 * @return a Piece double array with all the current positions.
	 */
	public void setInitialBoard(Boolean useCustomPieces)
	{
		this.positions = new Piece[this.length][this.width];
		positions[0] = setPrimaryPieces(positions, 0, team0, useCustomPieces);
		positions[7] = setPrimaryPieces(positions, 7, team1, useCustomPieces);
		for(int i = 0; i < 8; i++)
		{
			positions[1][i] = new Pawn(team0, i, 1);
			positions[6][i] = new Pawn(team1, i, 6);
		}
		team0.removeAllPieces();
		team0.addPieces(positions[0]);
		team0.addPieces(positions[1]);
		team1.removeAllPieces();
		team1.addPieces(positions[7]);
		team1.addPieces(positions[6]);
	}
	
	/**
	 * Function to add a new piece to a specified coordinate on the board
	 * regardless of whether or not another piece exists there.
	 * @param piece
	 */
	public void addPieceToBoard(Piece piece)
	{
		int xCoordinate = piece.getXValue();
		int yCoordinate = piece.getYValue();
		positions[yCoordinate][xCoordinate] = piece;
	}
	
	/**
	 * Helper function to initialize all the non-pawns and put them in the right places.
	 * @param positions
	 * @param yValue
	 * @param team
	 * @return Pieces 1-d array with the primary non-pawn pieces for a team.
	 */
	public Piece[] setPrimaryPieces(Piece[][] positions, int yValue, Team team, Boolean useCustomPieces)
	{
		positions[yValue][0] = new Rook(team, 0, yValue);
		if(useCustomPieces)
		{
			positions[yValue][1] = new Checker(team, 1, yValue);
			positions[yValue][2] = new Ferz(team, 2, yValue);
		}
		else
		{
			positions[yValue][1] = new Knight(team, 1, yValue);
			positions[yValue][2] = new Bishop(team, 2, yValue);
		}
		positions[yValue][3] = new Queen(team, 3, yValue);
		positions[yValue][4] = new King(team, 4, yValue);
		if(useCustomPieces)
		{
			positions[yValue][6] = new Checker(team, 6, yValue);
			positions[yValue][5] = new Ferz(team, 5, yValue);
		}
		else
		{
			positions[yValue][6] = new Knight(team, 6, yValue);
			positions[yValue][5] = new Bishop(team, 5, yValue);
		}
		positions[yValue][7] = new Rook(team, 7, yValue);
		return positions[yValue];
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
	public void setPositions(Move move)
	{
		int startX = move.getStartX();
		int startY = move.getStartY();
		int endX = move.getEndX();
		int endY = move.getEndY();
		
		Piece replaced = positions[endY][endX];
		if(replaced != null)
		{
			Team opponent = replaced.getTeam();
			opponent.removePiece(replaced);
		}
		Piece moved = positions[startY][startX];
		Piece removed = positions[endY][endX];
		moved.setCoordinates(endX, endY);
		positions[endY][endX] = positions[startY][startX];
		positions[startY][startX] = null;
		move.setRemovedPiece(removed);
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
	public boolean isTeamInCheckAfterMove(Move move)
	{
		Piece removed = positions[move.getEndY()][move.getEndX()];
		this.setPositions(move);
		int turnTeamNumber = move.getTeamNumber();
		boolean isCheck = isTeamInCheck(turnTeamNumber);
		this.unsetPositions(move, removed);
		return isCheck;
	}
	
	/**
	 * Method to check if King of a certain team is currently in check.
	 * @param turnTeamNumber
	 * @return whether the king is in check
	 */
	public boolean isTeamInCheck(int turnTeamNumber)
	{
		List<Piece> checkPieces = getTeamPieces(turnTeamNumber);
		int opposingTeamNumber = toggleTeam(turnTeamNumber);
		List<Piece> opposingPieces = getTeamPieces(opposingTeamNumber);
		Piece checkKing = getKing(checkPieces);
		if(checkKing == null) // If board is set-up for testing with no King, no check possible
		{
			return false;
		}
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
		if(!isTeamInCheck(turnTeamNumber)) // If not currently check, can't be checkmate.
		{
			return false;
		}
		boolean isCheck = false;
		for(int i = 0; i < checkPieces.size(); i++)
		{
			Piece checkPiece = checkPieces.get(i);
			List<Move> possibleMoves = checkPiece.findAllMoves(this);
			for(int j = 0; j < possibleMoves.size(); j++)
			{
				isCheck = isTeamInCheckAfterMove(possibleMoves.get(j));
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
}