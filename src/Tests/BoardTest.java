package Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import Framework.Board;
import Framework.Move;
import Framework.Pieces.*;

/**
 * Tests for the Board class.
 * @author arnavmishra
 *
 */
public class BoardTest {

	/**
	 * Test for the Board constructor to ensure that the width and
	 * length are properly set.
	 * @throws Exception
	 */
	@Test
	public void validConstructor() throws Exception {
		int width = 8;
		int length = 8;
		Board board = new Board(8, 8);
		assertEquals(board.getWidth(), width);
		assertEquals(board.getLength(), length);
	}
	
	/**
	 * Test for piece movement where a pawn is moved forward and then the board
	 * is checked to confirm that the pawn is now in the new location.
	 * @throws Exception
	 */
	@Test
	public void movingPiece() throws Exception {
		Board board = new Board(8, 8);
		int teamNumber = 0;
		Common.movePiece(board, 1, 1, 1, 2, teamNumber);
		Piece[][] positions = board.getPositions();
		assertTrue(positions[2][1] != null);
	}
	
	/**
	 * Test to see if the board is not in check. This is done using the initial 
	 * board setup and ensuring that the team's are not in check with a simple
	 * movement such as moving a pawn forward.
	 * @throws Exception
	 */
	@Test
	public void notInCheck() throws Exception {
		Board board = new Board(8, 8);
		int teamNumber = 0;
		Move move = new Move(1, 1, 1, 2, teamNumber); // Move a pawn up from starting position
		assertFalse(board.isTeamInCheck(move));
	}
	
	/**
	 * Test to confirm that the team is still in check if a team does not move to prevent
	 * the current check.
	 * @throws Exception
	 */
	@Test
	public void notProtectingCheck() throws Exception {
		Board board = new Board(8,8);
		setUpCheck(board);
		Move move = new Move(6, 1, 6, 3, 0);
		boolean inCheck = board.isTeamInCheck(move);
		assertTrue(inCheck);
	}

	/**
	 * Test to confirm that after moving a piece in the way of a check, the check is no longer
	 * there.
	 * @throws Exception
	 */
	@Test
	public void protectingCheck() throws Exception {
		Board board = new Board(8,8);
		setUpCheck(board);
		Move move = new Move(6, 1, 6, 2, 0);
		boolean inCheck = board.isTeamInCheck(move);
		assertFalse(inCheck);
	}
	
	/**
	 * Test to check when the board is not in CheckMate by using the initial board setup.
	 * @throws Exception
	 */
	@Test
	public void notInCheckMate() throws Exception {
		Board board = new Board(8, 8);
		assertFalse(board.getCheckMate(0));
	}
	
	/**
	 * Test to simulate a checkmate and confirm that the board has the checkmate listed.
	 * @throws Exception
	 */
	@Test
	public void inCheckMate() throws Exception {
		Board board = new Board(8, 8);
		setUpCheck(board);
		Common.movePiece(board, 6, 1, 6, 2, 0);
		Common.movePiece(board, 7, 3, 6, 2, 1);
		boolean inCheckMate = board.getCheckMate(0);
		assertTrue(inCheckMate);
	}
	
	/**
	 * Helper function to set up a board into a check situation.
	 * @param board
	 */
	public void setUpCheck(Board board)
	{
		int team0 = 0;
		int team1 = 1;
		Common.movePiece(board, 5, 1, 5, 2, team0); //f3
		Common.movePiece(board, 4, 6, 4, 5, team1); //e6
		Common.movePiece(board, 7, 1, 7, 2, team0); //h3
		Common.movePiece(board, 3, 7, 7, 3, team1); //Qh4+
	}
	
	/**
	 * Test to check that the game is not in stalemate by using the 
	 * initial board setup.
	 * @throws Exception
	 */
	@Test
	public void notInStaleMate() throws Exception {
		Board board = new Board(8, 8);
		boolean isStaleMate = board.getStaleMate(0);
		assertFalse(isStaleMate);
	}
	
	/**
	 * Test to check stalemate to confirm that when no piece can move,
	 * the board stops for stalemate.
	 * @throws Exception
	 */
	@Test
	public void inStaleMate() throws Exception {
		Board board = new Board(8,8);
		setUpStaleMate(board);
		boolean isStaleMate = board.getStaleMate(1);
		assertTrue(isStaleMate);
	}
	
	/**
	 * Helper function to set up a stalemate.
	 * @param board
	 */
	public void setUpStaleMate(Board board)
	{
		int team0 = 0;
		int team1 = 1;
		Common.movePiece(board, 2, 1, 2, 3, team0); //c4
		Common.movePiece(board, 7, 6, 7, 4, team1); //h5
		Common.movePiece(board, 7, 1, 7, 3, team0); //h4
		Common.movePiece(board, 0, 6, 0, 4, team1); //a5
		Common.movePiece(board, 3, 0, 0, 3, team0); //Qa4
		Common.movePiece(board, 0, 7, 0, 5, team1); //Ra6
		Common.movePiece(board, 0, 3, 0, 4, team0); //Qxa5
		Common.movePiece(board, 0, 5, 7, 5, team1); //Rah6
		Common.movePiece(board, 0, 4, 2, 6, team0); //Qxc7
		Common.movePiece(board, 5, 6, 5, 5, team1); //f6
		Common.movePiece(board, 2, 6, 3, 6, team0); //Qxd7+
		Common.movePiece(board, 4, 7, 5, 6, team1); //Kf7
		Common.movePiece(board, 3, 6, 1, 6, team0); //Qxb7
		Common.movePiece(board, 3, 7, 3, 2, team1); //Qd3
		Common.movePiece(board, 1, 6, 1, 7, team0); //Qxb8
		Common.movePiece(board, 3, 2, 7, 6, team1); //Qh7
		Common.movePiece(board, 1, 7, 2, 7, team1); //Qxc8
		Common.movePiece(board, 5, 6, 6, 5, team0); //Kg6
		Common.movePiece(board, 2, 7, 4, 5, team1); //Qe6
	}
	
	
}
