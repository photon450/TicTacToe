package The_Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.ImageIcon;

import java.util.Random;

/*
 * The AI Status :in development not done -DP
 * Easy Mode: Random(need to improve efficency
 * Hard Mode: Min max with alpha beta pruning
 */
public class Adversary {    //PC is Player O (player 2)
	
	
	//private XOButton boardAcces = new XOButton();
	private static HashMap<XOButton, Integer> Board; 
	private static Random seed;
	private static int move;
	private static ImageIcon O;
	private static HashMap<Integer, Integer> map; // for hard mode this used as our simulated board for tests, is this ok?
	private int position;
	private int player = 0; //swithcer between AI(0) and Human 1 player
	private boolean simGameStat;
	private int[] PatternStore = {
			0b111000000, 0b000111000, 0b000000111,  //horizontals 1-3
			0b100100100, 0b010010010, 0b001001001,  //verticals 1-3
			0b100010001, 0b001010100  //diagonal top/right down, bottom/left up
	};
	
	
	public Adversary(){
		
		Board = XOButton.adversaryfeeder();	//reference to the XOBoard
		seed = new Random();
		O = new ImageIcon(this.getClass().getResource("OButton.png"));
	}
			
	public  void Admove(){   //Inefficient but good placeholder
		
		
		Board = XOButton.adversaryfeeder();	//reference to the XOBoard
		seed = new Random();
		O = new ImageIcon(this.getClass().getResource("OButton.png"));
		map = XOButton.adversaryfeeder(2); 
		
		//int i = 1;
		int[] buttonAIpicked = move();
		for( Entry<XOButton, Integer> e : Board.entrySet()){
			
			move = seed.nextInt(9)+1;  //9 max 1 min
			
			XOButton tile;
			tile = e.getKey(); //assign the xobutton to set the icon
			
			position = e.getValue(); //assign the position to notify XOButton of the value change
			
			/*if(tile.getIcon() == null && map.get(position) == -1){
				tile.setIcon(O);
			    map.put(position, 0); //0 is for player 2(player O)
				System.out.println("AI moved!");
				break;
			}*/
			//move();
			//The new AI implementation
			if( position == buttonAIpicked[1]){ //hashmaps are NOT in order
				tile.setIcon(O);
				map.put(position, 0); //0 is for player 2(player O)
				System.out.println("Smart AI moved!" + "POsition :" + buttonAIpicked[1] + " with a score : " + buttonAIpicked[0]);
				break;
			}
			
			}
		}
		
		
			
	

	   //To do pass map here and put this tile's postion and chnage the maps value to 1 or whatever O is.
	
	
	
	//Minmax AI code   : aka SAL the ai
	
	

		//return the best move for the Computer player
		int[] move() {
			int[] result = minmax(2, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);  
			   return new int[] { result[0], result[1]};  //score, best tile
		}
		
		/*int choosebest(){
			
			int bestscore = Integer.MIN_VALUE;
			int besttile = 0;
			for(int i = 1; i<=9; i++){
				
				if(bestscore < move()[i]){
				bestscore = move()[i];
				}
			}
			return bestscore;
		}*/
		
		//minmax algorithm for either given player
		
		private int[] minmax(int depth, int player, int alpha, int beta){
			
			
			//Generate our possible SAL's moves
			List<Integer> nextmoves = genmoves();
			
			//The given player of this maximized and other player is minimized
			//int bestScore = ( player == 1) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
			//int currentScore;
			int score;
			//int bestRow = -1;
			//int bestCol = -1;  //these go in next moves
			int bestTile = -1;
			
			if(nextmoves.isEmpty()  || depth == 0){
						//the base case
				score = evaluate();
				return new int[] {score,bestTile};
			} else {
				
				  //for each "move"
				for ( Integer move : nextmoves){
					
					/*cells[move[0]][move[1]].content = aiplayer;*/  //work out my own board to mess with and set this row, col tile to O(the ai player)
					map.put(move, player);  // set it to 0 
					
					
					
					
					
					if(player == 0){  //then the player is the aiplayer so maximize his score!
						
						//currentScore = minimax(depth - 1, oppPlayer)[0];
						score = minmax(depth -1,player = 1, alpha, beta )[0];  //now set player to human and get its min score
						System.out.println("Score: " + score + " move : " + move);
						if(score > alpha){
							alpha = score;
						
						bestTile = move;
						}
						
						
						
					} else {  //human gets minimized, the simulated human
					
						
						score = minmax(depth -1, player = 0, alpha, beta)[0];
						
						if(score < beta){
							beta = score;
						    bestTile = move;
						    System.out.println("AI predicts you will move at: " + bestTile + " with a score of: " + score);
							 
						}
						
					} 
					
					
					//undo the move...
					map.put(move, -1);  //improve this
					
					//stop if we are wasting time
					if(alpha >= beta) break;
					
					
				}
				
				return new int[] {(player == 0)? alpha : beta, bestTile};
			}
		}
			
