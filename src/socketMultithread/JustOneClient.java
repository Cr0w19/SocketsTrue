package socketMultithread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;


public class JustOneClient {
	private String ip;
	private static int port;
	
	public JustOneClient(String ip, int port) {
		this.ip = ip;
		JustOneClient.port = port;
	}
	public static void main(String[] args) {
		JustOneClient client = new JustOneClient("127.0.0.1", 12346);
		boolean portNotAvailable = true;
		while (portNotAvailable) {
			try {
				client.startClient();
				portNotAvailable = false;
			}
			catch (IOException ioe) {
				System.err.println("Porta " + port + " occupata, provo la successiva...");
				port++;
			}
			catch (IllegalArgumentException iae) {
				System.err.println("Numero porta non valido.");
				return;
			}
		}
	}
	public void startClient() throws IOException, IllegalArgumentException  {
		Socket socket = new Socket(ip, port);
		System.out.println("Connection established on port: " + port);
		ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
		ObjectOutputStream socketOut = new ObjectOutputStream(socket.getOutputStream());
		Message stdin =  new Message("fuck this", "shit");
		socketOut.writeObject(stdin);
		socketOut.flush();
		try {
			Message socketLine = (Message)socketIn.readObject();
			System.out.println(socketLine.getMessage() + " " + socketLine.getAnotherMessage());
			stdin.setMessage("quit");
		}
		catch (NoSuchElementException e) {
			System.out.println("Connection closed");
		}
		catch (ClassNotFoundException cnfe) {

		}
		finally {
			socketIn.close();
			socketOut.close();
			socket.close();
		}
	}
}
