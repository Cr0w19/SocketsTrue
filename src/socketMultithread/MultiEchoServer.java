package socketMultithread;

import java.net.*;
import java.io.*;
import java.util.concurrent.*;

public class MultiEchoServer {
	private int port;
	public MultiEchoServer(int port) {
		this.port = port;
	}
	public void startServer() {
		ExecutorService executor = Executors.newCachedThreadPool();
		ServerSocket serverSocket = null;
		boolean portNotAvailable = true;
		while(portNotAvailable) {
			try {
				serverSocket = new ServerSocket(port);
				portNotAvailable = false;
			}
			catch (IOException ioe) {
				System.err.println("Porta " + port + " non disponibile. Provo con la successiva"); //port unavailable
				port++;
			}
			catch (IllegalArgumentException iae) {
				System.err.println("Numero di porta non valido");
				return;
			}
		}
		//Arrives here only if the port is valid
		System.out.println("Server ready on port: " + port);
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				executor.submit(new EchoServerClientHandler(socket));
			}
			catch (IOException e) {
				System.err.println("Connection abrupted");
				break; //arrives here if serverSocket is closed
			}
		}
		executor.shutdown();
	}
	public static void main(String[] args) {
		MultiEchoServer echoServer = new MultiEchoServer(12346);
		echoServer.startServer();
	}

}
