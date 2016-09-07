package Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import Framework.Board;
import Framework.Move;
import Framework.Pieces.*;
import Tests.Common;

public class KingTest {

	@Test
	public void validKingForwardMovement() throws Exception {
		Board board = new Board(8, 8);
		int teamNumber = 0;
		Move pawnMove = new Move(4, 1, 4, 3, teamNumber); // Move the pawn out of the way to test the king forward
		board.setPositions(pawnMove);
		
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
	
	@Test
	public void validKingDiagonalMovement() throws Exception {
		Board board = new Board(8, 8);
		int teamNumber = 0;
		Move pawnMove = new Move(3, 1, 3, 3, teamNumber); // Move the pawn out of the way to test the king diagonal
		board.setPositions(pawnMove);
		
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
	
	@Test
	public void invalidKingMovement() throws Exception {
		Board board = new Board(8, 8);
		int teamNumber = 0;
		Move pawnMove = new Move(4, 1, 4, 3, teamNumber); // Move the pawn out of the way
		board.setPositions(pawnMove);
		
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
	
	@Test
	public void correctAllStartingKingMoves() throws Exception {
		Board board = new Board(8, 8);
		board.printBoard();
		int teamNumber = 0;
		Move pawnMove = new Move(3, 1, 3, 3, teamNumber); // Move the pawn out of the way
		board.setPositions(pawnMove);
		pawnMove = new Move(4, 1, 4, 3, teamNumber); // Move the pawn out of the way
		board.setPositions(pawnMove);
		
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
