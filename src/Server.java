/**Server-Client test, with Socket
 * The Server reads what the client writes and repeats it
 * @author Emilio
 *
 */
import java.net.*;
import java.io.*;
import java.util.*;
public class Server {
	/**
	 * @param args
	 */
	private int port;
	private ServerSocket serverSocket;
	
	public Server(int port) {
		this.port = port;
	}
		public static void main(String[] args) {
		Server server = new Server(1337);
		try {
			server.startServer();
		}
		catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	public void startServer() throws IOException {
		//opens a TCP port
		serverSocket = new ServerSocket(port);
		System.out.println("Server socket ready on port: " + port);
		//waits for a connection
		Socket socket = serverSocket.accept();
		System.out.println("Received client connection");
		//opens input and output streams to read and write in the received connection
		Scanner in = new Scanner(socket.getInputStream());
		PrintWriter out = new PrintWriter(socket.getOutputStream());
		//reads and writes in the connection until it receives 'quit'
		while (true) {
			String line = in.nextLine();
			if (line.equals("quit")) {
				break;
			}
			else {
				out.println("Received: " + line);
				out.flush();
			}
		}
		//closes streams and sockets
		System.out.println("Closing sockets");
		in.close();
		out.close();
		socket.close();
		serverSocket.close();
	}
}
