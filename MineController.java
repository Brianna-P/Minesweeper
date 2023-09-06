import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import javax.swing.border.TitledBorder;
import javax.swing.BorderFactory;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MineController extends JPanel {
	private MinePanel panel;
	private int row;
	private int column;
    private boolean isNewGame;
    private boolean isEasy;



	public MineController(MinePanel p){
		panel = p;
        isNewGame = false;
        isEasy = false;
		addHandler();
		addListener();
        addMenuHandler();
	}

	public void addHandler(){
		 ActionListener clickMine = new ActionListener() {
		 	 public void actionPerformed(ActionEvent e) {
                row = -1;
				column = -1;

                for (int i = 0; i < panel.model.getRows(); i++) {
                    for (int j = 0; j < panel.model.getCols(); j++) {
						if(e.getSource() == panel.getButton(i, j)){
                            row = i;
                            column = j;
                            break;
                        }
                    }
                }

                panel.revealNums(row, column);
                panel.model.revealed[row][column] = true;
            }
        };

        for (int i = 0; i < panel.model.getRows(); i++) {
         	for (int j = 0; j < panel.model.getCols(); j++) {
                panel.getButton(i, j).addActionListener(clickMine);
            }
        }
    }

    public void addListener(){
         MouseAdapter flagMine = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                row = -1;
				column = -1;

                for (int i = 0; i < panel.model.getRows(); i++) {
                    for (int j = 0; j < panel.model.getCols(); j++) {
                        if (e.getButton() == MouseEvent.BUTTON3) {
							if(e.getSource() == panel.grid[i][j]){
                            	panel.setFlag(i, j);

                            	panel.checkIfWon();
                           		break;
                           	}
                        }
                    }
                }
                panel.setBorder(BorderFactory.createTitledBorder(
                        BorderFactory.createEtchedBorder(), "Mines Left: " + panel.model.getMines(), TitledBorder.CENTER, TitledBorder.TOP));
            }
        };

        for (int i = 0; i < panel.model.getRows(); i++) {
            for (int j = 0; j < panel.model.getCols(); j++) {
                panel.getButton(i, j).addMouseListener(flagMine);
            }
        }
    }

    public void addMenuHandler(){
        panel.getNewGame().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panel.chooseMode();
                addDifficultyHandler();
            }
        });
         panel.getSaveGame().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panel.model.save();
            }
        });
         panel.getLoadGame().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panel.model.load();
                panel.loadGame();
                addHandler();
                addListener();
                addMenuHandler();
            }
        });
         panel.getQuitGame().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panel.quitGame();
            }
        });
    }

    public void addDifficultyHandler(){
        panel.getEasy().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panel.model.setDifficulty(10, 10);
                panel.newGame();
                addHandler();
                addListener();
            }
        });
         panel.getMedium().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panel.model.setDifficulty(9, 9);
                panel.newGame();
                addHandler();
                addListener();
            }
        });
         panel.getHard().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panel.model.setDifficulty(8, 8);
                panel.newGame();
                addHandler();
                addListener();
            }
        });
    }
}