package The_Game;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.awt.Color;
import java.awt.event.ActionEvent; 

/*
 * Sets its Icons and action Listeners
 */

public class XOButton extends JButton implements ActionListener {  //pritty much the Jbutton
	
	
	private static Adversary AI = new Adversary();  //instatiate our AI
	private ImageIcon X,O; //both letters not number, also will hold our icon and not call pc constantly.

	private static JLabel lblcopy; //COPY OF THE LABEL in the ui
	
	private static byte total; //counts how many have been changed
	private static int pos = 0;  //counter
	private int indivpos = 0; //actual position of this individual button
	private static byte value = 0;  //my boolean for 3 possible outcomes
	private static boolean gamedone = false;
	public static boolean  aiActivity;  //if true on. 
	
	private static HashMap<Integer, Integer> map; // = new Map<XOButton,Integer>();   //so our position, value 
	private static HashMap<XOButton,Integer> board; //testing    this individual button and position
	/*
	 *   0: is x
	 *   1: is O
	 *   
	 */
	
	//Constructor
	public XOButton(){
	
		try{
			pos++;
		}catch( Exception e){
			pos = 0;
		}
		
		indivpos = pos;
		
		try {
		map.put(indivpos, -1);
		board.put(this, pos);
		} catch ( Exception e){  //then its new and not initialized
			
			map = new HashMap<>();
			map.put(indivpos, -1);
			
			board = new HashMap<>();
			if(map.size() >= 9)
			board.put(this, pos);
		}
		
		
		X = new ImageIcon(this.getClass().getResource("OButton.png")); //forlder of my resource
		O = new ImageIcon(this.getClass().getResource("xButton.png")); 
		//we are triggering         //we are Listening               both 	
		this.addActionListener(this);
		System.out.println(indivpos);
	}
	
	//this constructor receives a kill code to reset mid game from the UI.
	public XOButton(int i){
		if(i ==1){
			clear();
			setVisible(false);
			lblcopy.setText("New Game");
			lblcopy.setOpaque(false);
		}
	} 
	
	
	public XOButton(JLabel lbl) {
		
		lbl.setText("Game Started");
		lblcopy  = lbl;
		
		
	}

	public void actionPerformed(ActionEvent e) {
		
	    if(this.getIcon() != null){return;}  //already set don't change it!! (handles people clicking on tile that already has icon)
	     
	   
		total++;
		value++;
		 
		value%=2; //  brings it back down to 0 if we are at 3;     3 = 3 % 3 //would have nothing remaining so sets it to 0
		
		switch(value){
		
		case 0:
			if(!aiActivity){ //if its not the ai 
			map.put(indivpos, (int) value);   //if its the ai he already does this
			
			System.out.println(indivpos + " " + value);
			setIcon(X);
			
		 System.out.println("X");
			
			break; //dont break, AI already moved hence its the 1st player moving
			}
		case 1:    //1 or X is O... (	This is palyer 1
			setIcon(O);
			map.put(indivpos, (int) value);
			System.out.println(indivpos + " " + value);
			System.out.println("O");
			
			if(aiActivity){  //1 st player moves so this works after they move , then it goes to case 0(wich the human will click so hence the break
				if(total > 3)
					checkGame(); //check if human won.
				//if not ai gets to move
				if(!gamedone)
				AI.Admove();
				total++;  //add the ai's move
				value++;
			}
			break;
		}
		
		if(total >3){
			checkGame();
		} 
	}

	public void checkGame(){
		checkHorizontally();
		checkVertical();
		checkDiagonal();
		
		
		
		//its a draw if true
		if(total == 9){
			if(gamedone){
				clear();
				return;
			}
			JOptionPane.showMessageDialog(this, "Game was a Draw please reset if you want to play again");
			//if(gamedone){
			//	return; //already cleared can lead to error
			//}
			clear();   //reset everything
			return;
		}
	}
	
	
	private void checkHorizontally(){
		
		if(gamedone){
			return;
		}
		
		int ply1 = 0; //player 1 score
		int ply2 = 0; //player 2 score
		
		int p = 3;  //place will inner loop will end
		int q =1;   //the place where inner loop will start
		for(int i = 1; i <=9; ){
			
			
			//if(!aiActivity){
			for(int n = q; n <=p ; n++){
				
				if( map.get(n) == 0){
				  
					ply2++;
				}
		
				
				else if( map.get(n) == 1){
					ply1++;
				} 
				
			}
			
			
			q+=3;
			p+=3;
			i+=3;		

			if(gamedone)
				return;
		
			if(checkForWin(ply1,ply2)){
				return; //game is finished
			}
			
			ply1 = 0;
			ply2 = 0;
		}
		
	
	}
	
	public void checkVertical(){
		
		if(gamedone)
			return;
			
		
		int ply1 = 0; //player 1 score
		int ply2 = 0; //player 2 score
		
		for(int i = 1; i <= 3;i++){
			
			
			for(int n = i; n <=9; n+=3){
				
				if(map.get(n) == 0){
					ply2++;
				} 
				else if(map.get(n) == 1 ){
					ply1++;
				}
				
			}
			
		
			if(gamedone)
				return;
			
			if(checkForWin(ply1,ply2)){
				break; //game is finished
			}
			
			ply1 = 0;
			ply2 = 0;
			
		}
		
		
	}
	
	public void checkDiagonal(){
		
		if(gamedone)
			return;
			
		int ply1 = 0; //player 1 score
		int ply2 = 0; //player 2 score
		
		
		//check bottom left to upper right
		for(int i =3; i<=7; i+=2){
			if(map.get(i) == 0){
				ply2++;
				
			}else if( map.get(i) == 1){
				ply1++;
			}
			
			
			
			
		}
		
		
		
		if(checkForWin(ply1,ply2)){
			return; //game is finished
		}
		
		ply1 = 0;
		ply2 = 0;
		
		// check for upper right to lower left diagonal wins
		for(int i= 1; i<=9; i+=4){
			if(map.get(i) == 0){
				ply2++;
			} else if(map.get(i) == 1){
				ply1++;
			}
		}
		
		
		
		if(checkForWin(ply1,ply2)){
			return; //game is finished
		}
		
		
		
			
	}
	
	//reset mechanism
	public void clear(){
		pos = 0;
		map.clear();
		total = 0;
		value = 0;
		gamedone = false;
		board.clear();
	}
	
	public boolean checkForWin(int i, int y){  //i is ply1 ie. player 1, y is player 2
		
		if( i == 3){
			
			JOptionPane.showMessageDialog(this, "Player 1 won  please press the RESET button");
			//clear();
			gamedone = true;
			lblcopy.setText("Player 1 won");
			lblcopy.setOpaque(true);
			lblcopy.setBackground(Color.GREEN);
			
			return true;
			
		}else if (y ==3){
			JOptionPane.showMessageDialog(this, "Player 2 won please press the RESET button");
			lblcopy.setText("Player 2 won");
			lblcopy.setOpaque(true);
			lblcopy.setBackground(Color.RED);
			
			gamedone=true;
			return true;
		}
		
		return false; 
	}
	
	
	public boolean getStatus(){
		
		return gamedone;
	}
	
	
	
	public static HashMap<XOButton, Integer> adversaryfeeder(){
		
	
		return board;   //retrive list of xoboard to change the logo 
		
	}
	
	
	public static  HashMap<Integer, Integer> adversaryfeeder(int i){
		
		
	
			return map; //retrive list of buttons position and icon value
	}
	//REMEBER TO ADD THIS TO CLEAR()
	public static void setPlayerMode(){
		aiActivity =true;
	}
}

