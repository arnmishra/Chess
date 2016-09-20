package View;

import javax.swing.JButton;

/**
 * http://stackoverflow.com/questions/12869523/find-index-of-button-in-2d-array-java
 * @author arnavmishra
 *
 */
public class ChessboardTile extends JButton {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5966756585107219979L;
	private int xValue;
    private int yValue;
    
    public ChessboardTile(int yValue, int xValue)
    {
    	super();
    	this.xValue = xValue;
    	this.yValue = yValue;
    }

    public int getXValue() {
        return xValue;
    }

    public int getYValue() {
        return yValue;
    }
}