import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class GameClient {

	public static void main(String[] args){
		try{
			Scanner scan = new Scanner(System.in);
			System.out.println("Welcome to TicTacToe Online!");
			System.out.println("Please type in the host's IP address");
			
			String hostIP = scan.nextLine();
					//"172.18.59.7";
			System.out.println("Please type in the server's port number");

			int port = scan.nextInt();
			boolean myTurn = true;
			System.out.println("Connecting to the game server on port " + port);
			Socket connectionSock = new Socket(hostIP, port);
			DataOutputStream serverOutput = new DataOutputStream(connectionSock.getOutputStream());
			System.out.println("The connection is successful!");
			
			//Start a thread to listen and display data sent by the server
			GameListener listener = new GameListener(connectionSock);
			Thread theThread = new Thread(listener);
			theThread.start();
			
			//Read input from the keyboard and send it to everyone else
			
			Scanner userIn = new Scanner(System.in);
			while(serverOutput != null){
				String data = userIn.nextLine();
				if(!myTurn){
					System.out.println("Its not your turn.");
				}else if(data.equals("0") || data.equals("1") || data.equals("2")){
					serverOutput.writeBytes(data + "\n");
					
				}
				else if(data.equals("quit")){
					serverOutput.close();
					serverOutput = null;
				}else{
					System.out.println("Not valid, try something else.");
				}
				
			}
			System.out.println("Connection Lost");
		}
		catch (IOException e){
			System.out.println(e.getMessage());
			
			
		}
		
		
		
	}
	
	
	
}
