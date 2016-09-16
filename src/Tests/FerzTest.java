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
	 * Test to check ferz's invalid movement by trying to move it two
	 * spaces instead of a single space.
	 * @throws Exception
	 */
	@Test
	public void invalidFerzMovement() throws Exception {
		int teamNumber = 0;
		Team team0 = new Team(teamNumber);
		int xValue = 1;
		int yValue = 1;
		
		Board board = new Board(8, 8);
		Ferz ferz = new Ferz(team0, xValue, yValue);
		board.addPieceToBoard(ferz);
		
		Move move = new Move(xValue, yValue, 3, 3, teamNumber);
		
		assertFalse(ferz.isValidMove(move, board));
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
