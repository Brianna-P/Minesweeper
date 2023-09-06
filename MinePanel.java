import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.TitledBorder;
import javax.swing.BorderFactory;
import java.util.Random;

public class MinePanel extends JPanel{
	
	private MineFrame frame;
	public MineModel model;
	public JButton[][] grid;
	public JMenuItem newGameButton;
	public JMenuItem saveGameButton;
	public JMenuItem loadGameButton;
	public JMenuItem quitGameButton;
	public JButton easy;
	public JButton medium;
	public JButton hard;
	private JMenuBar menu;
	private JMenu menuName;
	private int rows;
	private int columns; 

	public MinePanel(MineFrame frame) {
		this.frame = frame;
		this.model = frame.getModel();

		menu = new JMenuBar();
		frame.setJMenuBar(menu); 
		menuName = new JMenu("File");
		newGameButton = new JMenuItem("New");
		menuName.add(newGameButton);
		saveGameButton = new JMenuItem("Save");
		menuName.add(saveGameButton);
		loadGameButton = new JMenuItem("Load");
		menuName.add(loadGameButton);
		quitGameButton = new JMenuItem("Quit");
		menuName.add(quitGameButton);
		menu.add(menuName); 

		newGame();
	}

	public void newGame(){
		removeAll();
		repaint();
		grid = new JButton[model.getRows()][model.getCols()];
		setLayout(new GridLayout(model.getRows(), model.getCols()));

		for (int i = 0; i < model.getRows(); ++i) {
        	for (int j = 0; j < model.getCols(); ++j) {
            	grid[i][j] = new JButton("?");
        		grid[i][j].setFont(new Font("Dialog", Font.BOLD, 16));
        		grid[i][j].setBackground(Color.GRAY);
        		grid[i][j].setOpaque(true);
        		add(grid[i][j]);
        	}
    	}
    	setBorder(BorderFactory.createTitledBorder(
        	BorderFactory.createEtchedBorder(), "Mines Left: " + model.getMines(), TitledBorder.CENTER, TitledBorder.TOP));
    	rows = model.getRows();
		columns = model.getCols();
    	repaint();
    	revalidate();

	}

	public String getText(int i, int j) {
		return grid[i][j].getText();
	}

	public JButton getButton(int i, int j) {
		return grid[i][j];
	}

	public void setFlag(int i, int j) {
		if(grid[i][j].getText() == "?"){
			grid[i][j].setFont(new Font("Dialog", Font.BOLD, 18));
			grid[i][j].setForeground(Color.RED);	
			grid[i][j].setText("►");
			model.flagged[i][j] = true;
			model.flag(i, j);
		} else if(grid[i][j].getText() != "?" && grid[i][j].getText() != "►"){
			model.flagged[i][j] = false;
			System.out.println(model.isFlagged(i, j));
			model.unFlag(i, j);
		} else {
			grid[i][j].setForeground(Color.BLACK);	
			grid[i][j].setText("?");
			model.flagged[i][j] = false;
			System.out.println(model.isFlagged(i, j));
			model.unFlag(i, j);
		}
	}

	public void revealNums(int i, int j) {
		if(model.isMine(i, j)) {
			//GameisOver = true;
			model.gameLost();
			revealBoard();
			grid[i][j].setBackground(Color.RED);
			grid[i][j].setText("MINE");
			for (int x = 0; x < model.getRows(); ++x) {
				for (int y = 0; y < model.getCols(); ++y) {
					grid[x][y].setEnabled(false);
				}
			}
			frame.setBackground(Color.RED);
			frame.setTitle("GAME OVER! YOU HIT A MINE.");
		}
		else {
			model.flagged[i][j] = false;
			if (model.setNums(i, j).equals("0")){
				grid[i][j].setBackground(Color.WHITE);
				grid[i][j].setForeground(Color.BLACK);
				grid[i][j].setText(model.setNums(i, j));
			}
			else if (model.setNums(i, j).equals("1")){
				grid[i][j].setBackground(Color.BLUE);
				grid[i][j].setForeground(Color.BLUE);
				grid[i][j].setText(model.setNums(i, j));
			}
			else if (model.setNums(i, j).equals("2")){
				grid[i][j].setBackground(Color.GREEN);
				grid[i][j].setForeground(Color.GREEN);
				grid[i][j].setText(model.setNums(i, j));
			}
			else { 
				grid[i][j].setBackground(Color.MAGENTA);
				grid[i][j].setForeground(Color.MAGENTA);
				grid[i][j].setText(model.setNums(i, j));
			}
		}
	}

	public void revealBoard(){
		for (int i = 0; i < model.getRows(); ++i) {
        	for (int j = 0; j < model.getCols(); ++j) {
        		if(!model.isFlagged(i, j)) {
	        		if(model.isMine(i, j)) {
	        			grid[i][j].setBackground(Color.RED);
						grid[i][j].setText("MINE");
	        		} else {
	        			revealNums(i, j);
	        		}
	        	}
        	}
        }
	}

