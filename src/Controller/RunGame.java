package Controller;
import java.util.*;

import Model.Board;
import Model.Move;
import Model.Team;
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
	private static List<Move> allPossibleMoves;
	/**
	 * Main function to set-up the game and then start the moves.
	 * @param args
	 */
	public static void main(String args[])
	{
		Board board = new Board(8,8);
		gui = new GUI(board);
	}

	public static Board setInputs(int xValue, int yValue, Board board)
	{
		if(numMoves == 1 && xValue == inputs.get(0) && yValue == inputs.get(1))
		{
			numMoves = 0;
			gui.unsetBorder(xValue, yValue);
			removeInputs();
			return board;
		}
		numMoves += 1;
		inputs.add(xValue);
		inputs.add(yValue);
		if(numMoves == 2)
		{
			Move move = new Move(inputs.get(0), inputs.get(1), inputs.get(2), inputs.get(3), turnTeamNumber);
			int ret = playMove(board, board.getPositions(), move);
			if(ret == -1)
			{
				gui.unsetBorder(inputs.get(0), inputs.get(1));
				gui.unsetBorder(xValue, yValue);
			}
			removeInputs();
		}
		else
		{
			displayPossibleMoves(board, yValue, xValue);
		}
		return board;
	}
	
	public static void displayPossibleMoves(Board board, int yValue, int xValue)
	{
		Piece[][] positions = board.getPositions();
		Piece piece = positions[yValue][xValue];
		if(piece == null)
		{
			gui.unsetBorder(xValue, yValue);
			gui.errorMessage("No Piece at that Start Coordinate");
			removeInputs();
		}
		else
		{
			if(piece.getTeamNumber() != turnTeamNumber)
			{
				Team turnOfTeam = board.getTeam(turnTeamNumber);
				gui.unsetBorder(xValue, yValue);
				gui.errorMessage("It is " + turnOfTeam.getTeamName() + "'s turn.");
				removeInputs();
			}
			else
			{
				allPossibleMoves = piece.findAllMoves(board);
				gui.showPossibleMoves(allPossibleMoves);
			}
		}
	}
	
	public static void forfeitGame(Board board, Boolean useCustomPieces)
	{
		Team notForfeitTeam = board.getTeam(board.toggleTeam(turnTeamNumber));
		notForfeitTeam.incrementTeamScore();
		restartGame(board, useCustomPieces);
	}
	
	public static void restartGame(Board board, Boolean useCustomPieces)
	{
		board.setInitialBoard(useCustomPieces);
		numMoves = 0;
		inputs = new ArrayList<Integer>();
		turnTeamNumber = 0;
	}
	
	public static void removeInputs()
	{
		numMoves = 0;
		inputs = new ArrayList<Integer>();
		gui.unshowPossibleMoves(allPossibleMoves);
	}
	
	public static void undoMove(Board board, Boolean useCustomPieces)
	{
		Team opposingTeam = board.getTeam(board.toggleTeam(turnTeamNumber));
		Move lastOpposingMove = opposingTeam.undoLastMove();
		Team currentTeam = board.getTeam(turnTeamNumber);
		Move lastMoveByCurrentTeam = currentTeam.undoLastMove();
		if(lastOpposingMove != null && lastMoveByCurrentTeam != null)
		{
			board.unsetPositions(lastOpposingMove, lastOpposingMove.getRemovedPiece());
			board.unsetPositions(lastMoveByCurrentTeam, lastMoveByCurrentTeam.getRemovedPiece());
		}
		else
		{
			restartGame(board, useCustomPieces);
		}
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
			gui.errorMessage("Stale Mate!");
			return -1;
		}
		Piece piece = positions[move.getStartY()][move.getStartX()];
		if(piece.getTeamNumber() != turnTeamNumber)
		{
			gui.errorMessage("It is team " + turnTeamNumber + "'s turn");
			return -1;
		}
		
		if(board.isTeamInCheckAfterMove(move))
		{
			gui.errorMessage("Invalid Move: Your King is in Check");
			return -1;
		}
		boolean isValid = piece.isValidMove(move, board);
		if(!isValid)
		{
			gui.errorMessage("Invalid Move");
			return -1;
		}
		board.setPositions(move);
		Team moving = board.getTeam(turnTeamNumber);
		moving.addMove(move);
		gui.makeMove(move);
		board.printBoard();
		boolean isCheckMate = board.getCheckMate(board.toggleTeam(turnTeamNumber)); // Check if the opposing team lost
		if(isCheckMate)
		{
			int winningTeamNumber = turnTeamNumber;
			turnTeamNumber = board.toggleTeam(turnTeamNumber);
			gui.isCheckMate(winningTeamNumber);
			return 0;
		}
		turnTeamNumber = board.toggleTeam(turnTeamNumber);
		
		if(board.isTeamInCheck(turnTeamNumber))
		{
			gui.errorMessage("Team " + turnTeamNumber + " is in Check!");
		}
		return 0;
	}
	
	
}