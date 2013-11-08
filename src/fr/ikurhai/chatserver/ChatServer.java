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

		// Param�trage des diff�rents attributs du serveur
		
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

			// Boucle d'�coute du serveur
			while (true) {

				// On attend une connexion d'un client
				clientSocket = serverSocket.accept();	

				// Une fois le client connect�, on cr�e le buffer de lecture des donn�es envoy�es par le nouveau client
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				// Et on cr�e le flux d'envoi de donn�es vers ce client
				out = new PrintWriter(clientSocket.getOutputStream());

				// Cr�ation du processus de r�ception des messages d'un client
				// (in.readLine() r�cup�re le nom que le client a envoy�)
				new ReceiverThread(in.readLine(), clientSocket, in).start();

				// Envoi du nom et du MOTD du serveur au nouveau client
				out.println(name);
				out.flush();
				out.println(motd);
				out.flush();
				
				// Fin de la boucle, on attend la connexion d'un nouveau client
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
