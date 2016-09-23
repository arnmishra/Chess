package Controller;
import java.util.*;

import Model.Board;
import Model.Move;
import Model.Team;
import Model.Pieces.*;
import View.GUI;

/**
 * The RunGame class connects the GUI View with the Model of the Chess Game
 * @author arnavmishra
 * Fastest check mate: 5153, 4645, 6163, 3773
 */
public class RunGame
{
	private static int numClicks = 0; //Number of tiles clicked on the GUI --> at 2, make the move
	private static List<Integer> inputs = new ArrayList<Integer>(); //Input coordinates from GUI
	private static int turnTeamNumber = 0; //Which team number's turn it is
	private static GUI gui; //GUI object
	private static List<Move> allPossibleMoves; //A list of all possible moves for the last moved piece
	
	/**
	 * Main function to set-up the board and the gui.
	 * @param args
	 */
	public static void main(String args[])
	{
		Board board = new Board(8,8);
		gui = new GUI(board);
	}
	
	/**
	 * Function to deal with a new board click and add to the inputs list if 
	 * the click is valid
	 * @param xValue
	 * @param yValue
	 * @param board
	 * @return the updated board object
	 */
	public static Board setInputs(int xValue, int yValue, Board board)
	{
		Boolean isMoving = checkMovement(xValue, yValue);
		if(!isMoving)
		{
			return board;
		}
		numClicks += 1;
		inputs.add(xValue);
		inputs.add(yValue);
		if(numClicks == 2) // Make movement after 2 tiles are clicked
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
		else if(numClicks == 1) // Show all possible moves after 1 tile is clicked
		{
			displayPossibleMoves(board, yValue, xValue);
		}
		return board;
	}
	
	/**
	 * Check if the 2nd tile that is clicked is different or if there is no
	 * movement. If nothing moves, un-set the possible moves and delete the inputs
	 * so that the user can enter a different move.
	 * @param xValue
	 * @param yValue
	 * @return true if the 2 clicked tiles are different
	 */
	public static Boolean checkMovement(int xValue, int yValue)
	{
		if(numClicks == 1 && xValue == inputs.get(0) && yValue == inputs.get(1))
		{
			numClicks = 0;
			gui.unsetBorder(xValue, yValue);
			removeInputs();
			return false;
		}
		return true;
	}
	
	/**
	 * Displays all possible moves from the first clicked tile if the tile has a
	 * piece and the piece is on the appropriate team.
	 * @param board
	 * @param yValue
	 * @param xValue
	 */
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
		else if(piece.getTeamNumber() != turnTeamNumber)
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
	
	/**
	 * If a team forfeits, increment the score of the the opposing team and
	 * set up a new board.
	 * @param board
	 * @param useCustomPieces
	 */
	public static void forfeitGame(Board board, Boolean useCustomPieces)
	{
		Team notForfeitTeam = board.getTeam(board.toggleTeam(turnTeamNumber));
		notForfeitTeam.incrementTeamScore();
		restartGame(board, useCustomPieces);
	}
	
	/**
	 * If both teams agree to restart the game, set up a new board, clear
	 * the clicks and input values, and set the turn to team 0.
	 * @param board
	 * @param useCustomPieces
	 */
	public static void restartGame(Board board, Boolean useCustomPieces)
	{
		board.setInitialBoard(useCustomPieces);
		numClicks = 0;
		inputs = new ArrayList<Integer>();
		turnTeamNumber = 0;
	}
	
	/**
	 * Helper function to reset the number of clicks, input values, and 
	 * un-highlight the squares that the last clicked piece can move too.
	 */
	public static void removeInputs()
	{
		numClicks = 0;
		inputs = new ArrayList<Integer>();
		gui.unshowPossibleMoves(allPossibleMoves);
	}
	
	/**
	 * Function to undo the last move by getting the last move made by each
	 * team and unsetting it. If there have been less than 2 moves made, just
	 * reset the entire board.
	 * @param board
	 * @param useCustomPieces
	 */
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