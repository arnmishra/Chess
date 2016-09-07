package Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import Framework.Board;
import Framework.Move;
import Framework.Pieces.*;
import Tests.Common;

public class PawnTest {

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
		Move pawnMoveTwoSpaces = new Move(xCoordinate, yCoordinate, xCoordinate, yCoordinate + 2, teamNumber);
		assertTrue(pawn.isValidMove(pawnMoveTwoSpaces, board));
	}
	
	@Test
	public void validPawnDoubleSpaceMovement() throws Exception {
		Board board = new Board(8, 8);
		int teamNumber = 0;
		
		int xCoordinate = 0;
		int yCoordinate = 1;
		Piece pawn = board.getPositions()[yCoordinate][xCoordinate];
		if(!(pawn instanceof Pawn))
		{
			fail("Incorrect piece type");
		}
		Move pawnMoveOneSpaces = new Move(xCoordinate, yCoordinate, xCoordinate, yCoordinate + 1, teamNumber);
		assertTrue(pawn.isValidMove(pawnMoveOneSpaces, board));
	}
	
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
		Move team0PawnMove = new Move(4, 1, 4, 3, 0); // Move Team 0's Pawn closer to test taking a piece
		board.setPositions(team0PawnMove);
		Move team1PawnMove = new Move(3, 6, 3, 4, 1); // Move Team 1's Pawn closer to test taking a piece
		board.setPositions(team1PawnMove);
		Move pawnMoveDiagonal = new Move(4, 3, 3, 4, 0); // Move Team 1's Pawn closer to test taking a piece
		assertTrue(pawn.isValidMove(pawnMoveDiagonal, board));
	}
	
	@Test
	public void invalidPawnMovement() throws Exception {
		Board board = new Board(8, 8);
		int teamNumber = 0;
		
		int xCoordinate = 0;
		int yCoordinate = 1;
		Piece pawn = board.getPositions()[yCoordinate][xCoordinate];
		if(!(pawn instanceof Pawn))
		{
			fail("Incorrect piece type");
		}
		Move move = new Move(xCoordinate, yCoordinate, xCoordinate + 1, yCoordinate + 1, teamNumber);// Try diagonal move
		assertFalse(pawn.isValidMove(move, board));
	}
	
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