			//Find all possible valid moves
			//return is a list of arrays[2] of { row, col} or empty list if game is over
			
		private List<Integer> genmoves() {
				
				List<Integer> nextmoves = new ArrayList<Integer>();
				
				
				
				if(won(0) || won(1)){//0 is ai, 1 is human
					return nextmoves;
				}
				//if the game is over (when no more moves)
				/*if(simGameStat){
					 return nextmoves;
				}*/
				
				
						
						//search for any empty cells and add to list
						for(Map.Entry<Integer, Integer> e : map.entrySet()){
							
							if(e.getValue() == -1)
								nextmoves.add(e.getKey());
							
							
						}
						
			return nextmoves;
			
			
		
	}
	
	
	//The component that gives us our scores
			private int evaluate() {
				
				
				int score = 0;
				
				//Evaluate the score for the 8 possible Moveset's.
				
				score += evaluateCondition(1,2,3); //Horizontal row 1
				score += evaluateCondition(4,5,6); //Horizontal row 2
				score += evaluateCondition(7,8,9); //Horizontal row 3
				score += evaluateCondition(1,4,1); //Vertical 1
				score += evaluateCondition(2,5,8); //Vertical 2
				score += evaluateCondition(3,6,9); //Vertical 3
				score += evaluateCondition(1,5,9); //Diagonal 1
				score += evaluateCondition(7,5,3); //Diagonal 2
				
				
				return score;
				
				
			}
			
			private int evaluateCondition(int tile1, int tile2, int tile3){
				
				int score =0;
				
				if(map.get(tile1) == 0){ //if 1st cell was chosen by AI
					score = 1;
				} else if(map.get(tile1) == 1)  //the enemy has the tile
					score = -1;
			
			
			//check second tile
			if(map.get(tile2) == 0){
				
				if(score == 1){
					score = 10;
					
				} else if (score == -1){
					return 0;    //this tile is the oponents
				} else {
					score = 1; //cell 1 is empty but not tile 2
				}
			} else if(map.get(tile2) == 1){
				if(score == -1){ //the opponent has this
					score = -10;
				} else if(score == 1){  //tile 1 is mine but this one is humans hence no need to check third as inconsequential
					return 0;
				} else { 
					score = -1;  //well tile is is empty but at least this tile is humans.
				}
			}
				
				
			
			//The Third Tile
			if(map.get(tile3) == 0){
				 
				if(score > 0){  //then tile 1 and/or tile 2 is mine
					score *= 10;
				} else if(score < 0){ //cell 1&/or tile 2 is oponents
					return 0;
				} else {
					score =-1;   //both tiles are empty
				
				}
				
			} else if(map.get(tile3) == 1){
				if(score < 0){
					score *= 10;
				} else if(score > 1){  //well the other 2 are possibly AI
					return 0;
				} else {
					score = -1;
				}
				
				
			}
				
			return score;	
					
				
			}
			
			
			
		  private boolean won(int player){
			  
			  
			  int pattern = 0b000000000; 
			  
			  for ( int i= 1; i<=9; i++){
				  
				  if(map.get(i) == player){ //player is 0 for ai, 1 for human
					  pattern |= ( 1 << i); //make the ith bit and make it a 1
				  }
				  
			  }
			  for( int correct : PatternStore){
				  
				  if((pattern & correct) == correct) return true;
				  
			  }
			  //well hasent won then, so 
			  return false;
		  }
			
			
			
			
}
