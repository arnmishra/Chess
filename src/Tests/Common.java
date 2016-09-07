package Tests;

import java.util.List;
import Framework.Move;

public class Common
{
	public static boolean checkIfMoveListsAreEqual(List<Move> expected, List<Move> actual)
	{
		Move[] actualMovesArray = actual.toArray(new Move[actual.size()]);	
		Move[] expectedMovesArray = expected.toArray(new Move[expected.size()]);
		
		if(actualMovesArray.length != expectedMovesArray.length)
		{
			return false;
		}
		for(int i = 0; i < actualMovesArray.length; i++)
		{
			if(actualMovesArray[i].getStartX() != expectedMovesArray[i].getStartX())
			{
				return false;
			}
			if(actualMovesArray[i].getStartY() != expectedMovesArray[i].getStartY())
			{
				return false;
			}
			if(actualMovesArray[i].getEndX() != expectedMovesArray[i].getEndX())
			{
				return false;
			}
			if(actualMovesArray[i].getEndY() != expectedMovesArray[i].getEndY())
			{
				return false;
			}
			if(actualMovesArray[i].getTeamNumber() != expectedMovesArray[i].getTeamNumber())
			{
				return false;
			}
		}
		return true;
	}
}