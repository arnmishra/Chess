package Controller;
import java.util.*;

import Model.Board;
import Model.Move;
import Model.Pieces.*;
import View.GUI;

/**
 * The Game class runs the primary game including developing the UI.
 * @author arnavmishra
 * Fastest check mate: 5153, 4645, 6163, 3773
 */
public class RunGame
{
	private static int numMoves = 0;
	private static List<Integer> inputs = new ArrayList<Integer>();
	private static int turnTeamNumber = 0;
	private static GUI gui;
	/**
	 * Main function to set-up the game and then start the moves.
	 * @param args
	 */
	public static void main(String args[])
	{
		Board board = new Board(8,8);
		board.setInitialBoard();
		gui = new GUI(board);
	}

	public static Board setInputs(int xValue, int yValue, Board board)
	{
		numMoves += 1;
		inputs.add(xValue);
		inputs.add(yValue);
		if(numMoves == 2)
		{
			Move move = new Move(inputs.get(0), inputs.get(1), inputs.get(2), inputs.get(3), turnTeamNumber);
			int ret = playMove(board, board.getPositions(), move);
			if(ret == -1)
			{
				gui.unsetBorder(move);
			}
			removeInputs();
		}
		return board;
	}
	
	public static Board restartGame()
	{
		Board board = new Board(8,8);
		board.setInitialBoard();
		removeInputs();
		turnTeamNumber = 0;
		return board;
	}
	
	public static void removeInputs()
	{
		numMoves = 0;
		inputs = new ArrayList<Integer>();
	}
	
	/**
	 * Method to run each move including checking for check, checkmate, stalemate, and validity of move.
	 * @param board
	 * @param positions
	 * @param readInput
	 * @param turnTeamNumber
	 * @return which team's turn it is. -1 when the game is done.
	 */
	public static int playMove(Board board, Piece[][] positions, Move move)
	{
		boolean isStaleMate = board.getStaleMate(turnTeamNumber);
		if(isStaleMate)
		{
			System.out.println("Stale Mate!");
			return -1;
		}
		if(positions[move.getStartY()][move.getStartX()] == null)
		{
			System.out.println("No Piece at that Start Coordinate");
			return -1;
		}
		Piece piece = positions[move.getStartY()][move.getStartX()];
		if(piece.getTeamNumber() != turnTeamNumber)
		{
			System.out.println("It is team " + turnTeamNumber + "'s turn");
			return -1;
		}
		
		if(board.isTeamInCheck(move))
		{
			System.out.println("Invalid Move: Your King is in Check");
			board.printBoard();
			return -1;
		}
		boolean isValid = piece.isValidMove(move, board);
		if(!isValid)
		{
			System.out.println("Invalid Move");
			return -1;
		}
		board.setPositions(move);
		gui.makeMove(move);
		board.printBoard();
		boolean isCheckMate = board.getCheckMate(board.toggleTeam(turnTeamNumber)); // Check if the opposing team lost
		if(isCheckMate)
		{
			System.out.println("Team " + turnTeamNumber + " wins!");
			return -1;
		}
		turnTeamNumber = board.toggleTeam(turnTeamNumber);
		return 0;
	}
	
	
}