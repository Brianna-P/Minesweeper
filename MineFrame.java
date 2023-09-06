import javax.swing.*;
import java.awt.*;

//MAKING FRAME
public class MineFrame extends JFrame{	
	private MineModel model;
	private MinePanel panel;
	private MineController controller;

	public MineFrame() {
		setTitle("Minesweeper");
		setBackground(Color.BLACK);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 800);
		model = new MineModel();
		panel = new MinePanel(this);
		controller = new MineController(panel);
		//controller = new MineController(panel);
		add(panel);
		setVisible(true);
	}

	public MineModel getModel(){
		return model;
	}
}