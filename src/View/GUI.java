package View;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicButtonUI;

import Model.Board;
import Model.Move;
import Model.Pieces.*;
import Controller.RunGame;

/**
 * Sources: 
 * http://stackoverflow.com/questions/299495/how-to-add-an-image-to-a-jpanel
 * https://wiki.illinois.edu/wiki/pages/viewpage.action?spaceKey=cs242&title=Assignment+1.1
 * http://stackoverflow.com/questions/22865976/how-do-you-change-the-color-of-a-ChessboardTile-after-it-is-clicked
 * GUI class to develop primary chess board.
 * @author arnavmishra
 *
 */
public class GUI implements ActionListener{
	private static ChessboardTile[][] tiles = new ChessboardTile[8][8];
	private static JPanel boardPanels;
	private static int WIDTH = 8;
	private static int HEIGHT = 8;
	private static int DIMENSIONS = 500;
	private static Board gameBoard;
	private static JFrame window;
	private static String PATH = "/Users/arnavmishra/Repos/cs242/Chess/src/View/Pieces/";
	
	/**
	 * Constructor to create GUI using positions of pieces on board.
	 * @param piecePositions
	 */
	public GUI(Board board){
		gameBoard = board;
		Piece[][] piecePositions = board.getPositions();
		try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e) {
            //silently ignore
        }
        window = new JFrame("Chess Game");
        window.setSize(DIMENSIONS, DIMENSIONS);
        initializeBoard(piecePositions);
        setUpMenu(window);
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
    	boardPanels = new JPanel();
    	LayoutManager layout = new GridLayout(WIDTH, HEIGHT);
    	boardPanels.setLayout(layout);
    	Color color = new Color(255, 255, 255); // Top left square is white.
		for(int i = piecePositions.length - 1; i >= 0; i--)
		{
			for(int j = 0; j < piecePositions[0].length; j++)
			{
				ImageIcon label = getPieceImage(i, piecePositions[i][j]);
				ChessboardTile button = new ChessboardTile(i, j);
				button.setLayout(new GridBagLayout());
				button.setIcon(label);
				button.setBackground(color);
				button.setUI(new BasicButtonUI());
				button.addActionListener(this);
				color = toggleColor(color);
				tiles[i][j] = button;
				boardPanels.add(button);
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
    private ImageIcon getPieceImage(int row, Piece piece)
    {
    	if(piece == null)
    	{
    		return null;
    	}
    	
    	String pieceColor = getPieceColor(piece);
    	String piecePath = getPiecePath(piece, pieceColor);
    	BufferedImage pieceImage;
		ImageIcon icon = null;
    	try {
			pieceImage = ImageIO.read(new File(PATH + piecePath));
			Image scaledPiece = pieceImage.getScaledInstance(-1, DIMENSIONS/WIDTH, Image.SCALE_SMOOTH);
			//label = new JLabel(new ImageIcon(scaledPiece));
			icon = new ImageIcon(scaledPiece);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	return icon;
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
    
    public void makeMove(Move move)
    {
    	ChessboardTile sourceButton = tiles[ move.getStartY()][move.getStartX()];
        //lastSource = source;
        ChessboardTile destinationButton = tiles[move.getEndY()][move.getEndX()];
        //lastDestination = destination;
        Icon sourceIcon = sourceButton.getIcon();
        //lastSourceIcon = sourceIcon;
        //lastDestinationIcon = destination.getIcon();
        destinationButton.setIcon(sourceIcon);
        sourceButton.setIcon(null);
        sourceButton.setBorder(null);
        destinationButton.setBorder(null);
    }
    
    
    public void unsetBorder(Move move)
    {
    	ChessboardTile sourceButton = tiles[ move.getStartY()][move.getStartX()];
        ChessboardTile destinationButton = tiles[move.getEndY()][move.getEndX()];
        sourceButton.setBorder(null);
        destinationButton.setBorder(null);
    }
    
    private void setUpMenu(JFrame window) {
        JMenuBar menubar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem newGame = new JMenuItem("New Game");
        newGame.addActionListener(this);
        file.add(newGame);
        menubar.add(file);
        window.setJMenuBar(menubar);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if(action.equals("New Game"))
		{
			gameBoard = RunGame.restartGame();
			initializeBoard(gameBoard.getPositions());
			window.setContentPane(boardPanels);
			window.setVisible(true);
			return;
		}
		else
		{
			ChessboardTile sourceButton = (ChessboardTile)e.getSource();
			sourceButton.setBorder(new LineBorder(Color.RED, 2));
			int xValue = sourceButton.getXValue();
			int yValue = sourceButton.getYValue();
			gameBoard = RunGame.setInputs(xValue, yValue, gameBoard);
		}
	}
}