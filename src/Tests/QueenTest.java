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
 * Tests for the queen class.
 * @author arnavmishra
 *
 */
public class QueenTest {

	/**
	 * Tests the queen's forward movement by moving the pawn in front of
	 * it and then moving the queen forward from there.
	 * @throws Exception
	 */
	@Test
	public void validQueenForwardMovement() throws Exception {
		Board board = new Board(8, 8);
		board.setInitialBoard();
		int teamNumber = 0;
		Common.movePiece(board, 3, 1, 3, 3, teamNumber); // Move the pawn out of the way to test the queen forward
		
		int xCoordinate = 3;
		int yCoordinate = 0;
		Piece queen = board.getPositions()[yCoordinate][xCoordinate];
		if(!(queen instanceof Queen))
		{
			fail("Incorrect piece type");
		}
		Move queenMoveForward = new Move(xCoordinate, yCoordinate, xCoordinate, yCoordinate + 2, teamNumber);
		assertTrue(queen.isValidMove(queenMoveForward, board)); // Validate queen's forward movement
		
	}
	
	/**
	 * Tests the queen's diagonal movement by moving the pawn that is diagonal
	 * to it (up right) and then moving the queen in that direction.
	 * @throws Exception
	 */
	@Test
	public void validQueenDiagonalMovement() throws Exception {
		Board board = new Board(8, 8);
		board.setInitialBoard();
		int teamNumber = 0;
		Common.movePiece(board, 4, 1, 4, 3, teamNumber); // Move the pawn out of the way to test the queen diagonal
		
		int xCoordinate = 3;
		int yCoordinate = 0;
		Piece queen = board.getPositions()[yCoordinate][xCoordinate];
		if(!(queen instanceof Queen))
		{
			fail("Incorrect piece type");
		}
		Move queenMoveDiagonal = new Move(xCoordinate, yCoordinate, xCoordinate + 2, yCoordinate + 2, teamNumber);
		assertTrue(queen.isValidMove(queenMoveDiagonal, board)); // Validate queen's diagonal movement
	}
	
	/**
	 * Tests the queen's invalid movement by trying a knight's move for the
	 * queen and ensuring that the movement returns as invalid.
	 * @throws Exception
	 */
	@Test
	public void invalidQueenMovement() throws Exception {
		Board board = new Board(8, 8);
		board.setInitialBoard();
		int teamNumber = 0;
		Common.movePiece(board, 3, 1, 3, 3, teamNumber); // Move the pawn out of the way
		Common.movePiece(board, 4, 1, 4, 3, teamNumber); // Move the pawn out of the way
		
		int xCoordinate = 3;
		int yCoordinate = 0;
		Piece queen = board.getPositions()[yCoordinate][xCoordinate];
		if(!(queen instanceof Queen))
		{
			fail("Incorrect piece type");
		}
		Move move = new Move(xCoordinate, yCoordinate, xCoordinate + 1, yCoordinate + 2, teamNumber);// Try knight move
		assertFalse(queen.isValidMove(move, board));
	}
	
	/**
	 * Tests the queen's list of all possible moves by moving both the pawn
	 * in front and the pawn diagonal (up right) out of the way and then 
	 * checking that the queen can move to all the appropriate locations
	 * from there.
	 * @throws Exception
	 */
	@Test
	public void correctAllStartingQueenMoves() throws Exception {
		Board board = new Board(8, 8);
		board.setInitialBoard();
		int teamNumber = 0;
		Common.movePiece(board, 3, 1, 3, 3, teamNumber); // Move the pawn out of the way to test forward movement
		Common.movePiece(board, 4, 1, 4, 3, teamNumber); // Move the pawn out of the way to test diagonal movement
		
		int xCoordinate = 3;
		int yCoordinate = 0;
		Piece queen = board.getPositions()[yCoordinate][xCoordinate];
		if(!(queen instanceof Queen))
		{
			fail("Incorrect piece type");
		}
		List<Move> expectedMoves = queen.findAllMoves(board);
		List<Move> actualMoves = new ArrayList<Move>();
		actualMoves.add(new Move(xCoordinate, yCoordinate, 3, 1, 0));
		actualMoves.add(new Move(xCoordinate, yCoordinate, 3, 2, 0));
		actualMoves.add(new Move(xCoordinate, yCoordinate, 4, 1, 0));
		actualMoves.add(new Move(xCoordinate, yCoordinate, 5, 2, 0));
		actualMoves.add(new Move(xCoordinate, yCoordinate, 6, 3, 0));
		actualMoves.add(new Move(xCoordinate, yCoordinate, 7, 4, 0));
		
		assertTrue(Common.checkIfMoveListsAreEqual(expectedMoves, actualMoves));
	}

}
