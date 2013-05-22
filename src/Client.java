import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client {
	private Socket clientSocket;
	private int port = 14500;
	private BufferedReader inFromUser, inFromServer;
	private DataOutputStream outToServer;
	
	public static void main (String[] args) {
		
		if (args.length == 1) {
			String address = args[0];
			Client client = new Client();
			try {
				client.getUserInput(address);
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		} else if (args.length < 1) {
			System.out.println("Please enter the server address as the first argument:");
			System.out.println("Usage: java Client <ip-address>");
		} else {
			System.out.println("Too many arguments."); 
			System.out.println("Usage: java Client <ip-address>");
		}
	}
	
	public Client() {
		inFromUser = new BufferedReader(new InputStreamReader(System.in));
	}
	
	private void connectToServer(String address) throws UnknownHostException, IOException {
		System.out.println("Attempting connection...");
		clientSocket = new Socket(InetAddress.getByName(address), this.port);
		inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		outToServer = new DataOutputStream(clientSocket.getOutputStream());
		System.out.println("Connected to: " + clientSocket.getInetAddress().getHostName());
		System.out.println("Send 'QUIT' to terminate session.");
	}
	
	private void getUserInput(String address) throws IOException {
		String fromUser = "";
		String reply = "";
		String timestamp = "";
		while(!fromUser.equalsIgnoreCase("quit")) {
			connectToServer(address);
			System.out.print("\nEnter message: "); 
			
			fromUser = inFromUser.readLine();
			outToServer.writeBytes(fromUser + "\n");
			
			reply = inFromServer.readLine();
			timestamp = inFromServer.readLine();
			System.out.println("Response from server: " + reply);
			System.out.println("Timestamp from server: " + timestamp); 
		}
		System.out.println("Closing connection...");
		clientSocket.close(); 
	}
}
