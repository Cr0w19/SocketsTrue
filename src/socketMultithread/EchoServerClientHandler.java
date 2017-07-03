package socketMultithread;

import java.net.*;
import java.io.*;
import java.util.*;

	
public class EchoServerClientHandler implements Runnable {
	private Socket socket;
	public EchoServerClientHandler(Socket socket) {
		this.socket = socket;
	}
	@Override
	public void run() {
		try {
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			//reads and writes in the connection until 'quit' is received
			while (true) {
				Message line =(Message) in.readObject();
				if (line.getMessage().equals("quit")) {
					break;
				}else {
					out.writeObject("Received: " + line.getMessage() + " " + line.getAnotherMessage());
					out.flush();
				}
			}
			//closes streams and socket
			in.close();
			out.close();
			socket.close();
		}
		catch (IOException e) {
			System.err.println(e.getMessage());
		}
		catch (ClassNotFoundException cnfe) {
			System.err.println("boh");
		}
	}

}
