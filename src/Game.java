import java.util.*;

import Framework.Board;
import Framework.Move;
import Framework.Pieces.*;

public class Game
{
	public static void main(String args[])
	{
		Board board = new Board(8,8);
		printBoard(board);
		Piece[][] positions = board.getPositions(); 
		Scanner readInput = new Scanner(System.in);
		int turnTeamNumber = 0;
		while(true)
		{
			turnTeamNumber = playMove(board, positions, readInput, turnTeamNumber);
		}
	}
	
	public static int playMove(Board board, Piece[][] positions, Scanner readInput, int turnTeamNumber)
	{
		int[] inputs = getInputs(readInput);
		if(positions[inputs[1]][inputs[0]] == null)
		{
			System.out.println("No Piece at that Start Coordinate");
			return turnTeamNumber;
		}
		Piece piece = positions[inputs[1]][inputs[0]];
		if(piece.getTeam() != turnTeamNumber)
		{
			System.out.println("It is team " + turnTeamNumber + "'s turn");
			return turnTeamNumber;
		}
		Move move = new Move(inputs[0], inputs[1], inputs[2], inputs[3], turnTeamNumber);
		
		/**if(board.getCheck(turnTeamNumber, move))
		{
			System.out.println("Invalid Move: Your King is in Check");
			return turnTeamNumber;
		}*/
		boolean isValid = piece.isValidMove(move, board);
		if(!isValid)
		{
			System.out.println("Invalid Move");
			return turnTeamNumber;
		}
		board.setPositions(move);
		printBoard(board);
		return toggleTeam(turnTeamNumber);
	}
	
	public static int toggleTeam(int turnTeamNumber)
	{
		if(turnTeamNumber == 0)
		{
			turnTeamNumber = 1;
		}
		else
		{
			turnTeamNumber = 0;
		}
		return turnTeamNumber;
	}

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
	
	public static void printBoard(Board board)
	{
		Piece[][] positions = board.getPositions();
		System.out.println("- - - - - - - - - - - - -");
		for(int i = 0; i < board.getLength(); i++)
		{
			System.out.print("|");
			for(int j = 0; j < board.getWidth(); j++)
			{
				printPiece(positions[i][j]);
				System.out.print("|");
			}
			System.out.println("");
			System.out.println("- - - - - - - - - - - - -");
		}
	}
	
	public static void printPiece(Piece piece)
	{
		if(piece == null)
		{
			System.out.print("  ");
		}
		else
		{
			int team = piece.getTeam();
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