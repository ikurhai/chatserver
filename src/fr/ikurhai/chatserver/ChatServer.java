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
 * @version 0.2
 */
public class ChatServer {

	private ServerSocket serverSocket;
	private int port;
	private String name;
	private String motd;
	private Scanner s;	


	/**
	 * Constructeur par d�fault
	 */
	public ChatServer() {

		this.s = new Scanner(System.in);

		System.out.println("- ChatServer -");

		System.out.print("Enter server name: ");		
		this.name = s.nextLine();

		System.out.print("Enter port: ");		
		this.port = s.nextInt();
		s.nextLine();

		System.out.print("Enter MOTD: ");		
		this.motd = s.nextLine();

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

				out.println(name);
				out.flush();
				out.println(motd);
				out.flush();
				
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
