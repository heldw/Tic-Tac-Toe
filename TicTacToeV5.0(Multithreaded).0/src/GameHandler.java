import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class GameHandler implements Runnable {

	public Socket connectionSock;
	public Socket[] socketList;
	public GameBoard game;
	public int playerID;
	
	public GameHandler(Socket sock, Socket[] socketList, GameBoard game, int playerID){
		this.connectionSock = sock;
		this.socketList = socketList;
		this.game = game;
		this.playerID = playerID;
		
	}
	

	public void run() {
		try{
			BufferedReader playerInput = new BufferedReader(new InputStreamReader(this.connectionSock.getInputStream()));
	//checks which player is which. 
			if(this.playerID == 1){
				sendMessage("\nYou are X, your turn is first ." + "\r\n");
				sendMessage("no" + "\r\n");
			}
			else if(this.playerID == -1){
				sendMessage("\nYou are O, your turn is second" + "\r\n");
				sendMessage("yes" + "\r\n");
			}
			else{
				sendMessage("\nError: You are not a valid player." + "\r\n");
				sendMessage("yes" + "\r\n");
			}
			
	//checks whose move it is as long as no one has won the game yet.
		while(this.game.checkWin() == 0){
			sendMessage(this.game.printState() + "\r\n");
			
			
			if(this.game.playerMove == this.playerID){
				//Its your turn
				sendMessage("Please enter a row from (0-2): " + "\r\n");
				String row = playerInput.readLine().trim();
				sendMessage("Please enter a column from (0-2): " + "\r\n");
				String col = playerInput.readLine().trim();
				if(!(this.game.submitMove(Integer.parseInt(row), Integer.parseInt(col)))){
					sendMessage("invalid move." + "\r\n");
				} else {
					sendMessage("no" + "\r\n");
				}
			} else {
				//its the other players turn
				sendMessage("Please wait for opponents move." + "\r\n");
				while(this.game.playerMove != this.playerID){
					Thread.sleep(500);
				}
				sendMessage("no" + "\r\n");
			}
		}
		
		sendMessage(this.game.printState());
		
		int checkResult = this.game.checkWin();
		sendMessage(Integer.toString(checkResult) + "\r\n");
		if(checkResult == this.playerID){
			sendMessage("GAME OVER, YOU WIN!" + "\r\n");
			
			
		}else if(checkResult == 2){
			sendMessage("GAME OVER, Tie Game" + "\r\n");
		
		}else{
			sendMessage("GAME OVER Sorry, you lost the game :(" + "\r\n");
		}
		
	} catch (IOException e){
		System.out.println(e.getMessage());
	} catch (InterruptedException z) {
		System.out.println(z.getMessage());

	}
}
	//gets and prints the message from the output of the client.
	private void sendMessage(String message){
		try{
			DataOutputStream clientOutput = new DataOutputStream(this.connectionSock.getOutputStream());
			clientOutput.writeBytes(message);
			
		} catch (IOException e){
			System.out.println(e.getMessage());
		}
		
	}
	
}
