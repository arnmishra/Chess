package View;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Sources: 
 * http://stackoverflow.com/questions/299495/how-to-add-an-image-to-a-jpanel
 * https://wiki.illinois.edu/wiki/pages/viewpage.action?spaceKey=cs242&title=Assignment+1.1
 * @author arnavmishra
 *
 */
public class GUI{
	private static JPanel panel = new JPanel();
	private static JPanel board = new JPanel();
	private static int WIDTH = 8;
	private static int HEIGHT = 8;
	private static String PATH = "/Users/arnavmishra/Repos/cs242/Chess/src/View/Pieces/";
	
	
	public GUI(){
		try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e) {
            //silently ignore
        }
        JFrame window = new JFrame("Chess Game");
        window.setSize(500, 500);
        initializeBoard();
        window.setContentPane(board);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
	
    private void initializeBoard() {
    	LayoutManager layout = new GridLayout(WIDTH, HEIGHT);
    	board.setLayout(layout);
    	Color color = new Color(255, 255, 255); // Bottom left is white.
		for(int i = 0; i < HEIGHT; i++)
		{
			for(int j = 0; j < WIDTH; j++)
			{
				JLabel label = getPieceImage(i, j);
				panel = new JPanel();
				panel.setLayout(new GridBagLayout());
				if(label != null)
				{
					panel.add(label);
				}
				panel.setBackground(color);
				color = toggleColor(color);
				board.add(panel);
			}
			color = toggleColor(color);
		}
    }
 
    private JLabel getPieceImage(int i, int j)
    {
    	String piecePath = null;
    	if(i == 0)
    	{
    		piecePath = "black" + getPrimaryPathName(j) + ".png";
    	}
    	else if(i == 1)
    	{
    		piecePath = "blackPawn.png";
    	}
    	else if(i == 6)
    	{
    		piecePath = "whitePawn.png";
    	}
    	else if(i == 7)
    	{
    		piecePath = "white" + getPrimaryPathName(j) + ".png";
    	}
    	if(piecePath == null)
    	{
    		return null;
    	}
    	BufferedImage pieceImage;
		JLabel label = null;
    	try {
			pieceImage = ImageIO.read(new File(PATH + piecePath));
			Image scaledPiece = pieceImage.getScaledInstance(-1, 62, Image.SCALE_DEFAULT);
			label = new JLabel(new ImageIcon(scaledPiece));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return label;
    }
    
    private String getPrimaryPathName(int j)
    {
    	if(j == 0 || j == 7)
    	{
    		return "Rook";
    	}
    	else if(j == 1 || j == 6)
    	{
    		return "Knight";
    	}
    	else if(j == 2 || j == 5)
    	{
    		return "Bishop";
    	}
    	else if(j == 3)
    	{
    		return "Queen";
    	}
    	else if(j == 4)
    	{
    		return "King";
    	}
    	return null;
    }
    
    private Color toggleColor(Color color)
    {
    	if(color.getBlue() == 255)
    	{
    		return new Color(100, 100, 100);
    	}
    	else
    	{
    		return new Color(255, 255, 255);
    	}
    }
 
    public static void main(String[] args) {
        new GUI();
    }
}