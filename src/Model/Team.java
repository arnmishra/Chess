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
	
	/**
	 * Constructor to initialize team with a team number.
	 * @param teamNumber
	 */
	public Team(int teamNumber)
	{
		this.teamNumber = teamNumber;
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
	 * Getter for the pieces of the team.
	 * @return
	 */
	public List<Piece> getPieces()
	{
		return pieces;
	}
}