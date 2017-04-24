package The_Game;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel; 

public class TicTacToe  extends JFrame {
	
	JPanel p = new JPanel();
	
	
	

	
	JButton b1 = new JButton("Reset");
	JLabel lbl = new JLabel("Welcome to the Game!");
    XOButton buttons[] = new XOButton[9]; //last one is a flag to reset.
	
	
	public static void main(String args[]){
		
		new TicTacToe();
	}
	
	public TicTacToe() {
		
		super("TicTacToe"); //  //name our jframe.

		
		setSize(500,500);
		
		
		setDefaultCloseOperation(EXIT_ON_CLOSE); //CLOSE program when close window!!
		setResizable(true);  //will make it resizable 
		
		
		//Layout so here are my JPanles!
		
		p.setLayout( new GridLayout(3,3));  //everything added to panel is according to THIS LAYOUT
		
		
		
		for(int i = 0; i<9; i++){    //populate the Grid
			 buttons[i] = new XOButton();
			 p.add(buttons[i]);
			 
			 
		}
		
	
			 
		b1.addActionListener( new ActionListener() {
			
			public void actionPerformed(ActionEvent e){
				reset();
			}
			
		});
		
		add(lbl, BorderLayout.NORTH);
		
		add(p);
		add(b1, BorderLayout.AFTER_LAST_LINE);
		setVisible(true);
		

	}

	public void reset(){    //reset if needed
		
		XOButton button = new XOButton(1); //kill code sent
		
		p.removeAll();  //Removes 
		
		for(int i = 0; i<9; i++){  
			buttons[i] = null; //erase it	
			buttons[i] = new XOButton();
			p.add(buttons[i]);  //add them again
			
		}
		button = null; //remove this from mem not needed now
		p.revalidate();
		p.repaint();
		
		
	}
	
	

	

}
