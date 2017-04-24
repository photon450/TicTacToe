package The_Game;

import java.util.HashMap;
import java.util.Map;

import java.util.Random;

/*
 * The AI Status :in development not done -DP
 */
public class Adversary {
	
	
	//private XOButton boardAcces = new XOButton();
	HashMap<Integer, Integer> Board; 
	Random seed;
	int move;
	
	public Adversary(){
		
		Board = XOButton.adversaryfeeder();	//reference to the XOBoard
		seed = new Random();
		
	}
			
	public void Admove(){   //ineficent but good placeholder
		
		
		for( Map.Entry<Integer, Integer> e : Board.entrySet()){
			
			move = seed.nextInt(9)+1;  //9 max 1 min
			
			
			while(e.getValue() != -1 && move != e.getKey() ){  //this finds  empty spot and changes it
				Board.put(e.getKey(), 0);
				break;
			}
		}
			
	}

	
	
}
