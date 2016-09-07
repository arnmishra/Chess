package Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import Framework.Team;
import Framework.Pieces.*;

public class PieceTest {

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
	
	@Test
	public void moveOffBoard() throws Exception {
		fail("Not yet implemented");
	}
	
	@Test
	public void spaceOccupiedBySameTeam() throws Exception {
		fail("Not yet implemented");
	}
}
