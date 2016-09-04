package Framework;
public class Move
{
	protected int startX;
	protected int endX;
	protected int startY;
	protected int endY;
	protected int team;
	
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