import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class GameListener implements Runnable {

	private Socket connectionSocket = null;
	private boolean myTurn;
	
	GameListener(Socket socket){
		this.connectionSocket = socket;
		
	}

	public void run() {
		
		try{
			BufferedReader serverInput = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			while(true){
	
				String serverText = serverInput.readLine();
			//changes turns
				if(serverText.startsWith("#")){
					printBoardFormatted(serverText.substring(1));
				}else if(serverText.startsWith("~")){
					//wait
				} else if(serverText.startsWith("good")){
					this.myTurn = true;
				} else if(serverText.startsWith("bad")){
					this.myTurn = false;
				} else {
					System.out.println(serverText);
					
				}
				
			}
		}
		catch (Exception e){
			System.out.println("Error: " + e.toString());
			
		}
	}
	//Creates the ticTacToeBoard in a 3x3 format. Also handles placing X's and O's in their appropriate places. 
	private void printBoardFormatted(String boardData){
		String[] lines = boardData.split(";");
		String[][]board = new String[3][3];
		for(int i = 0; i < 3; i++){
			board[i] = lines[i].split(",");
		}
		for(int k = 0; k < 3; k++){
			for(int j = 0 ; j < 3; j++){
				if(board[k][j].equals("1")){
					board[k][j] = "X";
					
				} else if(board[k][j].equals("-1")){
					board[k][j] = "O";
				}else{
					board[k][j] = " ";
				}
			}
		}
		//Prints out the board in as nice 3x3 tic tac toe grid. 
		System.out.println("   0   1   2");
		System.out.format("0 %2s |%2s |%2s \n", board[0][0], board [0][1], board[0][2]);
		System.out.println("  ---|---|---");
		System.out.format("1 %2s |%2s |%2s \n", board[1][0], board [1][1], board[1][2]);
		System.out.println("  ---|---|---");
		System.out.format("2 %2s |%2s |%2s \n", board[2][0], board [2][1], board[2][2]);

	}

}
