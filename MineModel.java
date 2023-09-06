import java.util.Random;
import javax.swing.JFileChooser;
import java.io.*;
import javax.swing.*;

public class MineModel implements Serializable  {
	private int rows;
	private int columns;
	private int[][] field;
	private int mineCounter = 10;
	public boolean gameLost;
	public boolean gameWon;
	public boolean[][] flagged;
	public boolean[][] revealed;
	public boolean[][] mine;

	public MineModel(){
		gameLost = false;
		gameWon = false;
		rows = 8;
		columns = 8;
		setDifficulty(rows, columns);
	}

	public void setDifficulty(int rows, int columns){
    	this.rows = rows;
    	this.columns = columns;
    	field = new int[rows][columns];
    	flagged = new boolean[rows][columns];
    	revealed = new boolean[rows][columns];
    	mine = new boolean[rows][columns];
    	mineCounter = 10;

    	for (int i = 0; i < rows; ++i) {
			for (int j = 0; j < columns; ++j) {
				revealed[i][j] = false;
				flagged[i][j] = false;
				mine[i][j] = false;

			}
		}

    	setMines();
    }

	public int getRows(){
		return rows;
	}

	public int getCols(){
		return columns;
	}

	private void setMines(){
		Random x = new Random();
		int numMines = 10;
		int[][] field = this.field;

		while (numMines != 0) {
			for (int i = 0; i < rows; ++i) {
				int randomRow = x.nextInt(8);
				for (int j = 0; j < columns; ++j) {
					int randomCol = x.nextInt(8);
					if(field[randomRow][randomCol] == 0) {
						field[randomRow][randomCol] = 1;
						mine[i][j] = true;
						--numMines;
						break;
					}
				}break;
			}
		}
	}

	public boolean isMine(int i, int j){
		if (field[i][j] == 1){
			return true;
		}else {
			return false;
		}
	}

	public String setNums(int i, int j){
		int adjacentMines = 0;

		if (i > 0 && field[i - 1][j] == 1) {
			adjacentMines++;
		}
		if (i < 7 && field[i + 1][j] == 1) {
			adjacentMines++;
		}
		if (j > 0 && field[i][j - 1] == 1) {
			adjacentMines++;
		}
		if (j < 7 && field[i][j + 1] == 1) {
			adjacentMines++;
		}
		//
		if (i > 0 && j > 0 && field[i - 1][j - 1] == 1) {
			adjacentMines++;
		}
		if (i > 0 && j < 7 && field[i - 1][j + 1] == 1) {
			adjacentMines++;
		}
		if (i < 7 && j < 7 && field[i + 1][j + 1] == 1) {
			adjacentMines++;
		}
		if (i < 7 && j > 0 && field[i + 1][j - 1] == 1) {
			adjacentMines++;
		}	

		String string = String.valueOf(adjacentMines);
		return string;
	}

	public void flag(int i, int j){
		if(field[i][j] == 1) {
			mineCounter --;
		}
	}

	public boolean isFlagged(int row, int col) {
    	return flagged[row][col];
	}

	public void unFlag(int i, int j){
		if(field[i][j] == 1) {
			++mineCounter;
		}

		flagged[i][j] = false;
	}

	public int getMines(){
		return mineCounter;
	}

	public void gameLost(){
		gameLost = true;
	}

	 public int[][] getField() {
        return field;
    }

    public boolean isRevealed(int row, int col) {
    	return !flagged[row][col] && revealed[row][col];
	}

    public void save(){
    	JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File userFile = fileChooser.getSelectedFile();
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(userFile);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
				objectOutputStream.writeObject(this);


                objectOutputStream.close();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    
    }

    public void load(){
    	JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File userFile = fileChooser.getSelectedFile();
            try {
                FileInputStream fileInputStream = new FileInputStream(userFile);
            	ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
	            MineModel saved = (MineModel) objectInputStream.readObject();
	            this.field = saved.field;
				this.rows = saved.rows;
	            this.columns = saved.columns;
	            this.mineCounter = saved.mineCounter;
	            this.flagged = saved.flagged;
	            this.revealed = saved.revealed;
	            this.mine = saved.mine;

	            
            	objectInputStream.close();
                fileInputStream.close();
            } catch (StreamCorruptedException e) {
            	System.out.println("The loaded file does not contain minesweeper data");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
            e.printStackTrace();
        	}
        }
    }
}
