package Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import Model.Board;
import Model.Move;
import Model.Pieces.*;
import Tests.Common;

/**
 * Tests for the Bishop Class.
 * @author arnavmishra
 *
 */
public class BishopTest {

	/**
	 * Test to check the bishop's valid movement by moving the pawn that is
	 * diagonally to the left up from it and then moving the bishop in that
	 * direction.
	 * @throws Exception
	 */
	@Test
	public void validBishopMovement() throws Exception {
		Board board = new Board(8, 8);
		board.setInitialBoard();
		int teamNumber = 0;
		Common.movePiece(board, 1, 1, 1, 2, teamNumber); // Move the pawn out of the way to test the bishop
		
		int xCoordinate = 2;
		int yCoordinate = 0;
		Piece bishop = board.getPositions()[yCoordinate][xCoordinate];
		if(!(bishop instanceof Bishop))
		{
			fail("Incorrect piece type");
		}
		Move bishopMove = new Move(xCoordinate, yCoordinate, xCoordinate - 2, yCoordinate + 2, teamNumber);
		assertTrue(bishop.isValidMove(bishopMove, board));
	}
	
	/**
	 * Test to check bishop's invalid movement by moving the pawn in front of it
	 * and testing to ensure the bishop cannot move forward.
	 * @throws Exception
	 */
	@Test
	public void invalidBishopMovement() throws Exception {
		Board board = new Board(8, 8);
		board.setInitialBoard();
		int teamNumber = 0;
		Common.movePiece(board, 2, 1, 2, 3, teamNumber); // Move the pawn out of the way to test the bishop
		
		int xCoordinate = 2;
		int yCoordinate = 0;
		Piece bishop = board.getPositions()[yCoordinate][xCoordinate];
		if(!(bishop instanceof Bishop))
		{
			fail("Incorrect piece type");
		}
		Move move = new Move(xCoordinate, yCoordinate, xCoordinate, yCoordinate + 2, teamNumber);// Try forward move
		assertFalse(bishop.isValidMove(move, board));
	}
	
	/**
	 * Test to check the bishop's possible movements by moving the pawn diagonally
	 * up left from it out of the way and testing to ensure that the bishop can move
	 * to all diagonal positions.
	 * @throws Exception
	 */
	@Test
	public void correctAllStartingBishopMoves() throws Exception {
		Board board = new Board(8, 8);
		board.setInitialBoard();
		int teamNumber = 0;
		Common.movePiece(board, 1, 1, 1, 2, teamNumber); // Move the pawn out of the way to test the bishop
		
		int xCoordinate = 2;
		int yCoordinate = 0;
		Piece bishop = board.getPositions()[yCoordinate][xCoordinate];
		if(!(bishop instanceof Bishop))
		{
			fail("Incorrect piece type");
		}
		List<Move> expectedMoves = bishop.findAllMoves(board);
		List<Move> actualMoves = new ArrayList<Move>();
		actualMoves.add(new Move(xCoordinate, yCoordinate, 1, 1, 0));
		actualMoves.add(new Move(xCoordinate, yCoordinate, 0, 2, 0));
		
		assertTrue(Common.checkIfMoveListsAreEqual(expectedMoves, actualMoves));
	}

}
