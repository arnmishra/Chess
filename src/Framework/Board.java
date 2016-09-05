package Framework;
import java.util.*;

import Framework.Move;
import Framework.Team;
import Framework.Pieces.*;

public class Board
{
	
	private Piece[][] positions; // Holds positions of all pieces on the board.
	private int width;
	private int length;
	private Team team0 = new Team(0);
	private Team team1 = new Team(1);
	
	public Board(int width, int length)
	{
		this.width = width;
		this.length = length;
		this.positions = setBoard();
		
	}
	
	public Piece[][] setBoard()
	{
		Piece[][] positions = new Piece[this.length][this.width];
		positions[0] = setPrimaryPieces(positions, 0, team0);
		positions[7] = setPrimaryPieces(positions, 7, team1);
		for(int i = 0; i < 8; i++)
		{
			positions[1][i] = new Pawn(team0);
			setCoordinates(positions[1][i], i, 1);
			positions[6][i] = new Pawn(team1);
			setCoordinates(positions[6][i], i, 6);
		}
		team0.addPieces(positions[0]);
		team0.addPieces(positions[1]);
		team1.addPieces(positions[7]);
		team1.addPieces(positions[6]);
		return positions;
	}
	
	
	public Piece[] setPrimaryPieces(Piece[][] positions, int yValue, Team team)
	{
		positions[yValue][0] = new Rook(team);
		setCoordinates(positions[yValue][0], 0, yValue);
		positions[yValue][1] = new Knight(team);
		setCoordinates(positions[yValue][1], 1, yValue);
		positions[yValue][2] = new Bishop(team);
		setCoordinates(positions[yValue][2], 2, yValue);
		positions[yValue][3] = new King(team);
		setCoordinates(positions[yValue][3], 3, yValue);
		positions[yValue][4] = new Queen(team);
		setCoordinates(positions[yValue][4], 4, yValue);
		positions[yValue][5] = new Bishop(team);
		setCoordinates(positions[yValue][5], 5, yValue);
		positions[yValue][6] = new Knight(team);
		setCoordinates(positions[yValue][6], 6, yValue);
		positions[yValue][7] = new Rook(team);
		setCoordinates(positions[yValue][7], 7, yValue);
		return positions[yValue];
	}
	
	public void setCoordinates(Piece piece, int xValue, int yValue)
	{
		piece.setXValue(xValue);
		piece.setYValue(yValue);
	}
	
	/**
	 * Get width of board
	 * @return width
	 */
	public int getWidth()
	{
		return this.width;
	}
	
	/**
	 * Set width of board
	 * @param width
	 */
	public void setWidth(int width)
	{
		this.width = width;
	}
	
	/**
	 * Get length of board
	 * @return length
	 */
	public int getLength()
	{
		return this.length;
	}
	
	/**
	 * Set length of board
	 * @param length
	 */
	public void setLength(int length)
	{
		this.length = length;
	}
	
	/**
	 * Get positions on entire chess board
	 * @return positions
	 */
	
	public Piece[][] getPositions()
	{
		return this.positions;
	}
	
	/**
	 * Set the new positions of a chess board after a move
	 * @param move
	 */
	public void setPositions(Move move)
	{
		int startX = move.getStartX();
		int startY = move.getStartY();
		int endX = move.getEndX();
		int endY = move.getEndY();
		
		Piece replaced = positions[endY][endX];
		if(replaced != null)
		{
			Team team = replaced.getTeam();
			team.removePiece(replaced);
		}
		Piece moved = positions[startY][startX];
		moved.setXValue(endX);
		moved.setYValue(endY);
		positions[endY][endX] = positions[startY][startX];
		positions[startY][startX] = null;
	}
	
	public void unsetPositions(Move move, Piece removed)
	{
		int startX = move.getStartX();
		int startY = move.getStartY();
		int endX = move.getEndX();
		int endY = move.getEndY();
		
		Piece [] removedArray = new Piece[]{removed};
		
		if(removed != null)
		{
			Team team = removed.getTeam();
			team.addPieces(removedArray);
		}
		
		Piece moved = positions[endY][endX];
		moved.setXValue(startX);
		moved.setYValue(startY);
		
		positions[startY][startX] = moved;
		positions[endY][endX] = removed;
	}
	
	public boolean getCheck(Move move, Board board)
	{
		Piece removed = positions[move.getEndY()][move.getEndX()];
		board.setPositions(move);
		int turnTeamNumber = move.getTeam();
		Team checkTeam = getTeam(turnTeamNumber);
		int opposingTeamNumber = toggleTeam(turnTeamNumber);
		Team opposingTeam = getTeam(opposingTeamNumber);
		List<Piece> checkPieces = checkTeam.getPieces();
		Piece checkKing = null;
		for(int i = 0; i < checkPieces.size(); i++)
		{
			if(checkPieces.get(i) instanceof King)
			{
				checkKing = checkPieces.get(i);
				break;
			}
		}
		int kingX = checkKing.getXValue();
		int kingY = checkKing.getYValue();
		List<Piece> opposingPieces = opposingTeam.getPieces();
		boolean isCheck = false;
		for(int i = 0; i < opposingPieces.size(); i++)
		{
			Piece opposingPiece = opposingPieces.get(i);
			int opposingX = opposingPiece.getXValue();
			int opposingY = opposingPiece.getYValue();
			Move checkMove = new Move(opposingX, opposingY, kingX, kingY, opposingTeamNumber);
			isCheck = opposingPiece.isValidMove(checkMove, board);
			if(isCheck)
			{
				break;
			}
		}
		board.unsetPositions(move, removed);
		return isCheck;
	}
	
	public int toggleTeam(int turnTeamNumber)
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
	
	public Team getTeam(int turnTeamNumber)
	{
		if(turnTeamNumber == 0)
		{
			return this.team0;
		}
		else
		{
			return this.team1;
		}
	}
}