import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Server {
	
	private ServerSocket welcomeSocket;
	private DateFormat dateFormat;
	private int port = 14500;
	
	public static void main(String[] args) {
		try {
			Server server = new Server();
			while(true) {
				server.acceptConnections();
			}
		} catch (IOException e) {
			e.printStackTrace(); 
		}
	}
	
	public Server() throws IOException {
		System.out.println("Starting server..."); 
		this.welcomeSocket = new ServerSocket(port);
		this.dateFormat = new SimpleDateFormat("MMMMM dd',' yyyy 'at' HH:mm:ss");
	}
	
	public void acceptConnections() throws IOException {
		System.out.println("Accepting connections...\n"); 
		String clientSentence, reply;
		while(true) {
			Socket connectionSocket = welcomeSocket.accept();
			String clientIP;
			int clientPort;
			
			BufferedReader inFromClient = 
					new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			
			clientIP = getClientIP(connectionSocket);
			clientPort = getClientPort(connectionSocket);
			
			System.out.println("Connected to client: " + clientIP + " at Port " + clientPort); 
			clientSentence = inFromClient.readLine();
			
			String timestamp = getTimestamp();
			System.out.println("Received message: " + clientSentence);
			reply = clientSentence + "\n" + timestamp + "\n";
			outToClient.writeBytes(reply);
			System.out.println("Sent timestamp on " + timestamp + " to " + clientIP + "\n");
		}
	}
	
	public String getClientIP(Socket s) {
		return s.getInetAddress().toString();
	}
	
	public int getClientPort(Socket s) {
		return s.getPort();
	}
	
	public String getTimestamp() {
		Date ts = Calendar.getInstance().getTime();
		return this.dateFormat.format(ts); 
	}
}
