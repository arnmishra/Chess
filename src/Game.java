import java.util.*;

import Framework.Board;
import Framework.Pieces.*;

public class Game
{
	public static void main(String args[])
	{
		Board board = new Board(8,8);
		printBoard(board);
		Piece[][] positions = board.getPositions(); 
		while(true)
		{
			int[] inputs = getInputs();
			if(positions[inputs[0]][inputs[1]] != null)
			{
				Piece piece = positions[inputs[0]][inputs[1]];
				System.out.println("");
				printPiece(piece);
			}
			else
			{
				continue;
			}
		}
	}
	
	public static int[] getInputs()
	{
		Scanner readInput = new Scanner(System.in);
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