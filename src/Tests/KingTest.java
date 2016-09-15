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
 * Tests for the King class.
 * @author arnavmishra
 *
 */
public class KingTest {

	/**
	 * Test to check the king's forward movement by moving the pawn ahead of
	 * it out of the way and ensuring that the King can move forward.
	 * @throws Exception
	 */
	@Test
	public void validKingForwardMovement() throws Exception {
		Board board = new Board(8, 8);
		int teamNumber = 0;
		Common.movePiece(board, 4, 1, 4, 3, teamNumber); // Move the pawn out of the way to test the king forward
		
		int xCoordinate = 4;
		int yCoordinate = 0;
		Piece king = board.getPositions()[yCoordinate][xCoordinate];
		if(!(king instanceof King))
		{
			fail("Incorrect piece type");
		}
		Move kingMoveForward = new Move(xCoordinate, yCoordinate, xCoordinate, yCoordinate + 1, teamNumber);
		assertTrue(king.isValidMove(kingMoveForward, board)); // Validate king's forward movement
		
	}
	
	/**
	 * Test to check the King's diagonal movement by moving the pawn diagonally 
	 * (left up) ahead out of the way and moving the King in that direction.
	 * @throws Exception
	 */
	@Test
	public void validKingDiagonalMovement() throws Exception {
		Board board = new Board(8, 8);
		int teamNumber = 0;
		Common.movePiece(board, 3, 1, 3, 3, teamNumber); // Move the pawn out of the way to test the king diagonal
		
		int xCoordinate = 4;
		int yCoordinate = 0;
		Piece king = board.getPositions()[yCoordinate][xCoordinate];
		if(!(king instanceof King))
		{
			fail("Incorrect piece type");
		}
		Move kingMoveDiagonal = new Move(xCoordinate, yCoordinate, xCoordinate + -1, yCoordinate + 1, teamNumber);
		assertTrue(king.isValidMove(kingMoveDiagonal, board)); // Validate king's diagonal movement
	}
	
	/**
	 * Test to check the King's invalid movement by moving the King
	 * forward 2 spaces.
	 * @throws Exception
	 */
	@Test
	public void invalidKingMovement() throws Exception {
		Board board = new Board(8, 8);
		int teamNumber = 0;
		Common.movePiece(board, 4, 1, 4, 3, teamNumber); // Move the pawn out of the way
		
		int xCoordinate = 4;
		int yCoordinate = 0;
		Piece king = board.getPositions()[yCoordinate][xCoordinate];
		if(!(king instanceof King))
		{
			fail("Incorrect piece type");
		}
		Move move = new Move(xCoordinate, yCoordinate, xCoordinate, yCoordinate + 2, teamNumber);// Try moving forward 2
		assertFalse(king.isValidMove(move, board));
	}
	
	/**
	 * Test to check the possible moves for the King by moving the pawn ahead
	 * and diagonal out of the way and ensuring the King can move to the open
	 * spots.
	 * @throws Exception
	 */
	@Test
	public void correctAllStartingKingMoves() throws Exception {
		Board board = new Board(8, 8);
		int teamNumber = 0;
		Common.movePiece(board, 3, 1, 3, 3, teamNumber); // Move the pawn out of the way
		Common.movePiece(board, 4, 1, 4, 3, teamNumber); // Move the pawn out of the way
		int xCoordinate = 4;
		int yCoordinate = 0;
		Piece king = board.getPositions()[yCoordinate][xCoordinate];
		if(!(king instanceof King))
		{
			fail("Incorrect piece type");
		}
		List<Move> expectedMoves = king.findAllMoves(board);
		List<Move> actualMoves = new ArrayList<Move>();
		actualMoves.add(new Move(xCoordinate, yCoordinate, 4, 1, 0));
		actualMoves.add(new Move(xCoordinate, yCoordinate, 3, 1, 0));
		assertTrue(Common.checkIfMoveListsAreEqual(expectedMoves, actualMoves));
	}

}
