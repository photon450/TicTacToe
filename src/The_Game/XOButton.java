package The_Game;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener; 
import java.util.HashMap;
import java.awt.event.ActionEvent; 

/*
 * Sets its Icons and action Listeners
 */

public class XOButton extends JButton implements ActionListener {  //pritty much the Jbutton
	
	
	
	ImageIcon X,O; //both letters not number, also will hold our icon and not call pc constantly.

	
	
	static byte total; //counts how many have been changed
	static int pos = 0;  //counter
	int indivpos = 0; //actual position of this individual button
	static byte value = 0;  //my boolean for 3 possible outcomes
	static boolean gamedone = false;
	
	static HashMap<Integer, Integer> map; // = new Map<XOButton,Integer>();   //so our position, value 
	
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
		} catch ( Exception e){
			
			map = new HashMap<>();
			map.put(indivpos, -1);
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
		}
	} 
	
	
	public void actionPerformed(ActionEvent e) {
		
	    if(this.getIcon() != null){return;}  //already set don't change it!!
	    
		total++;
		value++;
		 
		value%=2; //  brings it back down to 0 if we are at 3;     3 = 3 % 3 //would have nothing remaining so sets it to 0
		
		switch(value){
		
		case 0:
			map.put(indivpos, (int) value);
			System.out.println(indivpos + " " + value);
			setIcon(X);
		 System.out.println("X");
			break;
		case 1: 
			setIcon(O);
			map.put(indivpos, (int) value);
			System.out.println(indivpos + " " + value);
			System.out.println("O");
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
	
	
	public void checkHorizontally(){
		
		if(gamedone){
			return;
		}
		
		int ply1 = 0; //player 1 score
		int ply2 = 0; //player 2 score
		
		int p = 3;  //place will inner loop will end
		int q =1;   //the place where inner loop will start
		for(int i = 1; i <=9; ){
			
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
	}
	
	public boolean checkForWin(int i, int y){  //i is ply1 ie. player 1, y is player 2
		
		if( i == 3){
			
			JOptionPane.showMessageDialog(this, "Player 1 won  please press the RESET button");
			//clear();
			gamedone = true;
			return true;
			
		}else if (y ==3){
			JOptionPane.showMessageDialog(this, "Player 2 won please press the RESET button");
			//clear();
			gamedone=true;
			return true;
		}
		
		return false; 
	}
	
}

