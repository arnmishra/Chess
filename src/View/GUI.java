package View;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicButtonUI;

import Model.Board;
import Model.Move;
import Model.Team;
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
		setTeamNames(gameBoard, 0);
		setTeamNames(gameBoard, 1);
		int addCustomPieces = JOptionPane.showConfirmDialog(null, "Do you want to play with custom pieces (Ferz/Checker)?");
		if(addCustomPieces == 0) // 0 Means "Yes"
		{
			gameBoard.setCustomBoard();
		}
		gameBoard = board;
		Piece[][] piecePositions = gameBoard.getPositions();
		try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e) {
            //silently ignore
        }
        window = new JFrame("Chess Game");
        window.setSize(DIMENSIONS + 350, DIMENSIONS + 150);
        window.setLayout(new BoxLayout(window, BoxLayout.X_AXIS));
        JPanel boardPanels = initializeBoard(piecePositions);
        setUpMenu(window);
        JPanel fullWindow = initializeWindow(boardPanels);
        window.setContentPane(fullWindow);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
	
	private void setTeamNames(Board board, int teamNumber)
	{
		String teamName = JOptionPane.showInputDialog("Team " + teamNumber + " Name");
		Team team = board.getTeam(teamNumber);
		team.setTeamName(teamName);
	}
	
	private JPanel initializeWindow(JPanel boardPanels)
	{
		JPanel fullWindow = new JPanel();
        JPanel textPanel = new JPanel();
        Team team0 = gameBoard.getTeam(0);
        Team team1 = gameBoard.getTeam(1);
        String team0Name = team0.getTeamName();
        int team0Score = team0.getTeamScore();
        String team1Name = team1.getTeamName();
        int team1Score = team1.getTeamScore();
		JLabel label = new JLabel("Score: " + team0Score +  " (" + team0Name + ") - " + team1Score + " (" + team1Name + ")");
		textPanel.add(label);
		fullWindow.add(boardPanels);
		fullWindow.add(textPanel);
		return fullWindow;
	}
	
	/**
	 * Function to initialize the board by creating a JPanel that is
	 * correctly colored for each of the tiles on the board.
	 * @param piecePositions
	 */
    private JPanel initializeBoard(Piece[][] piecePositions) {
    	boardPanels = new JPanel();
    	LayoutManager layout = new GridLayout(HEIGHT, WIDTH);
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
		return boardPanels;
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
    
    public void isCheckMate(int teamNumber)
    {
    	Team winningTeam = gameBoard.getTeam(teamNumber);
    	String teamName = winningTeam.getTeamName();
    	JOptionPane.showMessageDialog(null, teamName + " wins!");
    	winningTeam.incrementTeamScore();
    	RunGame.restartGame(gameBoard);
		JPanel boardPanels = initializeBoard(gameBoard.getPositions());
		JPanel fullWindow = initializeWindow(boardPanels);
		window.setContentPane(fullWindow);
		window.setVisible(true);
    }
    
    public void makeMove(Move move)
    {
    	ChessboardTile sourceButton = tiles[move.getStartY()][move.getStartX()];
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
    
    
    public void unsetBorder(int xValue, int yValue)
    {
    	ChessboardTile button = tiles[yValue][xValue];
        button.setBorder(null);
    }
    
    private void setUpMenu(JFrame window) {
        JMenuBar menubar = new JMenuBar();
        JMenu file = new JMenu("File");
        menubar = addMenuItem(window, file, menubar, "Restart");
        menubar = addMenuItem(window, file, menubar, "Forfeit");
        menubar = addMenuItem(window, file, menubar, "Undo");
        window.setJMenuBar(menubar);
    }
    
    private JMenuBar addMenuItem(JFrame window, JMenu file, JMenuBar menubar, String menuItem)
    {
    	JMenuItem newGame = new JMenuItem(menuItem);
        newGame.addActionListener(this);
        file.add(newGame);
        menubar.add(file);
        return menubar;
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
    
    public void errorMessage(String message)
    {
    	JOptionPane.showMessageDialog(null, message);
    }
    
    public void showPossibleMoves(List<Move> allPossibleMoves)
    {
    	for(int i = 0; i < allPossibleMoves.size(); i++)
		{
			Move possibleMove = allPossibleMoves.get(i);
			int endX = possibleMove.getEndX();
			int endY = possibleMove.getEndY();
			ChessboardTile button = tiles[endY][endX];
			button.setBorder(new LineBorder(Color.CYAN, 3));
		}
    }
    
    public void unshowPossibleMoves(List<Move> allPossibleMoves)
    {
    	for(int i = 0; i < allPossibleMoves.size(); i++)
		{
			Move possibleMove = allPossibleMoves.get(i);
			int endX = possibleMove.getEndX();
			int endY = possibleMove.getEndY();
			ChessboardTile button = tiles[endY][endX];
			button.setBorder(null);
		}
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if(action.equals("Restart"))
		{
			int confirmRestart = JOptionPane.showConfirmDialog(null, "Do Both Teams agree to Restart?");
			if(confirmRestart != 0)
			{
				return;
			}
			RunGame.restartGame(gameBoard);
			JPanel boardPanels = initializeBoard(gameBoard.getPositions());
			JPanel fullWindow = initializeWindow(boardPanels);
			window.setContentPane(fullWindow);
			window.setVisible(true);
		}
		else if(action.equals("Forfeit"))
		{
			RunGame.forfeitGame(gameBoard);
			JPanel boardPanels = initializeBoard(gameBoard.getPositions());
			JPanel fullWindow = initializeWindow(boardPanels);
			window.setContentPane(fullWindow);
			window.setVisible(true);
		}
		else if(action.equals("Undo"))
		{
			RunGame.undoMove(gameBoard);
			initializeBoard(gameBoard.getPositions());
			JPanel fullWindow = initializeWindow(boardPanels);
			window.setContentPane(fullWindow);
			window.setVisible(true);
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