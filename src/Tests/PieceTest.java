package Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import Framework.Board;
import Framework.Move;
import Framework.Team;
import Framework.Pieces.*;

/**
 * Tests for the Piece class.
 * @author arnavmishra
 *
 */
public class PieceTest {

	/**
	 * Test to check the constructor of the Piece class by using a
	 * new Pawn's x and y coordinates and team information.
	 * @throws Exception
	 */
	@Test
	public void validConstructor() throws Exception {
		int teamNumber = 0;
		int xCoordinate = 1;
		int yCoordinate = 2;
		Team team = new Team(teamNumber);
		Piece piece = new Pawn(team, xCoordinate, yCoordinate);
		assertEquals(xCoordinate, piece.getXValue());
		assertEquals(yCoordinate, piece.getYValue());
		assertEquals(team, piece.getTeam());
		assertEquals(teamNumber, piece.getTeamNumber());
	}
	
	/**
	 * Test to confirm that movement off the board is invalid.
	 * @throws Exception
	 */
	@Test
	public void moveOffBoard() throws Exception {
		Board board = new Board(8, 8);
		int teamNumber = 0;
		
		int xCoordinate = 0;
		int yCoordinate = 0;
		Piece piece = board.getPositions()[yCoordinate][xCoordinate];
		Move move = new Move(xCoordinate, yCoordinate, xCoordinate - 1, yCoordinate, teamNumber);
		assertFalse(piece.isValidMove(move, board));
	}
	
	/**
	 * Test to confirm that a piece cannot move into the same square
	 * as a piece of its own team.
	 * @throws Exception
	 */
	@Test
	public void spaceOccupiedBySameTeam() throws Exception {
		Board board = new Board(8, 8);
		int teamNumber = 0;
		
		int xCoordinate = 0;
		int yCoordinate = 0;
		Piece piece = board.getPositions()[yCoordinate][xCoordinate];
		Move move = new Move(xCoordinate, yCoordinate, xCoordinate + 1, yCoordinate, teamNumber);
		assertFalse(piece.isValidMove(move, board));
	}
}
