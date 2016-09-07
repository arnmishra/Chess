import java.util.*;

import Framework.Board;
import Framework.Move;
import Framework.Pieces.*;

/**
 * The Game class runs the primary game including developing the UI.
 * @author arnavmishra
 *
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
		printBoard(board);
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
		
		if(board.isKingInCheck(move))
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
		printBoard(board);
		boolean isCheckMate = board.getCheckMate(turnTeamNumber);
		if(isCheckMate)
		{
			System.out.println("Team " + turnTeamNumber + " wins!");
			return -1;
		}
		return board.toggleTeam(turnTeamNumber);
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
	 * Helper function to print out the board.
	 * @param board
	 */
	public static void printBoard(Board board)
	{
		Piece[][] positions = board.getPositions();
		System.out.println("   0  1  2  3  4  5  6  7 ");
		System.out.println("  - - - - - - - - - - - - -");
		for(int i = board.getLength() - 1; i >= 0; i--)
		{
			System.out.print(i + " |");
			for(int j = 0; j < board.getWidth(); j++)
			{
				printPiece(positions[i][j]);
				System.out.print("|");
			}
			System.out.print(" " + i);
			System.out.println("");
			System.out.println("  - - - - - - - - - - - - -");
		}
		System.out.println("   0  1  2  3  4  5  6  7 ");
	}
	
	/**
	 * Helper function to print out each piece on the board.
	 * @param piece
	 */
	public static void printPiece(Piece piece)
	{
		if(piece == null)
		{
			System.out.print("  ");
		}
		else
		{
			int team = piece.getTeamNumber();
			if(piece instanceof Rook)
			{
				System.out.print(team + "R");
			}
			else if(piece instanceof Knight)
			{
				System.out.print(team + "N");
			}
			else if(piece instanceof Bishop)
			{
				System.out.print(team + "B");
			}
			else if(piece instanceof Queen)
			{
				System.out.print(team + "Q");
			}
			else if(piece instanceof King)
			{
				System.out.print(team + "K");
			}
			else if(piece instanceof Pawn)
			{
				System.out.print(team + "P");
			}
		}
	}
	
}