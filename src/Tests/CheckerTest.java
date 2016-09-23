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
 * Tests for the Checker Class.
 * @author arnavmishra
 *
 */
public class CheckerTest {

	/**
	 * Test to check the checker's valid movement by placing the checker on
	 * the board and trying to move it diagonally.
	 * @throws Exception
	 */
	@Test
	public void validCheckerMovement() throws Exception {
		int teamNumber = 0;
		Team team0 = new Team(teamNumber);
		int xValue = 1;
		int yValue = 1;
		
		Board board = new Board(8, 8);
		Checker checker = new Checker(team0, xValue, yValue);
		board.addPieceToBoard(checker);
		
		Move move = new Move(xValue, yValue, 2, 2, teamNumber);
		assertTrue(checker.isValidMove(move, board));
	}
	
	/**
	 * Test to check the checker's valid leap movement by placing the checker on
	 * the board and trying to move it diagonally.
	 * @throws Exception
	 */
	@Test
	public void validCheckerLeapMovement() throws Exception {
		int teamNumber = 0;
		Team team0 = new Team(teamNumber);
		int xValue = 1;
		int yValue = 1;
		
		Board board = new Board(8, 8);
		Checker checker = new Checker(team0, xValue, yValue);
		board.addPieceToBoard(checker);
		Checker anotherChecker = new Checker(team0, 2, 2);
		board.addPieceToBoard(anotherChecker);
		
		Move move = new Move(xValue, yValue, 3, 3, teamNumber);
		
		assertTrue(checker.isValidMove(move, board));
	}
	
	/**
	 * Test to check checker's invalid movement by trying to move it two
	 * spaces instead of a single space.
	 * @throws Exception
	 */
	@Test
	public void invalidCheckerMovement() throws Exception {
		int teamNumber = 0;
		Team team0 = new Team(teamNumber);
		int xValue = 1;
		int yValue = 1;
		
		Board board = new Board(8, 8);
		Checker checker = new Checker(team0, xValue, yValue);
		board.addPieceToBoard(checker);
		
		Move moveTooFar = new Move(xValue, yValue, 3, 3, teamNumber);
		assertFalse(checker.isValidMove(moveTooFar, board));
		Move moveHorizontal = new Move(xValue, yValue, 3, 1, teamNumber);
		assertFalse(checker.isValidMove(moveHorizontal, board));
		Move moveVertical = new Move(xValue, yValue, 1, 3, teamNumber);
		assertFalse(checker.isValidMove(moveVertical, board));
	}
	
	/**
	 * Test to check checker's invalid leap movement by trying to jump it over
	 * another piece into a non-vacant square.
	 * @throws Exception
	 */
	@Test
	public void invalidCheckerLeapMovement() throws Exception {
		int teamNumber = 0;
		Team team0 = new Team(teamNumber);
		int xValue = 1;
		int yValue = 1;
		
		Board board = new Board(8, 8);
		Checker checker = new Checker(team0, xValue, yValue);
		Checker anotherChecker = new Checker(team0, 2, 2);
		board.addPieceToBoard(anotherChecker);
		anotherChecker = new Checker(team0, 3, 3);
		board.addPieceToBoard(anotherChecker);
		board.addPieceToBoard(checker);
		
		Move move = new Move(xValue, yValue, 3, 3, teamNumber);
		assertFalse(checker.isValidMove(move, board));
	}
	
	/**
	 * Test to check the checker's possible movements by testing to ensure
	 * that the checker can move to all diagonal positions.
	 * @throws Exception
	 */
	@Test
	public void correctAllCheckerMoves() throws Exception {
		int teamNumber = 0;
		Team team0 = new Team(teamNumber);
		int xValue = 1;
		int yValue = 1;
		
		Board board = new Board(8, 8);
		Checker checker = new Checker(team0, xValue, yValue);
		board.addPieceToBoard(checker);
		
		List<Move> expectedMoves = checker.findAllMoves(board);
		List<Move> actualMoves = new ArrayList<Move>();
		actualMoves.add(new Move(xValue, yValue, 2, 2, teamNumber));
		actualMoves.add(new Move(xValue, yValue, 0, 2, teamNumber));
		actualMoves.add(new Move(xValue, yValue, 2, 0, teamNumber));
		actualMoves.add(new Move(xValue, yValue, 0, 0, teamNumber));
		
		assertTrue(Common.checkIfMoveListsAreEqual(expectedMoves, actualMoves));
	}

}
