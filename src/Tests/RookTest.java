package Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import Framework.Board;
import Framework.Move;
import Framework.Pieces.*;
import Tests.Common;

public class RookTest {

	@Test
	public void validRookMovement() throws Exception {
		Board board = new Board(8, 8);
		int teamNumber = 0;
		Move pawnMove = new Move(0, 1, 0, 3, teamNumber); // Move the pawn out of the way to test the rook
		board.setPositions(pawnMove);
		
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
	
	@Test
	public void invalidRookMovement() throws Exception {
		Board board = new Board(8, 8);
		int teamNumber = 0;
		Move pawnMove = new Move(1, 1, 1, 3, teamNumber); // Move the pawn out of the way to test the rook
		board.setPositions(pawnMove);
		
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
	
	@Test
	public void correctAllStartingRookMoves() throws Exception {
		Board board = new Board(8, 8);
		int teamNumber = 0;
		Move pawnMove = new Move(0, 1, 0, 3, teamNumber); // Move the pawn out of the way to test the rook
		board.setPositions(pawnMove);
		
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
