package Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import Framework.Board;
import Framework.Move;
import Framework.Pieces.*;
import Tests.Common;

public class QueenTest {

	@Test
	public void validQueenForwardMovement() throws Exception {
		Board board = new Board(8, 8);
		int teamNumber = 0;
		Move pawnMove = new Move(3, 1, 3, 3, teamNumber); // Move the pawn out of the way to test the queen forward
		board.setPositions(pawnMove);
		
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
	
	@Test
	public void validQueenDiagonalMovement() throws Exception {
		Board board = new Board(8, 8);
		int teamNumber = 0;
		Move pawnMove = new Move(4, 1, 4, 3, teamNumber); // Move the pawn out of the way to test the queen diagonal
		board.setPositions(pawnMove);
		
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
	
	@Test
	public void invalidQueenMovement() throws Exception {
		Board board = new Board(8, 8);
		int teamNumber = 0;
		Move pawnMove = new Move(3, 1, 3, 3, teamNumber); // Move the pawn out of the way
		board.setPositions(pawnMove);
		pawnMove = new Move(4, 1, 4, 3, teamNumber); // Move the pawn out of the way
		board.setPositions(pawnMove);
		
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
	
	@Test
	public void correctAllStartingQueenMoves() throws Exception {
		Board board = new Board(8, 8);
		int teamNumber = 0;
		Move pawnMove = new Move(3, 1, 3, 3, teamNumber); // Move the pawn out of the way to test forward movement
		board.setPositions(pawnMove);
		pawnMove = new Move(4, 1, 4, 3, teamNumber); // Move the pawn out of the way to test diagonal movement
		board.setPositions(pawnMove);
		
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
