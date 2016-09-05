package Framework;
public class Move
{
	private int startX;
	private int endX;
	private int startY;
	private int endY;
	private int team;
	
	public Move(int startX, int startY, int endX, int endY, int team){
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.team = team;
	}
	
	public int getStartX()
	{
		return this.startX;
	}
	
	public int getEndX()
	{
		return this.endX;
	}
	
	public int getStartY()
	{
		return this.startY;
	}
	
	public int getEndY()
	{
		return this.endY;
	}
	
	public int getTeam()
	{
		return this.team;
	}
	
}