package Model;

import java.util.*;

import Model.Pieces.*;

/**
 * Team class to hold metadata about each team.
 * @author arnavmishra
 *
 */
public class Team
{
	private int teamNumber; // Holds the team's team number.
	private List<Piece> pieces = new ArrayList<Piece>(); // Holds all active pieces for a team.
	private String teamName;
	private int teamScore;
	private Stack<Move> moves = new Stack<Move>();
	
	/**
	 * Constructor to initialize team with a team number.
	 * @param teamNumber
	 */
	public Team(int teamNumber)
	{
		this.teamNumber = teamNumber;
	}
	
	/**
	 * Getter for team name.
	 */
	public String getTeamName()
	{
		return this.teamName;
	}
	
	/**
	 * Setter for team name.
	 */
	public void setTeamName(String teamName)
	{
		this.teamName = teamName;
	}
	
	/**
	 * Getter for team score.
	 */
	public int getTeamScore()
	{
		return this.teamScore;
	}
	
	/**
	 * Setter for team score.
	 */
	public void setTeamScore(int teamScore)
	{
		this.teamScore = teamScore;
	}
	
	/**
	 * Setter for team score.
	 */
	public void incrementTeamScore()
	{
		this.teamScore++;
	}
	
	/**
	 * Getter for team's number.
	 * @return
	 */
	public int getTeamNumber()
	{
		return this.teamNumber;
	}

	/**
	 * Adds an array of pieces to the active piece list for the team.
	 * @param piece
	 */
	public void addPieces(Piece[] piece)
	{
		pieces.addAll(Arrays.asList(piece));
	}
	
	/**
	 * Adds a single piece to the active piece list for the team.
	 * @param piece
	 */
	public void addPiece(Piece piece)
	{
		pieces.add(piece);
	}
	
	/**
	 * Removes a piece from the array of pieces after capture.
	 * @param piece
	 */
	public void removePiece(Piece piece)
	{
		pieces.remove(piece);
	}
	
	/**
	 * Remove all pieces from array of pieces when resetting game.
	 */
	public void removeAllPieces()
	{
		pieces = new ArrayList<Piece>();
	}
	
	/**
	 * Getter for the pieces of the team.
	 * @return pieces
	 */
	public List<Piece> getPieces()
	{
		return pieces;
	}
	
	/**
	 * Getter for moves of the team.
	 * @return moves
	 */
	public Stack<Move> getMoves()
	{
		return this.moves;
	}
	
	/**
	 * Add a move to the moves list.
	 * @param move
	 */
	public void addMove(Move move)
	{
		this.moves.push(move);
	}
	
	/**
	 * Remove last move from the moves list.
	 */
	public Move undoLastMove()
	{
		if(moves.isEmpty())
		{
			return null;
		}
		return this.moves.pop();
	}
}