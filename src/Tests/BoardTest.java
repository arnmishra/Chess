package Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import Framework.Board;
import Framework.Move;
import Framework.Pieces.*;

public class BoardTest {

	@Test
	public void validConstructor() throws Exception {
		int width = 8;
		int length = 8;
		Board board = new Board(8, 8);
		assertEquals(board.getWidth(), width);
		assertEquals(board.getLength(), length);
	}
	
	@Test
	public void movingPiece() throws Exception {
		Board board = new Board(8, 8);
		int teamNumber = 0;
		Move pawnMove = new Move(1, 1, 1, 2, teamNumber);
		board.setPositions(pawnMove);
		Piece[][] positions = board.getPositions();
		assertTrue(positions[2][1] != null);
	}
	
	@Test
	public void notInCheck() throws Exception {
		Board board = new Board(8, 8);
		int teamNumber = 0;
		Move move = new Move(1, 1, 1, 2, teamNumber); // Move a pawn up from starting position
		assertFalse(board.isTeamInCheck(move));
	}
	
	@Test
	public void notProtectingCheck() throws Exception {
		Board board = new Board(8,8);
		setUpCheck(board);
		Move move = new Move(6, 1, 6, 3, 0);
		boolean inCheck = board.isTeamInCheck(move);
		assertTrue(inCheck);
	}

	@Test
	public void protectingCheck() throws Exception {
		Board board = new Board(8,8);
		setUpCheck(board);
		Move move = new Move(6, 1, 6, 2, 0);
		boolean inCheck = board.isTeamInCheck(move);
		assertFalse(inCheck);
	}
	
	@Test
	public void notInCheckMate() throws Exception {
		Board board = new Board(8, 8);
		assertFalse(board.getCheckMate(0));
	}
	
	@Test
	public void inCheckMate() throws Exception {
		Board board = new Board(8, 8);
		setUpCheck(board);
		Move move = new Move(6, 1, 6, 2, 0);
		board.setPositions(move);
		move = new Move(7, 3, 6, 2, 1);
		board.setPositions(move);
		boolean inCheckMate = board.getCheckMate(0);
		assertTrue(inCheckMate);
	}
	
	public void setUpCheck(Board board)
	{
		Move move;
		int team0 = 0;
		int team1 = 1;
		move = new Move(5, 1, 5, 2, team0);
		board.setPositions(move);
		move = new Move(4, 6, 4, 5, team1);
		board.setPositions(move);
		move = new Move(7, 1, 7, 2, team0);
		board.setPositions(move);
		move = new Move(3, 7, 7, 3, team1);
		board.setPositions(move);
	}
	
	@Test
	public void notInStaleMate() throws Exception {
		Board board = new Board(8, 8);
		boolean isStaleMate = board.getStaleMate(0);
		assertFalse(isStaleMate);
	}
	
	@Test
	public void inStaleMate() throws Exception {
		Board board = new Board(8,8);
		setUpStaleMate(board);
		boolean isStaleMate = board.getStaleMate(1);
		assertTrue(isStaleMate);
	}
	
	public void setUpStaleMate(Board board)
	{
		Move move;
		int team0 = 0;
		int team1 = 1;
		move = new Move(2, 1, 2, 3, team0); //c4
		board.setPositions(move);
		move = new Move(7, 6, 7, 4, team1); //h5
		board.setPositions(move);
		move = new Move(7, 1, 7, 3, team0); //h4
		board.setPositions(move);
		move = new Move(0, 6, 0, 4, team1); //a5
		board.setPositions(move);
		move = new Move(3, 0, 0, 3, team0); //Qa4
		board.setPositions(move);
		move = new Move(0, 7, 0, 5, team1); //Ra6
		board.setPositions(move);
		move = new Move(0, 3, 0, 4, team0); //Qxa5
		board.setPositions(move);
		move = new Move(0, 5, 7, 5, team1); //Rah6
		board.setPositions(move);
		move = new Move(0, 4, 2, 6, team0); //Qxc7
		board.setPositions(move);
		move = new Move(5, 6, 5, 5, team1); //f6
		board.setPositions(move);
		move = new Move(2, 6, 3, 6, team0); //Qxd7+
		board.setPositions(move);
		move = new Move(4, 7, 5, 6, team1); //Kf7
		board.setPositions(move);
		move = new Move(3, 6, 1, 6, team0); //Qxb7
		board.setPositions(move);
		move = new Move(3, 7, 3, 2, team1); //Qd3
		board.setPositions(move);
		move = new Move(1, 6, 1, 7, team0); //Qxb8
		board.setPositions(move);
		move = new Move(3, 2, 7, 6, team1); //Qh7
		board.setPositions(move);
		move = new Move(1, 7, 2, 7, team1); //Qxc8
		board.setPositions(move);
		move = new Move(5, 6, 6, 5, team0); //Kg6
		board.setPositions(move);
		move = new Move(2, 7, 4, 5, team1); //Qe6
		board.setPositions(move);
	}
}
