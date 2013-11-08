package fr.ikurhai.chatserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


/** 
 * Classe serveur du chat
 * 
 * @author ikurhai
 * @version 0.1
 */
public class ChatServer {
	
	private ServerSocket serverSocket;
	private int port;
	private Scanner s;
	private String name;
	
	
	/**
	 * Constructeur par défault
	 */
	public ChatServer() {
		
		this.s = new Scanner(System.in);
		
		System.out.println("- ChatServer -");
		
		System.out.print("Enter server name: ");		
		this.name = s.nextLine();
		
		System.out.print("Enter port: ");		
		this.port = s.nextInt();
		
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Lance le processus du server
	 */
	public void run() {

		Socket clientSocket; 
		PrintWriter out;        
		
		try {
			
			System.out.println("Listening on " + port + ".");
			
			while (true) {
				
				System.out.println("Waiting connections...");

				clientSocket = serverSocket.accept();

				System.out.println("Connection detected!");

				out = new PrintWriter(clientSocket.getOutputStream());
				out.println("You are not connected on " + name + ".");
				out.flush();
				out.println("Disconnected.");
				out.flush();
				
				clientSocket.close();
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
