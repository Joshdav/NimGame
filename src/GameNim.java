import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class GameNim extends Game {
	 
    int WinningScore = 10;
    int LosingScore = -10;
    int NeutralScore = 0;    
    
    public GameNim() {
    	currentState = new StateNim();
    }
    
    public boolean isWinState(State state)
    {
        StateNim tstate = (StateNim) state;
        //player who did the last move
        int previous_player = (state.player==0 ? 1 : 0);  
        if (tstate.coins <= 0) {
            return true;
        }
        return false;
    }
	
	
	public Set<State> getSuccessors(State state)
    {
		if(isWinState(state))
			return null;
		
		Set<State> successors = new HashSet<State>();
        StateNim tstate = (StateNim) state;
        
        StateNim successor_state;
        
        for (int i = 0; i < 3; i++){
            successor_state = new StateNim(tstate);
            successor_state.coins -= i+1;
            successor_state.player = (state.player==0 ? 1 : 0); 
                
            successors.add(successor_state);
        }
        
    
        return successors;
    }	
    
    public double eval(State state) 
    {   
    	if(isWinState(state)) {
    	
            int previous_player = (state.player==0 ? 1 : 0); 
	    	if (previous_player == 1) //player went last, computer wins
	            return WinningScore;
	    	else //human wins
	            return LosingScore;
    	}

        return NeutralScore;
    }
    
    
    public static void main(String[] args) throws Exception {
        
        Game game = new GameNim(); 
        Search search = new Search(game);
        int depth = 8;
        
        //needed to get human's move
        StateNim nextState = new StateNim();
        System.out.println(nextState.coins + " Coins remain");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        
        while (true) {
        	
        	nextState = null;
        	
            switch ( game.currentState.player ) {
              case 1: //Human
                  
                  int removal = 0;
                  do {
            	       //get human's move
                        System.out.print("Enter 1-3 coins to remove: ");
                        removal = Integer.parseInt( in.readLine() );
                  } while (removal < 1 || removal > 3);
            	  
                  nextState = new StateNim((StateNim)game.currentState);
                  nextState.coins -= removal;
                  nextState.player = 1;
                  System.out.println("Human: \n" + nextState);
                  break;
                  
              case 0: //Computer
            	  
            	  nextState = (StateNim)search.bestSuccessorState(depth);
            	  nextState.player = 0;
            	  System.out.println("Computer: \n" + nextState);
                  break;
            }
                        
            game.currentState = nextState;
            //change player
            game.currentState.player = (game.currentState.player==0 ? 1 : 0);
            
            //Who wins?
            if ( game.isWinState(game.currentState) ) {
            
            	if (game.currentState.player == 1) //i.e. last move was by the human
            		System.out.println("You win!");
            	else
            		System.out.println("Computer wins!");
            	
            	break;
            }
        }
    }
}