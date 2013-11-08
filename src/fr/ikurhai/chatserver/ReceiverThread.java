package fr.ikurhai.chatserver;

import java.io.BufferedReader;
import java.net.Socket;

public class ReceiverThread extends Thread {

	private String user;
	private Socket socket;
	private BufferedReader in;

	public ReceiverThread(String name, Socket socket, BufferedReader in){
		super(name);
		this.user = name;
		this.socket = socket;
		this.in = in;
	}


	public void run(){
   
		String message;

		try {
			
			System.out.println("Connection detected: " + user + ".");

			do {
				message = in.readLine();
				System.out.println(user + "> " + message);
			} while (!message.equals("/quit"));

			socket.close();

			System.out.println("Connection closed: " + user + ".");

		} catch (Exception e) {
			System.out.println("Connection interrupted: " + user + ".");
		}
		
	}       

}
