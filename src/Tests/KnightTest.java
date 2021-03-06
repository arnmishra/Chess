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
 * Tests for the Knight class.
 * @author arnavmishra
 *
 */
public class KnightTest {

	/**
	 * Test to confirm valid knight movement by moving a knight up two, left one.
	 * @throws Exception
	 */
	@Test
	public void validKnightMovement() throws Exception {
		Board board = new Board(8, 8);
		board.setInitialBoard(false);
		int teamNumber = 0;
		int xCoordinate = 1;
		int yCoordinate = 0;
		Piece knight = board.getPositions()[yCoordinate][xCoordinate];
		if(!(knight instanceof Knight))
		{
			fail("Incorrect piece type");
		}
		Move knightMoveUpRight = new Move(xCoordinate, yCoordinate, xCoordinate + 1, yCoordinate + 2, teamNumber);
		assertTrue(knight.isValidMove(knightMoveUpRight, board));
		Move knightMoveUpLeft = new Move(xCoordinate, yCoordinate, xCoordinate - 1, yCoordinate + 2, teamNumber);
		assertTrue(knight.isValidMove(knightMoveUpLeft, board));
		Common.movePiece(board, xCoordinate, yCoordinate, xCoordinate - 1, yCoordinate + 2, teamNumber);//Move knight to test
		xCoordinate--;
		yCoordinate += 2;
		Move knightMoveRightUp = new Move(xCoordinate, yCoordinate, xCoordinate + 2, yCoordinate + 1, teamNumber);
		assertTrue(knight.isValidMove(knightMoveRightUp, board));
	}
	
	/**
	 * Test to check invalid knight movement by moving a knight diagonally.
	 * @throws Exception
	 */
	@Test
	public void invalidKnightMovement() throws Exception {
		Board board = new Board(8, 8);
		board.setInitialBoard(false);
		int teamNumber = 0;
		int xCoordinate = 1;
		int yCoordinate = 0;
		Piece knight = board.getPositions()[yCoordinate][xCoordinate];
		if(!(knight instanceof Knight))
		{
			fail("Incorrect piece type");
		}
		Move moveDiagonal = new Move(xCoordinate, yCoordinate, xCoordinate + 1, yCoordinate + 1, teamNumber);// Try diagonal move
		assertFalse(knight.isValidMove(moveDiagonal, board));
		Move moveHorizontal = new Move(xCoordinate, yCoordinate, xCoordinate, yCoordinate + 1, teamNumber);// Try vertical move
		assertFalse(knight.isValidMove(moveHorizontal, board));
		Move moveVertical = new Move(xCoordinate, yCoordinate, xCoordinate + 1, yCoordinate, teamNumber);// Try horizontal move
		assertFalse(knight.isValidMove(moveVertical, board));
	}
	
	/**
	 * Test to confirm that all possible knight moves are available by checking
	 * that the knight has all starting moves listed.
	 * @throws Exception
	 */
	@Test
	public void correctAllStartingKnightMoves() throws Exception {
		Board board = new Board(8, 8);
		board.setInitialBoard(false);
		int xCoordinate = 1;
		int yCoordinate = 0;
		Piece knight = board.getPositions()[yCoordinate][xCoordinate];
		if(!(knight instanceof Knight))
		{
			fail("Incorrect piece type");
		}
		List<Move> expectedMoves = knight.findAllMoves(board);
		List<Move> actualMoves = new ArrayList<Move>();
		actualMoves.add(new Move(xCoordinate, yCoordinate, 2, 2, 0));
		actualMoves.add(new Move(xCoordinate, yCoordinate, 0, 2, 0));
		
		assertTrue(Common.checkIfMoveListsAreEqual(expectedMoves, actualMoves));
	}

}
