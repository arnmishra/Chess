package Tests;

import java.util.List;
import Framework.Move;

/**
 * Common class to hold any common methods across the tests.
 * @author arnavmishra
 *
 */
public class Common
{
	/**
	 * Helper method to check if two lists of moves are the same by
	 * checking that every move has the same start and end coordinates
	 * and team number. This is used to confirm that the lists of all
	 * possible moves in each piece class is correct.
	 * @param expected
	 * @param actual
	 * @return True/False whether the lists are equal.
	 */
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