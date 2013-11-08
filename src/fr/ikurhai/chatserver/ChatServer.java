package fr.ikurhai.chatserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
	private String name;
	private Scanner s;	


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
		BufferedReader in;
		PrintWriter out; 

		try {

			System.out.println("Listening on " + port + "...");			

			while (true) {

				clientSocket = serverSocket.accept();	

				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				out = new PrintWriter(clientSocket.getOutputStream());

				new ReceiverThread(in.readLine(), clientSocket, in).start();

				out.println("You are now connected on " + name + ".");
				out.flush();

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
