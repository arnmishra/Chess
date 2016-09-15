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
 * Tests for the Rook class.
 * @author arnavmishra
 *
 */
public class RookTest {

	/**
	 * Test to check the rook's vertical movement by moving the pawn in front 
	 * of it out of the way and then moving the rook forward.
	 * @throws Exception
	 */
	@Test
	public void validVerticalRookMovement() throws Exception {
		Board board = new Board(8, 8);
		board.setInitialBoard();
		int teamNumber = 0;
		Common.movePiece(board, 0, 1, 0, 3, teamNumber); // Move the pawn out of the way to test the rook
		
		int xCoordinate = 0;
		int yCoordinate = 0;
		Piece rook = board.getPositions()[yCoordinate][xCoordinate];
		if(!(rook instanceof Rook))
		{
			fail("Incorrect piece type");
		}
		Move rookMove = new Move(xCoordinate, yCoordinate, xCoordinate, yCoordinate + 2, teamNumber);
		assertTrue(rook.isValidMove(rookMove, board));
	}
	
	/**
	 * Test to check rook's horizontal movement by moving the pawn in front of
	 * it out of the way, moving the rook forward, and then moving the rook
	 * to the right for the horizontal movement.
	 * @throws Exception
	 */
	@Test
	public void validHorizontalRookMovement() throws Exception {
		Board board = new Board(8, 8);
		board.setInitialBoard();
		int teamNumber = 0;
		Common.movePiece(board, 0, 1, 0, 3, teamNumber); // Move the pawn out of the way to test the rook
		Common.movePiece(board, 0, 0, 0, 2, teamNumber); // Move rook up to open horizontal movement 
		
		int xCoordinate = 0;
		int yCoordinate = 2;
		Piece rook = board.getPositions()[yCoordinate][xCoordinate];
		if(!(rook instanceof Rook))
		{
			fail("Incorrect piece type");
		}
		Move rookMove = new Move(xCoordinate, yCoordinate, xCoordinate + 2, yCoordinate, teamNumber);
		assertTrue(rook.isValidMove(rookMove, board));
	}
	
	/**
	 * Test to check the rook's invalid movement by attempting to move it diagonally 
	 * and checking that this returns false.
	 * @throws Exception
	 */
	@Test
	public void invalidRookMovement() throws Exception {
		Board board = new Board(8, 8);
		board.setInitialBoard();
		int teamNumber = 0;
		Common.movePiece(board, 1, 1, 1, 3, teamNumber); // Move the pawn out of the way to test the rook
		
		int xCoordinate = 0;
		int yCoordinate = 0;
		Piece rook = board.getPositions()[yCoordinate][xCoordinate];
		if(!(rook instanceof Rook))
		{
			fail("Incorrect piece type");
		}
		Move move = new Move(xCoordinate, yCoordinate, xCoordinate + 1, yCoordinate + 1, teamNumber);// Try diagonal move
		assertFalse(rook.isValidMove(move, board));
	}
	
	/**
	 * Test to check all possible moves by the rook by moving the pawn in front of it
	 * out of the way and ensuring that the rook can move to the open positions.
	 * @throws Exception
	 */
	@Test
	public void correctAllStartingRookMoves() throws Exception {
		Board board = new Board(8, 8);
		board.setInitialBoard();
		int teamNumber = 0;
		Common.movePiece(board, 0, 1, 0, 3, teamNumber); // Move the pawn out of the way to test the rook
		
		int xCoordinate = 0;
		int yCoordinate = 0;
		Piece rook = board.getPositions()[yCoordinate][xCoordinate];
		if(!(rook instanceof Rook))
		{
			fail("Incorrect piece type");
		}
		List<Move> expectedMoves = rook.findAllMoves(board);
		List<Move> actualMoves = new ArrayList<Move>();
		actualMoves.add(new Move(xCoordinate, yCoordinate, 0, 1, 0));
		actualMoves.add(new Move(xCoordinate, yCoordinate, 0, 2, 0));
		
		assertTrue(Common.checkIfMoveListsAreEqual(expectedMoves, actualMoves));
	}

}
