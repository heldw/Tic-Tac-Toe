
public class GameBoard {

	

	public volatile int playerMove;
	private int[][] currentBoard;
	
	public GameBoard(){
		this.currentBoard = new int[3][3];
		this.playerMove = 1;
	}
	
	public void resetGame(){
		this.currentBoard = new int[3][3];
		this.playerMove = 1;
		
	}
	//Submitting a move, checking if its valid or not. 
	public boolean submitMove(int i, int j){
		if(this.currentBoard[i][j] != 0){
			return false;
			
		}else{
			this.currentBoard[i][j] = this.playerMove;
			this.playerMove =-this.playerMove;
			return true;
		}
	}
	
	public String printState(){
		String output = "#";
		for(int i = 0; i < 3; i++){
			output+= Integer.toString(this.currentBoard[i][0]) + "," + Integer.toString(this.currentBoard[i][1]) + "," + Integer.toString(this.currentBoard[i][2]) + ";";
		}
		return output;
	}
	//Win checking method
	public int checkWin(){
		boolean bool = true;
		for(int i = 0; i < 3; i++){
			 if ((this.currentBoard[i][0] == this.currentBoard[i][1] && this.currentBoard[i][0] == this.currentBoard[i][2]) && this.currentBoard[i][0] != 0) { 
				 return this.currentBoard[i][0]; 
				 }
	    }
	    for (int i = 0; i < 3; ++i) {
	      if ((this.currentBoard[0][i] == this.currentBoard[1][i] && this.currentBoard[0][i] == this.currentBoard[2][i]) && this.currentBoard[0][i] != 0) {
	    	  return this.currentBoard[0][i];
	    	  }
	    }
	    if ((this.currentBoard[0][0] == this.currentBoard[1][1] && this.currentBoard[0][0] == this.currentBoard[2][2]) && this.currentBoard[0][0] != 0) {
	    	return this.currentBoard[0][0];
	    	}
	    if ((this.currentBoard[2][0] == this.currentBoard[1][1] && this.currentBoard[2][0] == this.currentBoard[0][2]) && this.currentBoard[2][0] != 0) {
	    	return this.currentBoard[2][0];
	    	}
	    for (int i = 0; i < 3; ++i) {
	      for (int j = 0; j < 3; ++j) {
	        if (this.currentBoard[i][j] == 0) { bool = false; }
	      }
	    }
	    if (bool == true) {
	      return 2;
	    } else
	    {
	      return 0;
		}
	}
	
}
