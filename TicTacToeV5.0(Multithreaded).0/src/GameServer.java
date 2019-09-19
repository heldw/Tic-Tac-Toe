import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {

	private Socket[] socketList;
	private Socket[] socketList2;

	private GameBoard[] gameList;
	
	public static void main(String[] args){
		GameServer server = new GameServer();
		try {
			server.getConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public GameServer(){
	}
	
	private void getConnection() throws IOException{
		//Creating an array of sockets to hold each client.
		//Have it set to support 100 clients (50 games) at time but can change that number to whatever we want it to be. 
		socketList = new Socket[100];
		//Creating an array of Gameboards to hold a game for each pair of players.
			gameList = new GameBoard[100];
			System.out.println("Server is up and Running!");
			System.out.println("Waiting for players to connect...");
			ServerSocket serverSocket = new ServerSocket(6789);
		while(true){
		
			int playerNumber = 1;
		int j =0;
			for(int i = 0; i < 100; i++){
				if(i % 2 == 0){
					playerNumber = 1;
					GameBoard game3 = new GameBoard();
					gameList[i] = game3;
				}
				Socket connectionSocket = serverSocket.accept();
				
	//			if(i < 2){
				//Add this socket to the list
				this.socketList[i] = connectionSocket;
				//Send to ClientHandler the socket and arraylist of all sockets

				System.out.println("Player "+ Integer.toString(i+1)+ " connected successfully.");
				
			
				
				GameHandler handler;
				GameBoard game;
			
				//Want to create a new GameHandler for every game,
				//So create a new one when every first player joins
				//eg: player 0, 2, 4, 6, ..., n
				if(i % 2 ==0){
					
				handler = new GameHandler(connectionSocket, this.socketList, gameList[i], playerNumber);
				Thread gameThread = new Thread(handler);
				gameThread.start();
				//System.out.println("AAAA testing");
				playerNumber =-1;
				}
				//If its on odd player then its the second player, so we just reuse the GameHander from player 1. 
		   	else{
				handler = new GameHandler(connectionSocket, this.socketList, gameList[i-1], playerNumber);
				Thread gameThread = new Thread(handler);
				gameThread.start();
			//	System.out.println("AAAA testing2");
				playerNumber =-1;
				}
				
				
			}
			System.out.println("A new game is up and running!");
			
			
			
			
			
			
			Socket connectionSocket = serverSocket.accept();
			//Close out the sockets
			for(int i = 0; i < this.socketList.length; i++){
				socketList[i].close();
			}
			
		}
		
	}
	
	}
	

