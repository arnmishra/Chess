package Tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import Framework.Board;
import Framework.Move;
import Framework.Team;
import Framework.Pieces.Pawn;
import Framework.Pieces.Piece;

/**
 * Tests for the Team class.
 * @author arnavmishra
 *
 */
public class TeamTest {

	/**
	 * Test to check the constructor by ensuring that a Team's teamNumber
	 * is set properly.
	 * @throws Exception
	 */
	@Test
	public void validConstructor() throws Exception {
		int teamNumber = 0;
		Team team = new Team(teamNumber);
		assertEquals(team.getTeamNumber(), teamNumber);
	}
	
	/**
	 * Test to confirm that when an opposing team takes a piece from a Team,
	 * the list of pieces works updates accordingly. In this situation, a pawn
	 * is taken from the team and there are only 7 pawns left after.
	 * @throws Exception
	 */
	@Test
	public void takeAPiece() throws Exception {
		Board board = new Board(8, 8);
		
		int xCoordinate = 4;
		int yCoordinate = 1;
		Piece pawn = board.getPositions()[yCoordinate][xCoordinate];
		if(!(pawn instanceof Pawn))
		{
			fail("Incorrect piece type");
		}
		Move team0PawnMove = new Move(4, 1, 4, 3, 0); // Move Team 0's Pawn closer to test taking a piece
		board.setPositions(team0PawnMove);
		Move team1PawnMove = new Move(3, 6, 3, 4, 1); // Move Team 1's Pawn closer to test taking a piece
		board.setPositions(team1PawnMove);
		Move pawnMoveDiagonal = new Move(4, 3, 3, 4, 0); // Move Team 1's Pawn closer to test taking a piece
		board.setPositions(pawnMoveDiagonal);
		
		Team team1 = board.getTeam(1);
		List<Piece> pieces = team1.getPieces();
		int numberOfPawns = 0;
		for(int i = 0; i < pieces.size(); i++)
		{
			Piece piece = pieces.get(i);
			if(piece instanceof Pawn)
			{
				numberOfPawns++;
			}
		}
		assertEquals(numberOfPawns, 7); // After one of it's pawns are taken, Team1 should only have 7 pawns left.
	}
	
	/**
	 * Test of adding a new piece to a team. The test adds a pawn and 
	 * confirms that there are now 9 pawns instead of 8.
	 * @throws Exception
	 */
	@Test
	public void addAPiece() throws Exception {
		Board board = new Board(8, 8);
		
		Team team0 = board.getTeam(0);
		Pawn pawn = new Pawn(team0, 4, 0);
		team0.addPiece(pawn);
		
		List<Piece> pieces = team0.getPieces();
		int numberOfPawns = 0;
		for(int i = 0; i < pieces.size(); i++)
		{
			Piece piece = pieces.get(i);
			if(piece instanceof Pawn)
			{
				numberOfPawns++;
			}
		}
		assertEquals(numberOfPawns, 9); // After adding a pawn, Team0 should only have 9 pawns.
	}
}
