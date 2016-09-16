package View;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import Model.Board;
import Model.Pieces.*;

/**
 * Sources: 
 * http://stackoverflow.com/questions/299495/how-to-add-an-image-to-a-jpanel
 * https://wiki.illinois.edu/wiki/pages/viewpage.action?spaceKey=cs242&title=Assignment+1.1
 * GUI class to develop primary chess board.
 * @author arnavmishra
 *
 */
public class GUI{
	private static JPanel panel;
	private static JPanel boardPanels = new JPanel();
	private static int WIDTH = 8;
	private static int HEIGHT = 8;
	private static int DIMENSIONS = 500;
	private static String PATH = "/Users/arnavmishra/Repos/cs242/Chess/src/View/Pieces/";
	
	/**
	 * Constructor to create GUI using positions of pieces on board.
	 * @param piecePositions
	 */
	public GUI(Piece[][] piecePositions){
		try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e) {
            //silently ignore
        }
        JFrame window = new JFrame("Chess Game");
        window.setSize(DIMENSIONS, DIMENSIONS);
        initializeBoard(piecePositions);
        window.setContentPane(boardPanels);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
	
	/**
	 * Function to initialize the board by creating a JPanel that is
	 * correctly colored for each of the tiles on the board.
	 * @param piecePositions
	 */
    private void initializeBoard(Piece[][] piecePositions) {
    	LayoutManager layout = new GridLayout(WIDTH, HEIGHT);
    	boardPanels.setLayout(layout);
    	Color color = new Color(255, 255, 255); // Top left square is white.
		for(int i = piecePositions.length - 1; i >= 0; i--)
		{
			for(int j = 0; j < piecePositions[0].length; j++)
			{
				JLabel label = getPieceImage(i, piecePositions[i][j]);
				panel = new JPanel();
				panel.setLayout(new GridBagLayout());
				if(label != null)
				{
					panel.add(label);
				}
				panel.setBackground(color);
				color = toggleColor(color);
				boardPanels.add(panel);
			}
			color = toggleColor(color);
		}
    }
 
    /**
     * Function to get the Image of the Piece that is currently being
     * considered from the View/Pieces directory.
     * @param row
     * @param piece
     * @return
     */
    private JLabel getPieceImage(int row, Piece piece)
    {
    	if(piece == null)
    	{
    		return null;
    	}
    	
    	String pieceColor = getPieceColor(piece);
    	String piecePath = getPiecePath(piece, pieceColor);
    	BufferedImage pieceImage;
		JLabel label = null;
		
    	try {
			pieceImage = ImageIO.read(new File(PATH + piecePath));
			Image scaledPiece = pieceImage.getScaledInstance(-1, DIMENSIONS/WIDTH, Image.SCALE_SMOOTH);
			label = new JLabel(new ImageIcon(scaledPiece));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	return label;
    }
    
    /**
     * Function to get the path to all the Piece images based on what
     * type of piece it is.
     * @param piece
     * @param pieceColor
     * @return
     */
    private String getPiecePath(Piece piece, String pieceColor)
    {
    	if(piece instanceof Rook)
    	{
    		return pieceColor + "Rook.png";
    	}
    	else if(piece instanceof Knight)
    	{
    		return pieceColor + "Knight.png";
    	}
    	else if(piece instanceof Bishop)
    	{
    		return pieceColor + "Bishop.png";
    	}
    	else if(piece instanceof Queen)
    	{
    		return pieceColor + "Queen.png";
    	}
    	else if(piece instanceof King)
    	{
    		return pieceColor + "King.png";
    	}
    	else if(piece instanceof Pawn)
    	{
    		return pieceColor + "Pawn.png";
    	}
    	return null;
    }
    
    /**
     * Function to get the color of the piece based on
     * the team number.
     * @param piece
     * @return
     */
    private String getPieceColor(Piece piece)
    {
    	if(piece.getTeamNumber() == 0)
    	{
    		return "white";
    	}
    	else
    	{
    		return "black";
    	}
    }
    
    /**
     * Function to toggle the tile color so that the tiles alternate.
     * @param color
     * @return
     */
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
 
    /**
     * Main function to make initial board and to set up the GUI.
     * @param args
     */
    public static void main(String[] args) {
    	Board board = new Board(8,8);
		board.setInitialBoard();
		new GUI(board.getPositions());
    }
}