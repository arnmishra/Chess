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
 * Tests for the Pawn class.
 * @author arnavmishra
 *
 */
public class PawnTest {

	/**
	 * Test to check a Pawn's forward single space movement by
	 * moving a pawn up one space.
	 * @throws Exception
	 */
	@Test
	public void validPawnSingleSpaceMovement() throws Exception {
		Board board = new Board(8, 8);
		int teamNumber = 0;
		
		int xCoordinate = 0;
		int yCoordinate = 1;
		Piece pawn = board.getPositions()[yCoordinate][xCoordinate];
		if(!(pawn instanceof Pawn))
		{
			fail("Incorrect piece type");
		}
		Move pawnMoveTwoSpaces = new Move(xCoordinate, yCoordinate, xCoordinate, yCoordinate + 1, teamNumber);
		assertTrue(pawn.isValidMove(pawnMoveTwoSpaces, board));
	}
	
	/**
	 * Test to check a Pawn's forward double space movement by moving a pawn 
	 * up two spaces from initial position.
	 * @throws Exception
	 */
	@Test
	public void validPawnDoubleSpaceMovementFromStart() throws Exception {
		Board board = new Board(8, 8);
		int teamNumber = 0;
		
		int xCoordinate = 0;
		int yCoordinate = 1;
		Piece pawn = board.getPositions()[yCoordinate][xCoordinate];
		if(!(pawn instanceof Pawn))
		{
			fail("Incorrect piece type");
		}
		Move pawnMoveOneSpaces = new Move(xCoordinate, yCoordinate, xCoordinate, yCoordinate + 2, teamNumber);
		assertTrue(pawn.isValidMove(pawnMoveOneSpaces, board));
	}
	
	/**
	 * Test to check a Pawn cannot move forward two spaces from a non-starting
	 * space by moving a Pawn up one space and then trying to move it up two spaces.
	 * @throws Exception
	 */
	@Test
	public void invalidPawnDoubleSpaceMovementFromOtherPosition() throws Exception {
		Board board = new Board(8, 8);
		int teamNumber = 0;
		
		int xCoordinate = 0;
		int yCoordinate = 1;
		Piece pawn = board.getPositions()[yCoordinate][xCoordinate];
		if(!(pawn instanceof Pawn))
		{
			fail("Incorrect piece type");
		}
		Common.movePiece(board, xCoordinate, yCoordinate, xCoordinate, yCoordinate + 1, teamNumber);
		Move pawnMoveTwoSpaces = new Move(xCoordinate, yCoordinate + 1, xCoordinate, yCoordinate + 3, teamNumber);
		assertFalse(pawn.isValidMove(pawnMoveTwoSpaces, board));
	}
	
	/**
	 * Test to check a pawn's diagonal movement by setting up the board with 
	 * two opposing pawns diagonal from each other and ensuring the movement
	 * is allowed.
	 * @throws Exception
	 */
	@Test
	public void validPawnDiagonalMovement() throws Exception {
		Board board = new Board(8, 8);
		
		int xCoordinate = 4;
		int yCoordinate = 1;
		Piece pawn = board.getPositions()[yCoordinate][xCoordinate];
		if(!(pawn instanceof Pawn))
		{
			fail("Incorrect piece type");
		}
		Common.movePiece(board, 4, 1, 4, 3, 0); // Move Team 0's Pawn closer to test taking a piece
		Common.movePiece(board, 3, 6, 3, 4, 1); // Move Team 1's Pawn closer to test taking a piece
		Move pawnMoveDiagonal = new Move(4, 3, 3, 4, 0); // Move Team 1's Pawn closer to test taking a piece
		assertTrue(pawn.isValidMove(pawnMoveDiagonal, board));
	}
	
	/**
	 * Test to ensure a pawn cannot move diagonally if the diagonal position
	 * doesn't have an opposing team.
	 * @throws Exception
	 */
	@Test
	public void invalidPawnDiagonalMovement() throws Exception {
		Board board = new Board(8, 8);
		int teamNumber = 0;
		
		int xCoordinate = 0;
		int yCoordinate = 1;
		Piece pawn = board.getPositions()[yCoordinate][xCoordinate];
		if(!(pawn instanceof Pawn))
		{
			fail("Incorrect piece type");
		}
		// Try diagonal movement.
		Move move = new Move(xCoordinate, yCoordinate, xCoordinate + 1, yCoordinate + 1, teamNumber);
		assertFalse(pawn.isValidMove(move, board));
	}
	
	/**
	 * Test to confirm that all possible movements for a pawn are found from
	 * starting position.
	 * @throws Exception
	 */
	@Test
	public void correctAllStartingPawnMoves() throws Exception {
		Board board = new Board(8, 8);
		
		int xCoordinate = 0;
		int yCoordinate = 1;
		Piece pawn = board.getPositions()[yCoordinate][xCoordinate];
		if(!(pawn instanceof Pawn))
		{
			fail("Incorrect piece type");
		}
		List<Move> expectedMoves = pawn.findAllMoves(board);
		List<Move> actualMoves = new ArrayList<Move>();
		actualMoves.add(new Move(xCoordinate, yCoordinate, 0, 2, 0));
		actualMoves.add(new Move(xCoordinate, yCoordinate, 0, 3, 0));
		
		assertTrue(Common.checkIfMoveListsAreEqual(expectedMoves, actualMoves));
	}

}
