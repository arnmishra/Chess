package Controller;
import java.util.*;

import Model.Board;
import Model.Move;
import Model.Pieces.*;

/**
 * The Game class runs the primary game including developing the UI.
 * @author arnavmishra
 * Fastest check mate: 5153, 4645, 6163, 3773
 */
public class Game
{
	/**
	 * Main function to set-up the game and then start the moves.
	 * @param args
	 */
	public static void main(String args[])
	{
		Board board = new Board(8,8);
		board.setInitialBoard();
		board.printBoard();
		Piece[][] positions = board.getPositions(); 
		Scanner readInput = new Scanner(System.in);
		int turnTeamNumber = 0;
		while(turnTeamNumber != -1)
		{
			turnTeamNumber = playMove(board, positions, readInput, turnTeamNumber);
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
	public static int playMove(Board board, Piece[][] positions, Scanner readInput, int turnTeamNumber)
	{
		boolean isStaleMate = board.getStaleMate(turnTeamNumber);
		if(isStaleMate)
		{
			System.out.println("Stale Mate!");
			return -1;
		}
		int[] inputs = getInputs(readInput);
		if(positions[inputs[1]][inputs[0]] == null)
		{
			System.out.println("No Piece at that Start Coordinate");
			return turnTeamNumber;
		}
		Piece piece = positions[inputs[1]][inputs[0]];
		if(piece.getTeamNumber() != turnTeamNumber)
		{
			System.out.println("It is team " + turnTeamNumber + "'s turn");
			return turnTeamNumber;
		}
		Move move = new Move(inputs[0], inputs[1], inputs[2], inputs[3], turnTeamNumber);
		
		if(board.isTeamInCheckAfterMove(move))
		{
			System.out.println("Invalid Move: Your King is in Check");
			return turnTeamNumber;
		}
		boolean isValid = piece.isValidMove(move, board);
		if(!isValid)
		{
			System.out.println("Invalid Move");
			return turnTeamNumber;
		}
		board.setPositions(move);
		Piece removedPiece = move.getRemovedPiece();
		board.printBoard();
		boolean isCheckMate = board.getCheckMate(board.toggleTeam(turnTeamNumber)); // Check if the opposing team lost
		if(isCheckMate)
		{
			System.out.println("Team " + turnTeamNumber + " wins!");
			return -1;
		}
		return promptForUndoMove(board, removedPiece, move, readInput, turnTeamNumber);
	}
	
	/**
	 * Helper function to get the input start and end coordinates for a move.
	 * @param readInput
	 * @return the input values for the move.
	 */
	public static int[] getInputs(Scanner readInput)
	{
		System.out.print("StartX: ");
		int startX = readInput.nextInt();
		System.out.print("StartY: ");
		int startY = readInput.nextInt();
		System.out.print("EndX: ");
		int endX = readInput.nextInt();
		System.out.print("EndY: ");
		int endY = readInput.nextInt();
		int[] inputs = {startX, startY, endX, endY};
		return inputs;
	}
	
	/**
	 * Helper function to undo a move that a player makes.
	 * @param board
	 * @param removedPiece
	 * @param undoMove
	 * @param readInput
	 * @param turnTeamNumber
	 * @return the TeamNumber for who's turn it currently is.
	 */
	public static int promptForUndoMove(Board board, Piece removedPiece, Move undoMove, Scanner readInput, int turnTeamNumber)
	{
		String undo = "";
		while(!undo.equals("no") && !undo.equals("yes"))
		{
			System.out.print("Undo Move? (Yes/No) ");
			undo = readInput.next();
			undo = undo.toLowerCase();
		}
		if(undo.equals("no"))
		{
			return board.toggleTeam(turnTeamNumber); // If the team won't undo, it's the other team's turn
		}
		else
		{
			board.unsetPositions(undoMove, removedPiece);
			board.printBoard();
			return turnTeamNumber;
		}
	}
	
}