	public void checkIfWon() {
		if(model.getMines() == 0) {
			revealBoard();
			for (int x = 0; x < rows; ++x) {
				for (int y = 0; y < columns; ++y) {
					grid[x][y].setEnabled(false);
				}
			}
			frame.setBackground(Color.GREEN);
			frame.setTitle("GAME WON! YOU FLAGGED ALL THE MINES.");
		}
	}

	public JMenuItem getNewGame(){
		return newGameButton;
	}

	public JMenuItem getSaveGame(){
		return saveGameButton;
	}

	public JMenuItem getQuitGame(){
		return quitGameButton;
	}

	public JMenuItem getLoadGame(){
		return loadGameButton;
	}

	public void chooseMode(){
		removeAll();
		repaint();
		frame.setBackground(Color.WHITE);
		frame.setTitle("Minesweeper");
		setBorder(BorderFactory.createTitledBorder(
        	BorderFactory.createEtchedBorder(), "NEW GAME", TitledBorder.CENTER, TitledBorder.TOP));
		easy = new JButton("Easy");
		medium = new JButton("Medium");
		hard = new JButton("Hard");
		add(easy);
		add(medium);
		add(hard);
		repaint();
		revalidate();
	}

	public JButton getEasy(){
		return easy;
	}

	public JButton getMedium(){
		return medium;
	}

	public JButton getHard(){
		return hard;
	}

	public void saveGame(){
		frame.setTitle("GAME SAVED SUCCESSFULLY");
	}

	public void quitGame(){
		frame.dispose();

	}

	public void loadGame(){
		removeAll();
		repaint();
		model.load();
		frame.setBackground(Color.BLACK);
		frame.setTitle("Minesweeper");
		grid = new JButton[model.getRows()][model.getCols()];
		setLayout(new GridLayout(model.getRows(), model.getCols()));
		for (int i = 0; i < model.getRows(); ++i) {
        	for (int j = 0; j < model.getCols(); ++j) {
            	if (model.isRevealed(i, j) && !model.isFlagged(i, j)) {
            		String num = model.setNums(i, j);
               		grid[i][j] = new JButton(num);
               		grid[i][j].setOpaque(true);
               		grid[i][j].setFont(new Font("Dialog", Font.BOLD, 16));
               		grid[i][j].setText(num);
               		if (model.isMine(i, j)) {
                		model.gameLost();
						grid[i][j].setBackground(Color.RED);
						grid[i][j].setText("MINE");
						frame.setBackground(Color.RED);
						frame.setTitle("GAME OVER! YOU HIT A MINE.");
                	}
                	else if (num.equals("0")){
						grid[i][j].setBackground(Color.WHITE);
						grid[i][j].setForeground(Color.BLACK);
					}
					else if (num.equals("1")){
						grid[i][j].setBackground(Color.BLUE);
						grid[i][j].setForeground(Color.BLUE);
					}
					else if (num.equals("2")){
						grid[i][j].setBackground(Color.GREEN);
						grid[i][j].setForeground(Color.GREEN);
					}
					else { 
						grid[i][j].setBackground(Color.MAGENTA);
						grid[i][j].setForeground(Color.MAGENTA);
           			} 
           		}
           		else if (model.isFlagged(i, j) && !model.isRevealed(i, j)) {
           			grid[i][j] = new JButton("►");
           			grid[i][j].setBackground(Color.GRAY);
           			grid[i][j].setForeground(Color.RED);
           			grid[i][j].setOpaque(true);
           			grid[i][j].setText("►");
            	} 
            	else {
                	grid[i][j] = new JButton("?");
            		grid[i][j].setFont(new Font("Dialog", Font.BOLD, 16));
        			grid[i][j].setBackground(Color.GRAY);
        			grid[i][j].setOpaque(true);
        			grid[i][j].setText("?");
            	}

            	add(grid[i][j]);	
        	}
        }

        for (int i = 0; i < model.getRows(); ++i) {
        	for (int j = 0; j < model.getCols(); ++j) {
		        if (model.isMine(i, j) && model.isRevealed(i, j)){
		           	for (int x = 0; x < model.getRows(); ++x){
		           		for (int y = 0; y < model.getCols(); ++y){
		           			revealBoard();
							grid[x][y].setEnabled(false);
		           		}
		           	}
		        }
		    }
		}

		checkIfWon();

        setBorder(BorderFactory.createTitledBorder(
        	BorderFactory.createEtchedBorder(), "Mines Left: " + model.getMines(), TitledBorder.CENTER, TitledBorder.TOP));
        repaint();
        revalidate();
	}
}