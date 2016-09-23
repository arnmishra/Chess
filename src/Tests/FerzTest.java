package Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import Model.Board;
import Model.Move;
import Model.Team;
import Model.Pieces.*;
import Tests.Common;

/**
 * Tests for the Ferz Class.
 * @author arnavmishra
 *
 */
public class FerzTest {

	/**
	 * Test to check the ferz's valid movement by placing the ferz on
	 * the board and trying to move it diagonally.
	 * @throws Exception
	 */
	@Test
	public void validFerzMovement() throws Exception {
		int teamNumber = 0;
		Team team0 = new Team(teamNumber);
		int xValue = 1;
		int yValue = 1;
		
		Board board = new Board(8, 8);
		Ferz ferz = new Ferz(team0, xValue, yValue);
		board.addPieceToBoard(ferz);
		
		Move ferzMoveUpRight = new Move(xValue, yValue, 2, 2, teamNumber);
		assertTrue(ferz.isValidMove(ferzMoveUpRight, board));
		Move ferzMoveDownRight = new Move(xValue, yValue, 2, 0, teamNumber);
		assertTrue(ferz.isValidMove(ferzMoveDownRight, board));
		Move ferzMoveUpLeft = new Move(xValue, yValue, 0, 2, teamNumber);
		assertTrue(ferz.isValidMove(ferzMoveUpLeft, board));
		Move ferzMoveDownLeft = new Move(xValue, yValue, 0, 0, teamNumber);
		assertTrue(ferz.isValidMove(ferzMoveDownLeft, board));
	}
	
	/**
	 * Test to check ferz's invalid movement by trying to move it forward
	 * instead of in a diagonal, and then trying to move it two spaces diagonally.
	 * @throws Exception
	 */
	@Test
	public void invalidFerzMovement() throws Exception {
		Board board = new Board(8, 8);
		board.setInitialBoard(true);
		int teamNumber = 0;
		Common.movePiece(board, 2, 1, 2, 3, teamNumber);
		Common.movePiece(board, 1, 1, 1, 3, teamNumber); // Move the pawns out of the way to test the ferz
		
		int xCoordinate = 2;
		int yCoordinate = 0;
		Piece ferz = board.getPositions()[yCoordinate][xCoordinate];
		if(!(ferz instanceof Ferz))
		{
			fail("Incorrect piece type");
		}
		Move moveForward = new Move(xCoordinate, yCoordinate, xCoordinate, yCoordinate + 1, teamNumber);// Try forward move
		assertFalse(ferz.isValidMove(moveForward, board));
		Move moveDiagonal = new Move(xCoordinate, yCoordinate, xCoordinate - 2, yCoordinate + 2, teamNumber);// Try diagonal 2 space
		assertFalse(ferz.isValidMove(moveDiagonal, board));
		Move moveOccupied = new Move(xCoordinate, yCoordinate, xCoordinate + 1, yCoordinate + 1, teamNumber);// Try taking own piece
		assertFalse(ferz.isValidMove(moveOccupied, board));
	}
	
	/**
	 * Test to check the ferz's possible movements by testing to ensure
	 * that the ferz can move to all diagonal positions.
	 * @throws Exception
	 */
	@Test
	public void correctAllFerzMoves() throws Exception {
		int teamNumber = 0;
		Team team0 = new Team(teamNumber);
		int xValue = 1;
		int yValue = 1;
		
		Board board = new Board(8, 8);
		Ferz ferz = new Ferz(team0, xValue, yValue);
		board.addPieceToBoard(ferz);
		
		List<Move> expectedMoves = ferz.findAllMoves(board);
		List<Move> actualMoves = new ArrayList<Move>();
		actualMoves.add(new Move(xValue, yValue, 2, 2, teamNumber));
		actualMoves.add(new Move(xValue, yValue, 2, 0, teamNumber));
		actualMoves.add(new Move(xValue, yValue, 0, 2, teamNumber));
		actualMoves.add(new Move(xValue, yValue, 0, 0, teamNumber));
		
		assertTrue(Common.checkIfMoveListsAreEqual(expectedMoves, actualMoves));
	}

}
