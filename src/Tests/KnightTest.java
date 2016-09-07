package Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import Framework.Board;
import Framework.Move;
import Framework.Pieces.*;
import Tests.Common;

public class KnightTest {

	@Test
	public void validKnightMovement() throws Exception {
		Board board = new Board(8, 8);
		int teamNumber = 0;
		int xCoordinate = 1;
		int yCoordinate = 0;
		Piece knight = board.getPositions()[yCoordinate][xCoordinate];
		if(!(knight instanceof Knight))
		{
			fail("Incorrect piece type");
		}
		Move move = new Move(xCoordinate, yCoordinate, xCoordinate + 1, yCoordinate + 2, teamNumber);
		assertTrue(knight.isValidMove(move, board));
	}
	
	@Test
	public void invalidKnightMovement() throws Exception {
		Board board = new Board(8, 8);
		int teamNumber = 0;
		int xCoordinate = 1;
		int yCoordinate = 0;
		Piece knight = board.getPositions()[yCoordinate][xCoordinate];
		if(!(knight instanceof Knight))
		{
			fail("Incorrect piece type");
		}
		Move move = new Move(xCoordinate, yCoordinate, xCoordinate + 1, yCoordinate + 1, teamNumber);// Try diagonal move
		assertFalse(knight.isValidMove(move, board));
	}
	
	@Test
	public void correctAllStartingKnightMoves() throws Exception {
		Board board = new Board(8, 8);
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
