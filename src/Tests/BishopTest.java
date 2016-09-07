package Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import Framework.Board;
import Framework.Move;
import Framework.Pieces.*;
import Tests.Common;

public class BishopTest {

	@Test
	public void validBishopMovement() throws Exception {
		Board board = new Board(8, 8);
		int teamNumber = 0;
		Move pawnMove = new Move(1, 1, 1, 2, teamNumber); // Move the pawn out of the way to test the bishop
		board.setPositions(pawnMove);
		
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
	
	@Test
	public void invalidBishopMovement() throws Exception {
		Board board = new Board(8, 8);
		int teamNumber = 0;
		Move pawnMove = new Move(2, 1, 2, 3, teamNumber); // Move the pawn out of the way to test the bishop
		board.setPositions(pawnMove);
		
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
	
	@Test
	public void correctAllStartingBishopMoves() throws Exception {
		Board board = new Board(8, 8);
		int teamNumber = 0;
		Move pawnMove = new Move(1, 1, 1, 2, teamNumber); // Move the pawn out of the way to test the bishop
		board.setPositions(pawnMove);
		
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
