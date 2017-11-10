/*
    13 Coins initially in a pile
    | | | | | | | | | | | | |
    Remove 1-3 at a time
*/


public class StateNim extends State {
	
    public int coins;
    
    public StateNim() {
    	
        coins = 13;
        player = 1;
    }
    
    public StateNim(StateNim state) {
    	
        this.coins = state.coins;   
        player = state.player;
    }
    
    public String toString() {
    	
    	String ret = String.format("%d coins remain", coins);
    	return ret;
    }
}
